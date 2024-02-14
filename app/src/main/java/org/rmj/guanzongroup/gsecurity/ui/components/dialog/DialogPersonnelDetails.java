package org.rmj.guanzongroup.gsecurity.ui.components.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.data.remote.response.PersonnelModel;
import org.rmj.guanzongroup.gsecurity.databinding.DialogPersonnelDetailBinding;

import java.util.Objects;

public class DialogPersonnelDetails {

    public interface OnDialogButtonClickCallback {
        void onClickGetReports(String id);
        void OnClickDeactivate(String id, String name);
    }

    private final AlertDialog alertDialog;

    @SuppressLint("SetTextI18n")
    public DialogPersonnelDetails(Context context, PersonnelModel personnelModel, OnDialogButtonClickCallback callback) {
        DialogPersonnelDetailBinding binding = DialogPersonnelDetailBinding.inflate(LayoutInflater.from(context));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(binding.getRoot());
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.PopupAnimation;

        binding.personnelFullName.setText(personnelModel.getSUserName());
        binding.personnelPosition.setText("Position: " + personnelModel.getSPositnID());
        binding.personnelPIN.setText("PIN: " + personnelModel.getNPINCodex());

        binding.closeButton.setOnClickListener(view -> alertDialog.dismiss());
        binding.reportButton.setOnClickListener(view -> {
            alertDialog.dismiss();
            callback.onClickGetReports(personnelModel.getSUserIDxx());
        });
        binding.deactivateButton.setOnClickListener(view -> {
            alertDialog.dismiss();
            callback.OnClickDeactivate(personnelModel.getSUserIDxx(), personnelModel.getSUserName());
        });
    }

    public void show() {
        alertDialog.show();
    }
}
