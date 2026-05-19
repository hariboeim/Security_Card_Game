package com.example.cardgame;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class CardCollectionActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvCollectedCount;
    private TextInputEditText etSearch;
    private ChipGroup chipGroup;
    private RecyclerView rvCards;

    private final List<CardItem> allCards = new ArrayList<>();
    private final List<CardItem> filteredCards = new ArrayList<>();

    private String currentCategory = "전체";
    private String currentQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );

        setContentView(R.layout.activity_card_collection);

        initViews();
        populateSampleCards();
        setupRecyclerView();
        setListeners();
        applyFilter();
        updateCollectedCount();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvCollectedCount = findViewById(R.id.tvCollectedCount);
        etSearch = findViewById(R.id.etSearch);
        chipGroup = findViewById(R.id.chipGroup);
        rvCards = findViewById(R.id.rvCards);
    }

    private void populateSampleCards() {
        int defaultIcon = android.R.drawable.ic_dialog_info;

        allCards.add(new CardItem("SQL 인젝션", "공격", 85, defaultIcon, true));
        allCards.add(new CardItem("XSS 공격", "공격", 70, defaultIcon, true));
        allCards.add(new CardItem("CSRF", "공격", 60, defaultIcon, true));
        allCards.add(new CardItem("버퍼 오버플로우", "공격", 90, defaultIcon, false));
        allCards.add(new CardItem("DDoS", "공격", 75, defaultIcon, false));
        allCards.add(new CardItem("피싱", "공격", 55, defaultIcon, true));
        allCards.add(new CardItem("랜섬웨어", "공격", 95, defaultIcon, false));
        allCards.add(new CardItem("제로데이 익스플로잇", "공격", 100, defaultIcon, false));

        allCards.add(new CardItem("방화벽", "방어", 70, defaultIcon, true));
        allCards.add(new CardItem("WAF", "방어", 75, defaultIcon, true));
        allCards.add(new CardItem("2단계 인증", "방어", 65, defaultIcon, true));
        allCards.add(new CardItem("TLS 암호화", "방어", 80, defaultIcon, false));
        allCards.add(new CardItem("패치 관리", "방어", 60, defaultIcon, false));
        allCards.add(new CardItem("침입 탐지 시스템", "방어", 85, defaultIcon, false));
        allCards.add(new CardItem("허니팟", "방어", 90, defaultIcon, false));

        allCards.add(new CardItem("해시 함수 이해", "퍼즐", 50, defaultIcon, true));
        allCards.add(new CardItem("공개키 암호화", "퍼즐", 60, defaultIcon, true));
        allCards.add(new CardItem("OAuth 흐름", "퍼즐", 65, defaultIcon, false));
        allCards.add(new CardItem("DNS 스푸핑", "퍼즐", 70, defaultIcon, false));
        allCards.add(new CardItem("인증 vs 인가", "퍼즐", 55, defaultIcon, true));

        filteredCards.addAll(allCards);
    }

    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvCards.setLayoutManager(layoutManager);
        rvCards.setHasFixedSize(true);

        /*
         * CardAdapter.java가 아직 없어서 adapter 연결은 제거했습니다.
         * 따라서 현재 화면은 RecyclerView 구조만 준비된 상태입니다.
         */
    }

    private void setListeners() {
        btnBack.setOnClickListener(v -> finish());

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentQuery = s.toString().trim();
                applyFilter();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (checkedIds.isEmpty()) return;

            int id = checkedIds.get(0);

            if (id == R.id.chipAll) {
                currentCategory = "전체";
            } else if (id == R.id.chipAttack) {
                currentCategory = "공격";
            } else if (id == R.id.chipDefense) {
                currentCategory = "방어";
            } else if (id == R.id.chipPuzzle) {
                currentCategory = "퍼즐";
            } else if (id == R.id.chipRare) {
                currentCategory = "레어";
            }

            applyFilter();
        });
    }

    private void applyFilter() {
        filteredCards.clear();

        for (CardItem card : allCards) {
            boolean matchCategory;

            if ("전체".equals(currentCategory)) {
                matchCategory = true;
            } else if ("레어".equals(currentCategory)) {
                matchCategory = card.getPower() >= 90;
            } else {
                matchCategory = card.getType().equals(currentCategory);
            }

            boolean matchQuery = currentQuery.isEmpty()
                    || card.getName().contains(currentQuery);

            if (matchCategory && matchQuery) {
                filteredCards.add(card);
            }
        }

        Toast.makeText(this, "카드 " + filteredCards.size() + "개", Toast.LENGTH_SHORT).show();
    }

    private void updateCollectedCount() {
        int unlocked = 0;

        for (CardItem card : allCards) {
            if (card.isUnlocked()) {
                unlocked++;
            }
        }

        tvCollectedCount.setText(unlocked + " / " + allCards.size());
    }
}