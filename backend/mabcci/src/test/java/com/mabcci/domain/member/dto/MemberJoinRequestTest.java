package com.mabcci.domain.member.dto;

import com.mabcci.domain.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static com.mabcci.domain.member.domain.Gender.MALE;
import static com.mabcci.global.common.EmailTest.EMAIL;
import static com.mabcci.global.common.NicknameTest.NICKNAME;
import static com.mabcci.global.common.PasswordTest.PASSWORD;
import static com.mabcci.global.common.PhoneTest.PHONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemberJoinRequestTest {

    private static final HashSet<String> CATEGORIES = new HashSet<>(Arrays.asList("categoryName"));

    @DisplayName("JoinRequestDto 인스턴스 생성 여부 테스트")
    @Test
    void initialize() {
        final MemberJoinRequest memberJoinRequest = new MemberJoinRequest(EMAIL, PASSWORD, NICKNAME, PHONE, MALE, CATEGORIES);

        assertAll(
                () -> assertThat(memberJoinRequest).isNotNull(),
                () -> assertThat(memberJoinRequest).isExactlyInstanceOf(MemberJoinRequest.class)
        );

    }

    @DisplayName("JoinRequestDto 인스턴스 getter 메서드들 테스트")
    @Test
    void getter_test() {
        final MemberJoinRequest memberJoinRequest = new MemberJoinRequest(EMAIL, PASSWORD, NICKNAME, PHONE, MALE, CATEGORIES);

        assertAll(
                () -> assertThat(memberJoinRequest.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(memberJoinRequest.getPassword()).isEqualTo(PASSWORD),
                () -> assertThat(memberJoinRequest.getNickname()).isEqualTo(NICKNAME),
                () -> assertThat(memberJoinRequest.getPhone()).isEqualTo(PHONE),
                () -> assertThat(memberJoinRequest.getGender()).isEqualTo(MALE)
        );

    }

    @DisplayName("JoinRequestDto 인스턴스 Member 엔티티로 변환 메서드 테스트")
    @Test
    void entity_test() {
        final MemberJoinRequest memberJoinRequest = new MemberJoinRequest(EMAIL, PASSWORD, NICKNAME, PHONE, MALE, CATEGORIES);
        final Member member = memberJoinRequest.member();

        assertAll(
                () -> assertThat(member).isNotNull(),
                () -> assertThat(member).isExactlyInstanceOf(Member.class),
                () -> assertThat(member.nickname()).isEqualTo(NICKNAME)
        );

    }
}