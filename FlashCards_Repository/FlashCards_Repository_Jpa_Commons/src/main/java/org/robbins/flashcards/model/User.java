
package org.robbins.flashcards.model;

import org.joda.time.DateTime;
import org.robbins.flashcards.model.common.AbstractAuditable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user")
@AttributeOverride(name = "id", column = @Column(name = "UserId"))
public class User extends AbstractAuditable<User, String> implements Serializable {

    private static final long serialVersionUID = 6131151516357988050L;

    @Column(name = "OpenId", unique = true, nullable = false)
    private String openid;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "FullName")
    private String fullName;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Nickname")
    private String nickname;

    @Column(name = "Country")
    private String country;

    @Column(name = "Language")
    private String language;

    // @JsonSerialize(using = CustomJsonDateSerializer.class)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LastLoginDate", nullable = true)
    private Date lastLoginDate;

    public User() {
    }

    public User(final String userId) {
        setId(userId);
    }

    public User(final String openid, final String email) {
        this.openid = openid;
        this.email = email;
    }

    public User(final String openid, final String firstName, final String lastName,
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

    public DateTime getLastLoginDate() {
        return null == lastLoginDate ? null : new DateTime(lastLoginDate);
    }

    public void setLastLoginDate(final DateTime lastLoginDate) {
        this.lastLoginDate = null == lastLoginDate ? null : lastLoginDate.toDate();
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
