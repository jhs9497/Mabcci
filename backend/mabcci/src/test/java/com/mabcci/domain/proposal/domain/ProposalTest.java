package com.mabcci.domain.proposal.domain;

import com.mabcci.domain.member.domain.Gender;
import com.mabcci.domain.member.domain.Member;
import com.mabcci.domain.member.domain.MemberRole;
import com.mabcci.global.common.Email;
import com.mabcci.global.common.Nickname;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static com.mabcci.domain.member.domain.MemberTest.DESCRIPTION;
import static com.mabcci.domain.member.domain.MemberTest.PICTURE;
import static com.mabcci.global.common.EmailTest.EMAIL;
import static com.mabcci.global.common.NicknameTest.NICKNAME;
import static com.mabcci.global.common.PasswordTest.PASSWORD;
import static com.mabcci.global.common.PhoneTest.PHONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProposalTest {

    private Proposal proposal;
    private Member targetMember;
    private Member mabcci;

    @BeforeEach
    void setUp() {
        targetMember = Member.Builder()
                .email(EMAIL)
                .password(PASSWORD)
                .nickname(NICKNAME)
                .phone(PHONE)
                .gender(Gender.MAN)
                .description(DESCRIPTION)
                .picture(PICTURE)
                .memberRole(MemberRole.USER)
                .build();
        mabcci = Member.Builder()
                .email(Email.of("mabcci@example.com"))
                .password(PASSWORD)
                .nickname(Nickname.of("mabcci"))
                .phone(PHONE)
                .gender(Gender.MAN)
                .description(DESCRIPTION)
                .picture(PICTURE)
                .memberRole(MemberRole.MABCCI)
                .build();
        proposal = Proposal.builder()
                .targetMember(targetMember)
                .mabcci(mabcci)
                .top("topPictureUrl")
                .bottom("bottomPictureUrl")
                .shoes("shoesPictureUrl")
                .accessory("accessoryPictureUrl")
                .description("description")
                .build();
    }

    @DisplayName("Proposal ???????????? ?????? ?????? ?????????")
    @Test
    void initialize() {
        assertAll(
                () -> assertThat(proposal).isNotNull(),
                () -> assertThat(proposal).isExactlyInstanceOf(Proposal.class)
        );
    }

    @DisplayName("Proposal ???????????? ????????? ???????????? ????????? ?????? ?????????")
    @Test
    void default_constructor_test() {
        final Proposal proposal = new Proposal();

        assertAll(
                () -> assertThat(proposal).isNotNull(),
                () -> assertThat(proposal).isExactlyInstanceOf(Proposal.class)
        );
    }

    @DisplayName("Proposal ???????????? getter ???????????? ?????????")
    @Test
    void getter_test() {
        ReflectionTestUtils.setField(proposal, "id", 1L);

        assertAll(
                () -> assertThat(proposal.id()).isEqualTo(1L),
                () -> assertThat(proposal.targetMember()).isEqualTo(targetMember),
                () -> assertThat(proposal.mabcci()).isEqualTo(mabcci),
                () -> assertThat(proposal.top()).isEqualTo("topPictureUrl"),
                () -> assertThat(proposal.bottom()).isEqualTo("bottomPictureUrl"),
                () -> assertThat(proposal.shoes()).isEqualTo("shoesPictureUrl"),
                () -> assertThat(proposal.accessory()).isEqualTo("accessoryPictureUrl"),
                () -> assertThat(proposal.description()).isEqualTo("description")
        );
    }

    @DisplayName("Proposal ???????????? ???????????? ????????? ?????? ?????????")
    @Test
    void validate_test() {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final Proposal invalidProposal = Proposal.builder().build();

        final Set<ConstraintViolation<Proposal>> invalidPropertiesOfValidProposal =
                validator.validate(proposal);
        final Set<ConstraintViolation<Proposal>> invalidPropertiesOfInvalidProposal =
                validator.validate(invalidProposal);

        assertAll(
                () -> assertThat(invalidPropertiesOfValidProposal.size()).isEqualTo(0),
                () -> assertThat(invalidPropertiesOfInvalidProposal.size()).isEqualTo(2)
        );
    }
}
