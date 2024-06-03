package bsmgg.bsmgg_backend.domain.user.controller;

import bsmgg.bsmgg_backend.domain.user.controller.dto.AssignRequestDto;
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
    public ResponseEntity<TokenDto> assignUser(@RequestBody AssignRequestDto dto) {
        return ResponseEntity.ok(userService.assign(dto));
    }
}
