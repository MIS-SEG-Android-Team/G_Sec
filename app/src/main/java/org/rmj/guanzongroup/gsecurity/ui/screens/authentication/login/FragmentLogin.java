package org.rmj.guanzongroup.gsecurity.ui.screens.authentication.login;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentLoginBinding;
import org.rmj.guanzongroup.gsecurity.pojo.login.LoginCredentials;
import org.rmj.guanzongroup.gsecurity.ui.activity.AdminActivity;
import org.rmj.guanzongroup.gsecurity.ui.activity.PersonnelActivity;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
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
        DialogLoad dialogLoad = new DialogLoad(requireActivity());

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_authentication);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();

        binding.forgotPasswordButton.setOnClickListener(view -> navController.navigate(R.id.action_fragmentLogin_to_fragmentForgotPassword));

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().toString().equalsIgnoreCase("Admin")) {
                    binding.adminAuthentication.setVisibility(View.VISIBLE);
                    binding.officerAuthentication.setVisibility(View.GONE);
                } else {
                    binding.adminAuthentication.setVisibility(View.GONE);
                    binding.officerAuthentication.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.loginButton.setOnClickListener(view1 -> {
            mViewModel.login(
                    new LoginCredentials(
                            Objects.requireNonNull(binding.tieEmail.getText()).toString().trim(),
                            Objects.requireNonNull(binding.tiePassword.getText()).toString().trim()),
                    new LoginCallback() {
                        @Override
                        public void onLogin(String message) {
                            dialogLoad.show(message);
                        }

                        @Override
                        public void onAdminSuccessLogin(String message) {
                            dialogLoad.dismiss();
                            requireActivity().startActivity(new Intent(requireActivity(), AdminActivity.class));
                            requireActivity().finish();
                        }

                        @Override
                        public void onPersonnelSuccessLogin(String message) {
                            dialogLoad.dismiss();
                            requireActivity().startActivity(new Intent(requireActivity(), PersonnelActivity.class));
                            requireActivity().finish();
                        }


                        @Override
                        public void onFailed(String message) {
                            dialogLoad.dismiss();
                            new DialogResult(requireActivity(), DialogResult.RESULT.FAILED, message).showDialog();
                        }
            });
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