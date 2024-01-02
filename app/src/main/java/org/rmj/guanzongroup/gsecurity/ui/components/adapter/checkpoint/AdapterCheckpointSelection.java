package org.rmj.guanzongroup.gsecurity.ui.components.adapter.checkpoint;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.data.room.checkpoint.NFCDeviceEntity;
import org.rmj.guanzongroup.gsecurity.databinding.ListItemCheckpointBinding;
import org.rmj.guanzongroup.gsecurity.databinding.ListItemCheckpointSelectionBinding;
import org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.route.SelectedCheckpoints;

import java.util.List;

public class AdapterCheckpointSelection extends RecyclerView.Adapter<AdapterCheckpointSelection.CheckpointViewHolder> {

    private final List<SelectedCheckpoints> checkpoints;
    private final AdapterCheckpointSelectionCallback callback;

    public AdapterCheckpointSelection(List<SelectedCheckpoints> checkpoints, AdapterCheckpointSelectionCallback callback) {
        this.checkpoints = checkpoints;
        this.callback = callback;
    }

    @NonNull
    @Override
    public CheckpointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckpointViewHolder(
                ListItemCheckpointSelectionBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CheckpointViewHolder holder, int position) {
        SelectedCheckpoints checkpoint = checkpoints.get(position);
        holder.binding.nfcDescription.setText(checkpoint.getsDescript());
        holder.binding.nfcPayload.setText(checkpoint.getsPayloadx());
        holder.binding.checkpointAdded.setChecked(checkpoint.isAdded());
    }

    @Override
    public int getItemCount() {
        return checkpoints.size();
    }

    public static class CheckpointViewHolder extends RecyclerView.ViewHolder {

        public ListItemCheckpointSelectionBinding binding;

        public CheckpointViewHolder(ListItemCheckpointSelectionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
