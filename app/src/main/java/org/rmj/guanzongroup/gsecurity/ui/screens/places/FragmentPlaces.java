package org.rmj.guanzongroup.gsecurity.ui.screens.places;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentPlacesBinding;

public class FragmentPlaces extends Fragment {

    private VMPlaces mViewModel;

    private FragmentPlacesBinding binding;

    public static FragmentPlaces newInstance() {
        return new FragmentPlaces();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMPlaces.class);
        binding = FragmentPlacesBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }
}