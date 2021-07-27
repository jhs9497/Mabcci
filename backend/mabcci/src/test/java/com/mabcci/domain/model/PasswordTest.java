package com.mabcci.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @DisplayName("Password 인스턴스 생성 여부 테스트")
    @Test
    void constructor_test() {
        final String value = "password";
        final Password password = Password.of(value);

        assertAll(
                () -> assertThat(password).isNotNull(),
                () -> assertThat(password).isExactlyInstanceOf(Password.class)
        );

    }
}