package altran.brastlewark.app.ui.detail;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import altran.brastlewark.app.ui.view.ChipView;

/**
 * Created by nicolas on 10/8/17.
 */

public class ChipAdapter extends RecyclerView.Adapter<ChipAdapter.ItemViewHolder> {

    private List<String> stringList;

    public ChipAdapter(List<String> stringList) {
        super();
        this.stringList = stringList;
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public String getItemAt(int position) {
        return stringList.get(position);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(new ChipView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.populateData(getItemAt(position));
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {


        ItemViewHolder(ChipView itemView) {
            super(itemView);
        }

        void populateData(String text) {
            ((ChipView) itemView).setText(text);
        }

    }

}
