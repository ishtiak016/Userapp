package com.customer.masters.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.customer.masters.models.LokasiDriverModel;


import java.util.ArrayList;
import java.util.List;



public class LokasiDriverResponse {

    @SerializedName("data")
    @Expose
    private final List<LokasiDriverModel> data = new ArrayList<>();

    public LokasiDriverResponse() {
    }

    public List<LokasiDriverModel> getData() {
        return data;
    }

}
