package digi.coders.capsicodeliverypartner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.databinding.FloatingCashBinding;
import digi.coders.capsicodeliverypartner.model.MyOrder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* loaded from: classes3.dex */
public class FloatingCashAdapter extends RecyclerView.Adapter<FloatingCashAdapter.MyHolder> {
    private Context ctx;
    private ArrayList<MyOrder> list;

    public FloatingCashAdapter(ArrayList<MyOrder> list) {
        this.list = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.ctx = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.floating_cash, parent, false);
        return new MyHolder(view);
    }

    public void onBindViewHolder(MyHolder holder, int position) {
        MyOrder transaction = this.list.get(position);
        holder.binding.amt.setText("₹ " + (Double.parseDouble(transaction.getAmount()) - Double.parseDouble(transaction.getShippinCharge())));

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(transaction.getCreatedAt().split(" ")[0]);
            String dtt=new SimpleDateFormat("dd MMM, yyyy").format(date);
            holder.binding.date.setText(dtt+" "+transaction.getCreatedAt().split(" ")[1]);
        }catch (Exception e){

        }
        holder.binding.txtId.setText("Order ID : " + transaction.getOrderId());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    /* loaded from: classes3.dex */
    public class MyHolder extends RecyclerView.ViewHolder {
        FloatingCashBinding binding;

        public MyHolder(View itemView) {
            super(itemView);
            this.binding = FloatingCashBinding.bind(itemView);
        }
    }

    public void addData(List<MyOrder> arrayList){
        list.addAll(arrayList);
        notifyDataSetChanged();
    }

    public void addClearData(){
        list.clear();
        notifyDataSetChanged();
    }
}
