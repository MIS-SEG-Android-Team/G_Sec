package org.rmj.guanzongroup.gsecurity.ui.components.adapter.checkpoint;

public interface AdapterCheckpointSelectionCallback {
    void onClickCheckpoint(String nfcID, String description);
    void onSelectCheckpoint(String nfcID);
}
