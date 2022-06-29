package digi.coders.capsicodeliverypartner.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.messaging.Constants;
import digi.coders.capsicodeliverypartner.databinding.ActivityFullImageViewBinding;

/* loaded from: classes5.dex */
public class FullImageViewActivity extends AppCompatActivity {
    ActivityFullImageViewBinding binding;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFullImageViewBinding inflate = ActivityFullImageViewBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        String s = getIntent().getStringExtra(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
        Uri uri = Uri.parse(s);
        this.binding.image.setImageURI(uri);
        this.binding.close.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.FullImageViewActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                FullImageViewActivity.this.finish();
            }
        });
    }
}
