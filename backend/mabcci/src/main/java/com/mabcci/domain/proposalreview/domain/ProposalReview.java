package com.mabcci.domain.proposalreview.domain;

import com.mabcci.domain.BaseTimeEntity;
import com.mabcci.domain.proposal.domain.Proposal;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ProposalReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proposal_review_proposal_id", unique = true, nullable = false, updatable = false)
    private Proposal proposal;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "proposal_star_rating", nullable = false)
    private StarRating starRating;

    @Column(name = "proposal_content")
    private String content;

    protected ProposalReview() {
    }

    protected ProposalReview(final ProposalReviewBuilder proposalReviewBuilder) {
        this.proposal = proposalReviewBuilder.proposal;
        this.starRating = proposalReviewBuilder.starRating;
        this.content = proposalReviewBuilder.content;
    }

    public static ProposalReviewBuilder builder() {
        return new ProposalReviewBuilder();
    }

    public Long id() {
        return id;
    }

    public Proposal proposal() {
        return proposal;
    }

    public StarRating starRating() {
        return starRating;
    }

    public String content() {
        return content;
    }

    public static class ProposalReviewBuilder {
        private Proposal proposal;
        private StarRating starRating;
        private String content;

        private ProposalReviewBuilder() {
        }

        public ProposalReviewBuilder proposal(final Proposal proposal) {
            this.proposal = proposal;
            return this;
        }

        public ProposalReviewBuilder starRating(final StarRating starRating) {
            this.starRating = starRating;
            return this;
        }

        public ProposalReviewBuilder content(final String content) {
            this.content = content;
            return this;
        }

        public ProposalReview build() {
            return new ProposalReview(this);
        }
    }
}
