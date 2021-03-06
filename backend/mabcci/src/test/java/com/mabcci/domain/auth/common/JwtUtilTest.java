package com.mabcci.domain.auth.common;

import com.mabcci.domain.auth.domain.vo.ClaimType;
import com.mabcci.domain.auth.domain.vo.JwtToken;
import com.mabcci.domain.auth.domain.vo.JwtTokenType;
import com.mabcci.domain.member.domain.Gender;
import com.mabcci.domain.member.domain.Member;
import com.mabcci.domain.member.domain.MemberRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.mabcci.domain.member.domain.MemberTest.DESCRIPTION;
import static com.mabcci.domain.member.domain.MemberTest.PICTURE;
import static com.mabcci.global.common.EmailTest.EMAIL;
import static com.mabcci.global.common.NicknameTest.NICKNAME;
import static com.mabcci.global.common.PasswordTest.PASSWORD;
import static com.mabcci.global.common.PhoneTest.PHONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JwtUtilTest {

    private Member member;
    private JwtUtil jwtUtil;

    static Stream<Arguments> provide_claim_types_for_create_claim_test() {
        return Stream.of(
                Arguments.of(ClaimType.HEADER, new String[]{"typ", "alg"}),
                Arguments.of(ClaimType.PAYLOAD, new String[]{"iss", "sub", "aud"})
        );
    }

    static Stream<Arguments> provide_token_types_for_tests_about_token() {
        return Arrays.stream(JwtTokenType.values())
                .map(tokenType -> Arguments.of(tokenType));
    }

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
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
        ReflectionTestUtils.setField(jwtUtil, "secretKey", "test secret key for jwt token util test");
    }

    @DisplayName("JwtUtil ???????????? ?????? ?????? ?????????")
    @Test
    void initialize() {
        assertAll(
                () -> assertThat(jwtUtil).isNotNull(),
                () -> assertThat(jwtUtil).isExactlyInstanceOf(JwtUtil.class)
        );
    }

    @DisplayName("JwtUtil ???????????? claim ?????? ?????????")
    @ParameterizedTest(name = "{index}. Claim Type: {0} | keys: {1}")
    @MethodSource("provide_claim_types_for_create_claim_test")
    void create_claim_test(final ClaimType claimType, final String[] expectedKeys) {
        final Map<String, Object> claim = ReflectionTestUtils.invokeMethod(jwtUtil, "createClaim", claimType);

        assertThat(claim.keySet()).contains(expectedKeys);
    }

    @DisplayName("JwtUtil ???????????? payload ?????? ?????????")
    @Test
    void set_payload_test() {
        final Map<String, Object> payLoad = new HashMap<>();

        ReflectionTestUtils.invokeMethod(jwtUtil, "setPayloads", JwtTokenType.ACCESS_TOKEN, member, payLoad);

        assertThat(payLoad.size()).isEqualTo(6);
    }


    @DisplayName("JwtUtil ???????????? payload ?????? ?????????")
    @ParameterizedTest(name = "{index}. Token Type: {0}")
    @MethodSource("provide_token_types_for_tests_about_token")
    void create_payload_test(final JwtTokenType jwtTokenType) {
        final String[] expectedKeys = new String[]{"iss", "sub", "aud", "exp", "nbf", "iat", "email", "nickname", "role"};

        final Map<String, Object> payload = ReflectionTestUtils.invokeMethod(jwtUtil, "createPayload", jwtTokenType, member);

        assertThat(payload.keySet()).contains(expectedKeys);
    }

    @DisplayName("JwtUtil ???????????? secretKey ?????? ?????????")
    @Test
    void create_secret_key_test() {
        final Key key = ReflectionTestUtils.invokeMethod(jwtUtil, "createSecretKey");

        assertThat(key.getAlgorithm()).isEqualTo("HmacSHA256");
    }

    @DisplayName("JwtUtil ???????????? jwtToken??? ????????? ??? ?????? ?????????")
    @ParameterizedTest(name = "{index}. Token Type: {0}")
    @MethodSource("provide_token_types_for_tests_about_token")
    void get_jwt_token_value_test(final JwtTokenType jwtTokenType) {
        final String jwtTokenValue = ReflectionTestUtils.invokeMethod(jwtUtil, "getJwtTokenValue", jwtTokenType, member);

        Arrays.stream(jwtTokenValue.split("."))
                .forEach(jwtTokenValueSplitByComma -> assertThat(jwtTokenValueSplitByComma).isBase64());
    }

    @DisplayName("JwtUtil ???????????? token ?????? ?????????")
    @ParameterizedTest(name = "{index}. Token Type: {0}")
    @MethodSource("provide_token_types_for_tests_about_token")
    void create_token_test(final JwtTokenType jwtTokenType) {
        final JwtToken jwtToken = jwtUtil.createToken(jwtTokenType, member);

        Arrays.stream(jwtToken.jwtToken().split("."))
                .forEach(jwtTokenSplitByComma -> assertThat(jwtTokenSplitByComma).isBase64());
    }

    @DisplayName("JwtUtil ???????????? token ????????? ?????? ?????????")
    @Test
    void is_valid_token_test() throws InterruptedException {
        final JwtToken accessToken = jwtUtil.createToken(JwtTokenType.ACCESS_TOKEN, member);
        final JwtToken refreshToken = jwtUtil.createToken(JwtTokenType.REFRESH_TOKEN, member);
        final JwtToken invalidToken = JwtToken.of("invalid.test.token");

        assertAll(
                () -> assertThat(jwtUtil.isValidToken(accessToken)).isTrue(),
                () -> assertThat(jwtUtil.isValidToken(refreshToken)).isTrue(),
                () -> assertThat(jwtUtil.isValidToken(invalidToken)).isFalse()
        );
    }
}
