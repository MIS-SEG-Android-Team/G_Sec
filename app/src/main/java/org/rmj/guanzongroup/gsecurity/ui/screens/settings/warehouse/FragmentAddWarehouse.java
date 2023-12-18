package org.rmj.guanzongroup.gsecurity.ui.screens.settings.warehouse;

import android.app.Dialog;
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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentAddWarehouseBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.branch.AdapterBranch;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

import java.util.Objects;

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
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_admin);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();
        DialogLoad dialogLoad = new DialogLoad(requireActivity());
        DialogMessage dialogMessage = new DialogMessage(requireActivity());

        // Observables
        mViewModel.savingWarehouse().observe(getViewLifecycleOwner(), isSavingWarehouse -> {
            if(isSavingWarehouse) {
                dialogLoad.show("Adding new warehouse info...");
            } else {
                dialogLoad.dismiss();
            }
        });

        mViewModel.successfullySave().observe(getViewLifecycleOwner(), successfullySave -> {
            if(successfullySave) {
                dialogMessage.initDialog("Warehouse", "Warehouse added!");
                dialogMessage.setPositiveButton("Close", dialog -> {
                    dialog.dismiss();
                    navController.popBackStack();
                });
                dialogMessage.show();
            }
        });

        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if(errorMessage == null) {
                return;
            }
            if(errorMessage.isEmpty()) {
                return;
            }
            new DialogResult(requireActivity(), DialogResult.RESULT.FAILED, errorMessage, Dialog::dismiss).showDialog();
        });

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
        mViewModel.hasCompleteInfo().observe(getViewLifecycleOwner(), hasCompleteInfo -> binding.saveButton.setEnabled(hasCompleteInfo));

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

        binding.saveButton.setOnClickListener(view -> mViewModel.saveWarehouse());

        return binding.getRoot();
    }
}