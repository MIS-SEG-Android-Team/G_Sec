package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.review;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.PERSONNEL_ID;

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
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.PersonnelPatrolRoute;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.PersonnelPatrolSchedule;
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

        if (getArguments() != null) {
            String userID = getArguments().getString(PERSONNEL_ID);
            mViewModel.getPatrolSchedulerForUser(userID);
            mViewModel.getPatrolRouteForUpdate().observe(getViewLifecycleOwner(), patrolRouteModel -> {
                if (patrolRouteModel == null) { return; }
                List<PersonnelPatrolRoute> checkpoints = patrolRouteModel.getSRoutexxx();
                List<PersonnelPatrolSchedule> schedules = patrolRouteModel.getSSchedule();

                ArrayList<String> checkpointNames = new ArrayList<>();
                for (int x = 0; x < checkpoints.size(); x++) {
                    checkpointNames.add(checkpoints.get(x).getSDescript());
                }

                ArrayList<String> patrolSchedules = new ArrayList<>();
                for (int x = 0; x < schedules.size(); x++) {
                    patrolSchedules.add(schedules.get(x).getDTimexxxx());
                }

                binding.patrolCheckpoints.setAdapter(
                        new ArrayAdapter<>(requireActivity(),
                                android.R.layout.simple_dropdown_item_1line,
                                checkpointNames)
                );

                binding.patrolSchedule.setAdapter(
                        new ArrayAdapter<>(requireActivity(),
                                android.R.layout.simple_dropdown_item_1line,
                                patrolSchedules)
                );
            });


            mViewModel.isLoadingSchedule().observe(getViewLifecycleOwner(), loading -> {
                if (loading) {
                    dialogLoad.show("Loading patrol schedule...");
                } else {
                    dialogLoad.dismiss();
                }
            });

            binding.editPersonnel.setOnClickListener(view -> navController.navigate(R.id.action_fragmentScheduleReview_to_fragmentPersonnelSelection));
            binding.editCheckpoints.setOnClickListener(view -> navController.navigate(R.id.action_fragmentScheduleReview_to_fragmentRouteSelection));
            binding.editSchedules.setOnClickListener(view -> navController.navigate(R.id.action_fragmentScheduleReview_to_fragmentPatrolSchedule));
            binding.saveButton.setVisibility(View.GONE);
        } else {
            binding.editPersonnel.setVisibility(View.GONE);
            binding.editCheckpoints.setVisibility(View.GONE);
            binding.editSchedules.setVisibility(View.GONE);
            binding.saveButton.setOnClickListener( view -> mViewModel.saveSchedule());
        }

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
                    navController.popBackStack(R.id.action_fragmentScheduleReview_to_fragmentSettings, false);
                });
                dialogMessage.show();
            }
        });

        mViewModel.getCreatedSchedule().observe(getViewLifecycleOwner(), createdSchedule -> {
            if (createdSchedule == null) { return; }
            binding.warehouse.setText(createdSchedule.getSWHouseNm());
            binding.assignedPersonnel.setText(createdSchedule.getSUserName());

            List<PersonnelPatrolRoute> checkpoints = createdSchedule.getSRoutexxx();
            List<PersonnelPatrolSchedule> schedules = createdSchedule.getSSchedule();

            ArrayList<String> checkpointNames = new ArrayList<>();
            if (checkpoints != null) {
                for (int x = 0; x < checkpoints.size(); x++) {
                    checkpointNames.add(checkpoints.get(x).getSDescript());
                }
            }

            ArrayList<String> patrolSchedules = new ArrayList<>();
            if (schedules != null) {
                for (int x = 0; x < schedules.size(); x++) {
                    patrolSchedules.add(schedules.get(x).getDTimexxxx());
                }
            }

            binding.patrolCheckpoints.setAdapter(
                    new ArrayAdapter<>(requireActivity(),
                            android.R.layout.simple_dropdown_item_1line,
                            checkpointNames)
            );

            binding.patrolSchedule.setAdapter(
                    new ArrayAdapter<>(requireActivity(),
                            android.R.layout.simple_dropdown_item_1line,
                            patrolSchedules)
            );
        });

        return binding.getRoot();
    }
}