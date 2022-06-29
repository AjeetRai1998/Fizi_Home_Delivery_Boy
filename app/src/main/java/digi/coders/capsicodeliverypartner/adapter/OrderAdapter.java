package digi.coders.capsicodeliverypartner.adapter;

import static digi.coders.capsicodeliverypartner.fragment.OfflineFragment.todayDeliveredAMount;
import static digi.coders.capsicodeliverypartner.fragment.OfflineFragment.totalDeliveredOrder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ncorti.slidetoact.SlideToActView;

import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.activity.OrderDetailsActivity;
import digi.coders.capsicodeliverypartner.activity.OrderStatusActivity;
import digi.coders.capsicodeliverypartner.activity.OrderSummaryActivity;
import digi.coders.capsicodeliverypartner.adapter.ProductListAdapter;
import digi.coders.capsicodeliverypartner.databinding.CancelReasonLayoutBinding;
import digi.coders.capsicodeliverypartner.databinding.DeliveredOrderLayoutBinding;
import digi.coders.capsicodeliverypartner.databinding.NewOrdersLayoutBinding;
import digi.coders.capsicodeliverypartner.databinding.OngoingOrderLayoutBinding;
import digi.coders.capsicodeliverypartner.databinding.ProductDesignBinding;
import digi.coders.capsicodeliverypartner.databinding.ProductListBottomSheetBinding;
import digi.coders.capsicodeliverypartner.fragment.DeliveredFragment;
import digi.coders.capsicodeliverypartner.fragment.DriversFragment;
import digi.coders.capsicodeliverypartner.fragment.HomeFragment;
import digi.coders.capsicodeliverypartner.fragment.NewOrderFragment;
import digi.coders.capsicodeliverypartner.fragment.OngoingFragment;
import digi.coders.capsicodeliverypartner.helper.Constraints;
import digi.coders.capsicodeliverypartner.helper.FunctionClass;
import digi.coders.capsicodeliverypartner.helper.MyApi;
import digi.coders.capsicodeliverypartner.helper.NotificationUtils;
import digi.coders.capsicodeliverypartner.helper.ProgressDisplay;
import digi.coders.capsicodeliverypartner.helper.SharedPrefManagerLocation;
import digi.coders.capsicodeliverypartner.model.DeliveryPartner;
import digi.coders.capsicodeliverypartner.model.IncentiveModel;
import digi.coders.capsicodeliverypartner.model.MyOrder;
import digi.coders.capsicodeliverypartner.model.Orderproduct;
import digi.coders.capsicodeliverypartner.model.User;
import digi.coders.capsicodeliverypartner.singletask.SingleTask;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* loaded from: classes3.dex */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyHolder> {
    ArrayList<CountDownTimer> arrayList;
    private Activity ctx;
    FragmentManager fm;
    GetPosition getPosition;
    List<String> idList = new ArrayList();
    private MyOrder myOrder;
    private List<MyOrder> myOrderList;
    ProgressDisplay progressDisplay;
    private SingleTask singleTask;
    private int status;

    public interface GetPosition {
        void click();
    }

    public OrderAdapter(int status) {
        this.status = status;
    }

    public OrderAdapter(int status, List<MyOrder> myOrderList, SingleTask singleTask, Activity ctx, FragmentManager fm) {
        this.status = status;
        this.myOrderList = myOrderList;
        this.singleTask = singleTask;
        this.ctx = ctx;
        this.fm = fm;
        this.progressDisplay = new ProgressDisplay(ctx);
        this.arrayList = new ArrayList<>(myOrderList.size());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        int i = this.status;
        if (i == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_orders_layout, parent, false);
        } else if (i == 2) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ongoing_order_layout, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivered_order_layout, parent, false);
        }
        return new MyHolder(view);
    }

    public void onBindViewHolder(MyHolder holder, int position) {
        MyOrder myOrder2;
        char c;
        final MyHolder myHolder = holder;
        final int i = position;
        int i2 = this.status;
        if (i2 == 1) {
            MyOrder myOrder3 = this.myOrderList.get(i);
            if (this.myOrderList != null) {
                myHolder.binding1.orderid.setText("Order Time - " + myOrder3.getModifiedAt().split(" ")[0]);
                myHolder.binding1.paymentType.setText(myOrder3.getMethod());
                myHolder.binding1.totalAmount.setText("₹" + myOrder3.getAmount());
                myHolder.binding1.date.setText(myOrder3.getCreatedAt().split(" ")[0]);
                String ven = this.singleTask.getValue("boy");
                if (!((DeliveryPartner) new Gson().fromJson(ven, DeliveryPartner.class)).getIsDefault().equalsIgnoreCase("no")) {
                    myOrder2 = myOrder3;
                    c = 0;
                    myHolder.binding1.lineTime.setVisibility(View.GONE);
                    myHolder.binding1.lineReject.setVisibility(View.GONE);
                    myHolder.binding1.acceptOrder.setText("Assign");
                } else if (myOrder3.getDelivery_boy_status_accepted().equalsIgnoreCase("false")) {
                    try {
                        long milliseconds = (long) (60000.0d * Double.parseDouble(myOrder3.getLefttime()));
                        if (myOrder3.getLefttime().isEmpty() || milliseconds <= 0) {
                            String str = ven;
                            myOrder2 = myOrder3;
                            c = 0;
                            myHolder.binding1.lineTime.setVisibility(View.GONE);
                            myHolder.binding1.acceptOrder.setVisibility(View.GONE);
                            myHolder.binding1.lineReject.setVisibility(View.GONE);
                            myHolder.binding1.notice.setVisibility(View.GONE);
                        } else {
                            ArrayList<CountDownTimer> arrayList2 = this.arrayList;
                            long j = milliseconds;
                            String str2 = ven;
                            myOrder2 = myOrder3;
                            final MyHolder myHolder2 = holder;
                            c = 0;
                            final int i3 = position;
                            try {
                                CountDownTimer r1 = new CountDownTimer(milliseconds, 1) {
                                    public void onTick(long millisUntilFinished) {
                                        DecimalFormat f = new DecimalFormat("00");
                                        long j = (millisUntilFinished / 3600000) % 24;
                                        myHolder2.binding1.timer.setText(f.format((millisUntilFinished / 60000) % 60) + " Min :" + f.format((millisUntilFinished / 1000) % 60) + " Sec");
                                    }

                                    public void onFinish() {
                                        myHolder2.binding1.timer.setText("00:00:00");
                                        cancel();
                                        OrderAdapter.this.orderRejected(i3);
                                    }
                                };
                                arrayList2.add(i, r1.start());
                            } catch (Exception e) {
                            }
                        }
                    } catch (Exception e2) {
                        String str3 = ven;
                        myOrder2 = myOrder3;
                        c = 0;
                    }
                } else {
                    myOrder2 = myOrder3;
                    c = 0;
                }
                StringBuffer st = new StringBuffer("");
                for (int i4 = 0; i4 < myOrder2.getOrderproduct().length; i4++) {
                    st.append(myOrder2.getOrderproduct()[i4].getQty() + " × " + myOrder2.getOrderproduct()[i4].getName() + "\n");
                }
                myHolder.binding1.product.setText(st);
                StringBuffer bd = new StringBuffer("");
                for (User name : myOrder2.getUser_detail()) {
                    bd = bd.append(name.getName());
                }
                myHolder.binding1.custName.setText(bd);
                StringBuffer cd = new StringBuffer("");
                for (int a = 0; a < myOrder2.getMerchant_detail().length; a++) {
                    cd = cd.append(myOrder2.getMerchant_detail()[a].getName() + ", " + myOrder2.getMerchant_detail()[a].getAddress());
                }
                myHolder.binding1.storeInformation.setText(cd);
                 if(!myOrder2.getCustomer_location().equalsIgnoreCase("")) {
                    double kms = FunctionClass.getKmFromLatLong(Double.parseDouble(myOrder2.getMerchant_detail()[0].getLatitude()), Double.parseDouble(myOrder2.getMerchant_detail()[0].getLongitude()),
                            Double.parseDouble(myOrder2.getDriver_location().split(",")[0]), Double.parseDouble(myOrder2.getDriver_location().split(",")[1]));
                    kms = kms + FunctionClass.getKmFromLatLong(Double.parseDouble(myOrder2.getMerchant_detail()[0].getLatitude()), Double.parseDouble(myOrder2.getMerchant_detail()[0].getLongitude()),
                            Double.parseDouble(myOrder2.getCustomer_location().split(",")[0]), Double.parseDouble(myOrder2.getCustomer_location().split(",")[1]));

                    if(kms<=Integer.parseInt(HomeFragment.baseKm)) {
                        myHolder.binding1.userInformation.setText("\u20b9"+HomeFragment.baseAmount);
                    }else{
                        myHolder.binding1.userInformation.setText("\u20b9"+new DecimalFormat("##.##").format(kms*Double.parseDouble(HomeFragment.delCharge)));
                    }
                     myHolder.binding1.distance.setText(new DecimalFormat("##.##").format(kms)+" Km");
                 }else{
                     myHolder.binding1.distance.setText("0 Km");
                     myHolder.binding1.userInformation.setText("\u20b90");
                }

            } else {
                myOrder2 = myOrder3;
            }
            final MyOrder myOrder4 = myOrder2;
            myHolder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
//                    OrderDetailsActivity.myOrder = myOrder4;
//                    OrderAdapter.this.ctx.startActivity(new Intent(OrderAdapter.this.ctx, OrderDetailsActivity.class));
                }
            });
            myHolder.binding1.acceptOrder.setOnSlideCompleteListener(
                    new SlideToActView.OnSlideCompleteListener() {
                        @Override
                        public void onSlideComplete(SlideToActView slideToActView) {
                            MyOrder myOrder1 = (MyOrder) OrderAdapter.this.myOrderList.get(i);
                             DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(OrderAdapter.this.singleTask.getValue("boy"), DeliveryPartner.class);
                            if (deliveryPartner.getIsDefault().equalsIgnoreCase("no")) {
                                final ProgressDialog progressDialog = ProgressDialog.show(OrderAdapter.this.ctx, "", "Loading...");
                                ((MyApi) OrderAdapter.this.singleTask.getRetrofit().create(MyApi.class)).orderStatus(deliveryPartner.getId(), myOrder1.getOrderId(), "Preparing", "").enqueue(new Callback<JsonArray>() {
                                    public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                                        if (response.isSuccessful()) {
                                            try {
                                                JSONObject jsonObject1 = new JSONArray(new Gson().toJson((JsonElement) response.body())).getJSONObject(0);
                                                String res = jsonObject1.getString("res");
                                                String message = jsonObject1.getString("message");
                                                Log.e("sdsd", jsonObject1.toString());
                                                progressDialog.dismiss();
                                                if (res.equals("success")) {
                                                    Toast.makeText(OrderAdapter.this.ctx, "Order Accepted", Toast.LENGTH_LONG).show();
                                                    Toast.makeText(OrderAdapter.this.ctx, message, Toast.LENGTH_LONG).show();
                                                    if (OrderAdapter.this.arrayList.size() > i && OrderAdapter.this.arrayList.get(i) != null) {
                                                        OrderAdapter.this.arrayList.get(i).cancel();
                                                    }
                                                    if (NotificationUtils.mMediaPlayer != null) {
                                                        NotificationUtils.mMediaPlayer.stop();
                                                    }
                                                    OrderAdapter.this.getPosition.click();
                                                    fm.beginTransaction().replace(R.id.frame, new OngoingFragment()).commitAllowingStateLoss();
                                                    HomeFragment.binding.txtNew.setTextColor(Color.parseColor("#000000"));
                                                    HomeFragment.binding.viewNewOrder.setVisibility(View.INVISIBLE);
                                                    HomeFragment.binding.txtPrepare.setTextColor(Color.parseColor("#f7b614"));
                                                    HomeFragment.binding.viewPrepare.setVisibility(View.VISIBLE);
                                                    HomeFragment.binding.txtComplete.setTextColor(Color.parseColor("#000000"));
                                                    HomeFragment.binding.viewComplete.setVisibility(View.INVISIBLE);
                                                    if(OngoingFragment.refershLayout!=null) {
                                                        OngoingFragment.refershLayout.onRefresh();
                                                    }
                                                    if (NewOrderFragment.refresh != null) {
                                                        NewOrderFragment.refresh.onRefresh();
                                                        return;
                                                    }
                                                    return;
                                                }
                                                progressDialog.dismiss();
                                                Toast.makeText(OrderAdapter.this.ctx, message, Toast.LENGTH_LONG).show();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    public void onFailure(Call<JsonArray> call, Throwable t) {
                                        progressDialog.dismiss();
                                        Toast.makeText(OrderAdapter.this.ctx, t.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            } else {
                                myHolder.binding1.acceptOrder.resetSlider();
                                new DriversFragment(myOrder1.getMerchant_detail()[0].getLatitude(), myOrder1.getMerchant_detail()[0].getLongitude(), myOrder1.getOrderId(), i, (CountDownTimer) null).show(fm, "");
                            }
                        }
                    }
            );
            myHolder.binding1.acceptOrder.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                }
            });
            myHolder.binding1.rejectOrder.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(OrderAdapter.this.ctx).create();
                    View view = LayoutInflater.from(OrderAdapter.this.ctx).inflate(R.layout.cancel_reason_layout, (ViewGroup) null);
                    CancelReasonLayoutBinding binding = CancelReasonLayoutBinding.bind(view);
                    binding.close.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    binding.submit.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            OrderAdapter.this.orderRejected(i);
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.setView(view);
                    alertDialog.show();
                }
            });
            return;
        }
        if (i2 == 2) {
            final MyOrder myOrder5 = this.myOrderList.get(i);
            if (this.myOrderList != null) {
                myHolder.binding2.orderid.setText("Order Id - " + myOrder5.getOrderId());
                myHolder.binding2.paymentType.setText(myOrder5.getMethod());
                myHolder.binding2.totalAmount.setText("₹" + myOrder5.getAmount());

                if(!myOrder5.getCustomer_location().equalsIgnoreCase("")) {
                    double kms = FunctionClass.getKmFromLatLong(Double.parseDouble(myOrder5.getMerchant_detail()[0].getLatitude()), Double.parseDouble(myOrder5.getMerchant_detail()[0].getLongitude()),
                            Double.parseDouble(myOrder5.getDriver_location().split(",")[0]), Double.parseDouble(myOrder5.getDriver_location().split(",")[1]));
                    kms = kms + FunctionClass.getKmFromLatLong(Double.parseDouble(myOrder5.getMerchant_detail()[0].getLatitude()), Double.parseDouble(myOrder5.getMerchant_detail()[0].getLongitude()),
                            Double.parseDouble(myOrder5.getCustomer_location().split(",")[0]), Double.parseDouble(myOrder5.getCustomer_location().split(",")[1]));

                    if(kms<=Integer.parseInt(HomeFragment.baseKm)) {
                        myHolder.binding2.earning.setText("\u20b9"+HomeFragment.baseAmount);
                    }else{
                        myHolder.binding2.earning.setText("\u20b9"+new DecimalFormat("##.##").format(kms*Double.parseDouble(HomeFragment.delCharge)));
                    }
//                    myHolder.binding1.distance.setText(new DecimalFormat("##.##").format(kms)+" Km");
                }else{
//                    myHolder.binding1.distance.setText("0 Km");
                    myHolder.binding2.earning.setText("\u20b90");
                }


//                myHolder.binding2.earning.setText("₹" + myOrder5.getDeliveryearnamount());
                myHolder.binding2.date.setText(myOrder5.getCreatedAt().split(" ")[0]);
                if(myOrder5.getMethod().equalsIgnoreCase("COD")){
                    myHolder.binding2.method.setText("CASH ON DELIVERY - \u20b9"+myOrder5.getAmount());
                }else  if(myOrder5.getMethod().equalsIgnoreCase("Wallet+COD")){
                    myHolder.binding2.method.setText("Collect - \u20b9"+myOrder5.getExtraAmount());
                }else{
                    myHolder.binding2.method.setText(myOrder5.getMethod());
                }


                myHolder.binding2.deliveryTime.setText("Reach Restaurant within - " + myOrder5.getDelivery_time() + " Minutes");
                myHolder.binding2.userInformation.setText(myOrder5.getAddress()[0].getType() + "\n" + myOrder5.getAddress()[0].getLandmark() + "\n" + myOrder5.getAddress()[0].getApartmentNo() + ", " + myOrder5.getAddress()[0].getAddress());
                StringBuffer cd2 = new StringBuffer("");
                for (int a2 = 0; a2 < myOrder5.getMerchant_detail().length; a2++) {
                    cd2 = cd2.append(myOrder5.getMerchant_detail()[a2].getName() + ", " + myOrder5.getMerchant_detail()[a2].getAddress());
                }
                myHolder.binding2.storeInformation.setText(cd2);
                myHolder.binding2.callUser.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    public void onClick(View v) {
                        Intent intent;
                        if (OrderAdapter.this.ctx.checkSelfPermission("android.permission.CALL_PHONE") != PackageManager.PERMISSION_GRANTED) {
                            OrderAdapter.this.ctx.requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 100);
                            return;
                        }
                        if (myOrder5.getAlt_number().equalsIgnoreCase("")) {
                            intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + myOrder5.getUser_detail()[0].getMobile()));
                        } else {
                            intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + myOrder5.getAlt_number()));
                        }
                        OrderAdapter.this.ctx.startActivity(intent);
                    }
                });
                if (myOrder5.getMethod().equalsIgnoreCase("cod")) {
                    myHolder.binding2.cashCollectedCheck.setText("Cash Collected \u20b9"+myOrder5.getAmount());
                    myHolder.binding2.cashCollectedCheck.setVisibility(View.VISIBLE);
                }else  if(myOrder5.getMethod().equalsIgnoreCase("Wallet+COD")){
                    myHolder.binding2.cashCollectedCheck.setText("Cash Collected \u20b9"+myOrder5.getExtraAmount());
                    myHolder.binding2.cashCollectedCheck.setVisibility(View.VISIBLE);
                } else {
                    myHolder.binding2.cashCollectedCheck.setVisibility(View.GONE);
                }


                StringBuffer st2 = new StringBuffer("");
                for (int i5 = 0; i5 < myOrder5.getOrderproduct().length; i5++) {
                    st2.append(myOrder5.getOrderproduct()[i5].getName() + " × " + myOrder5.getOrderproduct()[i5].getQty() + ",");
                }
                myHolder.binding2.product.setText(st2);
                StringBuffer rt = new StringBuffer("");
                for (User name2 : myOrder5.getUser_detail()) {
                    rt = rt.append(name2.getName());
                }
                myHolder.binding2.customorName.setText(rt);
                myHolder.binding2.orderDetails.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        OrderStatusActivity.myorder = myOrder5;
                        OrderAdapter.this.ctx.startActivity(new Intent(OrderAdapter.this.ctx, OrderStatusActivity.class));
                    }
                });
                if (myOrder5.getOrderStatus().equals("Picked")) {
                    if (myOrder5.getReach_u().equalsIgnoreCase("false")) {
                        myHolder.binding2.markAsDeliverd.setText("REACH CUSTOMER");
                    } else {
                        myHolder.binding2.markAsDeliverd.setText("DELIVERED?");
                        myHolder.binding2.lineDelConfirm.setVisibility(View.VISIBLE);
                    }
                    myHolder.binding2.txtGotoStore.setText("Customer");
                    myHolder.binding2.goToStore.setVisibility(View.GONE);
                    myHolder.binding2.goToUser.setVisibility(View.VISIBLE);
                    myHolder.binding2.method.setVisibility(View.VISIBLE);
                    myHolder.binding2.lineAltNo.setVisibility(View.VISIBLE);
                    myHolder.binding2.deliveryTime.setVisibility(View.GONE);
                } else if (myOrder5.getOrderStatus().equals("Prepared")) {
                    myHolder.binding2.markAsDeliverd.setText("Slide to Pickup Order");
                    myHolder.binding2.txtGotoStore.setText("Store");
                    myHolder.binding2.goToStore.setVisibility(View.GONE);
                    myHolder.binding2.goToUser.setVisibility(View.VISIBLE);
                    myHolder.binding2.lineAltNo.setVisibility(View.VISIBLE);
                } else {
                    if (myOrder5.getReach_r().equalsIgnoreCase("false")) {
                        myHolder.binding2.markAsDeliverd.setText("REACH RESTAURANT");
                    } else {
                        myHolder.binding2.markAsDeliverd.setText("PREPARING");
                    }
                    myHolder.binding2.txtGotoStore.setText("Store");
                    myHolder.binding2.goToStore.setVisibility(View.VISIBLE);
                    myHolder.binding2.goToUser.setVisibility(View.GONE);
                }
            }
            final MyOrder myOrder1 = this.myOrderList.get(i);
            myHolder.binding2.markAsDeliverd.setOnSlideCompleteListener(
                    new SlideToActView.OnSlideCompleteListener() {
                        @Override
                        public void onSlideComplete(SlideToActView slideToActView) {
                            if (myHolder.binding2.markAsDeliverd.getText().toString().equalsIgnoreCase("Slide to Pickup Order")) {
                                if (NotificationUtils.mMediaPlayer != null) {
                                    NotificationUtils.mMediaPlayer.stop();
                                }
                                if (myOrder1.getOrderStatus().equals("Prepared")) {
                                   openBottom(myHolder, i, slideToActView, myOrder1.getOrderId());
                                } else {
                                    holder.binding2.markAsDeliverd.resetSlider();
                                    Toast.makeText(ctx, "Order Preparing now..", Toast.LENGTH_LONG).show();
                                }
                                holder.binding2.markAsDeliverd.resetSlider();
                            } else if (myHolder.binding2.markAsDeliverd.getText().toString().equalsIgnoreCase("REACH RESTAURANT")) {
                                OrderAdapter.this.sendNotification(i);
                                myHolder.binding2.markAsDeliverd.setText("PREPARING");
                                holder.binding2.markAsDeliverd.resetSlider();
                            } else if (myHolder.binding2.markAsDeliverd.getText().toString().equalsIgnoreCase("REACH CUSTOMER")) {
                                OrderAdapter.this.sendNotificationUser(i);
                                myHolder.binding2.markAsDeliverd.setText("DELIVERED?");
                                myHolder.binding2.lineDelConfirm.setVisibility(View.VISIBLE);
                                holder.binding2.markAsDeliverd.resetSlider();
                            } else if (myHolder.binding2.markAsDeliverd.getText().toString().equalsIgnoreCase("PREPARING")) {
                                Toast.makeText(ctx, "Order Preparing now..", Toast.LENGTH_LONG).show();
                                holder.binding2.markAsDeliverd.resetSlider();
                            } else {
                                if(!myHolder.binding2.itemsGivenCheck.isChecked()){
                                    Toast.makeText(ctx,"Please Check if item is given",Toast.LENGTH_LONG).show();
                                    holder.binding2.markAsDeliverd.resetSlider();
                                    return;
                                }
                                if(myOrder1.getMethod().equalsIgnoreCase("cod")){
                                    if(!myHolder.binding2.cashCollectedCheck.isChecked()){
                                        Toast.makeText(ctx,"Please Check if cash is collected",Toast.LENGTH_LONG).show();
                                        holder.binding2.markAsDeliverd.resetSlider();
                                        return;
                                    }
                                }
                                if (NotificationUtils.mMediaPlayer != null) {
                                    NotificationUtils.mMediaPlayer.stop();
                                }
                                OrderAdapter.this.getPosition.click();
                                OrderAdapter orderAdapter = OrderAdapter.this;
                                orderAdapter.updateStatus("Delivered", myHolder, orderAdapter.idList.toString(), myOrder1.getOrderId(), i,myOrder1);
                            }
                        }
                    }
            );
            myHolder.binding2.goToStore.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (NotificationUtils.mMediaPlayer != null) {
                        NotificationUtils.mMediaPlayer.stop();
                    }
                    String latitude = SharedPrefManagerLocation.getInstance(ctx).locationModel(Constraints.LATITUDE);
                    String longitude = SharedPrefManagerLocation.getInstance(ctx).locationModel(Constraints.LONGITUDE);
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?saddr="+latitude+","+longitude+"&daddr="+myOrder5.getMerchant_detail()[0].getLatitude()+","+myOrder5.getMerchant_detail()[0].getLongitude()));
                    intent.setPackage("com.google.android.apps.maps");
                    ctx.startActivity(intent);
                }
            });
            myHolder.binding2.goToUser.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (NotificationUtils.mMediaPlayer != null) {
                        NotificationUtils.mMediaPlayer.stop();
                    }
                    String latitude = SharedPrefManagerLocation.getInstance(ctx).locationModel(Constraints.LATITUDE);
                    String longitude = SharedPrefManagerLocation.getInstance(ctx).locationModel(Constraints.LONGITUDE);
                    Intent intent = new Intent("android.intent.action.VIEW",Uri.parse("http://maps.google.com/maps?saddr="+latitude+","+longitude+"&daddr="+myOrder5.getAddress()[0].getLatitude()+","+myOrder5.getAddress()[0].getLongitude()));
                    intent.setPackage("com.google.android.apps.maps");
                    OrderAdapter.this.ctx.startActivity(intent);
                }
            });
            return;
        }
        final MyOrder myOrder6 = this.myOrderList.get(i);
        if (this.myOrderList != null) {
            myHolder.binding3.orderid.setText(myOrder6.getMerchant_detail()[0].getName());
            myHolder.binding3.paymentType.setText(myOrder6.getMethod());
            if(!myOrder6.getCustomer_location().equalsIgnoreCase("")) {
                double kms = FunctionClass.getKmFromLatLong(Double.parseDouble(myOrder6.getMerchant_detail()[0].getLatitude()), Double.parseDouble(myOrder6.getMerchant_detail()[0].getLongitude()),
                        Double.parseDouble(myOrder6.getDriver_location().split(",")[0]), Double.parseDouble(myOrder6.getDriver_location().split(",")[1]));
                kms = kms + FunctionClass.getKmFromLatLong(Double.parseDouble(myOrder6.getMerchant_detail()[0].getLatitude()), Double.parseDouble(myOrder6.getMerchant_detail()[0].getLongitude()),
                        Double.parseDouble(myOrder6.getCustomer_location().split(",")[0]), Double.parseDouble(myOrder6.getCustomer_location().split(",")[1]));

                if(kms<=Integer.parseInt(HomeFragment.baseKm)) {
                    myHolder.binding3.totalEarning.setText("₹" + (Double.parseDouble(HomeFragment.baseAmount)));
                }else{
                    myHolder.binding3.totalEarning.setText("₹" + new DecimalFormat("##.##").format(kms*Double.parseDouble(HomeFragment.delCharge)));

//                    myHolder.binding1.userInformation.setText("\u20b9"+new DecimalFormat("##.##").format(kms*Double.parseDouble(HomeFragment.delCharge)));
                }
//                myHolder.binding1.distance.setText(new DecimalFormat("##.##").format(kms)+" Km");
            }else{
//                myHolder.binding1.distance.setText("0 Km");
                myHolder.binding3.totalEarning.setText("\u20b90");
            }



            if(myOrder6.getMethod().equalsIgnoreCase("cod")){
                myHolder.binding3.txtMethod.setText("Cash Collected : ");
                myHolder.binding3.totalAmount.setText("₹" + myOrder6.getAmount());
            }else if(myOrder6.getMethod().equalsIgnoreCase("online")){
                myHolder.binding3.txtMethod.setText("(\u20b90)Online Paid : ");
                myHolder.binding3.totalAmount.setText("₹" + myOrder6.getAmount());
            }else if(myOrder6.getMethod().equalsIgnoreCase("cod")){
                myHolder.binding3.txtMethod.setText("Wallet Paid : ");
                myHolder.binding3.totalAmount.setText("₹" + myOrder6.getAmount());
            }else if(myOrder6.getMethod().equalsIgnoreCase("Wallet+COD")){
                myHolder.binding3.txtMethod.setText("Cash Collected : ");
                myHolder.binding3.totalAmount.setText("₹" + myOrder6.getExtraAmount());
            }else if(myOrder6.getMethod().equalsIgnoreCase("Wallet+ONLINE")){
                myHolder.binding3.txtMethod.setText("(\u20b90)Online Paid : ");
                myHolder.binding3.totalAmount.setText("₹" + myOrder6.getAmount());
            }else if(myOrder6.getMethod().equalsIgnoreCase("Wallet")){
                myHolder.binding3.txtMethod.setText("Cash Collected : \u20b90 (Online Paid)");
                myHolder.binding3.totalAmount.setText("");
            }
            if(myOrder6.getUser_detail().length>0) {
                myHolder.binding3.customername.setText(myOrder6.getUser_detail()[0].getName());
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(myOrder6.getModifiedAt().split(" ")[0]);
                    String dtt = new SimpleDateFormat("dd MMM, yyyy").format(date);
                    holder.binding3.date.setText(dtt + " " + myOrder6.getModifiedAt().split(" ")[1]);
                } catch (Exception e) {

                }
            }
            if(!myOrder6.getCustomer_location().equalsIgnoreCase("")) {
                double kms = FunctionClass.getKmFromLatLong(Double.parseDouble(myOrder6.getMerchant_detail()[0].getLatitude()), Double.parseDouble(myOrder6.getMerchant_detail()[0].getLongitude()),
                        Double.parseDouble(myOrder6.getDriver_location().split(",")[0]), Double.parseDouble(myOrder6.getDriver_location().split(",")[1]));
                kms = kms + FunctionClass.getKmFromLatLong(Double.parseDouble(myOrder6.getMerchant_detail()[0].getLatitude()), Double.parseDouble(myOrder6.getMerchant_detail()[0].getLongitude()),
                        Double.parseDouble(myOrder6.getCustomer_location().split(",")[0]), Double.parseDouble(myOrder6.getCustomer_location().split(",")[1]));
                myHolder.binding3.product.setText(new DecimalFormat("##.##").format(kms) + " Km");
            }else{
                myHolder.binding3.product.setText("0 Km");
            }
        }
        myHolder.binding3.viewOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OrderSummaryActivity.myOrder = myOrder6;
                OrderAdapter.this.ctx.startActivity(new Intent(OrderAdapter.this.ctx, OrderSummaryActivity.class));
            }
        });
    }

    public void openBottom(final MyHolder holder, final int position, View v, final String orderId) {
        idList.clear();
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this.ctx, R.style.myBottomSheetDialogTheme);
        View view1 = LayoutInflater.from(this.ctx).inflate(R.layout.product_list_bottom_sheet, (ViewGroup) ((RelativeLayout) v.findViewById(R.id.bottomSheetContainer)), false);
        bottomSheetDialog.setContentView(view1);
        ProductListBottomSheetBinding binding = ProductListBottomSheetBinding.bind(view1);
        binding.productList.setLayoutManager(new LinearLayoutManager(this.ctx, RecyclerView.VERTICAL, false));
        binding.close.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.adapter.OrderAdapter.11
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                bottomSheetDialog.dismiss();
            }
        });
        ProductListAdapter adapter = new ProductListAdapter(this.myOrderList.get(position).getOrderproduct());
        adapter.getAdapterPosition(new ProductListAdapter.GetPosition() { // from class: digi.coders.capsicodeliverypartner.adapter.OrderAdapter.12
            @Override // digi.coders.capsicodeliverypartner.adapter.ProductListAdapter.GetPosition
            public void findPosition(int position2, Orderproduct product, ProductDesignBinding binding2) {
                if (!binding2.checkBox.isChecked()) {
                    binding2.checkBox.setChecked(true);
                    OrderAdapter.this.idList.add(product.getId());
                   if(idList.size()==myOrderList.get(position).getOrderproduct().length){
                       binding.lineOtp.setVisibility(View.VISIBLE);
                   }else{
                       binding.lineOtp.setVisibility(View.GONE);
                   }
                    return;
                }
                binding2.checkBox.setChecked(false);
                for (int i = 0; i < OrderAdapter.this.idList.size(); i++) {
                    if (OrderAdapter.this.idList.get(i).equals(product.getId())) {
                        OrderAdapter.this.idList.remove(i);
                    }

                }


            }
        });
        binding.productList.setAdapter(adapter);
        binding.submit.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.adapter.OrderAdapter.13
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                Log.e("dss", OrderAdapter.this.idList.toString());
                if (OrderAdapter.this.idList.size() == ((MyOrder) OrderAdapter.this.myOrderList.get(position)).getOrderproduct().length) {
                   if(binding.otpPinView.getText().toString().length()==4) {
                       if (myOrderList.get(position).getOrderId().endsWith(binding.otpPinView.getText().toString())) {
                           bottomSheetDialog.dismiss();
                           OrderAdapter.this.getPosition.click();
                           OrderAdapter orderAdapter = OrderAdapter.this;
                           orderAdapter.updateStatus("Picked", holder, orderAdapter.idList.toString(), orderId, position, myOrderList.get(position));
                           return;
                       } else {
                           Toast.makeText(ctx, "Incorrect OTP", Toast.LENGTH_LONG).show();
                       }
                   }else {
                       Toast.makeText(ctx, "Please Enter Valid OTP", Toast.LENGTH_LONG).show();
                   }
                }
                Toast.makeText(ctx, "Please Collect All items to Deliver", Toast.LENGTH_LONG).show();
            }
        });
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.show();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<MyOrder> list = this.myOrderList;
        if (list != null) {
            return list.size();
        }
        return 5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateStatus(final String status, final MyHolder holder, String s, String OrderId, final int position,MyOrder myOrder) {
        final ProgressDialog progressDialog = ProgressDialog.show(this.ctx, "", "Loading...");
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven, DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Log.e(NotificationCompat.CATEGORY_STATUS, status);
        Call<JsonArray> call = myApi.orderStatus(deliveryPartner.getId(), OrderId, status, s);
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.adapter.OrderAdapter.14
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String res = jsonObject1.getString("res");
                        String message = jsonObject1.getString("message");
                        if (res.equals("success")) {
                            if (OrderAdapter.this.arrayList.size() > position && OrderAdapter.this.arrayList.get(position) != null) {
                                OrderAdapter.this.arrayList.get(position).cancel();
                            }
                            Toast.makeText(OrderAdapter.this.ctx, "Order " + status,  Toast.LENGTH_LONG).show();
                            Toast.makeText(OrderAdapter.this.ctx, message,  Toast.LENGTH_LONG).show();
                            if (status.equals("Picked")) {
                                holder.binding2.markAsDeliverd.setText("Mark As Delivered");
                                if (NotificationUtils.mMediaPlayer != null) {
                                    NotificationUtils.mMediaPlayer.stop();
                                }
                                if (OngoingFragment.refershLayout != null) {
                                    OngoingFragment.refershLayout.onRefresh();
                                }
                                OrderAdapter.this.getPosition.click();
                            } else if (status.equalsIgnoreCase("Delivered")) {
                                fm.beginTransaction().replace(R.id.frame, new DeliveredFragment()).commitAllowingStateLoss();
                                HomeFragment.binding.txtNew.setTextColor(Color.parseColor("#000000"));
                                HomeFragment.binding.viewNewOrder.setVisibility(View.INVISIBLE);
                                HomeFragment.binding.txtPrepare.setTextColor(Color.parseColor("#000000"));
                                HomeFragment.binding.viewPrepare.setVisibility(View.INVISIBLE);
                                HomeFragment.binding.txtComplete.setTextColor(Color.parseColor("#f7b614"));
                                HomeFragment.binding.viewComplete.setVisibility(View.VISIBLE);
                                if (OngoingFragment.refershLayout != null) {
                                    OngoingFragment.refershLayout.onRefresh();
                                }
                                if(DeliveredFragment.refershLayout!=null) {
                                    DeliveredFragment.refershLayout.referesh();
                                }
                                showDialoge(ctx,myOrder);
                            } else if (NewOrderFragment.refresh != null) {
                                NewOrderFragment.refresh.onRefresh();
                            }
                        } else {
                            Toast.makeText(OrderAdapter.this.ctx, message,  Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.dismiss();
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ctx, t.getMessage(),  Toast.LENGTH_LONG).show();
            }
        });
    }

    /* loaded from: classes3.dex */
    public class MyHolder extends RecyclerView.ViewHolder {
        NewOrdersLayoutBinding binding1;
        OngoingOrderLayoutBinding binding2;
        DeliveredOrderLayoutBinding binding3;

        public MyHolder(View itemView) {
            super(itemView);
            if (OrderAdapter.this.status == 1) {
                this.binding1 = NewOrdersLayoutBinding.bind(itemView);
            } else if (OrderAdapter.this.status == 2) {
                this.binding2 = OngoingOrderLayoutBinding.bind(itemView);
            } else {
                this.binding3 = DeliveredOrderLayoutBinding.bind(itemView);
            }
        }
    }

    public void findPosition(GetPosition getPositio) {
        this.getPosition = getPositio;
    }

    void orderRejected(final int position) {
        MyOrder myOrder1 = this.myOrderList.get(position);
        final ProgressDialog progressDialog = ProgressDialog.show(this.ctx, "", "Loading...");
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven, DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.orderStatus(deliveryPartner.getId(), myOrder1.getOrderId(), "Rejected", "");
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.adapter.OrderAdapter.15
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String res = jsonObject1.getString("res");
                        String message = jsonObject1.getString("message");
                        Log.e("sdsd", jsonObject1.toString());
                        progressDialog.dismiss();
                        if (res.equals("success")) {
                            Toast.makeText(OrderAdapter.this.ctx, "Order Rejected",  Toast.LENGTH_LONG).show();
                            Toast.makeText(OrderAdapter.this.ctx, message,  Toast.LENGTH_LONG).show();
                            if (OrderAdapter.this.arrayList.size() > position && OrderAdapter.this.arrayList.get(position) != null) {
                                OrderAdapter.this.arrayList.get(position).cancel();
                            }
                            if (NotificationUtils.mMediaPlayer != null) {
                                NotificationUtils.mMediaPlayer.stop();
                            }
                            if (NewOrderFragment.refresh != null) {
                                NewOrderFragment.refresh.onRefresh();
                            }
                            OrderAdapter.this.getPosition.click();
                            return;
                        }
                        progressDialog.dismiss();
                        Toast.makeText(OrderAdapter.this.ctx, message,  Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OrderAdapter.this.ctx, t.getMessage(),  Toast.LENGTH_LONG).show();
            }
        });
    }

    void sendNotification(int position) {
        MyOrder myOrder1 = this.myOrderList.get(position);
        final ProgressDialog progressDialog = ProgressDialog.show(this.ctx, "", "Loading...");
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven, DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.sendNotification(deliveryPartner.getId(), myOrder1.getOrderId());
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.adapter.OrderAdapter.16
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String res = jsonObject1.getString("res");
                        String message = jsonObject1.getString("message");
                        progressDialog.dismiss();
                        if (!res.equals("success")) {
                            progressDialog.dismiss();
                            Toast.makeText(OrderAdapter.this.ctx, message,  Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OrderAdapter.this.ctx, t.getMessage(),  Toast.LENGTH_LONG).show();
            }
        });
    }

    void sendNotificationUser(int position) {
        MyOrder myOrder1 = this.myOrderList.get(position);
        final ProgressDialog progressDialog = ProgressDialog.show(this.ctx, "", "Loading...");
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven, DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.sendNotificationUser(deliveryPartner.getId(), myOrder1.getOrderId());
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.adapter.OrderAdapter.17
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(new Gson().toJson((JsonElement) response.body()));
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String res = jsonObject1.getString("res");
                        String message = jsonObject1.getString("message");
                        progressDialog.dismiss();
                        if (!res.equals("success")) {

                            progressDialog.dismiss();
                            Toast.makeText(OrderAdapter.this.ctx, message,  Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OrderAdapter.this.ctx, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showDialoge(Context ctx,MyOrder myOrder2) {
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        View promptView = layoutInflater.inflate(R.layout.dialoge_incentive, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
        alertDialogBuilder.setView(promptView);


        ImageView iconTip=promptView.findViewById(R.id.iconTp);
        TextView txtTip=promptView.findViewById(R.id.txtTip);
        CardView cardIncentive=promptView.findViewById(R.id.cardIncentive);
        TextView incentive=promptView.findViewById(R.id.incentive);
        TextView incentiveAmount=promptView.findViewById(R.id.incentiveAmount);
        TextView earning=promptView.findViewById(R.id.earning);
        TextView distance=promptView.findViewById(R.id.distance);
        TextView todayEarning=promptView.findViewById(R.id.todayEarning);
        TextView orders=promptView.findViewById(R.id.orders);
        TextView orderPay=promptView.findViewById(R.id.orderPay);
        LottieAnimationView confety=promptView.findViewById(R.id.confety);
        RecyclerView incentiveList=promptView.findViewById(R.id.incentiveList);

        final AlertDialog alert2 = alertDialogBuilder.create();
        alert2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        if(!myOrder2.getCustomer_location().equalsIgnoreCase("")) {
        double kms = FunctionClass.getKmFromLatLong(Double.parseDouble(myOrder2.getMerchant_detail()[0].getLatitude()), Double.parseDouble(myOrder2.getMerchant_detail()[0].getLongitude()),
                Double.parseDouble(myOrder2.getDriver_location().split(",")[0]), Double.parseDouble(myOrder2.getDriver_location().split(",")[1]));
        kms = kms + FunctionClass.getKmFromLatLong(Double.parseDouble(myOrder2.getMerchant_detail()[0].getLatitude()), Double.parseDouble(myOrder2.getMerchant_detail()[0].getLongitude()),
                Double.parseDouble(myOrder2.getCustomer_location().split(",")[0]), Double.parseDouble(myOrder2.getCustomer_location().split(",")[1]));

        if(kms<=Integer.parseInt(HomeFragment.baseKm)) {
            earning.setText("\u20b9"+HomeFragment.baseAmount);
            orderPay.setText("\u20b9"+(todayDeliveredAMount+Double.parseDouble(HomeFragment.baseAmount))+"");

        }else{
            earning.setText("\u20b9"+new DecimalFormat("##.##").format(kms*Double.parseDouble(HomeFragment.delCharge)));
            orderPay.setText("\u20b9"+new DecimalFormat("##.##").format(todayDeliveredAMount+kms*Double.parseDouble(HomeFragment.delCharge))+"");
         }
            distance.setText(new DecimalFormat("##.##").format(kms)+" Km");
    }else{
        distance.setText("0 Km");
        orderPay.setText("\u20b90");
        earning.setText("\u20b90");

    }

        try {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");

            Date date1 = simpleDateFormat.parse(myOrder2.getAccepted_time());
            String sum = simpleDateFormat.format(Calendar.getInstance().getTime());
            Date date2=simpleDateFormat.parse(sum);
            long difference = date2.getTime() - date1.getTime();
            int days = (int) (difference / (1000 * 60 * 60 * 24));
           int  hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));

           if(hours/60>0){
               todayEarning.setText((int)(hours/60)+"H:"+(hours%60)+"M");
           }else{
               todayEarning.setText((int)(hours/60)+"H");
           }

        }catch (Exception e){
            todayEarning.setText("0M");
            Toast.makeText(ctx,e.getMessage().toString(),Toast.LENGTH_LONG);
        }

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
                            totalDeliveredOrder=totalDeliveredOrder+1;
                            int totalIncentiveAmount=0;
                            for(int i=0;i<Integer.parseInt(jsonArray1.getString("no_count"));i++){
                                if(Arrays.asList(jsonArray1.getString("breakpoints_order").split(",")).contains((i+1)+"")){
                                    arrayList.add(new IncentiveModel(jsonArray1.getString("breakpoint_amount").split(",")[j],(i+1)+""));


                                    if((i+1)==totalDeliveredOrder){
                                        if (!arrayList.get(i).getAmount().equalsIgnoreCase("")) {
                                            confety.setVisibility(View.VISIBLE);
                                            updateIncentive(myOrder2,arrayList.get(i).getAmount());
                                        }
                                    }
                                    if(totalDeliveredOrder>=(i+1)) {
                                        if (!arrayList.get(i).getAmount().equalsIgnoreCase("")) {
                                            totalIncentiveAmount = totalIncentiveAmount + Integer.parseInt(arrayList.get(i).getAmount());
                                        }

                                    }
                                    j++;
                                }else{


                                    arrayList.add(new IncentiveModel("",(i+1)+""));
                                }
                            }
                            if (totalIncentiveAmount != 0) {
                                incentiveAmount.setText("Congratulations! You earned \u20b9" + totalIncentiveAmount + " as incentive");
                                incentive.setText("\u20b9" + totalIncentiveAmount + "");
                                incentiveAmount.setVisibility(View.VISIBLE);
                            } else {
                                incentiveAmount.setVisibility(View.GONE);
                                incentive.setText("\u20b90");
                            }
                            orders.setText(totalDeliveredOrder+"");
                           incentiveList.setLayoutManager(new LinearLayoutManager(ctx,LinearLayoutManager.HORIZONTAL,false));
                            incentiveList.setHasFixedSize(true);
                            incentiveList.setAdapter(new IncelistAdapterAdapter(arrayList,ctx));
                            cardIncentive.setVisibility(View.VISIBLE);
                            iconTip.setVisibility(View.VISIBLE);
                            txtTip.setVisibility(View.VISIBLE);
                        }else{
                            cardIncentive.setVisibility(View.GONE);
                            iconTip.setVisibility(View.GONE);
                            txtTip.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        Toast.makeText(ctx, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonObject> call2, Throwable t) {
            }
        });


        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
               alert2.dismiss();
            }

        }.start();

        alert2.show();
    }

    void updateIncentive(MyOrder myOrder,String amount){
        String ven = this.singleTask.getValue("boy");
        DeliveryPartner deliveryPartner = (DeliveryPartner) new Gson().fromJson(ven,  DeliveryPartner.class);
        MyApi myApi = (MyApi) this.singleTask.getRetrofit().create(MyApi.class);
        Call<JsonArray> call = myApi.updateIncentive(deliveryPartner.getId(),amount,myOrder.getOrderId() );
        call.enqueue(new Callback<JsonArray>() { // from class: digi.coders.capsicodeliverypartner.fragment.OfflineFragment.10
            @Override // retrofit2.Callback
            public void onResponse(Call<JsonArray> call2, Response<JsonArray> response) {
                String res;
                JSONObject jsonObject1;
                if (response.isSuccessful()) {
                    try {

                    } catch (Exception e) {
                        Toast.makeText(ctx, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override // retrofit2.Callback
            public void onFailure(Call<JsonArray> call2, Throwable t) {
            }
        });
    }

    public void addData(List<MyOrder> arrayList){
        myOrderList.addAll(arrayList);
        notifyDataSetChanged();
    }

    public void addClearData(){
        myOrderList.clear();
        notifyDataSetChanged();
    }
}
