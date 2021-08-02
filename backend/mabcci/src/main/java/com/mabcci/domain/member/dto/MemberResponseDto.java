package com.mabcci.domain.member.dto;

import com.mabcci.domain.member.domain.*;
import com.mabcci.domain.model.Email;
import com.mabcci.domain.model.Nickname;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class MemberResponseDto {

    @Valid
    @NotBlank
    private Long id;

    @Valid
    private Email email;

    @Valid
    private Nickname nickname;

    @Valid
    private Gender gender;

    @Valid
    private MemberRole role;

    private int height;

    private int weight;

    private int footSize;

    private BodyType bodyType;

    private MemberResponseDto() {
    }

    public MemberResponseDto(final Member entity) {
        this(entity, entity.memberSpecs());
    }

    private MemberResponseDto(final Member entity, final MemberSpecs memberSpecs) {
        this(entity.id(), entity.email(), entity.nickname(),
                entity.gender(), entity.role(),
                memberSpecs.height(), memberSpecs.weight(), memberSpecs.footSize(), memberSpecs.form());
    }

    public MemberResponseDto(final Long id, @Valid final Email email, @Valid final Nickname nickname,
                                   final Gender gender, final MemberRole role,
                                   final int height, final int weight, final int footSize, final BodyType bodyType) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.role = role;
        this.height = height;
        this.weight = weight;
        this.footSize = footSize;
        this.bodyType = bodyType;
    }

    public final Long getId() {
        return id;
    }

    public final Email getEmail() {
        return email;
    }

    public final Nickname getNickname() {
        return nickname;
    }

    public final Gender getGender() {
        return gender;
    }

    public final MemberRole getRole() {
        return role;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getFootSize() {
        return footSize;
    }

    public BodyType getBodyType() {
        return bodyType;
    }
}