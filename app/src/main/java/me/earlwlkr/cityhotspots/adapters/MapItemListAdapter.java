package me.earlwlkr.cityhotspots.adapters;

import me.earlwlkr.cityhotspots.R;
import me.earlwlkr.cityhotspots.models.Diner;
import me.earlwlkr.cityhotspots.models.Place;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by earl on 5/18/2015.
 */
public class MapItemListAdapter extends RecyclerView.Adapter<MapItemListAdapter.ViewHolder> {
    private List<? extends Place> mItems;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private TextView mName;
        private TextView mAddress;
        private Place mItem;

        public ViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.name);
            mAddress = (TextView) v.findViewById(R.id.address);
            v.setOnClickListener(this);
        }

        public void setPlace(Place place) {
            mItem = place;
            mName.setText(place.getName());
            mAddress.setText(place.getAddress().getStreetAddress());
        }

        @Override
        public void onClick(View v) {
            System.out.println("You clicked on diner " + mItem.getName());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MapItemListAdapter(List<? extends Place> places) {
        mItems = places;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MapItemListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_diner_row, parent, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.setPlace(mItems.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
