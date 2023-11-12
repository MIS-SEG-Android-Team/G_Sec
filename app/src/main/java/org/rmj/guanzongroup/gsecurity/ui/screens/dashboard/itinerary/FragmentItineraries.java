package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.itinerary;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentItinerariesBinding;

public class FragmentItineraries extends Fragment {

    private VMItineraries mViewModel;

    private FragmentItinerariesBinding binding;

    public static FragmentItineraries newInstance() {
        return new FragmentItineraries();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMItineraries.class);
        binding = FragmentItinerariesBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }
}