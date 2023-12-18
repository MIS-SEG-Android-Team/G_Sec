package org.rmj.guanzongroup.gsecurity.ui.screens.request;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
            new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, "Site visitation request has been sent!", Dialog::dismiss).showDialog();
        });

        return binding.getRoot();
    }
}