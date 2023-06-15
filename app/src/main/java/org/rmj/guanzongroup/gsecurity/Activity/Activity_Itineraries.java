package org.rmj.guanzongroup.gsecurity.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;

import org.rmj.guanzongroup.gsecurity.Adapter.RecyclerViewAdapter_Itineraries;
import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.ViewModel.VMItineraries;

public class Activity_Itineraries extends AppCompatActivity {
    private RecyclerView rcv_visitlist;
    private VMItineraries vmItineraries;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itineraries);

        vmItineraries = new ViewModelProvider(this).get(VMItineraries.class);
        rcv_visitlist = findViewById(R.id.rcv_visitlist);
        toolbar = findViewById(org.rmj.guanzongroup.authlibrary.R.id.toolbar);

        setSupportActionBar(toolbar); //set object toolbar as default action bar for activity
        getSupportActionBar().setTitle("Itineraries"); //set default title for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set back button to toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true); //enable the back button set on toolbar

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RecyclerViewAdapter_Itineraries recyclerViewAdapter_itineraries = new RecyclerViewAdapter_Itineraries(Activity_Itineraries.this, vmItineraries.getVisitLists());
        rcv_visitlist.setAdapter(recyclerViewAdapter_itineraries);
        rcv_visitlist.setLayoutManager(new LinearLayoutManager(Activity_Itineraries.this, LinearLayoutManager.VERTICAL, false));
    }
}