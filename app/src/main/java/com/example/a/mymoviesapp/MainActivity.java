package com.example.a.mymoviesapp;

import android.app.LoaderManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Loader;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.arch.lifecycle.Observer;

import com.example.a.mymoviesapp.roomdatabase.FavourateMovies;
import com.example.a.mymoviesapp.roomdatabase.FavourateMoviesAdapter;
import com.example.a.mymoviesapp.roomdatabase.MyDao;
import com.example.a.mymoviesapp.roomdatabase.MyDatabase;
import com.example.a.mymoviesapp.roomdatabase.ViewModelClass;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    RecyclerView recyclerView;
    LiveData<List<FavourateMovies>> favourateMovies;
     FavourateMoviesAdapter adapter;
    HiratedMovies hiratedMovies;
    MyDatabase myDatabase;
    ViewModelClass viewModelClass;
    ProgressBar progressBar;
    List<FavourateMovies> list;
    ConnectivityManager connectivityManager;
    String imageurl = "https://image.tmdb.org/t/p/w500/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        // recyclerView.setAdapter(adapter);
        viewModelClass = ViewModelProviders.of(this).get(ViewModelClass.class);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        hiratedMovies = new HiratedMovies(this);
        getLoaderManager().initLoader(2,null,this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popularmovies:
                hasPopularMovies();
                break;
            case R.id.topratedmovies:
                hasHiratedMovies();
                break;
            case R.id.favorates:
               /* if (list.isEmpty()){
                    AlertDialog.Builder alertdialog=new AlertDialog.Builder(this)
                            .setTitle("No Favourite Movies..")
                            .setMessage("alet message")
                            .setPositiveButton("popular", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    hasPopularMovies();
                                }
                            })
                            .setNegativeButton("top rated", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    hasHiratedMovies();
                                }
                            });
                }
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

               */
               hasFouriteMovies();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void hasFouriteMovies() {
        //getLoaderManager().initLoader(2,null,this);

    //    favourateMovies = myDatabase.myDao().getAllData();
        viewModelClass.getListLiveData().observe(this, new Observer<List<FavourateMovies>>() {
            @Override
            public void onChanged(@Nullable List<FavourateMovies> favourateMovies) {
                //list=favourateMovies;
                FavourateMoviesAdapter favourateMoviesAdapter=new FavourateMoviesAdapter(MainActivity.this,favourateMovies);
                recyclerView.setAdapter(favourateMoviesAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        });

       // viewModelClass.getViewModelList().o
    }

    private void hasHiratedMovies() {
        getLoaderManager().initLoader(3,null,hiratedMovies);

    }

    private void hasPopularMovies() {
        getLoaderManager().initLoader(2,null,this);

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
                String url = "https://api.themoviedb.org/3/movie/popular?api_key=c11eca2bfc869e03c3cd133adab5ce6b";
                try {
                    URL u=new URL(url);
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) u.openConnection();
                    httpsURLConnection.setRequestMethod("GET");
                    httpsURLConnection.connect();
                    InputStream inputStream = httpsURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String ln = null;
                    while ((ln = bufferedReader.readLine()) != null) {
                        stringBuilder.append(ln + "");

                    }
                    return stringBuilder.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        List<ModelListClass> model = new ArrayList<>();
        // progressBar.setVisibility(View.INVISIBLE);
        //Log.i("modelDataaa", model.toString());
        if(s!=null) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray array = jsonObject.getJSONArray("results");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String id = object.getString("id");
                    String title = object.getString("original_title");
                    String poster = object.getString("poster_path");
                    String rating = object.getString("vote_average") + "/10";
                    String overview = object.getString("overview");
                    String releasedate = object.getString("release_date");
                    ModelListClass list = new ModelListClass(id, title, poster, rating, overview, releasedate);
                    model.add(list);
                }
                MyAdapter myAdapter = new MyAdapter(this, model);
                int ori = this.getResources().getConfiguration().orientation;
                if (ori == Configuration.ORIENTATION_LANDSCAPE) {
                    this.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
                } else {
                    this.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

                }
                recyclerView.setAdapter(myAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        /*else {
            Toast.makeText(this, "exception error", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}