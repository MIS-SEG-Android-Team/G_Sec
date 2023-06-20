package org.rmj.guanzongroup.gsecurity.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.utils.ImageFileManager;
import org.rmj.guanzongroup.gsecurity.R;

import java.util.List;

public class AdapterOfficersList extends  RecyclerView.Adapter<AdapterOfficersList.VHOfficerList> {
//    private final List<AdapterOfficersList> poList;
//    private final OnItemClickListener mListener;
    public interface OnItemClickListener{
        void OnClick(String args);
    }
//    public AdapterOfficersList(List<AdapterOfficersList> list, OnItemClickListener listener) {
//        this.poList = list;
//        this.mListener = listener;
//    }
    private String[] data;

    public AdapterOfficersList(String[] data) {
        this.data = data;
    }
    @NonNull

    @Override
    public VHOfficerList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_adapter_officer, parent, false);
        return new VHOfficerList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHOfficerList holder, int position) {
        String item = data[position];
        holder.lblTitle.setText(item);
//        AdapterOfficersList loInfo =poList.get(position);
//        String lsImgLink = loInfo.getsImageLnk();
//        holder.lblTitle.setText(loInfo.getsTitlexxx());
//        holder.lblMessage.setText(loInfo.getsContentx());
//        holder.lblDate.setText(loInfo.getsDateTime());
//        ImageFileManager.LoadImageToView(lsImgLink, holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class VHOfficerList extends RecyclerView.ViewHolder{

        public ShapeableImageView imageView;

        public MaterialTextView lblTitle, lblMessage, lblDate;

        public VHOfficerList(@NonNull View itemView) {
            super(itemView);
            lblTitle = itemView.findViewById(R.id.lblPersonel);
        }
    }
}
