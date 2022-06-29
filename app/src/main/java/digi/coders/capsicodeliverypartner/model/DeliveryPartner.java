package digi.coders.capsicodeliverypartner.model;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes6.dex */
public class DeliveryPartner {
    @SerializedName("active_status")
    private String activeStatus;
    private String address;
    @SerializedName("admin_commission")
    private String adminCommission;
    @SerializedName("bicycle_number")
    private String bicycleNumber;
    @SerializedName("bike_insurance_expiry_date")
    private String bikeInsuranceExpiryDate;
    @SerializedName("bike_no")
    private String bikeNo;
    @SerializedName("bike_owner_mobile")
    private String bikeOwnerMobile;
    @SerializedName("bike_owner_name")
    private String bikeOwnerName;
    @SerializedName("bike_pollution_expiry_date")
    private String bikePollutionExpiryDate;
    @SerializedName("bike_rc_expiry_date")
    private String bikeRcExpiryDate;
    private String cashwallet;
    private String cashwallet_limit;
    private String cities;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("delivery_status")
    private String deliveryStatus;
    private String description;
    @SerializedName("driving_licence_no")
    private String drivingLicenceNo;
    private String email;
    @SerializedName("expiry_date")
    private String expiryDate;
    private String floatingcash;
    private String icon;
    private String id;
    @SerializedName("id_icon")
    private String idIcon;
    @SerializedName("id_number")
    private String idNumber;
    @SerializedName("id_type")
    private String idType;
    @SerializedName("is_admin_verify")
    private String isAdminVerify;
    private String isDefault;
    @SerializedName("is_login")
    private String isLogin;
    @SerializedName("is_status")
    private String isStatus;
    @SerializedName("is_verified")
    private String isVerified;
    private String latitude;
    @SerializedName("licence_back_photo")
    private String licenceBackPhoto;
    @SerializedName("licence_front_photo")
    private String licenceFrontPhoto;
    @SerializedName("login_at")
    private String loginAt;
    private String login_hour;
    @SerializedName("logout_at")
    private String logoutAt;
    private String longDuration;
    private String longitude;
    private String mobile;
    @SerializedName("modified_at")
    private String modifiedAt;
    private String name;
    private String otp;
    private String password;
    private String todayearnings;
    private String totalorder;
    private String username;
    @SerializedName("verified_at")
    private String verifiedAt;
    @SerializedName("visit_count")
    private String visitCount;
    private String wallet;
    private String weeklyearnings;

    public String getIsDefault() {
        return this.isDefault;
    }

    public String getTodayearnings() {
        return this.todayearnings;
    }

    public String getCashwallet() {
        return this.cashwallet;
    }

    public String getCashwallet_limit() {
        return this.cashwallet_limit;
    }

    public String getLogin_hour() {
        return this.login_hour;
    }

    public String getTotalorder() {
        return this.totalorder;
    }

    public void setTodayearnings(String todayearnings) {
        this.todayearnings = todayearnings;
    }

    public String getWeeklyearnings() {
        return this.weeklyearnings;
    }

    public void setWeeklyearnings(String weeklyearnings) {
        this.weeklyearnings = weeklyearnings;
    }

    public String getFloatingcash() {
        return this.floatingcash;
    }

    public void setFloatingcash(String floatingcash) {
        this.floatingcash = floatingcash;
    }

    public String getLongDuration() {
        return this.longDuration;
    }

    public void setLongDuration(String longDuration) {
        this.longDuration = longDuration;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBikeNo() {
        return this.bikeNo;
    }

    public void setBikeNo(String bikeNo) {
        this.bikeNo = bikeNo;
    }

    public String getBikeRcExpiryDate() {
        return this.bikeRcExpiryDate;
    }

    public void setBikeRcExpiryDate(String bikeRcExpiryDate) {
        this.bikeRcExpiryDate = bikeRcExpiryDate;
    }

    public String getBikeInsuranceExpiryDate() {
        return this.bikeInsuranceExpiryDate;
    }

    public void setBikeInsuranceExpiryDate(String bikeInsuranceExpiryDate) {
        this.bikeInsuranceExpiryDate = bikeInsuranceExpiryDate;
    }

    public String getBikePollutionExpiryDate() {
        return this.bikePollutionExpiryDate;
    }

    public void setBikePollutionExpiryDate(String bikePollutionExpiryDate) {
        this.bikePollutionExpiryDate = bikePollutionExpiryDate;
    }

    public String getBikeOwnerName() {
        return this.bikeOwnerName;
    }

    public void setBikeOwnerName(String bikeOwnerName) {
        this.bikeOwnerName = bikeOwnerName;
    }

    public String getBikeOwnerMobile() {
        return this.bikeOwnerMobile;
    }

    public void setBikeOwnerMobile(String bikeOwnerMobile) {
        this.bikeOwnerMobile = bikeOwnerMobile;
    }

    public String getDrivingLicenceNo() {
        return this.drivingLicenceNo;
    }

    public void setDrivingLicenceNo(String drivingLicenceNo) {
        this.drivingLicenceNo = drivingLicenceNo;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getLicenceFrontPhoto() {
        return this.licenceFrontPhoto;
    }

    public void setLicenceFrontPhoto(String licenceFrontPhoto) {
        this.licenceFrontPhoto = licenceFrontPhoto;
    }

    public String getLicenceBackPhoto() {
        return this.licenceBackPhoto;
    }

    public void setLicenceBackPhoto(String licenceBackPhoto) {
        this.licenceBackPhoto = licenceBackPhoto;
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

    public String getCities() {
        return this.cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
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

    public String getActiveStatus() {
        return this.activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getDeliveryStatus() {
        return this.deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getIsStatus() {
        return this.isStatus;
    }

    public void setIsStatus(String isStatus) {
        this.isStatus = isStatus;
    }

    public String getIsVerified() {
        return this.isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public String getIsAdminVerify() {
        return this.isAdminVerify;
    }

    public void setIsAdminVerify(String isAdminVerify) {
        this.isAdminVerify = isAdminVerify;
    }

    public String getIsLogin() {
        return this.isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
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

    public String getIdType() {
        return this.idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return this.idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdIcon() {
        return this.idIcon;
    }

    public void setIdIcon(String idIcon) {
        this.idIcon = idIcon;
    }

    public String getBicycleNumber() {
        return this.bicycleNumber;
    }

    public void setBicycleNumber(String bicycleNumber) {
        this.bicycleNumber = bicycleNumber;
    }

    public String getAdminCommission() {
        return this.adminCommission;
    }

    public void setAdminCommission(String adminCommission) {
        this.adminCommission = adminCommission;
    }
}
