package digi.coders.capsicodeliverypartner.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.airbnb.lottie.L;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.messaging.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.helper.FunctionClass;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.model.DeliveryPartner;
import digi.coders.capsicodeliverypartner.singletask.SingleTask;
import org.json.JSONArray;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes5.dex */
public class VaccinationActivity extends AppCompatActivity {
    ImageView first_image;
    String image1 = "";
    String image2 = "";
    int imageSTatus = 0;
    LinearLayout lineDetails;
    ProgressDialog pd;
    ImageView second_image;
    private SingleTask singleTask;
    Button submit;
    TextView txt_info;
    LinearLayout upload_first;
    LinearLayout upload_second;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);
        this.singleTask = (SingleTask) getApplication();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.VaccinationActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                VaccinationActivity.this.finish();
            }
        });
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.pd = progressDialog;
        progressDialog.setMessage("Loading..");
        this.upload_first = (LinearLayout) findViewById(R.id.upload_first);
        this.upload_second = (LinearLayout) findViewById(R.id.upload_second);
        this.first_image = (ImageView) findViewById(R.id.first_image);
        this.second_image = (ImageView) findViewById(R.id.second_image);
        this.txt_info = (TextView) findViewById(R.id.txt_info);
        this.lineDetails = (LinearLayout) findViewById(R.id.lineDetails);
        this.submit = (Button) findViewById(R.id.submit);
        this.upload_first.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.VaccinationActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ImagePicker.with(VaccinationActivity.this).crop().compress(1024).maxResultSize(1080, 1080).start();
                VaccinationActivity.this.imageSTatus = 0;
            }
        });
        this.upload_second.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.VaccinationActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ImagePicker.with(VaccinationActivity.this).crop().compress(1024).maxResultSize(1080, 1080).start();
                VaccinationActivity.this.imageSTatus = 1;
            }
        });
        this.first_image.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.VaccinationActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ImagePicker.with(VaccinationActivity.this).crop().compress(1024).maxResultSize(1080, 1080).start();
                VaccinationActivity.this.imageSTatus = 0;
            }
        });
        this.second_image.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.VaccinationActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ImagePicker.with(VaccinationActivity.this).crop().compress(1024).maxResultSize(1080, 1080).start();
                VaccinationActivity.this.imageSTatus = 1;
            }
        });
        this.submit.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.VaccinationActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (VaccinationActivity.this.image1.equalsIgnoreCase("")) {
                    Toast.makeText(VaccinationActivity.this.getApplicationContext(), "No Image Selected", Toast.LENGTH_LONG).show();
                } else if (!VaccinationActivity.this.image2.equalsIgnoreCase("")) {
                    VaccinationActivity.this.addData();
                } else {
                    Toast.makeText(VaccinationActivity.this.getApplicationContext(), "No Image Selected", Toast.LENGTH_LONG).show();
                }
            }
        });
        getDetails();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addData() {
        this.pd.show();
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven, DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.uploadVaccinationDetails(deliveryPartner.getId(), this.image1 + "CAPSICO" + this.image2);
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.VaccinationActivity.7
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String res = jsonObject.getString("res");
                        if (res.equals("success")) {
                            VaccinationActivity.this.getDetails();
                        } else {
                            Toast.makeText(VaccinationActivity.this.getApplicationContext(), "Uploading Failed", Toast.LENGTH_LONG);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                VaccinationActivity.this.pd.dismiss();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                VaccinationActivity.this.pd.dismiss();
                Toast.makeText(VaccinationActivity.this.getApplicationContext(), "Uploading Failed", Toast.LENGTH_LONG);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDetails() {
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven, DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.getVaccination(deliveryPartner.getId());
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.VaccinationActivity.GONE
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String res = jsonObject.getString("res");
                        if (res.equals("success")) {
                            JSONArray jsonArray1 = jsonObject.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                            VaccinationActivity.this.upload_first.setVisibility(View.GONE);
                            String[] images = jsonObject1.getString("image").split("CAPSICO");
                            VaccinationActivity.this.image1 = images[0];
                            VaccinationActivity.this.image2 = images[1];
                            byte[] decodedString = Base64.decode(images[0], 0);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            byte[] decodedString1 = Base64.decode(images[1], 0);
                            Bitmap decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);
                            VaccinationActivity.this.first_image.setImageBitmap(decodedByte);
                            VaccinationActivity.this.first_image.setVisibility(View.VISIBLE);
                            VaccinationActivity.this.upload_second.setVisibility(View.GONE);
                            VaccinationActivity.this.second_image.setImageBitmap(decodedByte1);
                            VaccinationActivity.this.second_image.setVisibility(View.VISIBLE);
                            if (jsonObject1.getString(NotificationCompat.CATEGORY_STATUS).equalsIgnoreCase("Pending")) {
                                VaccinationActivity.this.txt_info.setText("Vaccination Approval Pending");
                                VaccinationActivity.this.txt_info.setTextColor(Color.parseColor("#f7b614"));
                                VaccinationActivity.this.submit.setVisibility(View.GONE);
                            } else if (jsonObject1.getString(NotificationCompat.CATEGORY_STATUS).equalsIgnoreCase("Cancelled")) {
                                VaccinationActivity.this.txt_info.setText("Vaccination Approval Cancelled");
                                VaccinationActivity.this.txt_info.setTextColor(Color.parseColor("#DC2529"));
                                VaccinationActivity.this.submit.setVisibility(View.VISIBLE);
                            } else if (jsonObject1.getString(NotificationCompat.CATEGORY_STATUS).equalsIgnoreCase("Approved")) {
                                VaccinationActivity.this.txt_info.setText("Vaccination Approval Done");
                                VaccinationActivity.this.txt_info.setTextColor(Color.parseColor("#44AC4GONE"));
                                VaccinationActivity.this.submit.setVisibility(View.GONE);
                            }
                            VaccinationActivity.this.txt_info.setVisibility(View.VISIBLE);
                        } else {
                            VaccinationActivity.this.txt_info.setVisibility(View.GONE);
                            Toast.makeText(VaccinationActivity.this.getApplicationContext(), "Downloading Failed", Toast.LENGTH_LONG);
                        }
                    } catch (Exception e) {
                        Toast.makeText(VaccinationActivity.this.getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG);
                    }
                }
                VaccinationActivity.this.lineDetails.setVisibility(View.VISIBLE);
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                VaccinationActivity.this.txt_info.setVisibility(View.GONE);
                VaccinationActivity.this.lineDetails.setVisibility(View.VISIBLE);
                Toast.makeText(VaccinationActivity.this.getApplicationContext(), "Downloading Failed", Toast.LENGTH_LONG);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            try {
                Uri uri = data.getData();
                int i = this.imageSTatus;
                if (i == 0) {
                    this.first_image.setImageURI(uri);
                    this.upload_first.setVisibility(View.GONE);
                    this.first_image.setVisibility(View.VISIBLE);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    this.image1 = new FunctionClass().encodeImagetoString(bitmap);
                } else if (i == 1) {
                    this.second_image.setImageURI(uri);
                    this.upload_second.setVisibility(View.GONE);
                    this.second_image.setVisibility(View.VISIBLE);
                    Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    this.image2 = new FunctionClass().encodeImagetoString(bitmap2);
                }
            } catch (Exception e) {
            }
        } else if (resultCode == 64) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_LONG).show();
        }
    }
}
