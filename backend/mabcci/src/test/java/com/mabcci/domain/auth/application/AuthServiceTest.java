package com.mabcci.domain.auth.application;

import com.mabcci.domain.auth.common.JwtUtil;
import com.mabcci.domain.auth.domain.RefreshToken;
import com.mabcci.domain.auth.domain.RefreshTokenRepository;
import com.mabcci.domain.auth.domain.vo.JwtToken;
import com.mabcci.domain.auth.domain.vo.JwtTokenType;
import com.mabcci.domain.auth.dto.request.LoginRequest;
import com.mabcci.domain.auth.dto.request.LogoutRequest;
import com.mabcci.domain.auth.dto.response.LoginResponse;
import com.mabcci.domain.auth.exception.NotLoginMemberException;
import com.mabcci.domain.member.domain.Member;
import com.mabcci.domain.member.domain.MemberRepository;
import com.mabcci.domain.member.exception.MemberNotFoundException;
import com.mabcci.global.common.Email;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.mabcci.global.common.EmailTest.EMAIL;
import static com.mabcci.global.common.PasswordTest.PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks private AuthService authService;
    @Mock private RefreshTokenRepository refreshTokenRepository;
    @Mock private MemberRepository memberRepository;
    @Mock private Member member;
    @Mock private JwtUtil jwtUtil;

    @DisplayName("AuthService 인스턴스 로그인 성공 테스트")
    @Test
    void login_success_test() {
        final JwtToken accessToken = JwtToken.of("test.access.token");
        final JwtToken refreshToken = JwtToken.of("test.refresh.token");

        doReturn(Optional.of(member)).when(memberRepository).findByEmailAndPassword(EMAIL, PASSWORD);
        doReturn(accessToken).when(jwtUtil).createToken(JwtTokenType.ACCESS_TOKEN, member);
        doReturn(refreshToken).when(jwtUtil).createToken(JwtTokenType.REFRESH_TOKEN, member);
        doReturn(RefreshToken.builder()
                .email(EMAIL)
                .refreshToken(refreshToken)
                .build()).when(refreshTokenRepository).save(any());

        final LoginRequest loginRequest = new LoginRequest(EMAIL, PASSWORD);
        final LoginResponse loginResponse = authService.login(loginRequest);

        verify(memberRepository, times(1)).findByEmailAndPassword(EMAIL, PASSWORD);
        verify(jwtUtil, times(2)).createToken(any(), any());
        verify(refreshTokenRepository, times(1)).save(any());

        assertAll(
                () -> assertThat(loginResponse.accessToken()).isEqualTo(accessToken),
                () -> assertThat(loginResponse.refreshToken()).isEqualTo(refreshToken)
        );
    }

    @DisplayName("AuthService 인스턴스 로그인 실패 테스트")
    @Test
    void login_fail_test() {
        final LoginRequest loginRequest = new LoginRequest(Email.of(Strings.EMPTY), PASSWORD);
        assertThatExceptionOfType(MemberNotFoundException.class).isThrownBy(() -> authService.login(loginRequest));
    }

    @DisplayName("AuthService 인스턴스 로그아웃 성공 테스트")
    @Test
    void logout_success_test() {
        final RefreshToken refreshToken = RefreshToken.builder()
                .email(EMAIL)
                .refreshToken("test.refresh.token")
                .build();
        doReturn(Optional.of(refreshToken)).when(refreshTokenRepository).findById(any());
        doNothing().when(refreshTokenRepository).delete(any());

        authService.logout(new LogoutRequest(EMAIL));

        verify(refreshTokenRepository, times(1)).findById(any());
        verify(refreshTokenRepository, times(1)).delete(any());
    }

    @DisplayName("AuthService 인스턴스 로그아웃 실패 테스트")
    @Test
    void logout_fail_test() {
        assertThatExceptionOfType(NotLoginMemberException.class).isThrownBy(() -> authService.logout(new LogoutRequest(EMAIL)));
    }
}
