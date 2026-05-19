package com.example.cardgame;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    public interface OnCardClickListener {
        void onCardClick(CardItem card);
    }

    private final List<CardItem> cards;
    private final OnCardClickListener listener;

    public CardAdapter(List<CardItem> cards, OnCardClickListener listener) {
        this.cards = cards;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);

        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardItem card = cards.get(position);
        holder.bind(card, listener);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardRoot;
        private final TextView tvCardType;
        private final TextView tvPower;
        private final TextView tvCardName;
        private final ImageView ivCardIcon;
        private final View viewLocked;
        private final ImageView ivLock;
        private final View viewRarityGlow;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);

            cardRoot = (CardView) itemView;
            tvCardType = itemView.findViewById(R.id.tvCardType);
            tvPower = itemView.findViewById(R.id.tvPower);
            tvCardName = itemView.findViewById(R.id.tvCardName);
            ivCardIcon = itemView.findViewById(R.id.ivCardIcon);
            viewLocked = itemView.findViewById(R.id.viewLocked);
            ivLock = itemView.findViewById(R.id.ivLock);
            viewRarityGlow = itemView.findViewById(R.id.viewRarityGlow);
        }

        void bind(CardItem card, OnCardClickListener listener) {
            tvCardName.setText(card.getName());
            tvCardType.setText(card.getType());
            tvPower.setText(String.valueOf(card.getPower()));

            ivCardIcon.setImageResource(card.getIconResId());

            int colorRes = card.getTypeColorRes();
            int color = itemView.getContext().getColor(colorRes);

            tvCardType.setTextColor(color);
            ivCardIcon.setImageTintList(ColorStateList.valueOf(color));

            if (card.isRare() && card.isUnlocked()) {
                viewRarityGlow.setVisibility(View.VISIBLE);
            } else {
                viewRarityGlow.setVisibility(View.GONE);
            }

            if (card.isUnlocked()) {
                viewLocked.setVisibility(View.GONE);
                ivLock.setVisibility(View.GONE);
                tvPower.setVisibility(View.VISIBLE);
                tvCardName.setAlpha(1f);
                ivCardIcon.setAlpha(1f);
            } else {
                viewLocked.setVisibility(View.VISIBLE);
                ivLock.setVisibility(View.VISIBLE);
                tvPower.setVisibility(View.INVISIBLE);
                tvCardName.setAlpha(0f);
                ivCardIcon.setAlpha(0.3f);
            }

            cardRoot.setOnClickListener(v -> {
                v.animate()
                        .scaleX(0.94f)
                        .scaleY(0.94f)
                        .setDuration(70)
                        .withEndAction(() -> v.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(70)
                                .start())
                        .start();

                listener.onCardClick(card);
            });
        }
    }
}