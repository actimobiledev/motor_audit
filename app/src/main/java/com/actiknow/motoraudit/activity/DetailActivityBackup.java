package com.actiknow.motoraudit.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import android.widget.Toast;

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
import com.kyanogen.signatureview.SignatureView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetailActivityBackup extends AppCompatActivity {

    public static final int BEFORE_IMAGE_PICKER = 1;
    public static final int AFTER_IMAGE_PICKER = 2;
    public static final int LIST_ITEM_PICKER = 3;

    public static final int PICK_FROM_CAMERA_BEFORE_IMAGE_1 = 111;
    public static final int PICK_FROM_GALLERY_BEFORE_IMAGE_1 = 121;
    public static final int PICK_FROM_CAMERA_AFTER_IMAGE_1 = 211;
    public static final int PICK_FROM_GALLERY_AFTER_IMAGE_1 = 221;
    public static final int PICK_FROM_CAMERA_LIST_ITEM_1 = 311;
    public static final int PICK_FROM_GALLERY_LIST_ITEM_1 = 321;
    public static final int PICK_FROM_CAMERA_BEFORE_IMAGE_2 = 112;
    public static final int PICK_FROM_GALLERY_BEFORE_IMAGE_2 = 122;
    public static final int PICK_FROM_CAMERA_AFTER_IMAGE_2 = 212;
    public static final int PICK_FROM_GALLERY_AFTER_IMAGE_2 = 222;
    public static final int PICK_FROM_CAMERA_LIST_ITEM_2 = 312;
    public static final int PICK_FROM_GALLERY_LIST_ITEM_2 = 322;
    public static List<ServiceCheck> FuelSystemGovernorList = new ArrayList<ServiceCheck> ();
    public static List<ServiceCheck> PreStartChecksCoolingSystemList = new ArrayList<ServiceCheck> ();
    public static List<ServiceCheck> LubricationSystemList = new ArrayList<ServiceCheck> ();
    public static List<ServiceCheck> AirSystemList = new ArrayList<ServiceCheck> ();
    public static List<ServiceCheck> ExhaustSystemList = new ArrayList<ServiceCheck> ();
    public static List<ServiceCheck> GeneratorList = new ArrayList<ServiceCheck> ();
    public static List<ServiceCheck> ControlPanelCabinetsList = new ArrayList<ServiceCheck> ();
    public static List<ServiceCheck> ATSMainList = new ArrayList<ServiceCheck> ();
    public static List<ServiceCheck> StartingSystemList = new ArrayList<ServiceCheck> ();
    public static List<ServiceCheck> GeneratorEnclosureList = new ArrayList<ServiceCheck> ();
    public static List<ServiceCheck> StartupAndRunningCheckList = new ArrayList<ServiceCheck> ();
    public static List<ServiceCheck> ScheduledMaintenanceList = new ArrayList<ServiceCheck> ();
    int wo_id, wo_contract_num;
    String wo_site_name;
    GoogleApiClient client;
    TextView tvWorkOrderDescription;
    LinearLayout llGeneralInfoTop;
    TextView tvCancel;
    TextView tvUpdate;
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
    TextView tvConditionDetails;
    RelativeLayout rlComment;
    EditText etComment;
    RelativeLayout rlAfterImage;
    ImageView ivAfterImage;
    RelativeLayout rlTechSignature;
    ImageView ivTechSignature;
    RelativeLayout rlCustomerSignature;
    ImageView ivCustomerSignature;
    TextView tvTimeOutSet;
    EditText etTimeOut;
    ManufacturerAdapter adapter;
    ServiceCheckAdapter serviceCheckAdapter;
    ServiceCheckAdapter FuelSystemGovernorAdapter;
    ServiceCheckAdapter PreStartChecksCoolingSystemAdapter;
    ServiceCheckAdapter LubricationSystemAdapter;
    ServiceCheckAdapter AirSystemAdapter;
    ServiceCheckAdapter ExhaustSystemAdapter;
    ServiceCheckAdapter GeneratorAdapter;
    ServiceCheckAdapter ControlPanelCabinetsAdapter;
    ServiceCheckAdapter ATSMainAdapter;
    ServiceCheckAdapter StartingSystemAdapter;
    ServiceCheckAdapter GeneratorEnclosureAdapter;
    ServiceCheckAdapter StartupAndRunningCheckAdapter;
    ServiceCheckAdapter ScheduledMaintenanceAdapter;

    ArrayList<String> listItem = new ArrayList<String> ();
    ArrayList<String> listGoodCondition = new ArrayList<String> ();
    ArrayList<String> listFairCondition = new ArrayList<String> ();
    ArrayList<String> listPoorCondition = new ArrayList<String> ();

    Dialog dialogSign;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_detail_screen);
        initView ();
        initData ();
        initListener ();
        initAdapter ();
        getWorkOrderDetailFromServer (78759, 97);
//        getWorkOrderDetailFromServer (wo_id, wo_contract_num);
        Utils.hideSoftKeyboard (this);


        new LoadServiceChecks (this).execute ();
    }

    private void initView () {
        tvWorkOrderDescription = (TextView) findViewById (R.id.tvWorkOrderDescription);

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
        lvStartingSystem = (ExpandableHeightListView) findViewById (R.id.lvStartingSystem);
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

        rlComment = (RelativeLayout) findViewById (R.id.rlComment);
        etComment = (EditText) findViewById (R.id.etComment);

        rlAfterImage = (RelativeLayout) findViewById (R.id.rlAfterImage);
        ivAfterImage = (ImageView) findViewById (R.id.ivAfterImage);

        rlTechSignature = (RelativeLayout) findViewById (R.id.rlTechSignature);
        ivTechSignature = (ImageView) findViewById (R.id.ivTechSignature);

        rlCustomerSignature = (RelativeLayout) findViewById (R.id.rlCustomerSignature);
        ivCustomerSignature = (ImageView) findViewById (R.id.ivCustomerSignature);

        tvCancel = (TextView) findViewById (R.id.tvCancel);
        tvUpdate = (TextView) findViewById (R.id.tvUpdate);
        tvTimeOutSet = (TextView) findViewById (R.id.tvTimeOutSet);
        etTimeOut = (EditText) findViewById (R.id.etTimeOut);

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
                Constants.workOrderDetail.setAts_make_id (Integer.parseInt (((TextView) v.findViewById (R.id.tvManufacturerID)).getText ().toString ()));
                Constants.workOrderDetail.setAts_make_name (((TextView) v.findViewById (R.id.tvManufacturerName)).getText ().toString ());
                Utils.showLog (Log.INFO, "ATS MANUFACTURER ID", "" + Constants.workOrderDetail.getAts_make_id (), true);
                Utils.showLog (Log.INFO, "ATS MANUFACTURER NAME", Constants.workOrderDetail.getAts_make_name (), true);
            }

            @Override
            public void onNothingSelected (AdapterView<?> parentView) {
            }
        });
        spGeneratorMake.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected (AdapterView<?> parentView, View v, int position, long id) {
                Constants.workOrderDetail.setGenerator_make_id (Integer.parseInt (((TextView) v.findViewById (R.id.tvManufacturerID)).getText ().toString ()));
                Constants.workOrderDetail.setGenerator_make_name (((TextView) v.findViewById (R.id.tvManufacturerName)).getText ().toString ());
                Utils.showLog (Log.INFO, "GENERATOR MANUFACTURER ID", "" + Constants.workOrderDetail.getGenerator_make_id (), true);
                Utils.showLog (Log.INFO, "GENERATOR MANUFACTURER NAME", Constants.workOrderDetail.getGenerator_make_name (), true);
            }

            @Override
            public void onNothingSelected (AdapterView<?> parentView) {
            }
        });
        rgOverAllCondition.setOnCheckedChangeListener (new RadioGroup.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (RadioGroup radioGroup, int i) {
                if (i == R.id.rbPoor) {
                    Constants.workOrderDetail.setGenerator_condition_flag (0);
                    Constants.workOrderDetail.setGenerator_condition_text ("POOR");
                } else if (i == R.id.rbFair) {
                    Constants.workOrderDetail.setGenerator_condition_flag (1);
                    Constants.workOrderDetail.setGenerator_condition_text ("FAIR");
                } else if (i == R.id.rbGood) {
                    Constants.workOrderDetail.setGenerator_condition_flag (2);
                    Constants.workOrderDetail.setGenerator_condition_text ("GOOD");
                }
            }
        });

        tvCancel.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    tvCancel.setBackgroundResource (R.color.background_button_red_pressed);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    tvCancel.setBackgroundResource (R.color.background_button_red);
                }
                return true;
            }
        });
        tvUpdate.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {


                    if (validate ()) {
                        JSONArray jsonArray = new JSONArray ();
                        try {
                            for (int i = 0; i < Constants.serviceCheckList.size (); i++) {
                                final ServiceCheck serviceCheck;
                                serviceCheck = Constants.serviceCheckList.get (i);
                                JSONObject jsonObject = new JSONObject ();
//                            jsonObject.put (AppConfigTags.GROUP_NAME, serviceCheck.getGroup_name ());
                                jsonObject.put (AppConfigTags.SERVICE_CHECK_ID, String.valueOf (serviceCheck.getService_check_id ()));
//                            jsonObject.put (AppConfigTags.SELECTION_FLAG, String.valueOf (serviceCheck.getSelection_flag ()));
                                jsonObject.put (AppConfigTags.SELECTION_TEXT, serviceCheck.getSelection_text ());
                                jsonObject.put (AppConfigTags.HEADING, serviceCheck.getHeading ());
//                            jsonObject.put (AppConfigTags.SUB_HEADING, serviceCheck.getSub_heading ());
                                jsonObject.put (AppConfigTags.IMAGE, serviceCheck.getImage_str ());
                                jsonObject.put (AppConfigTags.COMMENT, serviceCheck.getComment ());
                                jsonArray.put (jsonObject);
                            }
                        } catch (JSONException e) {
                            Utils.showLog (Log.ERROR, "JSON EXCEPTION", e.getMessage (), true);
                        }

                        Constants.workOrderDetail.setService_check_json (String.valueOf (jsonArray));
                        Constants.workOrderDetail.setTime_in (etTimeIn.getText ().toString ());
                        Constants.workOrderDetail.setTime_out (etTimeOut.getText ().toString ());
                        Constants.workOrderDetail.setOnsite_contact (etOnSiteContact.getText ().toString ());
                        Constants.workOrderDetail.setEmail (etEmail.getText ().toString ());
                        Constants.workOrderDetail.setGenerator_serial (etGeneratorSerial.getText ().toString ());
                        Constants.workOrderDetail.setGenerator_model (etGeneratorModel.getText ().toString ());
                        Constants.workOrderDetail.setEngine_model (etEngineModel.getText ().toString ());
                        Constants.workOrderDetail.setKw_rating (etKwRating.getText ().toString ());
                        Constants.workOrderDetail.setEngine_serial (etEngineSerial.getText ().toString ());
                        Constants.workOrderDetail.setAts_model (etAtsModel.getText ().toString ());
                        Constants.workOrderDetail.setGenerator_condition_comment (etGeneratorConditionComment.getText ().toString ());
                        Constants.workOrderDetail.setComments (etComment.getText ().toString ());


                        Utils.showLog (Log.INFO, "WORK ORDER TIME IN", Constants.workOrderDetail.getTime_in (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER ONSITE CONTACT", Constants.workOrderDetail.getOnsite_contact (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER EMAIL", Constants.workOrderDetail.getEmail (), true);
//                    Utils.showLog (Log.INFO, "WORK ORDER BEFORE IMAGE", Constants.workOrderDetail.getBefore_image_str (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER GENERATOR SERIAL", Constants.workOrderDetail.getGenerator_serial (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER GENERATOR MAKE ID", "" + Constants.workOrderDetail.getGenerator_make_id (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER GENERATOR MAKE NAME", Constants.workOrderDetail.getGenerator_make_name (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER GENERATOR MODEL", Constants.workOrderDetail.getGenerator_model (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER ENGINE MODEL", Constants.workOrderDetail.getEngine_model (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER KW RATING", Constants.workOrderDetail.getKw_rating (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER ENGINE SERIAL", Constants.workOrderDetail.getEngine_serial (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER ATS MAKE ID", "" + Constants.workOrderDetail.getAts_make_id (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER ATS MAKE NAME", Constants.workOrderDetail.getAts_make_name (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER ATS MODEL", Constants.workOrderDetail.getAts_model (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER SERVICE CHECK JSON", "" + Constants.workOrderDetail.getService_check_json (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER GENERATOR CONDITION FLAG", "" + Constants.workOrderDetail.getGenerator_condition_flag (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER GENERATOR CONDITION TEXT", Constants.workOrderDetail.getGenerator_condition_text (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER GENERATOR CONDITION COMMENT", Constants.workOrderDetail.getGenerator_condition_comment (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER COMMENTS", Constants.workOrderDetail.getComments (), true);
//                    Utils.showLog (Log.INFO, "WORK ORDER AFTER IMAGE", Constants.workOrderDetail.getAfter_image_str (), true);
//                    Utils.showLog (Log.INFO, "WORK ORDER TECH SIGNATURE", Constants.workOrderDetail.getTech_image_str (), true);
//                    Utils.showLog (Log.INFO, "WORK ORDER CUSTOMER SIGNATURE", Constants.workOrderDetail.getCustomer_signature (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER TIME OUT", Constants.workOrderDetail.getTime_out (), true);

                    }





                    /*
                    if (FuelSystemGovernorAdapter.isCommentSatisfied ()) {
                        Toast.makeText (getApplicationContext (), "Fuel System/Governor Empty", Toast.LENGTH_SHORT).show ();
                    } else {
                        Toast.makeText (getApplicationContext (), "Fuel System/Governor Not Empty", Toast.LENGTH_SHORT).show ();
                    }
                    if (PreStartChecksCoolingSystemAdapter.isCommentSatisfied ()) {
                        Toast.makeText (getApplicationContext (), "PreStartChecksCoolingSystem Empty", Toast.LENGTH_SHORT).show ();
                    } else {
                        Toast.makeText (getApplicationContext (), "PreStartChecksCoolingSystem Not Empty", Toast.LENGTH_SHORT).show ();
                    }
                    if (LubricationSystemAdapter.isCommentSatisfied ()) {
                        Toast.makeText (getApplicationContext (), "LubricationSystem Empty", Toast.LENGTH_SHORT).show ();
                    } else {
                        Toast.makeText (getApplicationContext (), "LubricationSystem Not Empty", Toast.LENGTH_SHORT).show ();
                    }
                    if (AirSystemAdapter.isCommentSatisfied ()) {
                        Toast.makeText (getApplicationContext (), "AirSystem Empty", Toast.LENGTH_SHORT).show ();
                    } else {
                        Toast.makeText (getApplicationContext (), "AirSystem Not Empty", Toast.LENGTH_SHORT).show ();
                    }
                    if (ExhaustSystemAdapter.isCommentSatisfied ()) {
                        Toast.makeText (getApplicationContext (), "ExhaustSystem Empty", Toast.LENGTH_SHORT).show ();
                    } else {
                        Toast.makeText (getApplicationContext (), "ExhaustSystem Not Empty", Toast.LENGTH_SHORT).show ();
                    }
                    if (GeneratorAdapter.isCommentSatisfied ()) {
                        Toast.makeText (getApplicationContext (), "Generator Empty", Toast.LENGTH_SHORT).show ();
                    } else {
                        Toast.makeText (getApplicationContext (), "Generator Not Empty", Toast.LENGTH_SHORT).show ();
                    }
                    if (ControlPanelCabinetsAdapter.isCommentSatisfied ()) {
                        Toast.makeText (getApplicationContext (), "ControlPanelCabinets Empty", Toast.LENGTH_SHORT).show ();
                    } else {
                        Toast.makeText (getApplicationContext (), "ControlPanelCabinets Not Empty", Toast.LENGTH_SHORT).show ();
                    }
                    if (ATSMainAdapter.isCommentSatisfied ()) {
                        Toast.makeText (getApplicationContext (), "ATSMain Empty", Toast.LENGTH_SHORT).show ();
                    } else {
                        Toast.makeText (getApplicationContext (), "ATSMain Not Empty", Toast.LENGTH_SHORT).show ();
                    }
                    if (StartingSystemAdapter.isCommentSatisfied ()) {
                        Toast.makeText (getApplicationContext (), "StartingSystem Empty", Toast.LENGTH_SHORT).show ();
                    } else {
                        Toast.makeText (getApplicationContext (), "StartingSystem Empty", Toast.LENGTH_SHORT).show ();
                    }
                    if (GeneratorEnclosureAdapter.isCommentSatisfied ()) {
                        Toast.makeText (getApplicationContext (), "GeneratorEnclosure Empty", Toast.LENGTH_SHORT).show ();
                    } else {
                        Toast.makeText (getApplicationContext (), "GeneratorEnclosure Empty", Toast.LENGTH_SHORT).show ();
                    }
                    if (StartupAndRunningCheckAdapter.isCommentSatisfied ()) {
                        Toast.makeText (getApplicationContext (), "StartupAndRunningCheck Empty", Toast.LENGTH_SHORT).show ();
                    } else {
                        Toast.makeText (getApplicationContext (), "StartupAndRunningCheck Empty", Toast.LENGTH_SHORT).show ();
                    }
*/


                    tvUpdate.setBackgroundResource (R.color.background_button_green_pressed);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    tvUpdate.setBackgroundResource (R.color.background_button_green);

                }
                return true;
            }
        });
        tvSet.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    tvSet.setBackgroundResource (R.color.background_blue_pressed);
                    DateFormat df = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                    String date = df.format (Calendar.getInstance ().getTime ());
                    etTimeIn.setText (date);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    tvSet.setBackgroundResource (R.color.background_blue);
                }
                return true;
            }
        });
        tvTimeOutSet.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    tvTimeOutSet.setBackgroundResource (R.color.background_blue_pressed);
                    DateFormat df = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                    String date = df.format (Calendar.getInstance ().getTime ());
                    etTimeOut.setText (date);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    tvTimeOutSet.setBackgroundResource (R.color.background_blue);
                }
                return true;
            }
        });

        ivTechSignature.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                showSignatureDialog (1);
            }
        });
        ivCustomerSignature.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                showSignatureDialog (2);
            }
        });

        ivBeforeImage.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                selectImage (BEFORE_IMAGE_PICKER);
            }
        });

        ivAfterImage.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                selectImage (AFTER_IMAGE_PICKER);
            }
        });

    }

    private void initData () {
        Intent intent = getIntent ();
        wo_id = intent.getIntExtra ("wo_id", 0);
        wo_contract_num = intent.getIntExtra ("wo_contract_num", 0);
        wo_site_name = intent.getStringExtra ("wo_site_name");
        //    Utils.setTypefaceToAllViews (this, tvNoInternetConnection);

        client = new GoogleApiClient.Builder (this).addApi (AppIndex.API).build ();

        Utils.hideKeyboard (DetailActivityBackup.this, etAtsModel);


/*
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            // Do something for 4.0 and above versions
            getWindow ().setSoftInputMode (WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        } else {
            // do something for phones running an SDK before 4.0
            getWindow ().setSoftInputMode (WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
*/
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
            TableRow row = (TableRow) LayoutInflater.from (DetailActivityBackup.this).inflate (R.layout.table_row_condition, null);
            tlGood.setGravity (Gravity.CENTER_VERTICAL);
            tvConditionDetails = (TextView) row.findViewById (R.id.tvConditionDetails);
            tvConditionDetails.setText ("" + GoodConditionText);
            tlGood.addView (row);
        }
        for (int i = 0; i < listFairCondition.size (); i++) {
            String FairConditionText = listFairCondition.get (i);
            TableRow row = (TableRow) LayoutInflater.from (DetailActivityBackup.this).inflate (R.layout.table_row_condition, null);
            tlFair.setGravity (Gravity.CENTER_VERTICAL);
            tvConditionDetails = (TextView) row.findViewById (R.id.tvConditionDetails);
            tvConditionDetails.setText ("" + FairConditionText);
            tlFair.addView (row);
        }
        for (int i = 0; i < listPoorCondition.size (); i++) {
            String PoorConditionText = listPoorCondition.get (i);
            TableRow row = (TableRow) LayoutInflater.from (DetailActivityBackup.this).inflate (R.layout.table_row_condition, null);
            tlPoor.setGravity (Gravity.CENTER_VERTICAL);
            tvConditionDetails = (TextView) row.findViewById (R.id.tvConditionDetails);
            tvConditionDetails.setText ("" + PoorConditionText);
            tlPoor.addView (row);
        }

        Constants.workOrderDetail.setGenerator_condition_flag (2);
        Constants.workOrderDetail.setGenerator_condition_text ("GOOD");
        tvWorkOrderDescription.setText ("WO#" + wo_id + " for " + wo_site_name);
    }

    private void initAdapter () {
        adapter = new ManufacturerAdapter (this, R.layout.spinner_item, Constants.manufacturerList);
        spAtsMake.setAdapter (adapter);
        spGeneratorMake.setAdapter (adapter);

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
            Utils.showOkDialog (DetailActivityBackup.this, "Seems like there is no internet connection, the app will continue in Offline mode", false);
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

    private void showSignatureDialog (final int flag) {
        Button btSignCanel;
        Button btSignClear;
        Button btSignSave;
        final SignatureView signatureView;

        dialogSign = new Dialog (DetailActivityBackup.this);
        dialogSign.setContentView (R.layout.dialog_signature);
        dialogSign.setCancelable (false);
        btSignCanel = (Button) dialogSign.findViewById (R.id.btSignCancel);
        btSignClear = (Button) dialogSign.findViewById (R.id.btSignClear);
        btSignSave = (Button) dialogSign.findViewById (R.id.btSignSave);
        signatureView = (SignatureView) dialogSign.findViewById (R.id.signSignatureView);

        Utils.setTypefaceToAllViews (DetailActivityBackup.this, btSignCanel);
//        dialogSign.requestWindowFeature (Window.FEATURE_NO_TITLE);
        dialogSign.getWindow ().setBackgroundDrawable (new ColorDrawable (android.graphics.Color.TRANSPARENT));
        dialogSign.show ();
        btSignCanel.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                dialogSign.dismiss ();
            }
        });
        btSignClear.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                signatureView.clearCanvas ();
            }
        });
        btSignSave.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                dialogSign.dismiss ();
                Bitmap bp = signatureView.getSignatureBitmap ();
                switch (flag) {
                    case 1:
                        Constants.workOrderDetail.setTech_image_str (Utils.bitmapToBase64 (bp));
                        ivTechSignature.setImageBitmap (bp);
                        break;
                    case 2:
                        Constants.workOrderDetail.setCustomer_signature (Utils.bitmapToBase64 (bp));
                        ivCustomerSignature.setImageBitmap (bp);
                        break;
                }
//                submitReportToServer (Constants.report);
            }
        });
    }

    private void selectImage (final int flag) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder (this);
        builder.setItems (options, new DialogInterface.OnClickListener () {
            @Override
            public void onClick (DialogInterface dialog, int item) {
                Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File (Environment.getExternalStorageDirectory () + File.separator + "img.jpg");
                if (options[item].equals ("Take Photo")) {
                    switch (flag) {
                        case BEFORE_IMAGE_PICKER:
                            intent.putExtra (MediaStore.EXTRA_OUTPUT, Uri.fromFile (f));
                            startActivityForResult (intent, PICK_FROM_CAMERA_BEFORE_IMAGE_1);
                            break;
                        case AFTER_IMAGE_PICKER:
                            intent.putExtra (MediaStore.EXTRA_OUTPUT, Uri.fromFile (f));
                            startActivityForResult (intent, PICK_FROM_CAMERA_AFTER_IMAGE_1);
                            break;
                        case LIST_ITEM_PICKER:
                            intent.putExtra (MediaStore.EXTRA_OUTPUT, Uri.fromFile (f));
                            startActivityForResult (intent, PICK_FROM_CAMERA_LIST_ITEM_1);
                            break;
                    }
                } else if (options[item].equals ("Choose from Gallery")) {
                    switch (flag) {
                        case BEFORE_IMAGE_PICKER:
                            Intent i = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult (i, PICK_FROM_GALLERY_BEFORE_IMAGE_1);
                            break;
                        case AFTER_IMAGE_PICKER:
                            Intent i2 = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult (i2, PICK_FROM_GALLERY_AFTER_IMAGE_1);
                            break;
                        case LIST_ITEM_PICKER:
                            Intent i3 = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult (i3, PICK_FROM_GALLERY_LIST_ITEM_1);
                            break;
                    }

                    // Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    ///startActivityForResult(intent, 1);

//                    Intent intent = new Intent ();
//                    intent.setType ("image/*");
//                    intent.setAction (Intent.ACTION_GET_CONTENT);
//                    intent.putExtra ("aspectX", 4);
//                    intent.putExtra ("aspectY", 3);
                    //indicate output X and Y
//                    intent.putExtra ("outputX", 256);
//                    intent.putExtra ("outputY", 256);

//                    try {

//                        intent.putExtra ("return-data", true);
//                        startActivityForResult (intent, PICK_FROM_GALLERY);
//
//                    } catch (ActivityNotFoundException e) {
//                        // Do nothing for now
//                    }


                } else if (options[item].equals ("Cancel")) {
                    dialog.dismiss ();
                }
            }
        });
        builder.show ();
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case PICK_FROM_CAMERA_BEFORE_IMAGE_1:
                        File file = new File (Environment.getExternalStorageDirectory () + File.separator + "img.jpg");
                        try {
                            cropCapturedImage (Uri.fromFile (file), PICK_FROM_CAMERA_BEFORE_IMAGE_1);
                        } catch (ActivityNotFoundException aNFE) {
                            Toast.makeText (this, "Sorry - your device doesn't support the crop action!", Toast.LENGTH_SHORT).show ();
                        }
                        break;
                    case PICK_FROM_CAMERA_BEFORE_IMAGE_2:
                        Bundle extras = data.getExtras ();
                        Bitmap thePic = extras.getParcelable ("data");
                        Constants.workOrderDetail.setBefore_image_str (Utils.bitmapToBase64 (thePic));
                        ivBeforeImage.setImageBitmap (thePic);
                        break;
                    case PICK_FROM_CAMERA_AFTER_IMAGE_1:
                        File file2 = new File (Environment.getExternalStorageDirectory () + File.separator + "img.jpg");
                        try {
                            cropCapturedImage (Uri.fromFile (file2), PICK_FROM_CAMERA_AFTER_IMAGE_1);
                        } catch (ActivityNotFoundException aNFE) {
                            Toast.makeText (this, "Sorry - your device doesn't support the crop action!", Toast.LENGTH_SHORT).show ();
                        }
                        break;
                    case PICK_FROM_CAMERA_AFTER_IMAGE_2:
                        Bundle extras2 = data.getExtras ();
                        Bitmap thePic2 = extras2.getParcelable ("data");
                        Constants.workOrderDetail.setAfter_image_str (Utils.bitmapToBase64 (thePic2));
                        ivAfterImage.setImageBitmap (thePic2);
                        break;


                    case PICK_FROM_GALLERY_BEFORE_IMAGE_1:
                        Uri selectedImage = data.getData ();
                        try {
                            cropCapturedImage (selectedImage, PICK_FROM_GALLERY_BEFORE_IMAGE_1);
                        } catch (ActivityNotFoundException aNFE) {
                            Toast.makeText (this, "Sorry - your device doesn't support the crop action!", Toast.LENGTH_SHORT).show ();
                        }
                        break;
                    case PICK_FROM_GALLERY_BEFORE_IMAGE_2:
                        Bundle extras3 = data.getExtras ();
                        Bitmap thePic3 = extras3.getParcelable ("data");
                        Constants.workOrderDetail.setBefore_image_str (Utils.bitmapToBase64 (thePic3));
                        ivBeforeImage.setImageBitmap (thePic3);
                        break;
                    case PICK_FROM_GALLERY_AFTER_IMAGE_1:
                        Uri selectedImage2 = data.getData ();
                        try {
                            cropCapturedImage (selectedImage2, PICK_FROM_GALLERY_AFTER_IMAGE_1);
                        } catch (ActivityNotFoundException aNFE) {
                            Toast.makeText (this, "Sorry - your device doesn't support the crop action!", Toast.LENGTH_SHORT).show ();
                        }
                        break;
                    case PICK_FROM_GALLERY_AFTER_IMAGE_2:
                        Bundle extras4 = data.getExtras ();
                        Bitmap thePic4 = extras4.getParcelable ("data");
                        Constants.workOrderDetail.setAfter_image_str (Utils.bitmapToBase64 (thePic4));
                        ivAfterImage.setImageBitmap (thePic4);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public void cropCapturedImage (Uri picUri, int flag) {
        try {
            Intent cropIntent = new Intent ("com.android.camera.action.CROP");
            cropIntent.setDataAndType (picUri, "image/*");
            cropIntent.putExtra ("crop", "true");
            cropIntent.putExtra ("aspectX", 4);
            cropIntent.putExtra ("aspectY", 3);
            cropIntent.putExtra ("outputX", 512);
            cropIntent.putExtra ("outputY", 512);
            cropIntent.putExtra ("return-data", true);
            switch (flag) {
                case PICK_FROM_CAMERA_BEFORE_IMAGE_1:
                    startActivityForResult (cropIntent, PICK_FROM_CAMERA_BEFORE_IMAGE_2);
                    break;
                case PICK_FROM_CAMERA_AFTER_IMAGE_1:
                    startActivityForResult (cropIntent, PICK_FROM_CAMERA_AFTER_IMAGE_2);
                    break;
                case PICK_FROM_CAMERA_LIST_ITEM_1:
                    startActivityForResult (cropIntent, PICK_FROM_CAMERA_LIST_ITEM_2);
                    break;

                case PICK_FROM_GALLERY_BEFORE_IMAGE_1:
                    startActivityForResult (cropIntent, PICK_FROM_GALLERY_BEFORE_IMAGE_2);
                    break;
                case PICK_FROM_GALLERY_AFTER_IMAGE_1:
                    startActivityForResult (cropIntent, PICK_FROM_GALLERY_AFTER_IMAGE_2);
                    break;
                case PICK_FROM_GALLERY_LIST_ITEM_1:
                    startActivityForResult (cropIntent, PICK_FROM_GALLERY_LIST_ITEM_2);
                    break;
            }
        } catch (Exception e) {
            Toast.makeText (DetailActivityBackup.this, "Your device doesn't support the crop action!", Toast.LENGTH_SHORT).show ();
        }
    }

    public boolean validate () {
        if (etTimeIn.getText ().toString ().length () == 0) {
            Utils.showToast (DetailActivityBackup.this, "Please set the time in");
            return false;
        } else if (etOnSiteContact.getText ().toString ().length () == 0) {
            Utils.showToast (DetailActivityBackup.this, "Please enter onsite contact");
            return false;
        } else if (etEmail.getText ().toString ().length () == 0) {
            Utils.showToast (DetailActivityBackup.this, "Please enter the email");
            return false;
        } else if (Constants.workOrderDetail.getBefore_image_str ().length () == 0) {
            Utils.showToast (DetailActivityBackup.this, "Please set the before image");
            return false;
        } else if (etGeneratorModel.getText ().toString ().length () == 0) {
            Utils.showToast (DetailActivityBackup.this, "Please enter the generator model");
            return false;
        } else if (Constants.workOrderDetail.getGenerator_make_id () == 0) {
            Utils.showToast (DetailActivityBackup.this, "Please select a generator make");
            return false;
        } else if (etGeneratorSerial.getText ().toString ().length () == 0) {
            Utils.showToast (DetailActivityBackup.this, "Please enter the generator serial");
            return false;
        } else if (etEngineModel.getText ().toString ().length () == 0) {
            Utils.showToast (DetailActivityBackup.this, "Please enter the engine model");
            return false;
        } else if (etKwRating.getText ().toString ().length () == 0) {
            Utils.showToast (DetailActivityBackup.this, "Please enter the Kw Rating");
            return false;
        } else if (etEngineSerial.getText ().toString ().length () == 0) {
            Utils.showToast (DetailActivityBackup.this, "Please enter the engine serial");
            return false;
        } else if (Constants.workOrderDetail.getAts_make_id () == 0) {
            Utils.showToast (DetailActivityBackup.this, "Please select a ATS make");
            return false;
        } else if (etGeneratorConditionComment.getText ().toString ().length () == 0) {
            Utils.showToast (DetailActivityBackup.this, "Please enter the generator rating");
            return false;
        } else if (etComment.getText ().toString ().length () == 0) {
            Utils.showToast (DetailActivityBackup.this, "Please enter a comment");
            return false;
        } else if (Constants.workOrderDetail.getAfter_image_str ().length () == 0) {
            Utils.showToast (DetailActivityBackup.this, "Please select an after image");
            return false;
        } else if (Constants.workOrderDetail.getTech_image_str ().length () == 0) {
            Utils.showToast (DetailActivityBackup.this, "Please select tech signature");
            return false;
        } else if (Constants.workOrderDetail.getCustomer_signature ().length () == 0) {
            Utils.showToast (DetailActivityBackup.this, "Please select customer signature");
            return false;
        } else if (etTimeOut.getText ().toString ().length () == 0) {
            Utils.showToast (DetailActivityBackup.this, "Please set time out");
            return false;
        } else {
            return true;
        }
    }

    private class LoadServiceChecks extends AsyncTask<String, Void, String> {

        Activity activity;

        LoadServiceChecks (Activity activity) {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute () {
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


            for (int i = 0; i < Constants.serviceCheckList.size (); i++) {
                ServiceCheck serviceCheck = Constants.serviceCheckList.get (i);
                switch (serviceCheck.getGroup_id ()) {
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


        }

        @Override
        protected String doInBackground (String... params) {
            FuelSystemGovernorAdapter = new ServiceCheckAdapter (activity, FuelSystemGovernorList, 1, 0);
            PreStartChecksCoolingSystemAdapter = new ServiceCheckAdapter (activity, PreStartChecksCoolingSystemList, 2, 7);
            LubricationSystemAdapter = new ServiceCheckAdapter (activity, LubricationSystemList, 3, 16);
            AirSystemAdapter = new ServiceCheckAdapter (activity, AirSystemList, 4, 18);
            ExhaustSystemAdapter = new ServiceCheckAdapter (activity, ExhaustSystemList, 5, 20);
            GeneratorAdapter = new ServiceCheckAdapter (activity, GeneratorList, 6, 22);
            ControlPanelCabinetsAdapter = new ServiceCheckAdapter (activity, ControlPanelCabinetsList, 7, 24);
            ATSMainAdapter = new ServiceCheckAdapter (activity, ATSMainList, 8, 26);
            StartingSystemAdapter = new ServiceCheckAdapter (activity, StartingSystemList, 9, 29);
            GeneratorEnclosureAdapter = new ServiceCheckAdapter (activity, GeneratorEnclosureList, 10, 33);
            StartupAndRunningCheckAdapter = new ServiceCheckAdapter (activity, StartupAndRunningCheckList, 11, 37);
            ScheduledMaintenanceAdapter = new ServiceCheckAdapter (activity, ScheduledMaintenanceList, 12, 53);
            return "Executed";
        }

        @Override
        protected void onPostExecute (String result) {
            lvFuelSystem.setExpanded (true);
            lvPreStartChecksCoolingSystem.setExpanded (true);
            lvLubricationSystem.setExpanded (true);
            lvAirSystem.setExpanded (true);
            lvExhaustSystem.setExpanded (true);
            lvGenerator.setExpanded (true);
            lvControlPanelCabinets.setExpanded (true);
            lvATSMain.setExpanded (true);
            lvStartingSystem.setExpanded (true);
            lvGeneratorEnclosure.setExpanded (true);
            lvStartupAndRunningCheck.setExpanded (true);
            lvScheduledMaintenance.setExpanded (true);

            lvFuelSystem.setAdapter (FuelSystemGovernorAdapter);
            lvPreStartChecksCoolingSystem.setAdapter (PreStartChecksCoolingSystemAdapter);
            lvLubricationSystem.setAdapter (LubricationSystemAdapter);
            lvAirSystem.setAdapter (AirSystemAdapter);
            lvExhaustSystem.setAdapter (ExhaustSystemAdapter);
            lvGenerator.setAdapter (GeneratorAdapter);
            lvControlPanelCabinets.setAdapter (ControlPanelCabinetsAdapter);
            lvATSMain.setAdapter (ATSMainAdapter);
            lvStartingSystem.setAdapter (StartingSystemAdapter);
            lvGeneratorEnclosure.setAdapter (GeneratorEnclosureAdapter);
            lvStartupAndRunningCheck.setAdapter (StartupAndRunningCheckAdapter);
            lvScheduledMaintenance.setAdapter (ScheduledMaintenanceAdapter);


//            FuelSystemGovernorAdapter.notifyDataSetChanged ();
//            PreStartChecksCoolingSystemAdapter.notifyDataSetChanged ();
//            LubricationSystemAdapter.notifyDataSetChanged ();
//            AirSystemAdapter.notifyDataSetChanged ();
//            ExhaustSystemAdapter.notifyDataSetChanged ();
//            GeneratorAdapter.notifyDataSetChanged ();
//            ControlPanelCabinetsAdapter.notifyDataSetChanged ();
//            ATSMainAdapter.notifyDataSetChanged ();
//            StartingSystemAdapter.notifyDataSetChanged ();
//            GeneratorEnclosureAdapter.notifyDataSetChanged ();
//            StartupAndRunningCheckAdapter.notifyDataSetChanged ();
//            ScheduledMaintenanceAdapter.notifyDataSetChanged ();

            Utils.hideSoftKeyboard (activity);
            // txt.setText(result);
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }
    }


}