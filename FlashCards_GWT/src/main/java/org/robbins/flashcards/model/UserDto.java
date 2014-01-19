
package org.robbins.flashcards.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class UserDto extends AbstractAuditable<UserDto, Long> implements Serializable {

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

    public UserDto(Long userId) {
        setId(userId);
    }

    public UserDto(String openid, String email) {
        this.openid = openid;
        this.email = email;
    }

    public UserDto(String openid, String firstName, String lastName, String fullName,
            String email, String nickname, String country, String language) {
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
    public UserDto getCreatedBy() {
        return super.getCreatedBy();
    }

    @Override
    @JsonIgnore
    public UserDto getLastModifiedBy() {
        return super.getLastModifiedBy();
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
}
