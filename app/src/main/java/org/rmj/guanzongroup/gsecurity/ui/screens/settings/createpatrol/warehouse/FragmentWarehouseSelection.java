package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.warehouse;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentWarehouseSelectionBinding;

import javax.inject.Inject;

public class FragmentWarehouseSelection extends Fragment {

    @Inject
    VMWarehouseSelection mViewModel;

    private FragmentWarehouseSelectionBinding binding;

    public static FragmentWarehouseSelection newInstance() {
        return new FragmentWarehouseSelection();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMWarehouseSelection.class);
        binding = FragmentWarehouseSelectionBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }
}