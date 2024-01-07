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

import org.rmj.guanzongroup.gsecurity.databinding.FragmentRouteSelectionBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.checkpoint.AdapterCheckpointSelection;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.checkpoint.AdapterCheckpointSelectionCallback;

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

        mViewModel.getNfcDeviceEntities().observe(getViewLifecycleOwner(), nfcDeviceEntities -> mViewModel.initializeSelectedCheckpoints(nfcDeviceEntities));

        mViewModel.getCheckpoints().observe(getViewLifecycleOwner(), checkpoints -> {
            if (checkpoints == null) {
                return;
            }

            if (checkpoints.size() == 0) {
                return;
            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

            AdapterCheckpointSelection adapterCheckpointSelection = new AdapterCheckpointSelection(
                    checkpoints, new AdapterCheckpointSelectionCallback() {
                @Override
                public void onClickCheckpoint(String nfcID, String description) {

                }

                @Override
                public void onSelectCheckpoint(int position, String nfcID) {
                    mViewModel.addSelectedCheckpoint();
                }
            });

            binding.patrolCheckpoints.setLayoutManager(linearLayoutManager);
            binding.patrolCheckpoints.setAdapter(adapterCheckpointSelection);
        });

        return binding.getRoot();
    }
}