package digi.coders.capsicodeliverypartner.model;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes6.dex */
public class Orderproduct {
    private String addonproduct_prize;
    @SerializedName("addonproduct_prize1")
    private String addonproduct_prize1;
    private String addonproductname;
    private String cgst;
    @SerializedName("created_at")
    private String createdAt;
    private String discount;
    private String gst;
    private String icon;
    private String id;
    @SerializedName("is_status")
    private String isStatus;
    @SerializedName("merchant_id")
    private String merchantId;
    @SerializedName("modified_at")
    private String modifiedAt;
    private String mrp;
    private String name;
    @SerializedName("order_id")
    private String orderId;
    private String price;
    @SerializedName("product_id")
    private String productId;
    private String qty;
    @SerializedName("sell_price")
    private String sellPrice;
    private String sgst;
    private String special_intersections;
    private String title;
    @SerializedName("user_id")
    private String userId;

    public String getId() {
        return this.id;
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

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQty() {
        return this.qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getMrp() {
        return this.mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCgst() {
        return this.cgst;
    }

    public void setCgst(String cgst) {
        this.cgst = cgst;
    }

    public String getSgst() {
        return this.sgst;
    }

    public void setSgst(String sgst) {
        this.sgst = sgst;
    }

    public String getGst() {
        return this.gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getSellPrice() {
        return this.sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getDiscount() {
        return this.discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSpecial_intersections() {
        return this.special_intersections;
    }

    public void setSpecial_intersections(String special_intersections) {
        this.special_intersections = special_intersections;
    }

    public String getAddonproductname() {
        return this.addonproductname;
    }

    public void setAddonproductname(String addonproductname) {
        this.addonproductname = addonproductname;
    }

    public String getAddonproduct_prize() {
        return this.addonproduct_prize;
    }

    public String getAddonproduct_prize1() {
        return this.addonproduct_prize1;
    }

    public void setAddonproduct_prize(String addonproduct_prize) {
        this.addonproduct_prize = addonproduct_prize;
    }
}
