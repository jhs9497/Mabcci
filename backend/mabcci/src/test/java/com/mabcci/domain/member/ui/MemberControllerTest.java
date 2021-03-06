package com.mabcci.domain.member.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mabcci.domain.member.application.MemberDeleteService;
import com.mabcci.domain.member.application.MemberFindService;
import com.mabcci.domain.member.application.MemberJoinService;
import com.mabcci.domain.member.application.MemberUpdateService;
import com.mabcci.domain.member.domain.Member;
import com.mabcci.domain.member.domain.MemberSpecs;
import com.mabcci.domain.member.dto.request.MemberJoinRequest;
import com.mabcci.domain.member.dto.response.MemberFindSimpleResponses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static com.mabcci.domain.member.application.MemberFindServiceTest.CATEGORY_NAME;
import static com.mabcci.domain.member.domain.Gender.MAN;
import static com.mabcci.domain.member.domain.MemberRole.USER;
import static com.mabcci.domain.member.domain.MemberSpecsTest.*;
import static com.mabcci.global.common.EmailTest.EMAIL;
import static com.mabcci.global.common.NicknameTest.NICKNAME;
import static com.mabcci.global.common.PasswordTest.PASSWORD;
import static com.mabcci.global.common.PhoneTest.PHONE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

    @Autowired private ObjectMapper objectMapper;
    @Autowired private MockMvc mvc;

    @MockBean private MemberJoinService memberJoinService;
    @MockBean private MemberFindService memberFindService;
    @MockBean private MemberUpdateService memberUpdateService;
    @MockBean private MemberDeleteService memberDeleteService;

    private Member member;
    private HashSet<String> categories;
    private MemberSpecs memberSpecs;

    @BeforeEach
    void setUp() {
        memberSpecs = memberSpecs.Builder()
                .height(HEIGHT)
                .weight(WEIGHT)
                .footSize(FOOT_SIZE)
                .form(BODY_TYPE)
                .build();
        member = Member.Builder()
                .email(EMAIL)
                .password(PASSWORD)
                .nickname(NICKNAME)
                .phone(PHONE)
                .gender(MAN)
                .memberRole(USER)
                .memberSpecs(memberSpecs)
                .build();
        categories = new HashSet<>(Arrays.asList(CATEGORY_NAME));
    }


    @DisplayName("MemberController join ????????? ?????????")
    @Test
    public void join_test() throws Exception {
        given(memberJoinService.join(any(), any())).willReturn(member);

        final MemberJoinRequest memberJoinRequest = new MemberJoinRequest(EMAIL, PASSWORD, NICKNAME, PHONE, MAN, categories);
        final String joinRequestDtoString = objectMapper.writeValueAsString(memberJoinRequest);

        mvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(joinRequestDtoString))
                .andExpect(status().isOk());
    }

    @DisplayName("MemberController findByNicknameContains() ????????? ?????????")
    @Test
    public void find_by_nickname_contains_test() throws Exception {
        final MemberFindSimpleResponses memberFindSimpleResponses =
                new MemberFindSimpleResponses(Collections.emptyList());
        given(memberFindService.findByNicknameContains(any())).willReturn(memberFindSimpleResponses);

        mvc.perform(get("/api/members/search?nickname={nickname}", NICKNAME)
                .content(objectMapper.writeValueAsString(memberFindSimpleResponses))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @DisplayName("MemberController findPopularMabccies() ????????? ?????????")
    @Test
    public void find_popular_mabccies_test() throws Exception {
        final MemberFindSimpleResponses memberFindSimpleResponses =
                new MemberFindSimpleResponses(Collections.emptyList());
        given(memberFindService.findPopularMabccies()).willReturn(memberFindSimpleResponses);

        mvc.perform(get("/api/members/mabcci/popular")
                .content(objectMapper.writeValueAsString(memberFindSimpleResponses))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//    @DisplayName("MemberController findByNickName() ????????? ?????????")
//    @Test
//    public void findByNickName_test() throws Exception {
//        doNothing().when(memberUpdateService).update(any(), any(), any(), any(),
//                anyInt(), anyInt(), anyInt(), any(), any(), any());
//        final MockMultipartFile picture = new MockMultipartFile(
//                "picture", "pngPicture.png", MediaType.MULTIPART_FORM_DATA_VALUE, "testPngPicture".getBytes());
//        final MockMultipartFile memberUpdateRequest = new MockMultipartFile(
//                "memberUpdateRequest", "", MediaType.MULTIPART_FORM_DATA_VALUE,
//                ("{" +
//                        "\"nickname\": \"nickname\"" +
//                        "\"gender\": \"WOMAN\"" +
//                        "\"categories\": [\"?????????\", \"?????????\"]" +
//                        "\"description\": \"??????\"" +
//                        "\"height\": \"177\"" +
//                        "\"weight\": \"70\"" +
//                        "\"footSize\": \"250\"" +
//                        "\"bodyType\": \"TRIANGLE\"" +
//                        "}").getBytes());
//
//        mvc.perform(multipart("/api/members/update/nickname")
//                .file("pictures", picture.getBytes())
//                .file("memberUpdateRequest", memberUpdateRequest.getBytes())
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .characterEncoding("UTF-8"))
//                .andExpect(status().isOk());
//    }
//
//
//    @DisplayName("MemberController update ????????? ?????????")
//    @Test
//    public void update_test() throws Exception {
//        final MemberByNickNameResponse memberByNickNameResponse = new MemberByNickNameResponse(member);
//        final MemberUpdateRequest updateRequestDto = new MemberUpdateRequest(NICKNAME, MAN);
//        final String updateRequestDtoString = objectMapper.writeValueAsString(updateRequestDto);
//        final String memberResponseDtoString = objectMapper.writeValueAsString(memberByNickNameResponse);
//
//        given(memberUpdateService.update(any(), any())).willReturn(member);
//
//        mvc.perform(put("/api/members/" + NICKNAME)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(updateRequestDtoString))
//                .andExpect(status().isOk())
//                .andExpect(content().json(memberResponseDtoString));
//    }
//
//    @DisplayName("MemberController delete ????????? ?????????")
//    @Test
//    public void delete_test() throws Exception {
//        final MemberDeleteRequest memberDeleteRequest = new MemberDeleteRequest(NICKNAME, PASSWORD);
//        final String memberDeleteRequestDtoString = objectMapper.writeValueAsString(memberDeleteRequest);
//
//        doNothing().when(memberDeleteService).delete(any(), any());
//
//        mvc.perform(delete("/api/members/" + NICKNAME)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(memberDeleteRequestDtoString))
//                .andExpect(status().isOk());
//    }
}