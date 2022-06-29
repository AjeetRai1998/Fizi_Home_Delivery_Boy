package digi.coders.capsicodeliverypartner.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cashfree.pg.CFPaymentService;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.exception.AppNotFoundException;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.PaymentApp;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.activity.CashTransferActivity;
import digi.coders.capsicodeliverypartner.activity.KYCActivity;
import digi.coders.capsicodeliverypartner.activity.KYCStatusActivity;
import digi.coders.capsicodeliverypartner.activity.WithDrawFormActivity;
import digi.coders.capsicodeliverypartner.adapter.FloatingCashAdapter;
import digi.coders.capsicodeliverypartner.adapter.TransactionAdapter;
import digi.coders.capsicodeliverypartner.databinding.FragmentWalletBinding;
import digi.coders.capsicodeliverypartner.databinding.PaymentOptionLayoutBinding;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.helper.Refresh;
import digi.coders.capsicodeliverypartner.helper.ShowProgress;
import digi.coders.capsicodeliverypartner.model.CashfreeDetails;
import digi.coders.capsicodeliverypartner.model.DeliveryPartner;
import digi.coders.capsicodeliverypartner.model.KycData;
import digi.coders.capsicodeliverypartner.model.MyOrder;
import digi.coders.capsicodeliverypartner.model.Transaction;
import digi.coders.capsicodeliverypartner.singletask.SingleTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes5.dex */
public class WalletFragment extends Fragment implements Refresh , PaymentStatusListener {
    public static String WalletPageStatus = "";
    private static CashfreeDetails details;
    public static String orderAmount;
    public static Refresh refresh;
    ArrayList<MyOrder> arrayList = new ArrayList<>();
    FragmentWalletBinding binding;
    private List<Transaction> list=new ArrayList<>();
    String orderAmout;
    String orderId;
    String paymentMode;
    String referenceid;
    String signature;
    private SingleTask singleTask;
    String txMsg;
    String txStatus;
    String txTime;
    String type;
    View view;
    int page=0;
    int scrollStatus=0,scrollStatusForData=0;
    FloatingCashAdapter floatingCashAdapter;
    TransactionAdapter transactionAdapter;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentWalletBinding inflate = FragmentWalletBinding.inflate(inflater, container, false);
        this.binding = inflate;
        return inflate.getRoot();
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        this.singleTask = (SingleTask) getActivity().getApplication();
        WalletFragment.this.binding.transactionList.setLayoutManager(new LinearLayoutManager(WalletFragment.this.getActivity(), RecyclerView.VERTICAL, false));
        transactionAdapter=new TransactionAdapter(WalletFragment.this.list);
        WalletFragment.this.binding.transactionList.setAdapter(transactionAdapter);
        loadTransactionList();
        refresh = this;
        Glide.with(getActivity()).load(R.raw.loading).into(binding.loading);
        Glide.with(getActivity()).load(R.raw.loading).into(binding.loading1);
        getUpiApp();
        if (WalletPageStatus.equalsIgnoreCase("f")) {
            this.binding.withdrawMoney.setVisibility(View.GONE);
            this.binding.earnLayout.setVisibility(View.GONE);
            this.binding.fLayout.setVisibility(View.VISIBLE);
            this.binding.codHistory.setVisibility(View.VISIBLE);
            this.binding.transactionHistory.setVisibility(View.GONE);
            loadDeliveredOrderList();
        } else {
            this.binding.withdrawMoney.setVisibility(View.VISIBLE);
            this.binding.earnLayout.setVisibility(View.VISIBLE);
            this.binding.fLayout.setVisibility(View.GONE);
            this.binding.codHistory.setVisibility(View.GONE);
            this.binding.transactionHistory.setVisibility(View.VISIBLE);
        }

        binding.seekbar.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                }
        );

        Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy");
        sdf.format(new Date());
        SimpleDateFormat sdf_ = new SimpleDateFormat("EEEE");
        Date date = new Date();
        String dayName = sdf_.format(date);
        this.binding.cashLimit.setText(sdf.format(new Date()));
        this.binding.withdrawMoney.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.fragment.WalletFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ShowProgress.getShowProgress(WalletFragment.this.getActivity()).show();
                String ven = WalletFragment.this.singleTask.getValue("boy");
                DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
                MyApi myApi = (MyApi) WalletFragment.this.singleTask.getRetrofit().create(MyApi.class);
                Call<JsonArray> call = myApi.kycStatus(deliveryPartner.getId());
                call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.fragment.WalletFragment.1.1
                    @Override // retrofit2.Callback
                    public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String res = jsonObject.getString("res");
                                jsonObject.getString("message");
                                if (res.equals("success")) {
                                    ShowProgress.getShowProgress(WalletFragment.this.getActivity()).hide();
                                    JSONObject jsonObject1 = jsonObject.getJSONObject(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                                    KycData kycData = (KycData) new Gson().fromJson(jsonObject1.toString(),  KycData.class);
                                    if (kycData.getIsStatus().equals("pending")) {
                                        WalletFragment.this.startActivity(new Intent(WalletFragment.this.getActivity(), KYCStatusActivity.class));
                                    } else if (kycData.getIsStatus().equals("verified")) {
                                        WalletFragment.this.startActivity(new Intent(WalletFragment.this.getActivity(), WithDrawFormActivity.class));
                                    } else {
                                        Toast.makeText(WalletFragment.this.getActivity(), "You kyc reject by Admin Retry again ?", Toast.LENGTH_LONG).show();
                                        WalletFragment.this.startActivity(new Intent(WalletFragment.this.getActivity(), KYCActivity.class));
                                    }
                                    return;
                                }
                                WalletFragment.this.startActivity(new Intent(WalletFragment.this.getActivity(), KYCActivity.class));
                                ShowProgress.getShowProgress(WalletFragment.this.getActivity()).hide();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override // retrofit2.Callback
                    public void onFailure(Call<JsonArray> call2, Throwable t) {
                        ShowProgress.getShowProgress(WalletFragment.this.getActivity()).hide();
                        Toast.makeText(WalletFragment.this.getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        binding.viewCashTransferHistory.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.fragment.WalletFragment.4.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                WalletFragment.this.startActivity(new Intent(WalletFragment.this.getActivity(), CashTransferActivity.class));
            }
        });

        binding.mainLayout.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // on scroll change we are checking when users scroll as bottom.
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if(scrollStatusForData==0) {
                        if (scrollStatus == 0) {
                            scrollStatus=1;
                            if (WalletPageStatus.equalsIgnoreCase("f")) {
                                page = page + 50;
                                loadDeliveredOrderList();
                            }else{
                                page = page + 50;
                                loadTransactionList();
                            }
                        }
                    }
                }
            }
        });
        WalletFragment.this.binding.floatingCashList.setLayoutManager(new LinearLayoutManager(WalletFragment.this.getActivity(), RecyclerView.VERTICAL, false));
        floatingCashAdapter=new FloatingCashAdapter(WalletFragment.this.arrayList);
        WalletFragment.this.binding.floatingCashList.setAdapter(floatingCashAdapter);



    }
    String cashWallet="0";
    /* JADX INFO: Access modifiers changed from: private */
    public void loadTransactionList() {
        binding.loading1.setVisibility(View.VISIBLE);
        ShowProgress.getShowProgress(getActivity()).show();
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.getTransactionHistory(deliveryPartner.getId(),page+"");
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
                        String wallet = jsonObject.getString("wallet");
                        Log.e("sdsd", jsonArray.toString());
                         cashWallet = jsonObject.getString("cashwallet");
                        String cashlimit = jsonObject.getString("cashwallet_limit");
                        WalletFragment.this.binding.money.setText("₹ " + wallet);
                        if (cashlimit.equals("")) {
                            WalletFragment.this.binding.limit.setText("\u20b90");
                            WalletFragment.this.binding.cashWallet.setText("\u20b90");
                        } else {
                            WalletFragment.this.binding.limit.setText("\u20b9" + cashlimit);
                            WalletFragment.this.binding.cashWallet.setText("\u20b9" +(Double.parseDouble(cashlimit)-Double.parseDouble(cashWallet)) );
                        }

                        binding.seekbar.setMax(Integer.parseInt(cashlimit));
                        binding.seekbar.setProgress((int)Double.parseDouble(cashWallet));
                        binding.inHand.setText("\u20b9"+cashWallet);
                        handleTansfer(cashlimit, cashWallet);
                        if (res.equals("success")) {
                            ShowProgress.getShowProgress(WalletFragment.this.getActivity()).hide();
                            JSONArray jsonArray1 = jsonObject.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                            list = new ArrayList();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                Transaction transaction = (Transaction) new Gson().fromJson(jsonArray1.getJSONObject(i).toString(),  Transaction.class);
                                WalletFragment.this.list.add(transaction);
                            }
                            transactionAdapter.addData(list);
                            scrollStatus=0;
                            return;
                        }
                        ShowProgress.getShowProgress(WalletFragment.this.getActivity()).hide();
                        Toast.makeText(WalletFragment.this.getActivity(), msg, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                binding.loading1.setVisibility(View.GONE);
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                binding.loading1.setVisibility(View.GONE);
                ShowProgress.getShowProgress(WalletFragment.this.getActivity()).hide();
                Toast.makeText(WalletFragment.this.getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadDeliveredOrderList() {

        binding.loading.setVisibility(View.VISIBLE);
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.getOrder(deliveryPartner.getId(), "Delivered",page+"");
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.fragment.WalletFragment.3
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String res = jsonObject1.getString("res");
                        if (res.equals("success")) {
                            JSONArray jsonArray1 = jsonObject1.getJSONArray(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                           arrayList=new ArrayList<>();
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                MyOrder myOrder = (MyOrder) new Gson().fromJson(jsonArray1.getJSONObject(i).toString(),  MyOrder.class);
                                if (myOrder.getMethod().equalsIgnoreCase("cod")) {
                                    WalletFragment.this.arrayList.add(myOrder);
                                }
                            }

                            floatingCashAdapter.addData(arrayList);
                            scrollStatus=0;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    binding.loading.setVisibility(View.GONE);
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                binding.loading.setVisibility(View.GONE);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: digi.coders.capsicodeliverypartner.fragment.WalletFragment$4  reason: invalid class name */
    /* loaded from: classes5.dex */

    CashfreeDetails unused;
    public class AnonymousClass4 implements View.OnClickListener {
        AnonymousClass4() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(WalletFragment.this.getActivity());
            final AlertDialog alertDialog = builder.create();
            LayoutInflater inflater = LayoutInflater.from(WalletFragment.this.getActivity());
            View dialogView = inflater.inflate(R.layout.transfer_money_layout, (ViewGroup) null);
            TextView submit = (TextView) dialogView.findViewById(R.id.submit);
            ImageView close = (ImageView) dialogView.findViewById(R.id.close);
            final EditText money = (EditText) dialogView.findViewById(R.id.money);
            ProgressBar progressBar = (ProgressBar) dialogView.findViewById(R.id.loadProgress);

            RadioGroup radioGroup=dialogView.findViewById(R.id.radioType);
            money.setText(  cashWallet);
            radioGroup.setOnCheckedChangeListener(
                    new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            if(checkedId== R.id.radio_normal){
                                money.setText("");
                            }else if(checkedId==R.id.radio_influencer){
                                money.setText(  cashWallet);
                            }

                        }
                    }
            );

            alertDialog.setCanceledOnTouchOutside(false);
            close.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.fragment.WalletFragment.4.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    alertDialog.dismiss();
                }
            });

            submit.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.fragment.WalletFragment.4.3
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    WalletFragment.orderAmount = money.getText().toString();
                    if (WalletFragment.orderAmount.isEmpty()) {
                        money.setError("Please Enter amount of money");
                        money.requestFocus();
                        return;
                    }
                    alertDialog.dismiss();
                    ShowProgress.getShowProgress(WalletFragment.this.getActivity()).show();
                    String ven = WalletFragment.this.singleTask.getValue("boy");
                    DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
                    MyApi myApi = (MyApi) WalletFragment.this.singleTask.getRetrofit().create(MyApi.class);
                    Call<JsonArray> call = myApi.codMoneyTransfer(deliveryPartner.getId(), WalletFragment.orderAmount);
                    call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.fragment.WalletFragment.4.3.1
                        @Override // retrofit2.Callback
                        public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                            if (response.isSuccessful()) {
                                try {
                                    JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String res = jsonObject.getString("res");
                                    String msg = jsonObject.getString("message");
                                    Log.e("sds", response.toString());
                                    if (res.equals("success")) {
                                        ShowProgress.getShowProgress(WalletFragment.this.getActivity()).hide();
                                        Toast.makeText(WalletFragment.this.getActivity(), msg, Toast.LENGTH_LONG).show();
                                        JSONObject jsonObject1 = jsonObject.getJSONObject(Constants.ScionAnalytics.MessageType.DATA_MESSAGE);
                                         unused = WalletFragment.details = (CashfreeDetails) new Gson().fromJson(jsonObject1.toString(),  CashfreeDetails.class);
//                                        WalletFragment.this.initiatePayment();

                                        paymentOption(null,WalletFragment.orderAmount);


                                    } else {
                                        ShowProgress.getShowProgress(WalletFragment.this.getActivity()).hide();
                                        Toast.makeText(WalletFragment.this.getActivity(), msg, Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override // retrofit2.Callback
                        public void onFailure(Call<JsonArray> call2, Throwable t) {
                            ShowProgress.getShowProgress(WalletFragment.this.getActivity()).hide();
                            Toast.makeText(WalletFragment.this.getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            alertDialog.setView(dialogView);
            alertDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTansfer(String cashlimit, String cashWallet) {
        this.binding.transferMoney.setOnClickListener(new AnonymousClass4());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initiatePayment() {
        Log.e("ew", "sdsd");
        Map<String, String> params = new HashMap<>();
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        Log.e("dssd", details.getAppId());
        params.put(CFPaymentService.PARAM_APP_ID, details.getAppId());
        params.put(CFPaymentService.PARAM_ORDER_ID, String.valueOf(details.getTxtId()));
        params.put(CFPaymentService.PARAM_ORDER_AMOUNT, orderAmount);
        params.put(CFPaymentService.PARAM_ORDER_NOTE, "Transfer Money");
        params.put(CFPaymentService.PARAM_CUSTOMER_NAME, deliveryPartner.getName());
        params.put(CFPaymentService.PARAM_CUSTOMER_PHONE, deliveryPartner.getMobile());
        params.put(CFPaymentService.PARAM_CUSTOMER_EMAIL, deliveryPartner.getEmail());
        params.put(CFPaymentService.PARAM_ORDER_CURRENCY, "INR");
        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
        cfPaymentService.setOrientation(0);
        cfPaymentService.doPayment(getActivity(), params, details.getToken(), details.getEnv(), "#f7b614", "#FFFFFF", false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("m", "API Response : ");
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    if (bundle.getString(key) != null) {
                        Log.e("sdsd", bundle.toString() + "");
                        Log.d("resp", key + " : " + bundle.getString(key));
                        this.paymentMode = bundle.getString("paymentMode");
                        this.orderId = bundle.getString(CFPaymentService.PARAM_ORDER_ID);
                        this.txTime = bundle.getString("txTime");
                        this.referenceid = bundle.getString("referenceId");
                        this.type = bundle.getString("type");
                        this.txMsg = bundle.getString("txMsg");
                        this.signature = bundle.getString("signature");
                        this.orderAmout = bundle.getString(CFPaymentService.PARAM_ORDER_AMOUNT);
                        this.txStatus = bundle.getString("txStatus");
                    }
                }
            }
            updatePaymentStatus(this.referenceid, this.txStatus, bundle, this.orderId, this.txMsg);
        }
    }

    private void updatePaymentStatus(String referenceid, String status, Bundle bundle, String orderId, String s) {
        ShowProgress.getShowProgress(getActivity()).show();
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.codeMoneyTransferUpdate(deliveryPartner.getId(), status, bundle, String.valueOf(details.getTxtId()));
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.fragment.WalletFragment.5
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String res = jsonObject.getString("res");
                        String msg = jsonObject.getString("message");
                        Log.e("sdsd", response.toString());
                        if (res.equals("success")) {
                            ShowProgress.getShowProgress(WalletFragment.this.getActivity()).hide();
                            Toast.makeText(WalletFragment.this.getActivity(), msg, Toast.LENGTH_LONG).show();
                            WalletFragment.this.loadTransactionList();
                            bottomSheetDialog.dismiss();
                        } else {
                            ShowProgress.getShowProgress(WalletFragment.this.getActivity()).hide();
                            Toast.makeText(WalletFragment.this.getActivity(), msg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                ShowProgress.getShowProgress(WalletFragment.this.getActivity()).hide();
                Toast.makeText(WalletFragment.this.getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override // digi.coders.capsicodeliverypartner.helper.Refresh
    public void onRefresh() {
        loadTransactionList();
    }
     BottomSheetDialog bottomSheetDialog;
    private void paymentOption(View v,String amount) {
        bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.myBottomSheetDialogTheme);
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.payment_option_layout, view.findViewById(R.id.bottom_sheet_container), false);
        bottomSheetDialog.setContentView(view1);
        PaymentOptionLayoutBinding binding = PaymentOptionLayoutBinding.bind(view1);
//        fetchWalletAmount(binding);


        binding.layoutPhone.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            boolean isInstalled = false;
                            for(int i=0;i<pkgAppsList.size();i++){
                                ResolveInfo resolveInfo = (ResolveInfo) pkgAppsList.get(i);
                                if(resolveInfo.activityInfo.packageName.equalsIgnoreCase("com.phonepe.app")){
                                    isInstalled=true;
                                    break;
                                }
                            }
                            if(isInstalled) {
                                UPIPayment(amount, PaymentApp.PHONE_PE);
                            }else{
                                Toast.makeText(getActivity(), "Phonepe not found in your device... Please Install and continue", Toast.LENGTH_SHORT).show();

                            }
                        }catch (AppNotFoundException exception) {
                            Toast.makeText(getActivity(), "Phonepe not found in your device... Please Install and continue", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
        );
        binding.layoutGpay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            boolean isInstalled = false;
                            for(int i=0;i<pkgAppsList.size();i++){
                                ResolveInfo resolveInfo = (ResolveInfo) pkgAppsList.get(i);
                                if(resolveInfo.activityInfo.packageName.equalsIgnoreCase("com.google.android.apps.nbu.paisa.user")){
                                    isInstalled=true;
                                    break;
                                }
                            }
                            if(isInstalled) {
                                UPIPayment(amount, PaymentApp.GOOGLE_PAY);
                            }else{
                                Toast.makeText(getActivity(), "Googlepay not found in your device... Please Install and continue", Toast.LENGTH_SHORT).show();

                            }
                        }catch (AppNotFoundException exception) {
                            Toast.makeText(getActivity(), "Googlepay not found in your device... Please Install and continue", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
        );
        binding.layoutPaytm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            boolean isInstalled = false;
                            for(int i=0;i<pkgAppsList.size();i++){
                                ResolveInfo resolveInfo = (ResolveInfo) pkgAppsList.get(i);
                                if(resolveInfo.activityInfo.packageName.equalsIgnoreCase("net.one97.paytm")){
                                    isInstalled=true;
                                    break;
                                }
                            }
                            if(isInstalled) {
                                UPIPayment(amount, PaymentApp.PAYTM);
                            }else{
                                Toast.makeText(getActivity(), "Paytm not found in your device... Please Install and continue", Toast.LENGTH_SHORT).show();

                            }
                        }catch (AppNotFoundException exception) {
                            Toast.makeText(getActivity(), "Paytm not found in your device... Please Install and continue", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
        );
        binding.layoutUPI.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            boolean isInstalled = false;
                            for(int i=0;i<pkgAppsList.size();i++){
                                ResolveInfo resolveInfo = (ResolveInfo) pkgAppsList.get(i);
                                if(resolveInfo.activityInfo.packageName.equalsIgnoreCase("in.org.npci.upiapp")){
                                    isInstalled=true;
                                    break;
                                }
                            }
                            if(isInstalled) {
                                UPIPayment(amount, PaymentApp.BHIM_UPI);
                            }else{
                                Toast.makeText(getActivity(), "Paytm not found in your device... Please Install and continue", Toast.LENGTH_SHORT).show();

                            }
                        }catch (AppNotFoundException exception) {
                            Toast.makeText(getActivity(), "Paytm not found in your device... Please Install and continue", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
        );
        binding.layoutCashfree.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        initiatePayment();
                    }
                }
        );


        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.show();

    }

    List pkgAppsList;
    void getUpiApp(){
        PackageManager packageManager = getActivity().getPackageManager();
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_DEFAULT);
        mainIntent.addCategory(Intent.CATEGORY_BROWSABLE);
        mainIntent.setAction(Intent.ACTION_VIEW);
        Uri uri1 = new Uri.Builder().scheme("upi").authority("pay").build();
        mainIntent.setData(uri1);
        pkgAppsList = getActivity().getPackageManager().queryIntentActivities(mainIntent, 0);

    }

    EasyUpiPayment easyUpiPayment;

    public void UPIPayment(String amount,PaymentApp paymentApp) throws AppNotFoundException{

        try {
            EasyUpiPayment.Builder  builder= new EasyUpiPayment.Builder(getActivity())
                    .with(paymentApp)
                    .setPayeeVpa("Q915573461@ybl")
                    .setPayeeName("Capsico")
                    .setTransactionId(System.currentTimeMillis()+"")
                    .setTransactionRefId(System.currentTimeMillis()+"")
                    .setPayeeMerchantCode("")
                    .setDescription("Capsico Online Ordering")
                    .setAmount(Double.parseDouble(amount)+"");


            // Build instance
            easyUpiPayment = builder.build();

            // Register Listener for Events
            easyUpiPayment.setPaymentStatusListener(WalletFragment.this);

            // Start payment / transaction
            easyUpiPayment.startPayment();
        } catch (AppNotFoundException exception) {
            if(paymentApp==PaymentApp.PAYTM) {
                Toast.makeText(getActivity(), "Paytm not found in your device... Please Install and continue", Toast.LENGTH_SHORT).show();
            }else if(paymentApp==PaymentApp.PHONE_PE) {
                Toast.makeText(getActivity(), "Phonepe not found in your device... Please Install and continue", Toast.LENGTH_SHORT).show();
            }else if(paymentApp==PaymentApp.GOOGLE_PAY) {
                Toast.makeText(getActivity(), "Googlepay not found in your device... Please Install and continue", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onTransactionCompleted(@NonNull TransactionDetails transactionDetails) {
        switch (transactionDetails.getTransactionStatus()) {
            case SUCCESS:
                onTransactionSuccess();
                break;
            case FAILURE:
                onTransactionFailed();
                break;
            case SUBMITTED:
                onTransactionSubmitted();
                break;
        }
    }
    @Override
    public void onTransactionCancelled() {
        Toast.makeText(getActivity(),"Cancelled by user",Toast.LENGTH_LONG).show();
    }

    private void onTransactionSuccess() {
        referenceid=System.currentTimeMillis()+"";
        orderId=System.currentTimeMillis()+"";
        updatePaymentStatus(referenceid, "SUCCESS",new Bundle(), orderId, "Order");
//        updatePaymentStatus(this.referenceid, this.txStatus, bundle, this.orderId, this.txMsg);

    }

    private void onTransactionSubmitted() {
        // Payment Pending
        Toast.makeText(getActivity(),"Pending | Submitted",Toast.LENGTH_LONG).show();

    }

    private void onTransactionFailed() {
        // Payment Failed
        Toast.makeText(getActivity(),"Failed",Toast.LENGTH_LONG).show();

    }

}
