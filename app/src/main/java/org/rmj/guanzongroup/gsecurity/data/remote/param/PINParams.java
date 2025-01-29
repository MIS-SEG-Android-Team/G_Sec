package org.rmj.guanzongroup.gsecurity.data.remote.param;

public class PINParams {

    private String mpin;
    private int vrsnNumber;

    public PINParams() {
    }

    public String getMpin() {
        return mpin;
    }

    public void setMpin(String mpin) {
        this.mpin = mpin;
    }

    public void setVrsnNumber(int vrsnNumber) {
        this.vrsnNumber = vrsnNumber;
    }

    public int getVrsnNumber() {
        return vrsnNumber;
    }
}
