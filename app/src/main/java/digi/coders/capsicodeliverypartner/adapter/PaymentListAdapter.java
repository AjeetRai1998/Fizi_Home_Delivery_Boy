package digi.coders.capsicodeliverypartner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.databinding.PaymentLayoutBinding;
import digi.coders.capsicodeliverypartner.model.PaymentDetails;
import java.util.List;

/* loaded from: classes3.dex */
public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.MyHolder> {
    private Context ctx;
    private List<PaymentDetails> list;

    public PaymentListAdapter(List<PaymentDetails> list) {
        this.list = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.ctx = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_layout, parent, false);
        return new MyHolder(view);
    }

    public void onBindViewHolder(MyHolder holder, int position) {
        PaymentDetails payment = this.list.get(position);
        holder.binding.transactionId.setText("Transaction id: " + payment.getTxtId());
        if (payment.getStatus().equals("true")) {
            holder.binding.paymentStatus.setText("Successfull");
            holder.binding.paymentStatus.setTextColor(this.ctx.getResources().getColor(R.color.color_blue));
        } else {
            holder.binding.paymentStatus.setText("Failed");
            holder.binding.paymentStatus.setTextColor(this.ctx.getResources().getColor(R.color.red));
        }
        holder.binding.totalAmount.setText("₹ " + payment.getAmount());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    /* loaded from: classes3.dex */
    public class MyHolder extends RecyclerView.ViewHolder {
        PaymentLayoutBinding binding;

        public MyHolder(View itemView) {
            super(itemView);
            this.binding = PaymentLayoutBinding.bind(itemView);
        }
    }
}
