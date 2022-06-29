package digi.coders.capsicodeliverypartner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.firebase.messaging.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import digi.coders.capsicodeliverypartner.adapter.PaymentListAdapter;
import digi.coders.capsicodeliverypartner.databinding.ActivityCashTransferBinding;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.model.DeliveryPartner;
import digi.coders.capsicodeliverypartner.model.PaymentDetails;
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
public class CashTransferActivity extends AppCompatActivity {
    ActivityCashTransferBinding binding;
    private List<PaymentDetails> paymentDetailsList;
    private SingleTask singleTask;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCashTransferBinding inflate = ActivityCashTransferBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.singleTask = (SingleTask) getApplication();
        loadTransferHistory();
    }

    private void loadTransferHistory() {
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven, DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.getCodMoneyTransferHistroy(deliveryPartner.getId());
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.CashTransferActivity.1
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String res = jsonObject.getString("res");
                        jsonObject.getString("message");
                        if (res.equals("success")) {
                            CashTransferActivity.this.binding.progressBar.setVisibility(View.GONE);
                            JSONArray jsonArray1 = jsonObject.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            String cashAmount = jsonObject.getString("cashwallet");
                            CashTransferActivity.this.binding.progressBar.setVisibility(View.GONE);
                            if (jsonArray1.length() > 0) {
                                CashTransferActivity.this.binding.progressBar.setVisibility(View.GONE);
                                CashTransferActivity.this.paymentDetailsList = new ArrayList();
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    PaymentDetails payment = (PaymentDetails) new Gson().fromJson(jsonArray1.getJSONObject(i).toString(), PaymentDetails.class);
                                    CashTransferActivity.this.paymentDetailsList.add(payment);
                                }
                                CashTransferActivity.this.binding.transferList.setLayoutManager(new LinearLayoutManager(CashTransferActivity.this, LinearLayoutManager.VERTICAL, false));
                                CashTransferActivity.this.binding.transferList.setAdapter(new PaymentListAdapter(CashTransferActivity.this.paymentDetailsList));
                            }
                            CashTransferActivity.this.binding.cashWallet.setText("₹" + cashAmount);
                            return;
                        }
                        CashTransferActivity.this.binding.progressBar.setVisibility(View.GONE);
                        CashTransferActivity.this.binding.noText.setVisibility(View.VISIBLE);
                        CashTransferActivity.this.startActivity(new Intent(CashTransferActivity.this, KYCActivity.class));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                CashTransferActivity.this.binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(CashTransferActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
