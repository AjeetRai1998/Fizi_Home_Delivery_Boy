package digi.coders.capsicodeliverypartner.model;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes6.dex */
public class MyOrder {
    private String accepted_time;
    private UserAddress[] address;
    private String alt_number;
    private String amount;
    private String coupon;
    @SerializedName("coupon_discount")
    private String couponDiscount;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("delivery_boy_id")
    private String deliveryBoyId;
    @SerializedName("delivery_tip")
    private String deliveryTip;
    @SerializedName("delivery_boy_status_accepted")
    private String delivery_boy_status_accepted;
    @SerializedName("delivery_time")
    private String delivery_time;
    private String deliveryearnamount;
    private String id;
    @SerializedName("is_status")
    private String isStatus;
    private String lefttime;
    @SerializedName("merchant_id")
    private String merchantId;
    private Merchant[] merchant_detail;
    private String message;
    private String method;
    @SerializedName("modified_at")
    private String modifiedAt;
    @SerializedName("order_id")
    private String orderId;
    @SerializedName("order_status")
    private String orderStatus;
    private Orderproduct[] orderproduct;
    @SerializedName("other_charge")
    private String otherCharge;
    @SerializedName("payment_response")
    private String paymentResponse;
    private String reach_r;
    private String reach_u;
    @SerializedName("shipping_charge")
    private String shippinCharge;
    private String subtotal;
    private String txn;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("user_detail")
    private User[] user_detail;
    private String wallet;
    private String extra_amount;
    private String driver_location;
    private String customer_location;

    public void setAccepted_time(String accepted_time) {
        this.accepted_time = accepted_time;
    }

    public void setAlt_number(String alt_number) {
        this.alt_number = alt_number;
    }

    public void setDelivery_boy_status_accepted(String delivery_boy_status_accepted) {
        this.delivery_boy_status_accepted = delivery_boy_status_accepted;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public void setLefttime(String lefttime) {
        this.lefttime = lefttime;
    }

    public void setReach_r(String reach_r) {
        this.reach_r = reach_r;
    }

    public void setReach_u(String reach_u) {
        this.reach_u = reach_u;
    }

    public String getExtra_amount() {
        return extra_amount;
    }

    public void setExtra_amount(String extra_amount) {
        this.extra_amount = extra_amount;
    }

    public String getDriver_location() {
        return driver_location;
    }

    public void setDriver_location(String driver_location) {
        this.driver_location = driver_location;
    }

    public String getCustomer_location() {
        return customer_location;
    }

    public void setCustomer_location(String customer_location) {
        this.customer_location = customer_location;
    }

    public String getExtraAmount() {
        return extra_amount;
    }

    public void setExtraAmount(String extraAmount) {
        this.extra_amount = extraAmount;
    }

    public String getDelivery_boy_status_accepted() {
        return this.delivery_boy_status_accepted;
    }

    public String getId() {
        return this.id;
    }

    public String getLefttime() {
        return this.lefttime;
    }

    public String getDelivery_time() {
        return this.delivery_time;
    }

    public String getAccepted_time() {
        return this.accepted_time;
    }

    public String getAlt_number() {
        return this.alt_number;
    }

    public String getReach_r() {
        return this.reach_r;
    }

    public String getReach_u() {
        return this.reach_u;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantId() {
        return this.merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCoupon() {
        return this.coupon;
    }

    public Merchant[] getMerchant_detail() {
        return this.merchant_detail;
    }

    public void setMerchant_detail(Merchant[] merchant_detail) {
        this.merchant_detail = merchant_detail;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getCouponDiscount() {
        return this.couponDiscount;
    }

    public void setCouponDiscount(String couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public String getSubtotal() {
        return this.subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTxn() {
        return this.txn;
    }

    public String getShippinCharge() {
        return this.shippinCharge;
    }

    public void setShippinCharge(String shippinCharge) {
        this.shippinCharge = shippinCharge;
    }

    public void setTxn(String txn) {
        this.txn = txn;
    }

    public String getPaymentResponse() {
        return this.paymentResponse;
    }

    public void setPaymentResponse(String paymentResponse) {
        this.paymentResponse = paymentResponse;
    }

    public User[] getUser_detail() {
        return this.user_detail;
    }

    public void setUser_detail(User[] user_detail) {
        this.user_detail = user_detail;
    }

    public String getWallet() {
        return this.wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDeliveryBoyId() {
        return this.deliveryBoyId;
    }

    public void setDeliveryBoyId(String deliveryBoyId) {
        this.deliveryBoyId = deliveryBoyId;
    }

    public String getDeliveryearnamount() {
        return this.deliveryearnamount;
    }

    public void setDeliveryearnamount(String deliveryearnamount) {
        this.deliveryearnamount = deliveryearnamount;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIsStatus() {
        return this.isStatus;
    }

    public void setIsStatus(String isStatus) {
        this.isStatus = isStatus;
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

    public String getOtherCharge() {
        return this.otherCharge;
    }

    public void setOtherCharge(String otherCharge) {
        this.otherCharge = otherCharge;
    }

    public String getDeliveryTip() {
        return this.deliveryTip;
    }

    public void setDeliveryTip(String deliveryTip) {
        this.deliveryTip = deliveryTip;
    }

    public UserAddress[] getAddress() {
        return this.address;
    }

    public void setAddress(UserAddress[] address) {
        this.address = address;
    }

    public Orderproduct[] getOrderproduct() {
        return this.orderproduct;
    }

    public void setOrderproduct(Orderproduct[] orderproduct) {
        this.orderproduct = orderproduct;
    }
}
