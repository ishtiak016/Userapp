package com.customer.masters.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.customer.masters.models.MerchantModel;

import java.util.ArrayList;
import java.util.List;



public class MerchantByCatResponseJson {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("merchantbykategori")
    @Expose
    private List<MerchantModel> data = new ArrayList<>();

    public MerchantByCatResponseJson() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MerchantModel> getData() {
        return data;
    }

    public void setData(List<MerchantModel> data) {
        this.data = data;
    }
}
