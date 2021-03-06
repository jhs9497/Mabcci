package com.mabcci.domain.proposalreview.domain;

import com.mabcci.domain.member.domain.Gender;
import com.mabcci.domain.member.domain.Member;
import com.mabcci.domain.member.domain.MemberRole;
import com.mabcci.domain.proposal.domain.Proposal;
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

class ProposalReviewTest {

    private ProposalReview proposalReview;
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
        proposalReview = ProposalReview.builder()
                .proposal(proposal)
                .starRating(StarRating.ZERO)
                .content("??????")
                .build();
    }

    @DisplayName("ProposalReview ???????????? ?????? ?????? ?????????")
    @Test
    void initialize() {
        assertAll(
                () -> assertThat(proposalReview).isNotNull(),
                () -> assertThat(proposalReview).isExactlyInstanceOf(ProposalReview.class)
        );
    }

    @DisplayName("ProposalReview ???????????? ????????? ???????????? ????????? ?????? ?????????")
    @Test
    void default_constructor_test() {
        final ProposalReview proposalReview = new ProposalReview();

        assertAll(
                () -> assertThat(proposalReview).isNotNull(),
                () -> assertThat(proposalReview).isExactlyInstanceOf(ProposalReview.class)
        );
    }

    @DisplayName("ProposalReview ???????????? getter ???????????? ?????????")
    @Test
    void getter_test() {
        ReflectionTestUtils.setField(proposalReview, "id", 1L);

        assertAll(
                () -> assertThat(proposalReview.id()).isEqualTo(1L),
                () -> assertThat(proposalReview.proposal()).isEqualTo(proposal),
                () -> assertThat(proposalReview.starRating()).isEqualTo(StarRating.ZERO),
                () -> assertThat(proposalReview.content()).isEqualTo("??????")
        );
    }

    @DisplayName("ProposalReview ???????????? ???????????? ????????? ?????? ?????????")
    @Test
    void validate_test() {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final ProposalReview invalidProposalReview = new ProposalReview();

        final Set<ConstraintViolation<ProposalReview>> invalidPropertiesOfValidProposalReview =
                validator.validate(proposalReview);
        final Set<ConstraintViolation<ProposalReview>> invalidPropertiesOfInvalidProposalReview =
                validator.validate(invalidProposalReview);

        assertAll(
                () -> assertThat(invalidPropertiesOfValidProposalReview.size()).isEqualTo(0),
                () -> assertThat(invalidPropertiesOfInvalidProposalReview.size()).isEqualTo(2)
        );
    }
}
