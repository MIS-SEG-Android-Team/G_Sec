package org.rmj.guanzongroup.gsecurity.ui.components.adapter.schedule;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.PersonnelPatrolSchedule;
import org.rmj.guanzongroup.gsecurity.databinding.ListItemPatrolScheduleBinding;

import java.util.List;

public class AdapterPatrolSchedule extends RecyclerView.Adapter<AdapterPatrolSchedule.PatrolScheduleViewHolder> {

    private final List<PersonnelPatrolSchedule> schedules;
    private final AdapterPatrolScheduleCallback callback;

    public AdapterPatrolSchedule(List<PersonnelPatrolSchedule> schedules, AdapterPatrolScheduleCallback callback) {
        this.schedules = schedules;
        this.callback = callback;
    }

    @NonNull
    @Override
    public PatrolScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PatrolScheduleViewHolder(
                ListItemPatrolScheduleBinding.inflate(
                        LayoutInflater.from(
                                parent.getContext()),
                        parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PatrolScheduleViewHolder holder, int position) {
        PersonnelPatrolSchedule schedule = schedules.get(position);

        holder.binding.patrolNumber.setText("Patrol " +schedule.getNSchedule());
        holder.binding.scheduleTime.setText(schedule.getDTimexxxx());
        holder.binding.editButton.setOnClickListener( view -> callback.onClickEdit(position));
        holder.binding.deleteButton.setOnClickListener( view -> callback.onClickRemove(position));
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public static class PatrolScheduleViewHolder extends RecyclerView.ViewHolder{

        public ListItemPatrolScheduleBinding binding;

        public PatrolScheduleViewHolder(ListItemPatrolScheduleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
