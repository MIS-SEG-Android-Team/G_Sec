package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.personnellist;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentPersonnelListBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.AdapterActivePersonnel;

import java.util.Objects;

public class FragmentPersonnelList extends Fragment {

    private VMPersonnelList mViewModel;

    private FragmentPersonnelListBinding binding;

    private NavController navController;

    public static FragmentPersonnelList newInstance() {
        return new FragmentPersonnelList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMPersonnelList.class);
        binding = FragmentPersonnelListBinding.inflate(getLayoutInflater());

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_admin);
        navController = Objects.requireNonNull(navHostFragment).getNavController();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mViewModel.getPersonnelList().observe(getViewLifecycleOwner(), personnels -> {
            if(personnels == null) {
                return;
            }

            binding.personnelList.setLayoutManager(linearLayoutManager);
            binding.personnelList.setAdapter(
                    new AdapterActivePersonnel(
                            personnels,
                            personnelID -> navController.navigate(R.id.action_fragmentPersonnelList_to_fragmentRecentActivities)
                    )
            );
        });

        return binding.getRoot();
    }
}