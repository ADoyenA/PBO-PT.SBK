package com.demotxt.myapp.splash.Database.Barang;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


@Entity(tableName = "barang")
public class Barang {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "nbarang")
    private String nbarang;

    @Nullable
    @ColumnInfo(name = "jkayu")
    private String jkayu;

    @Nullable
    @ColumnInfo(name = "stokb")
    private String stokb;



    @Ignore
    public Barang(@NonNull String nBarang, @Nullable String stokb, @Nullable String jKayu) {
        this.nbarang = nBarang;
        this.stokb = stokb;
        this.jkayu = jKayu;
    }

    public Barang(int id, @NonNull String nbarang, @Nullable String stokb, @Nullable String jkayu ) {
        this.id = id;
        this.nbarang = nbarang;
        this.stokb = stokb;
        this.jkayu = jkayu;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNbarang() {
        return nbarang;
    }

    @Nullable
    public String getJkayu() {
        return jkayu;
    }

    @Nullable
    public String getStokb() {
        return stokb;
    }
}
