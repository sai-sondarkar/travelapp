package edu.itm.natravelapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TravelModel {

    @SerializedName("fare")
    private int fare;

    @SerializedName("flightList")
    private List<FlightsModel>  flightList ;

    public TravelModel(int fare, List<FlightsModel> flightList) {
        this.fare = fare;
        this.flightList = flightList;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public List<FlightsModel> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<FlightsModel> flightList) {
        this.flightList = flightList;
    }
}
