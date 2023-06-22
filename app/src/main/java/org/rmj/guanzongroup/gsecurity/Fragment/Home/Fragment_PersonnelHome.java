package org.rmj.guanzongroup.gsecurity.Fragment.Home;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.GCircle.Etc.DeptCode;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.ViewModel.VMFragmentPersonnelHome;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class Fragment_PersonnelHome extends Fragment {

    private VMFragmentPersonnelHome mViewModel;

    private MaterialTextView lblFullNme ,lbltime;
    private TextInputEditText txtDate;
    private View view;
    public static Fragment_PersonnelHome newInstance() {
        return new Fragment_PersonnelHome();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMFragmentPersonnelHome.class);
        view = inflater.inflate(R.layout.fragment_personnel_home, container, false);
        lblFullNme = view.findViewById(R.id.lblYourName);
        lbltime = view.findViewById(R.id.lblTime);
        initUserInfo();
        return view;
    }
    private void initWidgets(){

//        lblDept = view.findViewById(R.id.lblEmpPosition);
    }
    private void initUserInfo(){
        Date currentTime = Calendar.getInstance().getTime();
        mViewModel.getEmployeeInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try {
                //lblEmail.setText(eEmployeeInfo.getEmailAdd());
//                lblUserLvl.setText(DeptCode.parseUserLevel(eEmployeeInfo.getEmpLevID()));
                lblFullNme.setText(eEmployeeInfo.getUserName());
//                lblDept.setText(currentTime));
                txtDate.setText(new AppConstants().CURRENT_DATE_WORD);
                txtDate.setOnClickListener(v -> {
                    final Calendar newCalendar = Calendar.getInstance();
                    @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
                    final DatePickerDialog StartTime = new DatePickerDialog(getActivity(), (view131, year, monthOfYear, dayOfMonth) -> {
                        try {
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);
                            String lsDate = dateFormatter.format(newDate.getTime());
                            txtDate.setText(lsDate);
                            Date loDate = new SimpleDateFormat("MMMM dd, yyyy").parse(lsDate);
                            lsDate = new SimpleDateFormat("yyyy-MM-dd").format(loDate);
                            mViewModel.SetSelectedDate(lsDate);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                    StartTime.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                    StartTime.show();
                });
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMFragmentPersonnelHome.class);
        // TODO: Use the ViewModel
    }

}