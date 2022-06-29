package digi.coders.capsicodeliverypartner.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.fragment.DriversFragment;
import digi.coders.capsicodeliverypartner.helper.Refresh;
import digi.coders.capsicodeliverypartner.model.DeliveryPartner;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class DriversAdapter extends RecyclerView.Adapter<DriversAdapter.ViewHolder> {
    Context ctx;
    int i = 9999999;
    Refresh refresh;
    ArrayList<DeliveryPartner> st;

    public DriversAdapter(ArrayList<DeliveryPartner> st, Context ctx, Refresh refresh) {
        this.st = st;
        this.ctx = ctx;
        this.refresh = refresh;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_driver_layout, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        if (this.i == position) {
            holder.card.setCardBackgroundColor(Color.parseColor("#89c74a"));
            holder.name.setTextColor(Color.parseColor("#ffffff"));
            holder.address.setTextColor(Color.parseColor("#ffffff"));
            holder.distance.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.card.setCardBackgroundColor(Color.parseColor("#ffffff"));
            holder.name.setTextColor(Color.parseColor("#44044F"));
            holder.address.setTextColor(Color.parseColor("#7A7979"));
            holder.distance.setTextColor(Color.parseColor("#7A7979"));
        }
        holder.address.setText("+91 " + this.st.get(position).getMobile());
        if (Double.parseDouble(this.st.get(position).getPassword()) < 1.0d) {
            holder.distance.setText("Less Than 1 KM Away");
        } else {
            holder.distance.setText(((int) Double.parseDouble(this.st.get(position).getPassword())) + " KM Away");
        }
        holder.name.setText(this.st.get(position).getName() + " (Delivering " + this.st.get(position).getEmail() + " Order)");
        Picasso.get().load("https://yourcapsico.com/assets/uploads/delivery_boy/" + this.st.get(position).getIcon()).placeholder(R.drawable.placeholder).into(holder.image);
        holder.card.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.adapter.DriversAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DriversAdapter.this.i = position;
                DriversFragment.selectedDriver = DriversAdapter.this.st.get(position).getId();
                DriversAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.st.size();
    }

    /* loaded from: classes3.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        CardView card;
        TextView distance;
        CircleImageView image;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            this.card = (CardView) itemView.findViewById(R.id.card);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.address = (TextView) itemView.findViewById(R.id.orders);
            this.image = (CircleImageView) itemView.findViewById(R.id.image);
            this.distance = (TextView) itemView.findViewById(R.id.distance);
        }
    }
}
