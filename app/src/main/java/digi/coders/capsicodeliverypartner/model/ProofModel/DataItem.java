package digi.coders.capsicodeliverypartner.model.ProofModel;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes4.dex */
public class DataItem {
    @SerializedName("amount")
    private String amount;
    @SerializedName("date")
    private String date;
    @SerializedName("id")
    private String id;
    @SerializedName("image")
    private String image;
    @SerializedName("merchant_id")
    private String merchantId;
    @SerializedName("remark")
    private String remark;
    @SerializedName("type")
    private String type;

    public String getDate() {
        return this.date;
    }

    public String getRemark() {
        return this.remark;
    }

    public String getImage() {
        return this.image;
    }

    public String getAmount() {
        return this.amount;
    }

    public String getId() {
        return this.id;
    }

    public String getMerchantId() {
        return this.merchantId;
    }

    public String getType() {
        return this.type;
    }
}
