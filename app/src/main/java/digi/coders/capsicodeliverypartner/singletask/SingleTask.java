package digi.coders.capsicodeliverypartner.singletask;

import android.app.Application;
import android.content.SharedPreferences;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.json.JSONObject;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/* loaded from: classes7.dex */
public class SingleTask extends Application {
    public static final String BASE_URL = "https://hungerji.com/DeliveryBoyTest/";
    public static final String BASE_URL1 = "https://hungerji.com/";
    private Retrofit retrofit;
    private Retrofit retrofit1;

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
        }
        if (retrofit1 == null) {
            retrofit1 = new Retrofit.Builder().baseUrl("https://hungerji.com/").client(client).addConverterFactory(GsonConverterFactory.create()).build();
        }
    }

    public Retrofit getRetrofit() {
        return this.retrofit;
    }

    public Retrofit getRetrofit1() {
        return this.retrofit1;
    }

    public SharedPreferences getSharedPreferene() {
        return getSharedPreferences("capsico_pref", 0);
    }

    public void addValue(String key, JSONObject jsonObject) {
        getSharedPreferene().edit().putString(key, String.valueOf(jsonObject)).commit();
    }

    public void removeUser(String key) {
        getSharedPreferene().edit().remove(key).commit();
    }

    public String getValue(String key) {
        return getSharedPreferene().getString(key, null);
    }
}
