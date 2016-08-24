package com.actiknow.motoraudit.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.actiknow.motoraudit.R;
import com.actiknow.motoraudit.model.ImageDetail;
import com.actiknow.motoraudit.model.Serial;
import com.actiknow.motoraudit.model.ServiceCheck;
import com.actiknow.motoraudit.utils.AppConfigTags;
import com.actiknow.motoraudit.utils.AppConfigURL;
import com.actiknow.motoraudit.utils.Constants;
import com.actiknow.motoraudit.utils.NetworkConnection;
import com.actiknow.motoraudit.utils.Utils;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ViewFormActivity extends AppCompatActivity {


    GoogleApiClient client;
    TextView tvWorkOrderDescription;
    LinearLayout llGeneralInfoTop;
    TextView tvCancel;
    TextView tvUpdate;
    TextView tvTimeIn;
    TextView tvOnSiteContact;
    TextView tvEmail;

    RelativeLayout rlBeforeImage;
    LinearLayout llBeforeImage;

    RelativeLayout rlGeneratorInfo;
    TextView tvGeneratorModel;
    TextView tvGeneratorSerial;
    TextView tvKwRating;
    TextView tvGeneratorMake;

    RelativeLayout rlEngineSerial;
    LinearLayout llEngineSerial;

    RelativeLayout rlATSSerial;
    LinearLayout llATSSerial;

    RelativeLayout rlFuelSystem;
    LinearLayout llFuelSystem;
    RelativeLayout rlPreStartChecksCoolingSystem;
    LinearLayout llPreStartChecksCoolingSystem;
    RelativeLayout rlLubricationSystem;
    LinearLayout llLubricationSystem;
    RelativeLayout rlAirSystem;
    LinearLayout llAirSystem;
    RelativeLayout rlExhaustSystem;
    LinearLayout llExhaustSystem;
    RelativeLayout rlGenerator;
    LinearLayout llGenerator;
    RelativeLayout rlControlPanelCabinets;
    LinearLayout llControlPanelCabinets;
    RelativeLayout rlATSMain;
    LinearLayout llATSMain;
    RelativeLayout rlStartingSystem;
    LinearLayout llStartingSystem;
    RelativeLayout rlGeneratorEnclosure;
    LinearLayout llGeneratorEnclosure;
    RelativeLayout rlStartupAndRunningCheck;
    LinearLayout llStartupAndRunningCheck;
    RelativeLayout rlScheduledMaintenance;
    LinearLayout llScheduledMaintenance;

    RelativeLayout rlCollapse1;
    RelativeLayout rlCollapse2;
    RelativeLayout rlCollapse3;
    RelativeLayout rlCollapse4;
    RelativeLayout rlCollapse5;
    RelativeLayout rlCollapse6;
    RelativeLayout rlCollapse7;
    RelativeLayout rlCollapse8;
    RelativeLayout rlCollapse9;
    RelativeLayout rlCollapse10;
    RelativeLayout rlCollapse11;
    RelativeLayout rlCollapse12;

    RelativeLayout rlGeneratorCondition;
    LinearLayout tlGood;
    LinearLayout tlFair;
    LinearLayout tlPoor;
    TextView tvGeneratorCondition;
    TextView tvGeneratorConditionComment;
    TextView tvConditionDetails;

    RelativeLayout rlComment;
    TextView tvComment;

    RelativeLayout rlAfterImage;
    LinearLayout llAfterImage;

    RelativeLayout rlTechSignature;
    ImageView ivTechSignature;

    RelativeLayout rlCustomerSignature;
    TextView tvCustomerName;
    ImageView ivCustomerSignature;

    TextView tvTimeOut;

    ArrayList<String> listGoodCondition = new ArrayList<String> ();
    ArrayList<String> listFairCondition = new ArrayList<String> ();
    ArrayList<String> listPoorCondition = new ArrayList<String> ();

    ProgressDialog pDialog;

    private List<Serial> engineSerialList = new ArrayList<> ();
    private List<Serial> atsSerialList = new ArrayList<> ();

    private List<Serial> engineSerialListTemp = new ArrayList<> ();
    private List<Serial> atsSerialListTemp = new ArrayList<> ();


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_view_form);

        pDialog = new ProgressDialog (ViewFormActivity.this);
        Utils.showProgressDialog (pDialog, null);

        initView ();
        initData ();
        initListener ();
        initAdapter ();
//        getWorkOrderDetailFromServer(78759, 97);
        getWorkOrderDetailFromServer (Constants.workOrderDetail.getWork_order_id (), Constants.workOrderDetail.getGenerator_serial_id ());
        Utils.hideSoftKeyboard (this);


//        new LoadServiceChecksInLinearLayout (this).execute ();


//        new LoadServiceChecks (this).execute ();
    }

    private void initView () {
        tvWorkOrderDescription = (TextView) findViewById (R.id.tvWorkOrderDescription);
        llGeneralInfoTop = (LinearLayout) findViewById (R.id.llGeneralInfoTop);
        tvTimeIn = (TextView) findViewById (R.id.tvTimeIn);
        tvOnSiteContact = (TextView) findViewById (R.id.tvOnSiteContact);
        tvEmail = (TextView) findViewById (R.id.tvEmail);

        rlBeforeImage = (RelativeLayout) findViewById (R.id.rlBeforeImage);
        llBeforeImage = (LinearLayout) findViewById (R.id.llBeforeImage);

        rlGeneratorInfo = (RelativeLayout) findViewById (R.id.rlGeneratorInfo);
        tvGeneratorModel = (TextView) findViewById (R.id.tvGeneratorModel);
        tvGeneratorSerial = (TextView) findViewById (R.id.tvGeneratorSerial);
        tvKwRating = (TextView) findViewById (R.id.tvKwRating);
        tvGeneratorMake = (TextView) findViewById (R.id.tvGeneratorMake);

        rlEngineSerial = (RelativeLayout) findViewById (R.id.rlEngineSerial);
        llEngineSerial = (LinearLayout) findViewById (R.id.llEngineSerial);

        rlATSSerial = (RelativeLayout) findViewById (R.id.rlAtsSerial);
        llATSSerial = (LinearLayout) findViewById (R.id.llAtsSerial);

        rlFuelSystem = (RelativeLayout) findViewById (R.id.rlFuelSystem);
        llFuelSystem = (LinearLayout) findViewById (R.id.llFuelSystem);
        rlPreStartChecksCoolingSystem = (RelativeLayout) findViewById (R.id.rlPreStartChecksCoolingSystem);
        llPreStartChecksCoolingSystem = (LinearLayout) findViewById (R.id.llPreStartChecksCoolingSystem);
        rlLubricationSystem = (RelativeLayout) findViewById (R.id.rlLubricationSystem);
        llLubricationSystem = (LinearLayout) findViewById (R.id.llLubricationSystem);
        rlAirSystem = (RelativeLayout) findViewById (R.id.rlAirSystem);
        llAirSystem = (LinearLayout) findViewById (R.id.llAirSystem);
        rlExhaustSystem = (RelativeLayout) findViewById (R.id.rlExhaustSystem);
        llExhaustSystem = (LinearLayout) findViewById (R.id.llExhaustSystem);
        rlGenerator = (RelativeLayout) findViewById (R.id.rlGenerator);
        llGenerator = (LinearLayout) findViewById (R.id.llGenerator);
        rlControlPanelCabinets = (RelativeLayout) findViewById (R.id.rlControlPanelCabinets);
        llControlPanelCabinets = (LinearLayout) findViewById (R.id.llControlPanelCabinets);
        rlATSMain = (RelativeLayout) findViewById (R.id.rlATSMain);
        llATSMain = (LinearLayout) findViewById (R.id.llATSMain);
        rlStartingSystem = (RelativeLayout) findViewById (R.id.rlStartingSystem);
        llStartingSystem = (LinearLayout) findViewById (R.id.llStartingSystem);
        rlGeneratorEnclosure = (RelativeLayout) findViewById (R.id.rlGeneratorEnclosure);
        llGeneratorEnclosure = (LinearLayout) findViewById (R.id.llGeneratorEnclosure);
        rlStartupAndRunningCheck = (RelativeLayout) findViewById (R.id.rlStartupAndRunningCheck);
        llStartupAndRunningCheck = (LinearLayout) findViewById (R.id.llStartupAndRunningCheck);
        rlScheduledMaintenance = (RelativeLayout) findViewById (R.id.rlScheduledMaintenance);
        llScheduledMaintenance = (LinearLayout) findViewById (R.id.llScheduledMaintenance);

        rlCollapse1 = (RelativeLayout) findViewById (R.id.rl2);
        rlCollapse2 = (RelativeLayout) findViewById (R.id.rl3);
        rlCollapse3 = (RelativeLayout) findViewById (R.id.rl4);
        rlCollapse4 = (RelativeLayout) findViewById (R.id.rl5);
        rlCollapse5 = (RelativeLayout) findViewById (R.id.rl6);
        rlCollapse6 = (RelativeLayout) findViewById (R.id.rl7);
        rlCollapse7 = (RelativeLayout) findViewById (R.id.rl8);
        rlCollapse8 = (RelativeLayout) findViewById (R.id.rl9);
        rlCollapse9 = (RelativeLayout) findViewById (R.id.rl10);
        rlCollapse10 = (RelativeLayout) findViewById (R.id.rl11);
        rlCollapse11 = (RelativeLayout) findViewById (R.id.rl12);
        rlCollapse12 = (RelativeLayout) findViewById (R.id.rl13);

        rlGeneratorCondition = (RelativeLayout) findViewById (R.id.rlGeneratorCondition);
        tlGood = (LinearLayout) findViewById (R.id.tlGood);
        tlFair = (LinearLayout) findViewById (R.id.tlFair);
        tlPoor = (LinearLayout) findViewById (R.id.tlPoor);
        tvGeneratorCondition = (TextView) findViewById (R.id.tvGeneratorCondition);
        tvGeneratorConditionComment = (TextView) findViewById (R.id.tvGeneratorConditionComment);

        rlComment = (RelativeLayout) findViewById (R.id.rlComment);
        tvComment = (TextView) findViewById (R.id.tvComment);

        rlAfterImage = (RelativeLayout) findViewById (R.id.rlAfterImage);
        llAfterImage = (LinearLayout) findViewById (R.id.llAfterImage);

        rlTechSignature = (RelativeLayout) findViewById (R.id.rlTechSignature);
        ivTechSignature = (ImageView) findViewById (R.id.ivTechSignature);

        rlCustomerSignature = (RelativeLayout) findViewById (R.id.rlCustomerSignature);
        tvCustomerName = (TextView) findViewById (R.id.tvCustomerName);
        ivCustomerSignature = (ImageView) findViewById (R.id.ivCustomerSignature);

        tvCancel = (TextView) findViewById (R.id.tvCancel);
        tvUpdate = (TextView) findViewById (R.id.tvUpdate);
        tvTimeOut = (TextView) findViewById (R.id.tvTimeOut);
    }

    private void initListener () {
        tvCancel.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    tvCancel.setBackgroundResource (R.color.background_button_red_pressed);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    tvCancel.setBackgroundResource (R.color.background_button_red);
                    finish ();
                }
                return true;
            }
        });

        tvUpdate.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    tvUpdate.setBackgroundResource (R.color.background_button_green_pressed);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    tvUpdate.setBackgroundResource (R.color.background_button_green);
                    Intent intent = new Intent (ViewFormActivity.this, DetailActivity.class);
                    startActivity (intent);
                }
                return true;
            }
        });
    }

    private void initData () {
        Utils.clearAllServiceChecks ();
        //    Utils.setTypefaceToAllViews (this, tvNoInternetConnection);
        client = new GoogleApiClient.Builder (this).addApi (AppIndex.API).build ();

        getContractSerialsFromServer (Constants.workOrderDetail.getWork_order_id ());

        String generator_condition_json = Utils.getGeneratorConditionJSONFromAsset (this);
        try {
            JSONObject jsonObj = new JSONObject (generator_condition_json);
            JSONArray jsonArrayGood = jsonObj.getJSONArray (AppConfigTags.GOOD);
            JSONArray jsonArrayFair = jsonObj.getJSONArray (AppConfigTags.FAIR);
            JSONArray jsonArrayPoor = jsonObj.getJSONArray (AppConfigTags.POOR);
            for (int i = 0; i < jsonArrayGood.length (); i++) {
                JSONObject jsonObject = jsonArrayGood.getJSONObject (i);
                listGoodCondition.add (jsonObject.getString (AppConfigTags.TEXT));
            }
            for (int i = 0; i < jsonArrayFair.length (); i++) {
                JSONObject jsonObject = jsonArrayFair.getJSONObject (i);
                listFairCondition.add (jsonObject.getString (AppConfigTags.TEXT));
            }
            for (int i = 0; i < jsonArrayPoor.length (); i++) {
                JSONObject jsonObject = jsonArrayPoor.getJSONObject (i);
                listPoorCondition.add (jsonObject.getString (AppConfigTags.TEXT));
            }
        } catch (JSONException e) {
            e.printStackTrace ();
            Utils.showLog (Log.ERROR, "JSON Exception", e.getMessage (), true);
        }

        for (int i = 0; i < listGoodCondition.size (); i++) {
            String GoodConditionText = listGoodCondition.get (i);
            TableRow row = (TableRow) LayoutInflater.from (ViewFormActivity.this).inflate (R.layout.table_row_condition, null);
            tlGood.setGravity (Gravity.CENTER_VERTICAL);
            tvConditionDetails = (TextView) row.findViewById (R.id.tvConditionDetails);
            tvConditionDetails.setText ("" + GoodConditionText);
            tlGood.addView (row);
        }
        for (int i = 0; i < listFairCondition.size (); i++) {
            String FairConditionText = listFairCondition.get (i);
            TableRow row = (TableRow) LayoutInflater.from (ViewFormActivity.this).inflate (R.layout.table_row_condition, null);
            tlFair.setGravity (Gravity.CENTER_VERTICAL);
            tvConditionDetails = (TextView) row.findViewById (R.id.tvConditionDetails);
            tvConditionDetails.setText ("" + FairConditionText);
            tlFair.addView (row);
        }
        for (int i = 0; i < listPoorCondition.size (); i++) {
            String PoorConditionText = listPoorCondition.get (i);
            TableRow row = (TableRow) LayoutInflater.from (ViewFormActivity.this).inflate (R.layout.table_row_condition, null);
            tlPoor.setGravity (Gravity.CENTER_VERTICAL);
            tvConditionDetails = (TextView) row.findViewById (R.id.tvConditionDetails);
            tvConditionDetails.setText ("" + PoorConditionText);
            tlPoor.addView (row);
        }
        tvWorkOrderDescription.setText ("WO#" + Constants.workOrderDetail.getWork_order_id () + " for " + Constants.workOrderDetail.getSite_name ());
    }

    private void initAdapter () {
    }

    @Override
    public void onStart () {
        super.onStart ();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect ();
        Action viewAction = Action.newAction (
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse ("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse ("android-app://com.actiknow.motoraudit/http/host/path")
        );
        AppIndex.AppIndexApi.start (client, viewAction);
    }

    @Override
    public void onStop () {
        super.onStop ();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction (
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse ("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse ("android-app://com.actiknow.motoraudit/http/host/path")
        );
        AppIndex.AppIndexApi.end (client, viewAction);
        client.disconnect ();
    }

    private void getContractSerialsFromServer (final int wo_id) {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.API_URL, true);
            StringRequest strRequest = new StringRequest (Request.Method.POST, AppConfigURL.API_URL,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {


                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {

                                atsSerialList.clear ();
                                engineSerialList.clear ();
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    JSONArray jsonArray = jsonObj.getJSONArray ("CONTRACT_SERIALS");
                                    for (int j = 0; j < jsonArray.length (); j++) {
                                        JSONObject c = jsonArray.getJSONObject (j);
                                        switch (c.getString ("Type")) {
                                            case "ATS":

                                                Serial ATSSerial = new
                                                        Serial (false, c.getInt ("serviceSerials_id"),
                                                        c.getInt ("manufacturer_id"), wo_id, 0, c.getString ("serial"),
                                                        c.getString ("model"), c.getString ("Type"), c.getString ("manufacturer_name"));
                                                atsSerialList.add (ATSSerial);
                                                break;
                                            case "Engine":
                                                Serial engineSerial = new
                                                        Serial (false, c.getInt ("serviceSerials_id"),
                                                        c.getInt ("manufacturer_id"), wo_id, 0, c.getString ("serial"),
                                                        c.getString ("model"), c.getString ("Type"), c.getString ("manufacturer_name"));
                                                engineSerialList.add (engineSerial);
                                                break;
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace ();
                                }
                            } else {
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }
                        }

//                        }
/*

                        Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {

//                                atsSerialList.clear ();
//                                engineSerialList.clear();

                                Constants.workOrderDetail.removeAllSerialInATSList ();
                                Constants.workOrderDetail.removeAllSerialInEngineList ();
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    JSONArray jsonArray = jsonObj.getJSONArray ("CONTRACT_SERIALS");
                                    for (int j = 0; j < jsonArray.length (); j++) {
                                        JSONObject c = jsonArray.getJSONObject (j);
                                        switch (c.getString ("Type")) {
                                            case "ATS":

                                                Serial ATSSerial = new
                                                        Serial (false, c.getInt ("serviceSerials_id"),
                                                        c.getInt ("manufacturer_id"), c.getString ("serial"),
                                                        c.getString ("model"), c.getString ("Type"), c.getString ("manufacturer_name"));
                                                Constants.workOrderDetail.setSerialInATSList (ATSSerial);
//                                                atsSerialList.add (ATSSerial);
                                                break;
                                            case "Engine":
                                                Serial engineSerial = new
                                                        Serial (false, c.getInt ("serviceSerials_id"),
                                                        c.getInt ("manufacturer_id"), c.getString ("serial"),
                                                        c.getString ("model"), c.getString ("Type"), c.getString ("manufacturer_name"));
                                                Constants.workOrderDetail.setSerialInEngineList (engineSerial);
//                                                engineSerialList.add (engineSerial);
                                                break;
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace ();
                                }
                            } else {
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }

//                            for (int i = 0; i < engineSerialList.size (); i++) {
                            for (int i = 0; i < Constants.workOrderDetail.getEngineSerialList ().size (); i++) {
//                                final Serial engineSerial = engineSerialList.get (i);
                                final Serial engineSerial = Constants.workOrderDetail.getEngineSerialList ().get (i);
                                View child = getLayoutInflater ().inflate (R.layout.listview_item_generator_serial, null);

                                TextView tvEngineSerial = (TextView) child.findViewById (R.id.tvGeneratorSerial);
                                TextView tvEngineModel = (TextView) child.findViewById (R.id.tvGeneratorModel);
                                TextView tvEngineManufacturer = (TextView) child.findViewById (R.id.tvGeneratorManufacturer);
                                final CheckBox cbSelected = (CheckBox) child.findViewById (R.id.cbSelected);

                                cbSelected.setVisibility (View.VISIBLE);
                                tvEngineSerial.setText (engineSerial.getSerial_number ());
                                tvEngineModel.setText (engineSerial.getModel_number ());
                                tvEngineManufacturer.setText (engineSerial.getManufacturer_name ());

                                llEngineSerial.addView (child);

//                                if (Constants.workOrderDetail.)

                                cbSelected.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
                                    @Override
                                    public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                                        if (cbSelected.isChecked ()) {
//                                            Constants.workOrderDetail.setSerialInEngineList (engineSerial);
                                        } else {
//                                            Constants.workOrderDetail.removeSerialInEngineList (engineSerial.getSerial_id ());
                                        }
                                    }
                                });
                            }


//                            for (int i = 0; i < atsSerialList.size (); i++) {
                            for (int i = 0; i < Constants.workOrderDetail.getAtsSerialList ().size (); i++) {
//                                final Serial atsSerial = atsSerialList.get (i);
                                final Serial atsSerial = Constants.workOrderDetail.getAtsSerialList ().get (i);
                                View child = getLayoutInflater ().inflate (R.layout.listview_item_generator_serial, null);

                                TextView tvATSSerial = (TextView) child.findViewById (R.id.tvGeneratorSerial);
                                TextView tvATSModel = (TextView) child.findViewById (R.id.tvGeneratorModel);
                                TextView tvATSManufacturer = (TextView) child.findViewById (R.id.tvGeneratorManufacturer);
                                final CheckBox cbSelected = (CheckBox) child.findViewById (R.id.cbSelected);

                                cbSelected.setVisibility (View.VISIBLE);

                                tvATSSerial.setText (atsSerial.getSerial_number ());
                                tvATSModel.setText (atsSerial.getModel_number ());
                                tvATSManufacturer.setText (atsSerial.getManufacturer_name ());

                                llATSSerial.addView (child);


                                cbSelected.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
                                    @Override
                                    public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                                        if (cbSelected.isChecked ()){
                                            Constants.workOrderDetail.
//                                            Constants.workOrderDetail.setSerialInATSList (atsSerial);
                                        } else{
//                                            Constants.workOrderDetail.removeSerialInATSList (atsSerial.getSerial_id ());
                                        }
                                    }
                                });


                            }
                        }


                    */


//                            adapter.notifyDataSetChanged ();
                    },
                    new Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                        }
                    }) {
                @Override
                public byte[] getBody () throws com.android.volley.AuthFailureError {

                    String str = "{\"API_username\":\"" + Constants.api_username + "\",\n" +
                            "\"API_password\":\"" + Constants.api_password + "\",\n" +
                            "\"API_function\":\"getContractSerials\",\n" +
                            "\"API_parameters\":{\"wo_num\" : \"" + wo_id + "\"}}";
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, str, true);
                    return str.getBytes ();
                }

                public String getBodyContentType () {
                    return "application/json; charset=utf-8";
                }
            };
            Utils.sendRequest (strRequest);
        } else {
            Utils.showOkDialog (ViewFormActivity.this, "Seems like there is no internet connection, the app will continue in Offline mode", false);
        }
    }

    private void LoadSerialsInLayout () {
        llEngineSerial.removeAllViews ();
        llATSSerial.removeAllViews ();

        for (int i = 0; i < engineSerialListTemp.size (); i++) {
            final Serial engineSerial = engineSerialListTemp.get (i);
            View child = getLayoutInflater ().inflate (R.layout.listview_item_generator_serial, null);

            TextView tvEngineSerial = (TextView) child.findViewById (R.id.tvGeneratorSerial);
            TextView tvEngineModel = (TextView) child.findViewById (R.id.tvGeneratorModel);
            TextView tvEngineManufacturer = (TextView) child.findViewById (R.id.tvGeneratorManufacturer);
            final CheckBox cbSelected = (CheckBox) child.findViewById (R.id.cbSelected);

            cbSelected.setVisibility (View.GONE);
            tvEngineSerial.setText (engineSerial.getSerial_number ());
            tvEngineModel.setText (engineSerial.getModel_number ());
            tvEngineManufacturer.setText (engineSerial.getManufacturer_name ());

            if (engineSerial.isChecked ()) {
                cbSelected.setChecked (true);
            } else {
                cbSelected.setChecked (false);
            }

            llEngineSerial.addView (child);


            cbSelected.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
                @Override
                public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                    if (cbSelected.isChecked ()) {
                        engineSerial.setChecked (true);
//                                            Constants.workOrderDetail.setSerialInEngineList (engineSerial);
                    } else {
                        engineSerial.setChecked (false);
//                                            Constants.workOrderDetail.removeSerialInEngineList (engineSerial.getSerial_id ());
                    }
                }
            });
        }


        for (int i = 0; i < atsSerialListTemp.size (); i++) {
            final Serial atsSerial = atsSerialListTemp.get (i);
            View child = getLayoutInflater ().inflate (R.layout.listview_item_generator_serial, null);

            TextView tvATSSerial = (TextView) child.findViewById (R.id.tvGeneratorSerial);
            TextView tvATSModel = (TextView) child.findViewById (R.id.tvGeneratorModel);
            TextView tvATSManufacturer = (TextView) child.findViewById (R.id.tvGeneratorManufacturer);
            final CheckBox cbSelected = (CheckBox) child.findViewById (R.id.cbSelected);

            cbSelected.setVisibility (View.GONE);

            tvATSSerial.setText (atsSerial.getSerial_number ());
            tvATSModel.setText (atsSerial.getModel_number ());
            tvATSManufacturer.setText (atsSerial.getManufacturer_name ());
            if (atsSerial.isChecked ()) {
                cbSelected.setChecked (true);
            } else {
                cbSelected.setChecked (false);
            }

            llATSSerial.addView (child);

            cbSelected.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
                @Override
                public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                    if (cbSelected.isChecked ()) {
                        atsSerial.setChecked (true);
//                                            Constants.workOrderDetail.setSerialInATSList (atsSerial);
                    } else {
                        atsSerial.setChecked (false);
//                                            Constants.workOrderDetail.removeSerialInATSList (atsSerial.getSerial_id ());
                    }
                }
            });
        }
    }

    private void getWorkOrderDetailFromServer (final int wo_id, final int generator_serial_id) {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.API_URL, true);
            StringRequest strRequest = new StringRequest (Request.Method.POST, AppConfigURL.API_URL,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    setFormDetails (jsonObj);

                                } catch (JSONException e) {
                                    e.printStackTrace ();
                                }
                            } else {
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }
                        }
                    },
                    new Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                        }
                    }) {
                @Override
                public byte[] getBody () throws com.android.volley.AuthFailureError {

                    String str = "{\"API_username\":\"" + Constants.api_username + "\",\n" +
                            "\"API_password\":\"" + Constants.api_password + "\",\n" +
//                            "\"API_function\":\"getWorkorderForms\",\n" +
//                            "\"API_parameters\":{\"WO_NUM\" : \"" + wo_id + "\"}}";
                            "\"API_function\":\"getServiceForm\",\n" +
                            "\"API_parameters\":{\"WO_NUM\" : " + wo_id + ", \"GEN_SERIALID\" : " + generator_serial_id + "}}";
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, str, true);
                    return str.getBytes ();
                }

                public String getBodyContentType () {
                    return "application/json; charset=utf-8";
                }
            };
            Utils.sendRequest (strRequest);
        } else {
            Utils.showOkDialog (ViewFormActivity.this, "Seems like there is no internet connection, the app will continue in Offline mode", false);
        }
    }

    private void setFormDetails (JSONObject jsonObject) {
        try {
            Constants.workOrderDetail.setForm_id (jsonObject.getInt ("ES_ID"));
            tvTimeIn.setText (jsonObject.getString ("TIME_IN"));
            tvOnSiteContact.setText (jsonObject.getString ("ONSITE_CONTACT"));
            tvEmail.setText (jsonObject.getString ("EMAIL"));
            tvKwRating.setText (jsonObject.getString ("KW_RATING"));
            tvGeneratorCondition.setText (jsonObject.getString ("GEN_STATUS"));
            switch (jsonObject.getString ("GEN_STATUS").toUpperCase ()) {
                case "GOOD":
                    tvGeneratorCondition.setTextColor (getResources ().getColor (R.color.color_pass));
                    break;
                case "FAIR":
                    tvGeneratorCondition.setTextColor (getResources ().getColor (R.color.color_advice));
                    break;
                case "POOR":
                    tvGeneratorCondition.setTextColor (getResources ().getColor (R.color.color_fail));
                    break;
            }
            tvGeneratorConditionComment.setText (jsonObject.getString ("GEN_CONDITION"));
            tvComment.setText (jsonObject.getString ("COMMENTS"));
            tvTimeOut.setText (jsonObject.getString ("TIME_OUT"));

            tvGeneratorModel.setText (Constants.workOrderDetail.getGenerator_model ());
            tvGeneratorSerial.setText (Constants.workOrderDetail.getGenerator_serial ());
            tvGeneratorMake.setText (Constants.workOrderDetail.getGenerator_make_name ());

            JSONArray jsonArrayBeforeImages = jsonObject.getJSONArray ("beforeImages");
            for (int i = 0; i < jsonArrayBeforeImages.length (); i++) {
                try {
                    JSONObject jsonObject1 = jsonArrayBeforeImages.getJSONObject (i);
                    ImageView image = new ImageView (ViewFormActivity.this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (300, 225);
                    params.setMargins (10, 10, 10, 10);
                    image.setLayoutParams (params);
                    llBeforeImage.addView (image);
                    Picasso.with (ViewFormActivity.this).load (jsonObject1.getString ("img_url")).into (image);
                } catch (JSONException e1) {
                    e1.printStackTrace ();
                }
            }

            JSONArray jsonArrayAfterImages = jsonObject.getJSONArray ("afterImages");
            for (int i = 0; i < jsonArrayAfterImages.length (); i++) {
                try {
                    JSONObject jsonObject1 = jsonArrayAfterImages.getJSONObject (i);
                    ImageView image = new ImageView (ViewFormActivity.this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (300, 225);
                    params.setMargins (10, 10, 10, 10);
                    image.setLayoutParams (params);
                    llAfterImage.addView (image);
                    Picasso.with (ViewFormActivity.this).load (jsonObject1.getString ("img_url")).into (image);
                } catch (JSONException e1) {
                    e1.printStackTrace ();
                }
            }

            JSONArray jsonArraySignatures = jsonObject.getJSONArray ("signatures");
            for (int i = 0; i < jsonArraySignatures.length (); i++) {
                try {
                    JSONObject jsonObject1 = jsonArraySignatures.getJSONObject (i);
                    switch (jsonObject1.getString ("sub_categories")) {
                        case "Tech":
                            Picasso.with (ViewFormActivity.this).load (jsonObject1.getString ("signature_url")).into (ivTechSignature);
                            break;
                        case "Customer":
                            tvCustomerName.setText (jsonObject1.getString ("signator"));
                            Picasso.with (ViewFormActivity.this).load (jsonObject1.getString ("signature_url")).into (ivCustomerSignature);
                            break;
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace ();
                }
            }

            JSONArray jsonArraySerials = jsonObject.getJSONArray ("linked_serials");
            for (int i = 0; i < jsonArraySerials.length (); i++) {
                try {
                    JSONObject jsonObject1 = jsonArraySerials.getJSONObject (i);
                    int serial_id = jsonObject1.getInt ("serial_id");

                    Utils.showLog (Log.DEBUG, "LINKED SERIALS", "serial id : " + serial_id, true);


                } catch (JSONException e1) {
                    e1.printStackTrace ();
                }
            }


            for (int j = 0; j < engineSerialList.size (); j++) {
                Utils.showLog (Log.DEBUG, "ENGINE SERIAL", "j : " + j, true);
                Serial engineSerial = engineSerialList.get (j);

                for (int i = 0; i < jsonArraySerials.length (); i++) {
                    try {
                        JSONObject jsonObject1 = jsonArraySerials.getJSONObject (i);
                        int serial_id = jsonObject1.getInt ("serial_id");
                        Utils.showLog (Log.DEBUG, "LINKED SERIALS", "serial id : " + serial_id, true);
                        if (jsonObject1.getInt ("serial_id") == engineSerial.getSerial_id ()) {
                            Utils.showLog (Log.DEBUG, "ENGINE SERIAL", "match found", true);
                            engineSerial.setChecked (false);
                            engineSerialListTemp.add (engineSerial);
//                            engineSerialList.remove (j);
                        } else {
                            Utils.showLog (Log.DEBUG, "ENGINE SERIAL", "match not found", true);
//                            engineSerialList.remove (j);
                            engineSerial.setChecked (true);
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace ();
                    }
                }
            }


            for (int k = 0; k < atsSerialList.size (); k++) {
                Utils.showLog (Log.DEBUG, "ATS SERIAL", "k : " + k, true);
                Serial atsSerial = atsSerialList.get (k);
                for (int i = 0; i < jsonArraySerials.length (); i++) {
                    try {
                        JSONObject jsonObject1 = jsonArraySerials.getJSONObject (i);
                        int serial_id = jsonObject1.getInt ("serial_id");
                        Utils.showLog (Log.DEBUG, "LINKED SERIALS", "serial id : " + serial_id, true);
                        if (jsonObject1.getInt ("serial_id") == atsSerial.getSerial_id ()) {
                            Utils.showLog (Log.DEBUG, "ATS SERIAL", "match found", true);
                            atsSerial.setChecked (false);
                            atsSerialListTemp.add (atsSerial);
//                            engineSerialList.remove (j);
                        } else {
                            Utils.showLog (Log.DEBUG, "ATS SERIAL", "match not found", true);
//                            engineSerialList.remove (j);
                            atsSerial.setChecked (true);
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace ();
                    }
                }
            }


            DateFormat df = new SimpleDateFormat ("yyyyMMdd_HHmmss");
            final String date = df.format (Calendar.getInstance ().getTime ());


            JSONArray jsonArray = jsonObject.getJSONArray ("form_checks");
            for (int i = 0; i < jsonArray.length (); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject (i);
                for (int j = 0; j < Constants.serviceCheckList.size (); j++) {
                    ServiceCheck serviceCheck = Constants.serviceCheckList.get (j);
                    if (jsonObject1.getInt ("smcheck_id") == serviceCheck.getService_check_id ()) {
                        serviceCheck.setComment (jsonObject1.getString ("Comment"));
                        serviceCheck.setSelection_text (jsonObject1.getString ("Status"));
                        switch (jsonObject1.getString ("Status")) {
                            case "NA":
                                serviceCheck.setSelection_text ("NA");
                                serviceCheck.setSelection_flag (0);
                                break;
                            case "N/A":
                                serviceCheck.setSelection_text ("N/A");
                                serviceCheck.setSelection_flag (0);
                                break;
                            case "Pass":
                                serviceCheck.setSelection_text ("Pass");
                                serviceCheck.setSelection_flag (3);
                                break;
                            case "Advise":
                                serviceCheck.setSelection_text ("Advise");
                                serviceCheck.setSelection_flag (2);
                                break;
                            case "Fail":
                                serviceCheck.setSelection_text ("Fail");
                                serviceCheck.setSelection_flag (1);
                                break;
                            case "Yes":
                                serviceCheck.setSelection_text ("Yes");
                                serviceCheck.setSelection_flag (2);
                                break;
                            case "No":
                                serviceCheck.setSelection_text ("No");
                                serviceCheck.setSelection_flag (1);
                                break;
                        }
                        ImageDetail imageDetail;
                        if (jsonObject1.getInt ("Image") != 0) {
                            imageDetail = new ImageDetail (jsonObject1.getInt ("Image"), "", "smcheck_img_" + date, "", "", jsonObject1.getString ("img_url"));
                            serviceCheck.setSmCheckImageInList (imageDetail);
                        } else {
                            serviceCheck.removeSmCheckImageInList (0);
                        }
                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace ();
        }
        LoadSerialsInLayout ();
        new LoadServiceChecksInLinearLayout (this).execute ();
    }

    private class LoadServiceChecksInLinearLayout extends AsyncTask<String, Void, String> {
        Activity activity;

        LoadServiceChecksInLinearLayout (Activity activity) {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute () {
            llFuelSystem.removeAllViews ();
            llPreStartChecksCoolingSystem.removeAllViews ();
            llLubricationSystem.removeAllViews ();
            llAirSystem.removeAllViews ();
            llExhaustSystem.removeAllViews ();
            llGenerator.removeAllViews ();
            llControlPanelCabinets.removeAllViews ();
            llATSMain.removeAllViews ();
            llStartingSystem.removeAllViews ();
            llGeneratorEnclosure.removeAllViews ();
            llStartupAndRunningCheck.removeAllViews ();
            llScheduledMaintenance.removeAllViews ();


            List<ImageDetail> serviceCheckImageDetailList = new ArrayList<> ();


            for (int i = 0; i < Constants.serviceCheckList.size (); i++) {
                final ServiceCheck serviceCheck = Constants.serviceCheckList.get (i);
                View child = getLayoutInflater ().inflate (R.layout.listview_item_service_check_view, null);

                TextView tvCheckHeading = (TextView) child.findViewById (R.id.tvCheckHeading);
                ImageView ivCheckImage = (ImageView) child.findViewById (R.id.ivCheckImage);
                TextView tvCheckStatus = (TextView) child.findViewById (R.id.tvCheckStatus);
                TextView tvCheckComment = (TextView) child.findViewById (R.id.tvCheckComment);

                Spanned text;
                if (serviceCheck.getSub_heading ().length () > 0)
                    text = Html.fromHtml ("<b>" + serviceCheck.getHeading () + "</b> - " + serviceCheck.getSub_heading ());
                else
                    text = Html.fromHtml ("<b>" + serviceCheck.getHeading () + "</b>");

                serviceCheckImageDetailList = serviceCheck.getSmCheckImageList ();

                if (serviceCheckImageDetailList.size () > 0) {
                    ImageDetail imageDetail = serviceCheckImageDetailList.get (0);
                    Picasso.with (ViewFormActivity.this).load (imageDetail.getImage_url ()).into (ivCheckImage);
                    ivCheckImage.setVisibility (View.VISIBLE);
                } else {
                    ivCheckImage.setVisibility (View.GONE);
                }

                tvCheckHeading.setText (text);
                tvCheckComment.setText (serviceCheck.getComment ());
                switch (serviceCheck.getSelection_text ().toUpperCase ()) {
                    case "PASS":
                        tvCheckStatus.setTextColor (getResources ().getColor (R.color.color_pass));
                        break;
                    case "ADVISE":
                        tvCheckStatus.setTextColor (getResources ().getColor (R.color.color_advice));
                        break;
                    case "FAIL":
                        tvCheckStatus.setTextColor (getResources ().getColor (R.color.color_fail));
                        break;
                    case "NA":
                        tvCheckStatus.setTextColor (getResources ().getColor (R.color.color_na));
                        break;
                    case "N/A":
                        tvCheckStatus.setTextColor (getResources ().getColor (R.color.color_na));
                        break;
                    case "YES":
                        tvCheckStatus.setTextColor (getResources ().getColor (R.color.color_pass));
                        break;
                    case "NO":
                        tvCheckStatus.setTextColor (getResources ().getColor (R.color.color_fail));
                        break;
                }
                tvCheckStatus.setText (serviceCheck.getSelection_text ());

                switch (serviceCheck.getGroup_id ()) {
                    case 1:
                        llFuelSystem.addView (child);
                        break;
                    case 2:
                        llPreStartChecksCoolingSystem.addView (child);
                        break;
                    case 3:
                        llLubricationSystem.addView (child);
                        break;
                    case 4:
                        llAirSystem.addView (child);
                        break;
                    case 5:
                        llExhaustSystem.addView (child);
                        break;
                    case 6:
                        llGenerator.addView (child);
                        break;
                    case 7:
                        llControlPanelCabinets.addView (child);
                        break;
                    case 8:
                        llATSMain.addView (child);
                        break;
                    case 9:
                        llStartingSystem.addView (child);
                        break;
                    case 10:
                        llGeneratorEnclosure.addView (child);
                        break;
                    case 11:
                        llStartupAndRunningCheck.addView (child);
                        break;
                    case 12:
                        llScheduledMaintenance.addView (child);
                        break;
                }
            }
        }

        @Override
        protected String doInBackground (String... params) {
            return "Executed";
        }

        @Override
        protected void onPostExecute (String result) {

            Utils.hideSoftKeyboard (activity);

            pDialog.dismiss ();

        }
    }
}
