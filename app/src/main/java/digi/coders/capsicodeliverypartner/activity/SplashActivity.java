package digi.coders.capsicodeliverypartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.helper.InternetCheck;
import digi.coders.capsicodeliverypartner.model.DeliveryPartner;
import digi.coders.capsicodeliverypartner.singletask.SingleTask;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    private ImageView logo;
    private SingleTask singleTask;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = (ImageView) findViewById(R.id.logo);
        singleTask = (SingleTask) getApplication();
        logo.setAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom));
        new Handler().postDelayed(new Runnable() {
            @Override // java.lang.Runnable
            public void run() {
                if (new InternetCheck().isNetworkAvailable(SplashActivity.this.getApplicationContext())) {
                    String js = singleTask.getValue("boy");
                    DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(js,  DeliveryPartner.class);
                    if (deliveryPartner != null) {
                        SplashActivity.this.startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                        SplashActivity.this.finish();
                        return;
                    }
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    SplashActivity.this.finish();
                    return;
                }
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, NoInternetActivity.class));
                SplashActivity.this.finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
