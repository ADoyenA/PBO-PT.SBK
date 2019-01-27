package com.demotxt.myapp.splash.Database.Barang;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;


public class AddBarangViewModel extends ViewModel {

        // COMPLETED (6) Add a task member variable for the TaskEntry object wrapped in a LiveData
        private LiveData<Barang> task;

        // COMPLETED (8) Create a constructor where you call loadTaskById of the taskDao to initialize the tasks variable
        // Note: The constructor should receive the database and the taskId
    public AddBarangViewModel(BarangRoomDatabase database, int taskId){
        task = database.barangDao().loadTaskByIdb(taskId);
    }

        // COMPLETED (7) Create a getter for the task variable
        public LiveData<Barang> getTask () {
        return task;
    }
    }
