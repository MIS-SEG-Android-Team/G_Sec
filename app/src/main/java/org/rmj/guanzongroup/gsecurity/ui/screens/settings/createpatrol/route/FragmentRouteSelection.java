package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.route;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
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
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
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

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMRouteSelection.class);
        binding = FragmentRouteSelectionBinding.inflate(getLayoutInflater());
        DialogLoad dialogLoad = new DialogLoad(requireActivity());

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_admin);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();

        mViewModel.isLoadingCheckpoints().observe(getViewLifecycleOwner(), isLoadingCheckpoints -> {
            if (isLoadingCheckpoints) {
                binding.loadingCheckpoints.setVisibility(View.VISIBLE);
                binding.checkpointsLayout.setVisibility(View.GONE);
                binding.errorMessageNotice.setVisibility(View.GONE);
            } else {
                binding.loadingCheckpoints.setVisibility(View.GONE);
                binding.errorMessageNotice.setVisibility(View.GONE);
                binding.checkpointsLayout.setVisibility(View.VISIBLE);
            }
        });
        mViewModel.getListErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if (!message.isEmpty()) {
                binding.loadingCheckpoints.setVisibility(View.GONE);
                binding.checkpointsLayout.setVisibility(View.GONE);
                binding.errorMessageNotice.setVisibility(View.VISIBLE);
                binding.errorMessage.setText(message);
            } else {
                binding.errorMessage.setText("");
                binding.errorMessageNotice.setVisibility(View.GONE);
            }
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
        mViewModel.isLoadingUpdatedRoute().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                dialogLoad.show("Updating patrol route...");
            } else {
                dialogLoad.dismiss();
            }
        });
        mViewModel.getMessage().observe(getViewLifecycleOwner(), message -> {
            if (message.isEmpty()) { return; }
            new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, message, dialog -> {
                dialog.dismiss();
                navController.popBackStack();
            }).showDialog();
        });
        mViewModel.forUpdate().observe(getViewLifecycleOwner(), forUpdate-> {
            if (forUpdate) {
                mViewModel.initForUpdate();
                mViewModel.getNfcTags();
                mViewModel.getNfcDeviceEntities().observe(getViewLifecycleOwner(), nfcDeviceEntities -> {
                    if (nfcDeviceEntities == null) { return; }
                    if (nfcDeviceEntities.size() == 0) { return; }
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

                binding.continueButton.setText("Update Route");
                binding.continueButton.setOnClickListener(view -> mViewModel.updatePatrolRoute());
            } else {
                mViewModel.initForCreatingSchedule();
                mViewModel.getNfcTags();
                mViewModel.getNfcDeviceEntities().observe(getViewLifecycleOwner(), nfcDeviceEntities -> {
                    if (nfcDeviceEntities == null) { return; }
                    if (nfcDeviceEntities.size() == 0) { return; }
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

                binding.continueButton.setOnClickListener(view -> mViewModel.saveRouteSelection());
            }
        });
        mViewModel.getCheckpoints().observe(getViewLifecycleOwner(), checkpoints -> {
            if (checkpoints == null) { return; }
            if (checkpoints.size() == 0) { return; }
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

        return binding.getRoot();
    }
}