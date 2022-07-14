package digi.coders.capsicodeliverypartner.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.firebase.messaging.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.adapter.OrderAdapter;
import digi.coders.capsicodeliverypartner.databinding.FragmentDeliveredBinding;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.helper.RefershLayout;
import digi.coders.capsicodeliverypartner.model.DeliveryPartner;
import digi.coders.capsicodeliverypartner.model.MyOrder;
import digi.coders.capsicodeliverypartner.singletask.SingleTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes5.dex */
public class ReturnDeliveredFragment extends Fragment implements RefershLayout {
    public static RefershLayout refershLayout;
    FragmentDeliveredBinding binding;
    FragmentManager fragmentManager;
    private List<MyOrder> myOrderList=new ArrayList<>();
    private SingleTask singleTask;
    int scrollStatus=0,scrollStatusForData=0;
    int page=0;
    OrderAdapter adapter;


    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentDeliveredBinding inflate = FragmentDeliveredBinding.inflate(inflater, container, false);
        this.binding = inflate;
        return inflate.getRoot();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.singleTask = (SingleTask) getActivity().getApplication();
        refershLayout = this;

        binding.deliveredOrderList.setLayoutManager(new LinearLayoutManager(ReturnDeliveredFragment.this.getActivity(), RecyclerView.VERTICAL, false));
         adapter = new OrderAdapter(3, ReturnDeliveredFragment.this.myOrderList, ReturnDeliveredFragment.this.singleTask, ReturnDeliveredFragment.this.getActivity(), ReturnDeliveredFragment.this.fragmentManager);
        Glide.with(getActivity()).load(R.raw.loading).into(binding.loading);


        this.fragmentManager = getParentFragmentManager();
        this.binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: digi.coders.capsicodeliverypartner.fragment.DeliveredFragment.1
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() { // from class: digi.coders.capsicodeliverypartner.fragment.DeliveredFragment.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        adapter.addClearData();
                        page = 0;
                        ReturnDeliveredFragment.this.loadDeliveredOrderList();
                        ReturnDeliveredFragment.this.binding.refresh.setRefreshing(false);
                    }
                }, 2000L);
            }
        });


        binding.mainLayout.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // on scroll change we are checking when users scroll as bottom.
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if(scrollStatusForData==0) {
                        if (scrollStatus == 0) {
                            scrollStatus = 1;
                            page = page + 50;
                            loadDeliveredOrderList();
                        }
                    }
                }
            }
        });


        loadDeliveredOrderList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadDeliveredOrderList() {
        binding.loading.setVisibility(View.VISIBLE);
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.getReturnOrder(deliveryPartner.getId(), "Delivered",page+"");
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.fragment.DeliveredFragment.2
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
                            ReturnDeliveredFragment.this.binding.progressBar.setVisibility(View.GONE);
                            JSONArray jsonArray1 = jsonObject1.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            int count = jsonObject1.getInt("count");

//                            HomeFragment.countComplete.setText(count+"");
//                            HomeFragment.countComplete.setVisibility(View.VISIBLE);
                            myOrderList=new ArrayList<>();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                MyOrder myOrder = (MyOrder) new Gson().fromJson(jsonArray1.getJSONObject(i).toString(),  MyOrder.class);
                                myOrderList.add(myOrder);
                            }
                            adapter.addData(myOrderList);
                            scrollStatus=0;
                            adapter.findPosition(new OrderAdapter.GetPosition() { // from class: digi.coders.capsicodeliverypartner.fragment.DeliveredFragment.2.1
                                @Override // digi.coders.capsicodeliverypartner.adapter.OrderAdapter.GetPosition
                                public void click() {
                                    ReturnDeliveredFragment.this.loadDeliveredOrderList();
                                }
                            });
                            ReturnDeliveredFragment.this.binding.deliveredOrderList.setAdapter(adapter);
                            ReturnDeliveredFragment.this.binding.noTxt.setVisibility(View.GONE);
                            return;
                        }else {
                            ReturnDeliveredFragment.this.binding.progressBar.setVisibility(View.GONE);
                            ReturnDeliveredFragment.this.binding.noTxt.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                binding.loading.setVisibility(View.GONE);
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                binding.loading.setVisibility(View.GONE);
                ReturnDeliveredFragment.this.binding.noTxt.setVisibility(View.VISIBLE);
                ReturnDeliveredFragment.this.binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override // digi.coders.capsicodeliverypartner.helper.RefershLayout
    public void referesh() {
        loadDeliveredOrderList();
    }
}
