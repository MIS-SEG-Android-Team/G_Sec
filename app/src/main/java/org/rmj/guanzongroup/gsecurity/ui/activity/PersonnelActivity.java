package org.rmj.guanzongroup.gsecurity.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.ActivityPersonnelBinding;
import org.rmj.guanzongroup.gsecurity.ui.activity.AuthenticationActivity;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PersonnelActivity extends AppCompatActivity {

//    @Inject
//    VMPersonnelActivity mViewModel;

    private DialogLoad dialogLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mViewModel = new ViewModelProvider(PersonnelActivity.this).get(VMPersonnelActivity.class);
        org.rmj.guanzongroup.gsecurity.databinding.ActivityPersonnelBinding binding = ActivityPersonnelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_personnel);
        BottomNavigationView navView = binding.bottomNavBar;

        dialogLoad = new DialogLoad(this);

        NavigationUI.setupWithNavController(navView, navController);
    }
}