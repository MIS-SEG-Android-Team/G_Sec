package org.rmj.guanzongroup.gsecurity.ui.screens.nfc;

import static android.app.Activity.RESULT_OK;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.NFC_PAYLOAD;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.rmj.guanzongroup.gsecurity.databinding.FragmentAddPlaceBinding;
import org.rmj.guanzongroup.gsecurity.ui.activity.WriteNfcActivity;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

public class FragmentAddPlace extends Fragment {

    private VMAddPlace mViewModel;

    private DialogMessage dialogMessage;

    private FragmentAddPlaceBinding binding;

    private final ActivityResultLauncher<Intent> nfcWritterIntent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),  result -> {
        if(result.getResultCode() == RESULT_OK) {
            new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, "New place has been printed to NFC tag.").showDialog();
        } else {
            new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, "Failed to print new place on NFC tag.").showDialog();
        }
    });

    public static FragmentAddPlace newInstance() {
        return new FragmentAddPlace();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMAddPlace.class);
        binding = FragmentAddPlaceBinding.inflate(getLayoutInflater());

        dialogMessage = new DialogMessage(requireActivity());

        binding.closeButton.setOnClickListener(view -> {});

        binding.saveButton.setOnClickListener(view-> {
            Intent intent = new Intent(requireActivity(), WriteNfcActivity.class);
            intent.putExtra(NFC_PAYLOAD, "Warehouse 1 Anolid");
            nfcWritterIntent.launch(intent);
        });

        return binding.getRoot();
    }
}