package com.areatak.gazette.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.areatak.gazette.entities.User.companyActivity.Unknown;

/**
 * Created by alirezaghias on 3/12/2017 AD.
 */
@Entity
@Table(name = "t_user")
public class User extends BaseEntity implements Parameterable {
	public enum companyActivity {
		FullServiceAdvertising, GraphicStudio, ProductionCompany, FullServiceDigital, DigitalExpertise, Event, Media, AdvertiserInHouse, Unknown
	}
	
	@Column(name = "c_email", unique = true)
	private String email;
	@Column(name = "c_passwordHash")
	private String passwordHash;
	
	public String getJob() {
		return job;
	}
	
	public void setJob(String job) {
		this.job = job;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public User.companyActivity getCompanyActivity() {
		return companyActivity;
	}
	
	public void setCompanyActivity(User.companyActivity companyActivity) {
		this.companyActivity = companyActivity;
	}
	
	public String getPostalAddress() {
		return postalAddress;
	}
	
	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getPostCode() {
		return postCode;
	}
	
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCompanyPhone() {
		return companyPhone;
	}
	
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	
	public String getCompanyWebsite() {
		return companyWebsite;
	}
	
	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}
	
	public String getCdFirstName() {
		return cdFirstName;
	}
	
	public void setCdFirstName(String cdFirstName) {
		this.cdFirstName = cdFirstName;
	}
	
	public String getCdLastName() {
		return cdLastName;
	}
	
	public void setCdLastName(String cdLastName) {
		this.cdLastName = cdLastName;
	}
	
	//	@Column(name = "c_credit", nullable = true)
//	private Double credit = 0.0;
	@Column(name = "c_job")
	private String job;
	@Column(name = "c_name")
	private String name;
	@Column(name = "c_lastname")
	private String lastName;
	@Column(name = "c_phone")
	private String phone;
	
	@Column(name = "c_company_name", nullable = true)
	private String companyName;
	
	@Column(name = "c_company_acivity", nullable = false)
	@Enumerated
	private companyActivity companyActivity = Unknown;
	
	@Column(name = "c_postal_address", nullable = true)
	private String postalAddress;
	
	@Column(name = "c_city", nullable = true)
	private String city;
	
	@Column(name = "c_post_code", nullable = true)
	private String postCode;
	
	@Column(name = "c_country", nullable = true)
	private String country;
	
	@Column(name = "c_company_phone", nullable = true)
	private String companyPhone;
	
	@Column(name = "c_company_website", nullable = true)
	private String companyWebsite;
	
	@Column(name = "c_cd_first_name", nullable = true)
	private String cdFirstName;
	
	@Column(name = "c_cd_last_name", nullable = true)
	private String cdLastName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Device> deviceList;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Transaction> transactionList;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Media> mediaList;
	
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public List<Device> getDeviceList() {
		return deviceList;
	}
	
	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}
	
	/*public Double getCredit() {
		return credit;
	}
	
	public void setCredit(Double credit) {
		this.credit = credit;
	}*/
	
	public List<Transaction> getTransactionList() {
		return transactionList;
	}
	
	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}
	
	public List<Media> getMediaList() {
		return mediaList;
	}
	
	public void setMediaList(List<Media> mediaList) {
		this.mediaList = mediaList;
	}
	
	@Override
	public HashMap<String, Serializable> toParamsMap() {
		HashMap<String, Serializable> map = new HashMap<>();
		map.put("userId", id);
		map.put("email", email);
		map.put("passwordHash", passwordHash);
		map.put("name", name == null ? "" : name);
		map.put("lastName", lastName == null ? "" : lastName);
//		map.put("credit", credit == null ? 0.0 : credit);
		return map;
	}
	
	
}
