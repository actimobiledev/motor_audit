package com.actiknow.motoraudit.model;

import android.util.Log;

import com.actiknow.motoraudit.utils.Utils;

public class ScheduledMaintenance {
    private int scheduled_maintenance_id, selection_flag; // selection flag : 0=> N/A, 1=>NO, 2=>YES
    private String heading, sub_heading, image_str, comment, selection_text;

    public ScheduledMaintenance () {
    }

    public ScheduledMaintenance (int scheduled_maintenance_id, int selection_flag, String heading,
                                 String sub_heading, String image_str, String comment,
                                 String selection_text) {
        this.scheduled_maintenance_id = scheduled_maintenance_id;
        this.selection_flag = selection_flag;
        this.heading = heading;
        this.sub_heading = sub_heading;
        this.image_str = image_str;
        this.comment = comment;
        this.selection_text = selection_text;
    }

    public int getScheduled_maintenance_id () {
        return scheduled_maintenance_id;
    }

    public void setScheduled_maintenance_id(int scheduled_maintenance_id) {
        this.scheduled_maintenance_id = scheduled_maintenance_id;
        Utils.showLog (Log.DEBUG, "scheduled_maintenance_id", "" + scheduled_maintenance_id, false);
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
