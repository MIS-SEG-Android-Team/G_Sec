package org.rmj.guanzongroup.gsecurity.ui.screens.settings.addpersonnel;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.rmj.guanzongroup.gsecurity.databinding.FragmentAddPersonnelBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

import java.util.ArrayList;

import javax.inject.Inject;

public class FragmentAddPersonnel extends Fragment {

    @Inject
    VMAddPersonnel mViewModel;

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

        mViewModel.getPersonnelMPIN().observe(getViewLifecycleOwner(), mpin -> {
            if (mpin.isEmpty()) {
                return;
            }

            binding.tieLastName.setText("");
            binding.tieFirstName.setText("");
            binding.tieMiddleName.setText("");
            binding.tiePosition.setText("");
            binding.tieDescription.setText("");
            binding.tieMPIN.setText(mpin);
            new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, "New personnel info has been saved.", Dialog::dismiss).showDialog();
        });

        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage == null) {
                return;
            }

            if (errorMessage.isEmpty()) {
                return;
            }

            new DialogResult(requireActivity(), DialogResult.RESULT.FAILED, errorMessage, Dialog::dismiss).showDialog();
        });

        mViewModel.getPositions().observe(getViewLifecycleOwner(), positions -> {
            ArrayList<String> list = new ArrayList<>();
            for (int x = 0; x < positions.size(); x++) {
                list.add(positions.get(x).getSPositnNm());
            }
            binding.tiePosition.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, list.toArray()));
            binding.tiePosition.setOnItemClickListener((parent, view, position, id) -> {
                for (int x = 0; x < positions.size(); x++) {
                    if (binding.tiePosition.getText().toString().equalsIgnoreCase(positions.get(position).getSPositnNm())) {
                        mViewModel.setPosition(positions.get(position).getsPositnID());
                        break;
                    }
                }
            });

        });

        binding.savePersonnelButton.setOnClickListener( view -> mViewModel.addPersonnel());

        return binding.getRoot();
    }

    private class TextChangeCallback implements TextWatcher {

        private final View view;

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