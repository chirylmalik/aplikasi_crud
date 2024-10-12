package com.example.penggajian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.penggajian.adapter.KasirAdapter;
import com.example.penggajian.R;
import com.example.penggajian.model.ModelDatabase;
import com.example.penggajian.database.AppDatabase;

import java.util.List;

public class LihatDataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private KasirAdapter adapter;
    private AppDatabase appDatabase;
    private ImageView back;
    private Button btnHapus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data);

        // Inisialisasi komponen UI
        recyclerView = findViewById(R.id.rvListData);
        back = findViewById(R.id.btnKembali);
        btnHapus = findViewById(R.id.btnHapus);

        // Inisialisasi database
        appDatabase = AppDatabase.getDatabase(this);

        // Menyiapkan RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Memuat data dari database dan memperbarui RecyclerView
        loadData();

        // Set aksi klik pada tombol kembali
        back.setOnClickListener(v -> finish()); // Menutup aktivitas ini

        // Set aksi klik pada tombol hapus semua
        btnHapus.setOnClickListener(v -> deleteAllData()); // Menghapus semua data
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Memperbarui data jika hasil aktivitas pembaruan
            loadData();
        }
    }

    private void loadData() {
        // Memuat data dari database dalam thread terpisah
        new Thread(() -> {
            List<ModelDatabase> kasirList = appDatabase.databaseDao().getAllKasir();
            runOnUiThread(() -> {
                Log.d("LihatDataActivity", "Loading data, count: " + kasirList.size());
                if (adapter == null) {
                    // Menyiapkan adapter jika belum ada
                    adapter = new KasirAdapter(kasirList, new KasirAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(ModelDatabase modelDatabase) {
                            Intent intent = new Intent(LihatDataActivity.this, UpdateDataActivity.class);
                            intent.putExtra("modelDatabase", modelDatabase);
                            startActivityForResult(intent, 1);
                        }

                        @Override
                        public void onDeleteClick(ModelDatabase modelDatabase) {
                            deleteData(modelDatabase);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.updateData(kasirList); // Memperbarui data di adapter
                }
            });
        }).start();
    }

    private void deleteData(ModelDatabase modelDatabase) {
        // Menampilkan dialog konfirmasi sebelum menghapus data
        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Apakah Anda yakin ingin menghapus data ini?")
                .setPositiveButton("Hapus", (dialog, which) -> {
                    // Menghapus data dalam thread terpisah
                    new Thread(() -> {
                        appDatabase.databaseDao().delete(modelDatabase);
                        runOnUiThread(() -> {
                            Toast.makeText(LihatDataActivity.this, "Data telah dihapus", Toast.LENGTH_SHORT).show();
                            loadData(); // Memperbarui data setelah penghapusan
                        });
                    }).start();
                })
                .setNegativeButton("Batal", (dialog, which) -> dialog.dismiss()) // Menutup dialog jika batal
                .create()
                .show();
    }

    private void deleteAllData() {
        // Menampilkan dialog konfirmasi sebelum menghapus semua data
        new AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Apakah Anda yakin ingin menghapus semua data?")
                .setPositiveButton("Hapus Semua", (dialog, which) -> {
                    // Menghapus semua data dalam thread terpisah
                    new Thread(() -> {
                        appDatabase.databaseDao().deleteAll();
                        runOnUiThread(() -> {
                            Toast.makeText(LihatDataActivity.this, "Semua data telah dihapus", Toast.LENGTH_SHORT).show();
                            loadData(); // Memperbarui data setelah penghapusan
                        });
                    }).start();
                })
                .setNegativeButton("Batal", (dialog, which) -> dialog.dismiss()) // Menutup dialog jika batal
                .create()
                .show();
    }
}
