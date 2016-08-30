package com.actiknow.motoraudit.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.actiknow.motoraudit.R;
import com.actiknow.motoraudit.adapter.ManufacturerAdapter;
import com.actiknow.motoraudit.helper.DatabaseHandler;
import com.actiknow.motoraudit.model.ImageDetail;
import com.actiknow.motoraudit.model.Serial;
import com.actiknow.motoraudit.model.ServiceCheck;
import com.actiknow.motoraudit.model.WorkOrderDetail;
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
import com.kyanogen.signatureview.SignatureView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final int BEFORE_IMAGE_PICKER = 1;
    public static final int AFTER_IMAGE_PICKER = 2;
    public static final int LIST_ITEM_PICKER = 3;

    public static final int PICK_FROM_CAMERA_BEFORE_IMAGE = 11;
    public static final int PICK_FROM_GALLERY_BEFORE_IMAGE = 12;
    public static final int PICK_FROM_CAMERA_AFTER_IMAGE = 21;
    public static final int PICK_FROM_GALLERY_AFTER_IMAGE = 22;
    public static final int PICK_FROM_CAMERA_LIST_ITEM = 31;
    public static final int PICK_FROM_GALLERY_LIST_ITEM = 32;

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
    LinearLayout llBeforeImage;
    TextView tvAddBeforeImage;

    RelativeLayout rlGeneratorInfo;
    EditText etGeneratorModel;
    EditText etGeneratorSerial;
    EditText etKwRating;
    EditText etGeneratorMake;
    Spinner spGeneratorMake;

    RelativeLayout rlEngineSerial;
    LinearLayout llEngineSerial;
    TextView tvAddEngineSerial;

    RelativeLayout rlATSSerial;
    LinearLayout llATSSerial;
    TextView tvAddATSSerial;

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
    RadioGroup rgOverAllCondition;
    RadioButton rbGood;
    RadioButton rbFair;
    RadioButton rbPoor;
    EditText etGeneratorConditionComment;
    TextView tvConditionDetails;

    RelativeLayout rlComment;
    EditText etComment;

    RelativeLayout rlAfterImage;
    LinearLayout llAfterImage;
    TextView tvAfterImage;

    RelativeLayout rlTechSignature;
    ImageView ivTechSignature;

    RelativeLayout rlCustomerSignature;
    EditText etCustomerName;
    ImageView ivCustomerSignature;

    TextView tvTimeOutSet;
    EditText etTimeOut;

    ManufacturerAdapter adapter;

    ArrayList<String> listGoodCondition = new ArrayList<String> ();
    ArrayList<String> listFairCondition = new ArrayList<String> ();
    ArrayList<String> listPoorCondition = new ArrayList<String> ();

    Dialog dialogSign;
    Dialog dialogAddNewSerial;
    ProgressDialog pDialog;

    ManufacturerAdapter manufacturerAdapter;
    DatabaseHandler db;
    WorkOrderDetail offlineServiceForm;
    private List<Serial> engineSerialList = new ArrayList<> ();
    private List<Serial> atsSerialList = new ArrayList<> ();
    private List<ImageDetail> beforeImageList = new ArrayList<> ();
    private List<ImageDetail> afterImageList = new ArrayList<> ();
    private List<ImageDetail> signatureImageList = new ArrayList<> ();
    private int smCheckIdTemp = 0;
    private int formIdTemp = 0;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_detail_screen);

        pDialog = new ProgressDialog (DetailActivity.this);
        Utils.showProgressDialog (pDialog, null);

        initView ();
        initData ();
        initListener ();
        initAdapter ();
        getWorkOrderDetailFromServer (Constants.workOrderDetail.getWork_order_id (), Constants.workOrderDetail.getGenerator_serial_id ());
        Utils.hideSoftKeyboard (this);

        db.closeDB ();
        Runtime rt = Runtime.getRuntime ();
        long maxMemory = rt.maxMemory ();
        Utils.showLog (Log.DEBUG, "Max Memory ", "" + maxMemory, true);

    }

    private void initView () {
        tvWorkOrderDescription = (TextView) findViewById (R.id.tvWorkOrderDescription);
        llGeneralInfoTop = (LinearLayout) findViewById (R.id.llGeneralInfoTop);
        tvSet = (TextView) findViewById (R.id.tvSet);
        etTimeIn = (EditText) findViewById (R.id.etTimeIn);
        etOnSiteContact = (EditText) findViewById (R.id.etOnSiteContact);
        etEmail = (EditText) findViewById (R.id.etEmail);

        rlBeforeImage = (RelativeLayout) findViewById (R.id.rlBeforeImage);
        llBeforeImage = (LinearLayout) findViewById (R.id.llBeforeImage);
        tvAddBeforeImage = (TextView) findViewById (R.id.tvAddBeforeImage);

        rlGeneratorInfo = (RelativeLayout) findViewById (R.id.rlGeneratorInfo);
        etGeneratorModel = (EditText) findViewById (R.id.etGeneratorModel);
        etGeneratorSerial = (EditText) findViewById (R.id.etGeneratorSerial);
        etKwRating = (EditText) findViewById (R.id.etKwRating);
        spGeneratorMake = (Spinner) findViewById (R.id.spGeneratorMake);
        etGeneratorMake = (EditText) findViewById (R.id.etGeneratorMake);

        rlEngineSerial = (RelativeLayout) findViewById (R.id.rlEngineSerial);
        llEngineSerial = (LinearLayout) findViewById (R.id.llEngineSerial);
        tvAddEngineSerial = (TextView) findViewById (R.id.tvAddEngineSerial);

        rlATSSerial = (RelativeLayout) findViewById (R.id.rlAtsSerial);
        llATSSerial = (LinearLayout) findViewById (R.id.llAtsSerial);
        tvAddATSSerial = (TextView) findViewById (R.id.tvAddAtsSerial);

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
        rgOverAllCondition = (RadioGroup) findViewById (R.id.rgOverAllCondition);
        rbFair = (RadioButton) findViewById (R.id.rbFair);
        rbGood = (RadioButton) findViewById (R.id.rbGood);
        rbPoor = (RadioButton) findViewById (R.id.rbPoor);
        etGeneratorConditionComment = (EditText) findViewById (R.id.etGeneratorConditionComment);

        rlComment = (RelativeLayout) findViewById (R.id.rlComment);
        etComment = (EditText) findViewById (R.id.etComment);

        rlAfterImage = (RelativeLayout) findViewById (R.id.rlAfterImage);
        llAfterImage = (LinearLayout) findViewById (R.id.llAfterImage);
        tvAfterImage = (TextView) findViewById (R.id.tvAddAfterImage);

        rlTechSignature = (RelativeLayout) findViewById (R.id.rlTechSignature);
        ivTechSignature = (ImageView) findViewById (R.id.ivTechSignature);

        rlCustomerSignature = (RelativeLayout) findViewById (R.id.rlCustomerSignature);
        etCustomerName = (EditText) findViewById (R.id.etCustomerName);
        ivCustomerSignature = (ImageView) findViewById (R.id.ivCustomerSignature);

        tvCancel = (TextView) findViewById (R.id.tvCancel);
        tvUpdate = (TextView) findViewById (R.id.tvUpdate);
        tvTimeOutSet = (TextView) findViewById (R.id.tvTimeOutSet);
        etTimeOut = (EditText) findViewById (R.id.etTimeOut);
    }

    private void initListener () {
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
                    finish ();
                }
                return true;
            }
        });


        tvUpdate.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    if (validate ()) {
                        pDialog = new ProgressDialog (DetailActivity.this);
                        Utils.showProgressDialog (pDialog, null);

                        JSONObject smChecksJSON = new JSONObject ();
                        JSONArray jsonArraySmChecks = new JSONArray ();
                        try {
                            for (int i = 0; i < Constants.serviceCheckList.size (); i++) {
                                final ServiceCheck serviceCheck;
                                serviceCheck = Constants.serviceCheckList.get (i);
                                JSONObject jsonObject = new JSONObject ();
                                jsonObject.put ("name_id", String.valueOf (serviceCheck.getService_check_id ()));
                                jsonObject.put ("status", serviceCheck.getSelection_text ());


                                JSONObject jsonObject1 = new JSONObject ();

                                if (serviceCheck.getSmCheckImageList ().size () > 0) {
                                    JSONArray jsonArraySmCheckImage = new JSONArray ();
                                    for (int j = 0; j < serviceCheck.getSmCheckImageList ().size (); j++) {
                                        ImageDetail smCheckImageDetail = serviceCheck.getSmCheckImageList ().get (j);
                                        jsonObject1.put ("64_image", smCheckImageDetail.getImage_str ());
//                                        jsonObject1.put ("64_image", "helo");
                                        jsonObject1.put ("file_name", smCheckImageDetail.getFile_name ());
                                        jsonArraySmCheckImage.put (jsonObject1);
                                    }
                                    jsonObject.put ("imageId", jsonArraySmCheckImage);
                                } else {
                                    jsonObject.put ("imageId", 0);
                                }

                                jsonObject.put ("text", serviceCheck.getComment ());
                                jsonArraySmChecks.put (jsonObject);
                            }
                            smChecksJSON.put ("smchecks", jsonArraySmChecks);
                        } catch (JSONException e) {
                            Utils.showLog (Log.ERROR, "JSON EXCEPTION", e.getMessage (), true);
                        }

                        JSONObject engineSerialCheckJSON = new JSONObject ();
                        JSONArray jsonArrayEngineSerialCheck = new JSONArray ();
                        try {
                            for (int i = 0; i < engineSerialList.size (); i++) {
                                Serial engineSerial = engineSerialList.get (i);
                                JSONObject jsonObject = new JSONObject ();
                                if (engineSerial.isChecked ())
                                    jsonObject.put ("checkval", 1);
                                else
                                    jsonObject.put ("checkval", 0);

                                jsonObject.put ("serid", engineSerial.getSerial_id ());
                                jsonArrayEngineSerialCheck.put (jsonObject);
                            }
                            engineSerialCheckJSON.put ("engSerialCheck", jsonArrayEngineSerialCheck);
                        } catch (JSONException e) {
                            Utils.showLog (Log.ERROR, "JSON EXCEPTION", e.getMessage (), true);
                        }

                        JSONObject atsSerialCheckJSON = new JSONObject ();
                        JSONArray jsonArrayATSSerialCheck = new JSONArray ();
                        try {
                            for (int i = 0; i < atsSerialList.size (); i++) {
                                Serial atsSerial = atsSerialList.get (i);
                                JSONObject jsonObject = new JSONObject ();
                                if (atsSerial.isChecked ())
                                    jsonObject.put ("checkval", 1);
                                else
                                    jsonObject.put ("checkval", 0);

                                jsonObject.put ("serid", atsSerial.getSerial_id ());
                                jsonArrayATSSerialCheck.put (jsonObject);
                            }
                            atsSerialCheckJSON.put ("atsSerialCheck", jsonArrayATSSerialCheck);
                        } catch (JSONException e) {
                            Utils.showLog (Log.ERROR, "JSON EXCEPTION", e.getMessage (), true);
                        }


                        JSONObject beforeImagesJSON = new JSONObject ();
                        JSONArray jsonArrayBeforeImages = new JSONArray ();
                        try {
                            for (int i = 0; i < beforeImageList.size (); i++) {
                                ImageDetail beforeImageDetail = beforeImageList.get (i);
                                JSONObject jsonObject = new JSONObject ();

                                jsonObject.put ("file_name", beforeImageDetail.getFile_name ());
                                jsonObject.put ("64_image", beforeImageDetail.getImage_str ());
//                                jsonObject.put ("64_image", "helo");

                                jsonArrayBeforeImages.put (jsonObject);
                            }
                            beforeImagesJSON.put ("beforeImages", jsonArrayBeforeImages);
                        } catch (JSONException e) {
                            Utils.showLog (Log.ERROR, "JSON EXCEPTION", e.getMessage (), true);
                        }

                        JSONObject afterImagesJSON = new JSONObject ();
                        JSONArray jsonArrayAfterImages = new JSONArray ();
                        try {
                            for (int i = 0; i < afterImageList.size (); i++) {
                                ImageDetail afterImageDetail = afterImageList.get (i);
                                JSONObject jsonObject = new JSONObject ();

                                jsonObject.put ("file_name", afterImageDetail.getFile_name ());
                                jsonObject.put ("64_image", afterImageDetail.getImage_str ());
//                                jsonObject.put ("64_image", "helo");

                                jsonArrayAfterImages.put (jsonObject);
                            }
                            afterImagesJSON.put ("afterImages", jsonArrayAfterImages);
                        } catch (JSONException e) {
                            Utils.showLog (Log.ERROR, "JSON EXCEPTION", e.getMessage (), true);
                        }

                        JSONObject signatureImagesJSON = new JSONObject ();
                        JSONArray jsonArraySignatureImages = new JSONArray ();
                        try {
                            for (int i = 0; i < signatureImageList.size (); i++) {
                                ImageDetail signatureImageDetail = signatureImageList.get (i);
                                JSONObject jsonObject = new JSONObject ();

                                jsonObject.put ("description", signatureImageDetail.getDescription ());
                                jsonObject.put ("signator", signatureImageDetail.getCustomer_name ());
                                jsonObject.put ("64_image", signatureImageDetail.getImage_str ());
//                                jsonObject.put ("64_image", "helo");

                                jsonArraySignatureImages.put (jsonObject);
                            }
                            signatureImagesJSON.put ("signatures", jsonArraySignatureImages);
                        } catch (JSONException e) {
                            Utils.showLog (Log.ERROR, "JSON EXCEPTION", e.getMessage (), true);
                        }


//                    Constants.workOrderDetail.setEngineSerialList (engineSerialList);
//                    Constants.workOrderDetail.setAtsSerialList (atsSerialList);


                        Constants.workOrderDetail.setAts_serial_json (String.valueOf (atsSerialCheckJSON));
                        Constants.workOrderDetail.setEngine_serial_json (String.valueOf (engineSerialCheckJSON));
                        Constants.workOrderDetail.setService_check_json (String.valueOf (smChecksJSON));
                        Constants.workOrderDetail.setBefore_image_list_json (String.valueOf (beforeImagesJSON));
                        Constants.workOrderDetail.setAfter_image_list_json (String.valueOf (afterImagesJSON));
                        Constants.workOrderDetail.setSignature_image_list_json (String.valueOf (signatureImagesJSON));

                        Constants.workOrderDetail.setEmp_id (Constants.employee_id);
                        Constants.workOrderDetail.setTime_in (etTimeIn.getText ().toString ());
                        Constants.workOrderDetail.setTime_out (etTimeOut.getText ().toString ());
                        Constants.workOrderDetail.setOnsite_contact (etOnSiteContact.getText ().toString ());
                        Constants.workOrderDetail.setEmail (etEmail.getText ().toString ());
                        Constants.workOrderDetail.setKw_rating (etKwRating.getText ().toString ());
                        Constants.workOrderDetail.setGenerator_condition_comment (etGeneratorConditionComment.getText ().toString ());
                        Constants.workOrderDetail.setComments (etComment.getText ().toString ());


                        Utils.showLog (Log.INFO, "WORK ORDER FORM ID", "" + Constants.workOrderDetail.getForm_id (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER NUMBER", "" + Constants.workOrderDetail.getWork_order_id (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER CONTRACT NUMBER", "" + Constants.workOrderDetail.getContract_number (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER GENERATOR SERIAL ID", "" + Constants.workOrderDetail.getGenerator_serial_id (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER CUSTOMER NAME", "" + Constants.workOrderDetail.getCustomer_name (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER TIME IN", Constants.workOrderDetail.getTime_in (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER ONSITE CONTACT", Constants.workOrderDetail.getOnsite_contact (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER EMAIL", Constants.workOrderDetail.getEmail (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER GENERATOR SERIAL", Constants.workOrderDetail.getGenerator_serial (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER GENERATOR MAKE ID", "" + Constants.workOrderDetail.getGenerator_make_id (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER GENERATOR MAKE NAME", Constants.workOrderDetail.getGenerator_make_name (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER GENERATOR MODEL", Constants.workOrderDetail.getGenerator_model (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER KW RATING", Constants.workOrderDetail.getKw_rating (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER ENGINE CHECK JSON", "" + Constants.workOrderDetail.getEngine_serial_json (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER ATS CHECK JSON", "" + Constants.workOrderDetail.getAts_serial_json (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER SERVICE CHECK JSON", "" + Constants.workOrderDetail.getService_check_json (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER GENERATOR STATUS", Constants.workOrderDetail.getGenerator_condition_text (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER GENERATOR CONDITION COMMENT", Constants.workOrderDetail.getGenerator_condition_comment (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER COMMENTS", Constants.workOrderDetail.getComments (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER TIME OUT", Constants.workOrderDetail.getTime_out (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER BEFORE IMAGES", Constants.workOrderDetail.getBefore_image_list_json (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER AFTER IMAGES", Constants.workOrderDetail.getAfter_image_list_json (), true);
                        Utils.showLog (Log.INFO, "WORK ORDER SIGNATURE IMAGES", Constants.workOrderDetail.getSignature_image_list_json (), true);


//                        pDialog = new ProgressDialog (DetailActivity.this);
//                        Utils.showProgressDialog (pDialog, null);

                        sendFormDetailToServer ();

//                        createResponse ();


                    }
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
                showSignatureDialog (1, 0);
            }
        });

        ivCustomerSignature.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                if (etCustomerName.getText ().toString ().length () == 0) {
                    etCustomerName.setError ("Please enter the name");
                    Utils.shakeView (DetailActivity.this, etCustomerName);
                } else {
                    showSignatureDialog (2, 0);
                }
            }
        });

        tvAddBeforeImage.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    tvAddBeforeImage.setBackgroundResource (R.color.background_button_green_pressed);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    tvAddBeforeImage.setBackgroundResource (R.color.background_button_green);
                    selectImage (BEFORE_IMAGE_PICKER);

                }
                return true;
            }
        });

        tvAfterImage.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    tvAfterImage.setBackgroundResource (R.color.background_button_green_pressed);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    tvAfterImage.setBackgroundResource (R.color.background_button_green);
                    selectImage (AFTER_IMAGE_PICKER);
                }
                return true;
            }
        });

        tvAddEngineSerial.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    tvAddEngineSerial.setBackgroundResource (R.color.background_button_green_pressed);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    tvAddEngineSerial.setBackgroundResource (R.color.background_button_green);
                    if (NetworkConnection.isNetworkAvailable (DetailActivity.this)) {
                        showSaveSerialDialog (0, Constants.workOrderDetail.getContract_number (), "Engine");
                    } else {
                        Utils.showOkDialog (DetailActivity.this, "Sorry you cannot add a new serial in Offline mode", false);
                    }

                }
                return true;
            }
        });

        tvAddATSSerial.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    tvAddATSSerial.setBackgroundResource (R.color.background_button_green_pressed);
                } else if (event.getAction () == MotionEvent.ACTION_UP) {
                    tvAddATSSerial.setBackgroundResource (R.color.background_button_green);
                    if (NetworkConnection.isNetworkAvailable (DetailActivity.this)) {
                        showSaveSerialDialog (0, Constants.workOrderDetail.getContract_number (), "ATS");
                    } else {
                        Utils.showOkDialog (DetailActivity.this, "Sorry you cannot add a new serial in Offline mode", false);
                    }
                }
                return true;
            }
        });
    }

    private void initData () {
        db = new DatabaseHandler (getApplicationContext ());
        Utils.clearAllServiceChecks ();
        //    Utils.setTypefaceToAllViews (this, tvNoInternetConnection);
        client = new GoogleApiClient.Builder (this).addApi (AppIndex.API).build ();
        Utils.hideKeyboard (DetailActivity.this, etOnSiteContact);

        Constants.workOrderDetail.setForm_id (0);
        getContractSerialsFromServer (Constants.workOrderDetail.getWork_order_id ());
        etGeneratorModel.setText (Constants.workOrderDetail.getGenerator_model ());
        etGeneratorSerial.setText (Constants.workOrderDetail.getGenerator_serial ());
        etGeneratorMake.setText (Constants.workOrderDetail.getGenerator_make_name ());

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
            TableRow row = (TableRow) LayoutInflater.from (DetailActivity.this).inflate (R.layout.table_row_condition, null);
            tlGood.setGravity (Gravity.CENTER_VERTICAL);
            tvConditionDetails = (TextView) row.findViewById (R.id.tvConditionDetails);
            tvConditionDetails.setText ("" + GoodConditionText);
            tlGood.addView (row);
        }
        for (int i = 0; i < listFairCondition.size (); i++) {
            String FairConditionText = listFairCondition.get (i);
            TableRow row = (TableRow) LayoutInflater.from (DetailActivity.this).inflate (R.layout.table_row_condition, null);
            tlFair.setGravity (Gravity.CENTER_VERTICAL);
            tvConditionDetails = (TextView) row.findViewById (R.id.tvConditionDetails);
            tvConditionDetails.setText ("" + FairConditionText);
            tlFair.addView (row);
        }
        for (int i = 0; i < listPoorCondition.size (); i++) {
            String PoorConditionText = listPoorCondition.get (i);
            TableRow row = (TableRow) LayoutInflater.from (DetailActivity.this).inflate (R.layout.table_row_condition, null);
            tlPoor.setGravity (Gravity.CENTER_VERTICAL);
            tvConditionDetails = (TextView) row.findViewById (R.id.tvConditionDetails);
            tvConditionDetails.setText ("" + PoorConditionText);
            tlPoor.addView (row);
        }
        Constants.workOrderDetail.setGenerator_condition_flag (2);
        Constants.workOrderDetail.setGenerator_condition_text ("GOOD");
        tvWorkOrderDescription.setText ("WO#" + Constants.workOrderDetail.getWork_order_id () + " for " + Constants.workOrderDetail.getSite_name ());


    }

    private void initAdapter () {
        adapter = new ManufacturerAdapter (this, R.layout.spinner_item, Constants.manufacturerList);
//        spAtsMake.setAdapter(adapter);
        spGeneratorMake.setAdapter (adapter);
    }

    private void sendFormDetailToServer () {
        if (NetworkConnection.isNetworkAvailable (this)) {
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.API_URL, true);
            StringRequest strRequest = new StringRequest (Request.Method.POST, AppConfigURL.API_URL,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            pDialog.dismiss ();
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    switch (jsonObj.getInt ("error_code")) {
                                        case 0:
                                            Utils.showToast (DetailActivity.this, jsonObj.getString ("error_message") + " Form id :" + jsonObj.getString ("formId"));

                                            Intent intent = new Intent (DetailActivity.this, MainActivity.class);
                                            intent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity (intent);
                                            finish ();
                                            break;
                                        default:
                                            Utils.showToast (DetailActivity.this, jsonObj.getString ("error_message"));
                                            break;
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
                            pDialog.dismiss ();
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                        }
                    }) {
                @Override
                public byte[] getBody () throws com.android.volley.AuthFailureError {

                    String str = "{\"API_username\":\"" + Constants.api_username + "\",\n" +
                            "\"API_password\":\"" + Constants.api_password + "\",\n" +
                            "\"API_function\":\"saveFormData\",\n" +
                            "\"API_parameters\":" + createResponse () + "}";
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, str, true);
                    return str.getBytes ();
                }

                public String getBodyContentType () {
                    return "application/json; charset=utf-8";
                }
            };
            Utils.sendRequest (strRequest, 30);

        } else {
            pDialog.dismiss ();
//            Utils.showOkDialog (DetailActivity.this, "Seems like there is no internet connection, the app will continue in Offline mode", false);

            if (db.isOfflineServiceFormPresent (Constants.workOrderDetail.getWork_order_id (), Constants.workOrderDetail.getGenerator_serial_id ())) {
                db.updateServiceForm (Constants.workOrderDetail);
            } else {
                db.createServiceForm (Constants.workOrderDetail);
            }

            AlertDialog.Builder builder = new AlertDialog.Builder (DetailActivity.this);
            builder.setMessage ("Form detail have been saved offline and will be uploaded once the network connection is available")
                    .setCancelable (false)
                    .setPositiveButton ("OK", new DialogInterface.OnClickListener () {
                        public void onClick (DialogInterface dialog, int id) {
                            dialog.dismiss ();
                            Intent intent = new Intent (DetailActivity.this, MainActivity.class);
                            intent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity (intent);
                            finish ();
                        }
                    });
            AlertDialog alert = builder.create ();
            alert.show ();
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
        db.closeDB ();
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

    private void showSignatureDialog (final int flag, final int image_id) {
        Button btSignCancel;
        Button btSignClear;
        Button btSignSave;
        final SignatureView signatureView;

        dialogSign = new Dialog (DetailActivity.this);
        dialogSign.setContentView (R.layout.dialog_signature);
        dialogSign.setCancelable (false);
        btSignCancel = (Button) dialogSign.findViewById (R.id.btSignCancel);
        btSignClear = (Button) dialogSign.findViewById (R.id.btSignClear);
        btSignSave = (Button) dialogSign.findViewById (R.id.btSignSave);
        signatureView = (SignatureView) dialogSign.findViewById (R.id.signSignatureView);

        Utils.setTypefaceToAllViews (DetailActivity.this, btSignCancel);
//        dialogSign.requestWindowFeature (Window.FEATURE_NO_TITLE);
        dialogSign.getWindow ().setBackgroundDrawable (new ColorDrawable (android.graphics.Color.TRANSPARENT));
        dialogSign.show ();
        btSignCancel.setOnClickListener (new View.OnClickListener () {
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
        DateFormat df = new SimpleDateFormat ("yyyyMMdd_HHmmss");
        final String date = df.format (Calendar.getInstance ().getTime ());

        btSignSave.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                dialogSign.dismiss ();
                Bitmap bp = signatureView.getSignatureBitmap ();
                switch (flag) {
                    case 1:

                        for (int i = 0; i < signatureImageList.size (); i++) {
                            ImageDetail imageDetail = signatureImageList.get (i);
                            if (imageDetail.getDescription ().equalsIgnoreCase ("Tech")) {
                                signatureImageList.remove (i);
                            }
                        }


                        ImageDetail signatureImageDetail = new ImageDetail (image_id, Utils.bitmapToBase64 (bp), "", "Tech", "", "");
                        signatureImageList.add (signatureImageDetail);

//                        Constants.workOrderDetail.setImageInList (imageDetail);

                        //      Constants.workOrderDetail.setTech_image_str(Utils.bitmapToBase64(bp));
                        ivTechSignature.setImageBitmap (bp);
                        break;
                    case 2:
                        for (int i = 0; i < signatureImageList.size (); i++) {
                            ImageDetail imageDetail = signatureImageList.get (i);
                            if (imageDetail.getDescription ().equalsIgnoreCase ("Customer")) {
                                signatureImageList.remove (i);
                            }
                        }

                        ImageDetail signatureImageDetail2 = new ImageDetail (image_id, Utils.bitmapToBase64 (bp), "", "Customer", etCustomerName.getText ().toString (), "");
                        signatureImageList.add (signatureImageDetail2);

//                        Constants.workOrderDetail.setImageInList (imageDetail2);

//                        Constants.workOrderDetail.setCustomer_signature(Utils.bitmapToBase64(bp));

                        ivCustomerSignature.setImageBitmap (bp);
                        break;
                }
            }
        });
    }

    private boolean selectImage (final int flag) {

        final boolean[] cancel_flag = new boolean[1];

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
//                            intent.putExtra (MediaStore.EXTRA_OUTPUT, Uri.fromFile (f));
                            intent.putExtra (MediaStore.EXTRA_OUTPUT, Uri.fromFile (f));
                            startActivityForResult (intent, PICK_FROM_CAMERA_BEFORE_IMAGE);
                            break;
                        case AFTER_IMAGE_PICKER:
//                            intent.putExtra (MediaStore.EXTRA_OUTPUT, Uri.fromFile (f));
                            intent.putExtra (MediaStore.EXTRA_OUTPUT, Uri.fromFile (f));
                            startActivityForResult (intent, PICK_FROM_CAMERA_AFTER_IMAGE);
                            break;
                        case LIST_ITEM_PICKER:
//                            intent.putExtra (MediaStore.EXTRA_OUTPUT, Uri.fromFile (f));
                            intent.putExtra (MediaStore.EXTRA_OUTPUT, Uri.fromFile (f));
                            startActivityForResult (intent, PICK_FROM_CAMERA_LIST_ITEM);
                            break;
                    }
                    cancel_flag[0] = true;

                } else if (options[item].equals ("Choose from Gallery")) {
                    switch (flag) {
                        case BEFORE_IMAGE_PICKER:
                            Intent i = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult (i, PICK_FROM_GALLERY_BEFORE_IMAGE);
                            break;
                        case AFTER_IMAGE_PICKER:
                            Intent i2 = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult (i2, PICK_FROM_GALLERY_AFTER_IMAGE);
                            break;
                        case LIST_ITEM_PICKER:
                            Intent i3 = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult (i3, PICK_FROM_GALLERY_LIST_ITEM);
                            break;

                    }

                    cancel_flag[0] = true;

                } else if (options[item].equals ("Cancel")) {
                    dialog.dismiss ();
                    cancel_flag[0] = false;
                }
            }
        });
        builder.show ();


        if (cancel_flag[0]) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        DateFormat df = new SimpleDateFormat ("yyyyMMdd_HHmmss");
        final String date = df.format (Calendar.getInstance ().getTime ());
        try {
            if (resultCode == RESULT_OK) {
                File f = new File (Environment.getExternalStorageDirectory () + File.separator + "img.jpg");
                switch (requestCode) {
                    case PICK_FROM_CAMERA_BEFORE_IMAGE:
                        ImageView image = null;
                        Bitmap thePic = null;
                        if (f.exists ()) {
                            thePic = Utils.compressBitmap (BitmapFactory.decodeFile (f.getAbsolutePath ()), DetailActivity.this);
                        }
                        //Bitmap thePic = Utils.compressBitmap ((Bitmap) data.getExtras ().get ("data"));
                        ImageDetail beforeImageDetail = new ImageDetail (0, Utils.bitmapToBase64 (thePic), "before_img_" + date, "", "", "");
                        beforeImageList.add (beforeImageDetail);
                        image = new ImageView (DetailActivity.this);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (300, 225);
                        params.setMargins (10, 10, 10, 10);
                        image.setLayoutParams (params);
                        llBeforeImage.addView (image);
                        image.setImageBitmap (thePic);
                        break;
                    case PICK_FROM_CAMERA_AFTER_IMAGE:
                        ImageView image2 = null;
                        Bitmap thePic2 = null;
                        if (f.exists ()) {
                            thePic2 = Utils.compressBitmap (BitmapFactory.decodeFile (f.getAbsolutePath ()), DetailActivity.this);
                        }

//                        Bitmap thePic2 = Utils.compressBitmap ((Bitmap) data.getExtras ().get ("data"));
                        ImageDetail afterImageDetail = new ImageDetail (0, Utils.bitmapToBase64 (thePic2), "after_img_" + date, "", "", "");
                        afterImageList.add (afterImageDetail);
                        image2 = new ImageView (DetailActivity.this);
                        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams (300, 225);
                        params3.setMargins (10, 10, 10, 10);
                        image2.setLayoutParams (params3);
                        llAfterImage.addView (image2);
                        image2.setImageBitmap (thePic2);
                        break;
                    case PICK_FROM_GALLERY_BEFORE_IMAGE:
                        final Uri uri = data.getData ();
                        Bitmap thePic3 = Utils.compressBitmap (MediaStore.Images.Media.getBitmap (this.getContentResolver (), uri), DetailActivity.this);
                        ImageDetail beforeImageDetail2 = new ImageDetail (0, Utils.bitmapToBase64 (thePic3), "before_img_" + date, "", "", "");
                        beforeImageList.add (beforeImageDetail2);
                        image = new ImageView (DetailActivity.this);
                        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams (300, 225);
                        params2.setMargins (10, 10, 10, 10);
                        image.setLayoutParams (params2);
                        llBeforeImage.addView (image);
                        image.setImageBitmap (thePic3);
                        break;
                    case PICK_FROM_GALLERY_AFTER_IMAGE:
                        final Uri uri2 = data.getData ();
                        Bitmap thePic4 = Utils.compressBitmap (MediaStore.Images.Media.getBitmap (this.getContentResolver (), uri2), DetailActivity.this);
                        ImageDetail afterImageDetail2 = new ImageDetail (0, Utils.bitmapToBase64 (thePic4), "after_img_" + date, "", "", "");
                        afterImageList.add (afterImageDetail2);
                        image2 = new ImageView (DetailActivity.this);
                        LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams (300, 225);
                        params4.setMargins (10, 10, 10, 10);
                        image2.setLayoutParams (params4);
                        llAfterImage.addView (image2);
                        image2.setImageBitmap (thePic4);
                        break;
                    case PICK_FROM_GALLERY_LIST_ITEM:
                        final Uri uri3 = data.getData ();
                        Bitmap thePic5 = Utils.compressBitmap (MediaStore.Images.Media.getBitmap (this.getContentResolver (), uri3), DetailActivity.this);
                        ImageDetail smCheckImageDetail = new ImageDetail (0, Utils.bitmapToBase64 (thePic5), "smcheck_img_" + date, "", "", "");
                        for (int i = 0; i < Constants.serviceCheckList.size (); i++) {
                            ServiceCheck serviceCheck = Constants.serviceCheckList.get (i);
                            if (serviceCheck.getService_check_id () == smCheckIdTemp) {
                                serviceCheck.setSmCheckImageInList (smCheckImageDetail);
                            }
                        }
                        smCheckIdTemp = 0;
                        break;
                    case PICK_FROM_CAMERA_LIST_ITEM:
                        Bitmap thePic6 = null;
                        if (f.exists ()) {
                            thePic6 = Utils.compressBitmap (BitmapFactory.decodeFile (f.getAbsolutePath ()), DetailActivity.this);
                        }
//                        Bitmap thePic6 = Utils.compressBitmap ((Bitmap) data.getExtras ().get ("data"));
                        ImageDetail smCheckImageDetail2 = new ImageDetail (0, Utils.bitmapToBase64 (thePic6), "smcheck_img_" + date, "", "", "");
                        for (int i = 0; i < Constants.serviceCheckList.size (); i++) {
                            ServiceCheck serviceCheck = Constants.serviceCheckList.get (i);
                            if (serviceCheck.getService_check_id () == smCheckIdTemp) {
                                serviceCheck.setSmCheckImageInList (smCheckImageDetail2);
                            }
                        }
                        smCheckIdTemp = 0;
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public boolean validate () {
/*
        if (etTimeIn.getText ().toString ().length () == 0) {
            Utils.showToast (DetailActivity.this, "Please set the time in");
            return false;
        } else if (etOnSiteContact.getText ().toString ().length () == 0) {
            Utils.showToast (DetailActivity.this, "Please enter onsite contact");
            return false;
        } else if (etEmail.getText ().toString ().length () == 0) {
            Utils.showToast (DetailActivity.this, "Please enter the email");
            return false;
        } else if (etKwRating.getText ().toString ().length () == 0) {
            Utils.showToast (DetailActivity.this, "Please enter the Kw Rating");
            return false;
//        } else if (etGeneratorConditionComment.getText().toString().length() == 0) {
//            Utils.showToast(DetailActivity.this, "Please enter the generator rating");
//            return false;
//        } else if (etComment.getText().toString().length() == 0) {
//            Utils.showToast(DetailActivity.this, "Please enter a comment");
//            return false;
        } else if (etTimeOut.getText ().toString ().length () == 0) {
            Utils.showToast (DetailActivity.this, "Please set time out");
            return false;
        } else {
            return true;
        }
*/
        return true;
    }

    private void showSaveSerialDialog (final int serial_id, final int contract_num, final String serial_type) {
        TextView tvSerialHeading;
        final EditText etSerial;
        final EditText etModel;
        final Spinner spManufacturer;
        TextView tvAddSerial;
        TextView tvCancelSerial;

        final Serial generatorSerial = new Serial (false, serial_id, 0, 0, 0, 0, "", "", "", "");

        dialogAddNewSerial = new Dialog (DetailActivity.this);
        dialogAddNewSerial.setContentView (R.layout.dialog_add_generator_serial);
        dialogAddNewSerial.setCancelable (false);

        tvSerialHeading = (TextView) dialogAddNewSerial.findViewById (R.id.tvSerialHeading);
        etSerial = (EditText) dialogAddNewSerial.findViewById (R.id.etSerial);
        etModel = (EditText) dialogAddNewSerial.findViewById (R.id.etModel);
        spManufacturer = (Spinner) dialogAddNewSerial.findViewById (R.id.spManufacturer);
        tvAddSerial = (TextView) dialogAddNewSerial.findViewById (R.id.tvAddSerial);
        tvCancelSerial = (TextView) dialogAddNewSerial.findViewById (R.id.tvCancelSerial);

        dialogAddNewSerial.getWindow ().setBackgroundDrawable (new ColorDrawable (android.graphics.Color.TRANSPARENT));
        dialogAddNewSerial.show ();

        manufacturerAdapter = new ManufacturerAdapter (DetailActivity.this, R.layout.spinner_item, Constants.manufacturerList);
        spManufacturer.setAdapter (manufacturerAdapter);

        tvCancelSerial.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {
                dialogAddNewSerial.dismiss ();
            }
        });
        tvAddSerial.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {

                if (etSerial.getText ().toString ().length () == 0) {
                    etSerial.setError ("Please enter Serial number");
                    Utils.shakeView (DetailActivity.this, etSerial);
                } else if (etModel.getText ().toString ().length () == 0) {
                    etModel.setError ("Please enter Model number");
                    Utils.shakeView (DetailActivity.this, etModel);
                } else if (generatorSerial.getManufacturer_id () == 0) {
                    Utils.showToast (DetailActivity.this, "Please select Manufacturer");
                } else {
                    generatorSerial.setModel_number (etModel.getText ().toString ());
                    generatorSerial.setSerial_number (etSerial.getText ().toString ());
                    generatorSerial.setSerial_id (serial_id);
                    generatorSerial.setSerial_type (serial_type);

                    Utils.showLog (Log.INFO, "MANUFACTURER ID", "" + generatorSerial.getManufacturer_id (), true);
                    Utils.showLog (Log.INFO, "MANUFACTURER NAME", generatorSerial.getManufacturer_name (), true);
                    Utils.showLog (Log.INFO, "MODEL NUMBER", generatorSerial.getModel_number (), true);
                    Utils.showLog (Log.INFO, "SERIAL NUMBER", generatorSerial.getSerial_number (), true);
                    Utils.showLog (Log.INFO, "SERIAL TYPE", generatorSerial.getSerial_type (), true);
                    Utils.showLog (Log.INFO, "SERIAL ID", "" + generatorSerial.getSerial_id (), true);
                    Utils.showLog (Log.INFO, "CONTRACT NUMBER", "" + contract_num, true);

                    dialogAddNewSerial.dismiss ();
                    pDialog = new ProgressDialog (DetailActivity.this);
                    Utils.showProgressDialog (pDialog, null);
                    saveSerial (generatorSerial.getSerial_id (), contract_num, generatorSerial.getSerial_type (),
                            generatorSerial.getSerial_number (), generatorSerial.getModel_number (), generatorSerial.getManufacturer_id ());
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

    public void saveSerial (final int ser_id, final int contract_num, final String serial_type, final String serial_number, final String model_number, final int manufacturer_id) {
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
                                        Utils.showToast (DetailActivity.this, message);
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
                            "\"API_function\":\"saveSerialData\",\n" +
                            "\"API_parameters\":{\"serid\" : \"" + ser_id + "\", \"contractNum\": \"" + contract_num + "\", \"serialType\": \"" + serial_type + "\", \"serialNumber\": \"" + serial_number + "\", \"modelNumber\": \"" + model_number + "\", \"manufacturer\": \"" + manufacturer_id + "\"}}";
                    Utils.showLog (Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, str, true);
                    return str.getBytes ();
                }

                public String getBodyContentType () {
                    return "application/json; charset=utf-8";
                }
            };
            Utils.sendRequest (strRequest, 30);
        } else {
            Utils.showOkDialog (DetailActivity.this, "Sorry you cannot add a new serial in Offline mode", false);
        }
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
                                                        c.getInt ("manufacturer_id"), wo_id, 0, Constants.workOrderDetail.getContract_number (),
                                                        c.getString ("serial"), c.getString ("model"), c.getString ("Type"), c.getString ("manufacturer_name"));
                                                atsSerialList.add (ATSSerial);
                                                break;
                                            case "Engine":
                                                Serial engineSerial = new
                                                        Serial (false, c.getInt ("serviceSerials_id"),
                                                        c.getInt ("manufacturer_id"), wo_id, 0, Constants.workOrderDetail.getContract_number (),
                                                        c.getString ("serial"), c.getString ("model"), c.getString ("Type"), c.getString ("manufacturer_name"));
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

                            LoadSerialsInLayout (true);
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
            Utils.sendRequest (strRequest, 30);
        } else {
            getSerialsFromLocalDatabase (wo_id);
            //          Utils.showOkDialog (DetailActivity.this, "Seems like there is no internet connection, the app will continue in Offline mode", false);
        }
    }

    private void LoadSerialsInLayout (boolean new_serial) {
        int i;
        llEngineSerial.removeAllViews ();
        llATSSerial.removeAllViews ();

//        if (new_serial){
//        } else {
//        }

        for (i = 0; i < engineSerialList.size (); i++) {
            final Serial engineSerial = engineSerialList.get (i);
            View child = getLayoutInflater ().inflate (R.layout.listview_item_generator_serial, null);

            TextView tvEngineSerial = (TextView) child.findViewById (R.id.tvGeneratorSerial);
            TextView tvEngineModel = (TextView) child.findViewById (R.id.tvGeneratorModel);
            TextView tvEngineManufacturer = (TextView) child.findViewById (R.id.tvGeneratorManufacturer);
            final CheckBox cbSelected = (CheckBox) child.findViewById (R.id.cbSelected);

            cbSelected.setVisibility (View.VISIBLE);
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
                    } else {
                        engineSerial.setChecked (false);
                    }
                }
            });
        }


        for (i = 0; i < atsSerialList.size (); i++) {
            final Serial atsSerial = atsSerialList.get (i);
            View child = getLayoutInflater ().inflate (R.layout.listview_item_generator_serial, null);

            TextView tvATSSerial = (TextView) child.findViewById (R.id.tvGeneratorSerial);
            TextView tvATSModel = (TextView) child.findViewById (R.id.tvGeneratorModel);
            TextView tvATSManufacturer = (TextView) child.findViewById (R.id.tvGeneratorManufacturer);
            final CheckBox cbSelected = (CheckBox) child.findViewById (R.id.cbSelected);

            cbSelected.setVisibility (View.VISIBLE);

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

    private String createResponse () {
        JSONObject responseJSON = new JSONObject ();
        try {
            JSONObject jsonObjectSmChecks = new JSONObject (Constants.workOrderDetail.getService_check_json ());
            JSONArray jsonArraySmChecks = jsonObjectSmChecks.getJSONArray ("smchecks");

            JSONObject jsonObjectEngineSerial = new JSONObject (Constants.workOrderDetail.getEngine_serial_json ());
            JSONArray jsonArrayEngineSerial = jsonObjectEngineSerial.getJSONArray ("engSerialCheck");

            JSONObject jsonObjectATSSerial = new JSONObject (Constants.workOrderDetail.getAts_serial_json ());
            JSONArray jsonArrayATSSerial = jsonObjectATSSerial.getJSONArray ("atsSerialCheck");

            JSONObject jsonObjectBeforeImages = new JSONObject (Constants.workOrderDetail.getBefore_image_list_json ());
            JSONArray jsonArrayBeforeImages = jsonObjectBeforeImages.getJSONArray ("beforeImages");

            JSONObject jsonObjectAfterImages = new JSONObject (Constants.workOrderDetail.getAfter_image_list_json ());
            JSONArray jsonArrayAfterImages = jsonObjectAfterImages.getJSONArray ("afterImages");

            JSONObject jsonObjectSignatureImages = new JSONObject (Constants.workOrderDetail.getSignature_image_list_json ());
            JSONArray jsonArraySignatureImages = jsonObjectSignatureImages.getJSONArray ("signatures");


            responseJSON.put ("formId", Constants.workOrderDetail.getForm_id ());
            responseJSON.put ("wo", Constants.workOrderDetail.getWork_order_id ());
            responseJSON.put ("genSerialID", Constants.workOrderDetail.getGenerator_serial_id ());
            responseJSON.put ("empid", Constants.workOrderDetail.getEmp_id ());
            responseJSON.put ("custName", Constants.workOrderDetail.getCustomer_name ());
            responseJSON.put ("onsiteContact", Constants.workOrderDetail.getOnsite_contact ());
            responseJSON.put ("email", Constants.workOrderDetail.getEmail ());
            responseJSON.put ("kwRating", Constants.workOrderDetail.getKw_rating ());
            responseJSON.put ("timeIn", Constants.workOrderDetail.getTime_in ());
            responseJSON.put ("timeOut", Constants.workOrderDetail.getTime_out ());
            responseJSON.put ("genCondition", Constants.workOrderDetail.getGenerator_condition_comment ());
            responseJSON.put ("genStatus", Constants.workOrderDetail.getGenerator_condition_text ());
            responseJSON.put ("GenSerial", Constants.workOrderDetail.getGenerator_serial ());
            responseJSON.put ("GenModel", Constants.workOrderDetail.getGenerator_model ());
            responseJSON.put ("GenMake", Constants.workOrderDetail.getGenerator_make_id ());
            responseJSON.put ("comments", Constants.workOrderDetail.getComments ());
            responseJSON.put ("beforeImages", jsonArrayBeforeImages);
            responseJSON.put ("afterImages", jsonArrayAfterImages);
            responseJSON.put ("smchecks", jsonArraySmChecks);
            responseJSON.put ("engSerialCheck", jsonArrayEngineSerial);
            responseJSON.put ("atsSerialCheck", jsonArrayATSSerial);
            responseJSON.put ("signatures", jsonArraySignatureImages);


        } catch (JSONException e) {
            Utils.showLog (Log.ERROR, "JSON EXCEPTION", e.getMessage (), true);
        }

        Utils.showLog (Log.ERROR, "RESPONSE", String.valueOf (responseJSON), true);
        return String.valueOf (responseJSON);
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
                                    switch (jsonObj.getInt ("error_code")) {
                                        case 0:
                                            setFormDetails (jsonObj);
//                                            intent = new Intent (activity, ViewFormActivity.class);
                                            break;
                                        default:
                                            LoadSerialsInLayout (false);
                                            new LoadServiceChecksInLinearLayout (DetailActivity.this).execute ();
//                                            intent = new Intent (activity, DetailActivity.class);
                                            break;
                                    }

//
//                                    Utils.showOkDialog (DetailActivity.this, "Form is already saved with this serial id " +
//                                            "it will load the saved form data", false);

/*
                                    JSONArray jsonArray = jsonObj.getJSONArray ("service_forms");
                                    if (jsonArray.length () == 0) {

                                    } else {
                                        for (int i=0;i<jsonArray.length ();i++){
                                            JSONObject jsonObject = jsonArray.getJSONObject (i);
                                            if (jsonObject.getInt ("GEN_SERIALID") == generator_serial_id){
                                                Utils.showOkDialog (DetailActivity.this, "Form is already saved with this serial id " +
                                                        "it will load the saved form data", false);
                                                setFormDetails (jsonObject);
                                            }
                                            else{
                                                new LoadServiceChecksInLinearLayout (DetailActivity.this).execute ();
                                                LoadSerialsInLayout ();
                                            }
                                        }
                                    }
*/

                                } catch (JSONException e) {
                                    e.printStackTrace ();
                                    LoadSerialsInLayout (false);
                                    new LoadServiceChecksInLinearLayout (DetailActivity.this).execute ();
                                }
                            } else {
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
//                                LoadSerialsInLayout ();
//                                new LoadServiceChecksInLinearLayout (DetailActivity.this).execute ();
                            }
                        }
                    },
                    new Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                            LoadSerialsInLayout (false);
                            new LoadServiceChecksInLinearLayout (DetailActivity.this).execute ();
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
            Utils.sendRequest (strRequest, 30);
        } else {
            if (db.isOfflineServiceFormPresent (wo_id, generator_serial_id)) {
                offlineServiceForm = db.getOfflineServiceForm (wo_id, generator_serial_id);
                setOfflineFormDetails (offlineServiceForm);
                Utils.showOkDialog (DetailActivity.this, "Seems like there is an offline form is already present in local database, the app will load the details", false);
            } else {
                Utils.showOkDialog (DetailActivity.this, "Seems like there is no internet connection, the app will continue in Offline mode", false);
                LoadSerialsInLayout (false);
                new LoadServiceChecksInLinearLayout (DetailActivity.this).execute ();
            }
        }
    }

    private void setFormDetails (JSONObject jsonObject) {
        try {
            Constants.workOrderDetail.setForm_id (jsonObject.getInt ("ES_ID"));
            etTimeIn.setText (jsonObject.getString ("TIME_IN"));
            etOnSiteContact.setText (jsonObject.getString ("ONSITE_CONTACT"));
            etEmail.setText (jsonObject.getString ("EMAIL"));
            etKwRating.setText (jsonObject.getString ("KW_RATING"));
            switch (jsonObject.getString ("GEN_STATUS").toUpperCase ()) {
                case "GOOD":
                    rbGood.setChecked (true);
                    break;
                case "FAIR":
                    rbFair.setChecked (true);
                    break;
                case "POOR":
                    rbPoor.setChecked (true);
                    break;
            }
            etGeneratorConditionComment.setText (jsonObject.getString ("GEN_CONDITION"));
            etComment.setText (jsonObject.getString ("COMMENTS"));
            etTimeOut.setText (jsonObject.getString ("TIME_OUT"));

            DateFormat df = new SimpleDateFormat ("yyyyMMdd_HHmmss");
            final String date = df.format (Calendar.getInstance ().getTime ());


            JSONArray jsonArrayBeforeImages = jsonObject.getJSONArray ("beforeImages");
            for (int i = 0; i < jsonArrayBeforeImages.length (); i++) {
                try {
                    JSONObject jsonObject1 = jsonArrayBeforeImages.getJSONObject (i);
                    ImageView image = new ImageView (DetailActivity.this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (300, 225);
                    params.setMargins (10, 10, 10, 10);
                    image.setLayoutParams (params);
                    llBeforeImage.addView (image);
                    Picasso.with (DetailActivity.this).load (jsonObject1.getString ("img_url")).into (image);
                } catch (JSONException e1) {
                    e1.printStackTrace ();
                }
            }


            JSONArray jsonArrayAfterImages = jsonObject.getJSONArray ("afterImages");
            for (int i = 0; i < jsonArrayAfterImages.length (); i++) {
                try {
                    JSONObject jsonObject1 = jsonArrayAfterImages.getJSONObject (i);
                    ImageView image = new ImageView (DetailActivity.this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (300, 225);
                    params.setMargins (10, 10, 10, 10);
                    image.setLayoutParams (params);
                    llAfterImage.addView (image);
                    Picasso.with (DetailActivity.this).load (jsonObject1.getString ("img_url")).into (image);
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
                            Picasso.with (DetailActivity.this).load (jsonObject1.getString ("signature_url")).into (ivTechSignature);
                            break;
                        case "Customer":
                            etCustomerName.setText (jsonObject1.getString ("signator"));
                            Picasso.with (DetailActivity.this).load (jsonObject1.getString ("signature_url")).into (ivCustomerSignature);
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
                    for (int j = 0; j < engineSerialList.size (); j++) {
                        Serial engineSerial = engineSerialList.get (j);
                        if (jsonObject1.getInt ("serial_id") == engineSerial.getSerial_id ()) {
                            engineSerial.setChecked (false);
                        } else {
                            engineSerial.setChecked (true);
                        }
                    }
                    for (int k = 0; k < atsSerialList.size (); k++) {
                        Serial atsSerial = atsSerialList.get (k);
                        if (jsonObject1.getInt ("serial_id") == atsSerial.getSerial_id ()) {
                            atsSerial.setChecked (false);
                        } else {
                            atsSerial.setChecked (true);
                        }
                    }

                } catch (JSONException e1) {
                    e1.printStackTrace ();
                }
            }


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
                            imageDetail = new ImageDetail (0, "", "", "", "", "");
                        }

                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace ();
        }
        LoadSerialsInLayout (false);
        new LoadServiceChecksInLinearLayout (this).execute ();
    }

    private void setOfflineFormDetails (WorkOrderDetail offlineServiceForm) {
        try {
            Constants.workOrderDetail.setForm_id (offlineServiceForm.getForm_id ());
            etTimeIn.setText (offlineServiceForm.getTime_in ());
            etOnSiteContact.setText (offlineServiceForm.getOnsite_contact ());
            etEmail.setText (offlineServiceForm.getEmail ());
            etKwRating.setText (offlineServiceForm.getKw_rating ());
            switch (offlineServiceForm.getGenerator_condition_text ().toUpperCase ()) {
                case "GOOD":
                    rbGood.setChecked (true);
                    break;
                case "FAIR":
                    rbFair.setChecked (true);
                    break;
                case "POOR":
                    rbPoor.setChecked (true);
                    break;
            }
            etGeneratorConditionComment.setText (offlineServiceForm.getGenerator_condition_comment ());
            etComment.setText (offlineServiceForm.getComments ());
            etTimeOut.setText (offlineServiceForm.getTime_out ());

            DateFormat df = new SimpleDateFormat ("yyyyMMdd_HHmmss");
            final String date = df.format (Calendar.getInstance ().getTime ());

            JSONObject jsonObjectBeforeImages = new JSONObject (offlineServiceForm.getBefore_image_list_json ());
            JSONArray jsonArrayBeforeImages = jsonObjectBeforeImages.getJSONArray ("beforeImages");
            for (int i = 0; i < jsonArrayBeforeImages.length (); i++) {
                try {
                    JSONObject jsonObject1 = jsonArrayBeforeImages.getJSONObject (i);
                    ImageView image = new ImageView (DetailActivity.this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (300, 225);
                    params.setMargins (10, 10, 10, 10);
                    image.setLayoutParams (params);
                    image.setImageBitmap (Utils.base64ToBitmap (jsonObject1.getString ("64_image")));
                    llBeforeImage.addView (image);

                    ImageDetail beforeImageDetail = new ImageDetail (0, jsonObject1.getString ("64_image"), "before_img_" + date, "", "", "");
                    beforeImageList.add (beforeImageDetail);

                } catch (JSONException e1) {
                    e1.printStackTrace ();
                }
            }


            JSONObject jsonObjectAfterImages = new JSONObject (offlineServiceForm.getAfter_image_list_json ());
            JSONArray jsonArrayAfterImages = jsonObjectAfterImages.getJSONArray ("afterImages");
            for (int i = 0; i < jsonArrayAfterImages.length (); i++) {
                try {
                    JSONObject jsonObject1 = jsonArrayAfterImages.getJSONObject (i);
                    ImageView image = new ImageView (DetailActivity.this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (300, 225);
                    params.setMargins (10, 10, 10, 10);
                    image.setLayoutParams (params);
                    image.setImageBitmap (Utils.base64ToBitmap (jsonObject1.getString ("64_image")));
                    llAfterImage.addView (image);

                    ImageDetail afterImageDetail = new ImageDetail (0, jsonObject1.getString ("64_image"), "after_img_" + date, "", "", "");
                    afterImageList.add (afterImageDetail);

                } catch (JSONException e1) {
                    e1.printStackTrace ();
                }
            }

            JSONObject jsonObjectSignatures = new JSONObject (offlineServiceForm.getSignature_image_list_json ());
            JSONArray jsonArraySignatures = jsonObjectSignatures.getJSONArray ("signatures");
            for (int i = 0; i < jsonArraySignatures.length (); i++) {
                try {
                    JSONObject jsonObject1 = jsonArraySignatures.getJSONObject (i);
                    ImageDetail techSignatureDetail = new ImageDetail (0, jsonObject1.getString ("64_image"),
                            "", jsonObject1.getString ("description"), jsonObject1.getString ("signator"), "");

                    switch (jsonObject1.getString ("description")) {
                        case "Tech":
                            ivTechSignature.setImageBitmap (Utils.base64ToBitmap (jsonObject1.getString ("64_image")));
                            break;
                        case "Customer":
                            etCustomerName.setText (jsonObject1.getString ("signator"));
                            ivCustomerSignature.setImageBitmap (Utils.base64ToBitmap (jsonObject1.getString ("64_image")));
                            break;
                    }
                    signatureImageList.add (techSignatureDetail);
                } catch (JSONException e1) {
                    e1.printStackTrace ();
                }
            }


            JSONObject jsonObjectEngSerials = new JSONObject (offlineServiceForm.getEngine_serial_json ());
            JSONArray jsonArrayEngSerials = jsonObjectEngSerials.getJSONArray ("engSerialCheck");
            for (int i = 0; i < jsonArrayEngSerials.length (); i++) {
                try {
                    JSONObject jsonObject1 = jsonArrayEngSerials.getJSONObject (i);
                    for (int j = 0; j < engineSerialList.size (); j++) {
                        Serial engineSerial = engineSerialList.get (j);
                        if (jsonObject1.getInt ("serid") == engineSerial.getSerial_id () && jsonObject1.getInt ("checkval") == 0) {
                            engineSerial.setChecked (false);
                        } else if (jsonObject1.getInt ("serid") == engineSerial.getSerial_id () && jsonObject1.getInt ("checkval") == 1) {
                            engineSerial.setChecked (true);
                        }
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace ();
                }
            }

            JSONObject jsonObjectAtsSerials = new JSONObject (offlineServiceForm.getAts_serial_json ());
            JSONArray jsonArrayAtsSerials = jsonObjectAtsSerials.getJSONArray ("atsSerialCheck");
            for (int i = 0; i < jsonArrayAtsSerials.length (); i++) {
                try {
                    JSONObject jsonObject1 = jsonArrayAtsSerials.getJSONObject (i);
                    for (int j = 0; j < atsSerialList.size (); j++) {
                        Serial atsSerial = atsSerialList.get (j);
                        if (jsonObject1.getInt ("serid") == atsSerial.getSerial_id () && jsonObject1.getInt ("checkval") == 0) {
                            atsSerial.setChecked (false);
                        } else if (jsonObject1.getInt ("serid") == atsSerial.getSerial_id () && jsonObject1.getInt ("checkval") == 1) {
                            atsSerial.setChecked (true);
                        }
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace ();
                }
            }

            JSONObject jsonObjectFormChecks = new JSONObject (offlineServiceForm.getService_check_json ());
            JSONArray jsonArrayFormChecks = jsonObjectFormChecks.getJSONArray ("smchecks");
            for (int i = 0; i < jsonArrayFormChecks.length (); i++) {
                JSONObject jsonObject1 = jsonArrayFormChecks.getJSONObject (i);
                for (int j = 0; j < Constants.serviceCheckList.size (); j++) {
                    ServiceCheck serviceCheck = Constants.serviceCheckList.get (j);
                    if (jsonObject1.getInt ("name_id") == serviceCheck.getService_check_id ()) {
                        serviceCheck.setComment (jsonObject1.getString ("text"));
                        serviceCheck.setSelection_text (jsonObject1.getString ("status"));
                        switch (jsonObject1.getString ("status")) {
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
                        if (! jsonObject1.getString ("imageId").equalsIgnoreCase ("0")) {
                            JSONArray jsonArrayFormCheckImage = jsonObject1.getJSONArray ("imageId");
                            JSONObject jsonObject2 = jsonArrayFormCheckImage.getJSONObject (0);
                            imageDetail = new ImageDetail (0, jsonObject2.getString ("64_image"), "smcheck_img_" + date, "", "", "");
                            serviceCheck.setSmCheckImageInList (imageDetail);
                        } else {
                            imageDetail = new ImageDetail (0, "", "", "", "", "");
                        }

                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace ();
        }
        LoadSerialsInLayout (false);
        new LoadServiceChecksInLinearLayout (this).execute ();
    }

    private void getSerialsFromLocalDatabase (int wo_id) {
        Utils.showLog (Log.DEBUG, AppConfigTags.TAG, "Getting all the engine serials from local database for wo id = " + wo_id, true);
        engineSerialList.clear ();
        atsSerialList.clear ();

        List<Serial> allSerials = db.getAllContractSerials (wo_id);
        for (Serial serial : allSerials) {
            switch (serial.getSerial_type ()) {
                case "Engine":
                    engineSerialList.add (serial);
                    break;
                case "ATS":
                    atsSerialList.add (serial);
                    break;

            }
        }
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


            for (int i = 0; i < Constants.serviceCheckList.size (); i++) {
                final ServiceCheck serviceCheck = Constants.serviceCheckList.get (i);

                View child = getLayoutInflater ().inflate (R.layout.listview_item_service_check, null);

                TextView tvHeading = (TextView) child.findViewById (R.id.tvHeading);
                final ImageView ivImage = (ImageView) child.findViewById (R.id.ivImage);
                final ImageView ivImageSelected = (ImageView) child.findViewById (R.id.ivImageSelected);
                LinearLayout llPassAdviceFail = (LinearLayout) child.findViewById (R.id.llPassAdviceFail);
                LinearLayout llYesNo = (LinearLayout) child.findViewById (R.id.llYesNo);
                final EditText etComment = (EditText) child.findViewById (R.id.etComments);

                final RadioButton rbPass = (RadioButton) child.findViewById (R.id.rbPass);
                final RadioButton rbAdvise = (RadioButton) child.findViewById (R.id.rbAdvise);
                final RadioButton rbFail = (RadioButton) child.findViewById (R.id.rbFail);
                final RadioButton rbNA = (RadioButton) child.findViewById (R.id.rbNA);
                final RadioButton rbYes = (RadioButton) child.findViewById (R.id.rbYes);
                final RadioButton rbNo = (RadioButton) child.findViewById (R.id.rbNo);
                final RadioButton rbNA2 = (RadioButton) child.findViewById (R.id.rbNA2);

                Spanned text;
                if (serviceCheck.getSub_heading ().length () > 0)
                    text = Html.fromHtml ("<b>" + serviceCheck.getHeading () + "</b> - " + serviceCheck.getSub_heading ());
                else
                    text = Html.fromHtml ("<b>" + serviceCheck.getHeading () + "</b>");

                ivImage.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick (View view) {
                        Utils.showLog (Log.DEBUG, "servicecheck_id", "" + serviceCheck.getService_check_id (), true);
                        smCheckIdTemp = serviceCheck.getService_check_id ();
                        if (selectImage (LIST_ITEM_PICKER)) {
                            Utils.showLog (Log.DEBUG, "CHECK IMAGE :", "TRUE", true);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ivImageSelected.setImageDrawable (getResources ().getDrawable (R.drawable.ic_check_blue, getApplicationContext ().getTheme ()));
                            } else {
                                ivImageSelected.setImageDrawable (getResources ().getDrawable (R.drawable.ic_check_blue));
                            }
                        } else {
                            Utils.showLog (Log.DEBUG, "CHECK IMAGE :", "FALSE", true);
                        }
                    }
                });

                tvHeading.setText (text);
                etComment.setText (serviceCheck.getComment ());

                rbPass.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
                    @Override
                    public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                        if (b) {
                            rbNA.setChecked (false);
                            rbFail.setChecked (false);
                            rbAdvise.setChecked (false);
                            rbPass.setChecked (true);
                            serviceCheck.setSelection_text ("Pass");
                            serviceCheck.setSelection_flag (3);
                            serviceCheck.setComment_required (false);
                            etComment.setError (null);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ivImageSelected.setImageDrawable (getResources ().getDrawable (R.drawable.ic_check_white, getApplicationContext ().getTheme ()));
                            } else {
                                ivImageSelected.setImageDrawable (getResources ().getDrawable (R.drawable.ic_check_white));
                            }

                        }
                    }
                });
                rbAdvise.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
                    @Override
                    public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                        if (b) {
                            rbNA.setChecked (false);
                            rbFail.setChecked (false);
                            rbAdvise.setChecked (true);
                            rbPass.setChecked (false);
                            serviceCheck.setSelection_text ("Advise");
                            serviceCheck.setSelection_flag (2);
                            serviceCheck.setComment_required (true);
                            if (etComment.getText ().toString ().length () > 0) {
                                etComment.setError (null);
                            } else {
                                etComment.setError ("Comment is required");
                            }

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ivImageSelected.setImageDrawable (getResources ().getDrawable (R.drawable.ic_check_white, getApplicationContext ().getTheme ()));
                            } else {
                                ivImageSelected.setImageDrawable (getResources ().getDrawable (R.drawable.ic_check_white));
                            }

                        }
                    }
                });
                rbFail.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
                    @Override
                    public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                        if (b) {
                            rbNA.setChecked (false);
                            rbFail.setChecked (true);
                            rbAdvise.setChecked (false);
                            rbPass.setChecked (false);
                            serviceCheck.setSelection_text ("Fail");
                            serviceCheck.setSelection_flag (1);
                            serviceCheck.setComment_required (true);
                            if (etComment.getText ().toString ().length () > 0) {
                                etComment.setError (null);
                            } else {
                                etComment.setError ("Comment is required");
                            }

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ivImageSelected.setImageDrawable (getResources ().getDrawable (R.drawable.ic_clear, getApplicationContext ().getTheme ()));
                            } else {
                                ivImageSelected.setImageDrawable (getResources ().getDrawable (R.drawable.ic_clear));
                            }
                        }

                    }
                });
                rbNA.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
                    @Override
                    public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                        if (b) {
                            rbNA.setChecked (true);
                            rbFail.setChecked (false);
                            rbAdvise.setChecked (false);
                            rbPass.setChecked (false);
                            serviceCheck.setSelection_text ("N/A");
                            serviceCheck.setSelection_flag (0);
                            serviceCheck.setComment_required (false);
                            etComment.setError (null);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ivImageSelected.setImageDrawable (getResources ().getDrawable (R.drawable.ic_check_white, getApplicationContext ().getTheme ()));
                            } else {
                                ivImageSelected.setImageDrawable (getResources ().getDrawable (R.drawable.ic_check_white));
                            }
                        }
                    }
                });

                rbYes.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
                    @Override
                    public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                        if (b) {
                            rbYes.setChecked (true);
                            rbNo.setChecked (false);
                            rbNA2.setChecked (false);
                            serviceCheck.setSelection_text ("Yes");
                            serviceCheck.setSelection_flag (2);
                        }
                    }
                });
                rbNo.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
                    @Override
                    public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                        if (b) {
                            rbYes.setChecked (false);
                            rbNo.setChecked (true);
                            rbNA2.setChecked (false);
                            serviceCheck.setSelection_text ("No");
                            serviceCheck.setSelection_flag (1);
                        }
                    }
                });
                rbNA2.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
                    @Override
                    public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                        if (b) {
                            rbYes.setChecked (false);
                            rbNo.setChecked (false);
                            rbNA2.setChecked (true);
                            serviceCheck.setSelection_text ("N/A");
                            serviceCheck.setSelection_flag (0);
                        }
                    }
                });


                switch (serviceCheck.getGroup_type ()) {
                    case 0:
                        switch (serviceCheck.getSelection_flag ()) {
                            case 0:
                                rbNA.setChecked (true);
                                rbFail.setChecked (false);
                                rbAdvise.setChecked (false);
                                rbPass.setChecked (false);
                                break;
                            case 1:
                                rbNA.setChecked (false);
                                rbFail.setChecked (true);
                                rbAdvise.setChecked (false);
                                rbPass.setChecked (false);
                                break;
                            case 2:
                                rbNA.setChecked (false);
                                rbFail.setChecked (false);
                                rbAdvise.setChecked (true);
                                rbPass.setChecked (false);
                                break;
                            case 3:
                                rbNA.setChecked (false);
                                rbFail.setChecked (false);
                                rbAdvise.setChecked (false);
                                rbPass.setChecked (true);
                                break;
                        }
                        llYesNo.setVisibility (View.GONE);
                        llPassAdviceFail.setVisibility (View.VISIBLE);
                        break;
                    case 1:
                        switch (serviceCheck.getSelection_flag ()) {
                            case 0:
                                rbNA2.setChecked (true);
                                rbNo.setChecked (false);
                                rbYes.setChecked (false);
                                break;
                            case 1:
                                rbNA2.setChecked (false);
                                rbNo.setChecked (true);
                                rbYes.setChecked (false);
                                break;
                            case 2:
                                rbNA2.setChecked (false);
                                rbNo.setChecked (false);
                                rbYes.setChecked (true);
                                break;
                        }
                        llYesNo.setVisibility (View.VISIBLE);
                        llPassAdviceFail.setVisibility (View.GONE);
                        break;
                }


                switch (serviceCheck.getGroup_id ()) {
                    case 1:
                        llFuelSystem.addView (child);
//                        Utils.showLog (Log.DEBUG, "VIEW", "view1", true);
                        break;
                    case 2:
                        llPreStartChecksCoolingSystem.addView (child);
//                        Utils.showLog (Log.DEBUG, "VIEW", "view2", true);
                        break;
                    case 3:
                        llLubricationSystem.addView (child);
//                        Utils.showLog (Log.DEBUG, "VIEW", "view3", true);
                        break;
                    case 4:
                        llAirSystem.addView (child);
//                        Utils.showLog (Log.DEBUG, "VIEW", "view4", true);
                        break;
                    case 5:
                        llExhaustSystem.addView (child);
//                        Utils.showLog (Log.DEBUG, "VIEW", "view5", true);
                        break;
                    case 6:
                        llGenerator.addView (child);
//                        Utils.showLog (Log.DEBUG, "VIEW", "view6", true);
                        break;
                    case 7:
                        llControlPanelCabinets.addView (child);
//                        Utils.showLog (Log.DEBUG, "VIEW", "view7", true);
                        break;
                    case 8:
                        llATSMain.addView (child);
//                        Utils.showLog (Log.DEBUG, "VIEW", "view8", true);
                        break;
                    case 9:
                        llStartingSystem.addView (child);
//                        Utils.showLog (Log.DEBUG, "VIEW", "view9", true);
                        break;
                    case 10:
                        llGeneratorEnclosure.addView (child);
//                        Utils.showLog (Log.DEBUG, "VIEW", "view10", true);
                        break;
                    case 11:
                        llStartupAndRunningCheck.addView (child);
//                        Utils.showLog (Log.DEBUG, "VIEW", "view11", true);
                        break;
                    case 12:
                        llScheduledMaintenance.addView (child);
//                        Utils.showLog (Log.DEBUG, "VIEW", "view12", true);
                        break;
                }


                etComment.addTextChangedListener (new TextWatcher () {
                    @Override
                    public void onTextChanged (CharSequence s, int start, int before, int count) {
                        if (s.length () != 0) {
                            serviceCheck.setComment (String.valueOf (s));
                        } else if (serviceCheck.isComment_required ()) {
                            etComment.setError ("Comment is required");
                        } else {
                            serviceCheck.setComment ("");
                        }
//                    serviceCheckTemp.setComment ("");
                    }

                    @Override
                    public void beforeTextChanged (CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged (Editable s) {
                    }
                });

                if (serviceCheck.isComment_required () && etComment.getText ().toString ().length () == 0)
                    etComment.setError ("Comment is required");
            }
        }

        @Override
        protected String doInBackground (String... params) {
            return "Executed";
        }

        @Override
        protected void onPostExecute (String result) {
            Utils.hideSoftKeyboard (activity);
            Utils.showLog (Log.DEBUG, "LOAD SERIALS", "in post execute", true);
            pDialog.dismiss ();
        }
    }
}
