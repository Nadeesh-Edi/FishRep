package com.example.fish;

public class DeliveryOrder {
    String orderID, phoneNo, DelAddress, DelDate;

    public DeliveryOrder() {

    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDelAddress() {
        return DelAddress;
    }

    public void setDelAddress(String delAddress) {
        DelAddress = delAddress;
    }

    public String getDelDate() {
        return DelDate;
    }

    public void setDelDate(String delDate) {
        DelDate = delDate;
    }
}
