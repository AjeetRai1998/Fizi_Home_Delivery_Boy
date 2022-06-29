package digi.coders.capsicodeliverypartner.model;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes6.dex */
public class Transaction {
    private String amount;
    @SerializedName("created_at")
    private String createdAt;
    private String id;
    @SerializedName("is_status")
    private String isStatus;
    @SerializedName("modified_at")
    private String modifiedAt;
    private String msg;
    @SerializedName("order_id")
    private String orderId;
    @SerializedName("payment_id")
    private String paymentId;
    @SerializedName("txt_id")
    private String txtId;
    private String type;
    @SerializedName("user_id")
    private String userId;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTxtId() {
        return this.txtId;
    }

    public void setTxtId(String txtId) {
        this.txtId = txtId;
    }

    public String getPaymentId() {
        return this.paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIsStatus() {
        return this.isStatus;
    }

    public void setIsStatus(String isStatus) {
        this.isStatus = isStatus;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedAt() {
        return this.modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
