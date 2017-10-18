package com.areatak.gazette.entities;
        
        import com.areatak.gazette.metadata.CoinType;
        
        import javax.persistence.*;
        import java.io.Serializable;
        import java.util.HashMap;
        
        /**
  * Created by alirezaghias on 7/4/2017 AD.
  */
        @Entity
 @Table(name = "t_coin_payment")
 public class CoinPayment extends BaseEntity implements Parameterable {
 
             @ManyToOne
     private User user;
 
             private double paidAmount;
     private int confirmations;
     private String txId;
     private String stompId;
 
             public void setConfirmations(int confirmations) {
                this.confirmations = confirmations;
            }
 
             public int getConfirmations() {
                return confirmations;
            }
 
             public void setTxId(String txId) {
                this.txId = txId;
            }
 
             public String getTxId() {
                return txId;
            }
 
             public String getStompId() {
                return stompId;
            }
 
             public void setStompId(String stompId) {
                this.stompId = stompId;
            }
 
         
             public enum Status {
         Listening, Paid, Canceled, AmountMisMatched, NotConfirmedYet
     }
     @Enumerated
     private Status status;
     @Enumerated
     private CoinType coinType;
     private double amount;
     @Column(unique = true)
     private String address;
 
             public Status getStatus() {
                return status;
            }
 
             public void setStatus(Status status) {
                this.status = status;
            }
 
             public CoinType getCoinType() {
                return coinType;
            }
 
             public void setCoinType(CoinType coinType) {
                this.coinType = coinType;
            }
 
             public double getAmount() {
                return amount;
            }
 
             public void setAmount(double amount) {
                this.amount = amount;
            }
 
             public String getAddress() {
                return address;
            }
 
             public void setAddress(String address) {
                this.address = address;
            }
     public void setPaidAmount(double paidAmount) {
                this.paidAmount = paidAmount;
            }
 
             public double getPaidAmount() {
                return paidAmount;
            }
 
             public User getUser() {
                return user;
            }
 
             public void setUser(User user) {
                this.user = user;
            }
     @Override
     public HashMap<String, Serializable> toParamsMap() {
                HashMap<String, Serializable> map = new HashMap<>();
                map.put("seq", id);
                map.put("amount", amount);
                map.put("status", status.name());
                map.put("address", address);
                map.put("coinType", coinType.name());
                return map;
            }
 }