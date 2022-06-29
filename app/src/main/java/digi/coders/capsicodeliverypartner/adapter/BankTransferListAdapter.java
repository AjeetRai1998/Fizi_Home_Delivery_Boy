package digi.coders.capsicodeliverypartner.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.databinding.BankProofBinding;
import digi.coders.capsicodeliverypartner.model.ProofModel.DataItem;
import java.util.List;

/* loaded from: classes3.dex */
public class BankTransferListAdapter extends RecyclerView.Adapter<BankTransferListAdapter.MyHolder> {
    Context mContext;
    List<DataItem> orderItems;

    public BankTransferListAdapter(List<DataItem> orderItems, Context mContext) {
        this.orderItems = orderItems;
        this.mContext = mContext;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_proof, parent, false);
        return new MyHolder(view);
    }

    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.binding.amount.setText("Payment of ₹" + this.orderItems.get(position).getAmount());
        holder.binding.date.setText(this.orderItems.get(position).getDate());
        holder.binding.remark.setText(this.orderItems.get(position).getRemark());
        Picasso.get().load("https://yourcapsico.com/assets/uploads/merchant/" + this.orderItems.get(position).getImage()).into(holder.binding.image);
        holder.binding.download.setOnClickListener(new View.OnClickListener() { // from class: digi.coders.capsicodeliverypartner.adapter.BankTransferListAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DownloadManager downloadmanager = (DownloadManager) BankTransferListAdapter.this.mContext.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse("https://yourcapsico.com/assets/uploads/merchant/" + BankTransferListAdapter.this.orderItems.get(position).getImage());
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle("Payment Proof");
                request.setDescription("Downloading");
                request.setNotificationVisibility(1);
                downloadmanager.enqueue(request);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.orderItems.size();
    }

    /* loaded from: classes3.dex */
    public class MyHolder extends RecyclerView.ViewHolder {
        BankProofBinding binding;

        public MyHolder(View itemView) {
            super(itemView);
            this.binding = BankProofBinding.bind(itemView);
        }
    }
}
