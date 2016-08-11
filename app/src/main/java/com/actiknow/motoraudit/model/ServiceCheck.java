package com.actiknow.motoraudit.model;

import android.util.Log;

import com.actiknow.motoraudit.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ServiceCheck {
    boolean comment_required, pass_required;
    private int group_id;
    // 0=> Default,
    // 1=> Fuel System/Governor
    // 2=> Pre-start Checks Cooling System
    // 3=> Lubrication System
    // 4=> Air System
    // 5=> Exhaust System
    // 6=> Generator
    // 7=> Control Panel/Cabinets
    // 8=> Automatic Transfer Switch (ATS) Main
    // 9=> Starting System
    // 10=> Generator Installation/Enclosure
    // 11=> Start-up and Running Checks
    // 12=> Scheduled Maintenance

    private int service_check_id, selection_flag, group_type;
    // selection flag for PAF-NA : 0=> N/A, 1=>FAIL, 2=>ADVISE, 3=>PASS
    // selection flag for YN-NA : 0=> N/A, 1=>NO, 2=>YES
    // group type : PAF-NA => 0, YN-NA =>1


    private String heading, sub_heading, comment, selection_text, group_name;

    private List<ImageDetail> smCheckImageList = new ArrayList<ImageDetail> ();


    public ServiceCheck () {
    }

    public ServiceCheck (boolean comment_required, boolean pass_required, int group_id, int service_check_id,
                         int selection_flag, int group_type, String heading, String sub_heading, String comment,
                         String selection_text, String group_name, List<ImageDetail> smCheckImageList) {
        this.comment_required = comment_required;
        this.pass_required = pass_required;
        this.group_id = group_id;
        this.service_check_id = service_check_id;
        this.selection_flag = selection_flag;
        this.group_type = group_type;
        this.heading = heading;
        this.sub_heading = sub_heading;
        this.comment = comment;
        this.selection_text = selection_text;
        this.group_name = group_name;
        this.smCheckImageList = smCheckImageList;
    }


    public boolean isComment_required() {return comment_required;}

    public void setComment_required(boolean comment_required) {
        this.comment_required = comment_required;
        Utils.showLog (Log.INFO, "comment_required", "" + comment_required, false);
    }

    public boolean isPass_required () {
        return pass_required;
    }

    public void setPass_required (boolean pass_required) {
        this.pass_required = pass_required;
        Utils.showLog (Log.INFO, "pass_required", "" + pass_required, false);
    }

    public int getService_check_id () {
        return service_check_id;
    }

    public void setService_check_id (int service_check_id) {
        this.service_check_id = service_check_id;
        Utils.showLog (Log.DEBUG, "service_check_id", "" + service_check_id, false);
    }

    public int getGroup_id () {
        return group_id;
    }

    public void setGroup_id (int group_id) {
        this.group_id = group_id;
        Utils.showLog (Log.DEBUG, "group_id", "" + group_id, false);
    }

    public int getSelection_flag () {
        return selection_flag;
    }

    public void setSelection_flag (int selection_flag) {
        this.selection_flag = selection_flag;
        Utils.showLog (Log.DEBUG, "selection_flag", "" + selection_flag, false);
    }

    public int getGroup_type () {
        return group_type;
    }

    public void setGroup_type (int group_type) {
        this.group_type = group_type;
        Utils.showLog (Log.DEBUG, "group_type", "" + group_type, false);
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

    public String getGroup_name () {
        return group_name;
    }

    public void setGroup_name (String group_name) {
        this.group_name = group_name;
        Utils.showLog (Log.DEBUG, "group_name", group_name, false);
    }

    public List<ImageDetail> getSmCheckImageList () {
        return smCheckImageList;
    }

    public void setSmCheckImageList (List<ImageDetail> smCheckImageList) {
        this.smCheckImageList = smCheckImageList;
    }

    public void setSmCheckImageInList (ImageDetail smCheckImage) {
        this.smCheckImageList.add (smCheckImage);
    }

}
