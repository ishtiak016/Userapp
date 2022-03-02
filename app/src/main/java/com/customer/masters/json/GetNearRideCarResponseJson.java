package com.customer.masters.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.customer.masters.models.DriverModel;

import java.util.ArrayList;



public class GetNearRideCarResponseJson {

    @Expose
    @SerializedName("data")
    private ArrayList<DriverModel> data = new ArrayList<>();

    public GetNearRideCarResponseJson() {
    }

    public ArrayList<DriverModel> getData() {
        return data;
    }

    public void setData(ArrayList<DriverModel> data) {
        this.data = data;
    }
}
