package org.rmj.guanzongroup.gsecurity.ui.components.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.DialogRouteLimitBinding;

import java.util.Objects;

public class Dialog_Route_Time {

    private final AlertDialog alertDialog;

    public interface DialogRouteTimeCallback {
        void onClickButton(Integer limit);
    }

    public Dialog_Route_Time(Context context, DialogRouteTimeCallback callback) {

        DialogRouteLimitBinding binding = DialogRouteLimitBinding.inflate(LayoutInflater.from(context));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(binding.getRoot());
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.PopupAnimation;

        if (!binding.tieInterval.getText().toString().isEmpty()){
            binding.setButton.setEnabled(true);
        }else {
            binding.setButton.setEnabled(false);
        }

        binding.setButton.setOnClickListener(view -> {
            Integer limit = Integer.parseInt(binding.tieInterval.getText().toString());
            callback.onClickButton(limit);
            alertDialog.dismiss();
        });

        binding.cancelButton.setOnClickListener(view -> alertDialog.dismiss());

    }

    public void show(){
        alertDialog.show();
    }
}
