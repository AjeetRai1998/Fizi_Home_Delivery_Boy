package digi.coders.capsicodeliverypartner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.databinding.CardLayoutBinding;
import digi.coders.capsicodeliverypartner.model.Card;
import java.util.List;

/* loaded from: classes3.dex */
public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.MyHolder> {
    private Context ctx;
    private List<Card> list;

    public CardListAdapter(List<Card> list) {
        this.list = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.ctx = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new MyHolder(view);
    }

    public void onBindViewHolder(MyHolder holder, int position) {
        Card card = this.list.get(position);
        holder.binding.title.setText(card.getTitle());
        holder.binding.todayEarning.setText(card.getValue());
        holder.binding.image.setImageDrawable(this.ctx.getResources().getDrawable(card.getImage()));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    /* loaded from: classes3.dex */
    public class MyHolder extends RecyclerView.ViewHolder {
        CardLayoutBinding binding;

        public MyHolder(View itemView) {
            super(itemView);
            this.binding = CardLayoutBinding.bind(itemView);
        }
    }
}
