package digi.coders.capsicodeliverypartner.activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import digi.coders.capsicodeliverypartner.databinding.ActivityDocumentBinding;

/* loaded from: classes5.dex */
public class DocumentActivity extends AppCompatActivity {
    ActivityDocumentBinding binding;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDocumentBinding inflate = ActivityDocumentBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.binding.back.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.DocumentActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DocumentActivity.this.finish();
            }
        });
    }
}
