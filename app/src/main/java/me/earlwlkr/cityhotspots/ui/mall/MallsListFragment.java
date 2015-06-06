package me.earlwlkr.cityhotspots.ui.mall;

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
import me.earlwlkr.cityhotspots.models.Place;

/**
 * A placeholder fragment containing a simple view.
 */
public class MallsListFragment extends Fragment {

    public static MallsListFragment createInstance(List<Place> malls) {
        MallsListFragment fragment = new MallsListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("malls", Parcels.wrap(malls));
        fragment.setArguments(bundle);
        return fragment;
    }

    public MallsListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results_list, container, false);
        List<Place> malls = Parcels.unwrap(getArguments().getParcelable("malls"));
        System.out.println("Size: " + malls.size());

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.results_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        MapItemListAdapter mAdapter = new MapItemListAdapter(malls);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }
}
