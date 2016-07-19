package com.actiknow.motoraudit.model;

import android.util.Log;

import com.actiknow.motoraudit.utils.Utils;

public class FuelGovernor {
    private int fuel_governor_id, selection_flag; // selection flag : 0=> PASS, 1=>ADVISE, 2=>FAIL, 3=>N/A
    private String heading, sub_heading, image_str, comment, selection_text;

    public FuelGovernor () {
    }

    public FuelGovernor (int fuel_governor_id, int selection_flag, String heading,
                         String sub_heading, String image_str, String comment,
                         String selection_text) {
        this.fuel_governor_id = fuel_governor_id;
        this.selection_flag = selection_flag;
        this.heading = heading;
        this.sub_heading = sub_heading;
        this.image_str = image_str;
        this.comment = comment;
        this.selection_text = selection_text;
    }

    public int getFuel_governor_id () {
        return fuel_governor_id;
    }

    public void setFuel_governor_id (int fuel_governor_id) {
        this.fuel_governor_id = fuel_governor_id;
        Utils.showLog (Log.DEBUG, "fuel_governor_id", "" + fuel_governor_id, false);
    }

    public int getSelection_flag () {
        return selection_flag;
    }

    public void setSelection_flag (int selection_flag) {
        this.selection_flag = selection_flag;
        Utils.showLog (Log.DEBUG, "selection_flag", "" + selection_flag, false);
    }

    public String getHeading () {
        return heading;
    }

    public void setHeading (String heading) {
        this.heading = heading;
        Utils.showLog (Log.DEBUG, "heading", heading, false);
    }

    public String getSub_heading () {
        return sub_heading;
    }

    public void setSub_heading (String sub_heading) {
        this.sub_heading = sub_heading;
        Utils.showLog (Log.DEBUG, "sub_heading", sub_heading, false);
    }

    public String getImage_str () {
        return image_str;
    }

    public void setImage_str (String image_str) {
        this.image_str = image_str;
        Utils.showLog (Log.DEBUG, "image_str", image_str, false);
    }

    public String getComment () {
        return comment;
    }

    public void setComment (String comment) {
        this.comment = comment;
        Utils.showLog (Log.DEBUG, "comment", comment, false);
    }

    public String getSelection_text () {
        return selection_text;
    }

    public void setSelection_text (String selection_text) {
        this.selection_text = selection_text;
        Utils.showLog (Log.DEBUG, "selection_text", selection_text, false);
    }
}
