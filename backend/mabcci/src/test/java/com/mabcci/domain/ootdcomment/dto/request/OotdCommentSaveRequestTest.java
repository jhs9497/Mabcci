package com.mabcci.domain.ootdcomment.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.mabcci.global.common.NicknameTest.NICKNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class OotdCommentSaveRequestTest {

    private OotdCommentSaveRequest ootdCommentSaveRequest;

    @BeforeEach
    void setUp() {
        ootdCommentSaveRequest = new OotdCommentSaveRequest(1L, NICKNAME, null, "내용");
    }

    @DisplayName("OotdCommentRequest 인스턴스 생성 여부 테스트")
    @Test
    void initialize() {
        assertAll(
                () -> assertThat(ootdCommentSaveRequest).isNotNull(),
                () -> assertThat(ootdCommentSaveRequest).isExactlyInstanceOf(OotdCommentSaveRequest.class)
        );
    }
}
