package digi.coders.capsicodeliverypartner.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.L;
import com.google.firebase.messaging.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import digi.coders.capsicodeliverypartner.databinding.ActivityRegisterationBinding;
import digi.coders.capsicodeliverypartner.helper.City;
import digi.coders.capsicodeliverypartner.helper.FilePath;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.helper.ShowProgress;
import digi.coders.capsicodeliverypartner.singletask.SingleTask;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes5.dex */
public class RegisterationActivity extends AppCompatActivity {
    private MultipartBody.Part backPic;
    String bikeInsuranceExpiryDate;
    String bikeNo;
    String bikeOwnerMobileNo;
    String bikeOwnerName;
    String bikePlutionExpiryDate;
    String bikeRcExpiryDate;
    ActivityRegisterationBinding binding;
    String city;
    String cityId;
    private List<City> cityList;
    String dlExpiryDate;
    String drivingLicenseNo;
    String email;
    private MultipartBody.Part frontPic;
    String idTypeStr = "Choose ID Type";
    String mobile;
    String name;
    String password;
    private SingleTask singleTask;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterationBinding inflate = ActivityRegisterationBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.singleTask = (SingleTask) getApplication();
        loadCity();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new String[]{"Choose ID Type", "Bike", "Cycle"});
        this.binding.idType.setAdapter((SpinnerAdapter) arrayAdapter);
        this.binding.idType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: digi.coders.capsicodeliverypartner.activity.RegisterationActivity.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position <= 0) {
                    return;
                }
                if (position == 1) {
                    RegisterationActivity.this.idTypeStr = "Bike";
                    RegisterationActivity.this.binding.bikeDetails.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    RegisterationActivity.this.idTypeStr = "Cycle";
                    RegisterationActivity.this.binding.bikeDetails.setVisibility(View.GONE);
                }
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        this.binding.next.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.RegisterationActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (RegisterationActivity.this.valid()) {
                    ShowProgress.getShowProgress(RegisterationActivity.this).show();
                    RequestBody dName = RequestBody.create(MediaType.parse("text/plain"), RegisterationActivity.this.name);
                    RequestBody dEmail = RequestBody.create(MediaType.parse("text/plain"), RegisterationActivity.this.email);
                    RequestBody dMobile = RequestBody.create(MediaType.parse("text/plain"), RegisterationActivity.this.mobile);
                    RequestBody dPassword = RequestBody.create(MediaType.parse("text/plain"), RegisterationActivity.this.password);
                    RequestBody dBikeNo = RequestBody.create(MediaType.parse("text/plain"), RegisterationActivity.this.bikeNo);
                    RequestBody dRcExpiryDate = RequestBody.create(MediaType.parse("text/plain"), RegisterationActivity.this.bikeRcExpiryDate);
                    RequestBody dInsuranceExpiryDate = RequestBody.create(MediaType.parse("text/plain"), RegisterationActivity.this.bikeInsuranceExpiryDate);
                    RequestBody dPolutionExpiryDate = RequestBody.create(MediaType.parse("text/plain"), RegisterationActivity.this.bikePlutionExpiryDate);
                    Log.e("sdsd", RegisterationActivity.this.bikeOwnerName + "");
                    RequestBody dBikeOwnerName = RequestBody.create(MediaType.parse("text/plain"), RegisterationActivity.this.bikeOwnerName);
                    RequestBody dOwnerMobileNo = RequestBody.create(MediaType.parse("text/plain"), RegisterationActivity.this.bikeOwnerMobileNo);
                    RequestBody ddlExpiryDate = RequestBody.create(MediaType.parse("text/plain"), RegisterationActivity.this.dlExpiryDate);
                    RequestBody dlNo = RequestBody.create(MediaType.parse("text/plain"), RegisterationActivity.this.drivingLicenseNo);
                    RequestBody cityid = RequestBody.create(MediaType.parse("text/plain"), RegisterationActivity.this.cityId);
                    RequestBody idTye = RequestBody.create(MediaType.parse("text/plain"), RegisterationActivity.this.idTypeStr);
                    MyApi myApi = (MyApi) RegisterationActivity.this.singleTask.getRetrofit().create(MyApi.class);
                    Call<JsonArray> call = myApi.deliveryBoyRegisteration(dName, dEmail, dMobile, dPassword, dBikeNo, dRcExpiryDate, dInsuranceExpiryDate, dPolutionExpiryDate, dBikeOwnerName, dOwnerMobileNo, dlNo, ddlExpiryDate, cityid, idTye, RegisterationActivity.this.frontPic, RegisterationActivity.this.backPic);
                    call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.RegisterationActivity.2.1
                        @Override // retrofit2.Callback
                        public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                            if (response.isSuccessful()) {
                                Log.e("sdsd", response.body().toString());
                                try {
                                    JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String res = jsonObject.getString("res");
                                    String msg = jsonObject.getString("message");
                                    if (res.equals("success")) {
                                        ShowProgress.getShowProgress(RegisterationActivity.this).hide();
                                        Intent in = new Intent(RegisterationActivity.this, OtpActivity.class);
                                        in.putExtra("key", 2);
                                        in.putExtra("mobile", RegisterationActivity.this.mobile);
                                        RegisterationActivity.this.startActivity(in);
                                        RegisterationActivity.this.finish();
                                        Toast.makeText(RegisterationActivity.this, msg, Toast.LENGTH_LONG).show();
                                    } else {
                                        ShowProgress.getShowProgress(RegisterationActivity.this).hide();
                                        Toast.makeText(RegisterationActivity.this, msg, Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override // retrofit2.Callback
                        public void onFailure(Call<JsonArray> call2, Throwable t) {
                            Toast.makeText(RegisterationActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            ShowProgress.getShowProgress(RegisterationActivity.this).hide();
                        }
                    });
                }
            }
        });
        this.binding.goBack.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.RegisterationActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                RegisterationActivity.this.finish();
            }
        });
        this.binding.addFrontPhoto.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.RegisterationActivity.4
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                RegisterationActivity.this.openGallery(1);
            }
        });
        this.binding.addBackPhoto.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.RegisterationActivity.5
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                RegisterationActivity.this.openGallery(2);
            }
        });
    }

    private void loadCity() {
        Log.e("Sdsd", "dsd");
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.getCity();
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.RegisterationActivity.6
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.e("sdsd", response.body().toString());
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String res = jsonObject1.getString("res");
                        if (res.equals("success")) {
                            JSONArray jsonArray1 = jsonObject1.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            RegisterationActivity.this.cityList = new ArrayList();
                            Log.e("ds", RegisterationActivity.this.cityList.size() + "");
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                City city = (City) new Gson().fromJson(jsonArray1.getJSONObject(i).toString(), City.class);
                                RegisterationActivity.this.cityList.add(city);
                            }
                            Log.e("size", RegisterationActivity.this.cityList.size() + "");
                            List<String> s = new ArrayList<>();
                            s.add("Choose city");
                            for (int i2 = 0; i2 < RegisterationActivity.this.cityList.size(); i2++) {
                                s.add(((City) RegisterationActivity.this.cityList.get(i2)).getCity());
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(RegisterationActivity.this, android.R.layout.simple_spinner_dropdown_item, s);
                            RegisterationActivity.this.binding.chooseCity.setAdapter((SpinnerAdapter) arrayAdapter);
                            RegisterationActivity.this.binding.chooseCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: digi.coders.capsicodeliverypartner.activity.RegisterationActivity.6.1
                                @Override // android.widget.AdapterView.OnItemSelectedListener
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position > 0) {
                                        City city2 = (City) RegisterationActivity.this.cityList.get(position - 1);
                                        RegisterationActivity.this.cityId = city2.getId();
                                    }
                                }

                                @Override // android.widget.AdapterView.OnItemSelectedListener
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean valid() {
        this.name = this.binding.name.getEditText().getText().toString();
        this.email = this.binding.email.getEditText().getText().toString();
        this.mobile = this.binding.mobile.getEditText().getText().toString();
        this.password = this.binding.password.getEditText().getText().toString();
        this.bikeNo = this.binding.bikeno.getEditText().getText().toString();
        this.bikeRcExpiryDate = this.binding.bikeRcExpiryDate.getEditText().getText().toString();
        this.bikeInsuranceExpiryDate = this.binding.bikeInsuranceDate.getEditText().getText().toString();
        this.bikePlutionExpiryDate = this.binding.bikePolutionExpiryDate.getEditText().getText().toString();
        this.bikeOwnerName = this.binding.bikeOwnerName.getEditText().getText().toString();
        this.bikeOwnerMobileNo = this.binding.bikeMobileNo.getEditText().getText().toString();
        this.drivingLicenseNo = this.binding.drivingLicenseNo.getEditText().getText().toString();
        this.dlExpiryDate = this.binding.drivingLicenseExpiryDate.getEditText().getText().toString();
        this.city = this.binding.chooseCity.getSelectedItem().toString();
        if (this.name.isEmpty()) {
            this.binding.name.setError("Please Enter your name");
            this.binding.name.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(this.email)) {
            this.binding.email.setError("Please Enter your email");
            this.binding.email.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(this.mobile)) {
            this.binding.mobile.setError("Please Enter your mobile no");
            this.binding.mobile.requestFocus();
            return false;
        } else if (this.mobile.length() < 10) {
            this.binding.mobile.setError("Please Enter  10 digit  mobile no");
            this.binding.mobile.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(this.password)) {
            this.binding.password.setError("Please Enter Password");
            this.binding.password.requestFocus();
            return false;
        } else if (this.idTypeStr.equalsIgnoreCase("Choose ID Type")) {
            this.binding.email.setError("Please Select ID Type");
            this.binding.email.requestFocus();
            return false;
        } else if (this.city.equals("Choose City")) {
            Toast.makeText(this, "Please Choose city", Toast.LENGTH_LONG).show();
            return false;
        } else if (this.frontPic == null) {
            Toast.makeText(this, "Please Choose Front Photo", Toast.LENGTH_LONG).show();
            return false;
        } else if (this.backPic != null) {
            return true;
        } else {
            Toast.makeText(this, "Please Choose Back Photo", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void openGallery(int i) {
        if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 100);
            return;
        }
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), i);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != 100 || grantResults.length <= 0 || grantResults[0] != 0) {
            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 100);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && data != null) {
            switch (requestCode) {
                case 1:
                    this.binding.frontPhoto.setImageURI(data.getData());
                    FilePath.getPath(this, data.getData());
                    try {
                        File file = new File(FilePath.getPath(this, data.getData()));
                        RequestBody reqFile = RequestBody.create(MediaType.parse("*/*"), file);
                        this.frontPic = MultipartBody.Part.createFormData("licence_front_photo", file.getName(), reqFile);
                    } catch (Exception e) {
                    }
                    this.binding.frontPhoto.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.RegisterationActivity.7
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v) {
                            Intent in = new Intent(RegisterationActivity.this, FullImageViewActivity.class);
                            Uri uri = data.getData();
                            in.putExtra(Constants.ScionAnalytics.MessageType.DATA_MESSAGE, uri.toString());
                            RegisterationActivity.this.startActivity(in);
                        }
                    });
                    return;
                case 2:
                    this.binding.backPhoto.setImageURI(data.getData());
                    FilePath.getPath(this, data.getData());
                    try {
                        File file2 = new File(FilePath.getPath(this, data.getData()));
                        RequestBody reqFile2 = RequestBody.create(MediaType.parse("*/*"), file2);
                        this.backPic = MultipartBody.Part.createFormData("licence_back_photo", file2.getName(), reqFile2);
                    } catch (Exception e2) {
                    }
                    this.binding.backPhoto.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.RegisterationActivity.8
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v) {
                            Intent in = new Intent(RegisterationActivity.this, FullImageViewActivity.class);
                            Uri uri = data.getData();
                            in.putExtra(Constants.ScionAnalytics.MessageType.DATA_MESSAGE, uri.toString());
                            RegisterationActivity.this.startActivity(in);
                        }
                    });
                    return;
                default:
                    return;
            }
        }
    }
}
