package digi.coders.capsicodeliverypartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import digi.coders.capsicodeliverypartner.adapter.OrderDetailAdapter;
import digi.coders.capsicodeliverypartner.databinding.ActivityDeliveredOrderBinding;

/* loaded from: classes5.dex */
public class DeliveredOrderActivity extends AppCompatActivity {
    ActivityDeliveredOrderBinding binding;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDeliveredOrderBinding inflate = ActivityDeliveredOrderBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.binding.back.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.DeliveredOrderActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DeliveredOrderActivity.this.finish();
            }
        });
        this.binding.viewOrder.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.DeliveredOrderActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DeliveredOrderActivity.this.startActivity(new Intent(DeliveredOrderActivity.this, OrderSummaryActivity.class));
            }
        });
        loadItemList();
    }

    private void loadItemList() {
        this.binding.itemList.setLayoutManager(new LinearLayoutManager(this, 1, false));
        this.binding.itemList.setAdapter(new OrderDetailAdapter());
    }
}
