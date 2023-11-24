package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.notifications;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentNotificationsBinding;

public class FragmentNotifications extends Fragment {

    private VMNotifications mViewModel;

    private FragmentNotificationsBinding binding;

    public static FragmentNotifications newInstance() {
        return new FragmentNotifications();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMNotifications.class);

        binding = FragmentNotificationsBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }
}