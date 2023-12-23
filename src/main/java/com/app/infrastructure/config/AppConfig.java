package com.app.infrastructure.config;

import com.app.application.dto.token.AuthorizationDto;
import com.app.application.service.cars.CarsService;
import com.app.application.service.cars.CarsServiceImpl;
import com.app.application.service.cars.provider.CarsProvider;
import com.app.application.service.email.EmailService;
import com.app.application.service.token.TokensService;
import com.app.application.service.token.TokensServiceImpl;
import com.app.application.service.user.UserService;
import com.app.application.service.user.UserServiceImpl;
import com.app.application.validator.token.AuthenticationDtoValidator;
import com.app.application.validator.token.AuthorizationDtoValidator;
import com.app.application.validator.token.RefreshTokenDtoValidator;
import com.app.application.validator.token.TokensDtoValidator;
import com.app.application.validator.token.generic.TokenValidator;
import com.app.application.validator.token.impl.AuthenticationDtoValidatorImpl;
import com.app.application.validator.token.impl.AuthorizationDtoValidatorImpl;
import com.app.application.validator.token.impl.RefreshTokenDtoValidatorImpl;
import com.app.application.validator.token.impl.TokensDtoValidatorImpl;
import com.app.application.validator.user.CreateUserDtoValidator;
import com.app.application.validator.user.impl.CreateUserDtoValidatorImpl;
import com.app.domain.users_management.model.repository.UserRepository;
import com.app.infrastructure.email.EmailConfiguration;
import com.app.domain.cars_management.model.repository.db.CarRepositoryDb;
import com.app.domain.cars_management.model.repository.json.CarRepositoryJson;
import com.app.domain.cars_management.model.repository.json.ComponentRepositoryJson;
import com.app.domain.cars_management.model.repository.txt.CarRepositoryTxt;
import com.app.domain.cars_management.model.repository.txt.ComponentRepositoryTxt;
import com.app.domain.cars_management.policy.factory.factory.FromDbToCarWithValidator;
import com.app.domain.cars_management.policy.factory.factory.FromJsonToCarWithValidator;
import com.app.domain.cars_management.policy.factory.factory.FromTxtToCarWithValidator;
import com.app.domain.cars_management.policy.factory.loader.car.db.CarDataDbLoader;
import com.app.domain.cars_management.policy.factory.loader.car.db.CarDataDbLoaderImpl;
import com.app.domain.cars_management.policy.factory.loader.car.json.CarDataJsonLoader;
import com.app.domain.cars_management.policy.factory.loader.car.json.CarDataJsonLoaderImpl;
import com.app.domain.cars_management.policy.factory.loader.car.txt.CarDataTxtLoader;
import com.app.domain.cars_management.policy.factory.loader.car.txt.CarDataTxtLoaderImpl;
import com.app.domain.cars_management.policy.factory.processor.CarDataProcessor;
import com.app.domain.cars_management.policy.factory.processor.impl.CarDataProcessorDbImpl;
import com.app.domain.cars_management.policy.factory.processor.impl.CarDataProcessorJsonImpl;
import com.app.domain.cars_management.policy.factory.processor.impl.CarDataProcessorTxtImpl;
import com.app.domain.cars_management.policy.factory.validator.CarDataValidator;
import com.app.domain.cars_management.policy.factory.validator.impl.CarDataValidatorImpl;
import com.app.infrastructure.persistence.repository.impl.db.dao.CarEntityDao;
import com.app.infrastructure.persistence.repository.impl.db.dao.impl.CarEntityDaoImpl;
import com.app.infrastructure.persistence.repository.impl.json.CarRepositoryJsonImpl;
import com.app.infrastructure.persistence.repository.impl.json.ComponentRepositoryJsonImpl;
import com.app.infrastructure.persistence.repository.impl.txt.CarRepositoryTxtImpl;
import com.app.infrastructure.persistence.repository.impl.txt.ComponentRepositoryTxtImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.RequiredArgsConstructor;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@Configuration
@ComponentScan("com.app")
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class AppConfig {
    private final Environment environment;

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("HBN");
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().serializeNulls().enableComplexMapKeySerialization().setPrettyPrinting().create();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    @Bean
    public Mailer mailer() {
        var smtpHost = environment.getRequiredProperty("simplejavamail.smtp.host");
        var smtpPort = environment.getRequiredProperty("simplejavamail.smtp.port", Integer.class);
        var smtpUsername = environment.getRequiredProperty("simplejavamail.smtp.username");
        var password = environment.getRequiredProperty("simplejavamail.smtp.password");
        return MailerBuilder
                .withSMTPServer(smtpHost, smtpPort, smtpUsername, password)
                .withTransportStrategy(TransportStrategy.SMTPS)
                .async()
                .buildMailer();
    }

    @Bean
    EmailConfiguration emailConfiguration() {
        var fromAddress = environment.getRequiredProperty("simplejavamail.defaults.from.address");
        var fromName = environment.getRequiredProperty("simplejavamail.defaults.from.name");
        return new EmailConfiguration(fromAddress, fromName);
    }

    // -----------------------------------------------------------------------------------
    // PERSISTENCE
    // -----------------------------------------------------------------------------------
    @Bean
    public ComponentRepositoryJson componentRepositoryJson() {
        return new ComponentRepositoryJsonImpl(gson(),
                environment.getProperty("json.components.path"));
    }

    @Bean
    public CarRepositoryJson carRepositoryJson() {
        return new CarRepositoryJsonImpl(gson(), environment.getProperty("json.cars.path"),
                componentRepositoryJson());
    }

    @Bean
    public ComponentRepositoryTxt componentRepositoryTxt() {
        return new ComponentRepositoryTxtImpl(environment.getRequiredProperty("txt.components.path"));
    }

    @Bean
    public CarRepositoryTxt carRepositoryTxt() {
        return new CarRepositoryTxtImpl(componentRepositoryTxt(),
                environment.getRequiredProperty("txt.cars.path"));
    }

    // -----------------------------------------------------------------------------------
    // SERVICE
    // -----------------------------------------------------------------------------------

    @Bean
    public CarsService carsService(CarsProvider carsProvider) {
        return new CarsServiceImpl(carsProvider);
    }

    @Bean
    public AuthorizationDtoValidator authorizationDtoValidator() {
        return new AuthorizationDtoValidatorImpl();
    }

    @Bean
    public RefreshTokenDtoValidator refreshTokenDtoValidator() {
        return new RefreshTokenDtoValidatorImpl();
    }

    @Bean
    public TokensDtoValidator tokensDtoValidator() {
        return new TokensDtoValidatorImpl();
    }

    @Bean
    public TokensService tokensService(UserRepository userRepository) {
        var accessTokenExpirationTimeMs = environment.getRequiredProperty("ACCESS_TOKEN_EXPIRATION_TIME_MS");
        var refreshTokenExpirationTimeMs = environment.getRequiredProperty("REFRESH_TOKEN_EXPIRATION_TIME_MS");
        var accessTokenExpirationTimePropertyInRefresh = environment
                .getRequiredProperty("ACCESS_TOKEN_EXPIRATION_TIME_PROPERTY_IN_REFRESH");

        return new TokensServiceImpl(userRepository, secretKey(), authorizationDtoValidator(),
                refreshTokenDtoValidator(), tokensDtoValidator(), accessTokenExpirationTimeMs,
                refreshTokenExpirationTimeMs, accessTokenExpirationTimePropertyInRefresh);
    }

    @Bean
    public AuthenticationDtoValidator authenticationDtoValidator() {
        return new AuthenticationDtoValidatorImpl();
    }

    @Bean
    public CreateUserDtoValidator createUserDtoValidator() {
        return new CreateUserDtoValidatorImpl();
    }

    @Bean
    public UserService userService(UserRepository userRepository, EmailService emailService) {
        return new UserServiceImpl(userRepository, passwordEncoder(), emailService,
                authenticationDtoValidator(), createUserDtoValidator(), environment);
    }

    // -----------------------------------------------------------------------------------
    // ABSTRACT FACTORY
    // -----------------------------------------------------------------------------------

    @Bean
    public CarDataDbLoader carDataDbLoader(CarRepositoryDb carRepositoryDb) {
        return new CarDataDbLoaderImpl(carRepositoryDb);
    }

    @Bean
    public CarDataJsonLoader carDataJsonLoader() {
        return new CarDataJsonLoaderImpl(gson(), carRepositoryJson());
    }

    @Bean
    public CarDataTxtLoader carDataTxtLoader() {
        return new CarDataTxtLoaderImpl(carRepositoryTxt());
    }

    @Bean
    public CarDataValidator carDataValidator() {
        return new CarDataValidatorImpl(
                environment.getRequiredProperty("validator.regex.model.name"),
                environment.getRequiredProperty("validator.regex.items.name")
        );
    }

    @Bean
    public FromDbToCarWithValidator fromDbToCarWithValidator(CarRepositoryDb carRepositoryDb) {
        return new FromDbToCarWithValidator(carRepositoryDb, carDataValidator());
    }

    @Bean
    public FromJsonToCarWithValidator fromJsonToCarWithValidator() {
        return new FromJsonToCarWithValidator(carRepositoryJson(), gson(), carDataValidator());
    }

    @Bean
    public FromTxtToCarWithValidator fromTxtToCarWithValidator() {
        return new FromTxtToCarWithValidator(carRepositoryTxt(), carDataValidator());
    }

    @Bean
    public CarDataProcessor carDataDbProcessor(CarRepositoryDb carRepositoryDb) {
        return new CarDataProcessorDbImpl(fromDbToCarWithValidator(carRepositoryDb));
    }

    @Bean
    public CarDataProcessor carDataJsonProcessor() {
        return new CarDataProcessorJsonImpl(fromJsonToCarWithValidator());
    }

    @Bean
    public CarDataProcessor carDataTxtProcessor() {
        return new CarDataProcessorTxtImpl(fromTxtToCarWithValidator());
    }

    @Bean
    public CarEntityDao carEntityDbDao() {
        return new CarEntityDaoImpl(entityManagerFactory());
    }

}
