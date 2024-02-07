package org.rmj.guanzongroup.gsecurity.ui.screens.settings.nfc;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static org.rmj.guanzongroup.gsecurity.constants.Constants.WRITE_NFC_DATA_PAYLOAD;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
        binding = FragmentAddCheckpointBinding.inflate(getLayoutInflater());
        mViewModel = new ViewModelProvider(requireActivity()).get(VMAddCheckpoint.class);
        DialogLoad dialogLoad = new DialogLoad(requireActivity());
        DialogMessage dialogMessage = new DialogMessage(requireActivity());

        // Observables...
        mViewModel.isLoadingData().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.loadingData.setVisibility(View.VISIBLE);
                binding.inputFields.setVisibility(View.GONE);
                binding.errorMessageNotice.setVisibility(View.GONE);
            } else {
                binding.loadingData.setVisibility(View.GONE);
                binding.inputFields.setVisibility(View.VISIBLE);
            }
        });
        mViewModel.getDataErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if(message == null) { return; }
            if(message.isEmpty()) { return; }
            binding.errorMessageNotice.setVisibility(View.VISIBLE);
            binding.errorMessage.setText(message);
            binding.inputFields.setVisibility(View.GONE);
        });
        mViewModel.addingCheckpoint().observe(getViewLifecycleOwner(), addingCheckpoint -> {
            if (addingCheckpoint) {
                dialogLoad.show("Adding NFC checkpoint...");
            } else {
                dialogLoad.dismiss();
            }
        });

        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if(message == null) { return; }
            if(message.isEmpty()) { return; }
            new DialogResult(requireActivity(), DialogResult.RESULT.FAILED, message, Dialog::dismiss).showDialog();
        });

        mViewModel.checkpointAdded().observe(getViewLifecycleOwner(), checkpointAdded -> {
            if (checkpointAdded) {
                new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, "New checkpoint added.", dialog -> {
                    dialog.dismiss();
                    mViewModel.setDescription("");
                    mViewModel.setWarehouse("");
                    mViewModel.setCategory("");
                    binding.tieWarehouse.setText("");
                    binding.tieCategory.setText("");
                    binding.tieDescription.setText("");
                }).showDialog();
            }
        });

        mViewModel.getWarehouses().observe(getViewLifecycleOwner(), warehouses -> {
            if (warehouses == null) { return; }
            if (warehouses.size() == 0) { return; }

            ArrayList<String> branchNames = new ArrayList<>();
            for (int x = 0; x < warehouses.size(); x++) {
                String branch = warehouses.get(x).getsBranchNm();

                // Validate if the branch has exists in the current branch list...
                // If the branch name already exists. Skip the current iteration to avoid duplicate.
                boolean branchExist = false;
                for (String branchName : branchNames) {
                    if (branchName.equalsIgnoreCase(branch)) {
                        branchExist = true;
                        break;
                    }
                }

                if (branchExist) { break; }

                branchNames.add(branch);
            }

            binding.tieBranchName.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, branchNames.toArray()));
            binding.tieBranchName.setOnItemClickListener((parent, view, position, id) -> {
                for (int x = 0; x < branchNames.size(); x ++) {
                    if (binding.tieBranchName.getText().toString().equalsIgnoreCase(branchNames.get(x))) {
                        mViewModel.setBranch(warehouses.get(x).getSBranchCd());
                        break;
                    }
                }
            });

            mViewModel.getBranch().observe(getViewLifecycleOwner(), branch -> {
                if (branch.isEmpty()) { return; }
                ArrayList<String> warehouseNames = new ArrayList<>();
                for (int x = 0; x < warehouses.size(); x++) {
                    if (warehouses.get(x).getSBranchCd().equalsIgnoreCase(branch)) {
                        String warehouse = warehouses.get(x).getSWHouseNm();
                        warehouseNames.add(warehouse);
                    }
                }
                binding.tieWarehouse.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, warehouseNames.toArray()));
                binding.tieWarehouse.setOnItemClickListener((parent, view, position, id) -> {
                    for (int x = 0; x < warehouses.size(); x++) {
                        if (binding.tieWarehouse.getText().toString().equalsIgnoreCase(warehouses.get(x).getSWHouseNm())) {
                            mViewModel.setWarehouse(warehouses.get(x).getSWHouseID());
                            break;
                        }
                    }
                });
            });
        });

        mViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            if (categories == null) { return; }
            if (categories.size() == 0) { return; }

            ArrayList<String> list = new ArrayList<>();
            for (int x = 0; x < categories.size(); x++) {
                list.add(categories.get(x).getSCategory());
            }
            binding.tieCategory.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, list.toArray()));
            binding.tieCategory.setOnItemClickListener((parent, view, position, id) -> {
                for (int x = 0; x < categories.size(); x++) {
                    if (binding.tieCategory.getText().toString().equalsIgnoreCase(categories.get(position).getSCategory())) {
                        mViewModel.setCategory(categories.get(position).getsCatIDxxx());
                        break;
                    }
                }
            });
        });

        binding.tieDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() != 0) {
                    mViewModel.setDescription(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
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
            try {
                String payload = mViewModel.getAddCheckpointParams();
                Intent intent = new Intent(requireActivity(), WriteNfcActivity.class);
                intent.putExtra(WRITE_NFC_DATA_PAYLOAD, payload);
                nfcWriterIntent.launch(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return binding.getRoot();
    }
}