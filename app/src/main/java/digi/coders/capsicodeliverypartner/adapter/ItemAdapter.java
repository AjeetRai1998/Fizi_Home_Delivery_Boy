package digi.coders.capsicodeliverypartner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import digi.coders.capsicodeliverypartner.R;

/* loaded from: classes3.dex */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyHolder> {
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyHolder(view);
    }

    public void onBindViewHolder(MyHolder holder, int position) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return 2;
    }

    /* loaded from: classes3.dex */
    public class MyHolder extends RecyclerView.ViewHolder {
        public MyHolder(View itemView) {
            super(itemView);
        }
    }
}
