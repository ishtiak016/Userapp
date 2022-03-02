package com.customer.masters.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RateRequestJson {
    @Expose
    @SerializedName("id_driver")
    public String id_driver;

    @Expose
    @SerializedName("id_pelanggan")
    public String id_pelanggan;

    @Expose
    @SerializedName("id_transaksi")
    public String id_transaksi;

    @Expose
    @SerializedName("rating")
    public String rating;

    @Expose
    @SerializedName("catatan")
    public String catatan;

    public RateRequestJson() {
    }
}
