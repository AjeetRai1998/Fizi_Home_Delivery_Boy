package digi.coders.capsicodeliverypartner.helper;

import android.os.Bundle;
import com.google.android.gms.common.Scopes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import digi.coders.capsicodeliverypartner.model.ProofModel.ResponseProof;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/* loaded from: classes5.dex */
public interface MyApi {
    @FormUrlEncoded
    @POST("assignDeliveryBoy")
    Call<JsonArray> assignDeliveryBoy(@Field("order_id") String merchantId, @Field("delivery_boy_id") String delivery_boy_id);

    @FormUrlEncoded
    @POST("changeLoginHour.php")
    Call<JsonObject> changeStatus(@Field("id") String id, @Field("flag") String status);

    @FormUrlEncoded
    @POST("codmoneytransfer")
    Call<JsonArray> codMoneyTransfer(@Field("delivery_boy_id") String deliveryBoyId, @Field("amount") String amount);

    @FormUrlEncoded
    @POST("codmoneytransferUpdate")
    Call<JsonArray> codeMoneyTransferUpdate(@Field("delivery_boy_id") String deliveryBoyId, @Field("payment_status") String paymentStatus, @Field("payment_response") Bundle payemntReponse, @Field("txt_id") String transactionId);

    @FormUrlEncoded
    @POST("currentlocation")
    Call<JsonArray> currentLocation(@Field("delivery_boy_id") String deliveryBoyId, @Field("latitude") String latitude, @Field("longitude") String longitude);

    @POST("registers")
    @Multipart
    Call<JsonArray> deliveryBoyRegisteration(@Part("name") RequestBody name, @Part("email") RequestBody email, @Part("mobile") RequestBody mobile, @Part("password") RequestBody password, @Part("bike_no") RequestBody bikeNo, @Part("bike_rc_expiry_date") RequestBody rcExpiryDate, @Part("bike_insurance_expiry_date") RequestBody insuranceExpiryDate, @Part("bike_pollution_expiry_date") RequestBody polutionExpiryDate, @Part("bike_owner_name") RequestBody ownerName, @Part("bike_owner_mobile") RequestBody ownerMobile, @Part("driving_licence_no") RequestBody dlNo, @Part("expiry_date") RequestBody expiryDate, @Part("cities") RequestBody cityId, @Part("del_type") RequestBody del_type, @Part MultipartBody.Part frontImage, @Part MultipartBody.Part backImage);

    @FormUrlEncoded
    @POST("ForgetPassword")
    Call<JsonArray> forgetPassword(@Field("mobile") String mobile);

    @POST("city")
    Call<JsonArray> getCity();

    @FormUrlEncoded
    @POST("codmoneytransferhistory")
    Call<JsonArray> getCodMoneyTransferHistroy(@Field("delivery_boy_id") String deliveryBoyId);

    @FormUrlEncoded
    @POST("deliveryBoys")
    Call<JsonArray> getDeliveryBoys(@Field("order_id") String merchantId, @Field("delivery_boy_id") String delivery_boy_id);

    @FormUrlEncoded
    @POST("changeLoginHour.php")
    Call<JsonObject> getLoginHour(@Field("id") String id, @Field("flag") String flag);

    @FormUrlEncoded
    @POST("getIncentive.php")
    Call<JsonObject> getIncentive(@Field("id") String id, @Field("flag") String flag);

    @FormUrlEncoded
    @POST("getDelCharge.php")
    Call<JsonObject> getDelCharge(@Field("id") String id, @Field("flag") String flag);

    @FormUrlEncoded
    @POST("MyRating")
    Call<JsonArray> getMyRating(@Field("delivery_boy_id") String deliveryBoyId);

    @FormUrlEncoded
    @POST("incentiveUpdate")
    Call<JsonArray> updateIncentive(
            @Field("delivery_boy_id") String deliveryBoyId,
            @Field("amount") String amounts,
            @Field("order_id") String order_id
    );

    @FormUrlEncoded
    @POST("orders")
    Call<JsonArray> getOrder(@Field("delivery_boy_id") String merchantId, @Field("order_status") String orderStatus,@Field("page") String page);

    @FormUrlEncoded
    @POST("return_orders")
    Call<JsonArray> getReturnOrder(@Field("delivery_boy_id") String merchantId, @Field("order_status") String orderStatus,@Field("page") String page);


    @FormUrlEncoded
    @POST("orderswithoutDetails")
    Call<JsonArray> getOrderwithoutDetails(@Field("delivery_boy_id") String merchantId, @Field("order_status") String orderStatus);


    @FormUrlEncoded
    @POST("merchantApi.php")
    Call<ResponseProof> getProof(@Field("m_id") String merchantId, @Field("flag") String status);

    @FormUrlEncoded
    @POST("transactionhistory")
    Call<JsonArray> getTransactionHistory(@Field("delivery_boy_id") String deliveryId,@Field("page") String page);

    @FormUrlEncoded
    @POST("alltransactionhistory")
    Call<JsonArray> getTransactionAllHistory(@Field("delivery_boy_id") String deliveryId);

    @FormUrlEncoded
    @POST("get_vaccination")
    Call<JsonArray> getVaccination(@Field("delivery_boy_id") String delivery_boy);

    @FormUrlEncoded
    @POST("kyc")
    Call<JsonArray> kyc(@Field("delivery_boy_id") String deliveryBoyId, @Field("bankname") String bankName, @Field("ifsc") String ifsc, @Field("panno") String panno, @Field("aadharno") String aadharNo, @Field("account_no") String accountNo, @Field("acountholdername") String accountHolderName, @Field("branch") String branch, @Field("pancard_photo") String panCardPhoto, @Field("vpa_id") String vpa_id, @Field("adharcard_photo") String adharCardPhoto);

    @FormUrlEncoded
    @POST("KycFullDetails")
    Call<JsonArray> kycStatus(@Field("delivery_boy_id") String deliveryBoyId);

    @FormUrlEncoded
    @POST("login")
    Call<JsonArray> login(@Field("mobile") String mobile, @Field("password") String password);

    @FormUrlEncoded
    @POST("logout")
    Call<JsonArray> logout(@Field("delivery_boy_id") String deliveryBoyId);

    @FormUrlEncoded
    @POST("ordersStatus")
    Call<JsonArray> orderStatus(@Field("delivery_boy_id") String orderId, @Field("order_id") String merchantId, @Field("order_status") String orderStatus, @Field("pickeditem") String idList);

    @FormUrlEncoded
    @POST("returnordersStatus")
    Call<JsonArray> returnOrderStatus(@Field("delivery_boy_id") String orderId, @Field("order_id") String merchantId, @Field("order_status") String orderStatus, @Field("pickeditem") String idList);

    @FormUrlEncoded
    @POST("otpVerification")
    Call<JsonArray> otpVerification(@Field("mobile") String mobile, @Field("otp") String otp);

    @FormUrlEncoded
    @POST("photoUpdate")
    Call<JsonArray> photoUpdate(@Field("delivery_boy_id") String id, @Field("icon") String icon);

    @FormUrlEncoded
    @POST("profile")
    Call<JsonArray> profile(@Field("delivery_boy_id") String deliveryBoyId);

    @FormUrlEncoded
    @POST("UpdateProfile")
    Call<JsonArray> profileUpdate(@Field("delivery_boy_id") String id, @Field("name") String name, @Field("email") String email, @Field("address") String address, @Field("description") String description);

    @FormUrlEncoded
    @POST("resendOtp")
    Call<JsonArray> resendOtp(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("ResetPassword")
    Call<JsonArray> resetPassword(@Field("mobile") String mobile, @Field("password") String password);

    @FormUrlEncoded
    @POST("notifyMerchant")
    Call<JsonArray> sendNotification(@Field("delivery_boy_id") String orderId, @Field("order_id") String merchantId);

    @FormUrlEncoded
    @POST("notifyUser")
    Call<JsonArray> sendNotificationUser(@Field("delivery_boy_id") String orderId, @Field("order_id") String merchantId);

    @FormUrlEncoded
    @POST("apptokans")
    Call<JsonObject> sentToken(@Field("delivery_boy_id") String userId, @Field("token") String token);

    @FormUrlEncoded
    @POST("activestatus")
    Call<JsonArray> updateStatus(@Field("delivery_boy_id") String id, @Field("status") String status);

    @FormUrlEncoded
    @POST("vaccination")
    Call<JsonArray> uploadVaccinationDetails(@Field("delivery_boy_id") String delivery_boy, @Field("image") String image);

    @FormUrlEncoded
    @POST("Withdrawal")
    Call<JsonArray> withdrawal(@Field("delivery_boy_id") String orderId, @Field("amount") String amount);
}
