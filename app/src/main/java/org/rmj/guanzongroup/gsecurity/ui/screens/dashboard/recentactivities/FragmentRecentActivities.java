package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.recentactivities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.rmj.guanzongroup.gsecurity.databinding.FragmentRecentActivitiesBinding;
import org.rmj.guanzongroup.gsecurity.pojo.activity.RecentActivity;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.AdapterRecentActivity;

import java.util.List;

public class FragmentRecentActivities extends Fragment {

    private VMRecentActivities mViewModel;

    private FragmentRecentActivitiesBinding binding;

    public static FragmentRecentActivities newInstance() {
        return new FragmentRecentActivities();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMRecentActivities.class);

        binding = FragmentRecentActivitiesBinding.inflate(getLayoutInflater());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mViewModel.getRecentActivity().observe(getViewLifecycleOwner(), recentActivities -> {
            if(recentActivities == null){
                return;
            }

            binding.recentActivityList.setLayoutManager(linearLayoutManager);
            binding.recentActivityList.setAdapter(new AdapterRecentActivity(recentActivities));
        });

        return binding.getRoot();
    }
}