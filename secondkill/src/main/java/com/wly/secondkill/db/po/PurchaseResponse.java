package com.wly.secondkill.db.po;

public class PurchaseResponse {

    private String orderId;
    private String info;
    private boolean createOrderSuccess;

    public String getInfo() {
        return info;
    }

    public boolean isCreateOrderSuccess() {
        return createOrderSuccess;
    }

    public void setCreateOrderSuccess(boolean createOrderSuccess) {
        this.createOrderSuccess = createOrderSuccess;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
