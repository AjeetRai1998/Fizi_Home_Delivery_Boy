package digi.coders.capsicodeliverypartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.messaging.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import digi.coders.capsicodeliverypartner.databinding.ActivityLoginBinding;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.helper.ProgressDisplay;
import digi.coders.capsicodeliverypartner.helper.ShowProgress;
import digi.coders.capsicodeliverypartner.singletask.SingleTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes5.dex */
public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private String mobile;
    private String password;
    ProgressDisplay progressDisplay;
    private SingleTask singleTask;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding inflate = ActivityLoginBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.singleTask = (SingleTask) getApplication();
        this.progressDisplay = new ProgressDisplay(this);
        this.binding.login.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.LoginActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (LoginActivity.this.valid()) {
                    LoginActivity.this.progressDisplay.showProgress();
                    MyApi myApi = (MyApi) LoginActivity.this.singleTask.getRetrofit().create(MyApi.class);
                    Call<JsonArray> call = myApi.login(LoginActivity.this.mobile, LoginActivity.this.password);
                    call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.LoginActivity.1.1
                        @Override // retrofit2.Callback
                        public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                            if (response.isSuccessful()) {
                                Log.e("sds", response.toString());
                                try {
                                    JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                    String res = jsonObject1.getString("res");
                                    String msg = jsonObject1.getString("message");
                                    if (res.equals("success")) {
                                        ShowProgress.getShowProgress(LoginActivity.this).hide();
                                        Toast.makeText(LoginActivity.this, msg, 0).show();
                                        Intent in = new Intent(LoginActivity.this, OtpActivity.class);
                                        in.putExtra("key", 3);
                                        in.putExtra("mobile", LoginActivity.this.mobile);
                                        LoginActivity.this.startActivity(in);
                                        LoginActivity.this.finish();
                                    } else {
                                        ShowProgress.getShowProgress(LoginActivity.this).hide();
                                        Toast.makeText(LoginActivity.this, msg, 0).show();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(LoginActivity.this, e.getMessage(), 0).show();
                                }
                            }
                            LoginActivity.this.progressDisplay.hideProgress();
                        }

                        @Override // retrofit2.Callback
                        public void onFailure(Call<JsonArray> call2, Throwable t) {
                            LoginActivity.this.progressDisplay.hideProgress();
                            Toast.makeText(LoginActivity.this, t.getMessage(), 0).show();
                            Log.e(Constants.IPC_BUNDLE_KEY_SEND_ERROR, t.getMessage());
                        }
                    });
                }
            }
        });
        this.binding.forgetPassword.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.LoginActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
            }
        });
        this.binding.createAccount.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.LoginActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, RegisterationActivity.class));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean valid() {
        this.mobile = this.binding.phone.getEditText().getText().toString();
        this.password = this.binding.vpassword.getEditText().getText().toString();
        if (TextUtils.isEmpty(this.mobile)) {
            this.binding.phone.setError("Please Enter mobile no");
            this.binding.phone.requestFocus();
            return false;
        } else if (this.mobile.length() < 10) {
            this.binding.phone.setError("Please Enter 10 digit mobile no");
            this.binding.phone.requestFocus();
            return false;
        } else if (!TextUtils.isEmpty(this.password)) {
            return true;
        } else {
            this.binding.vpassword.setError("Please Enter password");
            this.binding.vpassword.requestFocus();
            return false;
        }
    }
}
