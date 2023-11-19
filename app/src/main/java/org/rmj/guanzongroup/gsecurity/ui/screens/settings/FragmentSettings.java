package org.rmj.guanzongroup.gsecurity.ui.screens.settings;

import androidx.lifecycle.ViewModelProvider;

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

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        navController = Objects.requireNonNull(navHostFragment).getNavController();

        binding.addNfcTagButton.setOnClickListener(view -> {
            navController.navigate(R.id.action_fragmentAdminDashboard_to_fragmentAddPlace);
        });

        return binding.getRoot();
    }
}