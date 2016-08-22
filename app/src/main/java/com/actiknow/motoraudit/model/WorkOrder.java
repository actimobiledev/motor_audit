package com.actiknow.motoraudit.model;

import android.util.Log;

import com.actiknow.motoraudit.utils.Utils;

public class WorkOrder {
    private int wo_id, wo_contract_num, wo_customer_id;
    private String wo_site_name, wo_customer_name;

    public WorkOrder () {
    }

    public WorkOrder (int wo_id, int wo_contract_num, int wo_customer_id, String wo_site_name, String wo_customer_name) {
        this.wo_id = wo_id;
        this.wo_contract_num = wo_contract_num;
        this.wo_customer_id = wo_customer_id;
        this.wo_site_name = wo_site_name;
        this.wo_customer_name = wo_customer_name;
    }

    public int getWo_id () {
        return wo_id;
    }

    public void setWo_id (int wo_id) {
        this.wo_id = wo_id;
        Utils.showLog (Log.DEBUG, "wo_id", "" + wo_id, false);
    }

    public int getWo_contract_num () {
        return wo_contract_num;
    }

    public void setWo_contract_num (int wo_contract_num) {
        this.wo_contract_num = wo_contract_num;
        Utils.showLog (Log.DEBUG, "wo_contract_num", "" + wo_contract_num, false);
    }

    public int getWo_customer_id () {
        return wo_customer_id;
    }

    public void setWo_customer_id (int wo_customer_id) {
        this.wo_customer_id = wo_customer_id;
        Utils.showLog (Log.DEBUG, "wo_customer_id", "" + wo_customer_id, false);
    }

    public String getWo_site_name () {
        return wo_site_name;
    }

    public void setWo_site_name (String wo_site_name) {
        this.wo_site_name = wo_site_name;
        Utils.showLog (Log.DEBUG, "wo_site_name", wo_site_name, false);
    }

    public String getWo_customer_name () {
        return wo_customer_name;
    }

    public void setWo_customer_name (String wo_customer_name) {
        this.wo_customer_name = wo_customer_name;
        Utils.showLog (Log.DEBUG, "wo_customer_name", wo_customer_name, false);
    }
}
