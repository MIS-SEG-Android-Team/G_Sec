package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.personnel;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentPersonnelListBinding;
import org.rmj.guanzongroup.gsecurity.mockdata.PersonnelItem;
import org.rmj.guanzongroup.gsecurity.pojo.user.Personnel;
import org.rmj.guanzongroup.gsecurity.ui.components.adapters.AdapterActivePersonnel;

import java.util.List;

public class FragmentPersonnelList extends Fragment {

    private VMPersonnelList mViewModel;

    private FragmentPersonnelListBinding binding;

    public static FragmentPersonnelList newInstance() {
        return new FragmentPersonnelList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMPersonnelList.class);
        binding = FragmentPersonnelListBinding.inflate(getLayoutInflater());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mViewModel.getPersonnelList().observe(getViewLifecycleOwner(), personnels -> {
            if(personnels == null) {
                return;
            }

            binding.personnelList.setLayoutManager(linearLayoutManager);
            binding.personnelList.setAdapter(new AdapterActivePersonnel(personnels));
        });

        return binding.getRoot();
    }
}