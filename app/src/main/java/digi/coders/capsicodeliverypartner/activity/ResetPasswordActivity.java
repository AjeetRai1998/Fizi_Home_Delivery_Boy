package digi.coders.capsicodeliverypartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.databinding.ActivityResetPasswordBinding;
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
public class ResetPasswordActivity extends AppCompatActivity {
    ActivityResetPasswordBinding binding;
    private String confirmPassword;
    private String mobile;
    private String newPassword;
    private SingleTask singleTask;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityResetPasswordBinding inflate = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.singleTask = (SingleTask) getApplication();
        this.mobile = getIntent().getStringExtra("mobile");
        this.binding.reset.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.ResetPasswordActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(final View v) {
                if (ResetPasswordActivity.this.valid()) {
                    ShowProgress.getShowProgress(ResetPasswordActivity.this).show();
                    MyApi myApi = (MyApi) ResetPasswordActivity.this.singleTask.getRetrofit().create(MyApi.class);
                    Call<JsonArray> call = myApi.resetPassword(ResetPasswordActivity.this.mobile, ResetPasswordActivity.this.newPassword);
                    call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.ResetPasswordActivity.1.1
                        @Override // retrofit2.Callback
                        public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                            if (response.isSuccessful()) {
                                try {
                                    JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                    String res = jsonObject1.getString("res");
                                    String msg = jsonObject1.getString("message");
                                    if (res.equals("success")) {
                                        ShowProgress.getShowProgress(ResetPasswordActivity.this).hide();
                                        ResetPasswordActivity.this.openBottomSheet(v);
                                    } else {
                                        ShowProgress.getShowProgress(ResetPasswordActivity.this).hide();
                                        Toast.makeText(ResetPasswordActivity.this, msg, 0).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override // retrofit2.Callback
                        public void onFailure(Call<JsonArray> call2, Throwable t) {
                            ShowProgress.getShowProgress(ResetPasswordActivity.this).hide();
                            Toast.makeText(ResetPasswordActivity.this, t.getMessage(), 0).show();
                        }
                    });
                }
            }
        });
        this.binding.back.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.ResetPasswordActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ResetPasswordActivity.this.finish();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean valid() {
        this.newPassword = this.binding.newpassword.getEditText().getText().toString();
        this.confirmPassword = this.binding.confirmpassword.getEditText().getText().toString();
        if (this.newPassword.isEmpty()) {
            this.binding.newpassword.setError("Please Enter password");
            this.binding.newpassword.requestFocus();
            return false;
        } else if (this.confirmPassword.isEmpty()) {
            this.binding.confirmpassword.setError("Please Enter Confirm password");
            this.binding.confirmpassword.requestFocus();
            return false;
        } else if (this.confirmPassword.equals(this.newPassword)) {
            return true;
        } else {
            this.binding.confirmpassword.setError("Password not match");
            this.binding.confirmpassword.requestFocus();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openBottomSheet(View v) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.myBottomSheetDialogTheme);
        View view1 = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, (ViewGroup) ((RelativeLayout) findViewById(R.id.bottom_sheet_container)), false);
        Button mybotton = (Button) view1.findViewById(R.id.ok);
        mybotton.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.ResetPasswordActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                bottomSheetDialog.dismiss();
                ResetPasswordActivity.this.startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                ResetPasswordActivity.this.finish();
            }
        });
        bottomSheetDialog.setContentView(view1);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.show();
    }
}
