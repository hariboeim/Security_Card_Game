package com.example.cardgame;

/**
 * CardItem — 카드 데이터 모델
 */
public class CardItem {

    private String name;
    private String type;
    private int power;
    private int iconResId;
    private boolean unlocked;

    public CardItem(String name, String type, int power, int iconResId, boolean unlocked) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.iconResId = iconResId;
        this.unlocked = unlocked;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

    public int getIconResId() {
        return iconResId;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    // Setters
    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public void setPower(int power) {
        this.power = power;
    }

    /**
     * 카드 타입에 따른 색상 리소스 반환
     */
    public int getTypeColorRes() {
        switch (type) {
            case "공격":
                return R.color.accent_red;

            case "방어":
                return R.color.accent_cyan;

            case "퍼즐":
                return R.color.accent_gold;

            default:
                return R.color.text_muted;
        }
    }

    /**
     * 레어 카드 여부
     */
    public boolean isRare() {
        return power >= 90;
    }
}