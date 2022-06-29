package digi.coders.capsicodeliverypartner.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import digi.coders.capsicodeliverypartner.databinding.ActivityOrderStatusBinding;
import digi.coders.capsicodeliverypartner.helper.Constraints;
import digi.coders.capsicodeliverypartner.helper.SharedPrefManagerLocation;
import digi.coders.capsicodeliverypartner.model.MyOrder;
import digi.coders.capsicodeliverypartner.model.User;
import digi.coders.capsicodeliverypartner.singletask.SingleTask;
import java.util.Locale;

/* loaded from: classes5.dex */
public class OrderStatusActivity extends AppCompatActivity {
    public static MyOrder myorder;
    ActivityOrderStatusBinding binding;
    private SingleTask singleTask;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOrderStatusBinding inflate = ActivityOrderStatusBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.singleTask = (SingleTask) getApplication();
        setData();
        this.binding.back.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.OrderStatusActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                OrderStatusActivity.this.finish();
            }
        });
        Log.e("sdsd", myorder.getAmount() + "");
        User user = myorder.getUser_detail()[0];
        this.binding.userName.setText(user.getName());
        this.binding.toolbarOrderStatus.setText(user.getName());
        this.binding.userPhoneNo.setText(user.getMobile());
        this.binding.userEmail.setText(user.getEmail());
        this.binding.storNmame.setText(myorder.getMerchant_detail()[0].getName());
        this.binding.storeAddress.setText(myorder.getMerchant_detail()[0].getAddress());
        this.binding.customerrrNmae.setText(myorder.getUser_detail()[0].getName());
        this.binding.userAddrss.setText(myorder.getAddress()[0].getAddress());
        this.binding.totalAmount.setText("₹" + myorder.getAmount());
        this.binding.callToCustomer.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.OrderStatusActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (OrderStatusActivity.this.checkSelfPermission("android.permission.CALL_PHONE") != 0) {
                    OrderStatusActivity.this.requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 100);
                    return;
                }
                Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + OrderStatusActivity.myorder.getUser_detail()[0].getMobile()));
                OrderStatusActivity.this.startActivity(intent);
            }
        });
        this.binding.callToAlt.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.OrderStatusActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (OrderStatusActivity.this.checkSelfPermission("android.permission.CALL_PHONE") != 0) {
                    OrderStatusActivity.this.requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 100);
                    return;
                }
                Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + OrderStatusActivity.myorder.getAlt_number()));
                OrderStatusActivity.this.startActivity(intent);
            }
        });
        this.binding.locateCustomer.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.OrderStatusActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String latitude = SharedPrefManagerLocation.getInstance(OrderStatusActivity.this).locationModel(Constraints.LATITUDE);
                String longitude = SharedPrefManagerLocation.getInstance(OrderStatusActivity.this).locationModel(Constraints.LONGITUDE);
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)", Double.valueOf(Double.parseDouble(latitude)), Double.valueOf(Double.parseDouble(longitude)), "Home Sweet Home", Double.valueOf(Double.parseDouble(OrderStatusActivity.myorder.getAddress()[0].getLatitude())), Double.valueOf(Double.parseDouble(OrderStatusActivity.myorder.getAddress()[0].getLongitude())), "Where the party is at", "&mode=d");
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                OrderStatusActivity.this.startActivity(intent);
            }
        });
        this.binding.callToMerchant.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.OrderStatusActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (OrderStatusActivity.this.checkSelfPermission("android.permission.CALL_PHONE") != 0) {
                    OrderStatusActivity.this.requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 100);
                    return;
                }
                Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + OrderStatusActivity.myorder.getMerchant_detail()[0].getMobile()));
                OrderStatusActivity.this.startActivity(intent);
            }
        });
        this.binding.locateToMerchant.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.OrderStatusActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String latitude = SharedPrefManagerLocation.getInstance(OrderStatusActivity.this).locationModel(Constraints.LATITUDE);
                String longitude = SharedPrefManagerLocation.getInstance(OrderStatusActivity.this).locationModel(Constraints.LONGITUDE);
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f(%s)&daddr=%f,%f (%s)", Double.valueOf(Double.parseDouble(latitude)), Double.valueOf(Double.parseDouble(longitude)), "Home Sweet Home", Double.valueOf(Double.parseDouble(OrderStatusActivity.myorder.getMerchant_detail()[0].getLatitude())), Double.valueOf(Double.parseDouble(OrderStatusActivity.myorder.getMerchant_detail()[0].getLongitude())), "Where the party is at", "&mode=d");
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                OrderStatusActivity.this.startActivity(intent);
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != 100 || grantResults.length <= 0 || grantResults[0] != 0) {
            requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 100);
        }
    }

    private void setData() {
    }
}
