package org.rmj.guanzongroup.gsecurity.ui.components.adapter;

import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.ListItemPatrolRouteBinding;
import org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.patrolroute.PatrolCheckpoint;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdapterPatrolRoute extends RecyclerView.Adapter<AdapterPatrolRoute.ItineraryViewHolder> {

    private final List<PatrolCheckpoint> patrolRouteList;
    private final PatrolRouteClickListener mListener;
    private final String patrolCacheSchedule;
    private final String patrolCacheCheckpoint;

    public interface PatrolRouteClickListener{
        void onClick(PatrolCheckpoint patrol, int position);
    }

    public AdapterPatrolRoute(List<PatrolCheckpoint> patrolRouteList, String patrolCacheSchedule, String patrolCacheCheckpoint, PatrolRouteClickListener listener) {
        this.patrolRouteList = patrolRouteList;
        this.mListener = listener;
        this.patrolCacheSchedule = patrolCacheSchedule;
        this.patrolCacheCheckpoint = patrolCacheCheckpoint;
    }

    @NonNull
    @Override
    public ItineraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItineraryViewHolder(
                ListItemPatrolRouteBinding.inflate(
                        LayoutInflater.from(
                                parent.getContext()
                        ), parent, false
                )
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ItineraryViewHolder holder, int position) {

        PatrolCheckpoint patrolRoute = patrolRouteList.get(position);

        holder.binding.nfcSiteDescription.setText(patrolRoute.getsDescript());

        if(patrolRoute.isVisited())
            holder.binding.patrolRouteIcon.setImageResource(R.drawable.ic_location_check);
        else
            holder.binding.patrolRouteIcon.setImageResource(R.drawable.ic_location_next);

        if (!patrolCacheCheckpoint.isEmpty()){

            //todo: check if current checkpoint is same with one of the list, then display schedule to the current item
            if (patrolRoute.getsNFCIDxxx().equalsIgnoreCase(patrolCacheCheckpoint)){

                //todo: check if current schedule is not empty
                if (!patrolCacheSchedule.isEmpty()){

                    //todo: display schedule
                    holder.binding.nextsched
                            .setText(LocalTime.parse(patrolCacheSchedule, DateTimeFormatter.ofPattern("HH:mm"))
                                    .format(DateTimeFormatter.ofPattern("hh:mm a")));

                    //todo: set onclick event on item
                    holder.binding.getRoot().setOnClickListener(view -> {
                        if(position == NO_POSITION) {
                            return;
                        }

                        mListener.onClick(patrolRoute, position);
                    });

                }else {

                    holder.binding.nextsched
                            .setText("N/A");
                }

            }
        }
    }

    @Override
    public int getItemCount() {
        return patrolRouteList.size();
    }

    public static class ItineraryViewHolder extends RecyclerView.ViewHolder {

        public ListItemPatrolRouteBinding binding;

        public ItineraryViewHolder(ListItemPatrolRouteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
