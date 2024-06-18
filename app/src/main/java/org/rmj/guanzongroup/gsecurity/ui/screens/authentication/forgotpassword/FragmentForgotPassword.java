package org.rmj.guanzongroup.gsecurity.ui.screens.authentication.forgotpassword;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentForgotPasswordBinding;

public class FragmentForgotPassword extends Fragment {

    private VMForgotPassword mViewModel;

    private FragmentForgotPasswordBinding binding;

    public static FragmentForgotPassword newInstance() {
        return new FragmentForgotPassword();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMForgotPassword.class);
        binding = FragmentForgotPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();


        return view;
    }

}