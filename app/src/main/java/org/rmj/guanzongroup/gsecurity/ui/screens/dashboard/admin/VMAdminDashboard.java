package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.admin;

import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.repository.BranchRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class VMAdminDashboard extends ViewModel {

    @Inject
    public VMAdminDashboard(BranchRepository branchRepository) {

    }
}