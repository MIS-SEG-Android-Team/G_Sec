package org.rmj.guanzongroup.gsecurity.ui.screens.report;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.PERSONNEL_ID;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.rmj.guanzongroup.gsecurity.databinding.FragmentPatrolReportBinding;

import javax.inject.Inject;

public class FragmentPatrolReport extends Fragment {

    @Inject
    VMPatrolReport mViewModel;

    private FragmentPatrolReportBinding binding;

    public static FragmentPatrolReport newInstance() {
        return new FragmentPatrolReport();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMPatrolReport.class);
        String userID = "";
        if (getArguments() != null) {
            userID = getArguments().getString(PERSONNEL_ID);
        }

        mViewModel.getPatrolReportForPersonnel(userID);
        binding = FragmentPatrolReportBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }
}