package digi.coders.capsicodeliverypartner.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.work.WorkRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.skydoves.elasticviews.ElasticButton;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.databinding.ActivityDashboardBinding;
import digi.coders.capsicodeliverypartner.fragment.AccountFragment;
import digi.coders.capsicodeliverypartner.fragment.HomeFragment;
import digi.coders.capsicodeliverypartner.fragment.NewOrderFragment;
import digi.coders.capsicodeliverypartner.fragment.OfflineFragment;
import digi.coders.capsicodeliverypartner.fragment.OngoingFragment;
import digi.coders.capsicodeliverypartner.helper.FunctionClass;
import digi.coders.capsicodeliverypartner.helper.LocationTrace;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.helper.NotificationUtils;
import digi.coders.capsicodeliverypartner.helper.ShowProgress;
import digi.coders.capsicodeliverypartner.model.DeliveryPartner;
import digi.coders.capsicodeliverypartner.model.MyOrder;
import digi.coders.capsicodeliverypartner.singletask.SingleTask;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes5.dex */
public class DashboardActivity extends AppCompatActivity {
    public static Activity Dashboard;
    public static int newOrderSTatus = 0;
    ActivityDashboardBinding binding;
    FragmentManager fm;
    private SingleTask singleTask;
    Fragment fragment = null;
    public int counter = 0;
    boolean isStart = true;
    String[] permission = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() { // from class: digi.coders.capsicodeliverypartner.activity.DashboardActivity.9
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            intent.getStringExtra("order_id");
            intent.getStringExtra("id");
            String status1 = intent.getStringExtra(NotificationCompat.CATEGORY_STATUS);
            if (status1.equalsIgnoreCase("Prepared")) {
                if (OngoingFragment.refershLayout != null) {
                    OngoingFragment.refershLayout.onRefresh();
                }
            } else if (DashboardActivity.this.status == 0) {
                DashboardActivity.this.status = 1;
                DashboardActivity.this.getLastOrder();
            }
        }
    };
    int status = 0;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDashboardBinding inflate = ActivityDashboardBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.singleTask = (SingleTask) getApplication();
        Dashboard = this;
        this.fm = getSupportFragmentManager();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() { // from class: digi.coders.capsicodeliverypartner.activity.DashboardActivity.1
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public void onComplete(Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w("sd", "Fetching FCM registration token failed", task.getException());
                    return;
                }
                Log.e("sdsd", "firbase");
                String token = task.getResult();
                Log.e("sds", "ge" + token);
                Log.d("sads", token + "");
                sendNotification(token);
            }
        });
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.mMessageReceiver, new IntentFilter("GPSLocationUpdates"));
        showSomething();
        this.binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() { // from class: digi.coders.capsicodeliverypartner.activity.DashboardActivity.2
            @Override // com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.account /* 2131361846 */:
                        DashboardActivity.this.fragment = new AccountFragment();
                        break;
                    case R.id.home /* 2131362100 */:
                        DashboardActivity.newOrderSTatus = 0;
                        DashboardActivity.this.fragment = new OfflineFragment();
                        break;
                    case R.id.wallet /* 2131362539 */:
                        DashboardActivity.this.fragment = new HomeFragment();
                        DashboardActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.container, DashboardActivity.this.fragment, "h").commit();
                        break;
                }
                DashboardActivity dashboardActivity = DashboardActivity.this;
                dashboardActivity.exchangeFragment(dashboardActivity.fragment);
                return true;
            }
        });
        this.binding.chat.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.DashboardActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (DashboardActivity.this.requestPermission()) {
                    try {
                        Intent callIntent = new Intent("android.intent.action.CALL");
                        callIntent.setData(Uri.parse("tel:8188943693"));
                        DashboardActivity.this.startActivity(callIntent);
                    } catch (ActivityNotFoundException activityException) {
                        Toast.makeText(DashboardActivity.this.getApplicationContext(), "not working call", Toast.LENGTH_LONG).show();
                        Log.e("Calling a Phone Number", "Call failed", activityException);
                    }
                }
            }
        });
        loadMyProfile();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean requestPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.CALL_PHONE");
        if (result == 0) {
            return true;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.CALL_PHONE"}, 101);
        return false;
    }

    private void sendCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            Intent in = new Intent(this, LocationTrace.class);
            startService(in);
            return;
        }
        ActivityCompat.requestPermissions(this, this.permission, 500);
    }

    private void sendLocation(double latitude, double longitude) {
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        SingleTask singleTask = (SingleTask) getApplication();
        MyApi myApi = (MyApi) singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.currentLocation(deliveryPartner.getId(), String.valueOf(latitude), String.valueOf(longitude));
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.DashboardActivity.4
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                try {
                    JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String res = jsonObject.getString("res");
                    String message = jsonObject.getString("message");
                    if (res.equals("success")) {
                        Toast.makeText(DashboardActivity.this, message, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(DashboardActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                Toast.makeText(DashboardActivity.this.getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showSomething() {
        new Timer().schedule(new TimerTask() { // from class: digi.coders.capsicodeliverypartner.activity.DashboardActivity.5
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                DashboardActivity.this.runOnUiThread(new Runnable() { // from class: digi.coders.capsicodeliverypartner.activity.DashboardActivity.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (ActivityCompat.checkSelfPermission(DashboardActivity.this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(DashboardActivity.this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
                            Intent in = new Intent(DashboardActivity.this, LocationTrace.class);
                            startService(in);
                            return;
                        }
                        ActivityCompat.requestPermissions(DashboardActivity.this, DashboardActivity.this.permission, 500);
                    }
                });
            }
        }, 0L, WorkRequest.MIN_BACKOFF_MILLIS);
    }

    private void startSevice() {
    }

    public void exchangeFragment(Fragment fragment) {
        if (fragment != null) {
            this.fm.beginTransaction().replace(R.id.container, fragment).commit();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendNotification(String token) {
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonObject> call = myApi.sentToken(deliveryPartner.getId(), token);
        call.enqueue(new Callback<JsonObject>() {
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonObject> call2, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.e("sdsd", response.toString());
                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson((JsonElement) response.body()));
//                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String res = jsonObject.getString("res");
                        String msg = jsonObject.getString("message");
                        if (res.equals("success")) {
                            Log.e("sdsd", msg);
                        }
                        Log.e("dcdfd", res);
//                        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
//                        Toast.makeText(getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonObject> call2, Throwable t) {
//                Toast.makeText(getApplicationContext(),t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadMyProfile() {
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner vendor = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        ShowProgress.getShowProgress(this).show();
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.profile(vendor.getId());
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.DashboardActivity.7
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String res = jsonObject.getString("res");
                        String msg = jsonObject.getString("message");
                        if (res.equals("success")) {
                            ShowProgress.getShowProgress(DashboardActivity.this).hide();
                            JSONArray jsonArray1 = jsonObject.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(jsonArray1.getJSONObject(0).toString(),  DeliveryPartner.class);
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                            DashboardActivity.this.singleTask.addValue("boy", jsonObject1);
                            DashboardActivity.newOrderSTatus = 0;
                            DashboardActivity.this.exchangeFragment(new OfflineFragment());
                        } else {
                            ShowProgress.getShowProgress(DashboardActivity.this).hide();
                            Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                ShowProgress.getShowProgress(DashboardActivity.this).hide();
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateStatus(String status) {
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        ShowProgress.getShowProgress(this).show();
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.updateStatus(deliveryPartner.getId(), status);
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.DashboardActivity.8
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String res = jsonObject.getString("res");
                        String msg = jsonObject.getString("message");
                        ShowProgress.getShowProgress(DashboardActivity.this).hide();
                        if (res.equals("success")) {
                            Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                ShowProgress.getShowProgress(DashboardActivity.this).hide();
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("w");
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mMessageReceiver, new IntentFilter("GPSLocationUpdates"));
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    public void showAlert(MyOrder myOrder, Context ctx) {
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        View promptView = layoutInflater.inflate(R.layout.order_alert_layout, (ViewGroup) null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
        alertDialogBuilder.setView(promptView);
        final AlertDialog alert2 = alertDialogBuilder.create();
        alert2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        ElasticButton ok = (ElasticButton) promptView.findViewById(R.id.ok);
        TextView time = (TextView) promptView.findViewById(R.id.time);
        TextView productPrice = (TextView) promptView.findViewById(R.id.productPrice);
        TextView name = (TextView) promptView.findViewById(R.id.name);
        TextView address = (TextView) promptView.findViewById(R.id.address);
        time.setText(myOrder.getCreatedAt());
        name.setText(myOrder.getMerchant_detail()[0].getName());
        address.setText(myOrder.getMerchant_detail()[0].getAddress());

        if(!myOrder.getCustomer_location().equalsIgnoreCase("")) {
            double kms = FunctionClass.getKmFromLatLong(Double.parseDouble(myOrder.getMerchant_detail()[0].getLatitude()), Double.parseDouble(myOrder.getMerchant_detail()[0].getLongitude()),
                    Double.parseDouble(myOrder.getDriver_location().split(",")[0]), Double.parseDouble(myOrder.getDriver_location().split(",")[1]));
            kms = kms + FunctionClass.getKmFromLatLong(Double.parseDouble(myOrder.getMerchant_detail()[0].getLatitude()), Double.parseDouble(myOrder.getMerchant_detail()[0].getLongitude()),
                    Double.parseDouble(myOrder.getCustomer_location().split(",")[0]), Double.parseDouble(myOrder.getCustomer_location().split(",")[1]));

            if(kms<=Integer.parseInt(HomeFragment.baseKm)) {
                productPrice.setText("\u20b9"+HomeFragment.baseAmount);
            }else{
                productPrice.setText("\u20b9"+new DecimalFormat("##.##").format(kms*Double.parseDouble(HomeFragment.delCharge)));
            }
//            myHolder.binding1.distance.setText(new DecimalFormat("##.##").format(kms)+" Km");
        }else{
//            myHolder.binding1.distance.setText("0 Km");
            productPrice.setText("\u20b90");
        }
        ok.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.DashboardActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DashboardActivity.this.status = 0;
                alert2.dismiss();
                if (NewOrderFragment.refresh != null) {
                    NewOrderFragment.refresh.onRefresh();
                }
                if (NotificationUtils.mMediaPlayer != null) {
                    NotificationUtils.mMediaPlayer.stop();
                }
                DashboardActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
            }
        });
        alert2.setCanceledOnTouchOutside(false);
        alert2.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getLastOrder() {
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.getOrder(deliveryPartner.getId(), "NewOrder","0");
        Log.e("uhggbj", "loadNewOrderList: " + deliveryPartner.getId());
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.DashboardActivity.11
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String res = jsonObject1.getString("res");
                        jsonObject1.getString("message");
                        if (res.equals("success")) {
                            JSONArray jsonArray1 = jsonObject1.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            MyOrder myOrder = (MyOrder) new Gson().fromJson(jsonArray1.getJSONObject(0).toString(),  MyOrder.class);
                            if (myOrder.getOrderStatus().equalsIgnoreCase("Preparing")) {
                                DashboardActivity dashboardActivity = DashboardActivity.this;
                                dashboardActivity.showAlert(myOrder, dashboardActivity);
                            } else if (OngoingFragment.refershLayout != null) {
                                OngoingFragment.refershLayout.onRefresh();
                            }
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

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }
}
