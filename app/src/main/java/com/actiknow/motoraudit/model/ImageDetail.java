package com.actiknow.motoraudit.model;

/**
 * Created by Admin on 08-08-2016.
 */
public class ImageDetail {
    String image_str, file_name, description, customer_name;

    public ImageDetail (String image_str, String file_name, String description, String customer_name) {
        this.image_str = image_str;
        this.file_name = file_name;
        this.description = description;
        this.customer_name = customer_name;
    }

    public String getImage_str () {
        return image_str;
    }

    public void setImage_str (String image_str) {
        this.image_str = image_str;
    }

    public String getFile_name () {
        return file_name;
    }

    public void setFile_name (String file_name) {
        this.file_name = file_name;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getCustomer_name () {
        return customer_name;
    }

    public void setCustomer_name (String customer_name) {
        this.customer_name = customer_name;
    }
}
