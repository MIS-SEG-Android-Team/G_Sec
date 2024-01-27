package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.recentactivities;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.PERSONNEL_ID;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.rmj.guanzongroup.gsecurity.databinding.FragmentRecentActivitiesBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.AdapterRecentActivity;

import javax.inject.Inject;

public class FragmentRecentActivities extends Fragment {

    @Inject
    VMRecentActivities mViewModel;

    private FragmentRecentActivitiesBinding binding;

    public static FragmentRecentActivities newInstance() {
        return new FragmentRecentActivities();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMRecentActivities.class);
        binding = FragmentRecentActivitiesBinding.inflate(getLayoutInflater());

        if (getArguments()!= null) {
            String personnelID = getArguments().getString(PERSONNEL_ID);
            mViewModel.getRecentActivity(personnelID);
        }

        mViewModel.getActivities().observe(getViewLifecycleOwner(), recentActivities -> {
            if(recentActivities == null){
                return;
            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            binding.recentActivityList.setLayoutManager(linearLayoutManager);
            binding.recentActivityList.setAdapter(new AdapterRecentActivity(recentActivities));
        });

        return binding.getRoot();
    }
}