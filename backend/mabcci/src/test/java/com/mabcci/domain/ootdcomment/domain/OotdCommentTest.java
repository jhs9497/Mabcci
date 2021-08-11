package com.mabcci.domain.ootdcomment.domain;

import com.mabcci.domain.member.domain.Gender;
import com.mabcci.domain.member.domain.Member;
import com.mabcci.domain.member.domain.MemberRole;
import com.mabcci.domain.ootd.domain.Ootd;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.mabcci.domain.member.domain.MemberTest.DESCRIPTION;
import static com.mabcci.domain.member.domain.MemberTest.PICTURE;
import static com.mabcci.global.common.EmailTest.EMAIL;
import static com.mabcci.global.common.NicknameTest.NICKNAME;
import static com.mabcci.global.common.PasswordTest.PASSWORD;
import static com.mabcci.global.common.PhoneTest.PHONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class OotdCommentTest {

    private Member member;
    private Ootd ootd;
    private OotdComment ootdComment;

    @BeforeEach
    void setUp() {
        member = Member.Builder()
                .email(EMAIL)
                .password(PASSWORD)
                .nickname(NICKNAME)
                .phone(PHONE)
                .gender(Gender.MAN)
                .description(DESCRIPTION)
                .picture(PICTURE)
                .memberRole(MemberRole.USER)
                .build();
        ootd = Ootd.builder()
                .member(member)
                .content("content")
                .top("top")
                .bottom("bottom")
                .shoes("shoes")
                .accessory("accessory")
                .views(0L)
                .build();
        ootdComment = OotdComment.builder()
                .member(member)
                .ootd(ootd)
                .parentComment(Optional.empty())
                .content("내용")
                .build();
    }

    @DisplayName("OotdComment 인스턴스 생성 여부 테스트")
    @Test
    void initialize() {
        assertAll(
                () -> assertThat(ootdComment).isNotNull(),
                () -> assertThat(ootdComment).isInstanceOf(OotdComment.class)
        );
    }

    @DisplayName("OotdComment 인스턴스 디폴트 생성자를 이용한 생성 테스트")
    @Test
    void default_constructor_test() {
        final OotdComment ootdComment = new OotdComment();
        assertAll(
                () -> assertThat(ootdComment).isNotNull(),
                () -> assertThat(ootdComment).isInstanceOf(OotdComment.class)
        );
    }

}
