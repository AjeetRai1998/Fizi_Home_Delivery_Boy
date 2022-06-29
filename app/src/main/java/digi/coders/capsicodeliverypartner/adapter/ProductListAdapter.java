package digi.coders.capsicodeliverypartner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.databinding.ProductDesignBinding;
import digi.coders.capsicodeliverypartner.model.Orderproduct;

/* loaded from: classes3.dex */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyHolder> {
    private Context ctx;
    GetPosition getPosition;
    private Orderproduct[] orderproducts;

    /* loaded from: classes3.dex */
    public interface GetPosition {
        void findPosition(int position, Orderproduct product, ProductDesignBinding binding);
    }

    public ProductListAdapter(Orderproduct[] orderproducts) {
        this.orderproducts = orderproducts;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.ctx = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_design, parent, false);
        return new MyHolder(view);
    }

    public void onBindViewHolder(final MyHolder holder, final int position) {
        final Orderproduct product = this.orderproducts[position];
        holder.binding.productP.setText(product.getName() + "   ×  " + product.getQty());
        holder.binding.checkBox.setClickable(false);
        holder.binding.checkBox.setEnabled(false);
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.adapter.ProductListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ProductListAdapter.this.getPosition.findPosition(position, product, holder.binding);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.orderproducts.length;
    }

    public void getAdapterPosition(GetPosition getPosition) {
        this.getPosition = getPosition;
    }

    /* loaded from: classes3.dex */
    public class MyHolder extends RecyclerView.ViewHolder {
        ProductDesignBinding binding;

        public MyHolder(View itemView) {
            super(itemView);
            this.binding = ProductDesignBinding.bind(itemView);
        }
    }
}
