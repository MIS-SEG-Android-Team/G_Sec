package org.rmj.guanzongroup.gsecurity.ui.screens.nfc;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentAddPlaceBinding;

public class FragmentAddPlace extends Fragment {

    private VMAddPlace mViewModel;

    private FragmentAddPlaceBinding binding;

    public static FragmentAddPlace newInstance() {
        return new FragmentAddPlace();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMAddPlace.class);
        binding = FragmentAddPlaceBinding.inflate(getLayoutInflater());



        return binding.getRoot();
    }
}