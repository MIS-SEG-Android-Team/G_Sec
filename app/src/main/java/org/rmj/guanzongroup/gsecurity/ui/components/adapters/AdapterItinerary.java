package org.rmj.guanzongroup.gsecurity.ui.components.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterItinerary extends RecyclerView.Adapter<AdapterItinerary.ItineraryViewHolder> {


    @NonNull
    @Override
    public ItineraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItineraryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ItineraryViewHolder extends RecyclerView.ViewHolder {

        public ItineraryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
