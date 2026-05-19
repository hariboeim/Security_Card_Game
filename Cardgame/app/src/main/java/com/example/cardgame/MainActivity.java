package com.example.cardgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnPlay, btnCardCollection, btnQuiz;
    private ImageView ivShield;
    private TextView tvTitle, tvSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

        setContentView(R.layout.activity_main);

        initViews();
        startEntranceAnimation();
        setListeners();
    }

    private void initViews() {
        btnPlay = findViewById(R.id.btnPlay);
        btnCardCollection = findViewById(R.id.btnCardCollection);
        btnQuiz = findViewById(R.id.btnQuiz);
        ivShield = findViewById(R.id.ivShield);
        tvTitle = findViewById(R.id.tvTitle);
        tvSubtitle = findViewById(R.id.tvSubtitle);
    }

    private void startEntranceAnimation() {
        View[] targets = {
                ivShield,
                tvTitle,
                tvSubtitle,
                btnPlay,
                btnCardCollection,
                btnQuiz
        };

        for (View v : targets) {
            v.setAlpha(0f);
            v.setTranslationY(20f);
        }

        long delay = 100L;

        for (View v : targets) {
            v.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(500)
                    .setStartDelay(delay)
                    .start();

            delay += 80L;
        }

        ivShield.postDelayed(this::pulseCycle, 1200L);
    }

    private void pulseCycle() {
        ivShield.animate()
                .scaleX(1.06f)
                .scaleY(1.06f)
                .setDuration(1200)
                .withEndAction(() -> ivShield.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(1200)
                        .withEndAction(this::pulseCycle)
                        .start())
                .start();
    }

    private void setListeners() {
        btnPlay.setOnClickListener(v ->
                animateButtonPress(v, () -> {
                    Intent intent = new Intent(MainActivity.this, DifficultyActivity.class);
                    startActivity(intent);
                })
        );

        btnCardCollection.setOnClickListener(v ->
                animateButtonPress(v, () -> {
                    Intent intent = new Intent(MainActivity.this, CardCollectionActivity.class);
                    startActivity(intent);
                })
        );

        btnQuiz.setOnClickListener(v ->
                animateButtonPress(v, () -> {
                    Intent intent = new Intent(MainActivity.this, DifficultyActivity.class);
                    startActivity(intent);
                })
        );
    }

    private void animateButtonPress(View view, Runnable onEnd) {
        view.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(80)
                .withEndAction(() -> view.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(80)
                        .withEndAction(onEnd)
                        .start())
                .start();
    }
}