package org.rmj.guanzongroup.gsecurity.ui.components.adapter;

import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;

import android.app.Dialog;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.ListItemPatrolRouteBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;
import org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.patrolroute.PatrolCheckpoint;
import org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.patrolroute.VMPatrolRoute;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import timber.log.Timber;

public class AdapterPatrolRoute extends RecyclerView.Adapter<AdapterPatrolRoute.ItineraryViewHolder> {

    private final List<PatrolCheckpoint> patrolRouteList;
    private final PatrolRouteClickListener mListener;
    private final String patrolCacheSchedule;
    private final String patrolCacheCheckpoint;
    private final Boolean patrolStarted;
    private final VMPatrolRoute mViewModel;

    public interface PatrolRouteClickListener{
        void onClick(PatrolCheckpoint patrol, int position);
    }

    public AdapterPatrolRoute(List<PatrolCheckpoint> patrolRouteList, String patrolCacheSchedule,
                              String patrolCacheCheckpoint, Boolean patrolStarted, VMPatrolRoute mViewModel,
                              PatrolRouteClickListener listener) {
        this.patrolRouteList = patrolRouteList;
        this.mListener = listener;
        this.patrolCacheSchedule = patrolCacheSchedule;
        this.patrolCacheCheckpoint = patrolCacheCheckpoint;
        this.patrolStarted = patrolStarted;
        this.mViewModel = mViewModel;
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

        holder.binding.nfcSiteDescription.setText(patrolRoute.getsDescript()); //set patrol description

        //todo: check if patrol is visited
        if(patrolRoute.isVisited())
            holder.binding.patrolRouteIcon.setImageResource(R.drawable.ic_location_check);
        else
            holder.binding.patrolRouteIcon.setImageResource(R.drawable.ic_location_next);

        if (!patrolCacheCheckpoint.isEmpty()){

            //todo: check if current checkpoint matched the item, then display next schedule to the current item
            if (patrolRoute.getsNFCIDxxx().equalsIgnoreCase(patrolCacheCheckpoint)){

                //todo: check if current schedule is not empty
                if (!patrolCacheSchedule.isEmpty()){

                    if (mViewModel.getNextSchedule(patrolCacheSchedule) != null){

                        //todo: display schedule
                        holder.binding.nextsched
                                .setText(LocalTime.parse(mViewModel.getNextSchedule(patrolCacheSchedule).getdTimexxxx(),
                                                DateTimeFormatter.ofPattern("HH:mm:ss"))
                                        .format(DateTimeFormatter.ofPattern("hh:mm a")));

                    }else {

                        //todo: display schedule
                        holder.binding.nextsched
                                .setText(LocalTime.parse(patrolCacheSchedule,
                                                DateTimeFormatter.ofPattern("HH:mm:ss"))
                                        .format(DateTimeFormatter.ofPattern("hh:mm a")));

                    }

                    holder.binding.getRoot().setOnClickListener(view -> {

                        Timber.tag("AdapterPatrolRoute").d(patrolCacheCheckpoint);
                        Timber.tag("AdapterPatrolRoute").d(patrolCacheSchedule);
                        Timber.tag("AdapterPatrolRoute").d(String.valueOf(patrolStarted));

                        if (patrolStarted){
                            new DialogResult(holder.itemView.getContext(), DialogResult.RESULT.FAILED, "You haven't started patrol yet.", Dialog::dismiss).showDialog();
                            return;
                        }

                        //todo:enable tagging
                        mListener.onClick(patrolRoute, position);
                    });

                }else {
                    holder.binding.nextsched.setText("N/A");
                }

            }else {

                //todo: check if last nfc schedule is not empty, set last schedule and set onclick event
                if (!mViewModel.getLastNFCSchedule(patrolRoute.getsNFCIDxxx()).equalsIgnoreCase("N/A")){

                    //todo: display last cached schedule
                    holder.binding.nextsched.setText(mViewModel.getLastNFCSchedule(patrolRoute.getsNFCIDxxx()));

                    holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Timber.tag("AdapterPatrolRoute").d(patrolCacheCheckpoint);
                            Timber.tag("AdapterPatrolRoute").d(patrolCacheSchedule);
                            Timber.tag("AdapterPatrolRoute").d(String.valueOf(patrolStarted));

                            if(position == NO_POSITION) {
                                return;
                            }

                            //todo: enable tagging if patrol is not started
                            if (!patrolStarted){

                                //todo:enable tagging
                                mListener.onClick(patrolRoute, position);

                            }else {
                                new DialogResult(holder.itemView.getContext(), DialogResult.RESULT.FAILED, "Patrol time is finished", Dialog::dismiss).showDialog();
                            }

                        }
                    });

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
