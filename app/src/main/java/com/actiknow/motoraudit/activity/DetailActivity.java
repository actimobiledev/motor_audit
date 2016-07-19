package com.actiknow.motoraudit.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.actiknow.motoraudit.R;
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

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    int wo_id, wo_contract_num;

    GoogleApiClient client;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_detail_screen);
        initView ();
        initData ();
        initListener ();
        getWorkOrderDetailFromServer (wo_id, wo_contract_num);
    }

    private void initView () {
    }

    private void initListener () {
    }

    private void initData () {
        Intent intent = getIntent ();
        wo_id = intent.getIntExtra ("wo_id",0);
        wo_contract_num = intent.getIntExtra ("wo_contract_num",0);

        //    Utils.setTypefaceToAllViews (this, tvNoInternetConnection);
        client = new GoogleApiClient.Builder (this).addApi (AppIndex.API).build ();
    }

    private void getWorkOrderDetailFromServer (final int wo_id, final int wo_contract_number) {
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
                            "\"API_function\":\"getWorkorderForms\",\n" +
                            "\"API_parameters\":{\"WO_NUM\" : \"" + wo_id + "\",\"CONTRACT_NUM\": \"" + wo_contract_number + "\"}}";
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, str, true);
                    return str.getBytes ();
                }

                public String getBodyContentType () {
                    return "application/json; charset=utf-8";
                }
            };
            Utils.sendRequest (strRequest);

        } else {
            Utils.showOkDialog (DetailActivity.this, "Seems like there is no internet connection, the app will continue in Offline mode", false);
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
