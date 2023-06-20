package org.rmj.guanzongroup.gsecurity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;

import org.rmj.guanzongroup.gsecurity.Adapter.RecyclerViewAdapter_Facilities;
import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.ViewModel.VMFacilities;

public class Activity_Facilities extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private RecyclerView rcv_facilities;
    private VMFacilities vmFacilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilities);

        vmFacilities = new ViewModelProvider(this).get(VMFacilities.class);
        toolbar = findViewById(org.rmj.guanzongroup.authlibrary.R.id.toolbar);
        rcv_facilities = findViewById(R.id.rcv_facilities);

        setSupportActionBar(toolbar); //set object toolbar as default action bar for activity
        getSupportActionBar().setTitle("Facilities"); //set default title for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set back button to toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true); //enable the back button set on toolbar

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RecyclerViewAdapter_Facilities recyclerViewAdapter_facilities = new RecyclerViewAdapter_Facilities(Activity_Facilities.this, vmFacilities.getFacilityLists());
        rcv_facilities.setAdapter(recyclerViewAdapter_facilities);
        rcv_facilities.setLayoutManager(new LinearLayoutManager(Activity_Facilities.this, LinearLayoutManager.VERTICAL, false));
    }
}