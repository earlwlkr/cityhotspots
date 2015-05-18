package me.earlwlkr.cityhotspots.adapters;

import me.earlwlkr.cityhotspots.R;
import me.earlwlkr.cityhotspots.models.Diner;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by earl on 5/18/2015.
 */
public class DinersListAdapter extends RecyclerView.Adapter<DinersListAdapter.ViewHolder> {
    private List<Diner> mDinersList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;
        public TextView address;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            address = (TextView) v.findViewById(R.id.address);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DinersListAdapter(List<Diner> diners) {
        mDinersList = diners;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DinersListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_diner_row, parent, false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.name.setText(mDinersList.get(position).getName());
        holder.address.setText((mDinersList.get(position).getAddress().getStreetAddress()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDinersList.size();
    }
}
