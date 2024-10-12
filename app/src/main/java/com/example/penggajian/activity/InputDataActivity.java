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

public class InputDataActivity extends AppCompatActivity {

    private EditText etNik, etName, etEmail;
    private Button btnSimpan;
    private ImageView back;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        // Inisialisasi komponen UI
        etNik = findViewById(R.id.etNik);
        etName = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        btnSimpan = findViewById(R.id.btnSimpan);
        back = findViewById(R.id.btnKembali);

        // Inisialisasi database
        appDatabase = AppDatabase.getDatabase(this);

        // Set aksi klik pada tombol kembali
        back.setOnClickListener(v -> finish()); // Menutup aktivitas ini

        // Set aksi klik pada tombol simpan
        btnSimpan.setOnClickListener(v -> {
            try {
                saveData(); // Menyimpan data
            } catch (Exception e) {
                Log.e("InputDataActivity", "Error saving data", e);
                Toast.makeText(InputDataActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveData() {
        // Mengambil input dari EditText
        String nik = etNik.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        // Validasi input
        if (nik.isEmpty() || name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        // Membuat objek ModelDatabase
        ModelDatabase modelDatabase = new ModelDatabase();
        modelDatabase.setNIK(nik);
        modelDatabase.setNama(name);
        modelDatabase.setEmail(email);

        // Menyimpan data ke database dalam thread terpisah
        new Thread(() -> {
            try {
                appDatabase.databaseDao().insert(modelDatabase);
                runOnUiThread(() -> {
                    Toast.makeText(InputDataActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    // Berpindah ke LihatDataActivity
                    Intent intent = new Intent(InputDataActivity.this, LihatDataActivity.class);
                    startActivity(intent);
                    finish(); // Menutup aktivitas ini
                });
            } catch (Exception e) {
                Log.e("InputDataActivity", "Error saving data to the database", e);
                runOnUiThread(() -> Toast.makeText(InputDataActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
