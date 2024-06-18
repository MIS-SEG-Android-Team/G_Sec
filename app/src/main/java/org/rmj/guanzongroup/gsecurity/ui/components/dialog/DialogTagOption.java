package org.rmj.guanzongroup.gsecurity.ui.components.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.DialogTagOptionBinding;

import java.util.Objects;

public class DialogTagOption {

    private final AlertDialog alertDialog;

    public interface DialogTagOptionCallback {
        void onClickNFCButton(String remarks);
        void onClickQrCodeButton(String remarks);
    }

    private String remarks = "";

    public DialogTagOption(Context context, String visitedPlace, DialogTagOptionCallback callback) {
        DialogTagOptionBinding binding = DialogTagOptionBinding.inflate(LayoutInflater.from(context));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(binding.getRoot());
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.PopupAnimation;

        binding.title.setText(visitedPlace);

        binding.nfcButton.setOnClickListener(view -> {
            remarks = Objects.requireNonNull(binding.tieRemarks.getText()).toString();
            callback.onClickNFCButton(remarks);
            alertDialog.dismiss();
        });

        binding.qrCodeButton.setOnClickListener(view -> {
            callback.onClickQrCodeButton(remarks);
            alertDialog.dismiss();
        });

        binding.cancelButton.setOnClickListener(view -> alertDialog.dismiss());
    }

    public void show(){
        alertDialog.show();
    }
}
