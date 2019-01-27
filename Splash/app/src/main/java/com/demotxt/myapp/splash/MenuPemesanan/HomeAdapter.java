package com.demotxt.myapp.splash.MenuPemesanan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demotxt.myapp.splash.Database.Pemesanan.Word;
import com.demotxt.myapp.splash.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.WordViewHolder> {


        class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private final TextView nama, tanggal, kusen, stok, alamat, telp, jenisKayu, harga;

            private WordViewHolder(View itemView) {
                super(itemView);
                nama = itemView.findViewById(R.id.nama);
                tanggal = itemView.findViewById(R.id.tanggal);
                kusen = itemView.findViewById(R.id.kusen);
                stok = itemView.findViewById(R.id.stok);
                alamat = itemView.findViewById(R.id.alamat);
                telp = itemView.findViewById(R.id.telp);
                jenisKayu = itemView.findViewById(R.id.jenisKayu);
                harga = itemView.findViewById(R.id.harga);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int elementId = mWords.get(getAdapterPosition()).getId();
                mItemClickListener.onItemClickListener(elementId);
            }
        }

        final private ItemClickListener mItemClickListener;
        private final LayoutInflater mInflater;
        private List<Word> mWords; // Cached copy of words

        HomeAdapter(Context context , ItemClickListener listener) {
            mInflater = LayoutInflater.from(context);
            mItemClickListener = listener;
        }

        @NonNull
        @Override
        public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.recycleview_item, parent, false);
            return new WordViewHolder(itemView);
        }

        public interface ItemClickListener {
            void onItemClickListener(int itemId);
        }


        @Override
        public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
            Word current = mWords.get(position);
            holder.nama.setText(current.getNama());
            holder.tanggal.setText(current.getTanggal());
            holder.kusen.setText(current.getKusen());
            holder.stok.setText(current.getStok());
            holder.alamat.setText(current.getAlamat());
            holder.telp.setText(current.getTelp());
            holder.jenisKayu.setText(current.getJenisKayu());
            holder.harga.setText(current.getHarga());
        }

        void setWords(List<Word> words){
            mWords = words;
            notifyDataSetChanged();
        }

        public List<Word> getTasks() {
            return mWords;
        }

        // getItemCount() is called many times, and when it is first called,
        // mWords has not been updated (means initially, it's null, and we can't return null).
        @Override
        public int getItemCount() {
            if (mWords != null)
                return mWords.size();
            else return 0;
        }

    }

