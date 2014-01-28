
package org.robbins.flashcards.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDto extends AbstractPersistableDto implements Serializable {

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
        return null == lastLoginDate ? null : new Date();
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = null == lastLoginDate ? null : lastLoginDate;
    }

    /**
     * toString
     * 
     * @return String
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(
                Integer.toHexString(hashCode())).append(" [");
        buffer.append("id").append("='").append(getId()).append("' ");
        buffer.append("openid").append("='").append(getOpenid()).append("' ");
        buffer.append("firstName").append("='").append(getFirstName()).append("' ");
        buffer.append("lastName").append("='").append(getLastName()).append("' ");
        buffer.append("fullName").append("='").append(getFullName()).append("' ");
        buffer.append("email").append("='").append(getEmail()).append("' ");
        buffer.append("nickname").append("='").append(getNickname()).append("' ");
        buffer.append("country").append("='").append(getCountry()).append("' ");
        buffer.append("language").append("='").append(getLanguage()).append("' ");
        buffer.append("]");

        return buffer.toString();
    }
}
