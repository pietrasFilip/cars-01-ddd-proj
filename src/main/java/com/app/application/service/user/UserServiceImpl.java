package com.app.application.service.user;

import com.app.application.dto.token.AuthenticationDto;
import com.app.application.dto.user.CreateUserDto;
import com.app.application.dto.user.GetUserDto;
import com.app.application.service.email.EmailService;
import com.app.application.validator.token.AuthenticationDtoValidator;
import com.app.application.validator.user.CreateUserDtoValidator;
import com.app.domain.users_management.model.User;
import com.app.domain.users_management.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthenticationDtoValidator authenticationDtoValidator;
    private final CreateUserDtoValidator createUserDtoValidator;
    private final Environment environment;
    private static final Logger logger = LogManager.getRootLogger();

    @Override
    public GetUserDto register(CreateUserDto createUserDto) {
        createUserDtoValidator.validate(createUserDto);

        var username = createUserDto.username();
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalStateException("Username already exists");
        }

        var email = createUserDto.email();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException("Email already exists");
        }

        var password = createUserDto.password();
        var userToRegister = createUserDto
                .toUserEntity()
                .toDomainWithPassword(passwordEncoder.encode(password));

        var insertedUser = userRepository
                .save(userToRegister)
                .map(User::toGetUserDto)
                .orElseThrow(IllegalStateException::new);

        var timestamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli() +
                Long.parseLong(environment.getRequiredProperty("registration.activation.time"));
        emailService.send(
                insertedUser.email(),
                environment.getRequiredProperty("registration.email.subject"),
                environment.getRequiredProperty("registration.email.content") +
                        "http://localhost:8080/users/activate?id=%s&timestamp=%s"
                                .formatted(insertedUser.id(), timestamp)
        );
        return insertedUser;
    }

    @Override
    public GetUserDto activate(Long userId, Long expirationTime) {
        if (userId == null) {
            throw new IllegalArgumentException("User id is null");
        }

        if (expirationTime == null) {
            throw new IllegalArgumentException("Expiration time is null");
        }

        logger.debug(expirationTime);
        logger.debug(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
        if (expirationTime < LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()) {
            throw new IllegalArgumentException("Registration token has been expired");
        }

        return userRepository
                .findById(userId)
                .flatMap(user -> userRepository.save(user.withActiveState(1)))
                .orElseThrow()
                .toGetUserDto();
    }

    @Override
    public GetUserDto findById(Long id) {
        return userRepository
                .findById(id)
                .map(User::toGetUserDto)
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public GetUserDto findByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .map(User::toGetUserDto)
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public GetUserDto findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .map(User::toGetUserDto)
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public Long isUserCorrect(AuthenticationDto authenticationDto) {
        authenticationDtoValidator.validate(authenticationDto);

        var exceptionContent = "Wrong username or password";

        var user = userRepository
                .findByUsername(authenticationDto.username()).
                orElseThrow(() -> {
                    logger.error("User with this username not found");
                    return new IllegalStateException(exceptionContent);
                });

        if (!user.isActive()) {
            logger.error("Account is not activated");
            throw new IllegalStateException(exceptionContent);
        }

        if (!passwordEncoder.matches(authenticationDto.password(), user.toEntity().getPassword())) {
            logger.error("Wrong password");
            throw new IllegalStateException("Wrong username or password");
        }

        return user.getId();
    }
}
