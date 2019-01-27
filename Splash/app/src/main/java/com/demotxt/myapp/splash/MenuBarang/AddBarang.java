package com.demotxt.myapp.splash.MenuBarang;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;


import com.demotxt.myapp.splash.Database.Barang.BViewModel;
import com.demotxt.myapp.splash.Database.Barang.Barang;
import com.demotxt.myapp.splash.Database.Barang.BarangRoomDatabase;
import com.demotxt.myapp.splash.Database.Pemesanan.AppExecutor;
import com.demotxt.myapp.splash.R;

import java.util.List;

public class AddBarang extends AppCompatActivity implements BarangAdapter.ItemClickListener{

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private BViewModel mBarangViewModel;
    private BarangAdapter adapter;
    private BarangRoomDatabase mDb;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barang);

        recyclerView = findViewById(R.id.recyclerviewb);
        adapter = new BarangAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerViewb, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                AppExecutor.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Barang> tasksBarangs = adapter.getTasks();
                        mDb.barangDao().deleteTask(tasksBarangs.get(position));
                    }
                });
            }
        }).attachToRecyclerView(recyclerView);

        FloatingActionButton addb = findViewById(R.id.addb);
        addb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddBarang.this, NewBarangActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
        mDb = BarangRoomDatabase.getDatabase(getApplicationContext());
        setupViewModel();
    }


    private void setupViewModel() {
        mBarangViewModel = ViewModelProviders.of(this).get(BViewModel.class);
        mBarangViewModel.getAllBarangs().observe(this, new Observer<List<Barang>>() {
            @Override
            public void onChanged(@Nullable List<Barang> taskEntries) {
                //Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                adapter.setWords(taskEntries);
            }
        });
    }




    @Override
    public void onItemClickListener(int itemId) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent
        Intent intent = new Intent(AddBarang.this, NewBarangActivity.class);
        intent.putExtra(NewBarangActivity.EXTRA_TASK_ID, itemId);
        startActivity(intent);
    }

}