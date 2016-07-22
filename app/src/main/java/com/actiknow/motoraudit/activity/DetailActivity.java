package com.actiknow.motoraudit.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.actiknow.motoraudit.R;
import com.actiknow.motoraudit.adapter.ManufacturerAdapter;
import com.actiknow.motoraudit.adapter.ServiceCheckAdapter;
import com.actiknow.motoraudit.model.ServiceCheck;
import com.actiknow.motoraudit.utils.AppConfigTags;
import com.actiknow.motoraudit.utils.AppConfigURL;
import com.actiknow.motoraudit.utils.Constants;
import com.actiknow.motoraudit.utils.ExpandableHeightListView;
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

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    int wo_id, wo_contract_num;

    GoogleApiClient client;

    LinearLayout llGeneralInfoTop;
    TextView tvSet;
    EditText etTimeIn;
    EditText etOnSiteContact;
    EditText etEmail;

    RelativeLayout rlBeforeImage;
    ImageView ivBeforeImage;

    RelativeLayout rlGeneratorInfo;
    EditText etGeneratorModel;
    EditText etGeneratorSerial;
    EditText etEngineModel;
    EditText etKwRating;
    EditText etEngineSerial;
    EditText etAtsModel;
    Spinner spGeneratorMake;
    Spinner spAtsMake;

    RelativeLayout rlFuelSystem;
    ExpandableHeightListView lvFuelSystem;

    RelativeLayout rlPreStartChecksCoolingSystem;
    ExpandableHeightListView lvPreStartChecksCoolingSystem;

    RelativeLayout rlLubricationSystem;
    ExpandableHeightListView lvLubricationSystem;

    RelativeLayout rlAirSystem;
    ExpandableHeightListView lvAirSystem;

    RelativeLayout rlExhaustSystem;
    ExpandableHeightListView lvExhaustSystem;

    RelativeLayout rlGenerator;
    ExpandableHeightListView lvGenerator;

    RelativeLayout rlControlPanelCabinets;
    ExpandableHeightListView lvControlPanelCabinets;

    RelativeLayout rlATSMain;
    ExpandableHeightListView lvATSMain;

    RelativeLayout rlStartingSystem;
    ExpandableHeightListView lvStartingSystem;

    RelativeLayout rlGeneratorEnclosure;
    ExpandableHeightListView lvGeneratorEnclosure;

    RelativeLayout rlStartupAndRunningCheck;
    ExpandableHeightListView lvStartupAndRunningCheck;

    RelativeLayout rlScheduledMaintenance;
    ExpandableHeightListView lvScheduledMaintenance;

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
    TableLayout tlGood;
    TableLayout tlFair;
    TableLayout tlPoor;
    RadioGroup rgOverAllCondition;
    RadioButton rbGood;
    RadioButton rbFair;
    RadioButton rbPoor;
    EditText etGeneratorConditionComment;
    TextView tvGoodConditionDetails;

    ManufacturerAdapter adapter;
    ServiceCheckAdapter serviceCheckAdapter;

    // String variables to hold the data
    String ATSManufacturerId;
    String ATSManufacturerName;
    String GeneratorManufacturerId;
    String GeneratorManufacturerName;

    public static List<ServiceCheck> FuelSystemGovernorList = new ArrayList<ServiceCheck> ();
    ServiceCheckAdapter FuelSystemGovernorAdapter;

    public static List<ServiceCheck> PreStartChecksCoolingSystemList = new ArrayList<ServiceCheck> ();
    ServiceCheckAdapter PreStartChecksCoolingSystemAdapter;

    public static List<ServiceCheck> LubricationSystemList = new ArrayList<ServiceCheck> ();
    ServiceCheckAdapter LubricationSystemAdapter;

    public static List<ServiceCheck> AirSystemList = new ArrayList<ServiceCheck> ();
    ServiceCheckAdapter AirSystemAdapter;

    public static List<ServiceCheck> ExhaustSystemList = new ArrayList<ServiceCheck> ();
    ServiceCheckAdapter ExhaustSystemAdapter;

    public static List<ServiceCheck> GeneratorList = new ArrayList<ServiceCheck> ();
    ServiceCheckAdapter GeneratorAdapter;

    public static List<ServiceCheck> ControlPanelCabinetsList = new ArrayList<ServiceCheck> ();
    ServiceCheckAdapter ControlPanelCabinetsAdapter;

    public static List<ServiceCheck> ATSMainList = new ArrayList<ServiceCheck> ();
    ServiceCheckAdapter ATSMainAdapter;

    public static List<ServiceCheck> StartingSystemList = new ArrayList<ServiceCheck> ();
    ServiceCheckAdapter StartingSystemAdapter;

    public static List<ServiceCheck> GeneratorEnclosureList = new ArrayList<ServiceCheck> ();
    ServiceCheckAdapter GeneratorEnclosureAdapter;

    public static List<ServiceCheck> StartupAndRunningCheckList = new ArrayList<ServiceCheck> ();
    ServiceCheckAdapter StartupAndRunningCheckAdapter;

    public static List<ServiceCheck> ScheduledMaintenanceList = new ArrayList<ServiceCheck> ();
    ServiceCheckAdapter ScheduledMaintenanceAdapter;

    ArrayList<String> listItem = new ArrayList<String> ();
    ArrayList<String> listGoodCondition = new ArrayList<String> ();
    ArrayList<String> listFairCondition = new ArrayList<String> ();
    ArrayList<String> listPoorCondition = new ArrayList<String> ();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_detail_screen);
        initView ();
        initData ();
        initListener ();
        initAdapter();
        getWorkOrderDetailFromServer (78759, 97);
//        getWorkOrderDetailFromServer (wo_id, wo_contract_num);
        Utils.hideSoftKeyboard (this);
    }

    private void initView () {
        llGeneralInfoTop = (LinearLayout) findViewById (R.id.llGeneralInfoTop);
        tvSet = (TextView) findViewById (R.id.tvSet);
        etTimeIn = (EditText) findViewById (R.id.etTimeIn);
        etOnSiteContact = (EditText) findViewById (R.id.etOnSiteContact);
        etEmail = (EditText) findViewById (R.id.etEmail);

        rlBeforeImage = (RelativeLayout) findViewById (R.id.rlBeforeImage);
        ivBeforeImage = (ImageView) findViewById (R.id.ivBeforeImage);

        rlGeneratorInfo = (RelativeLayout) findViewById (R.id.rlGeneratorInfo);
        etGeneratorModel = (EditText) findViewById (R.id.etGeneratorModel);
        etGeneratorSerial = (EditText) findViewById (R.id.etGeneratorSerial);
        etEngineModel = (EditText) findViewById (R.id.etEngineModel);
        etKwRating = (EditText) findViewById (R.id.etKwRating);
        etEngineSerial = (EditText) findViewById (R.id.etEngineSerial);
        etAtsModel = (EditText) findViewById (R.id.etAtsModel);
        spGeneratorMake = (Spinner) findViewById (R.id.spGeneratorMake);
        spAtsMake = (Spinner) findViewById (R.id.spAtsMake);

        rlFuelSystem = (RelativeLayout) findViewById (R.id.rlFuelSystem);
        lvFuelSystem = (ExpandableHeightListView) findViewById (R.id.lvFuelSystem);

        rlPreStartChecksCoolingSystem = (RelativeLayout) findViewById (R.id.rlPreStartChecksCoolingSystem);
        lvPreStartChecksCoolingSystem = (ExpandableHeightListView) findViewById (R.id.lvPreStartChecksCoolingSystem);

        rlLubricationSystem = (RelativeLayout) findViewById (R.id.rlLubricationSystem);
        lvLubricationSystem = (ExpandableHeightListView) findViewById (R.id.lvLubricationSystem);

        rlAirSystem = (RelativeLayout) findViewById (R.id.rlAirSystem);
        lvAirSystem = (ExpandableHeightListView) findViewById (R.id.lvAirSystem);

        rlExhaustSystem = (RelativeLayout) findViewById (R.id.rlExhaustSystem);
        lvExhaustSystem = (ExpandableHeightListView) findViewById (R.id.lvExhaustSystem);

        rlGenerator = (RelativeLayout) findViewById (R.id.rlGenerator);
        lvGenerator = (ExpandableHeightListView) findViewById (R.id.lvGenerator);

        rlControlPanelCabinets = (RelativeLayout) findViewById (R.id.rlControlPanelCabinets);
        lvControlPanelCabinets = (ExpandableHeightListView) findViewById (R.id.lvControlPanelCabinets);

        rlATSMain = (RelativeLayout) findViewById (R.id.rlATSMain);
        lvATSMain = (ExpandableHeightListView) findViewById (R.id.lvATSMain);

        rlStartingSystem = (RelativeLayout) findViewById (R.id.rlStartingSystem);
        lvStartingSystem= (ExpandableHeightListView) findViewById (R.id.lvStartingSystem);

        rlGeneratorEnclosure = (RelativeLayout) findViewById (R.id.rlGeneratorEnclosure);
        lvGeneratorEnclosure = (ExpandableHeightListView) findViewById (R.id.lvGeneratorEnclosure);

        rlStartupAndRunningCheck = (RelativeLayout) findViewById (R.id.rlStartupAndRunningCheck);
        lvStartupAndRunningCheck = (ExpandableHeightListView) findViewById (R.id.lvStartupAndRunningCheck);

        rlScheduledMaintenance = (RelativeLayout) findViewById (R.id.rlScheduledMaintenance);
        lvScheduledMaintenance = (ExpandableHeightListView) findViewById (R.id.lvScheduledMaintenance);


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
        tlGood = (TableLayout) findViewById (R.id.tlGood);
        tlFair = (TableLayout) findViewById (R.id.tlFair);
        tlPoor = (TableLayout) findViewById (R.id.tlPoor);
        rgOverAllCondition = (RadioGroup) findViewById (R.id.rgOverAllCondition);
        rbFair = (RadioButton) findViewById (R.id.rbFair);
        rbGood = (RadioButton) findViewById (R.id.rbGood);
        rbPoor = (RadioButton) findViewById (R.id.rbPoor);
        etGeneratorConditionComment = (EditText) findViewById (R.id.etGeneratorConditionComment);

    }

    private void initListener () {
//        spAtsMake.setOnTouchListener (new View.OnTouchListener () {
//            @Override
//            public boolean onTouch (View view, MotionEvent motionEvent) {
//                Utils.hideSoftKeyboard (DetailActivity.this);
//               return false;
//            }
//        });
//        spGeneratorMake.setOnTouchListener (new View.OnTouchListener () {
//            @Override
//            public boolean onTouch (View view, MotionEvent motionEvent) {
//                Utils.hideSoftKeyboard (DetailActivity.this);
//                return false;
//            }
//        });
        spAtsMake.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected (AdapterView<?> parentView, View v, int position, long id) {
                ATSManufacturerId = ((TextView) v.findViewById (R.id.tvManufacturerID)).getText ().toString ();
                ATSManufacturerName = ((TextView) v.findViewById (R.id.tvManufacturerName)).getText ().toString ();
                Utils.showLog (Log.INFO, "ATS MANUFACTURER ID", ATSManufacturerId, true);
                Utils.showLog (Log.INFO, "ATS MANUFACTURER NAME", ATSManufacturerName, true);
            }

            @Override
            public void onNothingSelected (AdapterView<?> parentView) {
            }
        });
        spGeneratorMake.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected (AdapterView<?> parentView, View v, int position, long id) {
                GeneratorManufacturerId = ((TextView) v.findViewById (R.id.tvManufacturerID)).getText ().toString ();
                GeneratorManufacturerName = ((TextView) v.findViewById (R.id.tvManufacturerName)).getText ().toString ();
                Utils.showLog (Log.INFO, "GENERATOR MANUFACTURER ID", GeneratorManufacturerId, true);
                Utils.showLog (Log.INFO, "GENERATOR MANUFACTURER NAME", GeneratorManufacturerName, true);
            }

            @Override
            public void onNothingSelected (AdapterView<?> parentView) {
            }

        });
    }

    private void initData () {
        Intent intent = getIntent ();
        wo_id = intent.getIntExtra ("wo_id",0);
        wo_contract_num = intent.getIntExtra ("wo_contract_num",0);
        //    Utils.setTypefaceToAllViews (this, tvNoInternetConnection);
        client = new GoogleApiClient.Builder (this).addApi (AppIndex.API).build ();

        FuelSystemGovernorList.clear ();
        PreStartChecksCoolingSystemList.clear ();
        LubricationSystemList.clear ();
        AirSystemList.clear ();
        ExhaustSystemList.clear ();
        GeneratorList.clear ();
        ControlPanelCabinetsList.clear ();
        ATSMainList.clear ();
        StartingSystemList.clear ();
        GeneratorEnclosureList.clear ();
        StartupAndRunningCheckList.clear ();
        ScheduledMaintenanceList.clear ();

        for(int i=0; i<Constants.serviceCheckList.size ();i++){
            ServiceCheck serviceCheck = Constants.serviceCheckList.get (i);
            switch (serviceCheck.getGroup_id ()){
                case 1:
                    FuelSystemGovernorList.add (serviceCheck);
                    break;
                case 2:
                    PreStartChecksCoolingSystemList.add (serviceCheck);
                    break;
                case 3:
                    LubricationSystemList.add (serviceCheck);
                    break;
                case 4:
                    AirSystemList.add (serviceCheck);
                    break;
                case 5:
                    ExhaustSystemList.add (serviceCheck);
                    break;
                case 6:
                    GeneratorList.add (serviceCheck);
                    break;
                case 7:
                    ControlPanelCabinetsList.add (serviceCheck);
                    break;
                case 8:
                    ATSMainList.add (serviceCheck);
                    break;
                case 9:
                    StartingSystemList.add (serviceCheck);
                    break;
                case 10:
                    GeneratorEnclosureList.add (serviceCheck);
                    break;
                case 11:
                    StartupAndRunningCheckList.add (serviceCheck);
                    break;
                case 12:
                    ScheduledMaintenanceList.add (serviceCheck);
                    break;
            }
        }


        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            // Do something for 4.0 and above versions
            getWindow ().setSoftInputMode (WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        } else {
            // do something for phones running an SDK before 4.0
            getWindow ().setSoftInputMode (WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        listGoodCondition.add ("Condition of the generator meet the performance expectations responding to its age");
        listGoodCondition.add ("Critical components (engine ,radiator , alternator) are not at risk obsolescence in the next 5 years ");
        listGoodCondition.add ("Technical expect minimal or minor,but no major repair in the next 5 years (as long as the equipment is maintain accordingly to manufacturer recommendations ) ");

        listFairCondition.add ("Condition of the generator meets the performance expectations corresponding to its age.");
        listFairCondition.add ("Critical components (engine, alternator, or radiator) may be at risk of obsolescence in the next 5 years.");
        listFairCondition.add ("Technician expects an increase in repairs within the next 5 years (despite the equipment is maintained according to manufacturer recommendations)");

        listPoorCondition.add ("Condition of the generator does not meets the performance expectations corresponding to its age.");
        listPoorCondition.add ("Critical components (engine, alternator, or radiator) are difficult to source, have long lead-time, are expensive to buy, or have became obsolete.");
        listPoorCondition.add ("Repairs needed exceed 25% of the cost to repair with a like-for-like generator (despite the equipment is being maintained according to manufacturer recommendations)");


        for (int i = 0; i < listGoodCondition.size (); i++) {
            String GoodConditionText = listGoodCondition.get (i);
            TableRow row = (TableRow) LayoutInflater.from (DetailActivity.this).inflate (R.layout.table_row_condition, null);

            tlGood.setGravity (Gravity.CENTER_VERTICAL);
            tvGoodConditionDetails = (TextView) row.findViewById (R.id.tvConditionDetails);

            tvGoodConditionDetails.setText ("" + GoodConditionText);
            tlGood.addView (row);
        }

        for (int i = 0; i < listFairCondition.size (); i++) {
            String GoodConditionText = listFairCondition.get (i);
            TableRow row = (TableRow) LayoutInflater.from (DetailActivity.this).inflate (R.layout.table_row_condition, null);

            tlGood.setGravity (Gravity.CENTER_VERTICAL);
            tvGoodConditionDetails = (TextView) row.findViewById (R.id.tvConditionDetails);

            tvGoodConditionDetails.setText ("" + GoodConditionText);
            tlFair.addView (row);
        }
        for (int i = 0; i < listPoorCondition.size (); i++) {
            String GoodConditionText = listPoorCondition.get (i);
            TableRow row = (TableRow) LayoutInflater.from (DetailActivity.this).inflate (R.layout.table_row_condition, null);

            tlGood.setGravity (Gravity.CENTER_VERTICAL);
            tvGoodConditionDetails = (TextView) row.findViewById (R.id.tvConditionDetails);

            tvGoodConditionDetails.setText ("" + GoodConditionText);
            tlPoor.addView (row);
        }

    }

    private void initAdapter(){
        adapter = new ManufacturerAdapter (this, R.layout.spinner_item, Constants.manufacturerList);
        spAtsMake.setAdapter (adapter);
        spGeneratorMake.setAdapter (adapter);

        FuelSystemGovernorAdapter = new ServiceCheckAdapter (this, FuelSystemGovernorList, 1);
        lvFuelSystem.setExpanded (true);
        lvFuelSystem.setAdapter (FuelSystemGovernorAdapter);

        PreStartChecksCoolingSystemAdapter = new ServiceCheckAdapter (this, PreStartChecksCoolingSystemList, 2);
        lvPreStartChecksCoolingSystem.setExpanded (true);
        lvPreStartChecksCoolingSystem.setAdapter (PreStartChecksCoolingSystemAdapter);

        LubricationSystemAdapter = new ServiceCheckAdapter (this, LubricationSystemList, 3);
        lvLubricationSystem.setExpanded (true);
        lvLubricationSystem.setAdapter (LubricationSystemAdapter);

        AirSystemAdapter = new ServiceCheckAdapter (this, AirSystemList, 4);
        lvAirSystem.setExpanded (true);
        lvAirSystem.setAdapter (AirSystemAdapter);

        ExhaustSystemAdapter = new ServiceCheckAdapter (this, ExhaustSystemList, 5);
        lvExhaustSystem.setExpanded (true);
        lvExhaustSystem.setAdapter (ExhaustSystemAdapter);

        GeneratorAdapter = new ServiceCheckAdapter (this, GeneratorList, 6);
        lvGenerator.setExpanded (true);
        lvGenerator.setAdapter (GeneratorAdapter);

        ControlPanelCabinetsAdapter = new ServiceCheckAdapter (this, ControlPanelCabinetsList, 7);
        lvControlPanelCabinets.setExpanded (true);
        lvControlPanelCabinets.setAdapter (ControlPanelCabinetsAdapter);

        ATSMainAdapter = new ServiceCheckAdapter (this, ATSMainList, 8);
        lvATSMain.setExpanded (true);
        lvATSMain.setAdapter (ATSMainAdapter);

        StartingSystemAdapter = new ServiceCheckAdapter (this, StartingSystemList, 9);
        lvStartingSystem.setExpanded (true);
        lvStartingSystem.setAdapter (StartingSystemAdapter);

        GeneratorEnclosureAdapter = new ServiceCheckAdapter (this, GeneratorEnclosureList, 10);
        lvGeneratorEnclosure.setExpanded (true);
        lvGeneratorEnclosure.setAdapter (GeneratorEnclosureAdapter);

        StartupAndRunningCheckAdapter = new ServiceCheckAdapter (this, StartupAndRunningCheckList, 11);
        lvStartupAndRunningCheck.setExpanded (true);
        lvStartupAndRunningCheck.setAdapter (StartupAndRunningCheckAdapter);

        ScheduledMaintenanceAdapter = new ServiceCheckAdapter (this, ScheduledMaintenanceList, 12);
        lvScheduledMaintenance.setExpanded (true);
        lvScheduledMaintenance.setAdapter (ScheduledMaintenanceAdapter);
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
