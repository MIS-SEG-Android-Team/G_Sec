package org.rmj.guanzongroup.gsecurity.ui.screens.settings.category;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentCategoryBinding;

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

        mViewModel.importingCategories().observe(getViewLifecycleOwner(), importingCategories -> {

        });

        mViewModel.hasCompleteInfo().observe(getViewLifecycleOwner(), hasCompleteInfo -> binding.addCategory.setEnabled(hasCompleteInfo));



        binding.tieCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
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
                if (s.toString().length() > 0) {
                    mViewModel.setDescription(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.refreshButton.setOnClickListener(view -> mViewModel.importCategories());

        return binding.getRoot();
    }
}