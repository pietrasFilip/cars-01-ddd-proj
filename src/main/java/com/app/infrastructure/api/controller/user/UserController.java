package com.app.infrastructure.api.controller.user;

import com.app.application.dto.response.ResponseDto;
import com.app.application.dto.user.CreateUserDto;
import com.app.application.dto.user.GetUserDto;
import com.app.application.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseDto<GetUserDto> registerUser(@RequestBody CreateUserDto createUserDto) {
        return new ResponseDto<>(userService.register(createUserDto));
    }

    @GetMapping("/activate")
    public ResponseDto<GetUserDto> activateUser(@RequestParam Long id, @RequestParam Long timestamp) {
        return new ResponseDto<>(userService.activate(id, timestamp));
    }
}
