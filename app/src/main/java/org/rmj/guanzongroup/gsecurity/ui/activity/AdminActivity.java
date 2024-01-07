package org.rmj.guanzongroup.gsecurity.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.ActivityAdminBinding;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAdminBinding binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_admin);
        BottomNavigationView navView = binding.bottomNavBar;

        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener((navController12, navDestination, bundle) -> {
            if(navDestination.getId() == R.id.fragmentPersonnelList ||
                    navDestination.getId() == R.id.fragmentVisitArea ||
                    navDestination.getId() == R.id.fragmentSettings) {
                binding.bottomNavBar.setVisibility(View.VISIBLE);
            } else {
                binding.bottomNavBar.setVisibility(View.GONE);
            }
        });
        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
            getViewModelStore().clear();
        });
    }
}