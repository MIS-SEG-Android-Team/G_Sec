package org.rmj.guanzongroup.gsecurity.ui.components.adapter;

import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.databinding.ListItemPatrolRouteBinding;
import org.rmj.guanzongroup.gsecurity.pojo.itinerary.PatrolRoute;

import java.util.List;

public class AdapterPatrolRoute extends RecyclerView.Adapter<AdapterPatrolRoute.ItineraryViewHolder> {

    private final List<PatrolRoute> patrolRouteList;
    private final PatrolRouteClickListener mListener;

    public interface PatrolRouteClickListener{
        void onClick(String nfcID);
    }

    public AdapterPatrolRoute(List<PatrolRoute> patrolRouteList, PatrolRouteClickListener listener) {
        this.patrolRouteList = patrolRouteList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ItineraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItineraryViewHolder(
                ListItemPatrolRouteBinding.inflate(
                            LayoutInflater.from(
                                    parent.getContext()
                            )
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ItineraryViewHolder holder, int position) {
        PatrolRoute patrolRoute = patrolRouteList.get(position);
        holder.binding.nfcSiteDescription.setText(patrolRoute.getDescription());
        holder.binding.siteWarehouse.setText(patrolRoute.getWarehouse());
        holder.binding.patrolStatus.setText(patrolRoute.getPatrolType());

        holder.binding.getRoot().setOnClickListener(view -> {
            if(position == NO_POSITION) {
                return;
            }

            mListener.onClick(patrolRoute.getNfcID());
        });
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
