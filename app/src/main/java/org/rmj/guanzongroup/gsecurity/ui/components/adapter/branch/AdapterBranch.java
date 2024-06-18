package org.rmj.guanzongroup.gsecurity.ui.components.adapter.branch;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.data.room.branch.BranchDao;
import org.rmj.guanzongroup.gsecurity.databinding.ListItemBranchBinding;

import java.util.ArrayList;
import java.util.List;

public class AdapterBranch extends RecyclerView.Adapter<AdapterBranch.BranchViewHolder> {

    private final List<BranchDao.BranchNameCode> branchList;
    private List<BranchDao.BranchNameCode> filteredList;

    private final AdapterBranchCallback callback;
    private final BranchFilter branchFilter;

    public AdapterBranch(List<BranchDao.BranchNameCode> branchList, AdapterBranchCallback callback) {
        this.branchList = branchList;
        this.filteredList = branchList;
        this.callback = callback;
        this.branchFilter = new BranchFilter(this);
    }

    public BranchFilter getFilter() {
        return branchFilter;
    }

    @NonNull
    @Override
    public BranchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BranchViewHolder(
                ListItemBranchBinding.inflate(
                        LayoutInflater.from(
                                parent.getContext()
                        ), parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BranchViewHolder holder, int position) {
        BranchDao.BranchNameCode branch = filteredList.get(position);
        holder.binding.branchNameLabel.setText(branch.sBranchNm);
        holder.binding.branchCodeLabel.setText(branch.sBranchCd);

        holder.binding.getRoot().setOnClickListener(view -> {
            callback.onClickBranch(branch.sBranchCd, branch.sBranchNm);
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public static class BranchViewHolder extends RecyclerView.ViewHolder {

        public ListItemBranchBinding binding;

        public BranchViewHolder(ListItemBranchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class BranchFilter extends Filter {


        private final AdapterBranch adapterBranch;

        public BranchFilter(AdapterBranch adapterBranch) {
            super();
            this.adapterBranch = adapterBranch;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final FilterResults filterResults = new FilterResults();

            if (constraint.length() == 0) {
                filteredList = branchList;
            } else {

                List<BranchDao.BranchNameCode> filterSearch = new ArrayList<>();
                for (BranchDao.BranchNameCode branch: branchList) {
                    String branchName = branch.sBranchNm;
                    if (branchName.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterSearch.add(branch);
                    }
                }
                filteredList = filterSearch;
            }

            filterResults.values = filteredList;
            filterResults.count = filteredList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapterBranch.filteredList = (List<BranchDao.BranchNameCode>) results.values;
            this.adapterBranch.notifyDataSetChanged();
        }
    }
}
