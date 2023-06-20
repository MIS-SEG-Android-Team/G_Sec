package org.rmj.guanzongroup.gsecurity.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.GCircle.Etc.DeptCode;
import org.rmj.g3appdriver.GCircle.ImportData.ImportEmployeeRole;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppDeptIcon;
import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.guanzongroup.gsecurity.Adapter.FragmentAdapter;
import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.ViewModel.VMMainDashboard;

public class Activity_Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private VMMainDashboard mViewModel;
    private LoadDialog poDialog;
    private ViewPager viewPager;
    private MaterialTextView lblDept;
    private ImageView imgDept;
    private Intent loIntent;
    private DrawerLayout drawer;
    private ExpandableListView expListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMMainDashboard.class);
        setContentView(R.layout.activity_dashboard);
        initWidgets();
        mViewModel.getEmployeeInfo().observe(this, eEmployeeInfo -> {
            try{
                AppConfigPreference.getInstance(Activity_Dashboard.this).setIsAppFirstLaunch(false);
                imgDept.setImageResource(AppDeptIcon.getIcon("3"));
                lblDept.setText(DeptCode.getDepartmentName("3"));
                Fragment[] loFragment = new Fragment[]{mViewModel.GetUserFragments(3)};
                viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), loFragment));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    private void initWidgets(){
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        /*Edited by mike*/
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        MessageBox loMessage = new MessageBox(Activity_Dashboard.this);
        poDialog = new LoadDialog(Activity_Dashboard.this);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        expListView.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        viewPager = findViewById(R.id.viewpager);
        imgDept = view.findViewById(R.id.img_deptLogo);
        lblDept = view.findViewById(R.id.lbl_deptNme);
        lblDept.setOnClickListener(v -> {
            ImportEmployeeRole loImport = new ImportEmployeeRole(getApplication());
            loImport.RefreshEmployeeRole(new ImportEmployeeRole.OnImportEmployeeRoleCallback() {
                @Override
                public void OnRequest() {
                    poDialog.initDialog("Guanzon Security", "Refreshing employee access. Please wait...", false);
                    poDialog.show();
                }

                @Override
                public void OnSuccess() {
                    poDialog.dismiss();
                }

                @Override
                public void OnFailed(String message) {
                    poDialog.dismiss();
                    loMessage.initDialog();
                    loMessage.setTitle("Guanzon Security");
                    loMessage.setMessage(message);
                    loMessage.setPositiveButton("Okay", (view1, dialog) -> dialog.dismiss());
                    loMessage.show();
                }
            });
        });
    }
    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}