package org.rmj.guanzongroup.gsecurity.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAdminBinding binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setContentView(binding.getRoot());

//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.fragmentPersonnelList,
//                R.id.fragmentRecentActivities,
//                R.id.fragmentVisitArea,
//                R.id.fragmentNotifications,
//                R.id.fragmentSettings,
//                R.id.fragmentAddPlace)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_admin);
        BottomNavigationView navView = binding.bottomNavBar;

        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if(navDestination.getId() == R.id.fragmentPersonnelList ||
                        navDestination.getId() == R.id.fragmentVisitArea ||
                        navDestination.getId() == R.id.fragmentSettings) {
                    binding.bottomNavBar.setVisibility(View.VISIBLE);
                } else {
                    binding.bottomNavBar.setVisibility(View.GONE);
                }
            }
        });
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }
}