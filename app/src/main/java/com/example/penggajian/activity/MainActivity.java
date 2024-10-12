package com.example.penggajian.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.penggajian.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Menginisialisasi komponen LinearLayout dari layout activity_main.xml
        LinearLayout inputData = findViewById(R.id.inputData);
        LinearLayout lihatData = findViewById(R.id.lihatData);

        // Menetapkan aksi klik pada LinearLayout untuk memasukkan data
        inputData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuka InputDataActivity ketika LinearLayout diklik
                Intent intent = new Intent(MainActivity.this, InputDataActivity.class);
                startActivity(intent);
            }
        });

        // Menetapkan aksi klik pada LinearLayout untuk melihat data
        lihatData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuka LihatDataActivity ketika LinearLayout diklik
                Intent intent = new Intent(MainActivity.this, LihatDataActivity.class);
                startActivity(intent);
            }
        });
    }
}
