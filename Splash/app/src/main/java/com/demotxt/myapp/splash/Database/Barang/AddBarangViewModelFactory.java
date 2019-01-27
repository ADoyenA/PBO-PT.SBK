package com.demotxt.myapp.splash.Database.Barang;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;



public class AddBarangViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final BarangRoomDatabase mDb;
    private final int mTaskId;

    public AddBarangViewModelFactory(BarangRoomDatabase database, int taskId) {
        mDb = database;
        mTaskId = taskId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddBarangViewModel(mDb, mTaskId);
    }

}
