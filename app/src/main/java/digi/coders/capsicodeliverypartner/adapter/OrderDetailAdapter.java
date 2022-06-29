package digi.coders.capsicodeliverypartner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.databinding.OrderDetailsDesignBinding;
import digi.coders.capsicodeliverypartner.model.MyOrder;

/* loaded from: classes3.dex */
public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyHolder> {
    private MyOrder myOrderData;

    public OrderDetailAdapter(MyOrder myOrder) {
        this.myOrderData = myOrder;
    }

    public OrderDetailAdapter() {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details_design, parent, false);
        return new MyHolder(view);
    }

    public void onBindViewHolder(MyHolder holder, int position) {
        if (this.myOrderData != null) {
            MyOrder myOrder = this.myOrderData;
            holder.binding.itemName.setText(myOrder.getOrderproduct()[0].getName());
            holder.binding.itemAmout.setText("₹" + (Double.parseDouble(myOrder.getOrderproduct()[0].getSellPrice()) * Double.parseDouble(myOrder.getOrderproduct()[0].getQty())));
            holder.binding.qty.setText(myOrder.getOrderproduct()[0].getQty());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        MyOrder myOrder = this.myOrderData;
        if (myOrder != null) {
            return myOrder.getOrderproduct().length;
        }
        return 2;
    }

    /* loaded from: classes3.dex */
    public class MyHolder extends RecyclerView.ViewHolder {
        OrderDetailsDesignBinding binding;
        TextView coupon;
        TextView deliveryTip;
        TextView otherCharge;
        TextView shipingCharge;
        TextView subtotal;

        public MyHolder(View itemView) {
            super(itemView);
            this.binding = OrderDetailsDesignBinding.bind(itemView);
            this.subtotal = (TextView) itemView.findViewById(R.id.subtotal);
            this.coupon = (TextView) itemView.findViewById(R.id.coupon);
            this.otherCharge = (TextView) itemView.findViewById(R.id.otherCharge);
            this.shipingCharge = (TextView) itemView.findViewById(R.id.shipingCharge);
            this.deliveryTip = (TextView) itemView.findViewById(R.id.deliveryTip);
        }
    }
}
