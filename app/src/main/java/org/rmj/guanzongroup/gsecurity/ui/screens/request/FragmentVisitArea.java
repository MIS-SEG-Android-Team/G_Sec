package org.rmj.guanzongroup.gsecurity.ui.screens.request;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentVisitAreaBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

public class FragmentVisitArea extends Fragment {

    private VMVisitArea mViewModel;

    private FragmentVisitAreaBinding binding;

    public static FragmentVisitArea newInstance() {
        return new FragmentVisitArea();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMVisitArea.class);

        binding = FragmentVisitAreaBinding.inflate(getLayoutInflater());

        binding.sendRequestButton.setOnClickListener(view-> {
            binding.tiePersonnel.setText("");
            binding.tiePlace.setText("");
            binding.tieRemarks.setText("");
            new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, "Site visitation request has been sent!").showDialog();
        });

        return binding.getRoot();
    }
}