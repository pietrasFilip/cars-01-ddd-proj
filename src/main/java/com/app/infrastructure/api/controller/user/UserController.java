package com.app.infrastructure.api.controller.user;

import com.app.application.dto.response.ResponseDto;
import com.app.application.dto.user.CreateUserDto;
import com.app.application.dto.user.GetUserDto;
import com.app.application.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseDto<GetUserDto> registerUser(@RequestBody CreateUserDto createUserDto) {
        return new ResponseDto<>(userService.register(createUserDto));
    }
}
