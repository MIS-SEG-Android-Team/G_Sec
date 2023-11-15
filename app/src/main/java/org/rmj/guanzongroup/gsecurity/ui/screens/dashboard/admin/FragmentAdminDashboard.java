package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.admin;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentAdminDashboardBinding;

public class FragmentAdminDashboard extends Fragment {

    private VMAdminDashboard mViewModel;

    private FragmentAdminDashboardBinding binding;

    public static FragmentAdminDashboard newInstance() {
        return new FragmentAdminDashboard();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMAdminDashboard.class);

        binding = FragmentAdminDashboardBinding.inflate(getLayoutInflater());

        BottomNavigationView navView = binding.bottomNavBar;
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment_admin_dashboard);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(navView, navController);
        return binding.getRoot();
    }
}