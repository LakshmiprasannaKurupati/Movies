package com.example.a.mymoviesapp.roomdatabase;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a.mymoviesapp.DetailActivity;
import com.example.a.mymoviesapp.MainActivity;
import com.example.a.mymoviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavourateMoviesAdapter extends RecyclerView.Adapter<FavourateMoviesAdapter.MyViewHolder> {
    MainActivity activity;
    ConnectivityManager connectivityManager;
    List<FavourateMovies> favourateMovies;

    public FavourateMoviesAdapter(MainActivity activity, List<FavourateMovies> favourateMovies) {
        this.activity = activity;
        this.connectivityManager = connectivityManager;
        this.favourateMovies = favourateMovies;
    }

    @Override
    public FavourateMoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(activity).inflate(R.layout.favourates_design,viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavourateMoviesAdapter.MyViewHolder myViewHolder, int i) {
        Picasso.with(activity).load(favourateMovies.get(i).getPoster_movie()).into(myViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return favourateMovies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.fav_images);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            Bundle bundle=new Bundle();
            Intent i=new Intent(activity,DetailActivity.class);
            bundle.putString("id", String.valueOf(favourateMovies.get(position).getId()));
            bundle.putString("title",favourateMovies.get(position).getTitle_movie());
            bundle.putString("image",favourateMovies.get(position).getPoster_movie());
            bundle.putString("rating",favourateMovies.get(position).getRating_Movie());
            bundle.putString("overview",favourateMovies.get(position).getDescription_movie());
            bundle.getString("releaseDate",favourateMovies.get(position).getReleaseDate_movie());
            i.putExtras(bundle);
           // activity.startActivity(i);
            activity.startActivity(i);
        }
    }
}






