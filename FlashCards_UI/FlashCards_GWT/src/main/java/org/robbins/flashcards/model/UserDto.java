
package org.robbins.flashcards.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class UserDto extends AbstractAuditable<String, String> implements Serializable {

    private static final long serialVersionUID = 6131151516357988050L;

    private String openid;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String nickname;
    private String country;
    private String language;
    private Date lastLoginDate;

    public UserDto() {
    }

    public UserDto(final String userId) {
        setId(userId);
    }

    public UserDto(final String openid, final String email) {
        this.openid = openid;
        this.email = email;
    }

    public UserDto(final String openid, final String firstName, final String lastName,
            final String fullName, final String email, final String nickname,
            final String country, final String language) {
        this.openid = openid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.email = email;
        this.nickname = nickname;
        this.country = country;
        this.language = language;
    }

    @Override
    @JsonIgnore
    public String getCreatedBy() {
        return super.getCreatedBy();
    }

    @Override
    @JsonIgnore
    public String getLastModifiedBy() {
        return super.getLastModifiedBy();
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
        return lastLoginDate;
    }

    public void setLastLoginDate(final Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
}
