package com.mabcci.domain.auth.ui;

import com.mabcci.domain.auth.application.AuthService;
import com.mabcci.domain.auth.dto.LoginRequest;
import com.mabcci.domain.auth.dto.LogoutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid final LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@Valid @RequestBody final LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);
        return ResponseEntity.noContent()
                .build();
    }
}
