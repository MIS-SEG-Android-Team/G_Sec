package org.rmj.guanzongroup.gsecurity.ui.screens.settings.position;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentPositionBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

import java.util.Objects;

import javax.inject.Inject;

public class FragmentPosition extends Fragment {

    @Inject
    VMPosition mViewModel;

    private FragmentPositionBinding binding;

    public static FragmentPosition newInstance() {
        return new FragmentPosition();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMPosition.class);
        binding = FragmentPositionBinding.inflate(getLayoutInflater());
        DialogLoad dialogLoad = new DialogLoad(requireActivity());
        DialogMessage dialogMessage = new DialogMessage(requireActivity());

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_admin);
        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();

        mViewModel.savingPosition().observe(getViewLifecycleOwner(), savingPosition -> {
            if (savingPosition) {
                dialogLoad.show("Adding a new role to the system...");
            } else {
                dialogLoad.dismiss();
            }
        });

        mViewModel.successfullySave().observe(getViewLifecycleOwner(), successfullySave -> {
            if (successfullySave) {
                dialogMessage.initDialog("Position", "Position saved! Add new role?");
                dialogMessage.setPositiveButton("Close", dialog -> {
                    dialog.dismiss();
                    binding.tiePosition.setText("");
                    navController.popBackStack();
                });
                dialogMessage.show();
            }
        });

        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage == null) {
                return;
            }
            if (errorMessage.isEmpty()) {
                return;
            }

            new DialogResult(requireActivity(), DialogResult.RESULT.FAILED, errorMessage).showDialog();
        });

        binding.tiePosition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0) {
                    mViewModel.setPosition(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mViewModel.hasCompleteInfo().observe(getViewLifecycleOwner(), hasCompleteInfo -> binding.saveButton.setEnabled(hasCompleteInfo));

        binding.saveButton.setOnClickListener(view -> mViewModel.addPosition());

        return binding.getRoot();
    }
}