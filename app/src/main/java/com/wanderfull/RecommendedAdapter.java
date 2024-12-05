package com.wanderfull;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import jp.wasabeef.blurry.Blurry;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder> {
    private List<RecommendedItem> items;
    private Context context;

    public RecommendedAdapter(List<RecommendedItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_recommended, parent, false);
        return new RecommendedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedViewHolder holder, int position) {
        RecommendedItem item = items.get(position);
        holder.imageView.setImageResource(item.getImageResource());
        holder.titleText.setText(item.getTitle());
        holder.descriptionText.setText(item.getDescription());
        holder.locationText.setText(item.getLocation());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class RecommendedViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView blurImage;
        TextView titleText;
        TextView descriptionText;
        TextView locationText;

        public RecommendedViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewRecommended);
            titleText = itemView.findViewById(R.id.textViewTitle);
            descriptionText = itemView.findViewById(R.id.textViewDescription);
            locationText = itemView.findViewById(R.id.textViewLocation);
        }
    }
}
