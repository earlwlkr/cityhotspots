package me.earlwlkr.cityhotspots.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import me.earlwlkr.cityhotspots.R;
import me.earlwlkr.cityhotspots.models.Diner;

/**
 * Created by earl on 6/6/2015.
 */
public class DinerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    Context mContext;
    List<Diner> mDiners;

    public DinerInfoWindowAdapter(Context context, List<Diner> diners) {
        mContext = context;
        mDiners = diners;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        // Getting view from the layout file info_window_layout
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_info_window, null);

        // Getting the position from the marker
        LatLng latLng = marker.getPosition();

        // Getting reference to the TextView to set latitude
        TextView txtName = (TextView) v.findViewById(R.id.txt_name);

        // Getting reference to the TextView to set longitude
        TextView txtAddress = (TextView) v.findViewById(R.id.txt_address);

        TextView txtCuisine = (TextView) v.findViewById(R.id.txt_cuisine);
        TextView txtPriceRange = (TextView) v.findViewById(R.id.txt_price_range);
        TextView txtOpenTime = (TextView) v.findViewById(R.id.txt_open_time);
        RatingBar dinerRating = (RatingBar) v.findViewById(R.id.diner_rating);

        for (Diner diner: mDiners) {
            LatLng dinerPos = diner.getPosition();
            if (dinerPos != null && Math.abs(dinerPos.latitude - latLng.latitude) < 0.000001 &&
                    Math.abs(dinerPos.longitude - latLng.longitude) < 0.000001) {

                txtName.setText(diner.getName());
                try {
                    float rating = Float.parseFloat(diner.getRating()) / 2.0f;
                    dinerRating.setRating(rating);
                } catch (NumberFormatException e) {
                    dinerRating.setVisibility(View.INVISIBLE);
                }
                txtAddress.setText(diner.getAddress().getAddressString());
                txtCuisine.setText(diner.getCuisine());
                txtPriceRange.setText(diner.getPriceMin() + "đ - " + diner.getPriceMax() + "đ");
                txtOpenTime.setText(diner.getOpenTime().getHours() + "h - " + diner.getCloseTime().getHours() + "h");
            }
        }

        // Returning the view containing InfoWindow contents
        return v;
    }
}
