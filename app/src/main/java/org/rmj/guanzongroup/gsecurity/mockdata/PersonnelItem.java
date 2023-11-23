package org.rmj.guanzongroup.gsecurity.mockdata;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.guanzongroup.gsecurity.pojo.user.Personnel;

import java.util.ArrayList;
import java.util.List;

public class PersonnelItem {

    public static LiveData<List<Personnel>> getPersonnelList(){
        MutableLiveData<List<Personnel>> personnelList = new MutableLiveData<>();

        List<Personnel> list = new ArrayList<>();
        list.add(new Personnel("MX00000001","Dela Cruz, Jayson", "Anolid Warehouse", "Building 2 Back Door"));
        list.add(new Personnel("MX00000001","Abrigo, Jonathan", "Anolid Warehouse", "Building 1 Back Door"));
        list.add(new Personnel("MX00000001","Paragas, Arnel", "Anolid Warehouse", "Building 3 Back Door"));
        list.add(new Personnel("MX00000001","Patani, Bartolomeo", "Anolid Warehouse", "Warehouse Lobby"));
        list.add(new Personnel("MX00000001","Saragoza, Jayson", "Anolid Warehouse", "Back Door Entrance Area"));

        personnelList.postValue(list);
        return personnelList;
    }
}
