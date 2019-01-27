package com.demotxt.myapp.splash.MenuPemesanan;

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

import com.demotxt.myapp.splash.Database.Pemesanan.AddTaskViewModel;
import com.demotxt.myapp.splash.Database.Pemesanan.AddTaskViewModelFactory;
import com.demotxt.myapp.splash.Database.Pemesanan.AppExecutor;
import com.demotxt.myapp.splash.Database.Pemesanan.Word;
import com.demotxt.myapp.splash.Database.Pemesanan.WordRoomDatabase;
import com.demotxt.myapp.splash.R;

public class NewHomeActivity extends AppCompatActivity {
    public static final String NAMA = "nama";
    public static final String TANGGAL = "tanggal";
    public static final String KUSEN = "kusen";
    public static final String STOK = "stok";
    public static final String ALAMAT = "alamat";
    public static final String TELP = "telp";
    public static final String JENISKAYU = "jenisKayu";
    public static final String HARGA = "harga";

    public static final String EXTRA_TASK_ID = "extraTaskId";

    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;
    // Constant for logging
    private static final String TAG = NewHomeActivity.class.getSimpleName();
    private int mTaskId = DEFAULT_TASK_ID;
    private WordRoomDatabase mDb;
    private Button button;

    private EditText edit_nama, edit_tanggal, edit_kusen, edit_stok, edit_alamat, edit_telp, edit_jenisKayu, edit_harga;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home);
        edit_nama = findViewById(R.id.edit_nama);
        edit_tanggal = findViewById(R.id.edit_tanggal);
        edit_kusen = findViewById(R.id.edit_kusen);
        edit_stok = findViewById(R.id.edit_stok);
        edit_alamat = findViewById(R.id.edit_alamat);
        edit_telp = findViewById(R.id.edit_telp);
        edit_jenisKayu = findViewById(R.id.edit_jenisKayu);
        edit_harga = findViewById(R.id.edit_harga);
        button = findViewById(R.id.button_save);

        mDb = WordRoomDatabase.getDatabase(getApplicationContext());

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
                AddTaskViewModelFactory factory = new AddTaskViewModelFactory(mDb, mTaskId);
                // COMPLETED (11) Declare a AddTaskViewModel variable and initialize it by calling ViewModelProviders.of
                // for that use the factory created above AddTaskViewModel
                final AddTaskViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddTaskViewModel.class);

                // COMPLETED (12) Observe the LiveData object in the ViewModel. Use it also when removing the observer
                viewModel.getTask().observe(this, new Observer<Word>() {
                    @Override
                    public void onChanged(@Nullable Word taskEntry) {
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

    private void populateUI(Word task) {
        if (task == null) {
            return;
        }

        edit_nama.setText(task.getNama());
        edit_tanggal.setText(task.getTanggal());
        edit_kusen.setText(task.getKusen());
        edit_stok.setText(task.getStok());
        edit_alamat.setText(task.getAlamat());
        edit_telp.setText(task.getTelp());
        edit_jenisKayu.setText(task.getJenisKayu());
        edit_harga.setText(task.getHarga());
    }

    public void onSaveButtonClicked() {
        if (TextUtils.isEmpty(edit_nama.getText()) || TextUtils.isEmpty(edit_tanggal.getText())
                || TextUtils.isEmpty(edit_kusen.getText()) || TextUtils.isEmpty(edit_stok.getText())
                || TextUtils.isEmpty(edit_alamat.getText()) || TextUtils.isEmpty(edit_telp.getText())
                || TextUtils.isEmpty(edit_jenisKayu.getText()) || TextUtils.isEmpty(
                        edit_harga.getText())) {
            Toast.makeText(getApplicationContext(), "Tolong isi kolom yang kosong",
                    Toast.LENGTH_LONG).show();
        } else {
            String nama = edit_nama.getText().toString();
            String tanggal = edit_tanggal.getText().toString();
            String kusen = edit_kusen.getText().toString();
            String stok = edit_stok.getText().toString();
            String alamat = edit_alamat.getText().toString();
            String telp = edit_telp.getText().toString();
            String jenisKayu = edit_jenisKayu.getText().toString();
            String harga = edit_harga.getText().toString();

            final Word task = new Word(nama, tanggal, kusen, stok, alamat, telp, jenisKayu, harga);
            AppExecutor.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    if (mTaskId == DEFAULT_TASK_ID) {
                        // insert new task
                        mDb.wordDao().insert(task);
                    } else {
                        //update task
                        task.setId(mTaskId);
                        mDb.wordDao().updateTask(task);
                    }
                    finish();
                }
            });
        }
    }
}
