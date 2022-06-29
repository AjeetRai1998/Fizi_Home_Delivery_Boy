package digi.coders.capsicodeliverypartner.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import digi.coders.capsicodeliverypartner.databinding.ActivityWebBinding;
import digi.coders.capsicodeliverypartner.helper.InternetCheck;

/* loaded from: classes5.dex */
public class WebActivity extends AppCompatActivity {
    ActivityWebBinding binding;
    private int key;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWebBinding inflate = ActivityWebBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.key = getIntent().getIntExtra("key", 0);
        this.binding.back.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.WebActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                WebActivity.this.finish();
            }
        });
        int i = this.key;
        if (i == 1) {
            this.binding.toolbarText.setText("Terms and Conditions");
            loadLink("https://yourcapsico.com/Home/TermsConditions");
        } else if (i == 2) {
            this.binding.toolbarText.setText("Privacy Policy");
            loadLink("https://yourcapsico.com/Home/PrivacyPolicies");
        } else {
            this.binding.toolbarText.setText("About");
            loadLink("https://yourcapsico.com/Home#about");
        }
    }

    private void loadLink(String link) {
        if (new InternetCheck().isNetworkAvailable(getApplicationContext())) {
            this.binding.webview.getSettings().setLoadsImagesAutomatically(true);
            this.binding.webview.getSettings().setJavaScriptEnabled(true);
//            this.binding.webview.setScrollBarStyle(View.SCROLL_AXIS_VERTICAL);
            this.binding.webview.getSettings().setAllowContentAccess(true);
            this.binding.webview.getSettings().setAllowFileAccess(true);
            this.binding.webview.getSettings().setAllowFileAccessFromFileURLs(true);
            this.binding.webview.getSettings().setAllowUniversalAccessFromFileURLs(true);
            this.binding.webview.getSettings().setAppCachePath(getApplicationContext().getApplicationContext().getCacheDir().getAbsolutePath());
//            this.binding.webview.getSettings().setCacheMode(CL);
            this.binding.webview.getSettings().setDatabaseEnabled(true);
            this.binding.webview.getSettings().setDomStorageEnabled(true);
            this.binding.webview.getSettings().setUseWideViewPort(true);
            this.binding.webview.getSettings().setLoadWithOverviewMode(true);
            this.binding.webview.getSettings().setPluginState(WebSettings.PluginState.ON);
            this.binding.webview.setWebViewClient(new WebViewClient() { // from class: digi.coders.capsicodeliverypartner.activity.WebActivity.2
                @Override // android.webkit.WebViewClient
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    WebActivity.this.binding.progressBar.setVisibility(View.VISIBLE);
                }

                @Override // android.webkit.WebViewClient
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    WebActivity.this.binding.progressBar.setVisibility(View.GONE);
                }

                @Override // android.webkit.WebViewClient
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }

                @Override // android.webkit.WebViewClient
                public boolean shouldOverrideUrlLoading(WebView view, String request) {
                    view.loadUrl(request);
                    WebActivity.this.binding.progressBar.setVisibility(View.GONE);
                    return super.shouldOverrideUrlLoading(view, request);
                }
            });
            this.binding.webview.loadUrl(link);
            return;
        }
        Toast.makeText(getApplicationContext(), "No Internet!", Toast.LENGTH_LONG).show();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.binding.webview.canGoBack()) {
            this.binding.progressBar.setVisibility(View.VISIBLE);
            this.binding.webview.goBack();
            return;
        }
        finish();
    }
}
