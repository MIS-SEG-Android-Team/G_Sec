package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.admin;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;

public class FragmentAdminDashboard extends Fragment {

    private VMAdminDashboard mViewModel;

    public static FragmentAdminDashboard newInstance() {
        return new FragmentAdminDashboard();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMAdminDashboard.class);
        View view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false);


        return view;
    }
}