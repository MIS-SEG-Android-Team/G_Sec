package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.warehouse;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentWarehouseSelectionBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.warehouse.AdapterWarehouse;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;

import java.util.Objects;

import javax.inject.Inject;

public class FragmentWarehouseSelection extends Fragment {

    @Inject
    VMWarehouseSelection mViewModel;

    private FragmentWarehouseSelectionBinding binding;

    public static FragmentWarehouseSelection newInstance() {
        return new FragmentWarehouseSelection();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMWarehouseSelection.class);
        binding = FragmentWarehouseSelectionBinding.inflate(getLayoutInflater());

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_admin);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();

        mViewModel.importingWarehouse().observe(getViewLifecycleOwner(), importingWarehouse-> {
            if (importingWarehouse) {
                binding.loadingWarehouse.setVisibility(View.VISIBLE);
                binding.warehouseList.setVisibility(View.GONE);
            } else {
                binding.loadingWarehouse.setVisibility(View.GONE);
                binding.warehouseList.setVisibility(View.VISIBLE);
            }
        });

        mViewModel.getWarehouseList().observe(getViewLifecycleOwner(), warehouseList -> {
            if (warehouseList == null || warehouseList.size() == 0) {
                binding.noRecordMessage.setVisibility(View.VISIBLE);
                return;
            }

            binding.noRecordMessage.setVisibility(View.GONE);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            binding.warehouseList.setLayoutManager(linearLayoutManager);
            binding.warehouseList.setAdapter(
                    new AdapterWarehouse(warehouseList, ((warehouseID, warehouseName) -> {
                        DialogMessage dialogMessage = new DialogMessage(requireActivity());
                        dialogMessage.initDialog("Create Schedule", "Creating patrol for " + warehouseName);
                        dialogMessage.setPositiveButton("Create", dialog -> {
                            mViewModel.setWarehouse(warehouseID);
                            navController.navigate(R.id.action_fragmentWarehouseSelection_to_fragmentRouteSelection);
                            dialog.dismiss();
                        });
                        dialogMessage.setNegativeButton("Cancel", Dialog::dismiss);
                        dialogMessage.show();
            })));
        });

        return binding.getRoot();
    }
}