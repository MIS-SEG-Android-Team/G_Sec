package org.rmj.guanzongroup.gsecurity.Fragment.Home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.ViewModel.VMFragmentAdminHome;

public class Fragment_AdminHome extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VMFragmentAdminHome mViewModel;
    private MaterialTextView lblFullNme ,lblDept;
    private BottomNavigationView botNav;
    private RecyclerView recyclerView;
    private View view;
    public static Fragment_AdminHome newInstance() {
        return new Fragment_AdminHome();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_BHDashboard.
     */

    // TODO: Rename and change types and number of parameters
    public static Fragment_AdminHome newInstance(String param1, String param2) {
        Fragment_AdminHome fragment = new Fragment_AdminHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mViewModel = new ViewModelProvider(this).get(VMFragmentAdminHome.class);
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        lblFullNme = view.findViewById(R.id.txtName);
        initPersonnelList();
        initUserInfo();

        return view;
    }

public  void  initPersonnelList(){
    Log.e(null,"sample list!");}
    private void initUserInfo(){
        mViewModel.getEmployeeInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try {
                //lblEmail.setText(eEmployeeInfo.getEmailAdd());
//                lblUserLvl.setText(DeptCode.parseUserLevel(eEmployeeInfo.getEmpLevID()));
                lblFullNme.setText(eEmployeeInfo.getUserName());
//                lblDept.setText(DeptCode.parseUserLevel(eEmployeeInfo.getEmpLevID()));

            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}
