package org.rmj.guanzongroup.gsecurity.ui.components.adapter.personnel;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.data.remote.response.PersonnelModel;
import org.rmj.guanzongroup.gsecurity.databinding.ListItemPersonnelBinding;

import java.util.List;

public class AdapterPersonnel extends RecyclerView.Adapter<AdapterPersonnel.PersonnelViewHolder> {

    private final List<PersonnelModel> personnels;
    private final AdapterPersonnelCallback callback;

    public AdapterPersonnel(List<PersonnelModel> personnels, AdapterPersonnelCallback callback) {
        this.personnels = personnels;
        this.callback = callback;
    }

    @NonNull
    @Override
    public PersonnelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PersonnelViewHolder(
                ListItemPersonnelBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonnelViewHolder holder, int position) {
        PersonnelModel personnel = personnels.get(position);

        holder.binding.personnelFullName.setText(personnel.getSUserName());
        holder.binding.getRoot().setOnClickListener(view -> {
            callback.onClickPersonnel(personnel.getSUserName(), personnel.getSUserIDxx());
        });
    }

    @Override
    public int getItemCount() {
        return personnels.size();
    }

    public static class PersonnelViewHolder extends RecyclerView.ViewHolder {

        public ListItemPersonnelBinding binding;

        public PersonnelViewHolder(ListItemPersonnelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
