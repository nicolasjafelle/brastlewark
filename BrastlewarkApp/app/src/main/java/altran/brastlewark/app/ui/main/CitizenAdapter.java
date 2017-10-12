package altran.brastlewark.app.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import altran.brastlewark.app.domain.Citizen;
import altran.brastlewark.app.ui.view.CitizenItemView;

/**
 * Created by nicolas on 10/8/17.
 */

public class CitizenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerOnItemClickListener listener;

    private List<Citizen> citizenList;

    public CitizenAdapter(RecyclerOnItemClickListener listener) {
        super();
        this.listener = listener;
        citizenList = new ArrayList<>(0);
    }

    public void addList(List<Citizen> citizenList) {
        clearList();
        this.citizenList.addAll(citizenList);
        notifyDataSetChanged();
    }

    public void clearList() {
        this.citizenList.clear();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(new CitizenItemView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).populateData(getItemAt(position));
        }

    }

    public Citizen getItemAt(int position) {
        return (citizenList != null && !citizenList.isEmpty()) ? citizenList.get(position) : null;
    }

    @Override
    public int getItemCount() {
        return citizenList.size();
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ItemViewHolder(CitizenItemView itemView) {
            super(itemView);
        }

        public void populateData(Citizen citizen) {
            itemView.setOnClickListener(this);
            ((CitizenItemView) itemView).loadData(citizen);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClickListener(getAdapterPosition());
            }
        }
    }

}
