package digi.coders.capsicodeliverypartner.helper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import digi.coders.capsicodeliverypartner.model.DeliveryPartner;
import digi.coders.capsicodeliverypartner.singletask.SingleTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes5.dex */
public class LocationTrace extends Service implements LocationListener {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 10000;
    private static final int TWO_MINUTES = 1000;
    private Context context;
    double latitude;
    protected LocationManager locationManager;
    double longitude;
    private SingleTask singleTask;
    Location location = null;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.context = this;
        get_current_location();
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        this.context = this;
        get_current_location();

    }

    @Override // android.location.LocationListener
    public void onLocationChanged(Location location) {
        if (location != null && location.getLatitude() != 0.0d && location.getLongitude() != 0.0d) {
            this.latitude = location.getLatitude();
            this.longitude = location.getLongitude();
            Log.d("locatoon", "Lat" + this.latitude + "long" + this.longitude);
            sendDriverLocation(location, "Online");
        }
    }

    @Override // android.location.LocationListener
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override // android.location.LocationListener
    public void onProviderEnabled(String provider) {
    }

    @Override // android.location.LocationListener
    public void onProviderDisabled(String provider) {
    }

    public Location get_current_location() {
        Location location;
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        this.locationManager = locationManager;
        this.isGPSEnabled = locationManager.isProviderEnabled("gps");
        boolean isProviderEnabled = this.locationManager.isProviderEnabled("network");
        this.isNetworkEnabled = isProviderEnabled;
        boolean z = this.isGPSEnabled;
        if (z || isProviderEnabled) {
            if (z && this.location == null) {
                if (checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") != PackageManager.PERMISSION_GRANTED) {
                    checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION");
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000L, 10.0f, this);
                LocationManager locationManager2 = this.locationManager;
                if (locationManager2 != null) {
                    Location lastKnownLocation = locationManager2.getLastKnownLocation("gps");
                    this.location = lastKnownLocation;
                    if (lastKnownLocation != null) {
                        this.latitude = lastKnownLocation.getLatitude();
                        this.longitude = this.location.getLongitude();
                    }
                }
            }
            if (this.isNetworkEnabled) {
                this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000L, 10.0f, this);
                if (!(this.locationManager == null || (location = this.location) == null)) {
                    this.latitude = location.getLatitude();
                    this.longitude = this.location.getLongitude();
                }
            }
        }
        return this.location;
    }

    public double getLatitude() {
        Location location = this.location;
        if (location != null) {
            this.latitude = location.getLatitude();
        }
        return this.latitude;
    }

    public double getLongitude() {
        Location location = this.location;
        if (location != null) {
            this.longitude = location.getLongitude();
        }
        return this.longitude;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onDestroy() {
        LocationManager locationManager = this.locationManager;
        if (locationManager != null) {
            locationManager.removeUpdates(this);
            sendDriverLocation(this.location, "Offline");
        }
        super.onDestroy();
    }

    @Override // android.app.Service
    public void onTaskRemoved(Intent rootIntent) {
        LocationManager locationManager = this.locationManager;
        if (locationManager != null) {
            locationManager.removeUpdates(this);
            sendDriverLocation(this.location, "Offline");
            Constraints loc = new Constraints("", String.valueOf(this.location.getLatitude()), String.valueOf(this.location.getLongitude()), "");
            SharedPrefManagerLocation.getInstance(this.context).userLocation(loc);
        }
        super.onTaskRemoved(rootIntent);
    }

    public void sendDriverLocation(final Location location, String status) {
        SingleTask singleTask = (SingleTask) getApplication();
        MyApi myApi = (MyApi) singleTask.getRetrofit().create(MyApi.class);
        String ven = singleTask.getValue("boy");
        Constraints loc = new Constraints("", String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), "");
        SharedPrefManagerLocation.getInstance(this.context).userLocation(loc);
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        if (deliveryPartner != null) {
            Call<JsonArray> call = myApi.currentLocation(deliveryPartner.getId(), String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
            call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.helper.LocationTrace.1
                @Override // retrofit2.Callback
                public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
//                    Toast.makeText(LocationTrace.this.getApplicationContext(), response.body().toString(), Toast.LENGTH_LONG).show();

                }

                @Override // retrofit2.Callback
                public void onFailure(Call<JsonArray> call2, Throwable t) {
                    Toast.makeText(LocationTrace.this.getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void sendMessageToActivity(String newData) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("ServiceToActivityAction");
        broadcastIntent.putExtra("ServiceToActivityKey", newData);
        sendBroadcast(broadcastIntent);
    }
}
