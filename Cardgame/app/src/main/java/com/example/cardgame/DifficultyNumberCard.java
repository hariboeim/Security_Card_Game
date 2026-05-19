package com.example.cardgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DifficultyNumberCard extends View {

    private int level = 1;
    private boolean isSelected = false;
    private boolean isBattle = false;

    private final Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint subPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF rectF = new RectF();

    private int colorBgDefault;
    private int colorBgSelected;
    private int colorAccentCyan;
    private int colorAccentRed;
    private int colorTextWhite;
    private int colorTextMuted;

    public DifficultyNumberCard(Context context) {
        super(context);
        init();
    }

    public DifficultyNumberCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DifficultyNumberCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        colorBgDefault = getContext().getColor(R.color.card_bg);
        colorBgSelected = getContext().getColor(R.color.card_bg_selected);
        colorAccentCyan = getContext().getColor(R.color.accent_cyan);
        colorAccentRed = getContext().getColor(R.color.accent_red);
        colorTextWhite = getContext().getColor(R.color.text_white);
        colorTextMuted = getContext().getColor(R.color.text_muted);

        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(dpToPx(22));
        textPaint.setFakeBoldText(true);

        subPaint.setTextAlign(Paint.Align.CENTER);
        subPaint.setTextSize(dpToPx(8));

        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(dpToPx(2));
    }

    public void setLevel(int level) {
        this.level = level;
        this.isBattle = level >= 6;
        invalidate();
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        this.isSelected = selected;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float w = getWidth();
        float h = getHeight();
        float r = dpToPx(10);

        rectF.set(0, 0, w, h);

        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(isSelected ? colorBgSelected : colorBgDefault);
        canvas.drawRoundRect(rectF, r, r, bgPaint);

        int accentColor = isBattle ? colorAccentRed : colorAccentCyan;

        borderPaint.setColor(accentColor);
        borderPaint.setAlpha(isSelected ? 255 : 60);
        canvas.drawRoundRect(rectF, r, r, borderPaint);

        textPaint.setColor(isSelected ? accentColor : colorTextWhite);
        float cx = w / 2f;
        float cy = h / 2f - dpToPx(4);
        canvas.drawText(String.valueOf(level), cx, cy, textPaint);

        subPaint.setColor(isSelected ? accentColor : colorTextMuted);
        subPaint.setAlpha(isSelected ? 200 : 100);

        String sub = isBattle ? "배틀" : "퍼즐";
        canvas.drawText(sub, cx, cy + dpToPx(14), subPaint);
    }

    private float dpToPx(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }
}