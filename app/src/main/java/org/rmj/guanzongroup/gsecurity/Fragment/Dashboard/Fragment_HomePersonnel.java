package org.rmj.guanzongroup.gsecurity.Fragment.Dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.rmj.guanzongroup.gsecurity.Adapter.FragmentAdapter;
import org.rmj.guanzongroup.gsecurity.Fragment.Fragment_PersonnelList;
import org.rmj.guanzongroup.gsecurity.Fragment.Home.Fragment_AdminHome;
import org.rmj.guanzongroup.gsecurity.Fragment.Home.Fragment_PersonnelHome;
import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.ViewModel.VMFragmentDashboard;
import org.rmj.guanzongroup.gsecurity.ViewModel.VMFragmentHomePersonnel;

public class Fragment_HomePersonnel extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ViewPager viewPager;
    private VMFragmentHomePersonnel mViewModel;

    private BottomAppBar botAppBar;
    private BottomNavigationView botNav;
//    public static Fragment_Dashboard newInstance() {
//        return new Fragment_Dashboard();
//    }
    public static Fragment_HomePersonnel newInstance(String param1, String param2) {
        Fragment_HomePersonnel fragment = new Fragment_HomePersonnel();
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

        mViewModel = new ViewModelProvider(requireActivity()).get(VMFragmentHomePersonnel.class);
        View view = inflater.inflate(R.layout.fragment_homeperssonel, container, false);

        Fragment[] loFragments = new Fragment[]{
                new Fragment_AdminHome(),
                new Fragment_PersonnelHome(),
                new Fragment_PersonnelList()};

        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), loFragments));

        botNav = view.findViewById(R.id.bottomNavigationView);
            botNav.setOnItemSelectedListener(item -> {

                if (item.getItemId()==R.id.lblPersonel){
                    viewPager.setCurrentItem(0);
                }else {
                    viewPager.setCurrentItem(1);
                }

//                switch (item.getItemId()){
//                    case R.id.lblPersonel:
//                        viewPager.setCurrentItem(0);
//                        break;
//                    case R.id.lblFacilities:
//                        viewPager.setCurrentItem(1);
//                        break;
//                }
                return true;
            });
        return view;
    }
}