package org.rmj.guanzongroup.gsecurity.ui.screens.settings.nfc;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static org.rmj.guanzongroup.gsecurity.constants.Constants.WRITE_NFC_PAYLOAD;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.rmj.guanzongroup.gsecurity.databinding.FragmentAddCheckpointBinding;
import org.rmj.guanzongroup.gsecurity.ui.activity.WriteNfcActivity;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

public class FragmentAddCheckpoint extends Fragment {

    private VMAddPlace mViewModel;

    private DialogMessage dialogMessage;

    private FragmentAddCheckpointBinding binding;

    private final ActivityResultLauncher<Intent> nfcWriterIntent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),  result -> {
        if(result.getResultCode() == RESULT_OK) {
            new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, "New place has been printed to NFC tag.", Dialog::dismiss).showDialog();
        } else if(result.getResultCode() == RESULT_CANCELED) {
            Toast.makeText(requireActivity(), "Writing NFC payload cancelled", Toast.LENGTH_SHORT).show();
        } else {
            new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, "Failed to print new place on NFC tag.", Dialog::dismiss).showDialog();
        }
    });

    public static FragmentAddCheckpoint newInstance() {
        return new FragmentAddCheckpoint();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMAddPlace.class);
        binding = FragmentAddCheckpointBinding.inflate(getLayoutInflater());

        dialogMessage = new DialogMessage(requireActivity());

        binding.saveButton.setOnClickListener(view -> {});

        binding.saveButton.setOnClickListener(view-> {
            Intent intent = new Intent(requireActivity(), WriteNfcActivity.class);
            intent.putExtra(WRITE_NFC_PAYLOAD, "Warehouse 1 Anolid");
            nfcWriterIntent.launch(intent);
        });

        return binding.getRoot();
    }
}