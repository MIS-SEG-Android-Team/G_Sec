package org.rmj.guanzongroup.gsecurity.ui.screens.authentication.login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.tabs.TabLayout;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentLoginBinding;
import org.rmj.guanzongroup.gsecurity.ui.activity.AdminActivity;
import org.rmj.guanzongroup.gsecurity.ui.activity.PersonnelActivity;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

import java.util.Objects;

import javax.inject.Inject;

public class FragmentLogin extends Fragment {

    public static FragmentLogin newInstance() {
        return new FragmentLogin();
    }

    @Inject
    VMLogin mViewModel;

    private DialogResult dialogResult;

    private DialogLoad dialogLoad;

    private final StringBuilder stringBuilder = new StringBuilder();

    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMLogin.class);
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        dialogLoad = new DialogLoad(requireActivity());

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_authentication);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();

        binding.forgotPasswordButton.setOnClickListener(view -> navController.navigate(R.id.action_fragmentLogin_to_fragmentForgotPassword));

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(Objects.requireNonNull(tab.getText()).toString().equalsIgnoreCase("Admin")) {
                    binding.adminAuthentication.setVisibility(View.VISIBLE);
                    binding.officerAuthentication.setVisibility(View.GONE);
                } else {
                    binding.adminAuthentication.setVisibility(View.GONE);
                    binding.officerAuthentication.setVisibility(View.VISIBLE);

                    // Dismiss android soft keyboard if its active...
                    InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                    if(inputMethodManager.isAcceptingText()) {

                        inputMethodManager.hideSoftInputFromWindow(
                                binding.getRoot().getWindowToken(), 0
                        );
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Observe the value of adminHasLoggedIn -> Boolean, upon changing its value from false to true,
        // if true this means admin user has successfully login an account...
        mViewModel.getHasLogin().observe(getViewLifecycleOwner(), adminHasLoggedIn -> {
            if(adminHasLoggedIn) {
                Intent intent = new Intent(requireActivity(), AdminActivity.class);
                requireActivity().startActivity(intent);
                requireActivity().finish();
            }
        });

        // Observe the value of adminHasLoggedIn -> Boolean, upon changing its value from false to true,
        // if true this means an officer has successfully login an account...
        mViewModel.hasOfficerLogin().observe(requireActivity(), officerHasLoggedIn -> {
            if(officerHasLoggedIn) {
                Intent intent = new Intent(requireActivity(), PersonnelActivity.class);
                requireActivity().startActivity(intent);
                requireActivity().finish();
            }
        });

        //
        mViewModel.isLoading().observe(getViewLifecycleOwner(), loading -> {
            if (loading) {
                dialogLoad.show("Authenticating...");
            } else {
                dialogLoad.dismiss();
            }
        });

        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage == null) {
                return;
            }

            // Clear the PIN Edittext each time the user fails to authenticate...
            stringBuilder.delete(0, stringBuilder.length());
            binding.tiePIN.setText(stringBuilder.toString());
            dialogResult = new DialogResult(requireActivity(), DialogResult.RESULT.FAILED, errorMessage);
            dialogResult.showDialog();
        });

        mViewModel.hasCredentials().observe(requireActivity(), hasCredentials -> binding.loginButton.setEnabled(hasCredentials));

        mViewModel.getPIN().observe(requireActivity(), mpin -> {
            if(mpin.length() > 0) {
                binding.clearButton.setVisibility(View.VISIBLE);
            } else {
                binding.clearButton.setVisibility(View.INVISIBLE);
            }


            // Disable the numpad upon reaching the maximum length of 6 characters in order to avoid spam...
            // Automatically initiate login through PIN upon reaching 6 characters...
            if(mpin.length() >= 6) {
                binding.button1.setEnabled(false);
                binding.button2.setEnabled(false);
                binding.button3.setEnabled(false);
                binding.button4.setEnabled(false);
                binding.button5.setEnabled(false);
                binding.button6.setEnabled(false);
                binding.button7.setEnabled(false);
                binding.button8.setEnabled(false);
                binding.button9.setEnabled(false);
                binding.button0.setEnabled(false);


                mViewModel.loginPersonnel();
            } else {
                binding.button1.setEnabled(true);
                binding.button2.setEnabled(true);
                binding.button3.setEnabled(true);
                binding.button4.setEnabled(true);
                binding.button5.setEnabled(true);
                binding.button6.setEnabled(true);
                binding.button7.setEnabled(true);
                binding.button8.setEnabled(true);
                binding.button9.setEnabled(true);
                binding.button0.setEnabled(true);
            }

        });

        binding.tieEmail.addTextChangedListener(new TextChangeCallback(binding.tieEmail));
        binding.tiePassword.addTextChangedListener(new TextChangeCallback(binding.tiePassword));
        binding.tiePIN.addTextChangedListener(new TextChangeCallback(binding.tiePIN));

        binding.loginButton.setOnClickListener(view -> mViewModel.loginAdmin(
                binding.tieEmail.getText().toString().trim(),
                binding.tieEmail.getText().toString().trim()
        ));

        binding.button1.setOnClickListener(new NumPadCallback());
        binding.button2.setOnClickListener(new NumPadCallback());
        binding.button3.setOnClickListener(new NumPadCallback());
        binding.button4.setOnClickListener(new NumPadCallback());
        binding.button5.setOnClickListener(new NumPadCallback());
        binding.button6.setOnClickListener(new NumPadCallback());
        binding.button7.setOnClickListener(new NumPadCallback());
        binding.button8.setOnClickListener(new NumPadCallback());
        binding.button9.setOnClickListener(new NumPadCallback());
        binding.button0.setOnClickListener(new NumPadCallback());
        binding.clearButton.setOnClickListener(view -> {
            if(stringBuilder.length() > 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }

            binding.tiePIN.setText(stringBuilder.toString());
        });

        // This area of code will disable the back button to return on SplashScreen,
        // and instead will prompt an confirmation message to exit the app.
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

    private class NumPadCallback implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(stringBuilder.length() >= 6) {
                return;
            }

            int id = v.getId();

            if(id == binding.button0.getId()) {
                stringBuilder.append("0");
            } else if(id == binding.button1.getId()) {
                stringBuilder.append("1");
            } else if(id == binding.button2.getId()) {
                stringBuilder.append("2");
            } else if(id == binding.button3.getId()) {
                stringBuilder.append("3");
            } else if(id == binding.button4.getId()) {
                stringBuilder.append("4");
            } else if(id == binding.button5.getId()) {
                stringBuilder.append("5");
            } else if(id == binding.button6.getId()) {
                stringBuilder.append("6");
            } else if(id == binding.button7.getId()) {
                stringBuilder.append("7");
            } else if(id == binding.button8.getId()) {
                stringBuilder.append("8");
            } else {
                stringBuilder.append("9");
            }

            binding.tiePIN.setText(stringBuilder.toString());
        }
    }

    public class TextChangeCallback implements TextWatcher {

        private final View view;

        public TextChangeCallback(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int id = view.getId();

            if(id == binding.tieEmail.getId()) {
                mViewModel.setEmail(s.toString());
            } else if(id == binding.tiePassword.getId()) {
                mViewModel.setPassword(s.toString());
            } else {
                mViewModel.setPIN(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}