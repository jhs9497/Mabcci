package com.mabcci.domain.ootdcomment.domain;

import com.mabcci.domain.BaseTimeEntity;
import com.mabcci.domain.member.domain.Member;
import com.mabcci.domain.ootd.domain.Ootd;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
public class OotdComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ootd_comment_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ootd_comment_member_id", nullable = false, updatable = false)
    private Member member;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ootd_comment_ootd_id", nullable = false, updatable = false)
    private Ootd ootd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ootd_comment_parent_comment_id", updatable = false)
    private OotdComment parentComment;

    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OotdComment> childrenComments = new HashSet<>();

    @NotEmpty
    @Column(name = "ootd_comment_content", nullable = false)
    private String content;

    protected OotdComment() {
    }

    private OotdComment(final OotdCommentBuilder ootdCommentBuilder) {
        this.member = ootdCommentBuilder.member;
        this.ootd = ootdCommentBuilder.ootd;
        this.parentComment = ootdCommentBuilder.parentComment;
        this.content = ootdCommentBuilder.content;
    }

    public static OotdCommentBuilder builder() {
        return new OotdCommentBuilder();
    }

    public Long id() {
        return id;
    }

    public Member member() {
        return member;
    }

    public Ootd ootd() {
        return ootd;
    }

    public Optional<OotdComment> parentComment() {
        return Optional.ofNullable(parentComment);
    }

    public Set<OotdComment> childrenComments() {
        return childrenComments;
    }

    public String content() {
        return content;
    }

    public OotdComment update(final String content) {
        this.content = content;
        return this;
    }

    public static class OotdCommentBuilder {
        private Member member;
        private Ootd ootd;
        private OotdComment parentComment;
        private String content;

        private OotdCommentBuilder() {
        }

        public OotdCommentBuilder member(final Member member) {
            this.member = member;
            return this;
        }

        public OotdCommentBuilder ootd(final Ootd ootd) {
            this.ootd = ootd;
            return this;
        }

        public OotdCommentBuilder parentComment(final OotdComment parentComment) {
            this.parentComment = parentComment;
            return this;
        }

        public OotdCommentBuilder content(final String content) {
            this.content = content;
            return this;
        }

        public OotdComment build() {
            return new OotdComment(this);
        }
    }
}
