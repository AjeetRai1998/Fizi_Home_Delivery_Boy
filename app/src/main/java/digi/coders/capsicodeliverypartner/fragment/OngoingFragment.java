package digi.coders.capsicodeliverypartner.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.core.internal.view.SupportMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.skydoves.elasticviews.ElasticButton;

import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.adapter.OrderAdapter;
import digi.coders.capsicodeliverypartner.databinding.FragmentOngoingBinding;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.helper.Refresh;
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
public class OngoingFragment extends Fragment implements Refresh {
    public static Refresh refershLayout;
    FragmentOngoingBinding binding;
    FragmentManager fragmentManager;
    private List<MyOrder> myOrderList;
    private SingleTask singleTask;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentOngoingBinding inflate = FragmentOngoingBinding.inflate(inflater, container, false);
        this.binding = inflate;
        return inflate.getRoot();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.singleTask = (SingleTask) getActivity().getApplication();
        refershLayout = this;
//        showDialoge(getActivity());
        this.fragmentManager = getParentFragmentManager();
        this.binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: digi.coders.capsicodeliverypartner.fragment.OngoingFragment.1
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() { // from class: digi.coders.capsicodeliverypartner.fragment.OngoingFragment.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        OngoingFragment.this.loadOngoingFragment();
                        OngoingFragment.this.binding.refresh.setRefreshing(false);
                    }
                }, 2000L);
            }
        });
        loadOngoingFragment();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadOngoingFragment() {
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.getOrder(deliveryPartner.getId(), "Accepted","0");
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.fragment.OngoingFragment.2
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
                            OngoingFragment.this.binding.progressBar.setVisibility(View.GONE);
                            JSONArray jsonArray1 = jsonObject1.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            int count = jsonObject1.getInt("count");
                            HomeFragment.countPrepare.setText(count+"");
                            HomeFragment.countPrepare.setVisibility(View.VISIBLE);

                            OngoingFragment.this.myOrderList = new ArrayList();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                MyOrder myOrder = (MyOrder) new Gson().fromJson(jsonArray1.getJSONObject(i).toString(),  MyOrder.class);
                                OngoingFragment.this.myOrderList.add(myOrder);
                            }
                            OngoingFragment.this.binding.ongoingOrderList.setLayoutManager(new LinearLayoutManager(OngoingFragment.this.getActivity(), RecyclerView.VERTICAL, false));
                            OrderAdapter adapter = new OrderAdapter(2, OngoingFragment.this.myOrderList, OngoingFragment.this.singleTask, OngoingFragment.this.getActivity(), OngoingFragment.this.fragmentManager);
                            adapter.findPosition(new OrderAdapter.GetPosition() { // from class: digi.coders.capsicodeliverypartner.fragment.OngoingFragment.2.1
                                @Override // digi.coders.capsicodeliverypartner.adapter.OrderAdapter.GetPosition
                                public void click() {
                                    OngoingFragment.this.loadOngoingFragment();
                                }
                            });
                            OngoingFragment.this.binding.ongoingOrderList.setAdapter(adapter);
                            OngoingFragment.this.binding.progressBar.setVisibility(View.GONE);
                            OngoingFragment.this.binding.noTxt.setVisibility(View.GONE);
                            OngoingFragment.this.binding.ongoingOrderList.setVisibility(View.VISIBLE);
                            return;
                        }else{
                            HomeFragment.countPrepare.setVisibility(View.GONE);
                        }

                        OngoingFragment.this.binding.progressBar.setVisibility(View.GONE);
                        OngoingFragment.this.binding.ongoingOrderList.setVisibility(View.GONE);
                        OngoingFragment.this.binding.noTxt.setVisibility(View.VISIBLE);
                    } catch (JSONException e) {
                        HomeFragment.countPrepare.setVisibility(View.GONE);
                        OngoingFragment.this.binding.ongoingOrderList.setVisibility(View.GONE);
                        OngoingFragment.this.binding.noTxt.setVisibility(View.VISIBLE);
                        OngoingFragment.this.binding.progressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                OngoingFragment.this.binding.progressBar.setVisibility(View.GONE);
                OngoingFragment.this.binding.ongoingOrderList.setVisibility(View.GONE);
                OngoingFragment.this.binding.noTxt.setVisibility(View.VISIBLE);
                HomeFragment.countPrepare.setVisibility(View.GONE);
            }
        });
    }

    @Override // digi.coders.capsicodeliverypartner.helper.Refresh
    public void onRefresh() {
        loadOngoingFragment();
    }



}
