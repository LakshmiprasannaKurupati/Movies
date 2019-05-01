package com.example.a.mymoviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MoviesTrailerLoader implements LoaderManager.LoaderCallbacks<String> {
    DetailActivity detailActivity;
    String id;


    public MoviesTrailerLoader(DetailActivity detailActivity, String id) {
        this.detailActivity = detailActivity;
        this.id = id;

    }




    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<String>(detailActivity) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Override
            public String loadInBackground() {
                String url="https://api.themoviedb.org/3/movie/"+id+"/videos?api_key=c11eca2bfc869e03c3cd133adab5ce6b";
                Uri uri= Uri.parse(url);
                try{
                    URL url1=new URL(uri.toString());
                    Log.i("urlofvideos",url1.toString());
                    HttpURLConnection httpURLConnection= (HttpURLConnection) url1.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder builder=new StringBuilder();
                    String line="";
                    while (((line=bufferedReader.readLine())!=null)){
                        builder.append(line);
                    }
                    return builder.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        if (s != null) {
            ArrayList<ModelPojoClass> trailer = new ArrayList<>();
            Toast.makeText(detailActivity, "data" + trailer.toString(), Toast.LENGTH_SHORT).show();
            Log.i("jsondata", trailer.toString());
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("results");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object1 = array.getJSONObject(i);
                    String key = object1.getString("key");
                    String name = object1.getString("name");
                    ModelPojoClass modelPojoClass = new ModelPojoClass(key, name);
                    Log.i("modelJson", modelPojoClass.toString());
                    trailer.add(modelPojoClass);

                }
                if (trailer.size() > 0) {
                    TrailerAdapter adapter = new TrailerAdapter(detailActivity, trailer);
                    detailActivity.vidoesRecyclerView.setAdapter(adapter);
                    detailActivity.vidoesRecyclerView.setLayoutManager(new LinearLayoutManager(detailActivity));
                } else {
                    Toast.makeText(detailActivity, "No videos available...!", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
else {
            Toast.makeText(detailActivity, ""+s, Toast.LENGTH_SHORT).show(); }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
    private class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder>{
        DetailActivity detailActivity;
        ArrayList<ModelPojoClass> list;


        public TrailerAdapter(DetailActivity detailActivity, ArrayList<ModelPojoClass> list) {
            this.detailActivity = detailActivity;
            this.list = list;
        }

        @NonNull
        @Override
        public TrailerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v= LayoutInflater.from(detailActivity).inflate(R.layout.teasor_movies,viewGroup,false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull TrailerAdapter.MyViewHolder myViewHolder, int i) {
            myViewHolder.tv.setText(list.get(i).getName());


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tv;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tv=itemView.findViewById(R.id.video_tv);
                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View view) {
                int position=getAdapterPosition();
                String video_key=list.get(position).getKey();
                Uri uri= Uri.parse("https://www.youtube.com/watch?v="+video_key);
                Intent i=new Intent(Intent.ACTION_VIEW,uri);
                detailActivity.startActivity(i);

            }
        }
    }
}
