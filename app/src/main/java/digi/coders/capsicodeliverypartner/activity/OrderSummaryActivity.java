package digi.coders.capsicodeliverypartner.activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import digi.coders.capsicodeliverypartner.adapter.OrderDetailAdapter;
import digi.coders.capsicodeliverypartner.databinding.ActivityOrderSummaryBinding;
import digi.coders.capsicodeliverypartner.model.MyOrder;
import digi.coders.capsicodeliverypartner.model.User;

/* loaded from: classes5.dex */
public class OrderSummaryActivity extends AppCompatActivity {
    public static MyOrder myOrder;
    ActivityOrderSummaryBinding binding;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOrderSummaryBinding inflate = ActivityOrderSummaryBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.binding.back.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.OrderSummaryActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                OrderSummaryActivity.this.finish();
            }
        });
        setData();
    }

    private void setData() {
        MyOrder myOrder2 = myOrder;
        if (myOrder2 != null) {
            User user = myOrder2.getUser_detail()[0];
            this.binding.userName.setText(user.getName());
            this.binding.userPhoneNo.setText(user.getMobile());
            this.binding.userEmail.setText(user.getEmail());
            this.binding.storeAddress.setText(myOrder.getMerchant_detail()[0].getAddress());
            this.binding.userAddrss.setText(myOrder.getAddress()[0].getAddress());
            this.binding.totalAmount.setText("₹" + myOrder.getAmount());
            StringBuffer tt = new StringBuffer("");
            for (int i = 0; i < myOrder.getOrderproduct().length; i++) {
                tt.append(myOrder.getOrderproduct()[i].getQty() + " × " + myOrder.getOrderproduct()[i].getName() + "\n");
            }
            this.binding.item.setText(tt);
        }
        loadItemList(myOrder);
    }

    private void loadItemList(MyOrder myOrder2) {
        this.binding.itemList.setLayoutManager(new LinearLayoutManager(this, 1, false));
        this.binding.itemList.setAdapter(new OrderDetailAdapter(myOrder2));
    }
}
