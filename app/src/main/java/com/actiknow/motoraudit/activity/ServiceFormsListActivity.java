package com.actiknow.motoraudit.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.actiknow.motoraudit.R;
import com.actiknow.motoraudit.adapter.GeneratorSerialAdapter;
import com.actiknow.motoraudit.adapter.ManufacturerAdapter;
import com.actiknow.motoraudit.helper.DatabaseHandler;
import com.actiknow.motoraudit.model.Serial;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServiceFormsListActivity extends AppCompatActivity {

    GoogleApiClient client;
    //    int wo_id, wo_contract_num;
    String wo_site_name;
    ProgressBar progressBar;
    TextView tvNoSerialFound;
    TextView tvAddSerial;

    Dialog dialogEnterManually;

    Dialog dialogAddNewSerial;
    ProgressDialog pDialog;
    ListView lvGeneratorSerial;
    ManufacturerAdapter manufacturerAdapter;
    DatabaseHandler db;
    private List<Serial> generatorSerialList = new ArrayList<> ();
    private GeneratorSerialAdapter adapter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_service_forms_list);
        initView ();
        initData ();
        initListener ();
        initAdapter ();
        getContractSerialsFromServer (Constants.workOrderDetail.getWork_order_id ());
        db.closeDB ();

        Runtime rt = Runtime.getRuntime ();
        long maxMemory = rt.maxMemory ();
    }

    private void initView () {
        lvGeneratorSerial = (ListView) findViewById (R.id.lvGeneratorSerial);
        progressBar = (ProgressBar) findViewById (R.id.progressbar);
        tvNoSerialFound = (TextView) findViewById (R.id.tvNoSerialFound);
        tvAddSerial = (TextView) findViewById (R.id.tvAddSerial);
    }

    private void initListener () {
        tvAddSerial.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    tvAddSerial.setBackgroundResource (R.color.background_button_green_pressed);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    tvAddSerial.setBackgroundResource (R.color.background_button_green);
                    if (NetworkConnection.isNetworkAvailable (ServiceFormsListActivity.this)) {
                        showSaveSerialDialog (0, Constants.workOrderDetail.getContract_number (), false);
                    } else {
                        Utils.showOkDialog (ServiceFormsListActivity.this, "Sorry you cannot add a new serial in Offline mode", false);
                    }
                }
                return true;
            }
        });
    }

    private void initData () {
        db = new DatabaseHandler (getApplicationContext ());
        //        Intent intent = getIntent ();
//        wo_id = intent.getIntExtra ("wo_id", 0);
        //    Utils.setTypefaceToAllViews (this, tvNoInternetConnection);
        client = new GoogleApiClient.Builder (this).addApi (AppIndex.API).build ();
    }

    private void initAdapter () {
        adapter = new GeneratorSerialAdapter (this, generatorSerialList);
        lvGeneratorSerial.setAdapter (adapter);
    }

    private void showSaveSerialDialog (final int serial_id, final int contract_num, final boolean offline) {
        TextView tvSerialHeading;
        TextView tvSerialId;
        final EditText etSerialId;
        final EditText etSerial;
        final EditText etModel;
        final Spinner spManufacturer;
        TextView tvAddSerial;
        TextView tvCancelSerial;

        final Serial generatorSerial = new Serial (false, serial_id, 0, 0, 0, "", "", "", "");

        dialogAddNewSerial = new Dialog (ServiceFormsListActivity.this);
        dialogAddNewSerial.setContentView (R.layout.dialog_add_generator_serial);
        dialogAddNewSerial.setCancelable (false);

        tvSerialHeading = (TextView) dialogAddNewSerial.findViewById (R.id.tvSerialHeading);
        tvSerialId = (TextView) dialogAddNewSerial.findViewById (R.id.tvSerialId);
        etSerialId = (EditText) dialogAddNewSerial.findViewById (R.id.etSerialId);
        etSerial = (EditText) dialogAddNewSerial.findViewById (R.id.etSerial);
        etModel = (EditText) dialogAddNewSerial.findViewById (R.id.etModel);
        spManufacturer = (Spinner) dialogAddNewSerial.findViewById (R.id.spManufacturer);
        tvAddSerial = (TextView) dialogAddNewSerial.findViewById (R.id.tvAddSerial);
        tvCancelSerial = (TextView) dialogAddNewSerial.findViewById (R.id.tvCancelSerial);

        dialogAddNewSerial.getWindow ().setBackgroundDrawable (new ColorDrawable (android.graphics.Color.TRANSPARENT));
        dialogAddNewSerial.show ();


        if (offline) {
            tvSerialHeading.setText ("ENTER THE SERIAL DETAILS");
            tvSerialId.setVisibility (View.VISIBLE);
            etSerialId.setVisibility (View.VISIBLE);
            tvAddSerial.setText ("CONTINUE");
        } else {
            tvSerialHeading.setText ("ADD NEW SERIAL");
            tvSerialId.setVisibility (View.GONE);
            etSerialId.setVisibility (View.GONE);
            tvAddSerial.setText ("ADD");
        }


        manufacturerAdapter = new ManufacturerAdapter (ServiceFormsListActivity.this, R.layout.spinner_item, Constants.manufacturerList);
        spManufacturer.setAdapter (manufacturerAdapter);

        tvCancelSerial.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (offline) {
                    Utils.showOkDialog (ServiceFormsListActivity.this, "Sorry Generator Serial ID is required to create a new form", true);
                } else {
                    dialogAddNewSerial.dismiss ();
                }
            }
        });
        tvAddSerial.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                if (etSerial.getText ().toString ().length () == 0) {
                    etSerial.setError ("Please enter Serial number");
                    Utils.shakeView (ServiceFormsListActivity.this, etSerial);
                } else if (etModel.getText ().toString ().length () == 0) {
                    etModel.setError ("Please enter Model number");
                    Utils.shakeView (ServiceFormsListActivity.this, etModel);
                } else if (generatorSerial.getManufacturer_id () == 0) {
                    Utils.showToast (ServiceFormsListActivity.this, "Please select Manufacturer");
                } else {
                    if (offline) {
                        if (etSerialId.getText ().toString ().length () == 0) {
                            Utils.showToast (ServiceFormsListActivity.this, "Please enter Serial ID");
                        } else {
                            generatorSerial.setModel_number (etModel.getText ().toString ());
                            generatorSerial.setSerial_number (etSerial.getText ().toString ());
                            generatorSerial.setSerial_id (Integer.parseInt (etSerialId.getText ().toString ()));
                            Constants.workOrderDetail.setGenerator_model (generatorSerial.getModel_number ());
                            Constants.workOrderDetail.setGenerator_serial (generatorSerial.getSerial_number ());
                            Constants.workOrderDetail.setGenerator_make_id (generatorSerial.getManufacturer_id ());
                            Constants.workOrderDetail.setGenerator_make_name (generatorSerial.getManufacturer_name ());
                            Constants.workOrderDetail.setGenerator_serial_id (generatorSerial.getSerial_id ());

                            Intent intent = new Intent (ServiceFormsListActivity.this, DetailActivity.class);
                            startActivity (intent);

                        }
                    } else {
                        generatorSerial.setModel_number (etModel.getText ().toString ());
                        generatorSerial.setSerial_number (etSerial.getText ().toString ());
                        generatorSerial.setSerial_id (serial_id);

                        Utils.showLog (Log.INFO, "MANUFACTURER ID", "" + generatorSerial.getManufacturer_id (), true);
                        Utils.showLog (Log.INFO, "MANUFACTURER NAME", generatorSerial.getManufacturer_name (), true);
                        Utils.showLog (Log.INFO, "MODEL NUMBER", generatorSerial.getModel_number (), true);
                        Utils.showLog (Log.INFO, "SERIAL NUMBER", generatorSerial.getSerial_number (), true);
                        Utils.showLog (Log.INFO, "SERIAL ID", "" + generatorSerial.getSerial_id (), true);
                        Utils.showLog (Log.INFO, "CONTRACT NUMBER", "" + contract_num, true);

                        dialogAddNewSerial.dismiss ();
                        pDialog = new ProgressDialog (ServiceFormsListActivity.this);
                        Utils.showProgressDialog (pDialog, null);
                        saveSerial (contract_num, generatorSerial.getSerial_number (),
                                generatorSerial.getModel_number (), generatorSerial.getManufacturer_id ());
                    }
                }
            }
        });
        spManufacturer.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected (AdapterView<?> parentView, View v, int position, long id) {
                generatorSerial.setManufacturer_id ((Integer.parseInt (((TextView) v.findViewById (R.id.tvManufacturerID)).getText ().toString ())));
                generatorSerial.setManufacturer_name ((((TextView) v.findViewById (R.id.tvManufacturerName)).getText ().toString ()));
                Utils.showLog (Log.INFO, "MANUFACTURER ID", "" + generatorSerial.getManufacturer_id (), true);
                Utils.showLog (Log.INFO, "MANUFACTURER NAME", generatorSerial.getManufacturer_name (), true);
            }

            @Override
            public void onNothingSelected (AdapterView<?> parentView) {
            }
        });
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

    public void saveSerial (final int contract_num, final String serial_number, final String model_number, final int manufacturer_id) {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.API_URL, true);
            StringRequest strRequest = new StringRequest (Request.Method.POST, AppConfigURL.API_URL,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            pDialog.dismiss ();
                            if (response != null) {
                                Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    String serviceSerial_id = jsonObj.getString ("serviceSerial_id");
                                    String message = jsonObj.getString ("error_message");
                                    int error_code = jsonObj.getInt ("error_code");
                                    if (error_code != 0)
                                        Utils.showToast (ServiceFormsListActivity.this, message);
                                } catch (JSONException e) {
                                    e.printStackTrace ();
                                }
                                getContractSerialsFromServer (Constants.workOrderDetail.getWork_order_id ());
                            } else {
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }
                        }
                    },
                    new Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            pDialog.dismiss ();
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                        }
                    }) {
                @Override
                public byte[] getBody () throws com.android.volley.AuthFailureError {

                    String str = "{\"API_username\":\"" + Constants.api_username + "\",\n" +
                            "\"API_password\":\"" + Constants.api_password + "\",\n" +
                            "\"API_function\":\"createNewSerial\",\n" +
                            "\"API_parameters\":{\"contractNum\": \"" + contract_num + "\", \"serialNumber\": \"" + serial_number + "\", \"modelNumber\": \"" + model_number + "\", \"manufacturer\": \"" + manufacturer_id + "\"}}";
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, str, true);
                    return str.getBytes ();
                }

                public String getBodyContentType () {
                    return "application/json; charset=utf-8";
                }
            };
            Utils.sendRequest (strRequest, 30);
        } else {
            Utils.showOkDialog (ServiceFormsListActivity.this, "Seems like there is no internet connection, the app will continue in Offline mode", false);
        }
    }

    private void getContractSerialsFromServer (final int wo_id) {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.API_URL, true);
            StringRequest strRequest = new StringRequest (Request.Method.POST, AppConfigURL.API_URL,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            progressBar.setVisibility (View.VISIBLE);
                            int is_data_received = 0;
                            int json_array_len = 0;
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                generatorSerialList.clear ();
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    JSONArray jsonArray = jsonObj.getJSONArray ("CONTRACT_SERIALS");
                                    json_array_len = jsonArray.length ();
                                    for (int j = 0; j < jsonArray.length (); j++) {
                                        JSONObject c = jsonArray.getJSONObject (j);
                                        if (c.getString ("Type").equalsIgnoreCase ("Generator") || c.getString ("Type").equalsIgnoreCase ("")) {
                                            is_data_received = 1;
                                            Serial generatorSerial = new
                                                    Serial (false, c.getInt ("serviceSerials_id"),
                                                    c.getInt ("manufacturer_id"), wo_id, 0, c.getString ("serial"),
                                                    c.getString ("model"), c.getString ("Type"), c.getString ("manufacturer_name"));
                                            generatorSerialList.add (generatorSerial);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace ();
                                }
                            } else {
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }

                            adapter.notifyDataSetChanged ();
                            if (is_data_received != 0 && json_array_len != 0) {
                                progressBar.setVisibility (View.GONE);
                                lvGeneratorSerial.setVisibility (View.VISIBLE);
                                tvNoSerialFound.setVisibility (View.GONE);
                            } else if (is_data_received != 0 && json_array_len == 0) {
                                tvNoSerialFound.setVisibility (View.VISIBLE);
                                progressBar.setVisibility (View.GONE);
                                lvGeneratorSerial.setVisibility (View.GONE);
                            }
                            if (is_data_received == 0) {
                                progressBar.setVisibility (View.GONE);
                                lvGeneratorSerial.setVisibility (View.GONE);
                                tvNoSerialFound.setVisibility (View.VISIBLE);
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
                            "\"API_function\":\"getContractSerials\",\n" +
                            "\"API_parameters\":{\"wo_num\" : \"" + wo_id + "\"}}";
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, str, true);
                    return str.getBytes ();
                }

                public String getBodyContentType () {
                    return "application/json; charset=utf-8";
                }
            };
            Utils.sendRequest (strRequest, 30);
        } else {
//            showSaveSerialDialog (0, Constants.workOrderDetail.getContract_number (), true);

            progressBar.setVisibility (View.GONE);
            getGeneratorSerialsFromLocalDatabase (wo_id);


            //        Utils.showOkDialog (ServiceFormsListActivity.this, "Seems like there is no internet connection, the app will continue in Offline mode", false);
        }
    }

    private void getGeneratorSerialsFromLocalDatabase (int wo_id) {
        Utils.showLog (Log.DEBUG, AppConfigTags.TAG, "Getting all the contract serials from local database for wo id = " + wo_id, true);
        generatorSerialList.clear ();


        List<Serial> allContractSerials = db.getAllContractSerials (wo_id);

        tvNoSerialFound.setVisibility (View.VISIBLE);
        lvGeneratorSerial.setVisibility (View.GONE);

        for (Serial contractSerial : allContractSerials) {
            if (contractSerial.getSerial_type ().equalsIgnoreCase ("Generator") || contractSerial.getSerial_type ().equalsIgnoreCase ("")) {
                generatorSerialList.add (contractSerial);
                tvNoSerialFound.setVisibility (View.GONE);
                lvGeneratorSerial.setVisibility (View.VISIBLE);
            }
        }
        adapter.notifyDataSetChanged ();
    }

}