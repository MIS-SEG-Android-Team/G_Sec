package org.rmj.guanzongroup.gsecurity.ui.screens.schedule;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentScheduleBinding;

public class FragmentSchedule extends Fragment {

    private VMSchedule mViewModel;

    private FragmentScheduleBinding binding;

    public static FragmentSchedule newInstance() {
        return new FragmentSchedule();
    }

    

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMSchedule.class);
        binding = FragmentScheduleBinding.inflate(getLayoutInflater());


        return binding.getRoot();
    }
}