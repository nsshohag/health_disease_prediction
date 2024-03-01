package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {

    TextView appnamex;
    LottieAnimationView lottie_x;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        appnamex = findViewById(R.id.appnamex);
        lottie_x = findViewById(R.id.lottie_x);

        appnamex.animate().translationY(-300).setDuration(2000).setStartDelay(0);
        lottie_x.animate().setDuration(2000).setStartDelay(0);

        /*

                lottie_x.animate()
                .scaleX(1.3f) // Scale factor for X-axis (1.5 times bigger)
                .scaleY(1.3f) // Scale factor for Y-axis (1.5 times bigger)
                .setDuration(1000) // Animation duration in milliseconds
                .withEndAction(() -> {
                    // After the first animation, animate the text to become smaller
                    lottie_x.animate()
                            .scaleX(1.0f) // Original scale for X-axis
                            .scaleY(1.0f) // Original scale for Y-axis
                            .setDuration(1000) // Animation duration in milliseconds
                            .start();
                })
                .start();
         */

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        },2700);
    }
}