package org.rmj.guanzongroup.gsecurity.ui.components.adapter.personnel;

import org.rmj.guanzongroup.gsecurity.data.remote.response.PersonnelModel;

public interface AdapterPersonnelCallback {
    void onClickEdit(PersonnelModel personnel);
    void onClickInfo(PersonnelModel personnel);
}
