package digi.coders.capsicodeliverypartner.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.messaging.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.adapter.OrderHistoryAdapter;
import digi.coders.capsicodeliverypartner.databinding.ActivityOrderHistoryBinding;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.helper.ShowProgress;
import digi.coders.capsicodeliverypartner.model.DeliveryPartner;
import digi.coders.capsicodeliverypartner.model.MyOrder;
import digi.coders.capsicodeliverypartner.singletask.SingleTask;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes5.dex */
public class OrderHistoryActivity extends AppCompatActivity {
    ActivityOrderHistoryBinding binding;
    private List<MyOrder> myOrderList=new ArrayList<>();
    private SingleTask singleTask;
    OrderHistoryAdapter orderHistoryAdapter;
    int scrollStatus=0,scrollStatusForData=0;
    int page=0;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOrderHistoryBinding inflate = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.singleTask = (SingleTask) getApplication();
        Glide.with(this).load(R.raw.loading).into(binding.loading);
        this.binding.back.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.OrderHistoryActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                OrderHistoryActivity.this.finish();
            }
        });

        binding.orderHistoryList.setLayoutManager(new LinearLayoutManager(OrderHistoryActivity.this,LinearLayoutManager.VERTICAL,false));
        orderHistoryAdapter=new OrderHistoryAdapter(myOrderList);
        binding.orderHistoryList.setAdapter(orderHistoryAdapter);
        binding.mainLayout.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // on scroll change we are checking when users scroll as bottom.
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if(scrollStatusForData==0) {
                        if (scrollStatus == 0) {
                            scrollStatus = 1;
                            page = page + 50;
                            loadOrderHistoryList();
                        }
                    }
                }
            }
        });
        loadOrderHistoryList();
    }

    private void loadOrderHistoryList() {
        binding.loading.setVisibility(View.VISIBLE);
        ShowProgress.getShowProgress(this).show();
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven, DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.getOrder(deliveryPartner.getId(), "Delivered",page+"");
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.OrderHistoryActivity.2
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String res = jsonObject1.getString("res");
                        jsonObject1.getString("message");
                        Log.e("sdsd", jsonObject1.toString());
                        if (res.equals("success")) {
                            ShowProgress.getShowProgress(OrderHistoryActivity.this).hide();
                            JSONArray jsonArray1 = jsonObject1.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            OrderHistoryActivity.this.myOrderList = new ArrayList();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                MyOrder myOrder = (MyOrder) new Gson().fromJson(jsonArray1.getJSONObject(i).toString(),  MyOrder.class);
                                OrderHistoryActivity.this.myOrderList.add(myOrder);
                            }
                            orderHistoryAdapter.addData(myOrderList);
                            scrollStatus=0;
                            return;
                        }
                        ShowProgress.getShowProgress(OrderHistoryActivity.this).hide();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                binding.loading.setVisibility(View.GONE);
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                binding.loading.setVisibility(View.GONE);
                ShowProgress.getShowProgress(OrderHistoryActivity.this).hide();
                OrderHistoryActivity.this.binding.noTxt.setVisibility(View.VISIBLE);
            }
        });
    }
}
