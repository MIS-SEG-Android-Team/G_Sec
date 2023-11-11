package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.personnel;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;

public class FragmentPersonnelDashboard extends Fragment {

    private VMPersonnelDashboard mViewModel;

    public static FragmentPersonnelDashboard newInstance() {
        return new FragmentPersonnelDashboard();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMPersonnelDashboard.class);
        View view = inflater.inflate(R.layout.fragment_personnel_dashboard, container, false);


        return view;
    }
}