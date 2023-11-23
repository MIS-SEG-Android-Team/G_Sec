package org.rmj.guanzongroup.gsecurity.ui.components.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.DialogLoadBinding;

import java.util.Objects;

public class DialogLoad {

    private final AlertDialog alertDialog;

    private final DialogLoadBinding binding;

    public DialogLoad(Context context){
        binding = DialogLoadBinding.inflate(LayoutInflater.from(context));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(binding.getRoot());
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.PopupAnimation;
    }

    public void show(String message){
        binding.dialogMessage.setText(message);
        alertDialog.show();
    }

    public void dismiss(){
        alertDialog.dismiss();
    }
}
