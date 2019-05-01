package com.example.a.mymoviesapp.roomdatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class MoviesRepository {
    MyDao myDao;
    LiveData<List<FavourateMovies>> movieslist;
    public MoviesRepository(Application application) {
        MyDatabase dataBase=MyDatabase.getDatabase(application);
       myDao=dataBase.myDao();
        movieslist=myDao.getAllData();
    }

    LiveData<List<FavourateMovies>> getData(){
        return movieslist;
    }

    public void insert(FavourateMovies movies){
        new taskInsert(myDao).execute(movies);
    }

    public void delete(FavourateMovies movies1){
        new taskDelete(myDao).execute(movies1);
    }

    class taskInsert extends AsyncTask<FavourateMovies,Void,Void> {
        private MyDao dao;
        public taskInsert(MyDao moviesDAO) {

            dao=moviesDAO;
        }

        @Override
        protected Void doInBackground(FavourateMovies... favuoriteMovies) {
            dao.insert(favuoriteMovies[0]);
            return null;
        }
    }

    private class taskDelete extends AsyncTask<FavourateMovies,Void,Void> {
        MyDao mdao;
        public taskDelete(MyDao moviesDAO) {
            mdao=moviesDAO;
        }

        @Override
        protected Void doInBackground(FavourateMovies... favuoriteMovies) {
            mdao.deletdeData(favuoriteMovies[0]);
            return null;

        }
    }
}
