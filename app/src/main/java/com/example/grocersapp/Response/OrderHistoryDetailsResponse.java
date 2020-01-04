package com.example.grocersapp.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderHistoryDetailsResponse {
    @SerializedName("state")
    private String status;

    @SerializedName("data")
    private ArrayList<OrderHistoryDetailsResponse.OrderHistory> orderHistory;

    public OrderHistoryDetailsResponse(String status, ArrayList<OrderHistoryDetailsResponse.OrderHistory> orderHistory) {
        this.status = status;
        this.orderHistory = orderHistory;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<OrderHistoryDetailsResponse.OrderHistory> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(ArrayList<OrderHistoryDetailsResponse.OrderHistory> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public static class OrderHistory{
        @SerializedName("id")
        private String orderid;

        @SerializedName("status")
        private String orderstatus;

        @SerializedName("payment_mode")
        private String paymentmode;

        @SerializedName("scheduled_delivery_date")
        private String date;

        @SerializedName("otp")
        String otp;

        @SerializedName("total")
        private  String amount;

        @SerializedName("orders")
        public ArrayList<OrderHistoryDetailsResponse.OrderHistory.orders> orders ;

        public OrderHistory(ArrayList<OrderHistory.orders> orders) {
            this.orders = orders;
        }

        public ArrayList<OrderHistory.orders> getOrders() {
            return orders;
        }

        public void setOrders(ArrayList<OrderHistory.orders> orders) {
            this.orders = orders;
        }

        public OrderHistory(String orderid, String date, String amount, String paymentmode, String orderstatus,String otp) {
            this.orderid = orderid;
            this.date = date;
            this.amount = amount;
            this.paymentmode = paymentmode;
            this.orderstatus = orderstatus;
            this.otp = otp;
        }

        public OrderHistory() {
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
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


        public static class orders{

            @SerializedName("qty")
            String qty ;

            @SerializedName("unit_price")
            String price;

            @SerializedName("name")
            String name;

            @SerializedName("item_id")
            int id;

            public orders(String qty, String price, String name,int id) {
                this.qty = qty;
                this.price = price;
                this.name = name;
                this.id=id;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getQty() {
                return qty;
            }

            public void setQty(String qty) {
                this.qty = qty;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
