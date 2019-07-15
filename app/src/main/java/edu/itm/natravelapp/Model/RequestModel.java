package edu.itm.natravelapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestModel {

    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("distination")
    @Expose
    private String distination;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("adult")
    @Expose
    private String adult;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDistination() {
        return distination;
    }

    public void setDistination(String distination) {
        this.distination = distination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    @Override
    public String toString() {
        return  "Post{" +
                "source='" + source + '\'' +
                ", distination='" + distination + '\'' +
                ", date=" + date +
                ", adult=" + adult +
                '}';
    }

}
