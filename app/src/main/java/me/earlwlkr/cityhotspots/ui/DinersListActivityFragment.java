package me.earlwlkr.cityhotspots.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.earlwlkr.cityhotspots.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class DinersListActivityFragment extends Fragment {

    public DinersListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diners_list, container, false);
    }
}
