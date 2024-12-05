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

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder> {
    private List<DiscoverItem> items;
    private Context context;

    public DiscoverAdapter(List<DiscoverItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public DiscoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_discover, parent, false);
        return new DiscoverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverViewHolder holder, int position) {
        DiscoverItem item = items.get(position);
        holder.imageView.setImageResource(item.getImageResource());
        holder.titleText.setText(item.getTitle());
        holder.descriptionText.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class DiscoverViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleText;
        TextView descriptionText;

        public DiscoverViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewDiscover);
            titleText = itemView.findViewById(R.id.textViewDiscoverTitle);
            descriptionText = itemView.findViewById(R.id.textViewDiscoverDescription);
        }
    }
}
