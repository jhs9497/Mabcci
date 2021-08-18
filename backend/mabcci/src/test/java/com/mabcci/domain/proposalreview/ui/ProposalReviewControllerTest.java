package com.mabcci.domain.proposalreview.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mabcci.domain.proposalreview.application.ProposalReviewFindService;
import com.mabcci.domain.proposalreview.application.ProposalReviewSaveService;
import com.mabcci.domain.proposalreview.domain.StarRating;
import com.mabcci.domain.proposalreview.dto.request.ProposalReviewSaveRequest;
import com.mabcci.domain.proposalreview.dto.response.ProposalReviewFindResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProposalReviewController.class)
class ProposalReviewControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private ProposalReviewSaveService proposalReviewSaveService;
    @MockBean private ProposalReviewFindService proposalReviewFindService;

    @DisplayName("ProposalReviewController 인스턴스 제안서 리뷰 저장 API 테스트")
    @Test
    void save_proposal_review_api_test() throws Exception {
        doNothing().when(proposalReviewSaveService).saveProposalReview(any());

        final ProposalReviewSaveRequest proposalReviewSaveRequest = new ProposalReviewSaveRequest(1L, StarRating.ZERO, "내용");

        mockMvc.perform(post("/api/proposals/reviews")
                .content(objectMapper.writeValueAsString(proposalReviewSaveRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @DisplayName("ProposalReviewController 인스턴스 제안서 id에 해당하는 제안서 리뷰 상세 조회 API 테스트")
    @Test
    void find_proposal_review_api_test() throws Exception {
        final ProposalReviewFindResponse proposalReviewFindResponse = new ProposalReviewFindResponse(StarRating.ZERO.ordinal(), "내용");

        doReturn(proposalReviewFindResponse).when(proposalReviewFindService).findProposalReviewByProposalId(any());

        mockMvc.perform(get("/api/proposals/{id}/reviews/details", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
