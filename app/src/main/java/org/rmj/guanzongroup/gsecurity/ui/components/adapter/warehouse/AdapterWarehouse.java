package org.rmj.guanzongroup.gsecurity.ui.components.adapter.warehouse;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.data.room.warehouse.WarehouseEntity;
import org.rmj.guanzongroup.gsecurity.databinding.ListItemWarehouseBinding;

import java.util.List;

public class AdapterWarehouse extends RecyclerView.Adapter<AdapterWarehouse.WarehouseViewHolder> {

    private final List<WarehouseEntity> warehouseList;
    private List<WarehouseEntity> filteredList;

    private final AdapterWarehouseCallback callback;

    public AdapterWarehouse(List<WarehouseEntity> warehouseList, AdapterWarehouseCallback callback) {
        this.warehouseList = warehouseList;
        this.callback = callback;
        this.filteredList = warehouseList;
    }

    @NonNull
    @Override
    public WarehouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WarehouseViewHolder(
                ListItemWarehouseBinding.inflate(
                        LayoutInflater.from(
                                parent.getContext()
                        ), parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull WarehouseViewHolder holder, int position) {
        WarehouseEntity warehouse = warehouseList.get(position);
        holder.binding.warehouseNameLabel.setText(warehouse.getSWHouseNm());
        holder.binding.branchNameLabel.setText(warehouse.getsBranchNm());

        holder.itemView.setOnClickListener(view -> callback.onClickWarehouse(warehouse.getSWHouseID(), warehouse.getSWHouseNm()));
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public static class WarehouseViewHolder extends RecyclerView.ViewHolder{

        public ListItemWarehouseBinding binding;

        public WarehouseViewHolder(ListItemWarehouseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
