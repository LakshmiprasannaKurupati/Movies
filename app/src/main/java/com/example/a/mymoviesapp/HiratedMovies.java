package com.example.a.mymoviesapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HiratedMovies implements LoaderManager.LoaderCallbacks<String> {
    MainActivity activity;

    public HiratedMovies(MainActivity activity) {
        this.activity = activity;
    }

    String imageurl="https://image.tmdb.org/t/p/w500";

    @Override
    public android.content.Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<String>(activity) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Override
            public String loadInBackground() {
                String url="https://api.themoviedb.org/3/movie/top_rated?api_key=c11eca2bfc869e03c3cd133adab5ce6b";
                try {
                    URL url1=new URL(url);
                    HttpsURLConnection httpsURLConnection= (HttpsURLConnection) url1.openConnection();
                    httpsURLConnection.setRequestMethod("GET");
                    httpsURLConnection.connect();
                    InputStream inputStream=httpsURLConnection.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder=new StringBuilder();
                    String ln=null;
                    while ((ln=bufferedReader.readLine())!=null){
                        stringBuilder.append(ln);

                    }
                    return stringBuilder.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return  null;
            }
        };
    }

    @Override
    public void onLoadFinished(android.content.Loader<String> loader, String s) {
        List<ModelListClass> modelLists=new ArrayList<>();
        try{
            JSONObject jsonObject=new JSONObject(s);
            JSONArray array=jsonObject.getJSONArray("results");
            for (int i=0;i<array.length();i++)
            {
                JSONObject object=array.getJSONObject(i);
                String id=object.getString("id");
                String title=object.getString("original_title");
                String poster=imageurl+object.getString("poster_path");
                String rating=object.getString("vote_average")+"/10";
                String overview=object.getString("overview");
                String releasedate=object.getString("release_date");
                ModelListClass list=new ModelListClass(id,title,poster,rating,overview,releasedate);
                modelLists.add(list);
            }
            MyAdapter myAdapter=new MyAdapter(activity,modelLists);
            int ori=activity.getResources().getConfiguration().orientation;
            if(ori== Configuration.ORIENTATION_LANDSCAPE){
                activity.recyclerView.setLayoutManager(new GridLayoutManager(activity,4));
            }
            else{
                activity.recyclerView.setLayoutManager(new GridLayoutManager(activity,2));

            }
            activity.recyclerView.setAdapter(myAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(android.content.Loader<String> loader) {

    }
}
