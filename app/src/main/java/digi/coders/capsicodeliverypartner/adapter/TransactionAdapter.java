package digi.coders.capsicodeliverypartner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.databinding.TransactionLayoutBinding;
import digi.coders.capsicodeliverypartner.model.MyOrder;
import digi.coders.capsicodeliverypartner.model.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/* loaded from: classes3.dex */
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyHolder> {
    private Context ctx;
    private List<Transaction> list;

    public TransactionAdapter(List<Transaction> list) {
        this.list = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.ctx = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_layout, parent, false);
        return new MyHolder(view);
    }

    public void onBindViewHolder(MyHolder holder, int position) {
        Transaction transaction = this.list.get(position);
        holder.binding.msg.setText(transaction.getMsg());
        if (!transaction.getType().equals("debit")) {
            holder.binding.type.setText(transaction.getType());
            holder.binding.type.setTextColor(this.ctx.getResources().getColor(R.color.color_green));
        } else if (transaction.getIsStatus().equalsIgnoreCase("Pending")) {
            holder.binding.type.setText("Transaction Pending");
            holder.binding.type.setTextColor(this.ctx.getResources().getColor(R.color.color_blue));
        } else if (transaction.getIsStatus().equalsIgnoreCase("false")) {
            holder.binding.type.setText("Withdrawal Request Rejected By Admin");
            holder.binding.type.setTextColor(this.ctx.getResources().getColor(R.color.red));
        } else {
            holder.binding.type.setText("Amount Debited From Wallet");
            holder.binding.type.setTextColor(this.ctx.getResources().getColor(R.color.red));
        }
        holder.binding.amt.setText("₹ " + transaction.getAmount());
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(transaction.getCreatedAt().split(" ")[0]);
            String dtt=new SimpleDateFormat("dd MMM, yyyy").format(date);
            holder.binding.date.setText(dtt+" "+transaction.getCreatedAt().split(" ")[1]);
        }catch (Exception e){

        }
        holder.binding.txtId.setText(transaction.getTxtId());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    /* loaded from: classes3.dex */
    public class MyHolder extends RecyclerView.ViewHolder {
        TransactionLayoutBinding binding;

        public MyHolder(View itemView) {
            super(itemView);
            this.binding = TransactionLayoutBinding.bind(itemView);
        }
    }

    public void addData(List<Transaction> arrayList){
        list.addAll(arrayList);
        notifyDataSetChanged();
    }

    public void addClearData(){
        list.clear();
        notifyDataSetChanged();
    }
}
