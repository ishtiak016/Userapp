package com.customer.masters.json.fcm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FCMMessage {

    @Expose
    @SerializedName("to")
    private String to;

    @Expose
    @SerializedName("data")
    private Object data;

    public FCMMessage() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
