package bsmgg.bsmgg_backend.domain.user.controller;

import bsmgg.bsmgg_backend.domain.user.controller.dto.LoginRequestDto;
import bsmgg.bsmgg_backend.domain.user.controller.dto.SignupRequestDto;
import bsmgg.bsmgg_backend.domain.user.service.UserService;
import bsmgg.bsmgg_backend.global.jwt.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public

    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequestDto requestDto) {
        userService.create(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(userService.login(requestDto));
    }
}
