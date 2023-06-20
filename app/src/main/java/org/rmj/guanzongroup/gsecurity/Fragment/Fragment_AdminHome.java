package org.rmj.guanzongroup.gsecurity.Fragment;

import androidx.annotation.experimental.UseExperimental;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.rmj.g3appdriver.GCircle.Etc.DeptCode;
import org.rmj.g3appdriver.GCircle.room.Entities.ERaffleStatus;
import org.rmj.g3appdriver.lib.Panalo.model.PanaloRewards;
import org.rmj.guanzongroup.gsecurity.Adapter.FragmentAdapter;
import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.ViewModel.VMFragmentAdminHome;

import java.util.ArrayList;
import java.util.List;

public class Fragment_AdminHome extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VMFragmentAdminHome mViewModel;

    private BottomNavigationView botNav;
    private RecyclerView recyclerView;
    public Fragment_AdminHome() {
        // Required empty public constructor
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mViewModel = new ViewModelProvider(this).get(VMFragmentAdminHome.class);
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initWidgets(view);
        initPersonnelList();
        Fragment[] loFragments = new Fragment[]{
                new Fragment_PersonnelList(),
                new Fragment_RecentVisits()};

        ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), loFragments));
        BottomNavigationView botNav = view.findViewById(R.id.bottomNavigationView);
        botNav.setOnItemSelectedListener(item -> {
            if (item.getItemId()==R.id.lblPersonel){
                viewPager.setCurrentItem(0);
            }else {
                viewPager.setCurrentItem(1);
            }
            return true;
        });
        return view;
    }
public  void  initPersonnelList(){
    Log.e(null,"sample list!");

}
    private void initWidgets(View v) {
        recyclerView = v.findViewById(R.id.rvPersonnelList);
//        lblStatus = v.findViewById(R.id.lblRaffleStatus);
//        rlEmpty = v.findViewById(R.id.rlEmpty);
        LinearLayoutManager loManager = new LinearLayoutManager(requireActivity());
        loManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(loManager);

//        List<PanaloRewards> loList = new ArrayList<>();

    }
}