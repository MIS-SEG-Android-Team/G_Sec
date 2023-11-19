package org.rmj.guanzongroup.gsecurity.ui.screens.authentication.signup;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentSignUpBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

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
            DialogLoad dialogLoad = DialogLoad.getInstance(requireActivity());
            dialogLoad.show();
            firebaseAuth.createUserWithEmailAndPassword(
                    String.valueOf(binding.tieEmail.getText()),
                    String.valueOf(binding.tiePassword.getText())
            ).addOnCompleteListener(task -> {
                dialogLoad.dismiss();
                if(!task.isSuccessful()){
                    DialogResult.viewResult(requireActivity(), DialogResult.RESULT.FAILED, "Account registration failed!").showDialog();
                    return;
                }

                DialogResult.viewResult(
                        requireActivity(),
                        DialogResult.RESULT.FAILED,
                        "Account registration failed!",
                        new DialogResult.onDialogButtonClickListener() {
                            @Override
                            public void onClick() {
                                navController.popBackStack();
                            }
                        }).showDialog();
            });
        });

        return binding.getRoot();
    }
}