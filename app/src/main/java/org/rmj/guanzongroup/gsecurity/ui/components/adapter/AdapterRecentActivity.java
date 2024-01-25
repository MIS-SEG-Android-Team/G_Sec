package org.rmj.guanzongroup.gsecurity.ui.components.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.databinding.ListItemRecentActivityBinding;

import java.util.List;

public class AdapterRecentActivity extends RecyclerView.Adapter<AdapterRecentActivity.RecentActivityViewHolder> {

//    private final List<RecentActivity> recentActivityList;
//
//    public AdapterRecentActivity(List<RecentActivity> recentActivityList) {
//        this.recentActivityList = recentActivityList;
//    }

    @NonNull
    @Override
    public RecentActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecentActivityViewHolder(
                ListItemRecentActivityBinding.inflate(
                        LayoutInflater.from(
                                parent.getContext()
                        ), parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecentActivityViewHolder holder, int position) {
//        RecentActivity recentActivity = recentActivityList.get(position);
//
//        holder.binding.recentSpecificPlace.setText(recentActivity.getSpecificNFC());
//        holder.binding.wareHouseName.setText(recentActivity.getAssignedWarehouse());
//        holder.binding.lastTimeVisit.setText(recentActivity.getLastTimeVisit());
    }

    @Override
    public int getItemCount() {
//        return recentActivityList.size();
        return 0;
    }

    public static class RecentActivityViewHolder extends RecyclerView.ViewHolder {

        public ListItemRecentActivityBinding binding;

        public RecentActivityViewHolder(ListItemRecentActivityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
