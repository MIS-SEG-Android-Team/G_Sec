package org.rmj.guanzongroup.gsecurity.ui.components.adapter.category;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.databinding.ListItemCategoryBinding;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.CategoryViewHolder> {


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        public ListItemCategoryBinding binding;

        public CategoryViewHolder(ListItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
