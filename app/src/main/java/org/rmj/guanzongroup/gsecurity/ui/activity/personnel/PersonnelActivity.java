package org.rmj.guanzongroup.gsecurity.ui.activity.personnel;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.ActivityPersonnelBinding;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PersonnelActivity extends AppCompatActivity {

    PersonnelActivityViewModel mViewModel;

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
    }
}