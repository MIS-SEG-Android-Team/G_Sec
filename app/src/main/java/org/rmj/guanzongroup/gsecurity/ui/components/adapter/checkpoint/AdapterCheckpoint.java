package org.rmj.guanzongroup.gsecurity.ui.components.adapter.checkpoint;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.databinding.ListItemCheckpointBinding;

import java.util.List;

public class AdapterCheckpoint extends RecyclerView.Adapter<AdapterCheckpoint.CheckpointViewHolder> {

    @NonNull
    @Override
    public CheckpointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckpointViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class CheckpointViewHolder extends RecyclerView.ViewHolder {

        public ListItemCheckpointBinding binding;

        public CheckpointViewHolder(ListItemCheckpointBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
