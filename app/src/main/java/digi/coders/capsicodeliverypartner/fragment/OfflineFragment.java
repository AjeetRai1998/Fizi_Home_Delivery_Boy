package digi.coders.capsicodeliverypartner.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.messaging.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.suke.widget.SwitchButton;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.activity.DashboardActivity;
import digi.coders.capsicodeliverypartner.activity.VaccinationActivity;
import digi.coders.capsicodeliverypartner.adapter.IncelistAdapterAdapter;
import digi.coders.capsicodeliverypartner.adapter.TransactionAdapter;
import digi.coders.capsicodeliverypartner.databinding.FragmentOfflineBinding;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.helper.NotificationService;
import digi.coders.capsicodeliverypartner.helper.ShowProgress;
import digi.coders.capsicodeliverypartner.model.Card;
import digi.coders.capsicodeliverypartner.model.DeliveryPartner;
import digi.coders.capsicodeliverypartner.model.IncentiveModel;
import digi.coders.capsicodeliverypartner.model.MyOrder;
import digi.coders.capsicodeliverypartner.model.MyOrder1;
import digi.coders.capsicodeliverypartner.model.Transaction;
import digi.coders.capsicodeliverypartner.singletask.SingleTask;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes5.dex */
public class OfflineFragment extends Fragment {
    public static String ActiveSTatus = "";
    Activity activity;
    FragmentOfflineBinding binding;
    DecimalFormat decim;
    FragmentManager fm;
    private List<Card> list;
    private SingleTask singleTask;
    public int counter = 0;
    boolean isStart = true;
    double amountDelivered = 0.0d;
    public static double todayDeliveredAMount = 0.0d;
    double floatingcash = 0.0d;
    double earning = 0.0d;
    double totalSales = 0.0d;
    int saleCount = 0;
    int salCount=0;
    int orderCount = 0;
    int rejectStatus = 0;
    double loginHour = 0.0d;
    int changeStatus = 0;
    int cashwalletlimitstatus = 0;
  public static   int totalDeliveredOrder = 0,todayEarning=0;
    int totalIncentiveAmount = 0;
    TextView txtIncentive;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentOfflineBinding inflate = FragmentOfflineBinding.inflate(inflater, container, false);
        this.binding = inflate;
        return inflate.getRoot();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.singleTask = (SingleTask) getActivity().getApplication();
        this.fm = getParentFragmentManager();
        loadProfile();
        getDetails();

        txtIncentive=view.findViewById(R.id.txtIncentive);
        this.activity = getActivity();
        this.decim = new DecimalFormat("#");
        this.binding.switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() { // from class: digi.coders.capsicodeliverypartner.fragment.OfflineFragment.1
            @Override // com.suke.widget.SwitchButton.OnCheckedChangeListener
            public void onCheckedChanged(SwitchButton view2, boolean isChecked) {
                if (DashboardActivity.newOrderSTatus != 1) {
                    DashboardActivity.newOrderSTatus = 1;
                } else if (OfflineFragment.this.changeStatus == 0) {
                    OfflineFragment.this.changeStatus = 1;
                    if (isChecked) {
                        OfflineFragment.this.updateStatus("true");
                    } else {
                        OfflineFragment.this.updateStatus("false");
                    }
                } else {
                    OfflineFragment.this.changeStatus = 0;
                }
            }
        });
        this.binding.filterText.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.fragment.OfflineFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(OfflineFragment.this.activity, OfflineFragment.this.binding.filterText);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: digi.coders.capsicodeliverypartner.fragment.OfflineFragment.2.1
                    @Override // android.widget.PopupMenu.OnMenuItemClickListener
                    public boolean onMenuItemClick(MenuItem item) {
                        if (R.id.today == item.getItemId()) {
                            OfflineFragment.this.saleCount = 0;
                            OfflineFragment.this.loadDeliveredOrderList();
                            OfflineFragment.this.getLoginHour();
//                            loadTransactionList();
                            OfflineFragment.this.binding.txtFilter.setText("Today");
                            return true;
                        } else if (R.id.yesterday == item.getItemId()) {
                            OfflineFragment.this.saleCount = -1;
                            OfflineFragment.this.loadDeliveredOrderList();
                            OfflineFragment.this.getLoginHour();
//                            loadTransactionList();
                            OfflineFragment.this.binding.txtFilter.setText("Yesterday");
                            return true;
                        } else if (R.id.sevenDays == item.getItemId()) {
                            OfflineFragment.this.saleCount = -7;
                            OfflineFragment.this.loadDeliveredOrderList();
                            OfflineFragment.this.getLoginHour();
//                            loadTransactionList();
                            OfflineFragment.this.binding.txtFilter.setText("Last 7 Days");
                            return true;
                        } else if (R.id.thirtyDays == item.getItemId()) {
                            OfflineFragment.this.saleCount = -30;
                            OfflineFragment.this.loadDeliveredOrderList();
                            OfflineFragment.this.getLoginHour();
//                            loadTransactionList();
                            OfflineFragment.this.binding.txtFilter.setText("Last 30 Days");
                            return true;
                        } else if (R.id.allTime != item.getItemId()) {
                            return true;
                        } else {
                            OfflineFragment.this.saleCount = 777;
                            OfflineFragment.this.loadDeliveredOrderList();
                            OfflineFragment.this.getLoginHour();
//                            loadTransactionList();
                            OfflineFragment.this.binding.txtFilter.setText("All Time");
                            return true;
                        }
                    }
                });
                popup.show();
            }
        });
        this.binding.cardWallet.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.fragment.OfflineFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                WalletFragment.WalletPageStatus = "w";
                OfflineFragment.this.fm.beginTransaction().replace(R.id.container, new WalletFragment(), "w").commit();
            }
        });
        this.binding.cardProducts.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.fragment.OfflineFragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                WalletFragment.WalletPageStatus = "f";
                OfflineFragment.this.fm.beginTransaction().replace(R.id.container, new WalletFragment(), "w").commit();
            }
        });
        this.binding.cardvac.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.fragment.OfflineFragment.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                OfflineFragment.this.startActivity(new Intent(OfflineFragment.this.activity, VaccinationActivity.class));
            }
        });
        loadDeliveredOrderList();
        getLoginHour();
//        loadTransactionList();


    }

    public void updateStatus(final String status) {
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.updateStatus(deliveryPartner.getId(), status);
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.fragment.OfflineFragment.6
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String res = jsonObject.getString("res");
                        String msg = jsonObject.getString("message");
                        if (res.equals("success")) {
                            if (status.equalsIgnoreCase("true")) {
                                OfflineFragment.this.changeStatus(0);
                                OfflineFragment.this.binding.text.setText("You'r on Duty");
                                Intent inetnt = new Intent(OfflineFragment.this.activity, NotificationService.class);
                                inetnt.putExtra("title", "online");
                                OfflineFragment.this.activity.startService(inetnt);
                                OfflineFragment.ActiveSTatus = "online";
                            } else {
                                OfflineFragment.this.changeStatus(1);
                                OfflineFragment.this.binding.text.setText("Start Duty");
                                Intent inetnt2 = new Intent(OfflineFragment.this.activity, NotificationService.class);
                                inetnt2.putExtra("title", "offline");
                                OfflineFragment.this.activity.startService(inetnt2);
                                OfflineFragment.ActiveSTatus = "offline";
                            }
                            OfflineFragment.this.loadProfile();
                            Toast.makeText(OfflineFragment.this.activity, msg, Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (status.equalsIgnoreCase("false")) {
                            OfflineFragment.this.binding.text.setText("You'r on Duty");
                            OfflineFragment.this.binding.switchButton.setChecked(true);
                            Intent inetnt3 = new Intent(OfflineFragment.this.activity, NotificationService.class);
                            inetnt3.putExtra("title", "online");
                            OfflineFragment.this.activity.startService(inetnt3);
                        } else {
                            OfflineFragment.this.binding.text.setText("Start Duty");
                            OfflineFragment.this.binding.switchButton.setChecked(false);
                            Intent inetnt4 = new Intent(OfflineFragment.this.activity, NotificationService.class);
                            inetnt4.putExtra("title", "offline");
                            OfflineFragment.this.activity.startService(inetnt4);
                        }
                        Toast.makeText(OfflineFragment.this.activity, msg, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        if (status.equalsIgnoreCase("false")) {
                            OfflineFragment.this.binding.text.setText("You'r on Duty");
                            OfflineFragment.this.binding.switchButton.setChecked(true);
                            Intent inetnt5 = new Intent(OfflineFragment.this.activity, NotificationService.class);
                            inetnt5.putExtra("title", "online");
                            OfflineFragment.this.activity.startService(inetnt5);
                        } else {
                            OfflineFragment.this.binding.text.setText("Start Duty");
                            OfflineFragment.this.binding.switchButton.setChecked(false);
                            Intent inetnt6 = new Intent(OfflineFragment.this.activity, NotificationService.class);
                            inetnt6.putExtra("title", "offline");
                            OfflineFragment.this.activity.startService(inetnt6);
                        }
                        e.printStackTrace();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                if (status.equalsIgnoreCase("false")) {
                    OfflineFragment.this.binding.text.setText("You'r on Duty");
                    OfflineFragment.this.binding.switchButton.setChecked(true);
                    Intent inetnt = new Intent(OfflineFragment.this.activity, NotificationService.class);
                    inetnt.putExtra("title", "online");
                    OfflineFragment.this.activity.startService(inetnt);
                } else {
                    OfflineFragment.this.binding.text.setText("Start Duty");
                    OfflineFragment.this.binding.switchButton.setChecked(false);
                    Intent inetnt2 = new Intent(OfflineFragment.this.activity, NotificationService.class);
                    inetnt2.putExtra("title", "offline");
                    OfflineFragment.this.activity.startService(inetnt2);
                }
                Toast.makeText(OfflineFragment.this.activity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeStatus(int status) {
        Call<JsonObject> call;
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit1().create(MyApi.class);
        if (status == 0) {
            call = myApi.changeStatus(deliveryPartner.getId(), "login");
        } else {
            call = myApi.changeStatus(deliveryPartner.getId(), "logout");
        }
        call.enqueue(new Callback<JsonObject>() { // from class: digi.coders.capsicodeliverypartner.fragment.OfflineFragment.7
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonObject> call2, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String res = jsonObject.getString("res");
                        String msg = jsonObject.getString("message");
                        if (res.equals("success")) {
                            Toast.makeText(OfflineFragment.this.activity, msg, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(OfflineFragment.this.activity, msg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonObject> call2, Throwable t) {
                Toast.makeText(OfflineFragment.this.activity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadCardList() {
        this.list = new ArrayList();
        new Card("Today Earnings", "121", R.drawable.earnigs);
        new Card("Weekly Earnings", "11", R.drawable.earnigs);
        new Card("Long Duration", "11", R.drawable.ic_baseline_access_time_filled_24);
        new Card("Total Amount", "13", R.drawable.floating_money);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadProfile() {
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        Call<JsonArray> call = myApi.profile(deliveryPartner.getId());
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.fragment.OfflineFragment.8
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String res = jsonObject.getString("res");
                        jsonObject.getString("message");
                        if (res.equals("success")) {
                            JSONArray jsonArray1 = jsonObject.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            DeliveryPartner partner = (DeliveryPartner) new Gson().fromJson(jsonArray1.getJSONObject(0).toString(),  DeliveryPartner.class);
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                            OfflineFragment.this.singleTask.addValue("boy", jsonObject1);
                            OfflineFragment.this.binding.floatingCash.setText("₹ " + new DecimalFormat("##.##").format(Double.parseDouble(partner.getCashwallet())));
                            double percentage = (Double.parseDouble(partner.getCashwallet()) / Double.parseDouble(partner.getCashwallet_limit())) * 100.0d;
                            if (percentage >= 75.0d) {
                                OfflineFragment.this.binding.msgFloat.setVisibility(View.VISIBLE);
                                OfflineFragment.this.binding.msgFloat.setText("Your floating cash balance is going exceed soon");
                            }
                            if (Double.parseDouble(partner.getCashwallet()) >= Double.parseDouble(partner.getCashwallet_limit())) {
                                OfflineFragment.this.binding.msgFloat.setVisibility(View.VISIBLE);
                                OfflineFragment.this.cashwalletlimitstatus = 1;
                                OfflineFragment.this.binding.msgFloat.setText("Your floating cash balance has been exceeded\nYour ID is inactive and you will not recieve any order\nDeposite the balance to activate your ID.");
                            }
                            if (OfflineFragment.this.cashwalletlimitstatus == 1) {
                                OfflineFragment.this.binding.switchButton.setEnabled(false);
                                OfflineFragment.this.binding.switchButton.setChecked(false);
                            } else {
                                OfflineFragment.this.binding.switchButton.setEnabled(true);
                            }
                            String ven2 = OfflineFragment.this.singleTask.getValue("boy");
                            DeliveryPartner deliveryPartner2 = (DeliveryPartner) new Gson().fromJson(ven2,  DeliveryPartner.class);
                            if (!deliveryPartner2.getActiveStatus().equalsIgnoreCase("online")) {
                                OfflineFragment.this.binding.switchButton.setChecked(false);
                                DashboardActivity.newOrderSTatus = 1;
                                OfflineFragment.this.binding.text.setText("Start Duty");
                                Intent inetnt = new Intent(OfflineFragment.this.activity, NotificationService.class);
                                inetnt.putExtra("title", "offline");
                                OfflineFragment.this.activity.startService(inetnt);
                                OfflineFragment.ActiveSTatus = "offline";
                                OfflineFragment.this.changeStatus = 0;
                            } else if (OfflineFragment.this.cashwalletlimitstatus == 0) {
                                OfflineFragment.this.binding.switchButton.setChecked(true);
                                OfflineFragment.this.changeStatus = 0;
                                OfflineFragment.this.binding.text.setText("You'r on Duty");
                                Intent inetnt2 = new Intent(OfflineFragment.this.activity, NotificationService.class);
                                inetnt2.putExtra("title", "online");
                                OfflineFragment.this.activity.startService(inetnt2);
                                OfflineFragment.ActiveSTatus = "online";
                            } else {
                                OfflineFragment.this.binding.switchButton.setChecked(false);
                                OfflineFragment.this.changeStatus = 0;
                                OfflineFragment.this.binding.text.setText("Start Duty");
                                Intent inetnt3 = new Intent(OfflineFragment.this.activity, NotificationService.class);
                                inetnt3.putExtra("title", "offline");
                                OfflineFragment.this.activity.startService(inetnt3);
                                OfflineFragment.ActiveSTatus = "offline";
                                OfflineFragment.this.changeStatus(1);
                            }
                            if (!partner.getWallet().equalsIgnoreCase("0")) {
                                OfflineFragment.this.binding.msgEarning.setVisibility(View.VISIBLE);
                            } else {
                                OfflineFragment.this.binding.msgEarning.setVisibility(View.GONE);
                            }
                            OfflineFragment.this.binding.walletAmount.setText("₹ " + partner.getWallet());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadDeliveredOrderList() {

        this.floatingcash = 0.0d;
        this.amountDelivered = 0.0d;
        this.orderCount = 0;
        totalDeliveredOrder=0;
        todayDeliveredAMount=0;
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.getOrderwithoutDetails(deliveryPartner.getId(), "Delivered");
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.fragment.OfflineFragment.9
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                JSONObject jsonObject1;
                JSONArray jsonArray;
                String res;
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray2 = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject12 = jsonArray2.getJSONObject(0);
                        String res2 = jsonObject12.getString("res");
                        if (res2.equals("success")) {
                            JSONArray jsonArray1 = jsonObject12.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            jsonObject12.getInt("count");
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(new Date());
                            calendar.add(6, OfflineFragment.this.saleCount);
                            Date current = calendar.getTime();
                            int i = 0;
                            while (i < jsonArray1.length()) {
                                MyOrder1 myOrder = (MyOrder1) new Gson().fromJson(jsonArray1.getJSONObject(i).toString(),  MyOrder1.class);
                                if (myOrder.getMethod().equalsIgnoreCase("cod")) {
//                                    OfflineFragment.this.floatingcash += Double.parseDouble(myOrder.getAmount()) - Double.parseDouble(myOrder.getShippinCharge());
                                }
                                if (OfflineFragment.this.saleCount == 777) {
                                    OfflineFragment.this.orderCount++;
                                    OfflineFragment.this.amountDelivered += Double.parseDouble(myOrder.getAmount());
//                                    OfflineFragment.this.earning += Double.parseDouble(myOrder.getShippinCharge());
                                    jsonArray = jsonArray2;
                                    jsonObject1 = jsonObject12;
                                    res = res2;
                                } else if (OfflineFragment.this.saleCount == 0) {
                                    Date orderdate = new SimpleDateFormat("yyyy-MM-dd").parse(myOrder.getCreatedAt());
                                    String currentString = new SimpleDateFormat("yyyy-MM-dd").format(current);
                                    if (new SimpleDateFormat("yyyy-MM-dd").parse(currentString).equals(orderdate)) {
                                        jsonArray = jsonArray2;
                                        jsonObject1 = jsonObject12;
                                        OfflineFragment.this.amountDelivered += Double.parseDouble(myOrder.getAmount());
//                                        OfflineFragment.this.todayDeliveredAMount += Double.parseDouble(myOrder.getAmount());
                                        res = res2;
//                                        OfflineFragment.this.earning += Double.parseDouble(myOrder.getShippinCharge());

//                                        OfflineFragment.this.todayEarning += Double.parseDouble(myOrder.getShippinCharge());
                                        OfflineFragment.this.orderCount++;
                                        totalDeliveredOrder=totalDeliveredOrder+1;
                                    } else {
                                        jsonArray = jsonArray2;
                                        jsonObject1 = jsonObject12;
                                        res = res2;
                                    }
                                } else {
                                    jsonArray = jsonArray2;
                                    jsonObject1 = jsonObject12;
                                    res = res2;
                                    if (OfflineFragment.this.saleCount == -1) {
                                        Date orderdate2 = new SimpleDateFormat("yyyy-MM-dd").parse(myOrder.getCreatedAt());
                                        String currentString2 = new SimpleDateFormat("yyyy-MM-dd").format(current);
                                        if (new SimpleDateFormat("yyyy-MM-dd").parse(currentString2).equals(orderdate2)) {
                                            OfflineFragment.this.amountDelivered += Double.parseDouble(myOrder.getAmount());
//                                            OfflineFragment.this.earning += Double.parseDouble(myOrder.getShippinCharge());
                                            OfflineFragment.this.orderCount++;
                                        }
                                    } else if (OfflineFragment.this.saleCount == -7) {
                                        Date orderdate3 = new SimpleDateFormat("yyyy-MM-dd").parse(myOrder.getCreatedAt());
                                        String currentString3 = new SimpleDateFormat("yyyy-MM-dd").format(current);
                                        Date currentDate = new SimpleDateFormat("yyyy-MM-dd").parse(currentString3);
                                        if (currentDate.equals(orderdate3) || currentDate.before(orderdate3)) {
                                            OfflineFragment.this.amountDelivered += Double.parseDouble(myOrder.getAmount());
//                                            OfflineFragment.this.earning += Double.parseDouble(myOrder.getShippinCharge());
                                            OfflineFragment.this.orderCount++;
                                        }
                                    } else if (OfflineFragment.this.saleCount == -30) {
                                        Date orderdate4 = new SimpleDateFormat("yyyy-MM-dd").parse(myOrder.getCreatedAt());
                                        String currentString4 = new SimpleDateFormat("yyyy-MM-dd").format(current);
                                        Date currentDate2 = new SimpleDateFormat("yyyy-MM-dd").parse(currentString4);
                                        if (currentDate2.equals(orderdate4) || currentDate2.before(orderdate4)) {
                                            OfflineFragment.this.amountDelivered += Double.parseDouble(myOrder.getAmount());
//                                            OfflineFragment.this.earning += Double.parseDouble(myOrder.getShippinCharge());
                                            OfflineFragment.this.orderCount++;
                                        }
                                    }
                                }
                                i++;
                                res2 = res;
                                jsonArray2 = jsonArray;
                                jsonObject12 = jsonObject1;
                            }


                            OfflineFragment.this.binding.sales.setText("₹ " + OfflineFragment.this.decim.format(OfflineFragment.this.amountDelivered) + "");
//                            OfflineFragment.this.binding.floatingCash.setText("₹ " +floatingcash);
//                            OfflineFragment.this.binding.earning.setText("₹ " + OfflineFragment.this.decim.format(OfflineFragment.this.earning) + "");
                            binding.rejectedOrders.setText(OfflineFragment.this.orderCount + "");


                            binding.salesProgressBar.setVisibility(View.GONE);
                            binding.earningProgressBar.setVisibility(View.GONE);
                            binding.ordersProgressBar.setVisibility(View.GONE);
                            binding.rejectedProgressBar.setVisibility(View.GONE);
                            binding.walletAmountProgress.setVisibility(View.GONE);
                            binding.floatingProgressBar.setVisibility(View.GONE);
                            binding.sales.setVisibility(View.VISIBLE);
                            binding.earning.setVisibility(View.VISIBLE);
                            binding.floatingCash.setVisibility(View.VISIBLE);
                            binding.rejectedOrders.setVisibility(View.VISIBLE);
                            binding.walletAmount.setVisibility(View.VISIBLE);
                        }
                        loadTransactionList();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getLoginHour() {
        this.loginHour = 0.0d;
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit1().create(MyApi.class);
        Call<JsonObject> call = myApi.getLoginHour(deliveryPartner.getId(), "Get");
        call.enqueue(new Callback<JsonObject>() { // from class: digi.coders.capsicodeliverypartner.fragment.OfflineFragment.10
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonObject> call2, Response<JsonObject> response) {
                String res;
                JSONObject jsonObject1;
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject12 = new JSONObject(new Gson().toJson((JsonElement) response.body()));
                        String res2 = jsonObject12.getString("res");
                        if (res2.equals("success")) {
                            JSONArray jsonArray1 = jsonObject12.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(new Date());
                            calendar.add(6, OfflineFragment.this.saleCount);
                            Date current = calendar.getTime();
                            int i = 0;
                            while (i < jsonArray1.length()) {
                                JSONObject jsonObject = jsonArray1.getJSONObject(i);
                                if (jsonObject.getString("date_time").equalsIgnoreCase("")) {
                                    jsonObject1 = jsonObject12;
                                    res = res2;
                                } else if (OfflineFragment.this.saleCount == 777) {
                                    if (!jsonObject.getString("duration").equalsIgnoreCase("")) {
                                        OfflineFragment.this.loginHour += Double.parseDouble(jsonObject.getString("duration"));
                                        jsonObject1 = jsonObject12;
                                        res = res2;
                                    } else {
                                        jsonObject1 = jsonObject12;
                                        res = res2;
                                    }
                                } else if (OfflineFragment.this.saleCount == 0) {
                                    Date orderdate = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("date_time"));
                                    String currentString = new SimpleDateFormat("yyyy-MM-dd").format(current);
                                    if (!new SimpleDateFormat("yyyy-MM-dd").parse(currentString).equals(orderdate)) {
                                        jsonObject1 = jsonObject12;
                                        res = res2;
                                    } else if (!jsonObject.getString("duration").equalsIgnoreCase("")) {
                                        jsonObject1 = jsonObject12;
                                        res = res2;
                                        OfflineFragment.this.loginHour += Double.parseDouble(jsonObject.getString("duration"));
                                    } else {
                                        jsonObject1 = jsonObject12;
                                        res = res2;
                                    }
                                } else {
                                    jsonObject1 = jsonObject12;
                                    res = res2;
                                    if (OfflineFragment.this.saleCount == -1) {
                                        Date orderdate2 = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("date_time"));
                                        String currentString2 = new SimpleDateFormat("yyyy-MM-dd").format(current);
                                        if (new SimpleDateFormat("yyyy-MM-dd").parse(currentString2).equals(orderdate2) && !jsonObject.getString("duration").equalsIgnoreCase("")) {
                                            OfflineFragment.this.loginHour += Double.parseDouble(jsonObject.getString("duration"));
                                        }
                                    } else if (OfflineFragment.this.saleCount == -7) {
                                        Date orderdate3 = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("date_time"));
                                        String currentString3 = new SimpleDateFormat("yyyy-MM-dd").format(current);
                                        Date currentDate = new SimpleDateFormat("yyyy-MM-dd").parse(currentString3);
                                        if ((currentDate.equals(orderdate3) || currentDate.before(orderdate3)) && !jsonObject.getString("duration").equalsIgnoreCase("")) {
                                            OfflineFragment.this.loginHour += Double.parseDouble(jsonObject.getString("duration"));
                                        }
                                    } else if (OfflineFragment.this.saleCount == -30) {
                                        Date orderdate4 = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("date_time"));
                                        String currentString4 = new SimpleDateFormat("yyyy-MM-dd").format(current);
                                        Date currentDate2 = new SimpleDateFormat("yyyy-MM-dd").parse(currentString4);
                                        if ((currentDate2.equals(orderdate4) || currentDate2.before(orderdate4)) && !jsonObject.getString("duration").equalsIgnoreCase("")) {
                                            OfflineFragment.this.loginHour += Double.parseDouble(jsonObject.getString("duration"));
                                        }
                                    }
                                }
                                i++;
                                jsonObject12 = jsonObject1;
                                res2 = res;
                            }
                            double login = OfflineFragment.this.loginHour;
                            double monuts = login % 60.0d;
                            double hours = login / 60.0d;
                            if (hours >= 1.0d) {
                                OfflineFragment.this.binding.loginHours.setText(OfflineFragment.this.decim.format(hours) + ":" + OfflineFragment.this.decim.format(monuts) + " Hrs");
                            } else {
                                OfflineFragment.this.binding.loginHours.setText(OfflineFragment.this.decim.format(monuts) + " mins");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonObject> call2, Throwable t) {
            }
        });
    }

    public void getIncentive() {
        totalIncentiveAmount=0;
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit1().create(MyApi.class);
        Call<JsonObject> call = myApi.getIncentive(deliveryPartner.getId(), "Get");
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
                            ArrayList<IncentiveModel> arrayList=new ArrayList<>();
                            int j=0;
                            for(int i=0;i<Integer.parseInt(jsonArray1.getString("no_count"));i++){
                                if(Arrays.asList(jsonArray1.getString("breakpoints_order").split(",")).contains((i+1)+"")){
                                    arrayList.add(new IncentiveModel(jsonArray1.getString("breakpoint_amount").split(",")[j],(i+1)+""));

                                    if(totalDeliveredOrder>=(i+1)) {
                                        if (!arrayList.get(i).getAmount().equalsIgnoreCase("")) {
                                            totalIncentiveAmount = totalIncentiveAmount + Integer.parseInt(arrayList.get(i).getAmount());
                                        }
                                        if (totalIncentiveAmount != 0) {
                                            txtIncentive.setText("Congratulations! You earned \u20b9" + totalIncentiveAmount + " as incentive");
                                            binding.incentive.setText("\u20b9" + totalIncentiveAmount + "");
                                           txtIncentive.setVisibility(View.VISIBLE);
                                        } else {
                                            txtIncentive.setVisibility(View.GONE);
                                            binding.incentive.setText("\u20b90");
                                        }
                                    }
                                    j++;
                                }else{
                                    arrayList.add(new IncentiveModel("",(i+1)+""));
                                }
                            }

                            binding.noOfOrders.setText(totalDeliveredOrder+"");
                            binding.incentiveList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                            binding.incentiveList.setHasFixedSize(true);
                            binding.incentiveList.setAdapter(new IncelistAdapterAdapter(arrayList,getActivity()));
                           binding.cardIncentive.setVisibility(View.VISIBLE);
                        }else{
                            binding.cardIncentive.setVisibility(View.GONE);
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

    private void getDetails() {
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.getVaccination(deliveryPartner.getId());
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.fragment.OfflineFragment.11
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String res = jsonObject.getString("res");
                        if (res.equals("success")) {
                            JSONArray jsonArray1 = jsonObject.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                            if (jsonObject1.getString(NotificationCompat.CATEGORY_STATUS).equalsIgnoreCase("Pending")) {
                                OfflineFragment.this.binding.vaccinationMsg.setText("Vaccination Approval Pending");
                            } else if (jsonObject1.getString(NotificationCompat.CATEGORY_STATUS).equalsIgnoreCase("Cancelled")) {
                                OfflineFragment.this.binding.vaccinationMsg.setText("Vaccination Approval Cancelled");
                            } else if (jsonObject1.getString(NotificationCompat.CATEGORY_STATUS).equalsIgnoreCase("Approved")) {
                                OfflineFragment.this.binding.vaccinationMsg.setText("Vaccination Approval Done");
                            } else {
                                OfflineFragment.this.binding.vaccinationMsg.setText("Submit vaccination proof");
                            }
                            return;
                        }
                        OfflineFragment.this.binding.vaccinationMsg.setText("Submit vaccination proof");
                    } catch (Exception e) {
                        OfflineFragment.this.binding.vaccinationMsg.setText("Submit vaccination proof");
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                OfflineFragment.this.binding.vaccinationMsg.setText("Submit vaccination proof");
            }
        });
    }

    public void loadTransactionList() {
        earning = 0.0d;
        todayEarning=0;
        todayDeliveredAMount=0;
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.getTransactionAllHistory(deliveryPartner.getId());
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.fragment.WalletFragment.2
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
                            JSONArray jsonArray1 = jsonObject.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(new Date());
                            calendar.add(6, OfflineFragment.this.saleCount);
                            Date current = calendar.getTime();

                            for (int i = 0; i < jsonArray1.length(); i++) {
                                Transaction transaction = (Transaction) new Gson().fromJson(jsonArray1.getJSONObject(i).toString(),  Transaction.class);


                                if(transaction.getMsg().equalsIgnoreCase("Delivery amount")){

                                    Date orderdate = new SimpleDateFormat("yyyy-MM-dd").parse(transaction.getCreatedAt());
                                    String currentString = new SimpleDateFormat("yyyy-MM-dd").format(current);
                                    if (new SimpleDateFormat("yyyy-MM-dd").parse(currentString).equals(orderdate)) {
                                        todayDeliveredAMount += Double.parseDouble(transaction.getAmount());
                                        todayEarning += Double.parseDouble(transaction.getAmount());

                                    }
                                }
                                if(transaction.getMsg().equalsIgnoreCase("Delivery amount")) {

                                    if (OfflineFragment.this.saleCount == 777) {
                                        OfflineFragment.this.earning =earning+ Double.parseDouble(transaction.getAmount());
                                    } else if (OfflineFragment.this.saleCount == 0) {
                                        Date orderdate = new SimpleDateFormat("yyyy-MM-dd").parse(transaction.getCreatedAt());
                                        String currentString = new SimpleDateFormat("yyyy-MM-dd").format(current);
                                        if (new SimpleDateFormat("yyyy-MM-dd").parse(currentString).equals(orderdate)) {
                                            OfflineFragment.this.earning =earning+ Double.parseDouble(transaction.getAmount());
                                        }
                                    } else {
                                        if (OfflineFragment.this.saleCount == -1) {
                                            Date orderdate2 = new SimpleDateFormat("yyyy-MM-dd").parse(transaction.getCreatedAt());
                                            String currentString2 = new SimpleDateFormat("yyyy-MM-dd").format(current);
                                            if (new SimpleDateFormat("yyyy-MM-dd").parse(currentString2).equals(orderdate2)) {
                                                OfflineFragment.this.earning =earning+ Double.parseDouble(transaction.getAmount());
                                            }
                                        } else if (OfflineFragment.this.saleCount == -7) {
                                            Date orderdate3 = new SimpleDateFormat("yyyy-MM-dd").parse(transaction.getCreatedAt());
                                            String currentString3 = new SimpleDateFormat("yyyy-MM-dd").format(current);
                                            Date currentDate = new SimpleDateFormat("yyyy-MM-dd").parse(currentString3);
                                            if ((currentDate.equals(orderdate3) || currentDate.before(orderdate3))) {
                                                OfflineFragment.this.earning =earning+ Double.parseDouble(transaction.getAmount());
                                            }
                                        } else if (OfflineFragment.this.saleCount == -30) {
                                            Date orderdate4 = new SimpleDateFormat("yyyy-MM-dd").parse(transaction.getCreatedAt());
                                            String currentString4 = new SimpleDateFormat("yyyy-MM-dd").format(current);
                                            Date currentDate2 = new SimpleDateFormat("yyyy-MM-dd").parse(currentString4);
                                            if ((currentDate2.equals(orderdate4) || currentDate2.before(orderdate4))) {
                                                OfflineFragment.this.earning =earning+ Double.parseDouble(transaction.getAmount());
                                            }
                                        }
                                    }
                                }
                            }

                            binding.orderEarning.setText("\u20b9"+todayEarning+"");
                            binding.earning.setText("\u20b9"+new DecimalFormat("##.#").format(earning));

//                            Toast.makeText(getActivity(), saleCount+"", Toast.LENGTH_SHORT).show();

                        }

                        if(saleCount==0) {
                            getIncentive();
                        }

                    } catch (Exception e) {
//                        Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
//                Toast.makeText(getActivity(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
