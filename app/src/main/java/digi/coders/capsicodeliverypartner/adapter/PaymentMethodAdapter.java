package digi.coders.capsicodeliverypartner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.databinding.PaymentMethodDesignBinding;
import digi.coders.capsicodeliverypartner.model.PaymentModeImage;
import java.util.List;

/* loaded from: classes3.dex */
public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.MyHolde> {
    private Context ctx;
    private GetPosition getPosition;
    private List<PaymentModeImage> list;
    private int lastCheckedPosition = 4;
    private int row_index = 0;

    /* loaded from: classes3.dex */
    public interface GetPosition {
        void click(View v, int position);
    }

    public PaymentMethodAdapter(List<PaymentModeImage> list) {
        this.list = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyHolde onCreateViewHolder(ViewGroup parent, int viewType) {
        this.ctx = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_method_design, parent, false);
        return new MyHolde(view);
    }

    public void onBindViewHolder(MyHolde holder, int position) {
        PaymentModeImage image = this.list.get(position);
        holder.binding.paymentImage.setImageDrawable(this.ctx.getDrawable(image.getImage()));
        holder.binding.radioButton.setChecked(position == this.lastCheckedPosition);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    public void findMyPosition(GetPosition getPosition) {
        this.getPosition = getPosition;
    }

    /* loaded from: classes3.dex */
    public class MyHolde extends RecyclerView.ViewHolder {
        PaymentMethodDesignBinding binding;

        public MyHolde(View itemView) {
            super(itemView);
            PaymentMethodDesignBinding bind = PaymentMethodDesignBinding.bind(itemView);
            this.binding = bind;
            bind.radioButton.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.adapter.PaymentMethodAdapter.MyHolde.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    int copyOfLastCheckedPosition = PaymentMethodAdapter.this.lastCheckedPosition;
                    PaymentMethodAdapter.this.lastCheckedPosition = MyHolde.this.getAdapterPosition();
                    PaymentMethodAdapter.this.notifyItemChanged(copyOfLastCheckedPosition);
                    PaymentMethodAdapter.this.notifyItemChanged(PaymentMethodAdapter.this.lastCheckedPosition);
                    PaymentMethodAdapter.this.getPosition.click(v, PaymentMethodAdapter.this.lastCheckedPosition);
                }
            });
        }
    }
}
