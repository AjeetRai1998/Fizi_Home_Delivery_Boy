package digi.coders.capsicodeliverypartner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import digi.coders.capsicodeliverypartner.R;
import digi.coders.capsicodeliverypartner.databinding.ReviewDesignBinding;
import digi.coders.capsicodeliverypartner.model.Review;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/* loaded from: classes3.dex */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyHolder> {
    private List<Review> list;

    public ReviewAdapter(List<Review> list) {
        this.list = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_design, parent, false);
        return new MyHolder(view);
    }

    public void onBindViewHolder(MyHolder holder, int position) {
        Review review = this.list.get(position);
        holder.binding.rating.setRating(Float.parseFloat(review.getRating()));
        holder.binding.name.setText(review.getUser_details().getName());
        if (review.getRemark().equalsIgnoreCase("")) {
            holder.binding.LineFeedback.setVisibility(View.GONE);
        } else {
            holder.binding.LineFeedback.setVisibility(View.VISIBLE);
        }
        holder.binding.review.setText(review.getRemark());
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(review.getCreatedAt().split(" ")[0]);
            String dtt=new SimpleDateFormat("dd MMM, yyyy").format(date);
            holder.binding.date.setText(dtt+" "+review.getCreatedAt().split(" ")[1]);
        }catch (Exception e){

        }
        Picasso.get().load("https://yourcapsico.com/assets/uploads/customer/" + review.getUser_details().getIcon()).placeholder(R.drawable.placeholder).into(holder.binding.icon);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    /* loaded from: classes3.dex */
    public class MyHolder extends RecyclerView.ViewHolder {
        ReviewDesignBinding binding;

        public MyHolder(View itemView) {
            super(itemView);
            this.binding = ReviewDesignBinding.bind(itemView);
        }
    }
}
