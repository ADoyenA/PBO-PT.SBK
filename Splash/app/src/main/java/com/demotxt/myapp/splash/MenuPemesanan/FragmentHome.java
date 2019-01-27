package com.demotxt.myapp.splash.MenuPemesanan;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demotxt.myapp.splash.Database.Pemesanan.AppExecutor;
import com.demotxt.myapp.splash.Database.Pemesanan.MyViewModel;
import com.demotxt.myapp.splash.Database.Pemesanan.WordRoomDatabase;
import com.demotxt.myapp.splash.Database.Pemesanan.Word;
import com.demotxt.myapp.splash.R;

import java.util.List;

public class FragmentHome extends Fragment implements HomeAdapter.ItemClickListener  {


    View v;


    public FragmentHome() {

    }
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private MyViewModel mWordViewModel;
    private HomeAdapter adapter;
    private WordRoomDatabase mDb;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.home_fragment,container,false);
         recyclerView =  v.findViewById(R.id.recyclerview);
         adapter =  new HomeAdapter(v.getContext(),this);
         recyclerView.setAdapter(adapter);
         recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
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

            FloatingActionButton add = v.findViewById(R.id.add);
            add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NewHomeActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
       });
        mDb = WordRoomDatabase.getDatabase(getContext());
        setupViewModel();

        return v;

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
        Intent intent = new Intent(getActivity(), NewHomeActivity.class);
        intent.putExtra(NewHomeActivity.EXTRA_TASK_ID, itemId);
        startActivity(intent);
    }
}
