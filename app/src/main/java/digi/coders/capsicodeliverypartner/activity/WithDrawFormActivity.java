package digi.coders.capsicodeliverypartner.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.adapter.BankTransferListAdapter;
import digi.coders.capsicodeliverypartner.adapter.PaymentMethodAdapter;
import digi.coders.capsicodeliverypartner.databinding.ActivityWithDrawFormBinding;
import digi.coders.capsicodeliverypartner.fragment.WalletFragment;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.helper.ProgressDisplay;
import digi.coders.capsicodeliverypartner.helper.ShowProgress;
import digi.coders.capsicodeliverypartner.model.DeliveryPartner;
import digi.coders.capsicodeliverypartner.model.PaymentModeImage;
import digi.coders.capsicodeliverypartner.model.ProofModel.ResponseProof;
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
public class WithDrawFormActivity extends AppCompatActivity {
    ActivityWithDrawFormBinding binding;
    private List<PaymentModeImage> list;
    ProgressDisplay progressDisplay;
    private SingleTask singleTask;
    String wallet = "";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWithDrawFormBinding inflate = ActivityWithDrawFormBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.singleTask = (SingleTask) getApplication();
        this.progressDisplay = new ProgressDisplay(this);
        loadProof();
        this.binding.back.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.WithDrawFormActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                WithDrawFormActivity.this.finish();
            }
        });
        this.binding.btnWithdraw.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.WithDrawFormActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                String withdrawAmount = WithDrawFormActivity.this.binding.withdrawalMoney.getText().toString();
                if (withdrawAmount.equals("")) {
                    WithDrawFormActivity.this.binding.withdrawalMoney.setError("Enter Amount");
                    WithDrawFormActivity.this.binding.withdrawalMoney.requestFocus();
                } else if (Double.parseDouble(WithDrawFormActivity.this.wallet) > Double.parseDouble(withdrawAmount)) {
                    WithDrawFormActivity.this.withdrawal(withdrawAmount);
                } else {
                    Toast.makeText(WithDrawFormActivity.this.getApplicationContext(), "You have not enough amount in your wallet.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loadProof() {
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner vendor = (DeliveryPartner) new Gson().fromJson(ven, DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit1().create(MyApi.class);
        Call<ResponseProof> call = myApi.getProof(vendor.getId(), "BankProofDelivery");
        call.enqueue(new Callback<ResponseProof>() { // from class: digi.coders.capsicodeliverypartner.activity.WithDrawFormActivity.3
            @Override // retrofit2.Callback
            public void onResponse(Call<ResponseProof> call2, Response<ResponseProof> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body().getRes().equalsIgnoreCase("success")) {
                            WithDrawFormActivity.this.binding.prooflist.setLayoutManager(new LinearLayoutManager(WithDrawFormActivity.this.getApplicationContext()));
                            WithDrawFormActivity.this.binding.prooflist.setHasFixedSize(true);
                            WithDrawFormActivity.this.binding.prooflist.setAdapter(new BankTransferListAdapter(response.body().getData(), WithDrawFormActivity.this.getApplicationContext()));
                        } else {
                            Toast.makeText(WithDrawFormActivity.this.getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(WithDrawFormActivity.this.getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<ResponseProof> call2, Throwable t) {
                Toast.makeText(WithDrawFormActivity.this.getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadPaymentMethodOption() {
        ArrayList arrayList = new ArrayList();
        this.list = arrayList;
        arrayList.add(new PaymentModeImage(R.drawable.phone_pay));
        this.list.add(new PaymentModeImage(R.drawable.google_pay));
        this.list.add(new PaymentModeImage(R.drawable.amazon));
        this.list.add(new PaymentModeImage(R.drawable.paytm));
        this.list.add(new PaymentModeImage(R.drawable.bank_icon));
        PaymentMethodAdapter adapter = new PaymentMethodAdapter(this.list);
        this.binding.paymentMethodOption.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        this.binding.paymentMethodOption.setAdapter(adapter);
        adapter.findMyPosition(new PaymentMethodAdapter.GetPosition() { // from class: digi.coders.capsicodeliverypartner.activity.WithDrawFormActivity.4
            @Override // digi.coders.capsicodeliverypartner.adapter.PaymentMethodAdapter.GetPosition
            public void click(View v, int position) {
                if (position == 0) {
                    WithDrawFormActivity.this.binding.detailsForm.setVisibility(View.VISIBLE);
                    WithDrawFormActivity.this.binding.bankDetails.setVisibility(View.GONE);
                } else if (position == 1) {
                    WithDrawFormActivity.this.binding.detailsForm.setVisibility(View.VISIBLE);
                    WithDrawFormActivity.this.binding.bankDetails.setVisibility(View.GONE);
                } else if (position == 2) {
                    WithDrawFormActivity.this.binding.detailsForm.setVisibility(View.VISIBLE);
                    WithDrawFormActivity.this.binding.bankDetails.setVisibility(View.GONE);
                } else if (position == 3) {
                    WithDrawFormActivity.this.binding.detailsForm.setVisibility(View.VISIBLE);
                    WithDrawFormActivity.this.binding.bankDetails.setVisibility(View.GONE);
                } else {
                    WithDrawFormActivity.this.binding.detailsForm.setVisibility(View.GONE);
                    WithDrawFormActivity.this.binding.bankDetails.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void loadTransactionList() {
        ShowProgress.getShowProgress(this).show();
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.getTransactionHistory(deliveryPartner.getId(),"0");
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.WithDrawFormActivity.5
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    Log.e("sddde", response.body().toString());
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String res = jsonObject.getString("res");
                        jsonObject.getString("message");
                        WithDrawFormActivity.this.wallet = jsonObject.getString("wallet");
                        Log.e("sdsd", WithDrawFormActivity.this.wallet);
                        jsonObject.getString("cashwallet");
                        if (res.equals("success")) {
                            ShowProgress.getShowProgress(WithDrawFormActivity.this.getApplicationContext()).hide();
                        }
                    } catch (JSONException e) {
                        ShowProgress.getShowProgress(WithDrawFormActivity.this.getApplicationContext()).hide();
                        e.printStackTrace();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                ShowProgress.getShowProgress(WithDrawFormActivity.this.getApplicationContext()).hide();
                Toast.makeText(WithDrawFormActivity.this.getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void withdrawal(String amount) {
        ShowProgress.getShowProgress(this).show();
        this.progressDisplay.showProgress();
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven, DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.withdrawal(deliveryPartner.getId(), amount);
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.WithDrawFormActivity.6
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    Log.e("sddde", response.body().toString());
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String res = jsonObject.getString("res");
                        String msg = jsonObject.getString("message");
                        if (res.equals("success")) {
                            ShowProgress.getShowProgress(WithDrawFormActivity.this.getApplicationContext()).hide();
                            Toast.makeText(WithDrawFormActivity.this, msg, Toast.LENGTH_LONG).show();
                            WalletFragment.refresh.onRefresh();
                            WithDrawFormActivity.this.finish();
                        } else {
                            ShowProgress.getShowProgress(WithDrawFormActivity.this.getApplicationContext()).hide();
                            Toast.makeText(WithDrawFormActivity.this, msg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        ShowProgress.getShowProgress(WithDrawFormActivity.this.getApplicationContext()).hide();
                        e.printStackTrace();
                    }
                }
                WithDrawFormActivity.this.progressDisplay.hideProgress();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                ShowProgress.getShowProgress(WithDrawFormActivity.this.getApplicationContext()).hide();
                WithDrawFormActivity.this.progressDisplay.hideProgress();
                Toast.makeText(WithDrawFormActivity.this.getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
