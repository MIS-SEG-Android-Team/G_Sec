package org.rmj.guanzongroup.gsecurity.ui.screens.settings.category;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.databinding.FragmentCategoryBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.category.AdapterCategory;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

import javax.inject.Inject;

public class FragmentCategory extends Fragment {

    @Inject
    VMCategory mViewModel;

    private FragmentCategoryBinding binding;

    public static FragmentCategory newInstance() {
        return new FragmentCategory();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMCategory.class);
        binding = FragmentCategoryBinding.inflate(getLayoutInflater());
        DialogLoad dialogLoad = new DialogLoad(requireActivity());

        mViewModel.importingCategories().observe(getViewLifecycleOwner(), importingCategories -> {
            if (importingCategories) {

            }
        });

        mViewModel.savingCategory().observe(getViewLifecycleOwner(), savingCategory -> {
            if (savingCategory) {
                dialogLoad.show("Saving new category...");
            } else {
                dialogLoad.dismiss();
            }
        });

        mViewModel.hasCompleteInfo().observe(getViewLifecycleOwner(), hasCompleteInfo -> binding.addCategory.setEnabled(hasCompleteInfo));

        mViewModel.categorySave().observe(getViewLifecycleOwner(), categorySave -> {
            if (categorySave) {
                new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, "A new category saved!", dialog -> {
                    dialog.dismiss();
                    mViewModel.resetAddCategory();
                    binding.tieCategory.setText("");
                    binding.tieDescription.setText("");
                    mViewModel.importCategories();
                }).showDialog();
            }
        });

        mViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            if (categories == null) {
               return;
            }

            if(categories.size() == 0) {
                return;
            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            binding.categoryList.setLayoutManager(linearLayoutManager);
            binding.categoryList.setAdapter(new AdapterCategory(categories, (categoryID, categoryName) -> { }));
        });

        binding.tieCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    mViewModel.setCategory(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.tieCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    mViewModel.setDescription(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.refreshButton.setOnClickListener(view -> mViewModel.importCategories());
        binding.addCategory.setOnClickListener(view -> mViewModel.addCategory());

        return binding.getRoot();
    }
}