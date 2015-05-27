package me.earlwlkr.cityhotspots.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.List;

import me.earlwlkr.cityhotspots.R;
import me.earlwlkr.cityhotspots.adapters.MapItemListAdapter;
import me.earlwlkr.cityhotspots.models.Cinema;

/**
 * A placeholder fragment containing a simple view.
 */
public class CinemasListFragment extends Fragment {

    public static CinemasListFragment createInstance(List<Cinema> cinemas) {
        CinemasListFragment fragment = new CinemasListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("cinemas", Parcels.wrap(cinemas));
        fragment.setArguments(bundle);
        return fragment;
    }

    public CinemasListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cinemas_list, container, false);
        List<Cinema> cinemas = Parcels.unwrap(getArguments().getParcelable("cinemas"));
        System.out.println("Size: " + cinemas.size());

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.cinemas_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        MapItemListAdapter mAdapter = new MapItemListAdapter(cinemas);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
}
