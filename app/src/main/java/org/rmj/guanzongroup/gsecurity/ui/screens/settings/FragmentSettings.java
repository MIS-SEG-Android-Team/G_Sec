package org.rmj.guanzongroup.gsecurity.ui.screens.settings;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentSettingsBinding;
import org.rmj.guanzongroup.gsecurity.ui.activity.AuthenticationActivity;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

import java.util.Objects;

public class FragmentSettings extends Fragment {

    private VMSettings mViewModel;

    private FragmentSettingsBinding binding;

    private NavController navController;

    public static FragmentSettings newInstance() {
        return new FragmentSettings();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMSettings.class);
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());
        DialogLoad dialogLoad = new DialogLoad(requireActivity());

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        navController = Objects.requireNonNull(navHostFragment).getNavController();

        binding.addNfcTagButton.setOnClickListener(view -> {
            navController.navigate(R.id.action_fragmentAdminDashboard_to_fragmentAddPlace);
        });

        binding.warehouseButton.setOnClickListener(view -> {
            navController.navigate(R.id.action_fragmentAdminDashboard_to_fragmentAddWarehouse);
        });

        binding.logoutButton.setOnClickListener(view -> {
            mViewModel.logoutUser(new SettingsCallback.LogoutUserCallback() {
                @Override
                public void onLoad(String message) {
                    dialogLoad.show(message);
                }

                @Override
                public void onSuccess() {
                    startActivity(new Intent(requireActivity(), AuthenticationActivity.class));
                    requireActivity().finish();
                }

                @Override
                public void onFailed(String message) {
                    dialogLoad.dismiss();
                    new DialogResult(requireActivity(), DialogResult.RESULT.FAILED, message).showDialog();
                }
            });
        });

        return binding.getRoot();
    }
}