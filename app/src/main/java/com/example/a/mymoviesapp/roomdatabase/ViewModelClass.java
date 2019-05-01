package com.example.a.mymoviesapp.roomdatabase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.a.mymoviesapp.MainActivity;

import java.util.List;
import java.util.Observer;

public class ViewModelClass extends AndroidViewModel{
    LiveData<List<FavourateMovies>> listLiveData;
    //FavourateMovies favourateMovies;
    MoviesRepository moviesRepository;

    public ViewModelClass(@NonNull Application application) {
        super(application);
        moviesRepository=new MoviesRepository(application);
        listLiveData=moviesRepository.getData();
    }
    public LiveData<List<FavourateMovies>> getListLiveData()
    {
        return listLiveData;
    }

    public void  insertList(FavourateMovies favuoriteMovies){
        moviesRepository.insert(favuoriteMovies);
    }

    public void deletelist(FavourateMovies favuoriteMovies1){
        moviesRepository.delete(favuoriteMovies1);
    }

}
