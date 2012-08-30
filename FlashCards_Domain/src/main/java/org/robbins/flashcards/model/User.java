package org.robbins.flashcards.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;
import org.robbins.flashcards.model.common.FlashCardsAppAbstractAuditable;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "user")
@AttributeOverride(name="id", column=@Column(name="UserId"))
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@JsonFilter("apiFilter")
public class User extends FlashCardsAppAbstractAuditable<User, Long> implements Serializable {

	/**
	 * 
	 */
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

	//@JsonSerialize(using = CustomJsonDateSerializer.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LastLoginDate", nullable = false)
	private Date lastLoginDate;

	public User() {
	}

	public User(Long userId) {
		setId(userId);
	}

	public User(String openid, String email) {
		this.openid = openid;
		this.email = email;
	}

	public User(String openid, String firstName, String lastName,
			String fullName, String email, String nickname, String country,
			String language) {
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
	public User getCreatedBy() {
		return super.getCreatedBy();
	}

	@Override
	@JsonIgnore
	public User getLastModifiedBy() {
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

	public DateTime getLastLoginDate() {
		return null == lastLoginDate ? null : new DateTime(lastLoginDate);
	}

	public void setLastLoginDate(DateTime lastLoginDate) {
		this.lastLoginDate = null == lastLoginDate ? null : lastLoginDate.toDate();
	}
	
	/**
	 * toString
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(getClass().getName()).append("@")
				.append(Integer.toHexString(hashCode())).append(" [");
		buffer.append("id").append("='").append(getId()).append("' ");
		buffer.append("openid").append("='").append(getOpenid()).append("' ");
		buffer.append("firstName").append("='").append(getFirstName())
				.append("' ");
		buffer.append("lastName").append("='").append(getLastName())
				.append("' ");
		buffer.append("fullName").append("='").append(getFullName())
				.append("' ");
		buffer.append("email").append("='").append(getEmail()).append("' ");
		buffer.append("nickname").append("='").append(getNickname())
				.append("' ");
		buffer.append("country").append("='").append(getCountry()).append("' ");
		buffer.append("language").append("='").append(getLanguage())
				.append("' ");
		buffer.append("]");

		return buffer.toString();
	}
}
