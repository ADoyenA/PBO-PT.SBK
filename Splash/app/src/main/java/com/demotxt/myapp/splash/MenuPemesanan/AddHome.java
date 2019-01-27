package com.demotxt.myapp.splash.MenuPemesanan;

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

import com.demotxt.myapp.splash.Database.Pemesanan.AppExecutor;
import com.demotxt.myapp.splash.Database.Pemesanan.MyViewModel;
import com.demotxt.myapp.splash.Database.Pemesanan.Word;
import com.demotxt.myapp.splash.Database.Pemesanan.WordRoomDatabase;
import com.demotxt.myapp.splash.R;

import java.util.List;

public class AddHome extends AppCompatActivity implements HomeAdapter.ItemClickListener{

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private MyViewModel mWordViewModel;
    private HomeAdapter adapter;
    private WordRoomDatabase mDb;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home);

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new HomeAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
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
                        List<Word> tasks = adapter.getTasks();
                        mDb.wordDao().deleteTask(tasks.get(position));
                    }
                });
            }
        }).attachToRecyclerView(recyclerView);

        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddHome.this, NewHomeActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
        mDb = WordRoomDatabase.getDatabase(getApplicationContext());
        setupViewModel();
    }


    private void setupViewModel() {
        mWordViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> taskEntries) {
                //Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                adapter.setWords(taskEntries);
            }
        });
    }




    @Override
    public void onItemClickListener(int itemId) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent
        Intent intent = new Intent(AddHome.this, NewHomeActivity.class);
        intent.putExtra(NewHomeActivity.EXTRA_TASK_ID, itemId);
        startActivity(intent);
    }

}

