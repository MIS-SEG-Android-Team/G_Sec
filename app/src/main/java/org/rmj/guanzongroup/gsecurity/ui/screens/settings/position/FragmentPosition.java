package org.rmj.guanzongroup.gsecurity.ui.screens.settings.position;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentPositionBinding;

import javax.inject.Inject;

public class FragmentPosition extends Fragment {

    @Inject
    VMPosition mViewModel;

    private FragmentPositionBinding binding;

    public static FragmentPosition newInstance() {
        return new FragmentPosition();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMPosition.class);
        binding = FragmentPositionBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }
}