package org.rmj.guanzongroup.gsecurity.ui.components.adapter.personnel;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.data.remote.response.PersonnelModel;
import org.rmj.guanzongroup.gsecurity.databinding.ListItemPersonnelSelectionBinding;

import java.util.List;

public class AdapterPersonnelSelection extends RecyclerView.Adapter<AdapterPersonnelSelection.PersonnelViewHolder> {

    private final List<PersonnelModel> personnels;
    private final AdapterPersonnelSelectionCallback callback;

    public AdapterPersonnelSelection(List<PersonnelModel> personnels, AdapterPersonnelSelectionCallback callback) {
        this.personnels = personnels;
        this.callback = callback;
    }

    @NonNull
    @Override
    public PersonnelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PersonnelViewHolder(
                ListItemPersonnelSelectionBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonnelViewHolder holder, int position) {
        PersonnelModel personnel = personnels.get(position);

        holder.binding.personnelFullName.setText(personnel.getSUserName());
        holder.binding.getRoot().setOnClickListener(view -> {
            callback.onClickPersonnel(personnel);
        });
    }

    @Override
    public int getItemCount() {
        return personnels.size();
    }

    public static class PersonnelViewHolder extends RecyclerView.ViewHolder {

        public ListItemPersonnelSelectionBinding binding;

        public PersonnelViewHolder(ListItemPersonnelSelectionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
