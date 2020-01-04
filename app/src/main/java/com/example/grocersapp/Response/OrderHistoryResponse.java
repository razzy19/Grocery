package com.example.grocersapp.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderHistoryResponse {
    @SerializedName("state")
    private String status;

    @SerializedName("order_history")
    private ArrayList<OrderHistory> orderHistory;

    public OrderHistoryResponse(String status, ArrayList<OrderHistory> orderHistory) {
        this.status = status;
        this.orderHistory = orderHistory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<OrderHistory> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(ArrayList<OrderHistory> orderHistory) {
        this.orderHistory = orderHistory;
    }

             public static class OrderHistory{
                @SerializedName("id")
                private String orderid;

                @SerializedName("scheduled_delivery_date")
                 private String date;

                @SerializedName("total")
                 private  String amount;

                @SerializedName("payment_mode")
                 private String paymentmode;

                @SerializedName("status")
                 private String orderstatus;

                 public OrderHistory(String orderid, String date, String amount, String paymentmode, String orderstatus) {
                     this.orderid = orderid;
                     this.date = date;
                     this.amount = amount;
                     this.paymentmode = paymentmode;
                     this.orderstatus = orderstatus;
                 }


                 public String getOrderid() {
                     return orderid;
                 }

                 public void setOrderid(String orderid) {
                     this.orderid = orderid;
                 }

                 public String getDate() {
                     return date;
                 }

                 public void setDate(String date) {
                     this.date = date;
                 }

                 public String getAmount() {
                     return amount;
                 }

                 public void setAmount(String amount) {
                     this.amount = amount;
                 }

                 public String getPaymentmode() {
                     return paymentmode;
                 }

                 public void setPaymentmode(String paymentmode) {
                     this.paymentmode = paymentmode;
                 }

                 public String getOrderstatus() {
                     return orderstatus;
                 }

                 public void setOrderstatus(String orderstatus) {
                     this.orderstatus = orderstatus;
                 }
             }
}
