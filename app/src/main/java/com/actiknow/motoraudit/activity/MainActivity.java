package com.actiknow.motoraudit.activity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actiknow.motoraudit.R;
import com.actiknow.motoraudit.adapter.AllWorkOrdersAdapter;
import com.actiknow.motoraudit.helper.DatabaseHandler;
import com.actiknow.motoraudit.model.Manufacturer;
import com.actiknow.motoraudit.model.Serial;
import com.actiknow.motoraudit.model.ServiceCheck;
import com.actiknow.motoraudit.model.WorkOrder;
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

public class MainActivity extends AppCompatActivity {

    TextView tvNoInternetConnection;
    ProgressBar progressBar;
    ListView lvAllWorkOrder;
    GoogleApiClient client;
    DatabaseHandler db;

    // Action Bar components
    ProgressDialog progressDialog;
    private AllWorkOrdersAdapter adapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RelativeLayout mDrawerPanel;
    private List<WorkOrder> workOrderList = new ArrayList<> ();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        initView ();
        initData ();
        initListener ();
        getWorkOrderListFromServer (false);
        getManufacturerListFromServer (false);
        setServiceCheckList (false);
        setUpNavigationDrawer ();
        db.closeDB ();
    }

    private void initView () {
        lvAllWorkOrder = (ListView) findViewById (R.id.lvJobsList);
        tvNoInternetConnection = (TextView) findViewById (R.id.tvNoIternetConnection);
        progressBar = (ProgressBar) findViewById (R.id.progressbar);
    }

    private void initListener () {
    }

    private void initData () {
        db = new DatabaseHandler (getApplicationContext ());
        Utils.setTypefaceToAllViews (this, tvNoInternetConnection);
        adapter = new AllWorkOrdersAdapter (this, workOrderList);
        lvAllWorkOrder.setAdapter (adapter);
        client = new GoogleApiClient.Builder (this).addApi (AppIndex.API).build ();
    }

    private void setUpNavigationDrawer () {
        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar1);
        setSupportActionBar (toolbar);
        ActionBar actionBar = getSupportActionBar ();
        try {
            assert actionBar != null;
            actionBar.setDisplayHomeAsUpEnabled (false);
            actionBar.setHomeButtonEnabled (false);
            actionBar.setTitle ("Motor Audit");

            actionBar.setDisplayShowTitleEnabled (true);
        } catch (Exception ignored) {
        }

        /*
        ListView mDrawerListView = (ListView) findViewById (R.id.navDrawerList);
        mDrawerPanel = (RelativeLayout) findViewById (R.id.navDrawerPanel);
        mDrawerLayout = (DrawerLayout) findViewById (R.id.drawer_layout);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<> (this, android.R.layout.simple_list_item_1, getResources ().getStringArray (R.array.menulist));
        mDrawerListView.setAdapter (new NavDrawerAdapter (this, getResources ().getStringArray (R.array.menulist)));
        mDrawerListView.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent1 = new Intent (MainActivity.this, MyRequestsActivity.class);
                        startActivity (intent1);
                        overridePendingTransition (R.anim.slide_in, R.anim.slide_nothing);
                        break;
                    case 1:
                        Intent intent3 = new Intent (Intent.ACTION_DIAL);
                        intent3.setData (Uri.parse ("tel:+919643108086"));
                        startActivity (intent3);
                        overridePendingTransition (R.anim.slide_in, R.anim.slide_nothing);
                        break;
                    case 2:
                        Intent intent4 = new Intent (MainActivity.this, AboutUsActivity.class);
                        startActivity (intent4);
                        overridePendingTransition (R.anim.slide_in, R.anim.slide_nothing);
                        break;
                }
                mDrawerLayout.closeDrawer (mDrawerPanel);
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle (this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened (View drawerView) {
                super.onDrawerOpened (drawerView);
                //getSupportActionBar().setTitle(getString(R.string.drawer_opened));
                invalidateOptionsMenu ();
            }

            public void onDrawerClosed (View view) {
                super.onDrawerClosed (view);
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu ();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled (true);
        mDrawerLayout.setDrawerListener (mDrawerToggle);
*/
    }

    private void getWorkOrderListFromServer (final boolean sync) {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.API_URL, true);
            StringRequest strRequest = new StringRequest (Request.Method.POST, AppConfigURL.API_URL,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            int is_data_received = 0;
                            int json_array_len = 0;
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                is_data_received = 1;
                                try {
                                    if (sync) {
                                        db.deleteAllWorkorders ();
                                        db.deleteAllContractSerials ();
                                    } else {
                                    }
                                    JSONObject jsonObj = new JSONObject (response);
                                    JSONArray jsonArray = jsonObj.getJSONArray (AppConfigTags.WORKORDERS);
                                    json_array_len = jsonArray.length ();
                                    for (int i = 0; i < json_array_len; i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject (i);
                                        WorkOrder workOrder = new WorkOrder ();
                                        workOrder.setWo_id (jsonObject.getInt (AppConfigTags.WO_ID));
                                        workOrder.setWo_site_name (jsonObject.getString (AppConfigTags.WO_SITE_NAME));
                                        workOrder.setWo_contract_num (jsonObject.getInt (AppConfigTags.WO_CONTRACT_NUM));
                                        workOrder.setWo_customer_name (jsonObject.getString (AppConfigTags.WO_CUSTOMER_NAME));
                                        if (sync) {
                                            db.createWorkorder (workOrder);
                                            JSONArray jsonArrayContractSerial = jsonObject.getJSONArray ("CONTRACT_SERIALS");
                                            for (int j = 0; j < jsonArrayContractSerial.length (); j++) {
                                                JSONObject c = jsonArrayContractSerial.getJSONObject (j);
                                                Serial contractSerial = new Serial (false, c.getInt ("serviceSerials_id"),
                                                        c.getInt ("manufacturer_id"), jsonObject.getInt (AppConfigTags.WO_ID),
                                                        c.getString ("serial"), c.getString ("model"), c.getString ("Type"),
                                                        Utils.getManufacturerName (c.getInt ("manufacturer_id")));
//                                                Serial contractSerial = new Serial (false, c.getInt ("serviceSerials_id"),
//                                                        c.getInt ("manufacturer_id"), jsonObject.getInt (AppConfigTags.WO_ID),
//                                                        c.getString ("serial"), c.getString ("model"), c.getString ("Type"),
//                                                        c.getString ("manufacturer_name"));
                                                db.createContractSerial (contractSerial);
                                            }
                                        } else {
                                            workOrderList.add (workOrder);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace ();
                                }

                                if (sync) {
                                    progressDialog.dismiss ();
                                    Utils.showOkDialog (MainActivity.this, "All Data have been successfully synced from the server", false);
                                } else {
                                    getWorkOrderListFromLocalDatabase ();
                                }

                            } else {
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                                if (sync) {
                                    progressDialog.dismiss ();
                                    Utils.showOkDialog (MainActivity.this, "An error occurred while syncing", false);
                                } else {
                                    getWorkOrderListFromLocalDatabase ();
                                }
                            }
                            if (sync) {
                                progressDialog.dismiss ();
                            } else {
                                adapter.notifyDataSetChanged ();
                                if (is_data_received != 0 && json_array_len != 0) {
                                    progressBar.setVisibility (View.GONE);
                                    lvAllWorkOrder.setVisibility (View.VISIBLE);
                                    tvNoInternetConnection.setVisibility (View.GONE);
                                } else if (is_data_received != 0 && json_array_len == 0) {
                                    tvNoInternetConnection.setVisibility (View.VISIBLE);
                                    progressBar.setVisibility (View.GONE);
                                    lvAllWorkOrder.setVisibility (View.GONE);
                                }
                                if (is_data_received == 0) {
                                    progressBar.setVisibility (View.GONE);
                                    lvAllWorkOrder.setVisibility (View.GONE);
                                    tvNoInternetConnection.setVisibility (View.VISIBLE);
                                }
                            }
                        }
                    },
                    new Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                            progressBar.setVisibility (View.GONE);
                            lvAllWorkOrder.setVisibility (View.VISIBLE);
                            if (sync) {
                                progressDialog.dismiss ();
                            } else {
                                getWorkOrderListFromLocalDatabase ();
                            }
                        }
                    }) {
                @Override
                public byte[] getBody () throws com.android.volley.AuthFailureError {

                    String str = "{\"API_username\":\"" + Constants.api_username + "\",\n" +
                            "\"API_password\":\"" + Constants.api_password + "\",\n" +
                            "\"API_function\":\"getWorkorderList\",\n" +
                            "\"API_parameters\":{}}";
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, str, true);
                    return str.getBytes ();
                }

                public String getBodyContentType () {
                    return "application/json; charset=utf-8";
                }
            };
            Utils.sendRequest (strRequest);
        } else {
            progressBar.setVisibility (View.GONE);
            lvAllWorkOrder.setVisibility (View.VISIBLE);
            if (sync) {
                progressDialog.dismiss ();
                Utils.showOkDialog (MainActivity.this, "Seems like there is no internet connection, Data can't be synced", false);
            } else {
                Utils.showOkDialog (MainActivity.this, "Seems like there is no internet connection, the app will continue in Offline mode", false);
                getWorkOrderListFromLocalDatabase ();
            }
        }
    }

    private void getManufacturerListFromServer (final boolean sync) {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.API_URL, true);
            StringRequest strRequest = new StringRequest (Request.Method.POST, AppConfigURL.API_URL,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            int json_array_len = 0;
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                if (sync) {
                                    db.deleteAllManufacturer ();
                                } else {
                                    Constants.manufacturerList.clear ();
                                }
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    JSONArray jsonArray = jsonObj.getJSONArray (AppConfigTags.MANUFACTURERS);
                                    json_array_len = jsonArray.length ();
                                    for (int i = 0; i < json_array_len; i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject (i);
                                        Manufacturer manufacturer = new Manufacturer ();
                                        manufacturer.setManufacturer_id (jsonObject.getInt (AppConfigTags.MANUFACTURER_ID));
                                        manufacturer.setManufacturer_name (jsonObject.getString (AppConfigTags.MANUFACTURER_NAME));
                                        if (sync) {
                                            db.createManufacturer (manufacturer);
                                        } else {
                                            Constants.manufacturerList.add (manufacturer);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace ();
                                }
                            } else {
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                                if (sync) {
                                } else {
                                    getManufacturerListFromLocalDatabase ();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                            if (sync) {
                            } else {
                                getManufacturerListFromLocalDatabase ();
                            }
                        }
                    }) {
                @Override
                public byte[] getBody () throws com.android.volley.AuthFailureError {

                    String str = "{\"API_username\":\"" + Constants.api_username + "\",\n" +
                            "\"API_password\":\"" + Constants.api_password + "\",\n" +
                            "\"API_function\":\"getManufacturers\",\n" +
                            "\"API_parameters\":{}}";
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, str, true);
                    return str.getBytes ();
                }

                public String getBodyContentType () {
                    return "application/json; charset=utf-8";
                }
            };
            Utils.sendRequest (strRequest);
        } else {
            if (sync) {
            } else {
                getManufacturerListFromLocalDatabase ();
            }
            //           Utils.showOkDialog (MainActivity.this, "Seems like there is no internet connection, the app will continue in Offline mode", false);
        }
    }

    private void setServiceCheckListOld () {
        String service_check_json = Utils.getServiceCheckJSONFromAsset (this);
        Utils.showLog (Log.INFO, "Service Check JSON", service_check_json, false);
        try {
            JSONObject jsonObj = new JSONObject (service_check_json);
            JSONArray jsonArray = jsonObj.getJSONArray (AppConfigTags.SERVICE_CHECK);
            for (int i = 0; i < jsonArray.length (); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject (i);
                ServiceCheck serviceCheck = new ServiceCheck ();
                serviceCheck.setHeading (jsonObject.getString (AppConfigTags.HEADING));
                serviceCheck.setSub_heading (jsonObject.getString (AppConfigTags.SUB_HEADING));
//                serviceCheck.setImage_str (jsonObject.getString (AppConfigTags.IMAGE));
                serviceCheck.setComment (jsonObject.getString (AppConfigTags.COMMENT));
                serviceCheck.setSelection_text (jsonObject.getString (AppConfigTags.SELECTION_TEXT));
                serviceCheck.setGroup_name (jsonObject.getString (AppConfigTags.GROUP_NAME));
                serviceCheck.setGroup_id (jsonObject.getInt (AppConfigTags.GROUP_ID));
                serviceCheck.setService_check_id (jsonObject.getInt (AppConfigTags.SERVICE_CHECK_ID));
                serviceCheck.setSelection_flag (jsonObject.getInt (AppConfigTags.SELECTION_FLAG));
                serviceCheck.setComment_required (jsonObject.getBoolean (AppConfigTags.COMMENT_REQUIRED));

                Constants.serviceCheckList.add (serviceCheck);
            }
        } catch (JSONException e) {
            e.printStackTrace ();
            Utils.showLog (Log.ERROR, "JSON Exception", e.getMessage (), true);
        }

        /*
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.API_URL, true);
            StringRequest strRequest = new StringRequest (Request.Method.POST, AppConfigURL.API_URL,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            int json_array_len = 0;
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                Constants.manufacturerList.clear ();
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    JSONArray jsonArray = jsonObj.getJSONArray (AppConfigTags.MANUFACTURERS);
                                    json_array_len = jsonArray.length ();
                                    for (int i = 0; i < json_array_len; i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject (i);
                                        Manufacturer manufacturer = new Manufacturer ();
                                        manufacturer.setManufacturer_id (jsonObject.getInt (AppConfigTags.MANUFACTURER_ID));
                                        manufacturer.setManufacturer_name (jsonObject.getString (AppConfigTags.MANUFACTURER_NAME));
                                        Constants.manufacturerList.add (manufacturer);
                                    }
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
                            "\"API_function\":\"getManufacturers\",\n" +
                            "\"API_parameters\":{}}";
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, str, true);
                    return str.getBytes ();
                }

                public String getBodyContentType () {
                    return "application/json; charset=utf-8";
                }
            };
            Utils.sendRequest (strRequest);
        } else {
            //           Utils.showOkDialog (MainActivity.this, "Seems like there is no internet connection, the app will continue in Offline mode", false);
        }
*/
    }

    private void setServiceCheckList (final boolean sync) {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.API_URL, true);
            StringRequest strRequest = new StringRequest (Request.Method.POST, AppConfigURL.API_URL,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    if (sync) {
                                        db.deleteAllServiceChecks ();
                                    } else {
                                        Constants.serviceCheckList.clear ();
                                    }
                                    JSONObject jsonObj = new JSONObject (response);
                                    JSONArray jsonArray = jsonObj.getJSONArray ("checks");
                                    for (int i = 0; i < jsonArray.length (); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject (i);
                                        ServiceCheck serviceCheck = new ServiceCheck ();

                                        serviceCheck.setHeading (jsonObject.getString ("Name"));
                                        serviceCheck.setSub_heading (jsonObject.getString ("Name_description"));
                                        serviceCheck.setComment ("");
                                        serviceCheck.setSelection_text ("N/A");
//                                        serviceCheck.setGroup_name (jsonObject.getString (AppConfigTags.GROUP_NAME));
//                                        serviceCheck.setGroup_id (jsonObject.getInt (AppConfigTags.GROUP_ID));
                                        serviceCheck.setService_check_id (jsonObject.getInt ("id"));
                                        serviceCheck.setSelection_flag (0);
                                        serviceCheck.setComment_required (false);
                                        switch (jsonObject.getInt ("pass_required")) {
                                            case 0:
                                                serviceCheck.setPass_required (false);
                                                break;
                                            case 1:
                                                serviceCheck.setPass_required (true);
                                                break;
                                        }

                                        if (sync) {
                                            db.createServiceCheck (serviceCheck);
                                        } else {
                                            Constants.serviceCheckList.add (serviceCheck);
                                        }
                                    }

                                    JSONArray jsonArray2 = jsonObj.getJSONArray ("checking");
                                    for (int i = 0; i < jsonArray2.length (); i++) {
                                        JSONObject jsonObject2 = jsonArray2.getJSONObject (i);

                                        for (int j = 0; j < Constants.serviceCheckList.size (); j++) {
                                            ServiceCheck serviceCheck = Constants.serviceCheckList.get (j);
                                            if (serviceCheck.getService_check_id () == jsonObject2.getInt ("check_id")) {
                                                serviceCheck.setGroup_id (jsonObject2.getInt ("group_id"));
                                            }
                                        }
                                    }

                                    JSONArray jsonArray3 = jsonObj.getJSONArray ("checkgroups");
                                    for (int i = 0; i < jsonArray3.length (); i++) {
                                        JSONObject jsonObject3 = jsonArray3.getJSONObject (i);

                                        for (int j = 0; j < Constants.serviceCheckList.size (); j++) {
                                            ServiceCheck serviceCheck = Constants.serviceCheckList.get (j);
                                            if (serviceCheck.getGroup_id () == jsonObject3.getInt ("group_id")) {
                                                serviceCheck.setGroup_name (jsonObject3.getString ("group_name"));
                                                switch (jsonObject3.getString ("check_type")) {
                                                    case "PAF-NA":
                                                        serviceCheck.setGroup_type (0);
                                                        break;
                                                    case "YN-NA":
                                                        serviceCheck.setGroup_type (1);
                                                }
                                            }
                                        }
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace ();
                                }
                            } else {
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                                if (sync) {
                                } else {
                                    getServiceCheckListFromLocalDatabase ();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                            if (sync) {
                            } else {
                                getServiceCheckListFromLocalDatabase ();
                            }
                        }
                    }) {
                @Override
                public byte[] getBody () throws com.android.volley.AuthFailureError {
                    String str = "{\"API_username\":\"" + Constants.api_username + "\",\n" +
                            "\"API_password\":\"" + Constants.api_password + "\",\n" +
                            "\"API_function\":\"getChecksInfo\",\n" +
                            "\"API_parameters\":{}}";
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, str, true);
                    return str.getBytes ();
                }

                public String getBodyContentType () {
                    return "application/json; charset=utf-8";
                }
            };
            Utils.sendRequest (strRequest);
        } else {
            if (sync) {
            } else {
                getServiceCheckListFromLocalDatabase ();
            }
        }
    }


    private void getManufacturerListFromLocalDatabase () {
        Utils.showLog (Log.DEBUG, AppConfigTags.TAG, "Getting all the manufacturers from local database", true);
        Constants.manufacturerList.clear ();
        List<Manufacturer> allManufacturers = db.getAllManufacturers ();
        for (Manufacturer manufacturer : allManufacturers)
            Constants.manufacturerList.add (manufacturer);
    }

    private void getServiceCheckListFromLocalDatabase () {
        Utils.showLog (Log.DEBUG, AppConfigTags.TAG, "Getting all the service checks from local database", true);
        Constants.serviceCheckList.clear ();
        List<ServiceCheck> allServiceChecks = db.getAllServiceChecks ();
        for (ServiceCheck serviceCheck : allServiceChecks)
            Constants.serviceCheckList.add (serviceCheck);
    }

    private void getWorkOrderListFromLocalDatabase () {
        Utils.showLog (Log.DEBUG, AppConfigTags.TAG, "Getting all the workorder from local database", true);
        workOrderList.clear ();
        List<WorkOrder> allWorkOrders = db.getAllWorkorders ();
        for (WorkOrder workOrder : allWorkOrders)
            workOrderList.add (workOrder);
        adapter.notifyDataSetChanged ();
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

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater ().inflate (R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId ()) {
            case R.id.action_sync:
                sync ();
                return true;
        }
        Utils.hideSoftKeyboard (MainActivity.this);
        return super.onOptionsItemSelected (item);
    }

    private void sync () {
        progressDialog = new ProgressDialog (this);
        Utils.showProgressDialog (progressDialog, null);
        getWorkOrderListFromServer (true);
        getManufacturerListFromServer (true);
        setServiceCheckList (true);
    }

}
