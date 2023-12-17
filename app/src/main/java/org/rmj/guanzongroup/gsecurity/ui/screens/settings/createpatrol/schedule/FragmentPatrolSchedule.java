package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.schedule;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentPatrolScheduleBinding;

import javax.inject.Inject;

public class FragmentPatrolSchedule extends Fragment {

    @Inject
    VMSchedule mViewModel;

    private FragmentPatrolScheduleBinding binding;

    public static FragmentPatrolSchedule newInstance() {
        return new FragmentPatrolSchedule();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPatrolScheduleBinding.inflate(getLayoutInflater());
        mViewModel = new ViewModelProvider(requireActivity()).get(VMSchedule.class);


        return binding.getRoot();
    }
}