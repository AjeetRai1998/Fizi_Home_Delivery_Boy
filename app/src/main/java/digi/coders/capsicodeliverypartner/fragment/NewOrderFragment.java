package digi.coders.capsicodeliverypartner.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import digi.coders.capsicodeliverypartner.adapter.OrderAdapter;
import digi.coders.capsicodeliverypartner.databinding.FragmentNewOrderBinding;
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
public class NewOrderFragment extends Fragment implements Refresh {
    public static Refresh refresh;
    FragmentNewOrderBinding binding;
    CountDownTimer countDownTimer;
    FragmentManager fragmentManager;
    private List<MyOrder> myOrderList;
    private SingleTask singleTask;
    int len = 0;
    public int counter = 0;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentNewOrderBinding inflate = FragmentNewOrderBinding.inflate(inflater, container, false);
        this.binding = inflate;
        return inflate.getRoot();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.singleTask = (SingleTask) getActivity().getApplication();
        this.fragmentManager = getParentFragmentManager();
        refresh = this;
        this.binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: digi.coders.capsicodeliverypartner.fragment.NewOrderFragment.1
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() { // from class: digi.coders.capsicodeliverypartner.fragment.NewOrderFragment.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        NewOrderFragment.this.loadNewOrderList();
                        NewOrderFragment.this.binding.refresh.setRefreshing(false);
                    }
                }, 2000L);
            }
        });
        loadNewOrderList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadNewOrderList() {
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.getOrder(deliveryPartner.getId(), "NewOrder","0");
        Log.e("uhggbj", "loadNewOrderList: " + deliveryPartner.getId());
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.fragment.NewOrderFragment.2
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
                            NewOrderFragment.this.binding.progressBar.setVisibility(View.GONE);
                            JSONArray jsonArray1 = jsonObject1.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            int count = jsonObject1.getInt("count");
                            if(jsonArray1.length()>0) {
                                HomeFragment.countNewOrder.setText(jsonArray1.length() + "");
                                HomeFragment.countNewOrder.setVisibility(View.VISIBLE);
                            }else{
                                HomeFragment.countNewOrder.setText(jsonArray1.length() + "");
                                HomeFragment.countNewOrder.setVisibility(View.GONE);
                            }
                            if (NewOrderFragment.this.len < jsonArray1.length()) {

                            }
                            NewOrderFragment.this.len = jsonArray1.length();
                            NewOrderFragment.this.myOrderList = new ArrayList();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                MyOrder myOrder = (MyOrder) new Gson().fromJson(jsonArray1.getJSONObject(i).toString(),  MyOrder.class);
                                NewOrderFragment.this.myOrderList.add(myOrder);
                            }
                            NewOrderFragment.this.binding.newOrderList.setLayoutManager(new LinearLayoutManager(NewOrderFragment.this.getActivity(), RecyclerView.VERTICAL, false));
                            OrderAdapter adapter = new OrderAdapter(1, NewOrderFragment.this.myOrderList, NewOrderFragment.this.singleTask, NewOrderFragment.this.getActivity(), NewOrderFragment.this.fragmentManager);
                            adapter.findPosition(new OrderAdapter.GetPosition() { // from class: digi.coders.capsicodeliverypartner.fragment.NewOrderFragment.2.1
                                @Override // digi.coders.capsicodeliverypartner.adapter.OrderAdapter.GetPosition
                                public void click() {
                                    NewOrderFragment.this.loadNewOrderList();
                                }
                            });
                            NewOrderFragment.this.binding.newOrderList.setAdapter(adapter);
                            NewOrderFragment.this.binding.noTxt.setVisibility(View.GONE);
                            NewOrderFragment.this.binding.lottieAnimation.setVisibility(View.GONE);
                            NewOrderFragment.this.binding.newOrderList.setVisibility(View.VISIBLE);
                            NewOrderFragment.this.getTimer();
                            if(jsonArray1.length()==0){
                                if (OfflineFragment.ActiveSTatus.equalsIgnoreCase("online")) {
                                    NewOrderFragment.this.binding.noTxt.setText("Waiting For New Order");
                                    NewOrderFragment.this.binding.lottieAnimation.setVisibility(View.VISIBLE);
                                } else {
                                    NewOrderFragment.this.binding.noTxt.setText("You Are Offline");
                                    NewOrderFragment.this.binding.lottieAnimation.setVisibility(View.GONE);
                                }
                                NewOrderFragment.this.binding.noTxt.setVisibility(View.VISIBLE);
                                NewOrderFragment.this.binding.newOrderList.setVisibility(View.GONE);
                            }
                            return;
                        }else{
                            HomeFragment.countNewOrder.setVisibility(View.GONE);
                        }
                        NewOrderFragment.this.getTimer();
                        NewOrderFragment.this.binding.progressBar.setVisibility(View.GONE);
                        if (OfflineFragment.ActiveSTatus.equalsIgnoreCase("online")) {
                            NewOrderFragment.this.binding.noTxt.setText("Waiting For New Order");
                            NewOrderFragment.this.binding.lottieAnimation.setVisibility(View.VISIBLE);
                        } else {
                            NewOrderFragment.this.binding.noTxt.setText("You Are Offline");
                            NewOrderFragment.this.binding.lottieAnimation.setVisibility(View.GONE);
                        }
                        NewOrderFragment.this.binding.noTxt.setVisibility(View.VISIBLE);
                        NewOrderFragment.this.binding.newOrderList.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        HomeFragment.countNewOrder.setVisibility(View.GONE);
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                NewOrderFragment.this.binding.noTxt.setVisibility(View.VISIBLE);
                NewOrderFragment.this.binding.progressBar.setVisibility(View.GONE);
                HomeFragment.countNewOrder.setVisibility(View.GONE);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getTimer() {
    }

    @Override // digi.coders.capsicodeliverypartner.helper.Refresh
    public void onRefresh() {
        loadNewOrderList();
    }
}
