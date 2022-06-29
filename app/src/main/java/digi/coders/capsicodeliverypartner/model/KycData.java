package digi.coders.capsicodeliverypartner.model;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes6.dex */
public class KycData {
    @SerializedName("account_no")
    private String aadharno;
    private String accountNo;
    private String acountholdername;
    @SerializedName("adharcard_photo")
    private String adharcardPhoto;
    private String bankname;
    private String branch;
    @SerializedName("created_at")
    private String createdAt;
    private String id;
    private String ifsc;
    @SerializedName("is_status")
    private String isStatus;
    @SerializedName("merchant_id")
    private String merchantId;
    @SerializedName("modified_at")
    private String modifiedAt;
    @SerializedName("pancard_photo")
    private String pancardPhoto;
    private String panno;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankname() {
        return this.bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getMerchantId() {
        return this.merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getIfsc() {
        return this.ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getPanno() {
        return this.panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getAadharno() {
        return this.aadharno;
    }

    public void setAadharno(String aadharno) {
        this.aadharno = aadharno;
    }

    public String getAccountNo() {
        return this.accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAcountholdername() {
        return this.acountholdername;
    }

    public void setAcountholdername(String acountholdername) {
        this.acountholdername = acountholdername;
    }

    public String getBranch() {
        return this.branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getIsStatus() {
        return this.isStatus;
    }

    public void setIsStatus(String isStatus) {
        this.isStatus = isStatus;
    }

    public String getPancardPhoto() {
        return this.pancardPhoto;
    }

    public void setPancardPhoto(String pancardPhoto) {
        this.pancardPhoto = pancardPhoto;
    }

    public String getAdharcardPhoto() {
        return this.adharcardPhoto;
    }

    public void setAdharcardPhoto(String adharcardPhoto) {
        this.adharcardPhoto = adharcardPhoto;
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
}
