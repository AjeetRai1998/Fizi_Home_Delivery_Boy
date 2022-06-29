package digi.coders.capsicodeliverypartner.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.activity.OrderSummaryActivity;
import digi.coders.capsicodeliverypartner.databinding.OrderHistoryLayoutBinding;
import digi.coders.capsicodeliverypartner.model.MyOrder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/* loaded from: classes3.dex */
public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyHolder> {
    private Context ctx;
    private List<MyOrder> myOrderList;

    public OrderHistoryAdapter(List<MyOrder> myOrderList) {
        this.myOrderList = myOrderList;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.ctx = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_layout, parent, false);
        return new MyHolder(view);
    }

    public void onBindViewHolder(MyHolder holder, int position) {
        final MyOrder myOrder = this.myOrderList.get(position);
        holder.binding.orderid.setText("Order Id: " + myOrder.getOrderId());
        holder.binding.paymentType.setText(myOrder.getMethod());
        holder.binding.totalAmount.setText("₹" + myOrder.getAmount());
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(myOrder.getCreatedAt().split(" ")[0]);
            String dtt=new SimpleDateFormat("dd MMM, yyyy").format(date);
            holder.binding.date.setText(dtt);
        }catch (Exception e){

        }
        StringBuffer st = new StringBuffer("");
        for (int i = 0; i < myOrder.getOrderproduct().length; i++) {
            st.append(myOrder.getOrderproduct()[i].getName() + " × " + myOrder.getOrderproduct()[i].getQty() + ",");
        }
        holder.binding.product.setText(st);
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.adapter.OrderHistoryAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                OrderSummaryActivity.myOrder = myOrder;
                OrderHistoryAdapter.this.ctx.startActivity(new Intent(OrderHistoryAdapter.this.ctx, OrderSummaryActivity.class));
            }
        });
        holder.binding.viewOrder.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.adapter.OrderHistoryAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                OrderSummaryActivity.myOrder = myOrder;
                OrderHistoryAdapter.this.ctx.startActivity(new Intent(OrderHistoryAdapter.this.ctx, OrderSummaryActivity.class));
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.myOrderList.size();
    }

    /* loaded from: classes3.dex */
    public class MyHolder extends RecyclerView.ViewHolder {
        OrderHistoryLayoutBinding binding;

        public MyHolder(View itemView) {
            super(itemView);
            this.binding = OrderHistoryLayoutBinding.bind(itemView);
        }
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
