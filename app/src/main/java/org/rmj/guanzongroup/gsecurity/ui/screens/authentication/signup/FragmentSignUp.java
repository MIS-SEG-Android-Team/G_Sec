package org.rmj.guanzongroup.gsecurity.ui.screens.authentication.signup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentSignUpBinding;

import java.util.Objects;

public class FragmentSignUp extends Fragment {

    private VMSignUp mViewModel;

    private FragmentSignUpBinding binding;

    private FirebaseAuth firebaseAuth;

    public static FragmentSignUp newInstance() {
        return new FragmentSignUp();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMSignUp.class);
        binding = FragmentSignUpBinding.inflate(getLayoutInflater());
        firebaseAuth = FirebaseAuth.getInstance();

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_authentication);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();

        binding.registerButton.setOnClickListener(view -> {
            
        });

        return binding.getRoot();
    }
}