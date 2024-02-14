package org.rmj.guanzongroup.gsecurity.ui.screens.settings.personnellist;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.PERSONNEL_ID;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.data.remote.response.PersonnelModel;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentPersonnelListBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.personnel.AdapterPersonnel;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.personnel.AdapterPersonnelCallback;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogPersonnelDetails;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

import java.util.Objects;

import javax.inject.Inject;

public class FragmentPersonnelList extends Fragment {

    @Inject
    VMPersonnelList mViewModel;

    private FragmentPersonnelListBinding binding;

    public static FragmentPersonnelList newInstance() {
        return new FragmentPersonnelList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMPersonnelList.class);
        binding = FragmentPersonnelListBinding.inflate(getLayoutInflater());

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_admin);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();

        DialogLoad dialogLoad = new DialogLoad(requireActivity());
        DialogMessage dialogMessage = new DialogMessage(requireActivity());

        mViewModel.loadingPersonnel().observe(getViewLifecycleOwner(), loading -> {
            if (loading) {
                dialogLoad.show("Loading personnel list...");
            } else {
                dialogLoad.dismiss();
            }
        });

        mViewModel.getListErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if (message.isEmpty()) {
                binding.errorMessageNotice.setVisibility(View.GONE);
                return;
            }

            binding.errorMessage.setText(message);
            binding.errorMessageNotice.setVisibility(View.VISIBLE);
        });

        mViewModel.loadingDeactivate().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                dialogLoad.show("Deactivating account. Please wait...");
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
                mViewModel.importPersonnelList();
            }).showDialog();
        });

        mViewModel.getPersonnelList().observe(getViewLifecycleOwner(), personnelList -> {
            if (personnelList == null) { return; }

            LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            AdapterPersonnel adapter = new AdapterPersonnel(personnelList, new AdapterPersonnelCallback() {
                @Override
                public void onClickEdit(PersonnelModel personnel) {
                    Bundle bundle = new Bundle();
                    bundle.putString(PERSONNEL_ID, personnel.getSUserIDxx());
                    navController.navigate(R.id.action_fragmentPersonnelList2_to_fragmentScheduleReview, bundle);
                }

                @Override
                public void onClickInfo(PersonnelModel personnel) {
                    new DialogPersonnelDetails(requireActivity(), personnel, new DialogPersonnelDetails.OnDialogButtonClickCallback() {
                        @Override
                        public void onClickGetReports(String id) {
                            Bundle bundle = new Bundle();
                            bundle.putString(PERSONNEL_ID, id);
                            navController.navigate(R.id.action_fragmentPersonnelList2_to_fragmentPatrolReport, bundle);
                        }

                        @Override
                        public void OnClickDeactivate(String id, String name) {
                            dialogMessage.initDialog("Deactivate Account","Confirm Deactivation: Proceed with deactivating " + name + "'s account?");
                            dialogMessage.setPositiveButton("Yes", dialog -> {
                                dialog.dismiss();
                                mViewModel.deactivatePersonnelAccount(id);
                            });
                            dialogMessage.setNegativeButton("No", dialog -> dialogLoad.dismiss());
                            dialogMessage.show();
                        }
                    }).show();
                }
            });

            binding.personnelList.setLayoutManager(layoutManager);
            binding.personnelList.setAdapter(adapter);
        });

        return binding.getRoot();
    }
}