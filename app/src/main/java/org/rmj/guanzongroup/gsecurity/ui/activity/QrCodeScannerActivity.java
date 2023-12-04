package org.rmj.guanzongroup.gsecurity.ui.activity;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.QR_CODE_DATA;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.zxing.Result;

import org.rmj.guanzongroup.gsecurity.databinding.ActivityQrCodeScannerBinding;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrCodeScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ActivityQrCodeScannerBinding binding;

    private final ActivityResultLauncher<String> intentCameraPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
        if(result.booleanValue()) {
            binding.scanner.setResultHandler(this);
            binding.scanner.startCamera();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQrCodeScannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(ActivityCompat.checkSelfPermission(QrCodeScannerActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            intentCameraPermission.launch(Manifest.permission.CAMERA);
        } else {
            binding.scanner.setResultHandler(this);
            binding.scanner.startCamera();
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        Intent loIntent = new Intent();
        loIntent.putExtra(QR_CODE_DATA, rawResult.toString());
        setResult(Activity.RESULT_OK, loIntent);
        finish();
    }
}