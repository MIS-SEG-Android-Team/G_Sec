package org.rmj.guanzongroup.gsecurity.ui.screens.settings.addpersonnel;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.databinding.FragmentAddPersonnelBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

import javax.inject.Inject;

public class FragmentAddPersonnel extends Fragment {

    @Inject
    VMAddPersonnel mViewModel;

    private DialogResult dialogResult;
    private DialogLoad dialogLoad;

    private FragmentAddPersonnelBinding binding;

    public static FragmentAddPersonnel newInstance() {
        return new FragmentAddPersonnel();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMAddPersonnel.class);
        binding = FragmentAddPersonnelBinding.inflate(getLayoutInflater());
        dialogLoad = new DialogLoad(requireActivity());

        binding.tieLastName.addTextChangedListener(new TextChangeCallback(binding.tieLastName));
        binding.tieFirstName.addTextChangedListener(new TextChangeCallback(binding.tieFirstName));
        binding.tieMiddleName.addTextChangedListener(new TextChangeCallback(binding.tieMiddleName));
        binding.tieDescription.addTextChangedListener(new TextChangeCallback(binding.tieDescription));

        mViewModel.isLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                dialogLoad.show("Saving new personnel info...");
            } else {
                dialogLoad.dismiss();
            }
        });

        mViewModel.hasCompleteInfo().observe(getViewLifecycleOwner(), hasCompleteInfo -> binding.savePersonnelButton.setEnabled(hasCompleteInfo));

        mViewModel.isPersonnelAdded().observe(getViewLifecycleOwner(), isPersonnelAdded -> {
            if (isPersonnelAdded) {
                binding.tieLastName.setText("");
                binding.tieFirstName.setText("");
                binding.tieMiddleName.setText("");
                binding.tiePosition.setText("");
                binding.tieDescription.setText("");
                dialogResult = new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, "New personnel info has been saved.");
                dialogResult.showDialog();
            }
        });

        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage == null) {
                return;
            }

            if (errorMessage.isEmpty()) {
                return;
            }

            dialogResult = new DialogResult(requireActivity(), DialogResult.RESULT.FAILED, errorMessage);
            dialogResult.showDialog();
        });

        binding.savePersonnelButton.setOnClickListener( view -> mViewModel.addPersonnel());

        return binding.getRoot();
    }

    private class TextChangeCallback implements TextWatcher {

        private View view;

        public TextChangeCallback(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int id = view.getId();

            if(id == binding.tieLastName.getId()) {
                mViewModel.setLastName(s.toString());
            } else if(id == binding.tieFirstName.getId()) {
                mViewModel.setFirstName(s.toString());
            } else if(id == binding.tieMiddleName.getId()) {
                mViewModel.setMiddleName(s.toString());
            } else if(id == binding.tieDescription.getId()) {
                mViewModel.setPositionDescription(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}