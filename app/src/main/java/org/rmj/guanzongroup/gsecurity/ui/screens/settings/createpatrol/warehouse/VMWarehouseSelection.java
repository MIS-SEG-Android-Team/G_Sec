package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.warehouse;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.repository.PersonnelRepository;
import org.rmj.guanzongroup.gsecurity.data.repository.WarehouseRepository;
import org.rmj.guanzongroup.gsecurity.preferences.PatrolSchedulerCache;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class VMWarehouseSelection extends ViewModel {

    private final WarehouseRepository warehouseRepository;
    private final PersonnelRepository personnelRepository;
    private final PatrolSchedulerCache schedulerCache;

    private final MutableLiveData<Boolean> importedWarehouse = new MutableLiveData<>(false);

    @Inject
    public VMWarehouseSelection(
            WarehouseRepository warehouseRepository,
            PersonnelRepository personnelRepository,
            PatrolSchedulerCache schedulerCache
    ) {
        this.warehouseRepository = warehouseRepository;
        this.personnelRepository = personnelRepository;
        this.schedulerCache = schedulerCache;
    }

//    private void importWarehouse() {
//        warehouseRepository.addWarehouse()
//    }
}