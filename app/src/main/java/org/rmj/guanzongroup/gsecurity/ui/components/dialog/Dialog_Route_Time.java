package org.rmj.guanzongroup.gsecurity.ui.components.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
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

        binding.tieInterval.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0){
                    binding.setButton.setEnabled(true);
                }else {
                    binding.setButton.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
