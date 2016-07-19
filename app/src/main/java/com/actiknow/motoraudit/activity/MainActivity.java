package com.actiknow.motoraudit.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actiknow.motoraudit.R;
import com.actiknow.motoraudit.adapter.AllWorkOrdersAdapter;
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
    ListView listViewAllAtm;
    GoogleApiClient client;
    private AllWorkOrdersAdapter adapter;

    // Action Bar components

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RelativeLayout mDrawerPanel;

    private List<WorkOrder> workOrderList = new ArrayList<> ();

    int[] jobIds = {1, 2, 3, 4, 5};
    String[] jobSerialNumber = {"SO#11111", "SO#22222", "SO#33333", "SO#44444", "SO#55555"};

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        initView ();
        initData ();
        initListener ();
        getJobListFromServer ();
        setUpNavigationDrawer ();
    }

    private void initView () {
        listViewAllAtm = (ListView) findViewById (R.id.lvJobsList);
        tvNoInternetConnection = (TextView) findViewById (R.id.tvNoIternetConnection);
        progressBar = (ProgressBar) findViewById (R.id.progressbar);
    }

    private void initListener () {
    }

    private void initData () {
        Utils.setTypefaceToAllViews (this, tvNoInternetConnection);
        adapter = new AllWorkOrdersAdapter (this, workOrderList);
        listViewAllAtm.setAdapter (adapter);
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

    private void getJobListFromServer () {
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
                                    JSONObject jsonObj = new JSONObject (response);
                                    JSONArray jsonArray = jsonObj.getJSONArray (AppConfigTags.WORKORDERS);
                                    json_array_len = jsonArray.length ();
                                    for (int i = 0; i < json_array_len; i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject (i);
                                        WorkOrder workOrder = new WorkOrder ();
                                        workOrder.setWo_id (jsonObject.getInt (AppConfigTags.WO_ID));
                                        workOrder.setWo_site_name (jsonObject.getString (AppConfigTags.WO_SITE_NAME));
                                        workOrder.setWo_contract_num (jsonObject.getInt (AppConfigTags.WO_CONTRACT_NUM));
                                        workOrderList.add (workOrder);
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
                                listViewAllAtm.setVisibility (View.VISIBLE);
                                tvNoInternetConnection.setVisibility (View.GONE);
                            } else if (is_data_received != 0 && json_array_len == 0) {
                                tvNoInternetConnection.setVisibility (View.VISIBLE);
                                progressBar.setVisibility (View.GONE);
                                listViewAllAtm.setVisibility (View.GONE);
                            }
                            if (is_data_received == 0) {
                                progressBar.setVisibility (View.GONE);
                                listViewAllAtm.setVisibility (View.GONE);
                                tvNoInternetConnection.setVisibility (View.VISIBLE);
                            }
                        }
                    },
                    new Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                            progressBar.setVisibility (View.GONE);
                            listViewAllAtm.setVisibility (View.VISIBLE);
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
            listViewAllAtm.setVisibility (View.VISIBLE);
            Utils.showOkDialog (MainActivity.this, "Seems like there is no internet connection, the app will continue in Offline mode", false);
        }
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


}
