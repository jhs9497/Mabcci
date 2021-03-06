package com.mabcci.domain.ootdcomment.domain;

import com.mabcci.domain.member.domain.Gender;
import com.mabcci.domain.member.domain.Member;
import com.mabcci.domain.member.domain.MemberRole;
import com.mabcci.domain.ootd.domain.Ootd;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

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
    private OotdComment parentComment;
    private OotdComment childComment;

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
        parentComment = OotdComment.builder()
                .member(member)
                .ootd(ootd)
                .parentComment(null)
                .content("??????")
                .build();
        childComment = OotdComment.builder()
                .member(member)
                .ootd(ootd)
                .parentComment(parentComment)
                .content("??????")
                .build();
    }

    @DisplayName("OotdComment ???????????? ?????? ?????? ?????????")
    @Test
    void initialize() {
        assertAll(
                () -> assertThat(parentComment).isNotNull(),
                () -> assertThat(parentComment).isExactlyInstanceOf(OotdComment.class)
        );
    }

    @DisplayName("OotdComment ???????????? ????????? ???????????? ????????? ?????? ?????????")
    @Test
    void default_constructor_test() {
        final OotdComment ootdComment = new OotdComment();

        assertAll(
                () -> assertThat(ootdComment).isNotNull(),
                () -> assertThat(ootdComment).isExactlyInstanceOf(OotdComment.class)
        );
    }

    @DisplayName("OotdComment ???????????? getter ???????????? ?????????")
    @Test
    void getter_test() {
        ReflectionTestUtils.setField(parentComment, "id", 1L);
        ReflectionTestUtils.setField(parentComment, "childrenComments", new HashSet<>(Set.of(childComment)));

        assertAll(
                () -> assertThat(parentComment.id()).isEqualTo(1L),
                () -> assertThat(parentComment.member()).isEqualTo(member),
                () -> assertThat(parentComment.ootd()).isEqualTo(ootd),
                () -> assertThat(parentComment.parentComment()).isEmpty(),
                () -> assertThat(parentComment.childrenComments().contains(childComment)).isTrue(),
                () -> assertThat(parentComment.content()).isEqualTo("??????"),
                () -> assertThat(childComment.parentComment().get()).isEqualTo(parentComment)
        );
    }

    @DisplayName("OotdComment ???????????? ?????? ?????? ?????????")
    @Test
    void update_test() {
        ReflectionTestUtils.setField(parentComment, "id", 1L);
        final OotdComment updatedComment = parentComment.update("????????? ??????");

        assertAll(
                () -> assertThat(updatedComment.id()).isEqualTo(parentComment.id()),
                () -> assertThat(updatedComment.content()).isEqualTo("????????? ??????")
        );
    }


    @DisplayName("OotdComment ???????????? ???????????? ????????? ?????? ?????????")
    @Test
    void validate_test() {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final OotdComment invalidOotdComment = OotdComment.builder()
                .member(null)
                .ootd(null)
                .parentComment(null)
                .content("")
                .build();

        final Set<ConstraintViolation<OotdComment>> invalidPropertiesOfValidOotdComment =
                validator.validate(parentComment);
        final Set<ConstraintViolation<OotdComment>> invalidPropertiesOfInvalidOotdComment =
                validator.validate(invalidOotdComment);

        assertAll(
                () -> assertThat(invalidPropertiesOfValidOotdComment.size()).isEqualTo(0),
                () -> assertThat(invalidPropertiesOfInvalidOotdComment.size()).isEqualTo(3)
        );
    }
}
