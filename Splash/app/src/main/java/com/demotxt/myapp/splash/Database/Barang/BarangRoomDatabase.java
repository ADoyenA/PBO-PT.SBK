package com.demotxt.myapp.splash.Database.Barang;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.demotxt.myapp.splash.Database.Pemesanan.Word;


/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */

@Database(entities = {Barang.class}, version = 3)
public abstract class BarangRoomDatabase extends RoomDatabase {

    public abstract BarangDao barangDao();

    private static BarangRoomDatabase INSTANCEs;

    public static BarangRoomDatabase getDatabase(final Context context) {
        if (INSTANCEs == null) {
            synchronized (BarangRoomDatabase.class) {
                if (INSTANCEs == null) {
                    INSTANCEs = Room.databaseBuilder(context.getApplicationContext(),
                            BarangRoomDatabase.class, "SBKb.db")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallbackb)
                            .build();
                }
            }
        }
        return INSTANCEs;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallbackb = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCEs).execute();
        }
    };
    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final BarangDao mDao;

        PopulateDbAsync(BarangRoomDatabase db) {
            mDao = db.barangDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mDao.deleteAll();
            return null;
        }
    }

}
