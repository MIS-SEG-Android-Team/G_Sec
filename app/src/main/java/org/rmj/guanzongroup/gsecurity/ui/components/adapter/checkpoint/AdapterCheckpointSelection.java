package org.rmj.guanzongroup.gsecurity.ui.components.adapter.checkpoint;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.databinding.ListItemCheckpointSelectionBinding;
import org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.route.Checkpoint;

import java.util.List;

public class AdapterCheckpointSelection extends RecyclerView.Adapter<AdapterCheckpointSelection.CheckpointViewHolder> {

    private final List<Checkpoint> checkpoints;
    private final AdapterCheckpointSelectionCallback callback;

    public AdapterCheckpointSelection(
            List<Checkpoint> checkpoints,
            AdapterCheckpointSelectionCallback callback
    ) {
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
        Checkpoint checkpoint = checkpoints.get(position);
        holder.binding.nfcDescription.setText(checkpoint.getsDescript());
        holder.binding.checkpointAdded.setChecked(checkpoint.isSelected());
        holder.binding.checkpointAdded.setOnCheckedChangeListener((buttonView, isChecked) -> callback.onSelectCheckpoint(position, isChecked));
    }

    @Override
    public int getItemCount() {return checkpoints.size();}

    public static class CheckpointViewHolder extends RecyclerView.ViewHolder {

        public ListItemCheckpointSelectionBinding binding;

        public CheckpointViewHolder(ListItemCheckpointSelectionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
