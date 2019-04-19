package com.solution.engineering.docket_ereceipt;


import java.util.HashMap;

/**
 * Created by mavra on 09-Mar-18.
 */

public class DataModel {

    private String billNo;
    private String billType;
    private String date;
    private String time;
    private String company;
    private String total;
    private String userId;
    private String phone;
    private String paymentMode;

    public DataModel(){}

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
//
//    public String getDiscount() {
//        return discount;
//    }
//
//    public void setDiscount(String discount) {
//        this.discount = discount;
//    }
//
//    public HashMap<String, String> getZitems() {
//        return zitems;
//    }
//
//    public void setZitems(HashMap<String, String> zitems) {
//        this.zitems = zitems;
//    }
}

//    public DataModel(String billNo, String billType, String date, String time, String company, String phone, String total, String discount, HashMap<String, String> zitems){
//        BillNo = billNo;
//        BillType = billType;
//        Date = date;
//        Time = time;
//        ShopName = company;
//        Phone = phone;
//        Total = total;
//        Discount =discount;
//        Items = zitems;
//    }
//
//    public String getBillNo(){
//        return BillNo;
//    }
//    public String getBillType(){
//        return BillType;
//    }
//    public String getDate(){
//        return Date;
//    }
//    public String getTime(){
//        return Time;
//    }
//    public String getShopName(){
//        return ShopName;
//    }
//    public String getPhone(){
//        return Phone;
//    }
//    public String getTotal(){
//        return Total;
//    }
//    public String getDiscount(){
//        return Discount;
//    }
//    public HashMap<String, String>getItems(){
//        return Items;
//    }