package org.rmj.guanzongroup.gsecurity.Dialog;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import org.rmj.guanzongroup.gsecurity.R;

public class DialogNFC {
    public DialogNFC(Context context) {
        AlertDialog.Builder poBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_nfc, null);
        poBuilder.setCancelable(false)
                .setView(view);
    }
}
