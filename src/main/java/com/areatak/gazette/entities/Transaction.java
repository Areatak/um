package com.areatak.gazette.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by farnoosh on 1396/01/27.
 */
@Entity
@Table(name = "t_transaction")
public class Transaction extends BaseEntity implements Parameterable {
	public enum PaymentStatus {
		Pending, // waiting to be paid
		Paid// paid
	}
	
	@Column(name = "c_txId", unique = true)
	private String txId;
	@Column(name = "c_dataHash", unique = true)
	private String dataHash;
	@ManyToOne(cascade = CascadeType.REMOVE)
	private Media file;
	@Column(name = "c_confirmations")
	private int confirmations = 0;
	@Column(name = "c_confirmedBlockHash")
	private String confirmBlockHash;
	@Column(name = "c_confirmTime")
	private Date confirmDate = null;
	@Column(name = "c_desc")
	private String desc;
	
	@Column(name = "c_iaaId")
	@Type(type = "uuid-char")
	private UUID iaaId;
	
	@Column(name = "c_paymentTime")
	private Date paymentTime = null;
	
	@Enumerated(EnumType.STRING)
	private Transaction.PaymentStatus paymentStatus = PaymentStatus.Pending;
	
	@ManyToOne
	private User user;

	public String getTxId() {
		return txId;
	}
	
	public void setTxId(String txId) {
		this.txId = txId;
	}
	
	public String getDataHash() {
		return dataHash;
	}
	
	public void setDataHash(String dataHash) {
		this.dataHash = dataHash;
	}
	
	public Media getFile() {
		return file;
	}
	
	public void setFile(Media file) {
		this.file = file;
	}
	
	public int getConfirmations() {
		return confirmations;
	}
	
	public void setConfirmations(int confirmations) {
		this.confirmations = confirmations;
	}
	
	public String getConfirmBlockHash() {
		return confirmBlockHash;
	}
	
	public void setConfirmBlockHash(String confirmBlockHash) {
		this.confirmBlockHash = confirmBlockHash;
	}
	
	public Date getConfirmDate() {
		return confirmDate;
	}
	
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public UUID getIaaId() {
		return iaaId;
	}
	
	public void setIaaId(UUID iaaId) {
		this.iaaId = iaaId;
	}
	
	public Date getPaymentTime() {
		return paymentTime;
	}
	
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	@Override
	public HashMap<String, Serializable> toParamsMap() {
		HashMap<String, Serializable> map = new HashMap<>();
		map.put("id", id);
		map.put("txId", txId);
		map.put("desc", desc == null ? "" : desc);
		map.put("dataHash", dataHash == null ? "" : dataHash);
		map.put("confirmations", confirmations);
		if (file != null) {
			map.put("fileId", file.getId());
			map.put("fileName", file.getFileName());
		}
		if (created != null)
			map.put("txCreatedDate", created.getTime());
		if (confirmDate != null)
			map.put("txConfirmDate", confirmDate.getTime());
		map.put("txConfirmBlockHash", confirmBlockHash);
		map.put("userId", getUser().getId());
		map.put("paymentStatus", getPaymentStatus());
		if (paymentTime != null)
			map.put("paymentDate", paymentTime.getTime());
		return map;
	}
	
	public HashMap<String, Serializable> toPaymentBillInfo() {
		HashMap<String, Serializable> map = new HashMap<>();
		map.put("txId", txId);
		map.put("desc", desc == null ? "" : desc);
		if (file != null) {
			map.put("fileName", file.getFileName());
		}
		HashMap<String, Serializable> user = new HashMap<>();
		user.put("id", getUser().getId());
		user.put("name", getUser().getName());
		user.put("lastName", getUser().getLastName());
		map.put("user", user);
		
		return map;
	}
	
}
