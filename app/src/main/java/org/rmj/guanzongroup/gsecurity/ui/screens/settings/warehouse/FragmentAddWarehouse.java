package org.rmj.guanzongroup.gsecurity.ui.screens.settings.warehouse;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.rmj.guanzongroup.gsecurity.databinding.FragmentAddWarehouseBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.branch.AdapterBranch;

import javax.inject.Inject;

public class FragmentAddWarehouse extends Fragment {

    @Inject
    VMAddWarehouse mViewModel;

    public static FragmentAddWarehouse newInstance() {
        return new FragmentAddWarehouse();
    }

    private FragmentAddWarehouseBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMAddWarehouse.class);
        binding = FragmentAddWarehouseBinding.inflate(getLayoutInflater());

        mViewModel.getBranchList().observe(getViewLifecycleOwner(), branchList -> {

            if(branchList == null) {
                return;
            }

            if(branchList.size() == 0) {
                return;
            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            AdapterBranch adapterBranch = new AdapterBranch(branchList, (branchCode, branchName) -> {
                mViewModel.setBranchCode(branchCode);
                mViewModel.setBranchName(branchName);
            });

            binding.branchList.setLayoutManager(linearLayoutManager);
            binding.branchList.setAdapter(adapterBranch);

            binding.tieSearchBranch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length() > 0)
                        adapterBranch.getFilter().filter(s.toString());
                    else
                        adapterBranch.getFilter().filter("");
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        });

        mViewModel.getbranchName().observe(getViewLifecycleOwner(), branchName -> binding.tieBranchName.setText(branchName));

        binding.tieWarehouseName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0) {
                    mViewModel.setWarehouseName(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mViewModel.hasCompleteInfo().observe(getViewLifecycleOwner(), hasCompleteInfo -> binding.saveButton.setEnabled(hasCompleteInfo));

        return binding.getRoot();
    }
}