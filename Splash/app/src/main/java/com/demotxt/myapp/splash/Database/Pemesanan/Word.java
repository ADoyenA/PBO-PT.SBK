package com.demotxt.myapp.splash.Database.Pemesanan;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A basic class representing an entity that is a row in a one-column database table.
 *
 * @ Entity - You must annotate the class as an entity and supply a table name if not class name.
 * @ PrimaryKey - You must identify the primary key.
 * @ ColumnInfo - You must supply the column name if it is different from the variable name.
 *
 * See the documentation for the full rich set of annotations.
 * https://developer.android.com/topic/libraries/architecture/room.html
 */

@Entity(tableName = "pemesanan")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "nama")
    private String nama;

    @Nullable
    @ColumnInfo(name = "tanggal")
    private String tanggal;

    @Nullable
    @ColumnInfo(name = "kusen")
    private String kusen;

    @Nullable
    @ColumnInfo(name = "stok")
    private String stok;

    @Nullable
    @ColumnInfo(name = "alamat")
    private String alamat;

    @Nullable
    @ColumnInfo(name = "notelp")
    private String telp;

    @Nullable
    @ColumnInfo(name = "jenisKayu")
    private String jenisKayu;

    @Nullable
    @ColumnInfo(name = "harga")
    private String harga;

    @Ignore
    public Word(@NonNull String nama, @Nullable String tanggal, @Nullable String kusen,
                @Nullable String stok, @Nullable String alamat, @Nullable String telp,
                @Nullable String jenisKayu, @Nullable String harga) {
        this.nama = nama;
        this.tanggal = tanggal;
        this.kusen = kusen;
        this.stok = stok;
        this.alamat = alamat;
        this.telp = telp;
        this.jenisKayu = jenisKayu;
        this.harga = harga;
    }

    public Word(int id, @NonNull String nama, @Nullable String tanggal, @Nullable String kusen,
                @Nullable String stok, @Nullable String alamat, @Nullable String telp,
                @Nullable String jenisKayu, @Nullable String harga) {
        this.id = id;
        this.nama = nama;
        this.tanggal = tanggal;
        this.kusen = kusen;
        this.stok = stok;
        this.alamat = alamat;
        this.telp = telp;
        this.jenisKayu = jenisKayu;
        this.harga = harga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNama() {
        return nama;
    }

    @Nullable
    public String getKusen() {
        return kusen;
    }

    @Nullable
    public String getTanggal() {
        return tanggal;
    }

    @Nullable
    public String getStok() {
        return stok;
    }

    @Nullable
    public String getAlamat() {
        return alamat;
    }

    @Nullable
    public String getTelp() {
        return telp;
    }

    @Nullable
    public String getJenisKayu() {
        return jenisKayu;
    }

    @Nullable
    public String getHarga() {
        return harga;
    }
}
