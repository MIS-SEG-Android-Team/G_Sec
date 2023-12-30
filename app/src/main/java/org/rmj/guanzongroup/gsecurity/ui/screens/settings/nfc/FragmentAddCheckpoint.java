package org.rmj.guanzongroup.gsecurity.ui.screens.settings.nfc;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static org.rmj.guanzongroup.gsecurity.constants.Constants.WRITE_NFC_DATA_CATEGORY_ID;
import static org.rmj.guanzongroup.gsecurity.constants.Constants.WRITE_NFC_DATA_DESCRIPTION;
import static org.rmj.guanzongroup.gsecurity.constants.Constants.WRITE_NFC_DATA_WAREHOUSE_ID;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import org.rmj.guanzongroup.gsecurity.databinding.FragmentAddCheckpointBinding;
import org.rmj.guanzongroup.gsecurity.ui.activity.WriteNfcActivity;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

import java.util.ArrayList;

import javax.inject.Inject;

public class FragmentAddCheckpoint extends Fragment {

    @Inject
    VMAddCheckpoint mViewModel;

    private FragmentAddCheckpointBinding binding;

    private final ActivityResultLauncher<Intent> nfcWriterIntent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),  result -> {
        if(result.getResultCode() == RESULT_OK) {
            mViewModel.addCheckpoint();
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
        mViewModel = new ViewModelProvider(requireActivity()).get(VMAddCheckpoint.class);
        binding = FragmentAddCheckpointBinding.inflate(getLayoutInflater());
        DialogLoad dialogLoad = new DialogLoad(requireActivity());
        DialogMessage dialogMessage = new DialogMessage(requireActivity());

        mViewModel.getWarehouses().observe(getViewLifecycleOwner(), warehouses -> {
            if (warehouses == null) {
                return;
            }

            if (warehouses.size() == 0) {
                return;
            }

            ArrayList<String> list = new ArrayList<>();
            for (int x = 0; x < warehouses.size(); x++) {
                list.add(warehouses.get(x).getSWHouseNm());
            }
            binding.tieWarehouse.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, list.toArray()));
            binding.tieWarehouse.setOnItemClickListener((parent, view, position, id) -> {
                for (int x = 0; x < warehouses.size(); x++) {
                    if (binding.tieWarehouse.getText().toString().equalsIgnoreCase(warehouses.get(position).getSWHouseNm())) {
                        mViewModel.setCategory(warehouses.get(position).getSWHouseNm());
                        break;
                    }
                }
            });
        });

        mViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            if (categories == null) {
                return;
            }

            if (categories.size() == 0) {
                return;
            }

            ArrayList<String> list = new ArrayList<>();
            for (int x = 0; x < categories.size(); x++) {
                list.add(categories.get(x).getSCategory());
            }
            binding.tieCategory.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, list.toArray()));
            binding.tieCategory.setOnItemClickListener((parent, view, position, id) -> {
                for (int x = 0; x < categories.size(); x++) {
                    if (binding.tieCategory.getText().toString().equalsIgnoreCase(categories.get(position).getSCategory())) {
                        mViewModel.setCategory(categories.get(position).getSCategory());
                        break;
                    }
                }
            });
        });

        mViewModel.addingCheckpoint().observe(getViewLifecycleOwner(), addingCheckpoint -> {
            if (addingCheckpoint) {
                dialogLoad.show("Adding new checkpoint...");
            } else {
                dialogLoad.dismiss();
            }
        });

        mViewModel.checkpointAdded().observe(getViewLifecycleOwner(), checkpointAdded -> {
            if (checkpointAdded) {
                dialogMessage.initDialog("NFC TAG", "A new patrol checkpoint was added. Add another one?");
                dialogMessage.setPositiveButton("Yes", dialog -> {
                    binding.tieWarehouse.setText("");
                    binding.tieCategory.setText("");
                    binding.tieDescription.setText("");
                });
                dialogMessage.setNegativeButton("No", dialog -> {
                    dialog.dismiss();
                    Navigation.findNavController(binding.getRoot()).popBackStack();
                });
            }
        });

        binding.saveButton.setOnClickListener(view -> {});

        binding.saveButton.setOnClickListener(view-> {
            Intent intent = new Intent(requireActivity(), WriteNfcActivity.class);
            intent.putExtra(WRITE_NFC_DATA_WAREHOUSE_ID, mViewModel.getWarehouseID());
            intent.putExtra(WRITE_NFC_DATA_CATEGORY_ID, mViewModel.getCategoryID());
            intent.putExtra(WRITE_NFC_DATA_DESCRIPTION, mViewModel.getDescription());
            nfcWriterIntent.launch(intent);
        });

        return binding.getRoot();
    }
}