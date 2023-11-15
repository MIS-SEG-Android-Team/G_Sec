package org.rmj.guanzongroup.gsecurity.ui.components.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class RecyclerViewHolder_Itineraries extends RecyclerView.ViewHolder {
    public MaterialButton btn_timesched;
    public MaterialTextView mtv_time;
    public MaterialTextView mtv_place;
    public MaterialTextView mtv_timevisit;

    public RecyclerViewHolder_Itineraries(@NonNull View itemView) {
        super(itemView);

//        btn_timesched = itemView.findViewById(R.id.btn_timesched);
//        mtv_time = itemView.findViewById(R.id.mtv_time);
//        mtv_place = itemView.findViewById(R.id.mtv_place);
//        mtv_timevisit = itemView.findViewById(R.id.mtv_timevisit);
    }
}
