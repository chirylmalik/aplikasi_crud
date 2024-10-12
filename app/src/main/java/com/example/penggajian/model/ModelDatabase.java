package com.example.penggajian.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Kelas ModelDatabase adalah representasi dari entitas dalam tabel "kasir" di database.
 * Kelas ini juga mengimplementasikan Parcelable untuk mendukung transfer objek antar aktivitas.
 */
@Entity(tableName = "kasir")
public class ModelDatabase implements Parcelable {
    @PrimaryKey
    @NonNull
    private String NIK; // Nomor Induk Kependudukan, kunci utama untuk entitas ini
    private String Nama; // Nama lengkap
    private String Email; // Alamat email

    // Konstruktor default
    public ModelDatabase(){}

    // Konstruktor untuk mem-parcel data dari Parcel
    protected ModelDatabase(Parcel in) {
        NIK = in.readString();
        Nama = in.readString();
        Email = in.readString();
    }

    // Creator untuk membuat instance ModelDatabase dari Parcel
    public static final Creator<ModelDatabase> CREATOR = new Creator<ModelDatabase>() {
        @Override
        public ModelDatabase createFromParcel(Parcel in) {
            return new ModelDatabase(in);
        }

        @Override
        public ModelDatabase[] newArray(int size) {
            return new ModelDatabase[size];
        }
    };

    // Getter dan Setter untuk NIK
    @NonNull
    public String getNIK() {
        return NIK;
    }

    public void setNIK(@NonNull String NIK) {
        this.NIK = NIK;
    }

    // Getter dan Setter untuk Nama
    public String getNama() {
        return Nama;
    }

    public void setNama(String Nama) {
        this.Nama = Nama;
    }

    // Getter dan Setter untuk Email
    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    // Method untuk mendeskripsikan konten Parcel (tidak digunakan dalam implementasi ini)
    @Override
    public int describeContents() {
        return 0;
    }

    // Method untuk menulis data ke Parcel
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(NIK);
        parcel.writeString(Nama);
        parcel.writeString(Email);
    }
}
