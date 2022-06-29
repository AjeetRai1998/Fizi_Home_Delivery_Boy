package digi.coders.capsicodeliverypartner.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.google.firebase.messaging.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import digi.coders.capsicodeliverypartner.adapter.ReviewAdapter;
import digi.coders.capsicodeliverypartner.databinding.ActivityOurReviewsBinding;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.helper.ShowProgress;
import digi.coders.capsicodeliverypartner.model.DeliveryPartner;
import digi.coders.capsicodeliverypartner.model.Review;
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
public class OurReviewsActivity extends AppCompatActivity {
    ActivityOurReviewsBinding binding;
    private List<Review> list;
    private SingleTask singleTask;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOurReviewsBinding inflate = ActivityOurReviewsBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        this.singleTask = (SingleTask) getApplication();
        this.binding.back.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.activity.OurReviewsActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                OurReviewsActivity.this.finish();
            }
        });
        loadReviewList();
    }

    private void loadReviewList() {
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven, DeliveryPartner.class);
        ShowProgress.getShowProgress(this).show();
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.getMyRating(deliveryPartner.getId());
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.activity.OurReviewsActivity.2
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        Log.e("sdsd", jsonArray.toString());
                        String res = jsonObject.getString("res");
                        String message = jsonObject.getString("message");
                        if (res.equals("success")) {
                            ShowProgress.getShowProgress(OurReviewsActivity.this).hide();
                            JSONArray jsonArray1 = jsonObject.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            OurReviewsActivity.this.list = new ArrayList();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                Review review = (Review) new Gson().fromJson(jsonArray1.getJSONObject(i).toString(), Review.class);
                                OurReviewsActivity.this.list.add(review);
                            }
                            OurReviewsActivity.this.binding.reviewList.setLayoutManager(new LinearLayoutManager(OurReviewsActivity.this, RecyclerView.VERTICAL, false));
                            OurReviewsActivity.this.binding.reviewList.setAdapter(new ReviewAdapter(OurReviewsActivity.this.list));
                            return;
                        }
                        Toast.makeText(OurReviewsActivity.this, message, Toast.LENGTH_LONG).show();
                        ShowProgress.getShowProgress(OurReviewsActivity.this).hide();
                        OurReviewsActivity.this.binding.noTxt.setVisibility(View.VISIBLE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                ShowProgress.getShowProgress(OurReviewsActivity.this).hide();
                Toast.makeText(OurReviewsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
