package digi.coders.capsicodeliverypartner.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.firebase.messaging.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.activity.DashboardActivity;
import digi.coders.capsicodeliverypartner.activity.DocumentActivity;
import digi.coders.capsicodeliverypartner.activity.LoginActivity;
import digi.coders.capsicodeliverypartner.activity.OrderHistoryActivity;
import digi.coders.capsicodeliverypartner.activity.OurReviewsActivity;
import digi.coders.capsicodeliverypartner.activity.ViewProfileActivity;
import digi.coders.capsicodeliverypartner.activity.WebActivity;
import digi.coders.capsicodeliverypartner.databinding.FragmentAccountBinding;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.helper.ShowProgress;
import digi.coders.capsicodeliverypartner.model.DeliveryPartner;
import digi.coders.capsicodeliverypartner.singletask.SingleTask;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes5.dex */
public class AccountFragment extends Fragment {
    FragmentAccountBinding binding;
    private SingleTask singleTask;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentAccountBinding inflate = FragmentAccountBinding.inflate(inflater, container, false);
        this.binding = inflate;
        return inflate.getRoot();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.singleTask = (SingleTask) getActivity().getApplication();
        this.binding.viewProfile.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.fragment.AccountFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AccountFragment.this.startActivity(new Intent(AccountFragment.this.getActivity(), ViewProfileActivity.class));
            }
        });
        this.binding.viewDocument.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.fragment.AccountFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AccountFragment.this.startActivity(new Intent(AccountFragment.this.getActivity(), DocumentActivity.class));
            }
        });
        this.binding.orderHistory.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.fragment.AccountFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AccountFragment.this.startActivity(new Intent(AccountFragment.this.getActivity(), OrderHistoryActivity.class));
            }
        });
        this.binding.terms.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.fragment.AccountFragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(AccountFragment.this.getActivity(), WebActivity.class);
                intent.putExtra("key", 1);
                AccountFragment.this.startActivity(intent);
            }
        });
        this.binding.policy.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.fragment.AccountFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(AccountFragment.this.getActivity(), WebActivity.class);
                intent.putExtra("key", 2);
                AccountFragment.this.startActivity(intent);
            }
        });
        loadProfile();
        this.binding.about.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.fragment.AccountFragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AccountFragment.this.showDatePicker();
            }
        });
        this.binding.logout.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.fragment.AccountFragment.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ShowProgress.getShowProgress(AccountFragment.this.getActivity()).show();
                String ven = AccountFragment.this.singleTask.getValue("boy");
                DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
                MyApi myApi = (MyApi) AccountFragment.this.singleTask.getRetrofit().create(MyApi.class);
                Call<JsonArray> call = myApi.logout(deliveryPartner.getId());
                call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.fragment.AccountFragment.7.1
                    @Override // retrofit2.Callback
                    public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String res = jsonObject.getString("res");
                                String msg = jsonObject.getString("message");
                                if (res.equals("success")) {
                                    ShowProgress.getShowProgress(AccountFragment.this.getActivity()).hide();
                                    Toast.makeText(AccountFragment.this.getActivity(), msg, Toast.LENGTH_LONG).show();
                                    AccountFragment.this.changeStatus();
                                } else {
                                    ShowProgress.getShowProgress(AccountFragment.this.getActivity()).hide();
                                    Toast.makeText(AccountFragment.this.getActivity(), msg, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override // retrofit2.Callback
                    public void onFailure(Call<JsonArray> call2, Throwable t) {
                        Toast.makeText(AccountFragment.this.getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                        ShowProgress.getShowProgress(AccountFragment.this.getActivity()).hide();
                    }
                });
            }
        });
        this.binding.restaurantReview.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.fragment.AccountFragment.8
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AccountFragment.this.startActivity(new Intent(AccountFragment.this.getActivity(), OurReviewsActivity.class));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeStatus() {
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit1().create(MyApi.class);
        Call<JsonObject> call = myApi.changeStatus(deliveryPartner.getId(), "logout");
        call.enqueue(new Callback<JsonObject>() { // from class: digi.coders.capsicodeliverypartner.fragment.AccountFragment.9
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonObject> call2, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson((JsonElement) response.body()));
                        String res = jsonObject.getString("res");
                        String msg = jsonObject.getString(NotificationCompat.CATEGORY_MESSAGE);
                        if (res.equals("success")) {
                            AccountFragment.this.startActivity(new Intent(AccountFragment.this.getActivity(), LoginActivity.class));
                            if (DashboardActivity.Dashboard != null) {
                                DashboardActivity.Dashboard.finish();
                            }
                            AccountFragment.this.singleTask.removeUser("boy");
                            Toast.makeText(AccountFragment.this.getActivity(), msg, Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(AccountFragment.this.getActivity(), msg, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonObject> call2, Throwable t) {
                Toast.makeText(AccountFragment.this.getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadProfile() {
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner vendor = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        ShowProgress.getShowProgress(getActivity()).show();
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.profile(vendor.getId());
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.fragment.AccountFragment.10
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String res = jsonObject.getString("res");
                        String msg = jsonObject.getString("message");
                        ShowProgress.getShowProgress(AccountFragment.this.getActivity()).hide();
                        if (res.equals("success")) {
                            JSONArray jsonArray1 = jsonObject.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            DeliveryPartner partner = (DeliveryPartner) new Gson().fromJson(jsonArray1.getJSONObject(0).toString(),  DeliveryPartner.class);
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                            AccountFragment.this.singleTask.addValue("boy", jsonObject1);
                            AccountFragment.this.binding.useName.setText(partner.getName());
                            Picasso.get().load("https://yourcapsico.com/assets/uploads/delivery_boy/" + partner.getIcon()).placeholder(R.drawable.placeholder).into(AccountFragment.this.binding.userPic);
                        } else {
                            Toast.makeText(AccountFragment.this.getActivity(), msg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                ShowProgress.getShowProgress(AccountFragment.this.getActivity()).hide();
                Toast.makeText(AccountFragment.this.getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public String getTime(int hr, int min) {
        Calendar cal = Calendar.getInstance();
        cal.set(11, hr);
        cal.set(12, min);
        Format formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(cal.getTime());
    }

    public void showDatePicker() {
        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker().build();
        datePicker.addOnCancelListener(new DialogInterface.OnCancelListener() { // from class: digi.coders.capsicodeliverypartner.fragment.AccountFragment.12
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialog) {
            }
        });
        datePicker.show(getParentFragmentManager(), (String) null);
    }
}
