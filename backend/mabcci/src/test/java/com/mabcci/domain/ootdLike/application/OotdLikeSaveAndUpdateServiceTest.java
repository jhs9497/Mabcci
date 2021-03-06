package com.mabcci.domain.ootdLike.application;

import com.mabcci.domain.member.domain.Gender;
import com.mabcci.domain.member.domain.Member;
import com.mabcci.domain.member.domain.MemberRepository;
import com.mabcci.domain.member.domain.MemberRole;
import com.mabcci.domain.ootd.domain.Ootd;
import com.mabcci.domain.ootd.domain.OotdRepository;
import com.mabcci.domain.ootdLike.domain.OotdLike;
import com.mabcci.domain.ootdLike.domain.OotdLikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static com.mabcci.domain.member.domain.MemberTest.DESCRIPTION;
import static com.mabcci.domain.member.domain.MemberTest.PICTURE;
import static com.mabcci.global.common.EmailTest.EMAIL;
import static com.mabcci.global.common.NicknameTest.NICKNAME;
import static com.mabcci.global.common.PasswordTest.PASSWORD;
import static com.mabcci.global.common.PhoneTest.PHONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OotdLikeSaveAndUpdateServiceTest {

    @InjectMocks private OotdLikeSaveAndUpdateService ootdLikeSaveAndUpdateService;
    @Mock private OotdLikeRepository ootdLikeRepository;
    @Mock private MemberRepository memberRepository;
    @Mock private OotdRepository ootdRepository;

    private Member member;
    private Ootd ootd;
    private OotdLike ootdLike;

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
        ootdLike = OotdLike.builder()
                .member(member)
                .ootd(ootd)
                .build();
    }

    @DisplayName("OotdLikeSaveAndUpdateService ???????????? ????????? ?????? ?????????")
    @Test
    void save_ootd_like_test() {
        doReturn(Optional.empty()).when(ootdLikeRepository).findByOotdAndNickname(any(), any());
        doReturn(Optional.of(ootd)).when(ootdRepository).findById(any());
        doReturn(Optional.of(member)).when(memberRepository).findByNickName(any());
        doReturn(ootdLike).when(ootdLikeRepository).save(any());

        ootdLikeSaveAndUpdateService.saveOrUpdateOotdLike(ootd.id(), NICKNAME);

        verify(ootdLikeRepository, times(1)).findByOotdAndNickname(any(), any());
        verify(ootdRepository, times(1)).findById(any());
        verify(memberRepository, times(1)).findByNickName(any());
        verify(ootdLikeRepository, times(1)).save(any());
    }

    @DisplayName("OotdLikeSaveAndUpdateService ???????????? ????????? ?????? ?????? ?????????")
    @Test
    void update_ootd_like_test() {
        ReflectionTestUtils.setField(ootdLike, "status", true);
        doReturn(Optional.of(ootdLike)).when(ootdLikeRepository).findByOotdAndNickname(any(), any());

        ootdLikeSaveAndUpdateService.saveOrUpdateOotdLike(ootd.id(), NICKNAME);

        verify(ootdLikeRepository, times(1)).findByOotdAndNickname(any(), any());

        assertThat(ootdLike.status()).isFalse();
    }
}
