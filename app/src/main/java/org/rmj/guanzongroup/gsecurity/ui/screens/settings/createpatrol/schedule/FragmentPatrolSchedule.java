package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.schedule;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentPatrolScheduleBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.schedule.AdapterPatrolSchedule;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.schedule.AdapterPatrolScheduleCallback;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.inject.Inject;

public class FragmentPatrolSchedule extends Fragment {

    @Inject
    VMSchedule mViewModel;

    private FragmentPatrolScheduleBinding binding;

    public static FragmentPatrolSchedule newInstance() {
        return new FragmentPatrolSchedule();
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPatrolScheduleBinding.inflate(inflater);
        mViewModel = new ViewModelProvider(requireActivity()).get(VMSchedule.class);

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_admin);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();
        DialogLoad dialogLoad = new DialogLoad(requireActivity());
        DialogMessage dialogMessage = new DialogMessage(requireActivity());

        mViewModel.isLoadingUpdateSchedule().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                dialogLoad.show("Updating schedule. Please wait...");
            } else {
                dialogLoad.dismiss();
            }
        });

        mViewModel.errorMessage().observe(getViewLifecycleOwner(), message -> {
            if (!message.isEmpty()) {
                dialogMessage.initDialog("Patrol Schedule", message);
                dialogMessage.setPositiveButton("Close", dialog -> {
                    dialog.dismiss();
                    mViewModel.dismissErrorMessage();
                });
                dialogMessage.show();
            }
        });

        mViewModel.scheduleSaved().observe(getViewLifecycleOwner(), saved -> {
            if (saved) {
                navController.navigate(R.id.action_fragmentPatrolSchedule_to_fragmentPersonnelSelection);
            }
        });

        mViewModel.getMessage().observe(getViewLifecycleOwner(), message -> {
            if (message.isEmpty()) { return; }
            new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, message, dialog -> {
                dialog.dismiss();
                navController.popBackStack();
            }).showDialog();
        });

        mViewModel.foUpdate().observe(getViewLifecycleOwner(), forUpdate -> {
            if (forUpdate) {
                mViewModel.initScheduleForUpdate();
                binding.buttonNext.setText("Update Schedule");
                binding.buttonNext.setOnClickListener( view -> mViewModel.updateSchedule());
            } else {
                mViewModel.initCreatedSchedule();
                binding.buttonNext.setOnClickListener( view -> mViewModel.saveSchedule());
            }
        });

        mViewModel.getSchedules().observe(getViewLifecycleOwner(), schedules -> {
            if (schedules == null) { return; }

            LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            AdapterPatrolSchedule adapter = new AdapterPatrolSchedule(schedules, new AdapterPatrolScheduleCallback() {
                @Override
                public void onClickEdit(int position) {
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);

                    new TimePickerDialog(requireActivity(), android.R.style.Theme_Holo_Dialog, (view1, hourOfDay, minute1) -> {
                        try {
                            String time = hourOfDay + ":" + minute1;
                            Date parseDate = new SimpleDateFormat("hh:mm").parse(time);
                            if (parseDate == null) {
                                Toast.makeText(requireActivity(), "Unknown date time error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            String formattedTime = new SimpleDateFormat("hh:mm a").format(parseDate);
                            mViewModel.editSchedule(position, formattedTime);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    },
                    hour,
                    minute,
                    false).show();
                }

                @Override
                public void onClickRemove(int position) { mViewModel.deleteSchedule(position); }
            });
            binding.patrolSchedule.setLayoutManager(layoutManager);
            binding.patrolSchedule.setAdapter(adapter);
        });

        binding.buttonAddSchedule.setOnClickListener( view -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            new TimePickerDialog(requireActivity(), android.R.style.Theme_Holo_Dialog, (view1, hourOfDay, minute1) -> {
                try {
                    String time = hourOfDay + ":" + minute1;
                    Date parseDate = new SimpleDateFormat("hh:mm").parse(time);
                    if (parseDate == null) {
                        Toast.makeText(requireActivity(), "Unknown date time error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String formattedTime = new SimpleDateFormat("hh:mm a").format(parseDate);
                    mViewModel.addSchedule(formattedTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },
            hour,
            minute,
            false).show();
        });

        return binding.getRoot();
    }
}