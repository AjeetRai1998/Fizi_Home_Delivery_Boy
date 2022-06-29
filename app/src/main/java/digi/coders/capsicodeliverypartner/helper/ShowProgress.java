package digi.coders.capsicodeliverypartner.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AlertDialog;
import digi.coders.capsicodeliverypartner.R;

/* loaded from: classes5.dex */
public class ShowProgress {
    private static Context ctx;
    private static ShowProgress showProgress = new ShowProgress();
    private AlertDialog.Builder alert;
    private AlertDialog alertDialog;

    private ShowProgress() {
    }

    public static ShowProgress getShowProgress(Context context) {
        ctx = context;
        return showProgress;
    }

    public void show() {
        this.alert = new AlertDialog.Builder(ctx);
        View view = LayoutInflater.from(ctx).inflate(R.layout.loader_layout, (ViewGroup) null);
        this.alert.setView(view);
        this.alertDialog = this.alert.create();
    }

    public void hide() {
    }
}
