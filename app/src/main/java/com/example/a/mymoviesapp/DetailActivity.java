package com.example.a.mymoviesapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a.mymoviesapp.roomdatabase.FavourateMovies;
import com.example.a.mymoviesapp.roomdatabase.MyDatabase;
import com.example.a.mymoviesapp.roomdatabase.ViewModelClass;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

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
import java.util.List;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    ImageView imageView;
    TextView textView,ratingtext,relase,overviewtext;
    RecyclerView recyclerView,vidoesRecyclerView;
    MoviesTrailerLoader trailerLoader;
    LikeButton likeButton;
    String id,title,image,overview,rating,release;
    MyDatabase myDatabase;
    FavourateMovies favourateMovies;
    ViewModelClass viewModelClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView=findViewById(R.id.imageview);
        recyclerView=findViewById(R.id.recyclerview_review);
        vidoesRecyclerView=findViewById(R.id.recyclerview_vidoes);
        likeButton=findViewById(R.id.heart_button);
        textView=findViewById(R.id.tv1);
        ratingtext=findViewById(R.id.tv3);
        relase=findViewById(R.id.tv5);
        overviewtext=findViewById(R.id.tv7);
        viewModelClass= ViewModelProviders.of(this).get(ViewModelClass.class);
        myDatabase= Room.databaseBuilder(this,MyDatabase.class,"Movie.db")
                .allowMainThreadQueries().build();
        reviewClassLoder();
        Bundle bundle=getIntent().getExtras();
        id=bundle.getString("id");
          title=bundle.getString("title");
          image=bundle.getString("image");
          rating=bundle.getString("rating");
          overview=bundle.getString("overview");
          release=bundle.getString("release");

        Picasso.with(this).load(image).into(imageView);

        textView.setText(title);
        ratingtext.setText(rating);
        relase.setText(overview);
        overviewtext.setText(release);
        trailerLoader=new MoviesTrailerLoader(this,id);
        getSupportLoaderManager().initLoader(0,null,trailerLoader);
       favourateMovies();
        likeButton.setOnLikeListener(new OnLikeListener() {
           @Override
           public void liked(LikeButton likeButton) {
               Toast.makeText(DetailActivity.this,"Liked", Toast.LENGTH_SHORT).show();
               FavourateMovies favourateMovies=new FavourateMovies(id,title,image,rating,overview,release);
             /*  favourateMovies.setId(id);
               favourateMovies.setPoster_movie(image);
               favourateMovies.set
               Title_movie(title);
               favourateMovies.setRating_Movie(rating);
               favourateMovies.setReleaseDate_movie(release);
               favourateMovies.setDescription_movie(overview);*/
              // myDatabase.myDao().insert(favourateMovies);
              viewModelClass.insertList(favourateMovies);
               Toast.makeText(DetailActivity.this, "Inserted sucessfully", Toast.LENGTH_SHORT).show();

               }
               @Override
           public void unLiked(LikeButton likeButton) {
               Toast.makeText(DetailActivity.this, "unliked", Toast.LENGTH_SHORT).show();
               FavourateMovies favourateMovies=new FavourateMovies(id,title,image,rating,overview,release);
              // favourateMovies.setId(id);
              // myDatabase.myDao().deletdeData(favourateMovies);
               viewModelClass.deletelist(favourateMovies);
               Toast.makeText(DetailActivity.this, "deleted sucessfully", Toast.LENGTH_SHORT).show();



           }
       });
    }

    private void favourateMovies() {
        viewModelClass.getListLiveData().observe(this,new Observer<List<FavourateMovies>>() {
            @Override
            public void onChanged(@Nullable List<FavourateMovies> favourateMovies) {
                for(int i=0;i<favourateMovies.size();i++){
                    String ids=favourateMovies.get(i).getId();
                    if(ids.equalsIgnoreCase(id)){
                        likeButton.setLiked(true);
                    }
                }
            }
        });
    //    ArrayList<FavourateMovies> favourateMovies= new ArrayList<>();



    }

    private void reviewClassLoder(){

        getSupportLoaderManager().initLoader(1,null,this);
    }


    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<String>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Override
            public String loadInBackground() {
                String review="https://api.themoviedb.org/3/movie/"+id+"/reviews?api_key=c11eca2bfc869e03c3cd133adab5ce6b";
                try{
                    URL url=new URL(review);
                    HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder builder=new StringBuilder();
                    String ln="";
                    while((ln=bufferedReader.readLine())!=null){
                        builder.append(ln);
                    }
                    return  builder.toString();

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
        List<ReviewsModelClass> reviewsModelClasses=new ArrayList<>();
        try{
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray("results") ;
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                String author=jsonObject1.getString("author");
                String content=jsonObject1.getString("content");
                ReviewsModelClass modelClass=new ReviewsModelClass(author,content);
                reviewsModelClasses.add(modelClass);
            }
            if(reviewsModelClasses.size()>0){
                ReviewsModelAdapter modelAdapter=new ReviewsModelAdapter(this,reviewsModelClasses);
                recyclerView.setAdapter(modelAdapter);
                int orientation=this.getResources().getConfiguration().orientation;
                if(orientation== Configuration.ORIENTATION_LANDSCAPE){
                    recyclerView.setLayoutManager(new GridLayoutManager(this,4));
                }
                else {
                    recyclerView.setLayoutManager(new GridLayoutManager(this,2));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

}
