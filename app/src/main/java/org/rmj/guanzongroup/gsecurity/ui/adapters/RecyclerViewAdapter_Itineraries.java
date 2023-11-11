package org.rmj.guanzongroup.gsecurity.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.Etc.VisitLists;
import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.ViewHolder.RecyclerViewHolder_Itineraries;

import java.util.List;

public class RecyclerViewAdapter_Itineraries extends RecyclerView.Adapter<RecyclerViewHolder_Itineraries> {
    private Context context;
    private List<VisitLists> list;

    public RecyclerViewAdapter_Itineraries(Context context, List<VisitLists> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerViewHolder_Itineraries onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_itinerarylist, parent, false);
        return new RecyclerViewHolder_Itineraries(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder_Itineraries holder, int position) {
        String place = list.get(position).getPlacevisit();
        String timesched = list.get(position).getTimeevisit();
        String timearrived = list.get(position).getTimearrived();

        holder.mtv_place.setText(place);
        holder.mtv_timevisit.setText(timesched);
        holder.mtv_time.setText(timearrived);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
