package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.review;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.SRoutexxx;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.SSchedule;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentScheduleReviewBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class FragmentScheduleReview extends Fragment {

    @Inject
    VMScheduleReview mViewModel;

    private FragmentScheduleReviewBinding binding;

    public static FragmentScheduleReview newInstance() {
        return new FragmentScheduleReview();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMScheduleReview.class);
        binding = FragmentScheduleReviewBinding.inflate(getLayoutInflater());
        DialogLoad dialogLoad = new DialogLoad(requireActivity());
        DialogMessage dialogMessage = new DialogMessage(requireActivity());

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_admin);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();

        mViewModel.isLoadingSaveSchedule().observe(getViewLifecycleOwner(), loading -> {
            if (loading) {
                dialogLoad.show("Saving new patrol schedule...");
            } else {
                dialogLoad.dismiss();
            }
        });

        mViewModel.errorMessage().observe(getViewLifecycleOwner(), message -> {
            if (!message.isEmpty()) {
                dialogMessage.initDialog("Patrol Schedule", message);
                dialogMessage.setPositiveButton("Close", dialog -> {
                    dialog.dismiss();
                    mViewModel.dismissErrorDialog();
                });
                dialogMessage.show();
            }
        });

        mViewModel.scheduleSaved().observe(getViewLifecycleOwner(), saved -> {
            if (saved) {
                dialogMessage.initDialog("Patrol Schedule", "New schedule has been saved!");
                dialogMessage.setPositiveButton("Close", dialog -> {
                    dialog.dismiss();
                    navController.popBackStack(R.id.fragmentPersonnelList, false);
                });
                dialogMessage.show();
            }
        });

        mViewModel.getCreatedSchedule().observe(getViewLifecycleOwner(), createdSchedule -> {
            binding.warehouse.setText(createdSchedule.getSWHouseNm());
            binding.assignedPersonnel.setText(createdSchedule.getSUserName());

            List<SRoutexxx> checkpoints = createdSchedule.getSRoutexxx();
            List<SSchedule> schedules = createdSchedule.getSSchedule();

            ArrayList<String> checkpointNames = new ArrayList<>();
            for (int x = 0; x < checkpoints.size(); x++) {
                checkpointNames.add(checkpoints.get(x).getSCheckpnt());
            }

            ArrayList<String> patrolSchedules = new ArrayList<>();
            for (int x = 0; x < schedules.size(); x++) {
                patrolSchedules.add(schedules.get(x).getDTimexxxx());
            }

            binding.patrolCheckpoints.setAdapter(
                            new ArrayAdapter<String>(requireActivity(),
                            android.R.layout.simple_dropdown_item_1line,
                            checkpointNames)
            );


            binding.patrolSchedule.setAdapter(
                            new ArrayAdapter<String>(requireActivity(),
                            android.R.layout.simple_dropdown_item_1line,
                            patrolSchedules)
            );

            binding.assignedPersonnel.setText(createdSchedule.getSUserName());
        });

        binding.saveButton.setOnClickListener( view -> {
            mViewModel.saveSchedule();
        });

        return binding.getRoot();
    }
}