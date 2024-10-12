package com.example.penggajian.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.penggajian.database.dao.DatabaseDao;
import com.example.penggajian.model.ModelDatabase;

@Database(entities = {ModelDatabase.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // Method untuk mendapatkan instance database. Metode ini tidak digunakan dalam implementasi ini.
    public static AppDatabase getInstance(Context applicationContext) {
        return null;
    }

    // Method abstrak untuk mendapatkan DAO yang digunakan untuk akses data
    public abstract DatabaseDao databaseDao();

    // Instance database yang bersifat volatile untuk memastikan thread-safety
    private static volatile AppDatabase INSTANCE;

    // Method untuk mendapatkan instance singleton dari database
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "penggajian_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
