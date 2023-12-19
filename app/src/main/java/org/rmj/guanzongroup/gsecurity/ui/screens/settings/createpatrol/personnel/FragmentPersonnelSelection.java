package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.personnel;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;

public class FragmentPersonnelSelection extends Fragment {

    private VMPersonnelSelection mViewModel;

    public static FragmentPersonnelSelection newInstance() {
        return new FragmentPersonnelSelection();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personnel_selection, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMPersonnelSelection.class);
        // TODO: Use the ViewModel
    }

}