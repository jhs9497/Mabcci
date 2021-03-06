package com.mabcci.domain.member.domain;

import com.mabcci.domain.BaseTimeEntity;
import com.mabcci.domain.follow.domain.Follow;
import com.mabcci.domain.membercategory.domain.MemberCategory;
import com.mabcci.domain.ootd.domain.Ootd;
import com.mabcci.global.common.Email;
import com.mabcci.global.common.Nickname;
import com.mabcci.global.common.Password;
import com.mabcci.global.common.Phone;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    @AttributeOverride(name = "email", column =
    @Column(name = "member_email", nullable = false, unique = true, updatable = false))
    private Email email;

    @Embedded
    @AttributeOverride(name = "password", column =
    @Column(name = "member_password", nullable = false))
    private Password password;

    @Embedded
    @AttributeOverride(name = "nickname", column =
    @Column(name = "member_nickname", nullable = false, unique = true))
    private Nickname nickname;

    @Embedded
    @AttributeOverride(name = "phone", column =
    @Column(name = "member_phone", nullable = false, unique = true))
    private Phone phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_gender", nullable = false)
    private Gender gender;

    @Column(name = "member_description")
    private String description;

    @Column(name = "member_image")
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role", nullable = false)
    private MemberRole memberRole;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "member_specs_id")
    private MemberSpecs memberSpecs;

    @Column(name = "member_is_popular_mabcci", nullable = false)
    private Boolean isPopularMabcci;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private Set<MemberCategory> memberCategories = new HashSet<>();

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> followings = new HashSet<>();

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> followers = new HashSet<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ootd> ootds = new HashSet<>();

    public static MemberBuilder Builder() {
        return new MemberBuilder();
    }

    public static class MemberBuilder {

        private Email email;
        private Password password;
        private Nickname nickname;
        private Phone phone;
        private Gender gender;
        private String description;
        private String picture;
        private MemberRole memberRole;
        private MemberSpecs memberSpecs;

        private MemberBuilder() {
        }

        public MemberBuilder email(final Email email) {
            this.email = email;
            return this;
        }

        public MemberBuilder password(final Password password) {
            this.password = password;
            return this;
        }

        public MemberBuilder nickname(final Nickname nickname) {
            this.nickname = nickname;
            return this;
        }

        public MemberBuilder phone(final Phone phone) {
            this.phone = phone;
            return this;
        }

        public MemberBuilder gender(final Gender gender) {
            this.gender = gender;
            return this;
        }

        public MemberBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public MemberBuilder picture(final String picture) {
            this.picture = picture;
            return this;
        }

        public MemberBuilder memberRole(final MemberRole memberRole) {
            this.memberRole = memberRole;
            return this;
        }

        public MemberBuilder memberSpecs(final MemberSpecs memberSpecs) {
            this.memberSpecs = memberSpecs;
            return this;
        }

        public Member build() {
            return new Member(this);
        }

    }

    protected Member() {
    }

    protected Member(final MemberBuilder memberBuilder) {
        this.email = memberBuilder.email;
        this.nickname = memberBuilder.nickname;
        this.password = memberBuilder.password;
        this.phone = memberBuilder.phone;
        this.gender = memberBuilder.gender;
        this.description = memberBuilder.description;
        this.picture = memberBuilder.picture;
        this.memberRole = memberBuilder.memberRole;
        this.memberSpecs = memberBuilder.memberSpecs;
        isPopularMabcci = false;
    }

    public Long id() {
        return id;
    }

    public Email email() {
        return email;
    }

    public Nickname nickname() {
        return nickname;
    }

    public Gender gender() {
        return gender;
    }

    public String description() { return description; }

    public String picture() {
        return picture;
    }

    public MemberRole memberRole() {
        return memberRole;
    }

    public MemberSpecs memberSpecs() {
        return memberSpecs;
    }

    public Boolean isPopularMabcci() {
        return isPopularMabcci;
    }

    public Set<MemberCategory> memberCategories() { return memberCategories; }

    public Set<Follow> followings() { return followings; }

    public Set<Follow> followers() { return followers; }

    public Set<Ootd> ootds() { return ootds; }

    public boolean checkPassword(final Password otherPassword) {
        return password.checkPassword(otherPassword);
    }

    public void addMemberCategory(final MemberCategory memberCategory) {
        memberCategory.changeMember(this);
        if(!memberCategories.contains(memberCategory)) {
            memberCategories.add(memberCategory);
        }
    }

    public void update(final Nickname nickname, final Gender gender, final String description, final int height, final int weight, final int footSize, final BodyType bodyType) {
        update(nickname, gender, description, height, weight, footSize, bodyType, this.picture);
    }

    public void update(final Nickname nickname, final Gender gender, final String description,
                         int height, int weight, int footSize, BodyType bodyType, final String picture) {
        this.nickname = nickname;
        this.gender = gender;
        this.description = description;
        this.picture = picture;
        updateMemberSpecs(memberSpecs.update(height, weight, footSize, bodyType));
    }

    public Member updateToPopularMabcci(final Boolean isPopularMabcci) {
        this.isPopularMabcci = isPopularMabcci;
        return this;
    }

    public void updateMemberSpecs(final MemberSpecs memberSpecs) {
        this.memberSpecs = memberSpecs;
    }

    public void clearMemberCategory() {
        memberCategories.stream().forEach(memberCategory -> memberCategory.changeMember(null));
        memberCategories.clear();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Member member = (Member) o;
        return Objects.equals(id(), member.id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id());
    }

}
