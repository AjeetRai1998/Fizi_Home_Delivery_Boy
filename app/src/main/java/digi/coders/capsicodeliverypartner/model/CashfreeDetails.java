package digi.coders.capsicodeliverypartner.model;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes6.dex */
public class CashfreeDetails {
    @SerializedName("app_id")
    private String appId;
    private String env;
    @SerializedName("secret_key")
    private String secretKey;
    private String token;
    @SerializedName("token_url")
    private String tokenUrl;
    @SerializedName("txt_id")
    private Integer txtId;

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getTxtId() {
        return this.txtId;
    }

    public void setTxtId(Integer txtId) {
        this.txtId = txtId;
    }

    public String getEnv() {
        return this.env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTokenUrl() {
        return this.tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }
}
