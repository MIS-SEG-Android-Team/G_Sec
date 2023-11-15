package org.rmj.guanzongroup.gsecurity.ui.components.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.DialogMessageBinding;

import java.util.Objects;

public class DialogMessage {

    private final Context context;

    private AlertDialog alertDialog;

    private DialogMessageBinding binding;

    public DialogMessage(Context context){
        this.context = context;
    }

    public void initDialog(String title, String message){
        binding = DialogMessageBinding.inflate(LayoutInflater.from(context));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(binding.getRoot());
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.PopupAnimation;

        binding.title.setText(title);
        binding.message.setText(message);


    }

    public void setPositiveButton(String text, DialogMessageUICallBack callBack) {
        binding.positiveButton.setVisibility(View.VISIBLE);
        binding.positiveButton.setText(text);
        binding.positiveButton.setOnClickListener(view -> callBack.OnDialogButtonClick(alertDialog));
    }

    public void setNegativeButton(String text, DialogMessageUICallBack callBack) {
        binding.negativeButton.setVisibility(View.VISIBLE);
        binding.negativeButton.setText(text);
        binding.negativeButton.setOnClickListener(view -> callBack.OnDialogButtonClick(alertDialog));
    }

    public void show(){
        alertDialog.show();
    }

    public interface DialogMessageUICallBack{
        void OnDialogButtonClick(AlertDialog dialog);
    }
}
