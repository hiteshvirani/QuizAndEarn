package com.example.answerandearn;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class WithdrawRequest {


    private  String userId;
    private String upiAddress;
    private  String requestedBy;

    public WithdrawRequest(){

    }


    public WithdrawRequest(String userId, String upiAddress, String requestedBy) {
        this.userId = userId;
        this.upiAddress = upiAddress;
        this.requestedBy = requestedBy;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUpiAddress() {
        return upiAddress;
    }

    public void setUpiAddress(String upiAddress) {
        this.upiAddress = upiAddress;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    @ServerTimestamp
    private Date createdAt;


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
