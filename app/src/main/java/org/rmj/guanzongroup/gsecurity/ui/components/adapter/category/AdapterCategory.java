package org.rmj.guanzongroup.gsecurity.ui.components.adapter.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.data.room.category.CategoryEntity;
import org.rmj.guanzongroup.gsecurity.databinding.ListItemCategoryBinding;

import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.CategoryViewHolder> {

    private final List<CategoryEntity> categoryEntityList;
    private final AdapterCategoryCallback callback;

    public AdapterCategory(List<CategoryEntity> categoryEntityList, AdapterCategoryCallback callback) {
        this.categoryEntityList = categoryEntityList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(
                ListItemCategoryBinding.inflate(
                        LayoutInflater.from(
                                parent.getContext()
                        ), parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryEntity category = categoryEntityList.get(position);
        holder.binding.categoryName.setText(category.getSCategory());
        holder.binding.categoryDescription.setText(category.getSDescript());
        holder.binding.getRoot().setOnClickListener(view -> callback.onClickCategory(category.getsCatIDxxx(), category.getSDescript()));
    }

    @Override
    public int getItemCount() {
        return categoryEntityList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        public ListItemCategoryBinding binding;

        public CategoryViewHolder(ListItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
