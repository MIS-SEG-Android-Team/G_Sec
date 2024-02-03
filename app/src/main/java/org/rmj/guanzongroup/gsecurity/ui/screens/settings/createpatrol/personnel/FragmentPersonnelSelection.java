package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.personnel;

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
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentPersonnelSelectionBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.personnel.AdapterPersonnel;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

import java.util.Objects;

import javax.inject.Inject;

public class FragmentPersonnelSelection extends Fragment {

    @Inject
    VMPersonnelSelection mViewModel;

    private FragmentPersonnelSelectionBinding binding;

    public static FragmentPersonnelSelection newInstance() {
        return new FragmentPersonnelSelection();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMPersonnelSelection.class);
        binding = FragmentPersonnelSelectionBinding.inflate(getLayoutInflater());

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_admin);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();

        DialogLoad dialogLoad = new DialogLoad(requireActivity());
        DialogMessage dialogMessage = new DialogMessage(requireActivity());

        mViewModel.isLoadingPersonnel().observe(getViewLifecycleOwner(), isLoadingPersonnel -> {
            if (isLoadingPersonnel) {
                binding.loadingPersonnel.setVisibility(View.VISIBLE);
                binding.personnelList.setVisibility(View.GONE);
            } else {
                binding.loadingPersonnel.setVisibility(View.GONE);
                binding.personnelList.setVisibility(View.VISIBLE);
            }
        });
        mViewModel.isLoadingUpdate().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                dialogLoad.show("Assigning new personnel...");
            } else {
                dialogLoad.dismiss();
            }
        });
        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if (message.isEmpty()) { return; }
            new DialogResult(requireActivity(), DialogResult.RESULT.FAILED, message, Dialog::dismiss).showDialog();
        });
        mViewModel.getMessage().observe(getViewLifecycleOwner(), message -> {
            if (message.isEmpty()) { return; }

            new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, message, dialog -> {
                dialog.dismiss();
                navController.popBackStack();
            }).showDialog();
        });

        mViewModel.forUpdate().observe(getViewLifecycleOwner(), forUpdate -> {
            if (forUpdate) {
                mViewModel.getPersonnelListForUpdate().observe(getViewLifecycleOwner(), personnel -> {
                    if (personnel == null) { return; }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    AdapterPersonnel adapter = new AdapterPersonnel(personnel, (name, id) -> {
                        dialogMessage.initDialog("Assign Schedule", "Would you like to assign this schedule to " + name + "?");
                        dialogMessage.setPositiveButton("Assign", dialog -> {
                            dialog.dismiss();
                            mViewModel.updatePersonnel(id);
                        });
                        dialogMessage.setNegativeButton("Cancel", Dialog::dismiss);
                        dialogMessage.show();
                    });
                    binding.personnelList.setLayoutManager(layoutManager);
                    binding.personnelList.setAdapter(adapter);
                });
            } else {
                mViewModel.getPersonnelList().observe(getViewLifecycleOwner(), personnel -> {
                    if (personnel == null) { return; }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    AdapterPersonnel adapter = new AdapterPersonnel(personnel, (name, id) -> {
                        dialogMessage.initDialog("Assign Schedule", "Would you like to assign this schedule to " + name + "?");
                        dialogMessage.setPositiveButton("Assign", dialog -> {
                            dialog.dismiss();
                            mViewModel.setPersonnel(name, id);
                            navController.navigate(R.id.action_fragmentPersonnelSelection_to_fragmentScheduleReview);
                        });
                        dialogMessage.setNegativeButton("Cancel", Dialog::dismiss);
                        dialogMessage.show();
                    });
                    binding.personnelList.setLayoutManager(layoutManager);
                    binding.personnelList.setAdapter(adapter);
                });
            }
        });

        return binding.getRoot();
    }

}