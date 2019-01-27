package com.demotxt.myapp.splash.Database.Pemesanan;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class MyViewModel extends AndroidViewModel {
    private MyRepository mRepository;

    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<Word>> mAllWords;


    public LiveData<List<Word>> getAllWords() { return mAllWords; }


    public MyViewModel(Application application) {
        super(application);
        mRepository = new MyRepository(application);
        mAllWords = mRepository.getAllWords();

    }


    public void insert(Word word) {
        mRepository.insert(word);
    }
}

