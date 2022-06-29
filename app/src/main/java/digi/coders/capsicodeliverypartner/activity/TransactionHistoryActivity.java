package digi.coders.capsicodeliverypartner.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import digi.coders.capsicodeliverypartner.databinding.ActivityTransactionHistoryBinding;

/* loaded from: classes5.dex */
public class TransactionHistoryActivity extends AppCompatActivity {
    ActivityTransactionHistoryBinding binding;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTransactionHistoryBinding inflate = ActivityTransactionHistoryBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
    }
}
