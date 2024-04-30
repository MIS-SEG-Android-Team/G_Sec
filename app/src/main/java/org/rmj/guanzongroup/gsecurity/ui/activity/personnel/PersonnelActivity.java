package org.rmj.guanzongroup.gsecurity.ui.activity.personnel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.ActivityPersonnelBinding;
import org.rmj.guanzongroup.gsecurity.service.TimeCheckService;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PersonnelActivity extends AppCompatActivity {

    PersonnelActivityViewModel mViewModel;

    private final ActivityResultLauncher<String> notificationPermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                mViewModel.setNotificationPermissionEnabled(isGranted);
            });

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PersonnelActivityViewModel.class);
        org.rmj.guanzongroup.gsecurity.databinding.ActivityPersonnelBinding binding = ActivityPersonnelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_personnel);
        BottomNavigationView navView = binding.bottomNavBar;

        NavigationUI.setupWithNavController(navView, navController);

        boolean isNotificationPermissionGranted =
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;

        mViewModel.setNotificationPermissionEnabled(isNotificationPermissionGranted);

        mViewModel.isNotificationPermissionEnabled().observe(this, isGranted -> {
            if (isGranted) {
                Intent patrolServiceIntent = new Intent(this, TimeCheckService.class);
                startForegroundService(patrolServiceIntent);
            } else {
                DialogMessage dialogMessage = new DialogMessage(this);
                dialogMessage.initDialog("Permission", "Enable notifications permission in order to make the app run properly.");
                dialogMessage.setNegativeButton("Enable", dialog -> {
                    dialog.dismiss();
                    notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS);
                });
                dialogMessage.setPositiveButton("Cancel", dialog -> {
                    dialog.dismiss();
                    finish();
                });
                dialogMessage.show();
            }
        });
    }
}