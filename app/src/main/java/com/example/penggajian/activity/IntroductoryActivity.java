package com.example.penggajian.activity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.penggajian.R;

public class IntroductoryActivity extends AppCompatActivity {

    ImageView logo, splashImg;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        // Inisialisasi komponen UI
        logo = findViewById(R.id.logo);
        splashImg = findViewById(R.id.bg_splash);
        lottieAnimationView = findViewById(R.id.lottie);

        // Menyiapkan animasi
        splashImg.animate().translationY(-3200).setDuration(1000).setStartDelay(3000).start();
        logo.animate().translationY(-3200).setDuration(2200).setStartDelay(3000).start();
        lottieAnimationView.animate().translationY(2500).setDuration(1500).setStartDelay(3000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Tidak ada aksi
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Berpindah ke MainActivity setelah animasi selesai
                Intent intent = new Intent(IntroductoryActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Menutup aktivitas ini
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Tidak ada aksi
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Tidak ada aksi
            }
        }).start();
    }
}
