package com.actiknow.motoraudit.model;

import android.util.Log;

import com.actiknow.motoraudit.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class WorkOrderDetail {

    private int work_order_id, generator_make_id, ats_make_id, generator_condition_flag; // Generator condition flag  0=> POOR, 1=> FAIR, 2=> GOOD
    private String time_in, onsite_contact, email, before_image_str, generator_model,
            generator_make_name, generator_serial, engine_model, kw_rating, engine_serial,
            ats_make_name, ats_model, service_check_json, generator_condition_text, generator_condition_comment,
            comments, after_image_str, tech_image_str, customer_signature, time_out;

    private List<ServiceCheck> serviceCheckList = new ArrayList<ServiceCheck> ();


    public WorkOrderDetail () {
    }

    public WorkOrderDetail (int work_order_id, int generator_make_id, int generator_condition_flag,
                            int ats_make_id, String onsite_contact, String time_in, String before_image_str,
                            String email, String generator_make_name, String generator_model,
                            String kw_rating, String engine_model, String generator_serial,
                            String engine_serial, String ats_make_name, String ats_model, String service_check_json,
                            List<ServiceCheck> serviceCheckList, String generator_condition_text,
                            String generator_condition_comment, String comments, String after_image_str,
                            String tech_image_str, String customer_signature, String time_out) {
        this.work_order_id = work_order_id;
        this.generator_make_id = generator_make_id;
        this.generator_condition_flag = generator_condition_flag;
        this.ats_make_id = ats_make_id;
        this.onsite_contact = onsite_contact;
        this.time_in = time_in;
        this.before_image_str = before_image_str;
        this.email = email;
        this.generator_make_name = generator_make_name;
        this.generator_model = generator_model;
        this.kw_rating = kw_rating;
        this.engine_model = engine_model;
        this.generator_serial = generator_serial;
        this.engine_serial = engine_serial;
        this.ats_make_name = ats_make_name;
        this.ats_model = ats_model;
        this.service_check_json = service_check_json;
        this.serviceCheckList = serviceCheckList;
        this.generator_condition_text = generator_condition_text;
        this.generator_condition_comment = generator_condition_comment;
        this.comments = comments;
        this.after_image_str = after_image_str;
        this.tech_image_str = tech_image_str;
        this.customer_signature = customer_signature;
        this.time_out = time_out;
    }

    public int getWork_order_id () {
        return work_order_id;
    }

    public void setWork_order_id (int work_order_id) {
        this.work_order_id = work_order_id;
        Utils.showLog (Log.DEBUG, "work_order_id", "" + work_order_id, false);
    }

    public int getGenerator_make_id () {
        return generator_make_id;
    }

    public void setGenerator_make_id (int generator_make_id) {
        this.generator_make_id = generator_make_id;
        Utils.showLog (Log.DEBUG, "generator_make_id", "" + generator_make_id, false);
    }

    public int getGenerator_condition_flag () {
        return generator_condition_flag;
    }

    public void setGenerator_condition_flag (int generator_condition_flag) {
        this.generator_condition_flag = generator_condition_flag;
        Utils.showLog (Log.DEBUG, "generator_condition_flag", "" + generator_condition_flag, false);
    }

    public int getAts_make_id () {
        return ats_make_id;
    }

    public void setAts_make_id (int ats_make_id) {
        this.ats_make_id = ats_make_id;
        Utils.showLog (Log.DEBUG, "ats_make_id", "" + ats_make_id, false);
    }

    public String getTime_in () {
        return time_in;
    }

    public void setTime_in (String time_in) {
        this.time_in = time_in;
        Utils.showLog (Log.DEBUG, "time_in", time_in, false);
    }

    public String getOnsite_contact () {
        return onsite_contact;
    }

    public void setOnsite_contact (String onsite_contact) {
        this.onsite_contact = onsite_contact;
        Utils.showLog (Log.DEBUG, "onsite_contact", onsite_contact, false);
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
        Utils.showLog (Log.DEBUG, "email", email, false);
    }

    public String getBefore_image_str () {
        return before_image_str;
    }

    public void setBefore_image_str (String before_image_str) {
        this.before_image_str = before_image_str;
        Utils.showLog (Log.DEBUG, "before_image_str", before_image_str, false);
    }

    public String getGenerator_model () {
        return generator_model;
    }

    public void setGenerator_model (String generator_model) {
        this.generator_model = generator_model;
        Utils.showLog (Log.DEBUG, "generator_model", generator_model, false);
    }

    public String getGenerator_make_name () {
        return generator_make_name;
    }

    public void setGenerator_make_name (String generator_make_name) {
        this.generator_make_name = generator_make_name;
        Utils.showLog (Log.DEBUG, "generator_make_name", generator_make_name, false);
    }

    public String getGenerator_serial () {
        return generator_serial;
    }

    public void setGenerator_serial (String generator_serial) {
        this.generator_serial = generator_serial;
        Utils.showLog (Log.DEBUG, "generator_serial", generator_serial, false);
    }

    public String getEngine_model () {
        return engine_model;
    }

    public void setEngine_model (String engine_model) {
        this.engine_model = engine_model;
        Utils.showLog (Log.DEBUG, "engine_model", engine_model, false);
    }

    public String getKw_rating () {
        return kw_rating;
    }

    public void setKw_rating (String kw_rating) {
        this.kw_rating = kw_rating;
        Utils.showLog (Log.DEBUG, "kw_rating", kw_rating, false);
    }

    public String getEngine_serial () {
        return engine_serial;
    }

    public void setEngine_serial (String engine_serial) {
        this.engine_serial = engine_serial;
        Utils.showLog (Log.DEBUG, "engine_serial", engine_serial, false);
    }

    public String getAts_make_name () {
        return ats_make_name;
    }

    public void setAts_make_name (String ats_make_name) {
        this.ats_make_name = ats_make_name;
        Utils.showLog (Log.DEBUG, "ats_make_name", ats_make_name, false);
    }

    public String getAts_model () {
        return ats_model;
    }

    public void setAts_model (String ats_model) {
        this.ats_model = ats_model;
        Utils.showLog (Log.DEBUG, "ats_model", ats_model, false);
    }

    public String getService_check_json () {
        return service_check_json;
    }

    public void setService_check_json (String service_check_json) {
        this.service_check_json = service_check_json;
        Utils.showLog (Log.DEBUG, "service_check_json", service_check_json, false);
    }

    public List<ServiceCheck> getServiceCheckList () {
        return serviceCheckList;
    }

    public void setServiceCheckItem (ServiceCheck serviceCheck) {
        this.serviceCheckList.add (serviceCheck);
        Utils.showLog (Log.DEBUG, "SERVICE CHECK", "ADDED A NEW SERVICE CHECK IN SERVICE CHECK LIST", false);
    }

    public void setServiceCheckList (List<ServiceCheck> serviceCheckList) {
        this.serviceCheckList = serviceCheckList;
        Utils.showLog (Log.DEBUG, "SERVICE CHECK", "SET SERVICE CHECK LIST", false);
    }

    public String getGenerator_condition_text () {
        return generator_condition_text;
    }

    public void setGenerator_condition_text (String generator_condition_text) {
        this.generator_condition_text = generator_condition_text;
        Utils.showLog (Log.DEBUG, "generator_condition_text", generator_condition_text, false);
    }

    public String getGenerator_condition_comment () {
        return generator_condition_comment;
    }

    public void setGenerator_condition_comment (String generator_condition_comment) {
        this.generator_condition_comment = generator_condition_comment;
        Utils.showLog (Log.DEBUG, "generator_condition_comment", generator_condition_comment, false);
    }

    public String getComments () {
        return comments;
    }

    public void setComments (String comments) {
        this.comments = comments;
        Utils.showLog (Log.DEBUG, "comments", comments, false);
    }

    public String getAfter_image_str () {
        return after_image_str;
    }

    public void setAfter_image_str (String after_image_str) {
        this.after_image_str = after_image_str;
        Utils.showLog (Log.DEBUG, "after_image_str", after_image_str, false);
    }

    public String getTech_image_str () {
        return tech_image_str;
    }

    public void setTech_image_str (String tech_image_str) {
        this.tech_image_str = tech_image_str;
        Utils.showLog (Log.DEBUG, "tech_image_str", tech_image_str, false);
    }

    public String getCustomer_signature () {
        return customer_signature;
    }

    public void setCustomer_signature (String customer_signature) {
        this.customer_signature = customer_signature;
        Utils.showLog (Log.DEBUG, "customer_signature", customer_signature, false);
    }

    public String getTime_out () {
        return time_out;
    }

    public void setTime_out (String time_out) {
        this.time_out = time_out;
        Utils.showLog (Log.DEBUG, "time_out", time_out, false);
    }
}
