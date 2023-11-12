package org.rmj.guanzongroup.gsecurity.ui.screens.authentication.signup;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentSignUpBinding;

public class FragmentSignUp extends Fragment {

    private VMSignUp mViewModel;

    private FragmentSignUpBinding binding;

    public static FragmentSignUp newInstance() {
        return new FragmentSignUp();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMSignUp.class);
        binding = FragmentSignUpBinding.inflate(getLayoutInflater());



        return binding.getRoot();
    }
}