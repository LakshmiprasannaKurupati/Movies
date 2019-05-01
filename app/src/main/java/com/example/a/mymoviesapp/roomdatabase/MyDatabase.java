package com.example.a.mymoviesapp.roomdatabase;

import android.app.Application;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FavourateMovies.class},version = 1,exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
   // public abstract MyDao myDaoClassDatabase();

        public abstract MyDao myDao();
        //  public abstract MyDao myDao();
        private static MyDatabase INSTANCE;
        static MyDatabase getDatabase(final Context context){

            if (INSTANCE == null) {
                synchronized (MyDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                MyDatabase.class, "favuorite.db")
                                .fallbackToDestructiveMigration()
                                .build();
                    }
                }
            }
            return INSTANCE;
        }
    }



   /* private static RoomDatabase.Callback roomDatabase=new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateAsync(INSTANCE).execute();
        }
    };

    private static class PopulateAsync extends AsyncTask<Void,Void,LiveData<List<FavuoriteMovies>>> {
        MoviesDAO moviesDAO;
        public PopulateAsync(MyDataBase instance) {
            moviesDAO=instance.moviesDAO();
        }

        @Override
        protected LiveData<List<FavuoriteMovies>> doInBackground(Void... voids) {
            return moviesDAO.getAllData();
        }
    }*/

