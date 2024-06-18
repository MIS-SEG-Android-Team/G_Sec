package org.rmj.guanzongroup.gsecurity.ui.screens.report;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.PERSONNEL_ID;
import static org.rmj.guanzongroup.gsecurity.constants.Constants.PERSONNEL_NAME;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.opencsv.CSVWriter;

import org.rmj.guanzongroup.gsecurity.data.remote.response.patrolreport.PersonnelPatrolReport;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentPatrolReportBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.reports.AdapterPersonnelReports;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class FragmentPatrolReport extends Fragment {

    @Inject
    VMPatrolReport mViewModel;

    private FragmentPatrolReportBinding binding;

    private String userName;

    public static FragmentPatrolReport newInstance() {
        return new FragmentPatrolReport();
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMPatrolReport.class);
        binding = FragmentPatrolReportBinding.inflate(getLayoutInflater());

        String userID;
        if (getArguments() != null) {
            userID = getArguments().getString(PERSONNEL_ID);
            userName = getArguments().getString(PERSONNEL_NAME);
            mViewModel.setUserID(userID);
            mViewModel.setUserName(userName);
        }

        binding.editTextDateStart.setOnClickListener(view -> {
            final Calendar newCalendar = Calendar.getInstance();
            final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            final DatePickerDialog StartTime = new DatePickerDialog(requireActivity(), (view131, year, monthOfYear, dayOfMonth) -> {
                try {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    String lsDate = dateFormatter.format(newDate.getTime());
                    binding.editTextDateStart.setText(lsDate);
                    Date loDate = new SimpleDateFormat("MMMM dd, yyyy").parse(lsDate);
                    lsDate = new SimpleDateFormat("yyyy-MM-dd").format(loDate);
                    mViewModel.setDateStart(lsDate);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            StartTime.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
            StartTime.show();
        });

        binding.editTextDateEnd.setOnClickListener(view -> {
            final Calendar newCalendar = Calendar.getInstance();
            final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            final DatePickerDialog StartTime = new DatePickerDialog(requireActivity(), (view131, year, monthOfYear, dayOfMonth) -> {
                try {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    String lsDate = dateFormatter.format(newDate.getTime());
                    binding.editTextDateEnd.setText(lsDate);
                    Date loDate = new SimpleDateFormat("MMMM dd, yyyy").parse(lsDate);
                    lsDate = new SimpleDateFormat("yyyy-MM-dd").format(loDate);
                    mViewModel.setDateEnd(lsDate);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            StartTime.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
            StartTime.show();
        });

        mViewModel.isLoadingReports().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.reportsList.setVisibility(View.GONE);
                binding.loadingReports.setVisibility(View.VISIBLE);
            } else {
                binding.reportsList.setVisibility(View.VISIBLE);
                binding.loadingReports.setVisibility(View.GONE);
            }
        });

        mViewModel.getReports().observe(getViewLifecycleOwner(), reports -> {
            binding.exportButton.setEnabled(false);
            if (reports == null) { return; }
            if (reports.isEmpty()) { return; }

            LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            AdapterPersonnelReports adapter = new AdapterPersonnelReports(reports);
            binding.reportsList.setLayoutManager(layoutManager);
            binding.reportsList.setAdapter(adapter);

            binding.exportButton.setEnabled(true);
            binding.exportButton.setOnClickListener(view -> exportReport(reports));
        });

        mViewModel.completeInfo().observe(getViewLifecycleOwner(), hasCompleteInfo -> binding.reportButton.setEnabled(hasCompleteInfo));
        binding.reportButton.setOnClickListener(view -> mViewModel.getPatrolReportForPersonnel());

        return binding.getRoot();
    }

    private void exportReport(List<PersonnelPatrolReport> reports) {
//        String root = String.valueOf(requireActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS));
        String docsDir = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS));
        String folderName = "/GSecure Reports/";
        File fileDir = new File(docsDir + folderName);
        if (!fileDir.exists()) {
            if (fileDir.mkdirs()) {
                Timber.d("Sub Folder has been created!!!");
            }
        }

        File csvFile = new File(fileDir, mViewModel.getExportFileName() + ".csv");

        try {
            if(csvFile.createNewFile()) {
                Timber.d("New file created!!!");
            }

            CSVWriter csvWrite = getCsvWriter(reports, csvFile);

            csvWrite.close();
            Timber.d("Report has been exported!!!");
            new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, "Your report has been successfully exported. You can find the file in the File Explorer under Documents -> GSecure Reports", Dialog::dismiss).showDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private static CSVWriter getCsvWriter(List<PersonnelPatrolReport> reports, File csvFile) throws IOException {
        CSVWriter csvWrite = new CSVWriter(new FileWriter(csvFile));

        String[] heading = {"NFC ID", "Checkpoint", "User ID", "Personnel", "Patrol Schedule", "Time Visited", "Requested", "Remarks"};
        csvWrite.writeNext(heading);

        String schedule;
        for (int i = 0; i < reports.size(); i++) {
            String[] arrStr = getStrings(reports, i);
            csvWrite.writeNext(arrStr);
        }
        return csvWrite;
    }

    @NonNull
    private static String[] getStrings(List<PersonnelPatrolReport> reports, int i) {
        PersonnelPatrolReport model = reports.get(i); // You can also use JsonObject from Json array.
        String[] arrStr = {
                "'" + model.getSNFCIDxxx(), // NFC ID heading...
                model.getSDescript(), // Checkpoint
                model.getSUserIDxx(), // User ID
                model.getSOfficerx(), // Personnel
                model.getDSchedule(), // Patrol Schedule
                model.getDVisitedx(), // Time Visited
                model.getCRequestd(), // Requested
                model.getSRemarksx()}; // Remarks
        return arrStr;
    }
}