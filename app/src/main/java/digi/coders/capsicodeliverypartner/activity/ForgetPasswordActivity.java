package digi.coders.capsicodeliverypartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.messaging.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import digi.coders.capsicodeliverypartner.databinding.ActivityForgetPasswordBinding;
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
public class ForgetPasswordActivity extends AppCompatActivity {
    ActivityForgetPasswordBinding binding;
    private SingleTask singleTask;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityForgetPasswordBinding inflate = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.singleTask = (SingleTask) getApplication();
        this.binding.next.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.ForgetPasswordActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                final String mobile = ForgetPasswordActivity.this.binding.phone.getEditText().getText().toString();
                if (mobile.isEmpty()) {
                    ForgetPasswordActivity.this.binding.phone.setError("Please Enter Mobile no");
                    ForgetPasswordActivity.this.binding.phone.requestFocus();
                } else if (mobile.length() < 10) {
                    ForgetPasswordActivity.this.binding.phone.setError("Please Enter 10 digit Mobile no");
                    ForgetPasswordActivity.this.binding.phone.requestFocus();
                } else {
                    ShowProgress.getShowProgress(ForgetPasswordActivity.this).show();
                    MyApi myApi = (MyApi) ForgetPasswordActivity.this.singleTask.getRetrofit().create(MyApi.class);
                    Call<JsonArray> call = myApi.forgetPassword(mobile);
                    call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.ForgetPasswordActivity.1.1
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
                                        ShowProgress.getShowProgress(ForgetPasswordActivity.this).hide();
                                        Toast.makeText(ForgetPasswordActivity.this, msg, 0).show();
                                        Intent in = new Intent(ForgetPasswordActivity.this, OtpActivity.class);
                                        in.putExtra("mobile", mobile);
                                        in.putExtra("key", 1);
                                        ForgetPasswordActivity.this.startActivity(in);
                                    } else {
                                        ShowProgress.getShowProgress(ForgetPasswordActivity.this).hide();
                                        Toast.makeText(ForgetPasswordActivity.this, msg, 0).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override // retrofit2.Callback
                        public void onFailure(Call<JsonArray> call2, Throwable t) {
                            ShowProgress.getShowProgress(ForgetPasswordActivity.this).hide();
                            Toast.makeText(ForgetPasswordActivity.this, t.getMessage(), 0).show();
                            Log.e(Constants.IPC_BUNDLE_KEY_SEND_ERROR, t.getMessage());
                        }
                    });
                }
            }
        });
        this.binding.goBack.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.ForgetPasswordActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ForgetPasswordActivity.this.finish();
            }
        });
    }
}
