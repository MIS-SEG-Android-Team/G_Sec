package org.rmj.guanzongroup.gsecurity.ui.screens.authentication.login;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;

public class FragmentLogin extends Fragment {

    private VMLogin mViewModel;

//    private FragmentLogigBinding binding;

    public static FragmentLogin newInstance() {
        return new FragmentLogin();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMLogin.class);

        View view = inflater.inflate(R.layout.fragment_fragment_login, container, false);


        return view;
    }
}