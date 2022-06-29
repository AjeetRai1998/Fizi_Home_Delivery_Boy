package digi.coders.capsicodeliverypartner.helper;

import android.content.Context;
import android.util.Log;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/* loaded from: classes5.dex */
public class MyWorker extends Worker {
    private static final String TAG = "MyWorker";

    public MyWorker(Context appContext, WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @Override // androidx.work.Worker
    public ListenableWorker.Result doWork() {
        Log.d(TAG, "Performing long running task in scheduled job");
        return ListenableWorker.Result.success();
    }
}
