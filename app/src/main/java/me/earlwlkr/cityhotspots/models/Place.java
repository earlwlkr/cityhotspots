package me.earlwlkr.cityhotspots.models;

import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcel;

/**
 * Created by earl on 5/27/2015.
 */
@Parcel
public class Place {
    private String name;
    private Address address;
    private LatLng position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }
}
