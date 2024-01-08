package com.app.infrastructure.api.controller.user;

import com.app.application.dto.response.ResponseDto;
import com.app.application.dto.user.GetUserDto;
import com.app.application.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserAdminController {
    private final UserService userService;

    @GetMapping("/id/{id}")
    public ResponseDto<GetUserDto> findUserById(@PathVariable Long id) {
        return new ResponseDto<>(userService.findById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseDto<GetUserDto> findUserByEmail(@PathVariable String email) {
        return new ResponseDto<>(userService.findByEmail(email));
    }
}
