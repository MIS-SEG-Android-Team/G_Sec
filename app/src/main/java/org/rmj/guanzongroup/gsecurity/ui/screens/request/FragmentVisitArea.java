package org.rmj.guanzongroup.gsecurity.ui.screens.request;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.rmj.guanzongroup.gsecurity.databinding.FragmentVisitAreaBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.Dialog_Route_Time;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.inject.Inject;

import timber.log.Timber;

public class FragmentVisitArea extends Fragment {

    @Inject
    VMVisitArea mViewModel;

    private FragmentVisitAreaBinding binding;

    private String warehouseIDxx;

    public static FragmentVisitArea newInstance() {
        return new FragmentVisitArea();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMVisitArea.class);
        binding = FragmentVisitAreaBinding.inflate(getLayoutInflater());
        DialogLoad dialogLoad = new DialogLoad(requireActivity());

        mViewModel.isLoadingWarehouse().observe(getViewLifecycleOwner(), isLoading-> {
            if (isLoading) {
                dialogLoad.show("Loading warehouse list...");
            } else {
                dialogLoad.dismiss();
            }
        });

        mViewModel.isLoadingPersonnel().observe(getViewLifecycleOwner(), isLoading-> {
            if (isLoading) {
                dialogLoad.show("Loading personnel list...");
            } else {
                dialogLoad.dismiss();
            }
        });

        mViewModel.isLoadingCheckpoint().observe(getViewLifecycleOwner(), isLoading-> {
            if (isLoading) {
                dialogLoad.show("Loading checkpoints list...");
            } else {
                dialogLoad.dismiss();
            }
        });

        mViewModel.sendingRequest().observe(getViewLifecycleOwner(), sending -> {
            if (sending) {
                dialogLoad.show("Loading checkpoints list...");
            } else {
                dialogLoad.dismiss();
            }
        });

        mViewModel.hasSentRequest().observe(getViewLifecycleOwner(), requestSent -> {
            if (requestSent) {
                binding.tieWarehouse.setText("");
                binding.tiePersonnel.setText("");
                binding.tieCheckpoint.setText("");
                binding.tieTime.setText("");
                binding.tieRemarks.setText("");
                new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, "Site visitation request has been sent!", Dialog::dismiss).showDialog();
            }
        });

        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage.isEmpty()) { return; }

            new DialogResult(requireActivity(), DialogResult.RESULT.FAILED, errorMessage, Dialog::dismiss).showDialog();
        });

        mViewModel.getWarehouseList().observe(getViewLifecycleOwner(), warehouses -> {
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

                if (!branchExist) {
                    branchNames.add(branch);
                }
            }

            binding.tieBranchName.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, branchNames.toArray()));
            binding.tieBranchName.setOnItemClickListener((parent, view, position, id) -> {
                for (int x = 0; x < warehouses.size(); x ++) {
                    if (binding.tieBranchName.getText().toString().equalsIgnoreCase(warehouses.get(x).getsBranchNm())) {
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
                            mViewModel.setWarehouseID(warehouses.get(x).getSWHouseID());
                            binding.tieCheckpoint.setText("");
                            break;
                        }
                    }
                });
            });
        });

        mViewModel.getPersonnelList().observe(getViewLifecycleOwner(), personnelList -> {
            if (personnelList == null) { return; }
            if (personnelList.isEmpty()) { return; }

            ArrayList<String> personnelNames = new ArrayList<>();
            for (int x = 0; x < personnelList.size(); x++) {
                personnelNames.add(personnelList.get(x).getSUserName());
            }
            binding.tiePersonnel.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, personnelNames));
            binding.tiePersonnel.setOnItemClickListener((parent, view, position, id) -> {
                for (int x = 0; x < personnelList.size(); x++) {

                    String warehouseNm = personnelList.get(x).getSUserName();
                    String warehouseName = binding.tiePersonnel.getText().toString();

                    if (warehouseName.equalsIgnoreCase(warehouseNm)){
                        String personnelID = personnelList.get(x).getSUserIDxx();
                        mViewModel.setPersonnelID(personnelID);
                        break;
                    }
                }
            });
        });

        mViewModel.getWarehouseID().observe(getViewLifecycleOwner(), warehouseID -> {

            if (warehouseID == null) { return; }
            if (warehouseID.isEmpty()) { return; }

            //TODO: IMPORT NFC TAGS
            mViewModel.getNfcTags(warehouseID);

            //TODO: GET CHECKPOINT LIST AND DISPLAY SELECTION FOR SELECTED WAREHOUSE
            mViewModel.getNFCCheckpointList(warehouseID).observe(getViewLifecycleOwner(), checkpointList -> {

                if (checkpointList != null){

                    ArrayList<String> checkpoints = new ArrayList<>();

                    if (checkpointList.size() > 0){

                        for (int x = 0; x < checkpointList.size(); x++) {
                            checkpoints.add(checkpointList.get(x).getSDescript());
                        }

                    }

                    binding.tieCheckpoint.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, checkpoints));
                    binding.tieCheckpoint.setOnItemClickListener((parent, view, position, id) -> {
                        for (int x = 0; x < checkpointList.size(); x++) {

                            String warehouseNm = checkpointList.get(x).getSDescript();
                            String warehouseName = binding.tieCheckpoint.getText().toString();

                            if (warehouseName.equalsIgnoreCase(warehouseNm)){
                                String checkpointID = checkpointList.get(x).getSNFCIDxxx();
                                mViewModel.setCheckpointID(checkpointID);
                                break;
                            }

                        }
                    });

                }

            });

        });

        /*mViewModel.getCheckpointList().observe(getViewLifecycleOwner(), checkpointList -> {

            //todo: pending, bug may exist if the checkpoint list is empty. "last list value is still displayed" -Guillier
            if (checkpointList == null) {
                binding.tieCheckpoint.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, new String[]{""}));
                return;
            }else if (checkpointList.isEmpty()) {
                binding.tieCheckpoint.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, new String[]{""}));
                return;
            }

            ArrayList<String> checkpoints = new ArrayList<>();
            for (int x = 0; x < checkpointList.size(); x++) {

                //todo: add to checkpoint list, if selected warehouse id match the list
                if (checkpointList.get(x).getSWHouseID().equalsIgnoreCase(warehouseIDxx)){

                    //todo: get nfc tag latest timestamp, continue if not empty
                    if (mViewModel.getNFCLatestTimeStamp(warehouseIDxx) != null){

                        if (!mViewModel.getNFCLatestTimeStamp(warehouseIDxx).isEmpty()){

                            Timber.tag("FragmentVisitArea").d(mViewModel.getNFCLatestTimeStamp(warehouseIDxx));

                            //todo: format to local date time to compare
                            LocalDateTime listTimestmp = LocalDateTime.parse(checkpointList.get(x).getDTimeStmp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                            LocalDateTime vmTimestmp = LocalDateTime.parse(mViewModel.getNFCLatestTimeStamp(warehouseIDxx), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                            //todo: compare two local datetime, if list timestamp is after vm timestamp, add to checkpoint list
                            if (listTimestmp.isAfter(vmTimestmp) || listTimestmp.equals(vmTimestmp)) {
                                checkpoints.add(checkpointList.get(x).getSDescript());
                            }
                        }
                    }
                }

            }

            binding.tieCheckpoint.setAdapter(new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, checkpoints));
            binding.tieCheckpoint.setOnItemClickListener((parent, view, position, id) -> {
                for (int x = 0; x < checkpointList.size(); x++) {
                    String warehouseNm = checkpointList.get(x).getSDescript();
                    String warehouseName = binding.tieCheckpoint.getText().toString();
                    if (warehouseName.equalsIgnoreCase(warehouseNm)){
                        String checkpointID = checkpointList.get(x).getSNFCIDxxx();
                        mViewModel.setCheckpointID(checkpointID);
                        break;
                    }
                }
            });
        });*/

        binding.tieTime.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            new TimePickerDialog(requireActivity(), android.R.style.Theme_Holo_Dialog, (view1, hourOfDay, minute1) -> {
                try {
                    String time = hourOfDay + ":" + minute1;
                    Date parseDate = new SimpleDateFormat("HH:mm").parse(time);
                    if (parseDate == null) {
                        Toast.makeText(requireActivity(), "Unknown date time error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String formattedTime = new SimpleDateFormat("hh:mm aa").format(parseDate);
                    mViewModel.setScheduleTime(formattedTime);
                    binding.tieTime.setText(formattedTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },
                    hour,
                    minute,
                    true).show();
        });

        binding.sendRequestButton.setOnClickListener(view-> {

            new Dialog_Route_Time(requireActivity(), new Dialog_Route_Time.DialogRouteTimeCallback() {
                @Override
                public void onClickButton(Integer limit) {

                    mViewModel.setRemarks(Objects.requireNonNull(binding.tieRemarks.getText()).toString());
                    mViewModel.sendVisitationRequest(limit);

                }
            }).show();
        });

        return binding.getRoot();
    }
}