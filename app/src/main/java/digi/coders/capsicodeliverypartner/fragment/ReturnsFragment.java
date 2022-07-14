package digi.coders.capsicodeliverypartner.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.databinding.FragmentHomeBinding;
import digi.coders.capsicodeliverypartner.databinding.FragmentReturnsBinding;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.model.DeliveryPartner;
import digi.coders.capsicodeliverypartner.singletask.SingleTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes5.dex */
public class ReturnsFragment extends Fragment {
    public static FragmentReturnsBinding binding;
    int pageStatus=0;
    public static TextView countNewOrder,countPrepare,countComplete;
    SingleTask singleTask;
    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentReturnsBinding inflate = FragmentReturnsBinding.inflate(inflater, container, false);
        this.binding = inflate;
        singleTask=(SingleTask) getActivity().getApplication();
        return inflate.getRoot();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        countPrepare=view.findViewById(R.id.countPrepare);
        countComplete=view.findViewById(R.id.countComplete);
        countNewOrder=view.findViewById(R.id.countNewOrder);


        getChildFragmentManager().beginTransaction().replace(R.id.frame, new ReturnNewOrderFragment()).commitAllowingStateLoss();

        binding.lineNew.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.txtNew.setTextColor(Color.parseColor("#f7b614"));
                        binding.viewNewOrder.setVisibility(View.VISIBLE);
                        binding.txtPrepare.setTextColor(Color.parseColor("#000000"));
                        binding.viewPrepare.setVisibility(View.INVISIBLE);
                        binding.txtComplete.setTextColor(Color.parseColor("#000000"));
                        binding.viewComplete.setVisibility(View.INVISIBLE);
                        getChildFragmentManager().beginTransaction().replace(R.id.frame, new ReturnNewOrderFragment()).commitAllowingStateLoss();

                    }
                }
        );

        binding.linePrepare.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.txtNew.setTextColor(Color.parseColor("#000000"));
                        binding.viewNewOrder.setVisibility(View.INVISIBLE);
                        binding.txtPrepare.setTextColor(Color.parseColor("#f7b614"));
                        binding.viewPrepare.setVisibility(View.VISIBLE);
                        binding.txtComplete.setTextColor(Color.parseColor("#000000"));
                        binding.viewComplete.setVisibility(View.INVISIBLE);
                        getChildFragmentManager().beginTransaction().replace(R.id.frame, new ReturnOngoingFragment()).commitAllowingStateLoss();

                    }
                }
        );

        binding.lineComplete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.txtNew.setTextColor(Color.parseColor("#000000"));
                        binding.viewNewOrder.setVisibility(View.INVISIBLE);
                        binding.txtPrepare.setTextColor(Color.parseColor("#000000"));
                        binding.viewPrepare.setVisibility(View.INVISIBLE);
                        binding.txtComplete.setTextColor(Color.parseColor("#f7b614"));
                        binding.viewComplete.setVisibility(View.VISIBLE);
                        getChildFragmentManager().beginTransaction().replace(R.id.frame, new ReturnDeliveredFragment()).commitAllowingStateLoss();

                    }
                }
        );

        binding.left.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(pageStatus==1){
                            binding.txtNew.setTextColor(Color.parseColor("#f7b614"));
                            binding.viewNewOrder.setVisibility(View.VISIBLE);
                            binding.txtPrepare.setTextColor(Color.parseColor("#000000"));
                            binding.viewPrepare.setVisibility(View.INVISIBLE);
                            binding.txtComplete.setTextColor(Color.parseColor("#000000"));
                            binding.viewComplete.setVisibility(View.INVISIBLE);
                            getChildFragmentManager().beginTransaction().replace(R.id.frame, new ReturnNewOrderFragment()).commitAllowingStateLoss();
                            pageStatus=0;
                            binding.left.setVisibility(View.GONE);
                        }else if(pageStatus==2){
                            binding.txtNew.setTextColor(Color.parseColor("#000000"));
                            binding.viewNewOrder.setVisibility(View.INVISIBLE);
                            binding.txtPrepare.setTextColor(Color.parseColor("#f7b614"));
                            binding.viewPrepare.setVisibility(View.VISIBLE);
                            binding.txtComplete.setTextColor(Color.parseColor("#000000"));
                            binding.viewComplete.setVisibility(View.INVISIBLE);
                            getChildFragmentManager().beginTransaction().replace(R.id.frame, new ReturnOngoingFragment()).commitAllowingStateLoss();
                            pageStatus=1;
                            binding.right.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );

        binding.right.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(pageStatus==0){
                            binding.txtNew.setTextColor(Color.parseColor("#000000"));
                            binding.viewNewOrder.setVisibility(View.INVISIBLE);
                            binding.txtPrepare.setTextColor(Color.parseColor("#f7b614"));
                            binding.viewPrepare.setVisibility(View.VISIBLE);
                            binding.txtComplete.setTextColor(Color.parseColor("#000000"));
                            binding.viewComplete.setVisibility(View.INVISIBLE);
                            getChildFragmentManager().beginTransaction().replace(R.id.frame, new ReturnOngoingFragment()).commitAllowingStateLoss();
                            pageStatus=1;
                            binding.left.setVisibility(View.VISIBLE);
                        }else if(pageStatus==1){
                            binding.txtNew.setTextColor(Color.parseColor("#000000"));
                            binding.viewNewOrder.setVisibility(View.INVISIBLE);
                            binding.txtPrepare.setTextColor(Color.parseColor("#000000"));
                            binding.viewPrepare.setVisibility(View.INVISIBLE);
                            binding.txtComplete.setTextColor(Color.parseColor("#f7b614"));
                            binding.viewComplete.setVisibility(View.VISIBLE);
                            getChildFragmentManager().beginTransaction().replace(R.id.frame, new ReturnDeliveredFragment()).commitAllowingStateLoss();
                            pageStatus=2;
                            binding.right.setVisibility(View.GONE);
                        }
                    }
                }
        );



        Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        sdf.format(new Date());
        SimpleDateFormat sdf_ = new SimpleDateFormat("EEEE");
        Date date = new Date();
        String dayName = sdf_.format(date);
        this.binding.currentDate.setText("" + dayName + " " + sdf.format(new Date()));

        loadCompletedOrder();
        loadProcessOrder();
        getDelCharge();
    }

    private void loadCompletedOrder() {
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = singleTask.getRetrofit().create(MyApi.class);
        Log.e("merchant_id", deliveryPartner.getId() + "");
        Call<JsonArray> call = myApi.getOrderwithoutDetails(deliveryPartner.getId(),"Delivered");
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson(response.body()));
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String res = jsonObject1.getString("res");

                        String message = jsonObject1.getString("message");
                        Log.e("sdsd", jsonObject1.toString());
                        if (res.equals("success")) {
                            int count=jsonObject1.getInt("count");
                            countComplete.setText(count+"");
                            countComplete.setVisibility(View.VISIBLE);
                        } else {
                            ReturnsFragment.countComplete.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
            }
        });

    }

    private void loadProcessOrder() {
        //ShowProgress.getShowProgress(getActivity()).show();
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
//        Toast.makeText(getActivity(), "ID: "+vendor.getId(), Toast.LENGTH_SHORT).show();
        MyApi myApi = singleTask.getRetrofit().create(MyApi.class);
        Log.e("merchant_id", deliveryPartner.getId() + "");
        Call<JsonArray> call = myApi.getOrderwithoutDetails(deliveryPartner.getId(),"Accepted");
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.e("grgrr", response.body().toString());
                        JSONArray jsonArray = new JSONArray(new Gson().toJson(response.body()));
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String res = jsonObject1.getString("res");

                        String message = jsonObject1.getString("message");
                        if (res.equals("success")) {

                            int count=jsonObject1.getInt("count");
                            ReturnsFragment.countPrepare.setText(count+"");
                            ReturnsFragment.countPrepare.setVisibility(View.VISIBLE);

                        } else {
                            ReturnsFragment.countPrepare.setVisibility(View.GONE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {


            }
        });
    }

   public static String baseAmount="",baseKm="0",delCharge="0";
    public void getDelCharge() {
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit1().create(MyApi.class);
        Call<JsonObject> call = myApi.getDelCharge(deliveryPartner.getId(), "Get");
        call.enqueue(new Callback<JsonObject>() { // from class: digi.coders.capsicodeliverypartner.fragment.OfflineFragment.10
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonObject> call2, Response<JsonObject> response) {
                String res;
                JSONObject jsonObject1;
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject12 = new JSONObject(new Gson().toJson((JsonElement) response.body()));
                        String res2 = jsonObject12.getString("res");
                        if (res2.equalsIgnoreCase("success")) {
                            JSONObject jsonArray1 = jsonObject12.getJSONObject("data");
                            baseAmount=jsonArray1.getString("base");
                            baseKm=jsonArray1.getString("km");
                            delCharge=jsonArray1.getString("amount");

                        }else{

                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonObject> call2, Throwable t) {
            }
        });
    }

}
