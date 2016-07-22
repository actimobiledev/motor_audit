package com.actiknow.motoraudit.model;

import android.util.Log;

import com.actiknow.motoraudit.utils.Utils;

public class Manufacturer {
    private int manufacturer_id;
    private String manufacturer_name;

    public Manufacturer () {
    }

    public Manufacturer (int manufacturer_id, String manufacturer_name) {
        this.manufacturer_id = manufacturer_id;
        this.manufacturer_name = manufacturer_name;
    }

    public int getManufacturer_id() {
        return manufacturer_id;
    }

    public void setManufacturer_id (int manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
        Utils.showLog (Log.DEBUG, "manufacturer_id", "" + manufacturer_id, false);
    }

    public String getManufacturer_name () {
        return manufacturer_name;
    }

    public void setManufacturer_name (String manufacturer_name) {
        this.manufacturer_name = manufacturer_name;
        Utils.showLog (Log.DEBUG, "manufacturer_name", manufacturer_name, false);
    }
}
