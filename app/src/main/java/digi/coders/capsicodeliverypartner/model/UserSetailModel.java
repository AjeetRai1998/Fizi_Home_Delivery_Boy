package digi.coders.capsicodeliverypartner.model;

/* loaded from: classes6.dex */
public class UserSetailModel {
    public String created_at;
    public String email;
    public String icon;
    public String id;
    public String is_login;
    public String is_status;
    public String is_verified;
    public String login_at;
    public String logout_at;
    public String mobile;
    public String modified_at;
    public String name;
    public String otp;
    public String password;
    public String referral_code;
    public String username;
    public String verified_at;
    public String visit_count;
    public String wallet;

    public UserSetailModel(String id, String name, String username, String email, String mobile, String password, String wallet, String otp, String referral_code, String icon, String is_status, String is_verified, String is_login, String visit_count, String created_at, String verified_at, String login_at, String logout_at, String modified_at) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.wallet = wallet;
        this.otp = otp;
        this.referral_code = referral_code;
        this.icon = icon;
        this.is_status = is_status;
        this.is_verified = is_verified;
        this.is_login = is_login;
        this.visit_count = visit_count;
        this.created_at = created_at;
        this.verified_at = verified_at;
        this.login_at = login_at;
        this.logout_at = logout_at;
        this.modified_at = modified_at;
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

    public String getReferral_code() {
        return this.referral_code;
    }

    public void setReferral_code(String referral_code) {
        this.referral_code = referral_code;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIs_status() {
        return this.is_status;
    }

    public void setIs_status(String is_status) {
        this.is_status = is_status;
    }

    public String getIs_verified() {
        return this.is_verified;
    }

    public void setIs_verified(String is_verified) {
        this.is_verified = is_verified;
    }

    public String getIs_login() {
        return this.is_login;
    }

    public void setIs_login(String is_login) {
        this.is_login = is_login;
    }

    public String getVisit_count() {
        return this.visit_count;
    }

    public void setVisit_count(String visit_count) {
        this.visit_count = visit_count;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getVerified_at() {
        return this.verified_at;
    }

    public void setVerified_at(String verified_at) {
        this.verified_at = verified_at;
    }

    public String getLogin_at() {
        return this.login_at;
    }

    public void setLogin_at(String login_at) {
        this.login_at = login_at;
    }

    public String getLogout_at() {
        return this.logout_at;
    }

    public void setLogout_at(String logout_at) {
        this.logout_at = logout_at;
    }

    public String getModified_at() {
        return this.modified_at;
    }

    public void setModified_at(String modified_at) {
        this.modified_at = modified_at;
    }
}
