package digi.coders.capsicodeliverypartner.fragment;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.messaging.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.skydoves.elasticviews.ElasticButton;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.adapter.DriversAdapter;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.helper.NotificationUtils;
import digi.coders.capsicodeliverypartner.helper.Refresh;
import digi.coders.capsicodeliverypartner.model.DeliveryPartner;
import digi.coders.capsicodeliverypartner.singletask.SingleTask;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes5.dex */
public class DriversFragment extends BottomSheetDialogFragment implements Refresh {
    public static String selectedDriver = "";
    ArrayList<DeliveryPartner> arrayList = new ArrayList<>();
    CountDownTimer countDownTimer;
    LinearLayout data_found;
    ElasticButton done;
    RecyclerView drivers_list;
    String lat;
    String longi;
    String orderid;
    int pos;
    private SingleTask singleTask;

    public DriversFragment(String lat, String longi, String orderid, int pos, CountDownTimer countDownTimer) {
        this.lat = "0";
        this.longi = "0";
        this.orderid = "";
        this.lat = lat;
        this.longi = longi;
        this.orderid = orderid;
        this.pos = pos;
        this.countDownTimer = countDownTimer;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.drivers_layout, container, false);
        this.drivers_list = (RecyclerView) v.findViewById(R.id.drivers_list);
        this.data_found = (LinearLayout) v.findViewById(R.id.data_found);
        this.done = (ElasticButton) v.findViewById(R.id.done);
        selectedDriver = "";
        this.singleTask = (SingleTask) getActivity().getApplicationContext();
        this.done.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.fragment.DriversFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                if (!DriversFragment.selectedDriver.equalsIgnoreCase("")) {
                    DriversFragment.this.assignDriver();
                } else {
                    Toast.makeText(DriversFragment.this.getActivity(), "Please Select Driver !", Toast.LENGTH_LONG).show();
                }
            }
        });
        getDrivers();
        return v;
    }

    @Override // digi.coders.capsicodeliverypartner.helper.Refresh
    public void onRefresh() {
    }

    public void getDrivers() {
        this.arrayList.clear();
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.getDeliveryBoys(this.orderid, deliveryPartner.getId());
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.fragment.DriversFragment.2
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                try {
                    JSONArray jsonArray1 = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                    JSONObject jsonObject = jsonArray1.getJSONObject(0);
                    String status = jsonObject.getString("res");
                    if (status.equalsIgnoreCase("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            DeliveryPartner deliveryPartner2 = (DeliveryPartner) new Gson().fromJson(jsonObject1.getJSONObject("delivery_boy_id").toString(),  DeliveryPartner.class);
                            deliveryPartner2.setPassword(jsonObject1.getString("distance"));
                            DriversFragment.this.arrayList.add(deliveryPartner2);
                        }
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DriversFragment.this.getActivity());
                        DriversFragment.this.drivers_list.setLayoutManager(layoutManager);
                        DriversFragment.this.drivers_list.setHasFixedSize(true);
                        DriversAdapter driversAdapter = new DriversAdapter(DriversFragment.this.arrayList, DriversFragment.this.getActivity(), DriversFragment.this);
                        DriversFragment.this.drivers_list.setAdapter(driversAdapter);
                        DriversFragment.this.data_found.setVisibility(View.GONE);
                        DriversFragment.this.done.setVisibility(View.VISIBLE);
                        return;
                    }
                    Toast.makeText(DriversFragment.this.getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    DriversFragment.this.data_found.setVisibility(View.VISIBLE);
                    DriversFragment.this.done.setVisibility(View.GONE);
                } catch (Exception e) {
                    Toast.makeText(DriversFragment.this.getActivity(), e.getMessage().toString().toString(), Toast.LENGTH_LONG).show();
                    DriversFragment.this.data_found.setVisibility(View.VISIBLE);
                    DriversFragment.this.done.setVisibility(View.GONE);
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                DriversFragment.this.data_found.setVisibility(View.VISIBLE);
                DriversFragment.this.done.setVisibility(View.GONE);
                Toast.makeText(DriversFragment.this.getActivity(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void assignDriver() {
        final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "Assigning...");
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.assignDeliveryBoy(this.orderid, selectedDriver);
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.fragment.DriversFragment.3
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                try {
                    JSONArray jsonArray1 = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                    JSONObject jsonObject = jsonArray1.getJSONObject(0);
                    String status = jsonObject.getString("res");
                    if (status.equalsIgnoreCase("success")) {
                        if (NewOrderFragment.refresh != null) {
                            NewOrderFragment.refresh.onRefresh();
                        }
                        if (DriversFragment.this.countDownTimer != null) {
                            DriversFragment.this.countDownTimer.cancel();
                        }
                        if (NotificationUtils.mMediaPlayer != null) {
                            NotificationUtils.mMediaPlayer.stop();
                        }
                        DriversFragment.this.dismiss();
                    } else {
                        Toast.makeText(DriversFragment.this.getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(DriversFragment.this.getActivity(), e.getMessage().toString().toString(), Toast.LENGTH_LONG).show();
                }
                pd.dismiss();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                pd.dismiss();
            }
        });
    }

    public float getKmFromLatLong(float lat1, float lng1, float lat2, float lng2) {
        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lng1);
        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lng2);
        float distanceInMeters = loc1.distanceTo(loc2);
        return distanceInMeters / 1000.0f;
    }
}
