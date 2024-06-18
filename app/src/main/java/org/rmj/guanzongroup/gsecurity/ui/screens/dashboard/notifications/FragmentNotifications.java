package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.notifications;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        binding.notificationList.setLayoutManager(linearLayoutManager);
//        mViewModel.getNotifications().observe(getViewLifecycleOwner(), new Observer<List<Notification>>() {
//            @Override
//            public void onChanged(List<Notification> notifications) {
//                if(notifications == null){
//                    return;
//                }
//
//                binding.notificationList.setAdapter(new AdapterNotification(notifications, new AdapterNotificationCallback() {
//                    @Override
//                    public void onclickNotification(Notification notification) {
//
//                    }
//                }));
//            }
//        });

        return binding.getRoot();
    }
}