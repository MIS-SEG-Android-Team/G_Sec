package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.patrolroute;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.rmj.guanzongroup.gsecurity.databinding.FragmentPatrolRouteBinding;
import org.rmj.guanzongroup.gsecurity.pojo.itinerary.PatrolRoute;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.AdapterPatrolRoute;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogNFC;

import java.util.List;

public class FragmentPatrolRoute extends Fragment {

    private VMItineraries mViewModel;

    private FragmentPatrolRouteBinding binding;

    public static FragmentPatrolRoute newInstance() {
        return new FragmentPatrolRoute();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMItineraries.class);
        binding = FragmentPatrolRouteBinding.inflate(getLayoutInflater());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mViewModel.getItineraryList().observe(getViewLifecycleOwner(), patrolRoutes -> {
            if(patrolRoutes == null) {
                return;
            }

            binding.patrolRouteList.setLayoutManager(linearLayoutManager);
            binding.patrolRouteList.setAdapter(new AdapterPatrolRoute(patrolRoutes, nfcID -> {
                DialogNFC dialogNFC = new DialogNFC(requireActivity());
                dialogNFC.show();
            }));
        });

        return binding.getRoot();
    }
}