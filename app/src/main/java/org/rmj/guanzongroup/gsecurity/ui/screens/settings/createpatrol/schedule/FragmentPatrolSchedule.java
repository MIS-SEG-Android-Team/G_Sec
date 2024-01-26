package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.schedule;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;

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

    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPatrolScheduleBinding.inflate(inflater);
        mViewModel = new ViewModelProvider(requireActivity()).get(VMSchedule.class);

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_admin);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();

        DialogMessage dialogMessage = new DialogMessage(requireActivity());

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

        mViewModel.getSchedules().observe(getViewLifecycleOwner(), schedules -> {
            if (schedules == null) {
                return;
            }

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
                            Date parseDate = new SimpleDateFormat("HH:mm").parse(time);
                            String formattedTime = new SimpleDateFormat("HH:mm a").format(parseDate);
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
                public void onClickRemove(int position) {
                    mViewModel.deleteSchedule(position);
                }
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
                    Date parseDate = new SimpleDateFormat("HH:mm").parse(time);
                    String formattedTime = new SimpleDateFormat("HH:mm a").format(parseDate);
                    mViewModel.addSchedule(formattedTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },
            hour,
            minute,
            false).show();
        });

        binding.buttonNext.setOnClickListener( view -> {
            mViewModel.saveSchedule();
            navController.navigate(R.id.action_fragmentPatrolSchedule_to_fragmentPersonnelSelection);
        });

        return binding.getRoot();
    }
}