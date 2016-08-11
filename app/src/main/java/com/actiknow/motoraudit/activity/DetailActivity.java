package com.actiknow.motoraudit.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import com.actiknow.motoraudit.R;
import com.actiknow.motoraudit.adapter.ManufacturerAdapter;
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

public class DetailActivity extends AppCompatActivity {

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


    ArrayList<String> listItem = new ArrayList<String> ();
    ArrayList<String> listGoodCondition = new ArrayList<String> ();
    ArrayList<String> listFairCondition = new ArrayList<String> ();
    ArrayList<String> listPoorCondition = new ArrayList<String> ();

    Dialog dialogSign;
    Dialog dialogAddNewSerial;
    ProgressDialog pDialog;

    ManufacturerAdapter manufacturerAdapter;

    private List<Serial> engineSerialList = new ArrayList<> ();
    private List<Serial> atsSerialList = new ArrayList<> ();
    private List<ImageDetail> beforeImageList = new ArrayList<> ();
    private List<ImageDetail> afterImageList = new ArrayList<> ();
    private List<ImageDetail> smCheckImageList = new ArrayList<> ();
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
//        getWorkOrderDetailFromServer(78759, 97);
//        getWorkOrderDetailFromServer (Constants.workOrderDetail.getWork_order_id (), Constants.workOrderDetail.getContract_number ());
        Utils.hideSoftKeyboard (this);


        new LoadServiceChecksInLinearLayout (this).execute ();


//        new LoadServiceChecks (this).execute ();
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
                }
                return true;
            }
        });


        tvUpdate.setOnTouchListener (new View.OnTouchListener () {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction () == MotionEvent.ACTION_DOWN) {
                    if (validate ()) {
                        JSONObject smChecksJSON = new JSONObject ();
                        JSONArray jsonArraySmChecks = new JSONArray ();
                        try {
                            for (int i = 0; i < Constants.serviceCheckList.size (); i++) {
                                final ServiceCheck serviceCheck;
                                serviceCheck = Constants.serviceCheckList.get (i);
                                JSONObject jsonObject = new JSONObject ();
                                jsonObject.put ("name_id", String.valueOf (serviceCheck.getService_check_id ()));
                                jsonObject.put ("status", serviceCheck.getSelection_text ());

                                if (serviceCheck.getSmCheckImageList ().size () > 0) {
                                    JSONArray jsonArraySmCheckImage = new JSONArray ();
                                    for (int j = 0; j < serviceCheck.getSmCheckImageList ().size (); j++) {
                                        ImageDetail smCheckImageDetail = serviceCheck.getSmCheckImageList ().get (j);
                                        JSONObject jsonObject1 = new JSONObject ();
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


                        pDialog = new ProgressDialog (DetailActivity.this);
                        Utils.showProgressDialog (pDialog, null);

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
                showSignatureDialog (1);
            }
        });
        ivCustomerSignature.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                if (etCustomerName.getText ().toString ().length () == 0) {
                    etCustomerName.setError ("Please enter the name");
                    Utils.shakeView (DetailActivity.this, etCustomerName);
                } else {
                    showSignatureDialog (2);
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
                    showSaveSerialDialog (0, Constants.workOrderDetail.getContract_number (), "Engine");
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
                    showSaveSerialDialog (0, Constants.workOrderDetail.getContract_number (), "ATS");
                }
                return true;
            }
        });
    }

    private void initData () {
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
                                    JSONArray jsonArray = jsonObj.getJSONArray ("service_forms");
                                    if (jsonArray.length () == 0) {

                                    } else {

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

    private void showSignatureDialog (final int flag) {
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
                        ImageDetail signatureImageDetail = new ImageDetail (Utils.bitmapToBase64 (bp), "", "Tech", "");
                        signatureImageList.add (signatureImageDetail);

//                        Constants.workOrderDetail.setImageInList (imageDetail);

                        //      Constants.workOrderDetail.setTech_image_str(Utils.bitmapToBase64(bp));
                        ivTechSignature.setImageBitmap (bp);
                        break;
                    case 2:
                        ImageDetail signatureImageDetail2 = new ImageDetail (Utils.bitmapToBase64 (bp), "", "Customer", etCustomerName.getText ().toString ());
                        signatureImageList.add (signatureImageDetail2);

//                        Constants.workOrderDetail.setImageInList (imageDetail2);

//                        Constants.workOrderDetail.setCustomer_signature(Utils.bitmapToBase64(bp));

                        ivCustomerSignature.setImageBitmap (bp);
                        break;
                }
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
        DateFormat df = new SimpleDateFormat ("yyyyMMdd_HHmmss");
        final String date = df.format (Calendar.getInstance ().getTime ());
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
                        ImageView image = null;
                        Bundle extras = data.getExtras ();
                        Bitmap thePic = extras.getParcelable ("data");

                        ImageDetail beforeImageDetail = new ImageDetail (Utils.bitmapToBase64 (thePic), "before_img_" + date, "", "");
                        beforeImageList.add (beforeImageDetail);

//                        Constants.workOrderDetail.setImageInList (imageDetail);
//                        Constants.workOrderDetail.setBefore_image_str(Utils.bitmapToBase64(thePic));
                        image = new ImageView (DetailActivity.this);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams (300, 225);
                        params.setMargins (10, 10, 10, 10);
                        image.setLayoutParams (params);
                        llBeforeImage.addView (image);
                        image.setImageBitmap (thePic);
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
                        ImageView image2 = null;
                        Bundle extras2 = data.getExtras ();
                        Bitmap thePic2 = extras2.getParcelable ("data");

                        ImageDetail afterImageDetail = new ImageDetail (Utils.bitmapToBase64 (thePic2), "after_img_" + date, "", "");
                        afterImageList.add (afterImageDetail);

//                        Constants.workOrderDetail.setImageInList (imageDetail2);


//                        Constants.workOrderDetail.setAfter_image_str(Utils.bitmapToBase64(thePic2));
                        image2 = new ImageView (DetailActivity.this);
                        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams (300, 225);
                        params3.setMargins (10, 10, 10, 10);
                        image2.setLayoutParams (params3);
                        llAfterImage.addView (image2);
                        image2.setImageBitmap (thePic2);
                        // ivAfterImage.setImageBitmap(thePic2);
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

                        ImageDetail beforeImageDetail2 = new ImageDetail (Utils.bitmapToBase64 (thePic3), "before_img_" + date, "", "");
                        beforeImageList.add (beforeImageDetail2);

//                        Constants.workOrderDetail.setImageInList (imageDetail3);

//                        Constants.workOrderDetail.setBefore_image_str(Utils.bitmapToBase64(thePic3));
                        image = new ImageView (DetailActivity.this);
                        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams (300, 225);
                        params2.setMargins (10, 10, 10, 10);
                        image.setLayoutParams (params2);
                        llBeforeImage.addView (image);
                        image.setImageBitmap (thePic3);

                        // ivBeforeImage.setImageBitmap(thePic3);
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

                        ImageDetail afterImageDetail2 = new ImageDetail (Utils.bitmapToBase64 (thePic4), "after_img_" + date, "", "");
                        afterImageList.add (afterImageDetail2);

//                        Constants.workOrderDetail.setImageInList (imageDetail4);
//          Constants.workOrderDetail.setAfter_image_str (Utils.bitmapToBase64 (thePic4));

                        image2 = new ImageView (DetailActivity.this);
                        LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams (300, 225);
                        params4.setMargins (10, 10, 10, 10);
                        image2.setLayoutParams (params4);
                        llAfterImage.addView (image2);
                        image2.setImageBitmap (thePic4);
                        break;

                    case PICK_FROM_GALLERY_LIST_ITEM_1:
                        Uri selectedImage3 = data.getData ();
                        try {
                            cropCapturedImage (selectedImage3, PICK_FROM_GALLERY_LIST_ITEM_1);
                        } catch (ActivityNotFoundException aNFE) {
                            Toast.makeText (this, "Sorry - your device doesn't support the crop action!", Toast.LENGTH_SHORT).show ();
                        }
                        break;

                    case PICK_FROM_GALLERY_LIST_ITEM_2:
                        Bundle extras5 = data.getExtras ();
                        Bitmap thePic5 = extras5.getParcelable ("data");
                        ImageDetail smCheckImageDetail = new ImageDetail (Utils.bitmapToBase64 (thePic5), "smcheck_img_" + date, "", "");
                        for (int i = 0; i < Constants.serviceCheckList.size (); i++) {
                            ServiceCheck serviceCheck = Constants.serviceCheckList.get (i);
                            if (serviceCheck.getService_check_id () == smCheckIdTemp) {
                                serviceCheck.setSmCheckImageInList (smCheckImageDetail);
                            }
                        }
                        smCheckIdTemp = 0;

                        break;

                    case PICK_FROM_CAMERA_LIST_ITEM_1:
                        File file3 = new File (Environment.getExternalStorageDirectory () + File.separator + "img.jpg");
                        try {
                            cropCapturedImage (Uri.fromFile (file3), PICK_FROM_CAMERA_LIST_ITEM_1);
                        } catch (ActivityNotFoundException aNFE) {
                            Toast.makeText (this, "Sorry - your device doesn't support the crop action!", Toast.LENGTH_SHORT).show ();
                        }
                        break;
                    case PICK_FROM_CAMERA_LIST_ITEM_2:
                        Bundle extras6 = data.getExtras ();
                        Bitmap thePic6 = extras6.getParcelable ("data");

                        ImageDetail smCheckImageDetail2 = new ImageDetail (Utils.bitmapToBase64 (thePic6), "smcheck_img_" + date, "", "");
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
            Toast.makeText (DetailActivity.this, "Your device doesn't support the crop action!", Toast.LENGTH_SHORT).show ();
        }
    }

    public boolean validate () {

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

//        return true;
    }

    private void showSaveSerialDialog (final int serial_id, final int contract_num, final String serial_type) {
        TextView tvSerialHeading;
        final EditText etSerial;
        final EditText etModel;
        final Spinner spManufacturer;
        TextView tvAddSerial;
        TextView tvCancelSerial;

        final Serial generatorSerial = new Serial (false, serial_id, 0, "", "", "", "");

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
            Utils.sendRequest (strRequest);
        } else {
            Utils.showOkDialog (DetailActivity.this, "Seems like there is no internet connection, the app will continue in Offline mode", false);
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
                                                        c.getInt ("manufacturer_id"), c.getString ("serial"),
                                                        c.getString ("model"), c.getString ("Type"), c.getString ("manufacturer_name"));
                                                atsSerialList.add (ATSSerial);
                                                break;
                                            case "Engine":
                                                Serial engineSerial = new
                                                        Serial (false, c.getInt ("serviceSerials_id"),
                                                        c.getInt ("manufacturer_id"), c.getString ("serial"),
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

                            llEngineSerial.removeAllViews ();
                            llATSSerial.removeAllViews ();

                            for (int i = 0; i < engineSerialList.size (); i++) {
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


                            for (int i = 0; i < atsSerialList.size (); i++) {
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
            Utils.showOkDialog (DetailActivity.this, "Seems like there is no internet connection, the app will continue in Offline mode", false);
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

    private class LoadServiceChecksInLinearLayout extends AsyncTask<String, Void, String> {
        Activity activity;

        LoadServiceChecksInLinearLayout (Activity activity) {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute () {
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
                        selectImage (LIST_ITEM_PICKER);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ivImageSelected.setImageDrawable (getResources ().getDrawable (R.drawable.ic_check_blue, getApplicationContext ().getTheme ()));
                        } else {
                            ivImageSelected.setImageDrawable (getResources ().getDrawable (R.drawable.ic_check_blue));
                        }
                    }
                });

                tvHeading.setText (text);

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
                            etComment.setError ("Comment is required");

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
                            etComment.setError ("Comment is required");

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

            pDialog.dismiss ();

        }
    }
}
