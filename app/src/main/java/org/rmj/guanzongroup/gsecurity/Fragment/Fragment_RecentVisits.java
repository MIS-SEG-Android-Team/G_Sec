package org.rmj.guanzongroup.gsecurity.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.ViewModel.VMFragmentPersonnelList;
import org.rmj.guanzongroup.gsecurity.ViewModel.VMFragmentRecentVisits;

public class Fragment_RecentVisits extends Fragment {

    private VMFragmentRecentVisits mViewModel;

    public static Fragment_RecentVisits newInstance() {
        return new Fragment_RecentVisits();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recent_visits, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMFragmentRecentVisits.class);
        // TODO: Use the ViewModel
    }

}