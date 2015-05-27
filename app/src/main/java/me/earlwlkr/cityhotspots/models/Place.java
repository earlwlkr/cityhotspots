package me.earlwlkr.cityhotspots.models;

/**
 * Created by earl on 5/27/2015.
 */
public class Place {
    private String name;
    private Address address;

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
}
