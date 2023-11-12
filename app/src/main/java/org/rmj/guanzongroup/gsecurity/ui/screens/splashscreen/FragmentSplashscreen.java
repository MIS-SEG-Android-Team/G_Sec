package org.rmj.guanzongroup.gsecurity.ui.screens.splashscreen;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentSplashscreenBinding;

import java.util.Objects;

public class FragmentSplashscreen extends Fragment {

    private VMSplashscreen mViewModel;

    private FragmentSplashscreenBinding binding;

    public static FragmentSplashscreen newInstance() {
        return new FragmentSplashscreen();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMSplashscreen.class);
        binding = FragmentSplashscreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        mViewModel.startApp(new OnLoadApplicationCallback() {
            @Override
            public void onProgress(int progress) {
                Log.d("Splashscreen", "Loading application..." + progress);
            }

            @Override
            public void onFinished(String args) {
                NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
                NavController navController = Objects.requireNonNull(navHostFragment).getNavController();
                navController.navigate(R.id.fragmentLogin);
            }

            @Override
            public void onFailed(String message) {

            }
        });

        return view;
    }
}