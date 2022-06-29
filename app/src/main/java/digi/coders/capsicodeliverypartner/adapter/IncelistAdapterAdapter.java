package digi.coders.capsicodeliverypartner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.databinding.LayoutIncentiveBinding;
import digi.coders.capsicodeliverypartner.databinding.ProductDesignBinding;
import digi.coders.capsicodeliverypartner.fragment.OfflineFragment;
import digi.coders.capsicodeliverypartner.model.IncentiveModel;
import digi.coders.capsicodeliverypartner.model.Orderproduct;

/* loaded from: classes3.dex */
public class IncelistAdapterAdapter extends RecyclerView.Adapter<IncelistAdapterAdapter.MyHolder> {
    private Context ctx;

    ArrayList<IncentiveModel> arrayList;
    public static int amountCount=0;

    public IncelistAdapterAdapter(ArrayList<IncentiveModel> arrayList, Context ctx) {
        this.arrayList = arrayList;
        this.ctx = ctx;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.ctx = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_incentive, parent, false);
        return new MyHolder(view);
    }

    public void onBindViewHolder(final MyHolder holder, final int position) {

        holder.setIsRecyclable(false);

        if(OfflineFragment.totalDeliveredOrder>=(position+1)){

            holder.binding.circleImage.setBackgroundTintList(ctx.getResources().getColorStateList(R.color.color_green));
            holder.binding.circleImage.setImageResource(R.drawable.check_red);
        }else{
            holder.binding.circleImage.setBackgroundTintList(ctx.getResources().getColorStateList(R.color.red));
            holder.binding.circleImage.setImageResource(R.drawable.ic_lock_outline_black_24dp);
        }

        holder.binding.orderCount.setText((position+1)+"");

        if(!arrayList.get(position).getAmount().equalsIgnoreCase("")) {
            holder.binding.amount.setText("\u20b9"+arrayList.get(position).getAmount());
            holder.binding.amount.setVisibility(View.VISIBLE);
        }else{
            holder.binding.amount.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        LayoutIncentiveBinding binding;
        public MyHolder(View itemView) {
            super(itemView);
            this.binding = LayoutIncentiveBinding.bind(itemView);
        }
    }
}
