package org.rmj.guanzongroup.gsecurity.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.ViewModel.VMFragmentAdminHome;
import org.rmj.guanzongroup.gsecurity.ViewModel.VMFragmentPersonnelList;

public class Fragment_PersonnelList extends Fragment {

    private VMFragmentPersonnelList mViewModel;

    public static Fragment_PersonnelList newInstance() {
        return new Fragment_PersonnelList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personnel_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMFragmentPersonnelList.class);
        // TODO: Use the ViewModel
    }

}