package com.example.a.mymoviesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ReviewsModelAdapter extends RecyclerView.Adapter<ReviewsModelAdapter.ViewHolder> {
    Context context;
    List<ReviewsModelClass> reviewsModelClasses;

    public ReviewsModelAdapter(Context context, List<ReviewsModelClass> reviewsModelClasses) {
        this.context = context;
        this.reviewsModelClasses = reviewsModelClasses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.reviews_design,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {
        viewHolder.author.setText(reviewsModelClasses.get(i).getAuthor());
        viewHolder.content.setText(reviewsModelClasses.get(i).getContent());

    }

    @Override
    public int getItemCount() {
        return reviewsModelClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView author,content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author=itemView.findViewById(R.id.author_tv);
            content=itemView.findViewById(R.id.reviews_description_tv);
        }
    }
}
