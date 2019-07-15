package edu.itm.natravelapp.Model;

import com.google.gson.annotations.SerializedName;

public class FlightsModel {

    @SerializedName("arrivalCity")
    String arrivalCity;
    @SerializedName("arrivalTime")
    String arrivalTime;
    @SerializedName("carrierName")
    String carrierName;
    @SerializedName("departCity")
    String departCity;
    @SerializedName("departTime")
    String departTime;
    @SerializedName("flightClass")
    String flightClass;
    @SerializedName("flightNo")
    String flightNo;
    @SerializedName("flightRefundable")
    boolean flightRefundable;
    @SerializedName("key")
    int key;
    @SerializedName("flightDuration")
    int flightDuration;
    @SerializedName("flightLayover")
    int flightLayover;


    public FlightsModel(String arrivalCity, String arrivalTime, String carrierName, String departCity, String departTime, String flightClass, String flightNo, boolean flightRefundable, int key, int flightDuration, int flightLayover) {
        this.arrivalCity = arrivalCity;
        this.arrivalTime = arrivalTime;
        this.carrierName = carrierName;
        this.departCity = departCity;
        this.departTime = departTime;
        this.flightClass = flightClass;
        this.flightNo = flightNo;
        this.flightRefundable = flightRefundable;
        this.key = key;
        this.flightDuration = flightDuration;
        this.flightLayover = flightLayover;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getDepartCity() {
        return departCity;
    }

    public void setDepartCity(String departCity) {
        this.departCity = departCity;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public boolean isFlightRefundable() {
        return flightRefundable;
    }

    public void setFlightRefundable(boolean flightRefundable) {
        this.flightRefundable = flightRefundable;
    }


    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(int flightDuration) {
        this.flightDuration = flightDuration;
    }

    public int getFlightLayover() {
        return flightLayover;
    }

    public void setFlightLayover(int flightLayover) {
        this.flightLayover = flightLayover;
    }
}
