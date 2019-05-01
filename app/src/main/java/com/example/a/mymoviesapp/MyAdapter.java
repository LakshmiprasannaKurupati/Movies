package com.example.a.mymoviesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
    List<ModelListClass> modelLists;
    Bundle bundle;


    public MyAdapter(Context context, List<ModelListClass> modelLists) {
        this.context = context;
        this.modelLists = modelLists;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.movies_list, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder viewHolder, int i) {
        Picasso.with(context).load(modelLists.get(i).getPoster())
                .into(viewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return modelLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_images);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int exactPosition=getAdapterPosition();
            bundle=new Bundle();
            Intent intent=new Intent(context,DetailActivity.class);
            bundle.putString("id",modelLists.get(exactPosition).getId());
            bundle.putString("title",modelLists.get(exactPosition).getTitle());
            bundle.putString("image",modelLists.get(exactPosition).getPoster());
            bundle.putString("rating",modelLists.get(exactPosition).getRating());
            bundle.putString("overview",modelLists.get(exactPosition).getOverview());
            bundle.putString("release",modelLists.get(exactPosition).getRelasedate());
            intent.putExtras(bundle);
            context.startActivity(intent);

        }
    }
}


