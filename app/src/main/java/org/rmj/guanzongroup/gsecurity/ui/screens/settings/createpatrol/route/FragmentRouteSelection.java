package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.route;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.data.room.checkpoint.NFCDeviceEntity;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentRouteSelectionBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.checkpoint.AdapterCheckpointSelection;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.checkpoint.AdapterCheckpointSelectionCallback;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class FragmentRouteSelection extends Fragment {

    @Inject
    VMRouteSelection mViewModel;

    private FragmentRouteSelectionBinding binding;

    public static FragmentRouteSelection newInstance() {
        return new FragmentRouteSelection();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMRouteSelection.class);
        binding = FragmentRouteSelectionBinding.inflate(getLayoutInflater());

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_admin);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();

        mViewModel.isLoadingCheckpoints().observe(getViewLifecycleOwner(), isLoadingCheckpoints -> {
            if (isLoadingCheckpoints) {

            } else {

            }
        });

        mViewModel.getNfcDeviceEntities().observe(getViewLifecycleOwner(), nfcDeviceEntities -> {
            if (nfcDeviceEntities == null) {
                return;
            }

            if (nfcDeviceEntities.size() == 0) {
                return;
            }

            List<Checkpoint> checkpoints = new ArrayList<>();
            for (int x = 0; x < nfcDeviceEntities.size(); x++) {
                NFCDeviceEntity nfcDevice = nfcDeviceEntities.get(x);
                checkpoints.add(
                        new Checkpoint(
                                nfcDevice.getSNFCIDxxx(),
                                nfcDevice.getSDescript(),
                                nfcDevice.getSCatIDxxx(),
                                nfcDevice.getSWHouseID()
                        )
                );
            }
            mViewModel.initializeSelectedCheckpoints(checkpoints);
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

            AdapterCheckpointSelection adapterCheckpointSelection = new AdapterCheckpointSelection(
                    checkpoints, new AdapterCheckpointSelectionCallback() {
                @Override
                public void onClickCheckpoint(String nfcID, String description) {

                }

                @Override
                public void onSelectCheckpoint(int position, boolean isSelected) {
                    mViewModel.setSelectedCheckpoint(position, isSelected);
                }
            });

            binding.patrolCheckpoints.setLayoutManager(linearLayoutManager);
            binding.patrolCheckpoints.setAdapter(adapterCheckpointSelection);
        });

        mViewModel.savedPatrolCheckpoints().observe(getViewLifecycleOwner(), saved -> {
            if (saved) {
                navController.navigate(R.id.action_fragmentRouteSelection_to_fragmentPatrolSchedule);
            }
        });
        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if (!message.isEmpty()) {
                new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, message, Dialog::dismiss).showDialog();
            }
        });

        binding.continueButton.setOnClickListener(view -> {
            mViewModel.saveRouteSelection();
        });

        return binding.getRoot();
    }
}