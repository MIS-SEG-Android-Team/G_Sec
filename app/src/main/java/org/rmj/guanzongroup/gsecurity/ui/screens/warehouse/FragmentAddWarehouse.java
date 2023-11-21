package org.rmj.guanzongroup.gsecurity.ui.screens.warehouse;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentAddWarehouseBinding;

public class FragmentAddWarehouse extends Fragment {

    private VMAddWarehouse mViewModel;

    public static FragmentAddWarehouse newInstance() {
        return new FragmentAddWarehouse();
    }

    private FragmentAddWarehouseBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMAddWarehouse.class);
        binding = FragmentAddWarehouseBinding.inflate(getLayoutInflater());



        return binding.getRoot();
    }
}