package org.rmj.guanzongroup.gsecurity.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.ActivityPersonnelBinding;

public class PersonnelActivity extends AppCompatActivity {

    private ActivityPersonnelBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPersonnelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}