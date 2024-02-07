package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.personnellist;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.PERSONNEL_ID;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentActivePersonnelListBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.personnel.AdapterActivePersonnel;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

import java.util.Objects;

import javax.inject.Inject;

public class FragmentActivePersonnelList extends Fragment {

    @Inject
    VMActivePersonnelList mViewModel;

    private FragmentActivePersonnelListBinding binding;

    private NavController navController;

    public static FragmentActivePersonnelList newInstance() {
        return new FragmentActivePersonnelList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMActivePersonnelList.class);
        binding = FragmentActivePersonnelListBinding.inflate(getLayoutInflater());
        DialogLoad dialogLoad = new DialogLoad(requireActivity());
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_admin);
        navController = Objects.requireNonNull(navHostFragment).getNavController();

        mViewModel.isLoadingPersonnelList().observe(getViewLifecycleOwner(), loadingPersonnel -> {
            if (loadingPersonnel) {
                binding.loadingPersonnel.setVisibility(View.VISIBLE);
            } else {
                binding.loadingPersonnel.setVisibility(View.GONE);
            }
        });

        mViewModel.getListErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage.isEmpty()) {
                binding.errorMessageNotice.setVisibility(View.GONE);
                return;
            }

            binding.loadingPersonnel.setVisibility(View.GONE);
            binding.personnelList.setVisibility(View.GONE);
            binding.errorMessageNotice.setVisibility(View.VISIBLE);
            binding.errorMessage.setText(errorMessage);
        });

        mViewModel.getActivePersonnel().observe(getViewLifecycleOwner(), activePersonnel -> {
            if(activePersonnel == null) {
                return;
            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            binding.personnelList.setVisibility(View.VISIBLE);
            binding.personnelList.setLayoutManager(linearLayoutManager);
            binding.personnelList.setAdapter(
                    new AdapterActivePersonnel(
                            activePersonnel,
                            personnelID -> {
                                Bundle bundle = new Bundle();
                                bundle.putString(PERSONNEL_ID, personnelID);
                                navController.navigate(R.id.action_fragmentPersonnelList_to_fragmentRecentActivities, bundle);
                            }
                    )
            );
        });

        binding.refreshList.setOnClickListener( view -> mViewModel.getPersonnelList());
        return binding.getRoot();
    }
}