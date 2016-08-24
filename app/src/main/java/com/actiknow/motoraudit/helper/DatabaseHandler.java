package com.actiknow.motoraudit.helper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.actiknow.motoraudit.model.Manufacturer;
import com.actiknow.motoraudit.model.Serial;
import com.actiknow.motoraudit.model.ServiceCheck;
import com.actiknow.motoraudit.model.WorkOrder;
import com.actiknow.motoraudit.model.WorkOrderDetail;
import com.actiknow.motoraudit.utils.AppConfigTags;
import com.actiknow.motoraudit.utils.Constants;
import com.actiknow.motoraudit.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version


    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "MotorAudit";

    // Table Names
    private static final String TABLE_MANUFACTURER = "manufacturer";
    private static final String TABLE_WORKORDER = "workorder";
    private static final String TABLE_SERVICE_CHECK = "service_check";
    private static final String TABLE_CONTRACT_SERIAL = "contract_serial";
    private static final String TABLE_SERVICE_FORM = "service_form";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // MANUFACTURER Table - column names
    private static final String KEY_MANUFACTURER_ID = "manufacturer_id";
    private static final String KEY_MANUFACTURER_NAME = "manufacturer_name";

    // WORKORDER Table - column names
    private static final String KEY_WORKORDER_NUMBER = "workorder_number";
    private static final String KEY_CONTRACT_NUMBER = "contract_number";
    private static final String KEY_SITE_NAME = "site_name";
    private static final String KEY_CUSTOMER_NAME = "customer_name";
    private static final String KEY_CUSTOMER_ID = "customer_id";

    // SERVICE_CHECK Table - column names
    private static final String KEY_SERVICE_CHECK_ID = "service_check_id";
    private static final String KEY_HEADING = "heading";
    private static final String KEY_SUB_HEADING = "sub_heading";
    private static final String KEY_PASS_REQUIRED = "pass_required";
    private static final String KEY_GROUP_ID = "group_id";
    private static final String KEY_GROUP_NAME = "group_name";
    private static final String KEY_GROUP_TYPE = "group_type";


    // CONTRACT_SERIAL Table - column names
    private static final String KEY_SERVICE_SERIAL_ID = "service_serial_id";
    private static final String KEY_SERIAL_NUMBER = "serial_number";
    private static final String KEY_MODEL_NUMBER = "model_number";
    private static final String KEY_SERIAL_TYPE = "serial_type";
    private static final String KEY_NEW_FLAG = "new_flag";
    private static final String KEY_UPDATE_FLAG = "update_flag";


    // SERVICE_FORM Table - column names
    private static final String KEY_FORM_ID = "form_id";
    private static final String KEY_ONSITE_CONTACT = "onsite_contact";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_GEN_SERIAL_ID = "gen_serial_id";
    private static final String KEY_KW_RATING = "kw_rating";
    private static final String KEY_TIME_IN = "time_in";
    private static final String KEY_TIME_OUT = "time_out";
    private static final String KEY_GEN_CONDITION = "gen_condition";
    private static final String KEY_GEN_STATUS = "gen_status";
    private static final String KEY_COMMENTS = "comments";
    private static final String KEY_GEN_SERIAL = "gen_serial";
    private static final String KEY_GEN_MODEL = "gen_model";
    private static final String KEY_GEN_MAKE = "gen_make";
    private static final String KEY_BEFORE_IMAGE_JSON = "before_image_json";
    private static final String KEY_AFTER_IMAGE_JSON = "after_image_json";
    private static final String KEY_SERVICE_CHECK_JSON = "service_check_json";
    private static final String KEY_ENG_SERIAL_JSON = "eng_serial_json";
    private static final String KEY_ATS_SERIAL_JSON = "ats_serial_json";
    private static final String KEY_SIGNATURE_JSON = "signature_json";


    // Manufacturer table Create Statements
    private static final String CREATE_TABLE_MANUFACTURER = "CREATE TABLE "
            + TABLE_MANUFACTURER + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MANUFACTURER_NAME
            + " TEXT," + KEY_MANUFACTURER_ID + " INTEGER," + KEY_CREATED_AT + " DATETIME" + ")";

    // Workorder table create statement
    private static final String CREATE_TABLE_WORKORDER = "CREATE TABLE " + TABLE_WORKORDER
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_WORKORDER_NUMBER + " INTEGER," + KEY_CONTRACT_NUMBER + " INTEGER,"
            + KEY_SITE_NAME + " TEXT," + KEY_CUSTOMER_NAME + " TEXT," + KEY_CUSTOMER_ID + " INTEGER," + KEY_CREATED_AT + " DATETIME" + ")";

    // ServiceCheck table create statement
    private static final String CREATE_TABLE_SERVICE_CHECK = "CREATE TABLE " + TABLE_SERVICE_CHECK
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SERVICE_CHECK_ID + " INTEGER," + KEY_HEADING + " TEXT," + KEY_SUB_HEADING + " TEXT,"
            + KEY_PASS_REQUIRED + " INTEGER DEFAULT 0," + KEY_GROUP_ID + " INTEGER," + KEY_GROUP_NAME + " TEXT," +
            KEY_GROUP_TYPE + " INTEGER," + KEY_CREATED_AT + " DATETIME" + ")";


    // contract serial table create statement
    private static final String CREATE_TABLE_CONTRACT_SERIAL = "CREATE TABLE " + TABLE_CONTRACT_SERIAL
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SERVICE_SERIAL_ID + " INTEGER," +
            KEY_SERIAL_NUMBER + " TEXT," + KEY_MODEL_NUMBER + " TEXT," + KEY_MANUFACTURER_ID + " INTEGER," +
            KEY_MANUFACTURER_NAME + " TEXT," + KEY_SERIAL_TYPE + " TEXT," + KEY_WORKORDER_NUMBER + " INTEGER," + KEY_FORM_ID + " INTEGER," +
            KEY_NEW_FLAG + " INTEGER DEFAULT 0," + KEY_UPDATE_FLAG + " INTEGER DEFAULT 0," + KEY_CREATED_AT + " DATETIME" + ")";

    // service from table create statement
    private static final String CREATE_TABLE_SERVICE_FORM = "CREATE TABLE " + TABLE_SERVICE_FORM
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FORM_ID + " INTEGER," +
            KEY_WORKORDER_NUMBER + " INTEGER," + KEY_CUSTOMER_NAME + " TEXT," + KEY_ONSITE_CONTACT + " TEXT," +
            KEY_EMAIL + " TEXT," + KEY_CONTRACT_NUMBER + " INTEGER," + KEY_GEN_SERIAL_ID + " INTEGER," +
            KEY_KW_RATING + " TEXT," + KEY_TIME_IN + " TEXT," + KEY_TIME_OUT + " TEXT," +
            KEY_GEN_CONDITION + " TEXT," + KEY_GEN_STATUS + " TEXT," + KEY_COMMENTS + " TEXT," +
            KEY_GEN_SERIAL + " TEXT," + KEY_GEN_MODEL + " TEXT," + KEY_GEN_MAKE + " INTEGER," +
            KEY_BEFORE_IMAGE_JSON + " TEXT," + KEY_AFTER_IMAGE_JSON + " TEXT," + KEY_SERVICE_CHECK_JSON + " TEXT," +
            KEY_ENG_SERIAL_JSON + " TEXT," + KEY_ATS_SERIAL_JSON + " TEXT," + KEY_SIGNATURE_JSON + " TEXT," +
            KEY_CREATED_AT + " DATETIME" + ")";

    public DatabaseHandler (Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL (CREATE_TABLE_MANUFACTURER);
        db.execSQL (CREATE_TABLE_WORKORDER);
        db.execSQL (CREATE_TABLE_SERVICE_CHECK);
        db.execSQL (CREATE_TABLE_CONTRACT_SERIAL);
        db.execSQL (CREATE_TABLE_SERVICE_FORM);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL ("DROP TABLE IF EXISTS " + TABLE_MANUFACTURER);
        db.execSQL ("DROP TABLE IF EXISTS " + TABLE_WORKORDER);
        db.execSQL ("DROP TABLE IF EXISTS " + TABLE_SERVICE_CHECK);
        db.execSQL ("DROP TABLE IF EXISTS " + TABLE_CONTRACT_SERIAL);
        db.execSQL ("DROP TABLE IF EXISTS " + TABLE_SERVICE_FORM);
        onCreate (db);
    }

    // ------------------------ "Manufacturer" table methods ----------------//

    public long createManufacturer (Manufacturer manufacturer) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Creating Manufacturer", false);
        ContentValues values = new ContentValues ();
        values.put (KEY_MANUFACTURER_ID, manufacturer.getManufacturer_id ());
        values.put (KEY_MANUFACTURER_NAME, manufacturer.getManufacturer_name ());
        values.put (KEY_CREATED_AT, getDateTime ());
        long manufacturer_id = db.insert (TABLE_MANUFACTURER, null, values);
        return manufacturer_id;
    }

    public Manufacturer getManufacturer (long manufacturer_id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        String selectQuery = "SELECT  * FROM " + TABLE_MANUFACTURER + " WHERE " + KEY_MANUFACTURER_ID + " = " + manufacturer_id;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get Manufacturer where Manufacturer id = " + manufacturer_id, false);
        Cursor c = db.rawQuery (selectQuery, null);
        if (c != null)
            c.moveToFirst ();
        Manufacturer manufacturer = new Manufacturer ();
        manufacturer.setManufacturer_id (c.getInt (c.getColumnIndex (KEY_MANUFACTURER_ID)));
        manufacturer.setManufacturer_name ((c.getString (c.getColumnIndex (KEY_MANUFACTURER_NAME))));
        return manufacturer;
    }

    public List<Manufacturer> getAllManufacturers () {
        List<Manufacturer> manufacturers = new ArrayList<Manufacturer> ();
        String selectQuery = "SELECT  * FROM " + TABLE_MANUFACTURER;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get all manufacturers", false);
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor c = db.rawQuery (selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst ()) {
            do {
                Manufacturer manufacturer = new Manufacturer ();
                manufacturer.setManufacturer_id (c.getInt ((c.getColumnIndex (KEY_MANUFACTURER_ID))));
                manufacturer.setManufacturer_name ((c.getString (c.getColumnIndex (KEY_MANUFACTURER_NAME))));
                manufacturers.add (manufacturer);
            } while (c.moveToNext ());
        }
        return manufacturers;
    }

    public int getManufacturerCount () {
        String countQuery = "SELECT  * FROM " + TABLE_MANUFACTURER;
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.rawQuery (countQuery, null);
        int count = cursor.getCount ();
        cursor.close ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get total manufacturers count : " + count, false);
        return count;
    }

    public int updateManufacturer (Manufacturer manufacturer) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Update manufacturer", false);
        ContentValues values = new ContentValues ();
        values.put (KEY_MANUFACTURER_ID, manufacturer.getManufacturer_id ());
        values.put (KEY_MANUFACTURER_NAME, manufacturer.getManufacturer_name ());
        // updating row
        return db.update (TABLE_MANUFACTURER, values, KEY_MANUFACTURER_ID + " = ?", new String[] {String.valueOf (manufacturer.getManufacturer_id ())});
    }

    public void deleteManufacturer (long manufacturer_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete manufacturer where manufacturer id = " + manufacturer_id, false);
        db.delete (TABLE_MANUFACTURER, KEY_MANUFACTURER_ID + " = ?",
                new String[] {String.valueOf (manufacturer_id)});
    }

    public void deleteAllManufacturer () {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete all manufacturers", false);
        db.execSQL ("delete from " + TABLE_MANUFACTURER);
    }


    // ------------------------ "workorder" table methods ----------------//

    public long createWorkorder (WorkOrder workOrder) {
        SQLiteDatabase db = this.getWritableDatabase ();
        ContentValues values = new ContentValues ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Creating Workorder", false);
        values.put (KEY_WORKORDER_NUMBER, workOrder.getWo_id ());
        values.put (KEY_CONTRACT_NUMBER, workOrder.getWo_contract_num ());
        values.put (KEY_SITE_NAME, workOrder.getWo_site_name ());
        values.put (KEY_CUSTOMER_NAME, workOrder.getWo_customer_name ());
        values.put (KEY_CUSTOMER_ID, workOrder.getWo_customer_id ());
        values.put (KEY_CREATED_AT, getDateTime ());
        long atm_id = db.insert (TABLE_WORKORDER, null, values);
        return atm_id;
    }

    public WorkOrder getWorkOrder (long wo_id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        String selectQuery = "SELECT  * FROM " + TABLE_WORKORDER + " WHERE " + KEY_WORKORDER_NUMBER + " = " + wo_id;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get Workorder where Workorder number = " + wo_id, false);
        Cursor c = db.rawQuery (selectQuery, null);
        if (c != null)
            c.moveToFirst ();
        WorkOrder workOrder = new WorkOrder ();
        workOrder.setWo_id (c.getInt (c.getColumnIndex (KEY_WORKORDER_NUMBER)));
        workOrder.setWo_contract_num (c.getInt (c.getColumnIndex (KEY_CONTRACT_NUMBER)));
        workOrder.setWo_site_name (c.getString (c.getColumnIndex (KEY_SITE_NAME)));
        workOrder.setWo_customer_name (c.getString (c.getColumnIndex (KEY_CUSTOMER_NAME)));
        workOrder.setWo_customer_id (c.getInt (c.getColumnIndex (KEY_CUSTOMER_ID)));
        return workOrder;
    }

    public List<WorkOrder> getAllWorkorders () {
        List<WorkOrder> workOrders = new ArrayList<WorkOrder> ();
        String selectQuery = "SELECT  * FROM " + TABLE_WORKORDER;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get all Workorders", false);
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor c = db.rawQuery (selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst ()) {
            do {
                WorkOrder workOrder = new WorkOrder ();
                workOrder.setWo_id (c.getInt (c.getColumnIndex (KEY_WORKORDER_NUMBER)));
                workOrder.setWo_contract_num (c.getInt (c.getColumnIndex (KEY_CONTRACT_NUMBER)));
                workOrder.setWo_site_name (c.getString (c.getColumnIndex (KEY_SITE_NAME)));
                workOrder.setWo_customer_name (c.getString (c.getColumnIndex (KEY_CUSTOMER_NAME)));
                workOrder.setWo_customer_id (c.getInt (c.getColumnIndex (KEY_CUSTOMER_ID)));
                workOrders.add (workOrder);
            } while (c.moveToNext ());
        }
        return workOrders;
    }

    public int getWorkorderCount () {
        String countQuery = "SELECT  * FROM " + TABLE_WORKORDER;
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.rawQuery (countQuery, null);
        int count = cursor.getCount ();
        cursor.close ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get total workorder count : " + count, false);
        return count;
    }

    public int updateWorkorder (WorkOrder workOrder) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Update workorder", false);
        ContentValues values = new ContentValues ();
        values.put (KEY_WORKORDER_NUMBER, workOrder.getWo_id ());
        values.put (KEY_CONTRACT_NUMBER, workOrder.getWo_contract_num ());
        values.put (KEY_SITE_NAME, workOrder.getWo_site_name ());
        values.put (KEY_CUSTOMER_NAME, workOrder.getWo_customer_name ());
        values.put (KEY_CUSTOMER_ID, workOrder.getWo_customer_id ());
        // updating row
        return db.update (TABLE_WORKORDER, values, KEY_WORKORDER_NUMBER + " = ?", new String[] {String.valueOf (workOrder.getWo_id ())});
    }

    public void deleteWorkorder (long workorder_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete workorder where wo id = " + workorder_id, false);
        db.delete (TABLE_WORKORDER, KEY_WORKORDER_NUMBER + " = ?", new String[] {String.valueOf (workorder_id)});
    }

    public void deleteAllWorkorders () {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete all workorders", false);
        db.execSQL ("delete from " + TABLE_WORKORDER);
    }

    // ------------------------ "SERVICE CHECK" table methods ----------------//

    public long createServiceCheck (ServiceCheck serviceCheck) {
        SQLiteDatabase db = this.getWritableDatabase ();
        ContentValues values = new ContentValues ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Creating Servicecheck", false);
        values.put (KEY_SERVICE_CHECK_ID, serviceCheck.getService_check_id ());
        values.put (KEY_HEADING, serviceCheck.getHeading ());
        values.put (KEY_SUB_HEADING, serviceCheck.getSub_heading ());
        if (serviceCheck.isPass_required ()) {
            values.put (KEY_PASS_REQUIRED, 1);
        } else {
            values.put (KEY_PASS_REQUIRED, 0);
        }
        values.put (KEY_GROUP_ID, serviceCheck.getGroup_id ());
        values.put (KEY_GROUP_NAME, serviceCheck.getGroup_name ());
        values.put (KEY_GROUP_TYPE, serviceCheck.getGroup_type ());
        values.put (KEY_CREATED_AT, getDateTime ());
        long service_check_id = db.insert (TABLE_SERVICE_CHECK, null, values);
        return service_check_id;
    }

    public ServiceCheck getServiceCheck (long service_check_id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        String selectQuery = "SELECT  * FROM " + TABLE_SERVICE_CHECK + " WHERE " + KEY_SERVICE_CHECK_ID + " = " + service_check_id;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get Service check where service check id = " + service_check_id, false);
        Cursor c = db.rawQuery (selectQuery, null);
        if (c != null)
            c.moveToFirst ();
        ServiceCheck serviceCheck = new ServiceCheck ();
        serviceCheck.setService_check_id (c.getInt (c.getColumnIndex (KEY_SERVICE_CHECK_ID)));
        serviceCheck.setHeading (c.getString (c.getColumnIndex (KEY_HEADING)));
        serviceCheck.setSub_heading (c.getString (c.getColumnIndex (KEY_SUB_HEADING)));
        Boolean pass_required = (c.getInt (c.getColumnIndex (KEY_PASS_REQUIRED)) == 1);
        serviceCheck.setPass_required (pass_required);
        serviceCheck.setGroup_id (c.getInt (c.getColumnIndex (KEY_GROUP_ID)));
        serviceCheck.setGroup_type (c.getInt (c.getColumnIndex (KEY_GROUP_TYPE)));
        serviceCheck.setGroup_name (c.getString (c.getColumnIndex (KEY_GROUP_NAME)));
        serviceCheck.setComment ("");
        serviceCheck.setComment_required (false);
        serviceCheck.setSelection_flag (0);
        serviceCheck.setSelection_text ("N/A");
        return serviceCheck;
    }

    public List<ServiceCheck> getAllServiceChecks () {
        List<ServiceCheck> serviceChecks = new ArrayList<ServiceCheck> ();
        String selectQuery = "SELECT  * FROM " + TABLE_SERVICE_CHECK;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get all service checks", false);
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor c = db.rawQuery (selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst ()) {
            do {
                ServiceCheck serviceCheck = new ServiceCheck ();
                serviceCheck.setService_check_id (c.getInt (c.getColumnIndex (KEY_SERVICE_CHECK_ID)));
                serviceCheck.setHeading (c.getString (c.getColumnIndex (KEY_HEADING)));
                serviceCheck.setSub_heading (c.getString (c.getColumnIndex (KEY_SUB_HEADING)));
                Boolean pass_required = (c.getInt (c.getColumnIndex (KEY_PASS_REQUIRED)) == 1);
                serviceCheck.setPass_required (pass_required);
                serviceCheck.setGroup_id (c.getInt (c.getColumnIndex (KEY_GROUP_ID)));
                serviceCheck.setGroup_type (c.getInt (c.getColumnIndex (KEY_GROUP_TYPE)));
                serviceCheck.setGroup_name (c.getString (c.getColumnIndex (KEY_GROUP_NAME)));
                serviceCheck.setComment ("");
                serviceCheck.setComment_required (false);
                serviceCheck.setSelection_flag (0);
                serviceCheck.setSelection_text ("N/A");
                serviceChecks.add (serviceCheck);
            } while (c.moveToNext ());
        }
        return serviceChecks;
    }

    public int getServiceCheckCount () {
        String countQuery = "SELECT  * FROM " + TABLE_SERVICE_CHECK;
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.rawQuery (countQuery, null);
        int count = cursor.getCount ();
        cursor.close ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get total service check count : " + count, false);
        return count;
    }

    public int updateServiceCheck (ServiceCheck serviceCheck) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Update  service check", false);
        ContentValues values = new ContentValues ();
        values.put (KEY_HEADING, serviceCheck.getHeading ());
        values.put (KEY_SUB_HEADING, serviceCheck.getSub_heading ());
        if (serviceCheck.isPass_required ()) {
            values.put (KEY_PASS_REQUIRED, 1);
        } else {
            values.put (KEY_PASS_REQUIRED, 0);
        }
        values.put (KEY_GROUP_ID, serviceCheck.getGroup_id ());
        values.put (KEY_GROUP_NAME, serviceCheck.getGroup_name ());
        values.put (KEY_GROUP_TYPE, serviceCheck.getGroup_type ());
        // updating row
        return db.update (TABLE_SERVICE_CHECK, values, KEY_SERVICE_CHECK_ID + " = ?", new String[] {String.valueOf (serviceCheck.getService_check_id ())});
    }

    public void deleteServiceCheck (int service_check_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete service check where id = " + service_check_id, false);
        db.delete (TABLE_SERVICE_CHECK, KEY_SERVICE_CHECK_ID + " = ?", new String[] {String.valueOf (service_check_id)});
    }

    public void deleteAllServiceChecks () {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete all service checks", false);
        db.execSQL ("delete from " + TABLE_SERVICE_CHECK);
    }


    // ------------------------ "CONTRACT_SERIAL" table methods ----------------//

    public long createContractSerial (Serial contractSerial, boolean new_flag) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Creating contract serial", false);
        ContentValues values = new ContentValues ();
        values.put (KEY_SERVICE_SERIAL_ID, contractSerial.getSerial_id ());
        values.put (KEY_SERIAL_NUMBER, contractSerial.getSerial_number ());
        values.put (KEY_MODEL_NUMBER, contractSerial.getModel_number ());
        values.put (KEY_MANUFACTURER_ID, contractSerial.getManufacturer_id ());
        values.put (KEY_MANUFACTURER_NAME, contractSerial.getManufacturer_name ());
        values.put (KEY_SERIAL_TYPE, contractSerial.getSerial_type ());
        values.put (KEY_WORKORDER_NUMBER, contractSerial.getWo_number ());
        values.put (KEY_FORM_ID, contractSerial.getForm_id ());
        if (new_flag) {
            values.put (KEY_NEW_FLAG, 1);
        } else {
            values.put (KEY_NEW_FLAG, 0);
        }
        values.put (KEY_UPDATE_FLAG, 0);
        values.put (KEY_CREATED_AT, getDateTime ());
        long service_serial_id = db.insert (TABLE_CONTRACT_SERIAL, null, values);
        return service_serial_id;
    }

    public Serial getContractSerial (long service_serial_id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTRACT_SERIAL + " WHERE " + KEY_SERVICE_SERIAL_ID + " = " + service_serial_id;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get contract serial where serial id = " + service_serial_id, false);
        Cursor c = db.rawQuery (selectQuery, null);
        if (c != null)
            c.moveToFirst ();
        Serial contractSerial = new Serial ();
        contractSerial.setSerial_id (c.getInt (c.getColumnIndex (KEY_SERVICE_SERIAL_ID)));
        contractSerial.setSerial_number (c.getString (c.getColumnIndex (KEY_SERIAL_NUMBER)));
        contractSerial.setModel_number (c.getString (c.getColumnIndex (KEY_MODEL_NUMBER)));
        contractSerial.setManufacturer_id (c.getInt (c.getColumnIndex (KEY_MANUFACTURER_ID)));
        contractSerial.setManufacturer_name (c.getString (c.getColumnIndex (KEY_MANUFACTURER_NAME)));
        contractSerial.setSerial_type (c.getString (c.getColumnIndex (KEY_SERIAL_TYPE)));
        contractSerial.setForm_id (c.getInt (c.getColumnIndex (KEY_FORM_ID)));
        contractSerial.setWo_number (c.getInt (c.getColumnIndex (KEY_WORKORDER_NUMBER)));
        return contractSerial;
    }

    public List<Serial> getAllContractSerials (int wo_number) {
        List<Serial> contractSerials = new ArrayList<Serial> ();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTRACT_SERIAL + " WHERE " + KEY_WORKORDER_NUMBER + " = " + wo_number;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get all contract serials for wo number = " + wo_number, false);
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor c = db.rawQuery (selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst ()) {
            do {
                Serial contractSerial = new Serial ();
                contractSerial.setSerial_id (c.getInt (c.getColumnIndex (KEY_SERVICE_SERIAL_ID)));
                contractSerial.setSerial_number (c.getString (c.getColumnIndex (KEY_SERIAL_NUMBER)));
                contractSerial.setModel_number (c.getString (c.getColumnIndex (KEY_MODEL_NUMBER)));
                contractSerial.setManufacturer_id (c.getInt (c.getColumnIndex (KEY_MANUFACTURER_ID)));
                contractSerial.setManufacturer_name (c.getString (c.getColumnIndex (KEY_MANUFACTURER_NAME)));
                contractSerial.setSerial_type (c.getString (c.getColumnIndex (KEY_SERIAL_TYPE)));
                contractSerial.setWo_number (c.getInt (c.getColumnIndex (KEY_WORKORDER_NUMBER)));
                contractSerial.setForm_id (c.getInt (c.getColumnIndex (KEY_FORM_ID)));
                contractSerials.add (contractSerial);
            } while (c.moveToNext ());
        }
        return contractSerials;
    }

    public List<Serial> getAllNewContractSerials () {
        List<Serial> contractSerials = new ArrayList<Serial> ();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTRACT_SERIAL + " WHERE " + KEY_NEW_FLAG + " = 1";
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get all new contract serials", false);
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor c = db.rawQuery (selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst ()) {
            do {
                Serial contractSerial = new Serial ();
                contractSerial.setSerial_id (c.getInt (c.getColumnIndex (KEY_SERVICE_SERIAL_ID)));
                contractSerial.setSerial_number (c.getString (c.getColumnIndex (KEY_SERIAL_NUMBER)));
                contractSerial.setModel_number (c.getString (c.getColumnIndex (KEY_MODEL_NUMBER)));
                contractSerial.setManufacturer_id (c.getInt (c.getColumnIndex (KEY_MANUFACTURER_ID)));
                contractSerial.setManufacturer_name (c.getString (c.getColumnIndex (KEY_MANUFACTURER_NAME)));
                contractSerial.setSerial_type (c.getString (c.getColumnIndex (KEY_SERIAL_TYPE)));
                contractSerial.setWo_number (c.getInt (c.getColumnIndex (KEY_WORKORDER_NUMBER)));
                contractSerial.setForm_id (c.getInt (c.getColumnIndex (KEY_FORM_ID)));
                contractSerials.add (contractSerial);
            } while (c.moveToNext ());
        }
        return contractSerials;
    }

    public List<Serial> getAllUpdatedContractSerials () {
        List<Serial> contractSerials = new ArrayList<Serial> ();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTRACT_SERIAL + " WHERE " + KEY_UPDATE_FLAG + " = 1";
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get all updated contract serials", false);
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor c = db.rawQuery (selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst ()) {
            do {
                Serial contractSerial = new Serial ();
                contractSerial.setSerial_id (c.getInt (c.getColumnIndex (KEY_SERVICE_SERIAL_ID)));
                contractSerial.setSerial_number (c.getString (c.getColumnIndex (KEY_SERIAL_NUMBER)));
                contractSerial.setModel_number (c.getString (c.getColumnIndex (KEY_MODEL_NUMBER)));
                contractSerial.setManufacturer_id (c.getInt (c.getColumnIndex (KEY_MANUFACTURER_ID)));
                contractSerial.setManufacturer_name (c.getString (c.getColumnIndex (KEY_MANUFACTURER_NAME)));
                contractSerial.setSerial_type (c.getString (c.getColumnIndex (KEY_SERIAL_TYPE)));
                contractSerial.setWo_number (c.getInt (c.getColumnIndex (KEY_WORKORDER_NUMBER)));
                contractSerial.setForm_id (c.getInt (c.getColumnIndex (KEY_FORM_ID)));
                contractSerials.add (contractSerial);
            } while (c.moveToNext ());
        }
        return contractSerials;
    }

    public int getUpdatedContractSerialCount () {
        String countQuery = "SELECT  * FROM " + TABLE_CONTRACT_SERIAL + " WHERE " + KEY_UPDATE_FLAG + " = 1";
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.rawQuery (countQuery, null);
        int count = cursor.getCount ();
        cursor.close ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get updated contract serial count : " + count, false);
        return count;
    }

    public int getNewContractSerialCount () {
        String countQuery = "SELECT  * FROM " + TABLE_CONTRACT_SERIAL + " WHERE " + KEY_NEW_FLAG + " = 1";
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.rawQuery (countQuery, null);
        int count = cursor.getCount ();
        cursor.close ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get new contract serial count : " + count, false);
        return count;
    }

    public int getContractSerialCount () {
        String countQuery = "SELECT  * FROM " + TABLE_CONTRACT_SERIAL;
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.rawQuery (countQuery, null);
        int count = cursor.getCount ();
        cursor.close ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get total contract serial count : " + count, false);
        return count;
    }

    public int updateContractSerial (Serial contractSerial) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Update contract serial", false);
        ContentValues values = new ContentValues ();
        values.put (KEY_SERIAL_NUMBER, contractSerial.getSerial_number ());
        values.put (KEY_MODEL_NUMBER, contractSerial.getModel_number ());
        values.put (KEY_MANUFACTURER_ID, contractSerial.getManufacturer_id ());
        values.put (KEY_MANUFACTURER_NAME, contractSerial.getManufacturer_name ());
        values.put (KEY_SERIAL_TYPE, contractSerial.getSerial_type ());
        values.put (KEY_FORM_ID, contractSerial.getForm_id ());
        values.put (KEY_WORKORDER_NUMBER, contractSerial.getWo_number ());
        values.put (KEY_UPDATE_FLAG, 1);
        values.put (KEY_NEW_FLAG, 0);

        // updating row
        return db.update (TABLE_CONTRACT_SERIAL, values, KEY_SERVICE_SERIAL_ID + " = ?", new String[] {String.valueOf (contractSerial.getSerial_id ())});
    }

    public void deleteContractSerial (int service_serial_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete contract serial where serial id = " + service_serial_id, false);
        db.delete (TABLE_CONTRACT_SERIAL, KEY_SERVICE_SERIAL_ID + " = ?",
                new String[] {String.valueOf (service_serial_id)});
    }

    public void deleteAllContractSerials () {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete all contract serials", false);
        db.execSQL ("delete from " + TABLE_CONTRACT_SERIAL);
    }


    // ------------------------ "SERVICE FORM" table methods ----------------//

    public long createServiceForm (WorkOrderDetail serviceForm) {
        SQLiteDatabase db = this.getWritableDatabase ();
        ContentValues values = new ContentValues ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Creating service form", true);
        values.put (KEY_FORM_ID, serviceForm.getForm_id ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getForm_id (), true);
        values.put (KEY_WORKORDER_NUMBER, serviceForm.getWork_order_id ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getWork_order_id (), true);
        values.put (KEY_CUSTOMER_NAME, serviceForm.getCustomer_name ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getCustomer_name (), true);
        values.put (KEY_ONSITE_CONTACT, serviceForm.getOnsite_contact ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getOnsite_contact (), true);
        values.put (KEY_EMAIL, serviceForm.getEmail ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getEmail (), true);
        values.put (KEY_CONTRACT_NUMBER, serviceForm.getContract_number ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getContract_number (), true);
        values.put (KEY_GEN_SERIAL_ID, serviceForm.getGenerator_serial_id ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getGenerator_serial_id (), true);
        values.put (KEY_KW_RATING, serviceForm.getKw_rating ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getKw_rating (), true);
        values.put (KEY_TIME_IN, serviceForm.getTime_in ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getTime_in (), true);
        values.put (KEY_TIME_OUT, serviceForm.getTime_out ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getTime_out (), true);
        values.put (KEY_GEN_CONDITION, serviceForm.getGenerator_condition_comment ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getGenerator_condition_comment (), true);
        values.put (KEY_GEN_STATUS, serviceForm.getGenerator_condition_text ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getGenerator_condition_text (), true);
        values.put (KEY_COMMENTS, serviceForm.getComments ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getComments (), true);
        values.put (KEY_GEN_SERIAL, serviceForm.getGenerator_serial ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getGenerator_serial (), true);
        values.put (KEY_GEN_MODEL, serviceForm.getGenerator_model ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getGenerator_model (), true);
        values.put (KEY_GEN_MAKE, serviceForm.getGenerator_make_id ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getGenerator_make_id (), true);
        values.put (KEY_BEFORE_IMAGE_JSON, serviceForm.getBefore_image_list_json ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getBefore_image_list_json (), true);
        values.put (KEY_AFTER_IMAGE_JSON, serviceForm.getAfter_image_list_json ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getAfter_image_list_json (), true);
        values.put (KEY_SERVICE_CHECK_JSON, serviceForm.getService_check_json ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getService_check_json (), true);
        values.put (KEY_ENG_SERIAL_JSON, serviceForm.getEngine_serial_json ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getEngine_serial_json (), true);
        values.put (KEY_ATS_SERIAL_JSON, serviceForm.getAts_serial_json ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getAts_serial_json (), true);
        values.put (KEY_SIGNATURE_JSON, serviceForm.getSignature_image_list_json ());
        Utils.showLog (Log.DEBUG, "database log", "" + serviceForm.getSignature_image_list_json (), true);
        values.put (KEY_CREATED_AT, getDateTime ());
        Utils.showLog (Log.DEBUG, "database log", "" + getDateTime (), true);
        long form_id = db.insert (TABLE_SERVICE_FORM, null, values);
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Form id generated" + form_id, true);
        return form_id;
    }
/*
    public ServiceCheck getServiceForm (long form_id) {
        SQLiteDatabase db = this.getReadableDatabase ();
        String selectQuery = "SELECT  * FROM " + TABLE_SERVICE_FORM + " WHERE " + KEY_SERVICE_CHECK_ID + " = " + service_check_id;
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get Service check where service check id = " + service_check_id, false);
        Cursor c = db.rawQuery (selectQuery, null);
        if (c != null)
            c.moveToFirst ();
        ServiceCheck serviceCheck = new ServiceCheck ();
        serviceCheck.setService_check_id (c.getInt (c.getColumnIndex (KEY_SERVICE_CHECK_ID)));
        serviceCheck.setHeading (c.getString (c.getColumnIndex (KEY_HEADING)));
        serviceCheck.setSub_heading (c.getString (c.getColumnIndex (KEY_SUB_HEADING)));
        Boolean pass_required = (c.getInt (c.getColumnIndex (KEY_PASS_REQUIRED)) == 1);
        serviceCheck.setPass_required (pass_required);
        serviceCheck.setGroup_id (c.getInt (c.getColumnIndex (KEY_GROUP_ID)));
        serviceCheck.setGroup_type (c.getInt (c.getColumnIndex (KEY_GROUP_TYPE)));
        serviceCheck.setGroup_name (c.getString (c.getColumnIndex (KEY_GROUP_NAME)));
        serviceCheck.setComment ("");
        serviceCheck.setComment_required (false);
        serviceCheck.setSelection_flag (0);
        serviceCheck.setSelection_text ("N/A");
        return serviceCheck;
    }
*/


    public List<WorkOrderDetail> getAllServiceForms () {
        List<WorkOrderDetail> serviceForms = new ArrayList<WorkOrderDetail> ();
        String selectQuery = "SELECT  * FROM " + TABLE_SERVICE_FORM;

        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "query : " + selectQuery, true);
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get all service forms", true);
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor c2 = db.rawQuery (selectQuery, null);
        int count = c2.getCount ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get total service form count : " + count, true);

        // looping through all rows and adding to list
        if (c2.moveToFirst ()) {
            Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "in the if condition", true);
            do {
                Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "in the loop", true);
                WorkOrderDetail serviceForm = new WorkOrderDetail ();
                serviceForm.setForm_id (c2.getInt (c2.getColumnIndex (KEY_FORM_ID)));
                serviceForm.setEmp_id (Constants.employee_id);
                serviceForm.setWork_order_id (c2.getInt (c2.getColumnIndex (KEY_WORKORDER_NUMBER)));
                serviceForm.setCustomer_name (c2.getString (c2.getColumnIndex (KEY_CUSTOMER_NAME)));
                serviceForm.setOnsite_contact (c2.getString (c2.getColumnIndex (KEY_ONSITE_CONTACT)));
                serviceForm.setEmail (c2.getString (c2.getColumnIndex (KEY_EMAIL)));
                serviceForm.setContract_number (c2.getInt (c2.getColumnIndex (KEY_CONTRACT_NUMBER)));
                serviceForm.setGenerator_serial_id (c2.getInt (c2.getColumnIndex (KEY_GEN_SERIAL_ID)));
                serviceForm.setKw_rating (c2.getString (c2.getColumnIndex (KEY_KW_RATING)));
                serviceForm.setTime_in (c2.getString (c2.getColumnIndex (KEY_TIME_IN)));
                serviceForm.setTime_out (c2.getString (c2.getColumnIndex (KEY_TIME_OUT)));
                serviceForm.setGenerator_condition_comment (c2.getString (c2.getColumnIndex (KEY_GEN_CONDITION)));
                serviceForm.setGenerator_condition_text (c2.getString (c2.getColumnIndex (KEY_GEN_STATUS)));
                serviceForm.setComments (c2.getString (c2.getColumnIndex (KEY_COMMENTS)));
                serviceForm.setGenerator_serial (c2.getString (c2.getColumnIndex (KEY_GEN_SERIAL)));
                serviceForm.setGenerator_model (c2.getString (c2.getColumnIndex (KEY_GEN_MODEL)));
                serviceForm.setGenerator_make_id (c2.getInt (c2.getColumnIndex (KEY_GEN_MAKE)));
                serviceForm.setBefore_image_list_json (c2.getString (c2.getColumnIndex (KEY_BEFORE_IMAGE_JSON)));
                serviceForm.setAfter_image_list_json (c2.getString (c2.getColumnIndex (KEY_AFTER_IMAGE_JSON)));
                serviceForm.setService_check_json (c2.getString (c2.getColumnIndex (KEY_SERVICE_CHECK_JSON)));
                serviceForm.setEngine_serial_json (c2.getString (c2.getColumnIndex (KEY_ENG_SERIAL_JSON)));
                serviceForm.setAts_serial_json (c2.getString (c2.getColumnIndex (KEY_ATS_SERIAL_JSON)));
                serviceForm.setSignature_image_list_json (c2.getString (c2.getColumnIndex (KEY_SIGNATURE_JSON)));

                Utils.showLog (Log.DEBUG, "database log", "" + c2.getInt (c2.getColumnIndex (KEY_FORM_ID)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getInt (c2.getColumnIndex (KEY_WORKORDER_NUMBER)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getString (c2.getColumnIndex (KEY_CUSTOMER_NAME)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getString (c2.getColumnIndex (KEY_ONSITE_CONTACT)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getString (c2.getColumnIndex (KEY_EMAIL)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getInt (c2.getColumnIndex (KEY_CONTRACT_NUMBER)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getInt (c2.getColumnIndex (KEY_GEN_SERIAL_ID)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getString (c2.getColumnIndex (KEY_KW_RATING)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getString (c2.getColumnIndex (KEY_TIME_IN)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getString (c2.getColumnIndex (KEY_TIME_OUT)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getString (c2.getColumnIndex (KEY_GEN_CONDITION)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getString (c2.getColumnIndex (KEY_GEN_STATUS)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getString (c2.getColumnIndex (KEY_COMMENTS)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getString (c2.getColumnIndex (KEY_GEN_SERIAL)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getString (c2.getColumnIndex (KEY_GEN_MODEL)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getInt (c2.getColumnIndex (KEY_GEN_MAKE)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getString (c2.getColumnIndex (KEY_BEFORE_IMAGE_JSON)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getString (c2.getColumnIndex (KEY_AFTER_IMAGE_JSON)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getString (c2.getColumnIndex (KEY_SERVICE_CHECK_JSON)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getString (c2.getColumnIndex (KEY_ENG_SERIAL_JSON)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getString (c2.getColumnIndex (KEY_ATS_SERIAL_JSON)), false);
                Utils.showLog (Log.DEBUG, "database log", "" + c2.getString (c2.getColumnIndex (KEY_SIGNATURE_JSON)), false);


                serviceForms.add (serviceForm);
            } while (c2.moveToNext ());
        }
        return serviceForms;
    }

    public int getServiceFormCount () {
        String countQuery = "SELECT  * FROM " + TABLE_SERVICE_FORM;
        SQLiteDatabase db = this.getReadableDatabase ();
        Cursor cursor = db.rawQuery (countQuery, null);
        int count = cursor.getCount ();
        cursor.close ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Get total service form count : " + count, false);
        return count;
    }

    /*
        public int updateServiceCheck (ServiceCheck serviceCheck) {
            SQLiteDatabase db = this.getWritableDatabase ();
            Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Update  service check", false);
            ContentValues values = new ContentValues ();
            values.put (KEY_HEADING, serviceCheck.getHeading ());
            values.put (KEY_SUB_HEADING, serviceCheck.getSub_heading ());
            if (serviceCheck.isPass_required ()) {
                values.put (KEY_PASS_REQUIRED, 1);
            } else {
                values.put (KEY_PASS_REQUIRED, 0);
            }
            values.put (KEY_GROUP_ID, serviceCheck.getGroup_id ());
            values.put (KEY_GROUP_NAME, serviceCheck.getGroup_name ());
            values.put (KEY_GROUP_TYPE, serviceCheck.getGroup_type ());
            // updating row
            return db.update (TABLE_SERVICE_CHECK, values, KEY_SERVICE_CHECK_ID + " = ?", new String[] {String.valueOf (serviceCheck.getService_check_id ())});
        }
    */
    public void deleteServiceForm (int gen_serial_id) {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete service form where gen serial id = " + gen_serial_id, false);
        db.delete (TABLE_SERVICE_FORM, KEY_GEN_SERIAL_ID + " = ?", new String[] {String.valueOf (gen_serial_id)});
    }

    public void deleteAllServiceForms () {
        SQLiteDatabase db = this.getWritableDatabase ();
        Utils.showLog (Log.DEBUG, AppConfigTags.DATABASE_LOG, "Delete all service forms", false);
        db.execSQL ("delete from " + TABLE_SERVICE_FORM);
    }


    public void closeDB () {
        SQLiteDatabase db = this.getReadableDatabase ();
        if (db != null && db.isOpen ())
            db.close ();
    }

    private String getDateTime () {
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss", Locale.getDefault ());
        Date date = new Date ();
        return dateFormat.format (date);
    }
}
