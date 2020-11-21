package ru.catcherry.teammobile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    Context context;
    List<Review> list;

    public ListAdapter(Context context, List<Review> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = list.get(position);
        holder.detailReviewText.setText("Отзыв #"+review.review_id
                +"\nКомната #"+review.room_id
                +"\nСодержание:"+review.comment);
        holder.detailReviewLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, ReviewDetailActivity.class);
            intent.putExtra("reviewId", review.review_id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView detailReviewImg;
        TextView detailReviewText;
        LinearLayout detailReviewLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detailReviewImg = itemView.findViewById(R.id.detailReviewImg);
            detailReviewText = itemView.findViewById(R.id.detailReviewText);
            detailReviewLayout = itemView.findViewById(R.id.detailReviewLayout);
        }
    }
}
