package org.rmj.guanzongroup.gsecurity.ui.components.adapter;

import static org.rmj.guanzongroup.gsecurity.etc.DateTime.formatDateTimeToUIPreview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.data.remote.response.recentactivity.RecentActivityModel;
import org.rmj.guanzongroup.gsecurity.databinding.ListItemRecentActivityBinding;

import java.util.List;

public class AdapterRecentActivity extends RecyclerView.Adapter<AdapterRecentActivity.RecentActivityViewHolder> {

    private final List<RecentActivityModel> recentActivityList;

    public AdapterRecentActivity(List<RecentActivityModel> recentActivityList) {
        this.recentActivityList = recentActivityList;
    }

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
        RecentActivityModel recentActivity = recentActivityList.get(position);

        holder.binding.recentSpecificPlace.setText(recentActivity.getSActivity());
//        holder.binding.wareHouseName.setText(recentActivity.get());
        holder.binding.lastTimeVisit.setText(formatDateTimeToUIPreview(recentActivity.getDVisitedx()));
    }

    @Override
    public int getItemCount() {
        return recentActivityList.size();
    }

    public static class RecentActivityViewHolder extends RecyclerView.ViewHolder {

        public ListItemRecentActivityBinding binding;

        public RecentActivityViewHolder(ListItemRecentActivityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
