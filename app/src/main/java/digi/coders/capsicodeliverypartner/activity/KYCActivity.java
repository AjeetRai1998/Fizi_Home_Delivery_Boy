package digi.coders.capsicodeliverypartner.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import digi.coders.capsicodeliverypartner.databinding.ActivityKycactivityBinding;
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
public class KYCActivity extends AppCompatActivity {
    private String aadahrNo;
    private String acHolderName;
    private String bankAccoutnNo;
    private String bankName;
    ActivityKycactivityBinding binding;
    private String branch;
    private String ifscCode;
    private String panNo;
    private SingleTask singleTask;
    private String aadharFront = "";
    private String aadharBack = "";
    private String panPhoto = "";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityKycactivityBinding inflate = ActivityKycactivityBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.singleTask = (SingleTask) getApplication();
        this.binding.next.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.KYCActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (KYCActivity.this.valid()) {
                    ShowProgress.getShowProgress(KYCActivity.this).show();
                    String ven = KYCActivity.this.singleTask.getValue("boy");
                    DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
                    MyApi myApi = (MyApi) KYCActivity.this.singleTask.getRetrofit().create(MyApi.class);
                    Call<JsonArray> call = myApi.kyc(deliveryPartner.getId(), KYCActivity.this.bankName, KYCActivity.this.ifscCode, KYCActivity.this.panNo, KYCActivity.this.aadahrNo, KYCActivity.this.bankAccoutnNo, KYCActivity.this.acHolderName, KYCActivity.this.branch, KYCActivity.this.panPhoto, KYCActivity.this.binding.vpa.getEditText().getText().toString(), KYCActivity.this.aadharFront);
                    call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.KYCActivity.1.1
                        @Override // retrofit2.Callback
                        public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                            if (response.isSuccessful()) {
                                try {
                                    JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String res = jsonObject.getString("res");
                                    String msg = jsonObject.getString("message");
                                    if (res.equals("success")) {
                                        ShowProgress.getShowProgress(KYCActivity.this).hide();
                                        Toast.makeText(KYCActivity.this, msg,  Toast.LENGTH_LONG).show();
                                        KYCActivity.this.startActivity(new Intent(KYCActivity.this, KYCStatusActivity.class));
                                        KYCActivity.this.finish();
                                    } else {
                                        ShowProgress.getShowProgress(KYCActivity.this).hide();
                                        Toast.makeText(KYCActivity.this, msg,  Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override // retrofit2.Callback
                        public void onFailure(Call<JsonArray> call2, Throwable t) {
                            ShowProgress.getShowProgress(KYCActivity.this).hide();
                            Toast.makeText(KYCActivity.this, t.getMessage(),  Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        this.binding.goBack.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.KYCActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                KYCActivity.this.finish();
            }
        });
        this.binding.addAadharFrontPhoto.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.KYCActivity.3
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                gallery(1);
            }
        });
        this.binding.addAdharBackPhoto.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.KYCActivity.4
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                KYCActivity.this.gallery(2);
            }
        });
        this.binding.addFrotPan.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.KYCActivity.5
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                KYCActivity.this.gallery(4);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void gallery(int i) {
        if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 100);
        } else {
            openGallery(i);
        }
    }

    private void openGallery(int i) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && data != null) {
            handleGalleryPic(requestCode, data);
        }
    }

    private void handleGalleryPic(int i, final Intent data) {
        switch (i) {
            case 1:
                this.binding.aadharFrontPhoto.setImageURI(data.getData());
                try {
                    InputStream iStream = getContentResolver().openInputStream(data.getData());
                    Bitmap bit = BitmapFactory.decodeStream(iStream);
                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    bit.compress(Bitmap.CompressFormat.JPEG, 50, b);
                    byte[] bb = b.toByteArray();
                    if (Build.VERSION.SDK_INT >= 26) {
                        this.aadharFront = Base64.getEncoder().encodeToString(bb);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                this.binding.aadharFrontPhoto.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.KYCActivity.6
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        Intent in = new Intent(KYCActivity.this, FullImageViewActivity.class);
                        Uri uri = data.getData();
                        in.putExtra(Constants.ScionAnalytics.MessageType.DATA_MESSAGE, uri.toString());
                        KYCActivity.this.startActivity(in);
                    }
                });
                return;
            case 2:
                this.binding.adharBackPhoto.setImageURI(data.getData());
                try {
                    InputStream iStream2 = getContentResolver().openInputStream(data.getData());
                    Bitmap bit2 = BitmapFactory.decodeStream(iStream2);
                    ByteArrayOutputStream b2 = new ByteArrayOutputStream();
                    bit2.compress(Bitmap.CompressFormat.JPEG, 50, b2);
                    byte[] bb2 = b2.toByteArray();
                    if (Build.VERSION.SDK_INT >= 26) {
                        this.aadharBack = Base64.getEncoder().encodeToString(bb2);
                    }
                } catch (FileNotFoundException e2) {
                    e2.printStackTrace();
                }
                this.binding.adharBackPhoto.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.KYCActivity.7
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        Intent in = new Intent(KYCActivity.this, FullImageViewActivity.class);
                        Uri uri = data.getData();
                        in.putExtra(Constants.ScionAnalytics.MessageType.DATA_MESSAGE, uri.toString());
                        KYCActivity.this.startActivity(in);
                    }
                });
                return;
            case 3:
            default:
                return;
            case 4:
                this.binding.frontPan.setImageURI(data.getData());
                try {
                    InputStream iStream3 = getContentResolver().openInputStream(data.getData());
                    Bitmap bit3 = BitmapFactory.decodeStream(iStream3);
                    ByteArrayOutputStream b3 = new ByteArrayOutputStream();
                    bit3.compress(Bitmap.CompressFormat.JPEG, 50, b3);
                    byte[] bb3 = b3.toByteArray();
                    if (Build.VERSION.SDK_INT >= 26) {
                        this.panPhoto = Base64.getEncoder().encodeToString(bb3);
                    }
                } catch (FileNotFoundException e3) {
                    e3.printStackTrace();
                }
                this.binding.frontPan.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.KYCActivity.8
                    @Override // android.view.View.OnClickListener
                    public void onClick(View v) {
                        Intent in = new Intent(KYCActivity.this, FullImageViewActivity.class);
                        Uri uri = data.getData();
                        in.putExtra(Constants.ScionAnalytics.MessageType.DATA_MESSAGE, uri.toString());
                        KYCActivity.this.startActivity(in);
                    }
                });
                return;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != 100 || grantResults.length <= 0 || grantResults[0] != 0) {
            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 100);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean valid() {
        this.bankName = this.binding.bankName.getEditText().getText().toString();
        this.bankAccoutnNo = this.binding.bankAccountNo.getEditText().getText().toString();
        this.acHolderName = this.binding.accountHolderName.getEditText().getText().toString();
        this.ifscCode = this.binding.ifscCode.getEditText().getText().toString();
        this.aadahrNo = this.binding.aadharNo.getEditText().getText().toString();
        this.panNo = this.binding.panNo.getEditText().getText().toString();
        this.branch = this.binding.bankBranch.getEditText().getText().toString();
        if (TextUtils.isEmpty(this.bankName)) {
            this.binding.bankName.setError("Please Enter Bank Name");
            this.binding.bankName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(this.bankAccoutnNo)) {
            this.binding.bankAccountNo.setError("Please Enter Bank Account no");
            this.binding.bankAccountNo.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(this.acHolderName)) {
            this.binding.accountHolderName.setError("Please Enter Account Holder Name");
            this.binding.accountHolderName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(this.branch)) {
            this.binding.bankBranch.setError("Please Enter Branch Name");
            this.binding.bankBranch.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(this.ifscCode)) {
            this.binding.ifscCode.setError("Please Enter Ifsc Code");
            this.binding.ifscCode.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(this.aadahrNo)) {
            this.binding.aadharNo.setError("Please Enter aadhar No");
            this.binding.aadharNo.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(this.panNo)) {
            this.binding.panNo.setError("Please Enter Pan No");
            this.binding.panNo.requestFocus();
            return false;
        } else if (this.aadharFront.equals("")) {
            Toast.makeText(this, "Please Upload  Front Photo of Aadhar",  Toast.LENGTH_LONG).show();
            return false;
        } else if (this.aadharBack.equals("")) {
            Toast.makeText(this, "Please Upload  Back Photo of Aadhar",  Toast.LENGTH_LONG).show();
            return false;
        } else if (!this.panPhoto.equals("")) {
            return true;
        } else {
            Toast.makeText(this, "Please Upload  Pan Photo ",  Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
