package org.rmj.guanzongroup.gsecurity.ui.components.adapter.position;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.data.room.position.PositionEntity;
import org.rmj.guanzongroup.gsecurity.databinding.ListItemPositionBinding;

import java.util.List;

public class AdapterPosition extends RecyclerView.Adapter<AdapterPosition.PositionViewHolder> {

    private final List<PositionEntity> positionList;

    private final AdapterPositionCallback callback;

    public AdapterPosition(List<PositionEntity> positionList, AdapterPositionCallback callback) {
        this.positionList = positionList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public PositionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PositionViewHolder(
                ListItemPositionBinding.inflate(
                        LayoutInflater.from(
                                parent.getContext()
                        ), parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PositionViewHolder holder, int position) {
        PositionEntity role = positionList.get(position);
        holder.binding.positionNameLabel.setText(role.getSPositnNm());
    }

    @Override
    public int getItemCount() {
        return positionList.size();
    }

    public static class PositionViewHolder extends RecyclerView.ViewHolder {

        public ListItemPositionBinding binding;

        public PositionViewHolder(ListItemPositionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
