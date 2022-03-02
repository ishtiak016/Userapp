package com.customer.masters.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.customer.masters.models.BeritaModel;

import java.util.ArrayList;
import java.util.List;


public class BeritaDetailResponseJson {

    @Expose
    @SerializedName("data")
    private List<BeritaModel> data = new ArrayList<>();

    public BeritaDetailResponseJson() {
    }

    public List<BeritaModel> getData() {
        return data;
    }

    public void setData(List<BeritaModel> data) {
        this.data = data;
    }
}
