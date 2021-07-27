package com.mabcci.domain.auth.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LogoutRequestDtoTest {

    @DisplayName(value = "생성 테스트")
    @Test
    public void constructTest() {
        // given
        String email = "example@example.com";

        // when
        LogoutRequestDto logoutRequestDto = new LogoutRequestDto(email);

        // then
        assertThat(logoutRequestDto).isNotNull();
    }

    @DisplayName(value = "email 반환 테스트")
    @Test
    public void getEmailTest() {
        // given
        String expectedEmail = "example@example.com";
        LogoutRequestDto logoutRequestDto = new LogoutRequestDto(expectedEmail);

        // when
        String email = logoutRequestDto.getEmail();

        // then
        assertThat(email).isEqualTo(expectedEmail);
    }
}
