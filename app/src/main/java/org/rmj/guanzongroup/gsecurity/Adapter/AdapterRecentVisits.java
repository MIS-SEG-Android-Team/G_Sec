package org.rmj.guanzongroup.gsecurity.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.ViewModel.AdapterOfficersListViewModel;
import org.rmj.guanzongroup.gsecurity.ViewModel.AdapterRecentVisitsViewModel;

public class AdapterRecentVisits extends Fragment {

    private AdapterRecentVisitsViewModel mViewModel;

    public static AdapterRecentVisits newInstance() {
        return new AdapterRecentVisits();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_recent_visits, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AdapterRecentVisitsViewModel.class);
        // TODO: Use the ViewModel
    }

}