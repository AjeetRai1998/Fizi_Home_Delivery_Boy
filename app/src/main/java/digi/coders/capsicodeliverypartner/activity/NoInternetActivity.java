package digi.coders.capsicodeliverypartner.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.material.snackbar.Snackbar;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.databinding.ActivityNoInternetBinding;

/* loaded from: classes5.dex */
public class NoInternetActivity extends AppCompatActivity {
    ActivityNoInternetBinding binding;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNoInternetBinding inflate = ActivityNoInternetBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
    }

    public void retry(View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            final Snackbar snackbar = Snackbar.make(view, "No internet connection ", (int) CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE);
            if (Build.VERSION.SDK_INT >= 23) {
                snackbar.setActionTextColor(getColor(R.color.color_green));
            }
            snackbar.setAction("Ok", new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.NoInternetActivity.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
            return;
        }
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }
}
