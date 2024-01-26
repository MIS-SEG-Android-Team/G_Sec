package org.rmj.guanzongroup.gsecurity.ui.screens.splashscreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.messaging.FirebaseMessaging;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentSplashscreenBinding;
import org.rmj.guanzongroup.gsecurity.ui.activity.AdminActivity;

import java.util.Objects;

import javax.inject.Inject;

@SuppressLint("CustomSplashScreen")
public class FragmentSplashscreen extends Fragment {

    @Inject
    VMSplashscreen mViewModel;

    public static FragmentSplashscreen newInstance() {
        return new FragmentSplashscreen();
    }

    @SuppressLint("HardwareIds")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMSplashscreen.class);
        org.rmj.guanzongroup.gsecurity.databinding.FragmentSplashscreenBinding binding = FragmentSplashscreenBinding.inflate(getLayoutInflater());
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_authentication);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }

                    String token = task.getResult();
                    mViewModel.setFirebaseToken(token);
                });

        mViewModel.setDeviceID(
                Settings.Secure.getString(requireActivity().getContentResolver(),
                        Settings.Secure.ANDROID_ID)
        );

        if (mViewModel.hasSession() && mViewModel.isAdmin()) {
            Intent intent = new Intent(requireActivity(), AdminActivity.class);
            requireActivity().startActivity(intent);
            requireActivity().finish();
        } else {
            navController.navigate(R.id.action_fragmentSplashscreen_to_fragmentLogin);
        }
        return binding.getRoot();
    }
}