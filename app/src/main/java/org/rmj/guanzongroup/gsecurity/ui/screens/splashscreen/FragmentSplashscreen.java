package org.rmj.guanzongroup.gsecurity.ui.screens.splashscreen;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentSplashscreenBinding;

import java.util.Objects;

import javax.inject.Inject;

public class FragmentSplashscreen extends Fragment {

    @Inject
    VMSplashscreen mViewModel;

    private FragmentSplashscreenBinding binding;

    private NavController navController;

    private FirebaseAuth firebaseAuth;

    public static FragmentSplashscreen newInstance() {
        return new FragmentSplashscreen();
    }

    @SuppressLint("HardwareIds")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMSplashscreen.class);
        binding = FragmentSplashscreenBinding.inflate(getLayoutInflater());
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_authentication);
        navController = Objects.requireNonNull(navHostFragment).getNavController();

        firebaseAuth = FirebaseAuth.getInstance();

        mViewModel.setDeviceID(
                Settings.Secure.getString(requireActivity().getContentResolver(),
                        Settings.Secure.ANDROID_ID)
        );

        mViewModel.setFirebaseToken();

        navController.navigate(R.id.action_fragmentSplashscreen_to_fragmentLogin);
        return binding.getRoot();
    }
}