package org.rmj.guanzongroup.gsecurity.ui.components.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.databinding.ListItemPersonnelActiveBinding;
import org.rmj.guanzongroup.gsecurity.pojo.user.Personnel;

import java.util.List;

public class AdapterActivePersonnel extends RecyclerView.Adapter<AdapterActivePersonnel.ActivePersonelViewHolder> {

    private final List<Personnel> personnelList;

    public AdapterActivePersonnel(List<Personnel> personnelList) {
        this.personnelList = personnelList;
    }

    @NonNull
    @Override
    public ActivePersonelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActivePersonelViewHolder(
                    ListItemPersonnelActiveBinding.inflate(
                        LayoutInflater.from(
                                parent.getContext()
                        )
                    )
                );
    }

    @Override
    public void onBindViewHolder(@NonNull ActivePersonelViewHolder holder, int position) {
        Personnel personnel = personnelList.get(position);
        holder.binding.personnelFullName.setText(personnel.getPersonnelName());
        holder.binding.personnelAssignedDuty.setText(personnel.getWareHouseDuty());
        holder.binding.personnelLastVisit.setText(personnel.getLastVisited());
    }

    @Override
    public int getItemCount() {
        return personnelList.size();
    }

    public static class ActivePersonelViewHolder extends RecyclerView.ViewHolder{

        ListItemPersonnelActiveBinding binding;

        public ActivePersonelViewHolder(ListItemPersonnelActiveBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
