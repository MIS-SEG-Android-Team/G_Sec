package org.rmj.guanzongroup.gsecurity.ui.components.adapter;

import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.ListItemPatrolRouteBinding;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;
import org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.patrolroute.PatrolCheckpoint;
import org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.patrolroute.VMPatrolRoute;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import timber.log.Timber;

public class AdapterPatrolRoute extends RecyclerView.Adapter<AdapterPatrolRoute.ItineraryViewHolder> {

    private final List<PatrolCheckpoint> patrolRouteList;
    private final PatrolRouteClickListener mListener;
    private final String patrolCacheSchedule;
    private final String patrolCacheCheckpoint;
    private final VMPatrolRoute mViewModel;
    private final Boolean hasStarted;
    private final Integer nDuration;

    public interface PatrolRouteClickListener{
        void onClick(PatrolCheckpoint patrol, int position);
    }

    public AdapterPatrolRoute(List<PatrolCheckpoint> patrolRouteList, String patrolCacheSchedule,
                              String patrolCacheCheckpoint, VMPatrolRoute mViewModel, Boolean hasStarted,
                              Integer nDuration, PatrolRouteClickListener listener) {
        this.patrolRouteList = patrolRouteList;
        this.mListener = listener;
        this.patrolCacheSchedule = patrolCacheSchedule;
        this.patrolCacheCheckpoint = patrolCacheCheckpoint;
        this.hasStarted = hasStarted;
        this.nDuration = nDuration;
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

    @SuppressLint("NewApi")
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

                    //todo: get next schedule
                    if (mViewModel.getNextSchedule(patrolCacheSchedule) != null){

                        //todo: display next schedule
                        holder.binding.nextsched
                                .setText(LocalTime.parse(mViewModel.getNextSchedule(patrolCacheSchedule).getdTimexxxx(),
                                                DateTimeFormatter.ofPattern("HH:mm:ss"))
                                        .format(DateTimeFormatter.ofPattern("hh:mm a")));

                    }else {

                        //todo: retain current schedule
                        holder.binding.nextsched
                                .setText(LocalTime.parse(patrolCacheSchedule,
                                                DateTimeFormatter.ofPattern("HH:mm"))
                                        .format(DateTimeFormatter.ofPattern("hh:mm a")));

                    }

                }else {

                    //todo: set by default
                    holder.binding.nextsched.setText("N/A");
                }

            }else {

                //todo: check if last nfc schedule is not empty, set last schedule
                if (!mViewModel.getLastNFCSchedule(patrolRoute.getsNFCIDxxx()).equalsIgnoreCase("N/A")){

                    //todo: display last cached schedule
                    holder.binding.nextsched.setText(mViewModel.getLastNFCSchedule(patrolRoute.getsNFCIDxxx()));

                }

            }

            //todo: check item's display
            if (!holder.binding.nextsched.getText().toString().isEmpty()){

                if (!holder.binding.nextsched.getText().toString().equalsIgnoreCase("N/A")){

                    boolean isClicked = false;

                    //todo: set onclick event
                    holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("NewApi")
                        @Override
                        public void onClick(View v) {

                            Timber.tag("AdapterPatrolRoute").d(patrolCacheCheckpoint);
                            Timber.tag("AdapterPatrolRoute").d(patrolCacheSchedule);

                            if(position == NO_POSITION) {
                                return;
                            }

                            //TODO: FORMAT TIME AND DATE
                            LocalTime schedFormat = LocalTime.parse(patrolCacheSchedule, DateTimeFormatter.ofPattern("HH:mm"));
                            LocalTime displayFormat = LocalTime.parse(holder.binding.nextsched.getText().toString(),
                                    DateTimeFormatter.ofPattern("hh:mm a"));
                            String currentDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());

                            //todo: check current schedule on selected item's displayed schedule
                            if (schedFormat.equals(LocalTime.parse(displayFormat.format(DateTimeFormatter.ofPattern("HH:mm"))))){

                                //todo: check if schedule is visited, return if visited
                                if (mViewModel.isPatrolVisited(currentDateFormat +" "+ displayFormat.format(DateTimeFormatter.ofPattern("HH:mm:ss"))) > 0){

                                    new DialogResult(holder.itemView.getContext(), DialogResult.RESULT.FAILED, "You already tagged this checkpoint as visited", Dialog::dismiss).showDialog();
                                    return;
                                }

                                //todo: check current time if before patrol schedule, return not started
                                if (!hasStarted){
                                    new DialogResult(holder.itemView.getContext(), DialogResult.RESULT.FAILED, "You haven't started patrol yet", Dialog::dismiss).showDialog();
                                    return;
                                }

                                //todo: check schedule minute range to current time,
                                long duration = Duration.between(LocalTime.parse(patrolCacheSchedule),
                                        LocalTime.parse(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))).toMinutes();

                                //todo: notify user if assigned patrol duration, has exceeded
                                if (duration > nDuration){
                                    new DialogResult(holder.itemView.getContext(), DialogResult.RESULT.FAILED, "You have exceeded the duration of patrol", Dialog::dismiss).showDialog();
                                    return;
                                }

                                //todo:enable tagging
                                mListener.onClick(patrolRoute, position);

                            }else {
                                new DialogResult(holder.itemView.getContext(), DialogResult.RESULT.FAILED, "Patrol schedule not in this checkpoint", Dialog::dismiss).showDialog();
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
