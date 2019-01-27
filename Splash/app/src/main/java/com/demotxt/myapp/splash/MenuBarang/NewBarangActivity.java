package com.demotxt.myapp.splash.MenuBarang;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.demotxt.myapp.splash.Database.Barang.AddBarangViewModel;
import com.demotxt.myapp.splash.Database.Barang.AddBarangViewModelFactory;
import com.demotxt.myapp.splash.Database.Barang.Barang;
import com.demotxt.myapp.splash.Database.Barang.BarangRoomDatabase;
import com.demotxt.myapp.splash.Database.Pemesanan.AppExecutor;
import com.demotxt.myapp.splash.R;

public class NewBarangActivity extends AppCompatActivity {
    public static final String NBARANG = "nbarang";
    public static final String STOK = "stokb";
    public static final String JKAYU = "jkayu";

    public static final String EXTRA_TASK_ID = "extraTaskId";

    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;
    // Constant for logging
    private static final String TAG = NewBarangActivity.class.getSimpleName();
    private int mTaskId = DEFAULT_TASK_ID;
    private BarangRoomDatabase mDb;
    private Button button;

    private EditText edit_nBarang, edit_stokb, edit_jKayu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_barang);
        edit_nBarang = findViewById(R.id.edit_nBarang);
        edit_stokb = findViewById(R.id.edit_stokb);
        edit_jKayu = findViewById(R.id.edit_jKayu);
        button = findViewById(R.id.button_savee);

        mDb = BarangRoomDatabase.getDatabase(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            button.setText("UPDATE");
            if (mTaskId == DEFAULT_TASK_ID) {
                // populate the UI
                mTaskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);

                // COMPLETED (9) Remove the logging and the call to loadTaskById, this is done in the ViewModel now
                // COMPLETED (10) Declare a AddTaskViewModelFactory using mDb and mTaskId
                AddBarangViewModelFactory factory = new AddBarangViewModelFactory(mDb, mTaskId);
                // COMPLETED (11) Declare a AddTaskViewModel variable and initialize it by calling ViewModelProviders.of
                // for that use the factory created above AddTaskViewModel
                final AddBarangViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddBarangViewModel.class);

                // COMPLETED (12) Observe the LiveData object in the ViewModel. Use it also when removing the observer
                viewModel.getTask().observe(this, new Observer<Barang>() {
                    @Override
                    public void onChanged(@Nullable Barang taskEntry) {
                        viewModel.getTask().removeObserver(this);
                        populateUI(taskEntry);
                    }
                });
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }

    private void populateUI(Barang task) {
        if (task == null) {
            return;
        }

        edit_nBarang.setText(task.getNbarang());
        edit_stokb.setText(task.getStokb());
        edit_jKayu.setText(task.getJkayu());
    }

    public void onSaveButtonClicked() {
        if (TextUtils.isEmpty(edit_nBarang.getText()) || TextUtils.isEmpty(edit_stokb.getText())
                || TextUtils.isEmpty(edit_jKayu.getText())) {
            Toast.makeText(getApplicationContext(), "Tolong isi kolom yang kosong",
                    Toast.LENGTH_LONG).show();
        } else {
            String nBarang = edit_nBarang.getText().toString();
            String stokb = edit_stokb.getText().toString();
            String jKayu = edit_jKayu.getText().toString();

            final Barang task = new Barang(nBarang, stokb, jKayu);
            AppExecutor.getInstance().networkIO().execute(new Runnable() {
                @Override
                public void run() {
                    if (mTaskId == DEFAULT_TASK_ID) {
                        // insert new task
                        mDb.barangDao().insert(task);
                    } else {
                        //update task
                        task.setId(mTaskId);
                        mDb.barangDao().updateTask(task);
                    }
                    finish();
                }
            });
        }
    }
}
