package org.rmj.guanzongroup.gsecurity.ui.screens.authentication.login;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentLoginBinding;
import org.rmj.guanzongroup.gsecurity.pojo.login.LoginCredentials;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

import java.util.Objects;

public class FragmentLogin extends Fragment {

    public static FragmentLogin newInstance() {
        return new FragmentLogin();
    }

    private VMLogin mViewModel;

    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMLogin.class);
        binding = FragmentLoginBinding.inflate(getLayoutInflater());

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();

        binding.forgotPasswordButton.setOnClickListener(view -> navController.navigate(R.id.fragmentForgotPassword));

        binding.signupButton.setOnClickListener(view -> navController.navigate(R.id.fragmentSignUp));

        binding.loginButton.setOnClickListener(view1 -> {
//            DialogResult.viewResult(requireActivity(), DialogResult.RESULT.SUCCESS, "Login in successfully").showDialog();
//            LoginCredentials loginCredentials = new LoginCredentials(
//                    Objects.requireNonNull(binding.tieEmail.getText()).toString().trim(),
//                    Objects.requireNonNull(binding.tiePassword.getText()).toString().trim()
//            );
//
//            mViewModel.login(loginCredentials, new LoginCallback() {
//                @Override
//                public void onLogin(String title, String message) {
//
//                }
//
//                @Override
//                public void onSuccess(String message) {
//                    navController.navigate(R.id.action_fragmentLogin_to_fragmentAdminDashboard);
//                }
//
//                @Override
//                public void onFailed(String message) {
//
//                }
//            });
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                DialogMessage dialogMessage = new DialogMessage(requireActivity());
                dialogMessage.initDialog("GSecure", "Exit GSecure?");
                dialogMessage.setPositiveButton("Yes", dialog -> {
                    dialog.dismiss();
                    requireActivity().finish();
                });
                dialogMessage.setNegativeButton("No", Dialog::dismiss);
                dialogMessage.show();
            }
        });

        return binding.getRoot();
    }
}