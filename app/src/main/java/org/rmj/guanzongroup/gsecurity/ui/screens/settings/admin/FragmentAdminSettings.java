package org.rmj.guanzongroup.gsecurity.ui.screens.settings.admin;

import static androidx.core.content.ContextCompat.registerReceiver;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentAdminSettingsBinding;
import org.rmj.guanzongroup.gsecurity.ui.activity.AuthenticationActivity;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.inject.Inject;

public class FragmentAdminSettings extends Fragment {

    @Inject
    VMAdminSettings mViewModel;

    private FragmentAdminSettingsBinding binding;

    private NavController navController;

    public static FragmentAdminSettings newInstance() {
        return new FragmentAdminSettings();
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMAdminSettings.class);
        binding = FragmentAdminSettingsBinding.inflate(getLayoutInflater());
        DialogLoad dialogLoad = new DialogLoad(requireActivity());
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_admin);
        navController = Objects.requireNonNull(navHostFragment).getNavController();

        // region Observables

        mViewModel.getLastLog().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s != null){

                    if (!s.isEmpty()){

                        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        String logDate = s;

                        if (!currentDate.equals(logDate)){
                            mViewModel.logoutUser();
                        }
                    }
                }

            }
        });

        mViewModel.isLoggingOut().observe(getViewLifecycleOwner(), isLoggingOut -> {
            if (isLoggingOut) {
                dialogLoad.show("Signing out...");
            } else {
                dialogLoad.dismiss();
            }
        });
        mViewModel.hasLogout().observe(getViewLifecycleOwner(), hasLogOut -> {
            if (hasLogOut) {
                requireActivity().finish();
                requireActivity().startActivity(new Intent(requireActivity(), AuthenticationActivity.class));
            }
        });
        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage == null) {
                return;
            }

            if (errorMessage.isEmpty()) {
               return;
            }

            new DialogResult(requireActivity(), DialogResult.RESULT.FAILED, errorMessage, Dialog::dismiss).showDialog();
        });

        // endregion

        // region Navigation Buttons
        binding.addPosition.setOnClickListener(view -> navController.navigate(R.id.action_fragmentSettings_to_fragmentPosition));
        binding.addPersonnelButton.setOnClickListener(view -> navController.navigate(R.id.action_fragmentSettings_to_fragmentAddPersonnel));
        binding.personnelList.setOnClickListener(view -> navController.navigate(R.id.action_fragmentSettings_to_fragmentPersonnelList2));
        binding.createScheduleButton.setOnClickListener(view -> navController.navigate(R.id.action_fragmentSettings_to_fragmentWarehouseSelection));
        binding.nfcCategoryButton.setOnClickListener(view -> navController.navigate(R.id.action_fragmentSettings_to_fragmentNfcCategory));
        binding.addNfcTagButton.setOnClickListener(view -> navController.navigate(R.id.action_fragmentSettings_to_fragmentAddPlace));
        binding.warehouseButton.setOnClickListener(view -> navController.navigate(R.id.action_fragmentSettings_to_fragmentAddWarehouse));
        // endregion

        binding.logoutButton.setOnClickListener(view -> {
            DialogMessage dialogMessage = new DialogMessage(requireActivity());
            dialogMessage.initDialog("Logout", "Logout account?");
            dialogMessage.setNegativeButton("NO", Dialog::dismiss);
            dialogMessage.setPositiveButton("Yes", dialog -> {
                dialog.dismiss();
                mViewModel.logoutUser();
            });
            dialogMessage.show();
        });

        return binding.getRoot();
    }

}