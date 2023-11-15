package org.rmj.guanzongroup.gsecurity.ui.components.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import org.rmj.guanzongroup.gsecurity.R;

public class RecyclerViewHolder_Facilities extends RecyclerView.ViewHolder {
    public MaterialTextView mtv_facilityname;
    public MaterialTextView mtv_lastvisit;
    public MaterialTextView mtv_timesvisit;

    public RecyclerViewHolder_Facilities(@NonNull View itemView) {
        super(itemView);

        mtv_facilityname = itemView.findViewById(R.id.mtv_facilityname);
        mtv_lastvisit = itemView.findViewById(R.id.mtv_lastvisit);
        mtv_timesvisit = itemView.findViewById(R.id.mtv_timesvisit);
    }
}
