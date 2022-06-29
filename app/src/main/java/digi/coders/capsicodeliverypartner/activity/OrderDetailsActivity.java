package digi.coders.capsicodeliverypartner.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.adapter.OrderDetailAdapter;
import digi.coders.capsicodeliverypartner.databinding.ActivityNewOrderDetailsBinding;
import digi.coders.capsicodeliverypartner.helper.Constraints;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.helper.SharedPrefManagerLocation;
import digi.coders.capsicodeliverypartner.helper.ShowProgress;
import digi.coders.capsicodeliverypartner.model.DeliveryPartner;
import digi.coders.capsicodeliverypartner.model.MyOrder;
import digi.coders.capsicodeliverypartner.model.User;
import digi.coders.capsicodeliverypartner.singletask.SingleTask;

import java.security.Permission;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes5.dex */
public class OrderDetailsActivity extends AppCompatActivity {
    public static MyOrder myOrder;
    ActivityNewOrderDetailsBinding binding;
    TextView order;
    private SingleTask singleTask;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNewOrderDetailsBinding inflate = ActivityNewOrderDetailsBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.singleTask = (SingleTask) getApplication();
        this.order = (TextView) findViewById(R.id.order_Detail);
        this.binding.back.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.OrderDetailsActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                OrderDetailsActivity.this.finish();
            }
        });
        this.order.setText(myOrder.getUser_detail()[0].getName());
        this.binding.navigateToMarchent.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.OrderDetailsActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String latitude = SharedPrefManagerLocation.getInstance(OrderDetailsActivity.this).locationModel(Constraints.LATITUDE);
                String longitude = SharedPrefManagerLocation.getInstance(OrderDetailsActivity.this).locationModel(Constraints.LONGITUDE);
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)", Double.valueOf(Double.parseDouble(latitude)), Double.valueOf(Double.parseDouble(longitude)), "Home Sweet Home", Double.valueOf(Double.parseDouble(OrderDetailsActivity.myOrder.getMerchant_detail()[0].getLatitude())), Double.valueOf(Double.parseDouble(OrderDetailsActivity.myOrder.getMerchant_detail()[0].getLongitude())), "Where the party is at", "&mode=d");
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                OrderDetailsActivity.this.startActivity(intent);
            }
        });
        this.binding.customarNavigation.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.OrderDetailsActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                String latitude = SharedPrefManagerLocation.getInstance(OrderDetailsActivity.this).locationModel(Constraints.LATITUDE);
                String longitude = SharedPrefManagerLocation.getInstance(OrderDetailsActivity.this).locationModel(Constraints.LONGITUDE);
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)", Double.valueOf(Double.parseDouble(latitude)), Double.valueOf(Double.parseDouble(longitude)), "Home Sweet Home", Double.valueOf(Double.parseDouble(OrderDetailsActivity.myOrder.getAddress()[0].getLatitude())), Double.valueOf(Double.parseDouble(OrderDetailsActivity.myOrder.getAddress()[0].getLongitude())), "Where the party is at", "&mode=d");
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                OrderDetailsActivity.this.startActivity(intent);
            }
        });
        this.binding.callToCustomer.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.OrderDetailsActivity.4
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (OrderDetailsActivity.this.checkSelfPermission("android.permission.CALL_PHONE") != PackageManager.PERMISSION_GRANTED) {
                    OrderDetailsActivity.this.requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 100);
                    return;
                }
                Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + OrderDetailsActivity.myOrder.getUser_detail()[0].getMobile()));
                OrderDetailsActivity.this.startActivity(intent);
            }
        });
        this.binding.callToMerchant.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.OrderDetailsActivity.5
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Toast.makeText(OrderDetailsActivity.this.getApplicationContext(), "hello" + OrderDetailsActivity.myOrder.getMerchant_detail()[0].getMobile(), Toast.LENGTH_LONG).show();
                if (checkSelfPermission("android.permission.CALL_PHONE") != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 100);
                    return;
                }
                Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + OrderDetailsActivity.myOrder.getMerchant_detail()[0].getMobile()));
                OrderDetailsActivity.this.startActivity(intent);
            }
        });
        setData();
    }

    private void orderStatus(final String status) {
        ShowProgress.getShowProgress(this).show();
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.orderStatus(deliveryPartner.getId(), myOrder.getOrderId(), status, "");
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.OrderDetailsActivity.6
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.e("ueyhr", "onResponse: " + response.body().toString());
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String res = jsonObject1.getString("res");
                        String message = jsonObject1.getString("message");
                        Log.e("sdsd", jsonObject1.toString());
                        ShowProgress.getShowProgress(OrderDetailsActivity.this).hide();
                        if (res.equals("success")) {
                            Toast.makeText(OrderDetailsActivity.this, "Order" + status, Toast.LENGTH_LONG).show();
                            Toast.makeText(OrderDetailsActivity.this, message, Toast.LENGTH_LONG).show();
                        } else {
                            ShowProgress.getShowProgress(OrderDetailsActivity.this).hide();
                            Toast.makeText(OrderDetailsActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                ShowProgress.getShowProgress(OrderDetailsActivity.this).hide();
                Toast.makeText(OrderDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setData() {
        MyOrder myOrder2 = myOrder;
        if (myOrder2 != null) {
            User user = myOrder2.getUser_detail()[0];
            this.binding.userNameOrderDetail.setText(user.getName());
            this.binding.userPhoneNoOrderDetail.setText(user.getMobile());
            this.binding.userEmailOrderDetail.setText(user.getEmail());
            this.binding.storeName.setText(myOrder.getMerchant_detail()[0].getName());
            this.binding.storeAddress.setText(myOrder.getMerchant_detail()[0].getAddress());
            this.binding.custmName.setText(myOrder.getUser_detail()[0].getName());
            this.binding.userAddrss.setText(myOrder.getAddress()[0].getAddress());
            this.binding.totalAmount.setText("₹" + myOrder.getAmount());
            this.binding.paymentmethod.setText(myOrder.getMethod());
            this.binding.subtotal.setText("₹ " + myOrder.getSubtotal());
            this.binding.coupon.setText("₹ " + myOrder.getCouponDiscount());
            this.binding.otherCharge.setText("₹ " + myOrder.getOtherCharge());
            this.binding.shipingCharge.setText("₹ " + myOrder.getShippinCharge());
            this.binding.deliveryTip.setText("₹ " + myOrder.getDeliveryTip());
            StringBuffer tt = new StringBuffer("");
            for (int i = 0; i < myOrder.getOrderproduct().length; i++) {
                tt.append(myOrder.getOrderproduct()[i].getQty() + " × " + myOrder.getOrderproduct()[i].getName() + "\n");
            }
            this.binding.item.setText(tt);
            StringBuffer ee = new StringBuffer("");
            for (int a2 = 0; a2 < myOrder.getOrderproduct().length; a2++) {
                ee.append("₹" + (Double.parseDouble(myOrder.getOrderproduct()[a2].getSellPrice()) * Double.parseDouble(myOrder.getOrderproduct()[a2].getQty())) + "\n");
            }
            this.binding.pricc.setText(ee);
        }
    }

    private void loadNewOrder(MyOrder myOrder2) {
        this.binding.newOrderItemList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        this.binding.newOrderItemList.setAdapter(new OrderDetailAdapter(myOrder2));
    }
}
