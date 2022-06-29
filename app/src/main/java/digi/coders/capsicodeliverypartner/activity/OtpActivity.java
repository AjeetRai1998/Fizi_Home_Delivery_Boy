package digi.coders.capsicodeliverypartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.messaging.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import digi.coders.capsicodeliverypartner.databinding.ActivityOtpBinding;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.helper.ShowProgress;
import digi.coders.capsicodeliverypartner.singletask.SingleTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes5.dex */
public class OtpActivity extends AppCompatActivity {
    ActivityOtpBinding binding;
    private int key;
    private String mobile;
    String otp;
    private SingleTask singleTask;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOtpBinding inflate = ActivityOtpBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.singleTask = (SingleTask) getApplication();
        this.mobile = getIntent().getStringExtra("mobile");
        this.binding.mobile.setText(this.mobile);
        this.binding.otpVerify.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.OtpActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                OtpActivity otpActivity = OtpActivity.this;
                otpActivity.key = otpActivity.getIntent().getIntExtra("key", 0);
                if (OtpActivity.this.key == 1) {
                    OtpActivity.this.verifyOtp();
                } else if (OtpActivity.this.key == 3) {
                    OtpActivity.this.verifyOtp();
                } else {
                    OtpActivity.this.verifyOtp();
                }
            }
        });
        this.binding.goBack.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.OtpActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                OtpActivity.this.finish();
            }
        });
        this.binding.resendOtp.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.OtpActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ShowProgress.getShowProgress(OtpActivity.this).show();
                MyApi myApi = (MyApi) OtpActivity.this.singleTask.getRetrofit().create(MyApi.class);
                Call<JsonArray> call = myApi.resendOtp(OtpActivity.this.mobile);
                call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.OtpActivity.3.1
                    @Override // retrofit2.Callback
                    public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                String res = jsonObject1.getString("res");
                                String msg = jsonObject1.getString("message");
                                if (res.equals("success")) {
                                    ShowProgress.getShowProgress(OtpActivity.this).hide();
                                    Toast.makeText(OtpActivity.this, msg, Toast.LENGTH_LONG).show();
                                } else {
                                    ShowProgress.getShowProgress(OtpActivity.this).hide();
                                    Toast.makeText(OtpActivity.this, msg, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override // retrofit2.Callback
                    public void onFailure(Call<JsonArray> call2, Throwable t) {
                        ShowProgress.getShowProgress(OtpActivity.this).hide();
                        Toast.makeText(OtpActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void verifyOtp() {
        String obj = this.binding.otpView.getText().toString();
        this.otp = obj;
        if (obj.isEmpty()) {
            Toast.makeText(this, "Please Enter your otp", Toast.LENGTH_LONG).show();
        } else if (this.otp.length() < 6) {
            Toast.makeText(this, "Please Enter 6 digit otp", Toast.LENGTH_LONG).show();
        } else {
            ShowProgress.getShowProgress(this).show();
            MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
            Call<JsonArray> call = myApi.otpVerification(this.mobile, this.otp);
            call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.OtpActivity.4
                @Override // retrofit2.Callback
                public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            String res = jsonObject1.getString("res");
                            String msg = jsonObject1.getString("message");
                            if (!res.equals("success")) {
                                ShowProgress.getShowProgress(OtpActivity.this).hide();
                                Toast.makeText(OtpActivity.this, msg, Toast.LENGTH_LONG).show();
                            } else if (OtpActivity.this.key == 1) {
                                ShowProgress.getShowProgress(OtpActivity.this).hide();
                                Toast.makeText(OtpActivity.this, msg, Toast.LENGTH_LONG).show();
                                Intent in = new Intent(OtpActivity.this, ResetPasswordActivity.class);
                                in.putExtra("mobile", OtpActivity.this.mobile);
                                OtpActivity.this.startActivity(in);
                            } else if (OtpActivity.this.key == 3) {
                                ShowProgress.getShowProgress(OtpActivity.this).hide();
                                JSONArray jsonArray1 = jsonObject1.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                                JSONObject jsonObject2 = jsonArray1.getJSONObject(0);
                                OtpActivity.this.singleTask.addValue("boy", jsonObject2);
                                OtpActivity.this.startActivity(new Intent(OtpActivity.this, DashboardActivity.class));
                                OtpActivity.this.finish();
                                Toast.makeText(OtpActivity.this, msg, Toast.LENGTH_LONG).show();
                            } else {
                                ShowProgress.getShowProgress(OtpActivity.this).hide();
                                OtpActivity.this.startActivity(new Intent(OtpActivity.this, LoginActivity.class));
                                OtpActivity.this.finish();
                                Toast.makeText(OtpActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override // retrofit2.Callback
                public void onFailure(Call<JsonArray> call2, Throwable t) {
                    ShowProgress.getShowProgress(OtpActivity.this).hide();
                    Toast.makeText(OtpActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
