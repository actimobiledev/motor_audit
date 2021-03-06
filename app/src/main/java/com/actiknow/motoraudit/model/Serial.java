package com.actiknow.motoraudit.model;

/**
 * Created by Admin on 03-08-2016.
 */
public class Serial {
    boolean checked;
    int serial_id, manufacturer_id, wo_number, form_id, contract_num;
    String serial_number, model_number, serial_type, manufacturer_name;

    public Serial (boolean checked, int serial_id, int manufacturer_id, int wo_number, int form_id, int contract_num,
                   String serial_number, String model_number, String serial_type,
                   String manufacturer_name) {
        this.checked = checked;
        this.serial_id = serial_id;
        this.manufacturer_id = manufacturer_id;
        this.wo_number = wo_number;
        this.form_id = form_id;
        this.contract_num = contract_num;
        this.serial_number = serial_number;
        this.model_number = model_number;
        this.serial_type = serial_type;
        this.manufacturer_name = manufacturer_name;
    }

    public Serial () {
    }

    public boolean isChecked () {
        return checked;
    }

    public void setChecked (boolean checked) {
        this.checked = checked;
    }

    public int getSerial_id () {
        return serial_id;
    }

    public void setSerial_id (int serial_id) {
        this.serial_id = serial_id;
    }

    public int getManufacturer_id () {
        return manufacturer_id;
    }

    public void setManufacturer_id (int manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }

    public int getWo_number () {
        return wo_number;
    }

    public void setWo_number (int wo_number) {
        this.wo_number = wo_number;
    }

    public int getForm_id () {
        return form_id;
    }

    public void setForm_id (int form_id) {
        this.form_id = form_id;
    }

    public int getContract_num () {
        return contract_num;
    }

    public void setContract_num (int contract_num) {
        this.contract_num = contract_num;
    }

    public String getSerial_number () {
        return serial_number;
    }

    public void setSerial_number (String serial_number) {
        this.serial_number = serial_number;
    }

    public String getSerial_type () {
        return serial_type;
    }

    public void setSerial_type (String serial_type) {
        this.serial_type = serial_type;
    }

    public String getModel_number () {
        return model_number;
    }

    public void setModel_number (String model_number) {
        this.model_number = model_number;
    }

    public String getManufacturer_name () {
        return manufacturer_name;
    }

    public void setManufacturer_name (String manufacturer_name) {
        this.manufacturer_name = manufacturer_name;
    }
}
