package digi.coders.capsicodeliverypartner.helper;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes5.dex */
public class SharedPrefManagerLocation {
    private static final String FETAURE = "feature";
    private static final String LATITUDE = "latitude";
    private static final String LOCATION = "location";
    private static final String LONGITUDE = "longitude";
    private static final String SHARED_PREF_NAMEE = "capsicodeliveryloc";
    private static Context mCtx;
    private static SharedPrefManagerLocation mInstance;

    private SharedPrefManagerLocation(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManagerLocation getInstance(Context context) {
        SharedPrefManagerLocation sharedPrefManagerLocation;
        synchronized (SharedPrefManagerLocation.class) {
            if (mInstance == null) {
                mInstance = new SharedPrefManagerLocation(context);
            }
            sharedPrefManagerLocation = mInstance;
        }
        return sharedPrefManagerLocation;
    }

    public void userLocation(Constraints loc) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAMEE, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("location", loc.getLoc());
        editor.putString("feature", loc.getFeature());
        editor.putString("latitude", loc.getLatitude());
        editor.putString("longitude", loc.getLongitude());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAMEE, 0);
        return sharedPreferences.getString("location", null) != null;
    }

    public String locationModel(String key) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAMEE, 0);
        return sharedPreferences.getString(key, null);
    }

    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAMEE, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
