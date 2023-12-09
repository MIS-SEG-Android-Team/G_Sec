package org.rmj.guanzongroup.gsecurity.ui.screens.splashscreen;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentSplashscreenBinding;
import org.rmj.guanzongroup.gsecurity.ui.activity.AdminActivity;
import org.rmj.guanzongroup.gsecurity.ui.activity.PersonnelActivity;

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMSplashscreen.class);
        binding = FragmentSplashscreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_authentication);
        navController = Objects.requireNonNull(navHostFragment).getNavController();

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        mViewModel.setDeviceID(
                Settings.Secure.getString(requireActivity().getContentResolver(),
                        Settings.Secure.ANDROID_ID)
        );

        mViewModel.setFirebaseToken();

        mViewModel.startApp(new OnLoadApplicationCallback() {
            @Override
            public void onProgress(int progress) {
                Log.d("Splashscreen", "Loading application..." + progress);
            }

            @Override
            public void onFinished(String args) {
//                if(currentUser == null) {
                    navController.navigate(R.id.action_fragmentSplashscreen_to_fragmentLogin);
//                } else {
//                    startActivity(new Intent(requireActivity(), PersonnelActivity.class));
//                    requireActivity().finish();
//                }
            }

            @Override
            public void onFailed(String message) {

            }
        });

        return view;
    }
}