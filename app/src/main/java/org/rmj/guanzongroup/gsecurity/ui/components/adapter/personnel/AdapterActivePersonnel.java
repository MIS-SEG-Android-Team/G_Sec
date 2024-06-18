package org.rmj.guanzongroup.gsecurity.ui.components.adapter.personnel;

import static org.rmj.guanzongroup.gsecurity.etc.DateTime.formatDateTimeToUIPreview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.data.remote.response.ActivePersonnelModel;
import org.rmj.guanzongroup.gsecurity.databinding.ListItemPersonnelActiveBinding;

import java.util.List;

public class AdapterActivePersonnel extends RecyclerView.Adapter<AdapterActivePersonnel.ActivePersonelViewHolder> {

    private final List<ActivePersonnelModel> personnelList;
    private final AdapterActivePersonnelCallback listener;

    public AdapterActivePersonnel(List<ActivePersonnelModel> personnelList, AdapterActivePersonnelCallback listener) {
        this.personnelList = personnelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ActivePersonelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActivePersonelViewHolder(
                    ListItemPersonnelActiveBinding.inflate(
                        LayoutInflater.from(
                                parent.getContext()
                        ), parent, false
                    )
                );
    }

    @Override
    public void onBindViewHolder(@NonNull ActivePersonelViewHolder holder, int position) {
        ActivePersonnelModel personnel = personnelList.get(position);
        holder.binding.personnelFullName.setText(personnel.getSUserName());
        holder.binding.personnelAssignedDuty.setText(personnel.getSWHouseNm());
        holder.binding.personnelLastVisit.setText(formatDateTimeToUIPreview(personnel.getDVisitedx()));

        holder.binding.getRoot().setOnClickListener(view -> listener.onClickPersonnel(personnel.getSUserIDxx()));
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
