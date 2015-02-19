
package org.robbins.flashcards.cassandra.repository.domain;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Table(value = "user")
public class UserCassandraDto implements Serializable {

    @PrimaryKey
    private UUID id;

    private String openid;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String nickname;
    private String country;
    private String language;
    private Date lastLoginDate;

    public UserCassandraDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(final String openid) {
        this.openid = openid;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public Date getLastLoginDate() {
        return null == lastLoginDate ? null : new Date();
    }

    public void setLastLoginDate(final Date lastLoginDate) {
        this.lastLoginDate = null == lastLoginDate ? null : lastLoginDate;
    }

}
