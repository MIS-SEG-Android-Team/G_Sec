package org.rmj.guanzongroup.gsecurity.nfc;

import android.app.Application;

public class NFCDevice {
    private static final String TAG = NFCDevice.class.getSimpleName();

    private final Application application;

    public NFCDevice(Application application) {
        this.application = application;
    }


}
