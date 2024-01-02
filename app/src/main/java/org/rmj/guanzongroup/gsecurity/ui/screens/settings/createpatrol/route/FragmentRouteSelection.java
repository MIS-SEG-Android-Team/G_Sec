package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.route;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentRouteSelectionBinding;

public class FragmentRouteSelection extends Fragment {

    private VMRouteSelection mViewModel;

    private FragmentRouteSelectionBinding binding;

    public static FragmentRouteSelection newInstance() {
        return new FragmentRouteSelection();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRouteSelectionBinding.inflate(getLayoutInflater());
        mViewModel = new ViewModelProvider(requireActivity()).get(VMRouteSelection.class);

        mViewModel.isLoadingCheckpoints().observe(getViewLifecycleOwner(), isLoadingCheckpoints -> {
            if (isLoadingCheckpoints) {

            } else {

            }
        });

        mViewModel.getCheckpoints().observe(getViewLifecycleOwner(), checkpoints -> {
            if (checkpoints == null) {
                return;
            }

            if (checkpoints.size() == 0) {
                return;
            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        });

        return binding.getRoot();
    }
}