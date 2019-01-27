package com.demotxt.myapp.splash.Database.Barang;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;



import java.util.List;

public class BRepository {
    private BarangDao mBarangDao;
    private LiveData<List<Barang>> mAllBarangs;



    BRepository(Application application) {
        BarangRoomDatabase db = BarangRoomDatabase.getDatabase(application);
        mBarangDao = db.barangDao();
        mAllBarangs = mBarangDao.getAlphabetizeBarangs();

    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Barang>> getAllBarangs() {
        return mAllBarangs;
    }
    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert (Barang barang) {
        new BRepository.insertAsyncTask(mBarangDao).execute(barang);
    }


    private static class insertAsyncTask extends AsyncTask<Barang, Void, Void> {

        private BarangDao mAsyncTaskDao;


        insertAsyncTask(BarangDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(final   Barang... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
