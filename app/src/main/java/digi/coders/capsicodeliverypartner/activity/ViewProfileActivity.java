package digi.coders.capsicodeliverypartner.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.messaging.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import digi.coders.capsicodeliverypartner.databinding.ActivityViewProfileBinding;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.helper.ShowProgress;
import digi.coders.capsicodeliverypartner.model.DeliveryPartner;
import digi.coders.capsicodeliverypartner.singletask.SingleTask;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes5.dex */
public class ViewProfileActivity extends AppCompatActivity {
    String address;
    ActivityViewProfileBinding binding;
    String description;
    String email;
    private String image;
    String name;
    private SingleTask singleTask;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityViewProfileBinding inflate = ActivityViewProfileBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.singleTask = (SingleTask) getApplication();
        this.binding.back.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.ViewProfileActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ViewProfileActivity.this.finish();
            }
        });
        setData();
        this.binding.updateProfile.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.ViewProfileActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (ViewProfileActivity.this.valid()) {
                    String js = ViewProfileActivity.this.singleTask.getValue("boy");
                    DeliveryPartner partner = (DeliveryPartner) new Gson().fromJson(js,  DeliveryPartner.class);
                    ShowProgress.getShowProgress(ViewProfileActivity.this).show();
                    MyApi myApi = (MyApi) ViewProfileActivity.this.singleTask.getRetrofit().create(MyApi.class);
                    Call<JsonArray> call = myApi.profileUpdate(partner.getId(), ViewProfileActivity.this.name, ViewProfileActivity.this.email, ViewProfileActivity.this.address, ViewProfileActivity.this.description);
                    call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.ViewProfileActivity.2.1
                        @Override // retrofit2.Callback
                        public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                            if (response.isSuccessful()) {
                                try {
                                    JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String res = jsonObject.getString("res");
                                    String msg = jsonObject.getString("message");
                                    if (res.equals("success")) {
                                        ShowProgress.getShowProgress(ViewProfileActivity.this).hide();
                                        Toast.makeText(ViewProfileActivity.this, msg, Toast.LENGTH_LONG).show();
                                        JSONArray jsonArray1 = jsonObject.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                                        JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                                        ViewProfileActivity.this.singleTask.addValue("boy", jsonObject1);
                                    } else {
                                        ShowProgress.getShowProgress(ViewProfileActivity.this).hide();
                                        Toast.makeText(ViewProfileActivity.this, msg, Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override // retrofit2.Callback
                        public void onFailure(Call<JsonArray> call2, Throwable t) {
                            ShowProgress.getShowProgress(ViewProfileActivity.this).hide();
                            Toast.makeText(ViewProfileActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        this.binding.addPhoto.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.ViewProfileActivity.3
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (ViewProfileActivity.this.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
                    ViewProfileActivity.this.requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 100);
                    return;
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction("android.intent.action.GET_CONTENT");
                ViewProfileActivity.this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
        this.binding.photoUpdate.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.ViewProfileActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (!ViewProfileActivity.this.image.isEmpty()) {
                    String js = ViewProfileActivity.this.singleTask.getValue("boy");
                    DeliveryPartner partner = (DeliveryPartner) new Gson().fromJson(js,  DeliveryPartner.class);
                    ShowProgress.getShowProgress(ViewProfileActivity.this).show();
                    MyApi myApi = (MyApi) ViewProfileActivity.this.singleTask.getRetrofit().create(MyApi.class);
                    Call<JsonArray> call = myApi.photoUpdate(partner.getId(), ViewProfileActivity.this.image);
                    call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.ViewProfileActivity.4.1
                        @Override // retrofit2.Callback
                        public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                            if (response.isSuccessful()) {
                                try {
                                    JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String res = jsonObject.getString("res");
                                    String msg = jsonObject.getString("message");
                                    if (res.equals("success")) {
                                        ShowProgress.getShowProgress(ViewProfileActivity.this).hide();
                                        Toast.makeText(ViewProfileActivity.this, msg, Toast.LENGTH_LONG).show();
                                    } else {
                                        ShowProgress.getShowProgress(ViewProfileActivity.this).hide();
                                        Toast.makeText(ViewProfileActivity.this, msg, Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override // retrofit2.Callback
                        public void onFailure(Call<JsonArray> call2, Throwable t) {
                            ShowProgress.getShowProgress(ViewProfileActivity.this).hide();
                            Toast.makeText(ViewProfileActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private void setData() {
        String js = this.singleTask.getValue("boy");
        DeliveryPartner partner = (DeliveryPartner) new Gson().fromJson(js,  DeliveryPartner.class);
        this.binding.name.getEditText().setText(partner.getName());
        this.binding.mobile.getEditText().setText(partner.getMobile());
        this.binding.emailId.getEditText().setText(partner.getEmail());
        this.binding.address.getEditText().setText(partner.getAddress());
        this.binding.description.getEditText().setText(partner.getDescription());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean valid() {
        this.name = this.binding.name.getEditText().getText().toString();
        this.email = this.binding.emailId.getEditText().getText().toString();
        this.address = this.binding.address.getEditText().getText().toString();
        this.description = this.binding.description.getEditText().getText().toString();
        if (TextUtils.isEmpty(this.name)) {
            this.binding.name.setError("Please Enter your name");
            this.binding.name.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(this.email)) {
            this.binding.emailId.setError("Please Enter your email");
            this.binding.emailId.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(this.address)) {
            this.binding.address.setError("Please Enter Addresss");
            this.binding.address.requestFocus();
            return false;
        } else if (!TextUtils.isEmpty(this.description)) {
            return true;
        } else {
            this.binding.description.setError("Please Enter Description");
            this.binding.description.requestFocus();
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == 0) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.GET_CONTENT");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            return;
        }
        requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 100);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1 && data != null) {
            this.binding.userPhoto.setImageURI(data.getData());
            try {
                InputStream iStream = getContentResolver().openInputStream(data.getData());
                Bitmap bit = BitmapFactory.decodeStream(iStream);
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                bit.compress(Bitmap.CompressFormat.JPEG, 50, b);
                byte[] bb = b.toByteArray();
                if (Build.VERSION.SDK_INT >= 26) {
                    this.image = Base64.getEncoder().encodeToString(bb);
                    this.binding.photoUpdate.setVisibility(View.VISIBLE);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
