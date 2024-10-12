package com.example.penggajian.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.penggajian.model.ModelDatabase;

import java.util.List;

@Dao
public interface DatabaseDao {
    @Insert
    void insert(ModelDatabase modelDatabase);

    @Query("SELECT * FROM kasir")
    List<ModelDatabase> getAllKasir();

    @Update
    void update(ModelDatabase modelDatabase);

    @Delete
    void delete(ModelDatabase modelDatabase);

    @Query("DELETE FROM kasir")
    void deleteAll();

    @Query("SELECT * FROM kasir WHERE NIK = :nik LIMIT 1")
    ModelDatabase getKasirById(String nik);
}
