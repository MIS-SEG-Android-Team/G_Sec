package org.rmj.guanzongroup.gsecurity.ui.components.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.DialogResultBinding;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

public class DialogResult {

    private static AlertDialog alertDialog;

    public enum RESULT{
        SUCCESS,
        FAILED
    }

    public DialogResult(Context context, RESULT result, String message){
        DialogResultBinding binding = DialogResultBinding.inflate(LayoutInflater.from(context));

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(binding.getRoot());
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.PopupAnimation;

        if(result == RESULT.SUCCESS)
            binding.imageResult.setImageResource(R.drawable.ic_square_check);
        else
            binding.imageResult.setImageResource(R.drawable.ic_square_x);

        binding.message.setText(message);

        binding.closeButton.setOnClickListener(view -> {
//            listener.onClick(alertDialog);
            alertDialog.dismiss();
        });
    }

    public void showDialog(){
        alertDialog.show();
    }

    public interface onDialogButtonClickListener{
        void onClick(AlertDialog dialog);
    }
}
