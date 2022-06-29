package digi.coders.capsicodeliverypartner.model;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes6.dex */
public class Merchant {
    private String address;
    @SerializedName("admin_approved")
    private String adminApproved;
    @SerializedName("admin_commission")
    private String adminCommission;
    private String categories;
    private String cities;
    @SerializedName("closing_time")
    private String closingTime;
    @SerializedName("created_at")
    private String createdAt;
    private String description;
    private double discount;
    private String email;
    @SerializedName("estimated_delivery")
    private String estimatedDelivery;
    private String icon;
    private String id;
    @SerializedName("is_login")
    private String isLogin;
    @SerializedName("is_open")
    private String isOpen;
    @SerializedName("is_status")
    private String isStatus;
    @SerializedName("is_verified")
    private String isVerified;
    private String latitude;
    @SerializedName("login_at")
    private String loginAt;
    @SerializedName("logout_at")
    private String logoutAt;
    private String longitude;
    @SerializedName("merchant_category_id")
    private String merchantCategoryId;
    @SerializedName("merchant_category_name")
    private String merchantCategoryName;
    private String mobile;
    @SerializedName("modified_at")
    private String modifiedAt;
    private String name;
    @SerializedName("open_days")
    private String openDays;
    @SerializedName("opening_time")
    private String openingTime;
    private String otp;
    private String ownerName;
    @SerializedName("ownerphoto_back")
    private String ownerphotoBack;
    @SerializedName("ownerphoto_front")
    private String ownerphotoFront;
    @SerializedName("ownerproof_no")
    private String ownerproofNo;
    @SerializedName("ownerproof_type")
    private String ownerproofType;
    private String password;
    @SerializedName("proof_photo")
    private String proofPhoto;
    private String rating;
    @SerializedName("storeproof_type")
    private String storeproofType;
    private String subcategories;
    @SerializedName("taxes_charge")
    private String taxesCharge;
    private String username;
    @SerializedName("verified_at")
    private String verifiedAt;
    @SerializedName("visit_count")
    private String visitCount;
    private String wallet;
    private String wishliststatus;

    public double getDiscount() {
        return this.discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantCategoryId() {
        return this.merchantCategoryId;
    }

    public void setMerchantCategoryId(String merchantCategoryId) {
        this.merchantCategoryId = merchantCategoryId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTaxesCharge() {
        return this.taxesCharge;
    }

    public void setTaxesCharge(String taxesCharge) {
        this.taxesCharge = taxesCharge;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWallet() {
        return this.wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getOtp() {
        return this.otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getOpenDays() {
        return this.openDays;
    }

    public void setOpenDays(String openDays) {
        this.openDays = openDays;
    }

    public String getOpeningTime() {
        return this.openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return this.closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getEstimatedDelivery() {
        return this.estimatedDelivery;
    }

    public void setEstimatedDelivery(String estimatedDelivery) {
        this.estimatedDelivery = estimatedDelivery;
    }

    public String getCities() {
        return this.cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }

    public String getCategories() {
        return this.categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getSubcategories() {
        return this.subcategories;
    }

    public void setSubcategories(String subcategories) {
        this.subcategories = subcategories;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIsStatus() {
        return this.isStatus;
    }

    public void setIsStatus(String isStatus) {
        this.isStatus = isStatus;
    }

    public String getAdminApproved() {
        return this.adminApproved;
    }

    public void setAdminApproved(String adminApproved) {
        this.adminApproved = adminApproved;
    }

    public String getIsVerified() {
        return this.isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public String getIsLogin() {
        return this.isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public String getIsOpen() {
        return this.isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getVisitCount() {
        return this.visitCount;
    }

    public void setVisitCount(String visitCount) {
        this.visitCount = visitCount;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getVerifiedAt() {
        return this.verifiedAt;
    }

    public void setVerifiedAt(String verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public String getLoginAt() {
        return this.loginAt;
    }

    public void setLoginAt(String loginAt) {
        this.loginAt = loginAt;
    }

    public String getLogoutAt() {
        return this.logoutAt;
    }

    public void setLogoutAt(String logoutAt) {
        this.logoutAt = logoutAt;
    }

    public String getModifiedAt() {
        return this.modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getAdminCommission() {
        return this.adminCommission;
    }

    public void setAdminCommission(String adminCommission) {
        this.adminCommission = adminCommission;
    }

    public String getStoreproofType() {
        return this.storeproofType;
    }

    public void setStoreproofType(String storeproofType) {
        this.storeproofType = storeproofType;
    }

    public String getProofPhoto() {
        return this.proofPhoto;
    }

    public void setProofPhoto(String proofPhoto) {
        this.proofPhoto = proofPhoto;
    }

    public String getOwnerproofType() {
        return this.ownerproofType;
    }

    public void setOwnerproofType(String ownerproofType) {
        this.ownerproofType = ownerproofType;
    }

    public String getOwnerproofNo() {
        return this.ownerproofNo;
    }

    public void setOwnerproofNo(String ownerproofNo) {
        this.ownerproofNo = ownerproofNo;
    }

    public String getOwnerphotoFront() {
        return this.ownerphotoFront;
    }

    public void setOwnerphotoFront(String ownerphotoFront) {
        this.ownerphotoFront = ownerphotoFront;
    }

    public String getOwnerphotoBack() {
        return this.ownerphotoBack;
    }

    public void setOwnerphotoBack(String ownerphotoBack) {
        this.ownerphotoBack = ownerphotoBack;
    }

    public String getMerchantCategoryName() {
        return this.merchantCategoryName;
    }

    public void setMerchantCategoryName(String merchantCategoryName) {
        this.merchantCategoryName = merchantCategoryName;
    }

    public String getWishliststatus() {
        return this.wishliststatus;
    }

    public void setWishliststatus(String wishliststatus) {
        this.wishliststatus = wishliststatus;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
