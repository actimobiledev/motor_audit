package com.actiknow.motoraudit.model;

import android.util.Log;

import com.actiknow.motoraudit.utils.Utils;

public class Job {
    private int job_id;
    private String job_serial_number;

    public Job () {
    }

    public Job (int job_id, String job_serial_number) {
        this.job_id = job_id;
        this.job_serial_number = job_serial_number;
    }

    public int getJob_id () {
        return job_id;
    }

    public void setJob_id (int job_id) {
        this.job_id = job_id;
        Utils.showLog (Log.DEBUG, "job_id", "" + job_id, false);
    }

    public String getJob_serial_number () {
        return job_serial_number;
    }

    public void setJob_serial_number (String job_serial_number) {
        this.job_serial_number = job_serial_number;
        Utils.showLog (Log.DEBUG, "job_serial_number", job_serial_number, false);
    }
}
