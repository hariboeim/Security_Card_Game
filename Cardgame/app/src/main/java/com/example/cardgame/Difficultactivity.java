package com.example.cardgame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

public class DifficultyActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TabLayout tabModeSwitch;
    private Button btnConfirm;

    private ImageView ivModeIcon;
    private TextView tvModeLabel, tvModeRange, tvModeDesc;

    private GridLayout gridNumbers;
    private int selectedDifficulty = 1;
    private boolean isNumberTab = true;

    private static final int GRADE_MIDDLE = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

        setContentView(R.layout.activity_difficulty);

        initViews();
        setNumberCardListeners();
        setListeners();
        highlightCard(selectedDifficulty);
        updateModeDescription(selectedDifficulty);
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tabModeSwitch = findViewById(R.id.tabModeSwitch);
        btnConfirm = findViewById(R.id.btnConfirm);

        ivModeIcon = findViewById(R.id.ivModeIcon);
        tvModeLabel = findViewById(R.id.tvModeLabel);
        tvModeRange = findViewById(R.id.tvModeRange);
        tvModeDesc = findViewById(R.id.tvModeDesc);

        gridNumbers = findViewById(R.id.gridNumbers);
    }

    private void setNumberCardListeners() {
        for (int i = 0; i < gridNumbers.getChildCount(); i++) {
            final int level = i + 1;
            View card = gridNumbers.getChildAt(i);

            card.setTag(level);
            card.setOnClickListener(v -> selectNumberCard(level));
        }
    }

    private void setListeners() {
        btnBack.setOnClickListener(v -> finish());

        tabModeSwitch.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                isNumberTab = tab.getPosition() == 0;

                if (isNumberTab) {
                    selectedDifficulty = 1;
                } else {
                    selectedDifficulty = GRADE_MIDDLE;
                }

                highlightCard(selectedDifficulty);
                updateModeDescription(selectedDifficulty);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        btnConfirm.setOnClickListener(v -> launchGame());
    }

    private void selectNumberCard(int level) {
        selectedDifficulty = level;
        highlightCard(level);
        updateModeDescription(level);

        View card = gridNumbers.getChildAt(level - 1);
        card.animate()
                .scaleX(0.92f)
                .scaleY(0.92f)
                .setDuration(60)
                .withEndAction(() -> card.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(60)
                        .start())
                .start();
    }

    private void highlightCard(int selected) {
        for (int i = 0; i < gridNumbers.getChildCount(); i++) {
            View card = gridNumbers.getChildAt(i);

            if (i + 1 == selected) {
                card.setAlpha(1f);
                card.setScaleX(1.06f);
                card.setScaleY(1.06f);
            } else {
                card.setAlpha(0.65f);
                card.setScaleX(1f);
                card.setScaleY(1f);
            }
        }
    }

    private void updateModeDescription(int level) {
        boolean isPuzzle = level <= 5;

        if (isPuzzle) {
            ivModeIcon.setImageResource(android.R.drawable.ic_dialog_info);
            ivModeIcon.setColorFilter(getColor(R.color.accent_cyan));

            tvModeLabel.setText("퍼즐 모드");
            tvModeRange.setText("Lv. 1 - 5");
            tvModeDesc.setText("AI가 출제한 보안 문제를 카드로 해결하는 퍼즐 방식입니다.");

            btnConfirm.setBackgroundTintList(getColorStateList(R.color.accent_cyan));
        } else {
            ivModeIcon.setImageResource(android.R.drawable.ic_dialog_alert);
            ivModeIcon.setColorFilter(getColor(R.color.accent_red));

            tvModeLabel.setText("배틀 모드");
            tvModeRange.setText("Lv. 6 - 10");
            tvModeDesc.setText("AI와 카드를 배분받아 대결하는 배틀 방식입니다.");

            btnConfirm.setBackgroundTintList(getColorStateList(R.color.accent_red));
        }
    }

    private void launchGame() {
        Toast.makeText(
                this,
                "선택한 난이도: " + selectedDifficulty,
                Toast.LENGTH_SHORT
        ).show();
    }
}