package com.mabcci.domain.hashtag.dto;

import com.mabcci.domain.hashtag.domain.Hashtag;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public final class HashtagRegisterResponse {

    @NotEmpty
    private List<Hashtag> hashtags;

    private HashtagRegisterResponse() {
    }

    public HashtagRegisterResponse(final List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public final List<Hashtag> getHashtags() {
        return hashtags;
    }
}
