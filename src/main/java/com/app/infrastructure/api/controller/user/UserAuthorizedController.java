package com.app.infrastructure.api.controller.user;

import com.app.application.dto.response.ResponseDto;
import com.app.application.dto.user.GetUserDto;
import com.app.application.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/users")
@RequiredArgsConstructor
public class UserAuthorizedController {
    private final UserService userService;

    @GetMapping("/username/{username}")
    public ResponseDto<GetUserDto> findUserById(@PathVariable String username) {
        return new ResponseDto<>(userService.findByUsername(username));
    }
}
