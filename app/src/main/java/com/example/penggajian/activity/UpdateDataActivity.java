package com.example.penggajian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.penggajian.R;
import com.example.penggajian.database.AppDatabase;
import com.example.penggajian.model.ModelDatabase;

public class UpdateDataActivity extends AppCompatActivity {

    private EditText etNik, etName, etEmail;
    private Button btnSimpan;
    private ImageView back;
    private AppDatabase appDatabase;
    private ModelDatabase modelDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        // Inisialisasi komponen UI
        etNik = findViewById(R.id.etNik);
        etName = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        btnSimpan = findViewById(R.id.btnSimpan);
        back = findViewById(R.id.btnKembali);

        // Inisialisasi database
        appDatabase = AppDatabase.getDatabase(this);

        // Mengambil data dari Intent
        modelDatabase = getIntent().getParcelableExtra("modelDatabase");
        if (modelDatabase == null) {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
            finish(); // Menutup aktivitas jika data tidak ada
            return;
        }

        // Menampilkan data pada field input
        etNik.setText(modelDatabase.getNIK());
        etName.setText(modelDatabase.getNama());
        etEmail.setText(modelDatabase.getEmail());

        // Set aksi klik pada tombol kembali
        back.setOnClickListener(v -> finish()); // Menutup aktivitas ini

        // Set aksi klik pada tombol simpan
        btnSimpan.setOnClickListener(v -> {
            try {
                updateData(); // Memperbarui data
            } catch (Exception e) {
                Log.e("UpdateDataActivity", "Error updating data", e);
                Toast.makeText(UpdateDataActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateData() {
        // Mengambil input dari EditText
        String nik = etNik.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        // Validasi input
        if (nik.isEmpty() || name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        // Memperbarui data pada model
        modelDatabase.setNIK(nik);
        modelDatabase.setNama(name);
        modelDatabase.setEmail(email);

        // Memperbarui data di database dalam thread terpisah
        new Thread(() -> {
            try {
                appDatabase.databaseDao().update(modelDatabase);
                runOnUiThread(() -> {
                    Toast.makeText(UpdateDataActivity.this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                    // Mengirim hasil kembali ke LihatDataActivity
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish(); // Menutup aktivitas ini
                });
            } catch (Exception e) {
                Log.e("UpdateDataActivity", "Error updating data in the database", e);
                runOnUiThread(() -> Toast.makeText(UpdateDataActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
