package com.actiknow.motoraudit.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actiknow.motoraudit.R;
import com.actiknow.motoraudit.adapter.AllJobsAdapter;
import com.actiknow.motoraudit.model.Job;
import com.actiknow.motoraudit.utils.Utils;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvNoInternetConnection;
    ProgressBar progressBar;
    ListView listViewAllAtm;
    GoogleApiClient client;
    private AllJobsAdapter adapter;

    private List<Job> jobList = new ArrayList<> ();

    int[] jobIds = {1,2,3,4,5};
    String[] jobSerialNumber = {"SO#11111", "SO#22222", "SO#33333", "SO#44444", "SO#55555"};

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        initView ();
        initData ();
        initListener ();
        getJobListFromServer ();
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
        adapter = new AllJobsAdapter (this, jobList);
        listViewAllAtm.setAdapter (adapter);
        client = new GoogleApiClient.Builder (this).addApi (AppIndex.API).build ();
    }

    private void getJobListFromServer () {
        for (int i =0;i<jobIds.length;i++){
            Job job = new Job ();
            job.setJob_id (jobIds[i]);
            job.setJob_serial_number (jobSerialNumber[i]);
            jobList.add (job);
        }
        adapter.notifyDataSetChanged ();
        progressBar.setVisibility (View.GONE);
        listViewAllAtm.setVisibility (View.VISIBLE);
        tvNoInternetConnection.setVisibility (View.GONE);

/*





 if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.URL_GETALLATMS, true);
            StringRequest strRequest = new StringRequest (Request.Method.POST, AppConfigURL.URL_GETALLATMS,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            atmList.clear ();
                            int is_data_received = 0;
                            int json_array_len = 0;
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                is_data_received = 1;
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    JSONArray jsonArray = jsonObj.getJSONArray (AppConfigTags.ATMS);
                                    db.deleteAllAtms ();
                                    json_array_len = jsonArray.length ();
                                    for (int i = 0; i < json_array_len; i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject (i);
                                        Job job = new Job ();
                                        job.setJob_id (jsonObject.getInt (AppConfigTags.ATM_ID));
                                        job.setJob_serial_number (jsonObject.getString (AppConfigTags.ATM_UNIQUE_ID));
                                        jobList.add (job);
                                        db.createAtm (atm);
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
                            getAtmListFromLocalDatabase ();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams () throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String> ();
                    params.put (AppConfigTags.AUDITOR_ID, String.valueOf (Constants.auditor_id_main));
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, "" + params, true);
                    return params;
                }
            };
            Utils.sendRequest (strRequest);

        } else {
            progressBar.setVisibility (View.GONE);
            listViewAllAtm.setVisibility (View.VISIBLE);
            getAtmListFromLocalDatabase ();
            Utils.showOkDialog (MainActivity.this, "Seems like there is no internet connection, the app will continue in Offline mode", false);
        }

        */

    }

}
