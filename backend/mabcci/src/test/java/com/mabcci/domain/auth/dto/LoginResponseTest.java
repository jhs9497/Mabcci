package com.mabcci.domain.auth.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LoginResponseTest {

    @DisplayName(value = "생성 테스트")
    @Test
    public void constructTest() {
        // given
        String accessToken = "test.access.token";
        String refreshToken = "test.refresh.token";

        // when
        LoginResponse loginResponse = new LoginResponse(accessToken, refreshToken);

        // then
        assertAll(
                () -> assertThat(loginResponse).isNotNull(),
                () -> assertThat(loginResponse).isExactlyInstanceOf(LoginResponse.class)
        );
    }

    @DisplayName(value = "getter 테스트")
    @Test
    public void getterTest() {
        // given
        String expectedAccessToken = "test.access.token";
        String expectedRefreshToken = "test.refresh.token";
        LoginResponse loginResponse = new LoginResponse(expectedAccessToken, expectedRefreshToken);

        // when
        String accessToken = loginResponse.getAccessToken();
        String refreshToken = loginResponse.getRefreshToken();

        // then
        assertAll(
                () -> assertThat(accessToken).isEqualTo(expectedAccessToken),
                () -> assertThat(refreshToken).isEqualTo(expectedRefreshToken)
        );
    }
}
