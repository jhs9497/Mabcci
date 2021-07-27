package com.mabcci.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Embeddable
public class Email {

    @javax.validation.constraints.Email
    @NotEmpty
    @Column(name = "email")
    private String email;

    public static Email of(final String email) {
        return new Email(email);
    }

    protected Email() {
    }

    private Email(final String email) {
        this.email = email;
    }

    public String email() {
        return this.email;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
