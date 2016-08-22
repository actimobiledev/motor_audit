package com.actiknow.motoraudit.model;

import android.util.Log;

import com.actiknow.motoraudit.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class WorkOrderDetail {

    private int form_id, work_order_id, contract_number, generator_make_id, generator_condition_flag, emp_id, generator_serial_id; // Generator condition flag  0=> POOR, 1=> FAIR, 2=> GOOD
    private String customer_name, site_name, time_in, onsite_contact, email, generator_model,
            generator_make_name, generator_serial, kw_rating, engine_serial_json, ats_serial_json, service_check_json,
            generator_condition_text, generator_condition_comment, comments, time_out, before_image_list_json,
            after_image_list_json, signature_image_list_json;

    private List<ServiceCheck> serviceCheckList = new ArrayList<ServiceCheck> ();

    private List<ImageDetail> imageList = new ArrayList<ImageDetail> ();
    private List<Serial> engineSerialList = new ArrayList<Serial> ();
    private List<Serial> atsSerialList = new ArrayList<Serial> ();

    public WorkOrderDetail () {
    }

    public WorkOrderDetail (int form_id, int work_order_id, int contract_number, int generator_make_id, int emp_id,
                            int generator_condition_flag, String customer_name, int generator_serial_id,
                            String site_name, String time_in, String onsite_contact, String email,
                            String generator_model, String generator_make_name, String generator_serial,
                            String kw_rating, String engine_serial_json, String ats_serial_json,
                            String service_check_json, String generator_condition_text,
                            String generator_condition_comment, String comments, String time_out, String before_image_list_json,
                            String after_image_list_json, String signature_image_list_json,
                            List<ServiceCheck> serviceCheckList, List<ImageDetail> imageList, List<Serial> engineSerialList, List<Serial> atsSerialList) {
        this.form_id = form_id;
        this.work_order_id = work_order_id;
        this.contract_number = contract_number;
        this.generator_make_id = generator_make_id;
        this.emp_id = emp_id;
        this.generator_condition_flag = generator_condition_flag;
        this.customer_name = customer_name;
        this.generator_serial_id = generator_serial_id;
        this.site_name = site_name;
        this.time_in = time_in;
        this.onsite_contact = onsite_contact;
        this.email = email;
        this.generator_model = generator_model;
        this.generator_make_name = generator_make_name;
        this.generator_serial = generator_serial;
        this.kw_rating = kw_rating;
        this.engine_serial_json = engine_serial_json;
        this.ats_serial_json = ats_serial_json;
        this.service_check_json = service_check_json;
        this.generator_condition_text = generator_condition_text;
        this.generator_condition_comment = generator_condition_comment;
        this.comments = comments;
        this.time_out = time_out;
        this.before_image_list_json = before_image_list_json;
        this.after_image_list_json = after_image_list_json;
        this.signature_image_list_json = signature_image_list_json;
        this.serviceCheckList = serviceCheckList;
        this.imageList = imageList;
        this.engineSerialList = engineSerialList;
        this.atsSerialList = atsSerialList;
    }


    public String getBefore_image_list_json () {
        return before_image_list_json;
    }

    public void setBefore_image_list_json (String before_image_list_json) {
        this.before_image_list_json = before_image_list_json;
    }

    public String getAfter_image_list_json () {
        return after_image_list_json;
    }

    public void setAfter_image_list_json (String after_image_list_json) {
        this.after_image_list_json = after_image_list_json;
    }

    public String getSignature_image_list_json () {
        return signature_image_list_json;
    }

    public void setSignature_image_list_json (String signature_image_list_json) {
        this.signature_image_list_json = signature_image_list_json;
    }

    public String getEngine_serial_json () {
        return engine_serial_json;
    }

    public void setEngine_serial_json (String engine_serial_json) {
        this.engine_serial_json = engine_serial_json;
    }

    public String getAts_serial_json () {
        return ats_serial_json;
    }

    public void setAts_serial_json (String ats_serial_json) {
        this.ats_serial_json = ats_serial_json;
    }

    public int getForm_id () {
        return form_id;
    }

    public void setForm_id (int form_id) {
        this.form_id = form_id;
        Utils.showLog (Log.DEBUG, "form_id", "" + form_id, false);
    }

    public int getEmp_id () {
        return emp_id;
    }

    public void setEmp_id (int emp_id) {
        this.emp_id = emp_id;
        Utils.showLog (Log.DEBUG, "emp_id", "" + emp_id, false);
    }

    public int getWork_order_id () {
        return work_order_id;
    }

    public void setWork_order_id (int work_order_id) {
        this.work_order_id = work_order_id;
        Utils.showLog (Log.DEBUG, "work_order_id", "" + work_order_id, false);
    }

    public int getContract_number () {
        return contract_number;
    }

    public void setContract_number (int contract_number) {
        this.contract_number = contract_number;
        Utils.showLog (Log.DEBUG, "contract_number", "" + contract_number, false);
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

    public String getCustomer_name () {
        return customer_name;
    }

    public void setCustomer_name (String customer_name) {
        this.customer_name = customer_name;
        Utils.showLog (Log.DEBUG, "customer_name", customer_name, false);
    }

    public int getGenerator_serial_id () {
        return generator_serial_id;
    }

    public void setGenerator_serial_id (int generator_serial_id) {
        this.generator_serial_id = generator_serial_id;
        Utils.showLog (Log.DEBUG, "generator_serial_id", "" + generator_serial_id, false);
    }

    public String getSite_name () {
        return site_name;
    }

    public void setSite_name (String site_name) {
        this.site_name = site_name;
        Utils.showLog (Log.DEBUG, "site_name", site_name, false);
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

    public String getGenerator_model () {
        return generator_model;
    }

    public void setGenerator_model (String generator_model) {
        this.generator_model = generator_model;
        Utils.showLog (Log.DEBUG, "model_number", generator_model, false);
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
        Utils.showLog (Log.DEBUG, "serial_number", generator_serial, false);
    }

    public String getKw_rating () {
        return kw_rating;
    }

    public void setKw_rating (String kw_rating) {
        this.kw_rating = kw_rating;
        Utils.showLog (Log.DEBUG, "kw_rating", kw_rating, false);
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

    public void setServiceCheckList (List<ServiceCheck> serviceCheckList) {
        this.serviceCheckList = serviceCheckList;
        Utils.showLog (Log.DEBUG, "SERVICE CHECK", "SET SERVICE CHECK LIST", false);
    }

    public void setServiceCheckItem (ServiceCheck serviceCheck) {
        this.serviceCheckList.add (serviceCheck);
        Utils.showLog (Log.DEBUG, "SERVICE CHECK", "ADDED A NEW SERVICE CHECK IN SERVICE CHECK LIST", false);
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

    public String getTime_out () {
        return time_out;
    }

    public void setTime_out (String time_out) {
        this.time_out = time_out;
        Utils.showLog (Log.DEBUG, "time_out", time_out, false);
    }

    public List<ImageDetail> getImageList () {
        return imageList;
    }

    public void setImageList (List<ImageDetail> imageList) {
        this.imageList = imageList;
    }

    public void setImageInList (ImageDetail imageDetail) {
        this.imageList.add (imageDetail);
        Utils.showLog (Log.INFO, "image_detail", "added a new imagedetail in list", true);
    }

    public List<Serial> getEngineSerialList () {
        return engineSerialList;
    }

    public void setEngineSerialList (List<Serial> engineSerialList) {
        this.engineSerialList = engineSerialList;
    }

    public void setSerialInEngineList (Serial generatorSerial) {
        this.engineSerialList.add (generatorSerial);
        Utils.showLog (Log.INFO, "engine_serial", "added a new serial in engine list", true);
    }

    public void removeSerialInEngineList (int serial_id) {
        for (int i = 0; i < engineSerialList.size (); i++) {
            Serial engineSerial = engineSerialList.get (i);
            if (serial_id == engineSerial.getSerial_id ()) {
                this.engineSerialList.remove (i);
            }
        }
        Utils.showLog (Log.INFO, "engine_serial", "removed a serial id " + serial_id + " in engine list", true);
    }

    public void removeAllSerialInEngineList () {
        this.engineSerialList.clear ();
        Utils.showLog (Log.INFO, "engine_serial", "removed all serials in engine list", true);
    }

    public List<Serial> getAtsSerialList () {
        return atsSerialList;
    }

    public void setAtsSerialList (List<Serial> atsSerialList) {
        this.atsSerialList = atsSerialList;
    }

    public void setSerialInATSList (Serial ATSSerial) {
        this.atsSerialList.add (ATSSerial);
        Utils.showLog (Log.INFO, "ats_serial", "added a new serial in ats list", true);
    }

    public void removeSerialInATSList (int serial_id) {
        for (int i = 0; i < atsSerialList.size (); i++) {
            Serial ATSSerial = atsSerialList.get (i);
            if (serial_id == ATSSerial.getSerial_id ()) {
                this.atsSerialList.remove (i);
            }
        }
        Utils.showLog (Log.INFO, "ats_serial", "removed a serial id " + serial_id + " in ats list", true);
    }

    public void removeAllSerialInATSList () {
        this.atsSerialList.clear ();
        Utils.showLog (Log.INFO, "ats_serial", "removed all serials in ats list", true);
    }
}
