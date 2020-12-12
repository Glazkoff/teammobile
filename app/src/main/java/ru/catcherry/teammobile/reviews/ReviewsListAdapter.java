package ru.catcherry.teammobile.reviews;

import android.app.Activity;
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

import ru.catcherry.teammobile.R;

public class ReviewsListAdapter extends RecyclerView.Adapter<ReviewsListAdapter.ViewHolder> {

    Context context;
    List<Review> list;

    public ReviewsListAdapter(Context context, List<Review> list){
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
        holder.detailReviewText.setText("Содержание: " + review.comment);
        holder.detailReviewRoom.setText("Комната №" + review.room_id);
        holder.detailReviewHeader.setText("Отзыв №" + review.review_id);
        holder.detailReviewLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, ReviewDetailActivity.class);
            intent.putExtra("reviewId", review.review_id);
            ((Activity) context).startActivityForResult(intent, ReviewDetailActivity.DELETE);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        TextView detailReviewText, detailReviewRoom, detailReviewHeader;
        LinearLayout detailReviewLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detailReviewHeader = itemView.findViewById(R.id.detailReviewHeader);
            detailReviewRoom = itemView.findViewById(R.id.detailReviewRoom);
            detailReviewText = itemView.findViewById(R.id.detailReviewText);
            detailReviewLayout = itemView.findViewById(R.id.detailReviewLayout);
        }
    }
}
