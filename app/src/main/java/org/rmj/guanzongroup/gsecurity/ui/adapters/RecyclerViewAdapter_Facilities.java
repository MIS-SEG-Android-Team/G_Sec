package org.rmj.guanzongroup.gsecurity.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.Etc.Facility_List;
import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.ViewHolder.RecyclerViewHolder_Facilities;

import java.util.List;

public class RecyclerViewAdapter_Facilities extends RecyclerView.Adapter<RecyclerViewHolder_Facilities>{

    private Context context;
    private List<Facility_List> list;

    public RecyclerViewAdapter_Facilities(Context context, List<Facility_List> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerViewHolder_Facilities onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_facilities, parent, false);
        return new RecyclerViewHolder_Facilities(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder_Facilities holder, int position) {
        String facilityname = list.get(position).getFacilityname();
        String lastvisit = list.get(position).getRecentvisitor();
        String timesvisited = list.get(position).getTimesvisited();

        Log.d("Facility Name", facilityname);

        holder.mtv_facilityname.setText(facilityname);
        holder.mtv_lastvisit.setText(lastvisit);
        holder.mtv_timesvisit.setText(timesvisited);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
