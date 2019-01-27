package com.demotxt.myapp.splash.MenuBarang;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demotxt.myapp.splash.Database.Barang.Barang;
import com.demotxt.myapp.splash.R;

import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.BarangViewHolder>{



    class BarangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView  nbarang, stokb, jkayu;

        private BarangViewHolder(View itemView) {
            super(itemView);
            nbarang = itemView.findViewById(R.id.nbarang);
            jkayu = itemView.findViewById(R.id.jkayu);
            stokb = itemView.findViewById(R.id.stokb);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mBarangs.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    public ItemClickListener mItemClickListener;
    private final LayoutInflater mInflater;
    private List<Barang> mBarangs; // Cached copy of words

    BarangAdapter(Context context , ItemClickListener listener) {
        mInflater = LayoutInflater.from(context);
        mItemClickListener = listener;
    }

    @NonNull
    @Override
    public BarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycleview_barang, parent, false);
        return new BarangViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull BarangViewHolder holder, int position) {
        Barang current = mBarangs.get(position);
        holder.nbarang.setText(current.getNbarang());
        holder.stokb.setText(current.getStokb());
        holder.jkayu.setText(current.getJkayu());
    }

    void setWords(List<Barang> barangs){
        mBarangs = barangs;
        notifyDataSetChanged();
    }

    public List<Barang> getTasks() {
        return mBarangs;
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {

        if (mBarangs != null)
            return mBarangs.size();
        else return 0;
    }



}
