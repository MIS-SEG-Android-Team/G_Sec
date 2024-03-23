package org.rmj.guanzongroup.gsecurity.ui.components.adapter.reports;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.data.remote.response.patrolreport.PersonnelPatrolReport;
import org.rmj.guanzongroup.gsecurity.databinding.ListItemPersonnelReportBinding;

import java.util.List;

public class AdapterPersonnelReports extends RecyclerView.Adapter<AdapterPersonnelReports.ReportsViewHolder> {

    private final List<PersonnelPatrolReport> reportsList;

    public AdapterPersonnelReports(List<PersonnelPatrolReport> reportsList) {
        this.reportsList = reportsList;
    }

    @NonNull
    @Override
    public ReportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReportsViewHolder(ListItemPersonnelReportBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReportsViewHolder holder, int position) {
        PersonnelPatrolReport report = reportsList.get(position);
        holder.binding.placeVisitedLabel.setText("Visited " + report.getSDescript());
        holder.binding.scheduleLabel.setText("Schedule: " + report.getDSchedule());
        holder.binding.timeVisitedLabel.setText("Time Visited: " + report.getDVisitedx());
        holder.binding.remarksLabel.setText("No remarks");
        if (report.getSRemarksx() == null) { return; }
        if (report.getSRemarksx().isEmpty()) { return; }
        holder.binding.remarksLabel.setText(report.getSRemarksx());
    }

    @Override
    public int getItemCount() {
        return reportsList.size();
    }

    public static class ReportsViewHolder extends RecyclerView.ViewHolder {

        public ListItemPersonnelReportBinding binding;

        public ReportsViewHolder(ListItemPersonnelReportBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
