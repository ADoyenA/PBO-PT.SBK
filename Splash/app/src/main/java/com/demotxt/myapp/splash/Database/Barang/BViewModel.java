package com.demotxt.myapp.splash.Database.Barang;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;



import java.util.List;

public class BViewModel extends AndroidViewModel {
    private BRepository mbRepository;

    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<Barang>> mAllBarangs;


    public LiveData<List<Barang>> getAllBarangs() { return mAllBarangs; }


    public BViewModel(Application application) {
        super(application);
        mbRepository = new BRepository(application);
        mAllBarangs = mbRepository.getAllBarangs();

    }


    public void insert(Barang barang) {
        mbRepository.insert(barang);
    }
}
