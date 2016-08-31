package com.actiknow.motoraudit.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actiknow.motoraudit.R;
import com.actiknow.motoraudit.app.AppController;
import com.actiknow.motoraudit.model.Manufacturer;
import com.actiknow.motoraudit.model.ServiceCheck;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Admin on 23-12-2015.
 */
public class Utils {
    public static int isValidEmail (String email) {
        if (email.length () != 0) {
            boolean validMail = isValidEmail2 (email);
            if (validMail)
                return 1;
            else
                return 2;
        } else
            return 0;
    }

    public static boolean isValidEmail2 (CharSequence target) {
        return ! TextUtils.isEmpty (target) && android.util.Patterns.EMAIL_ADDRESS.matcher (target).matches ();
    }

    public static int isValidPassword (String password) {
        if (password.length () > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public static Bitmap base64ToBitmap (String b64) {
        byte[] imageAsBytes = Base64.decode (b64.getBytes (), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray (imageAsBytes, 0, imageAsBytes.length);
    }

    public static String bitmapToBase64 (Bitmap bmp) {
        if (bmp != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream ();
            bmp.compress (Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray ();
            String encodedImage = Base64.encodeToString (imageBytes, Base64.DEFAULT);
            return encodedImage;
        } else {
            return "";
        }
    }

    public static String convertTimeFormat (String OrigFormat) {
        if (OrigFormat != "null") {
            SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
            Date testDate = null;
            try {
                testDate = sdf.parse (OrigFormat);
            } catch (Exception ex) {
                ex.printStackTrace ();
            }
            SimpleDateFormat formatter = new SimpleDateFormat ("dd/MM/yyyy");
            String newFormat = formatter.format (testDate);
            return newFormat;
        } else {
            return "Unavailable";
        }
    }

    public static void showOkDialog (final Activity activity, String message, final boolean finish_flag) {
        AlertDialog.Builder builder = new AlertDialog.Builder (activity);
        builder.setMessage (message)
                .setCancelable (false)
                .setPositiveButton ("OK", new DialogInterface.OnClickListener () {
                    public void onClick (DialogInterface dialog, int id) {
                        dialog.dismiss ();
                        if (finish_flag) {
                            activity.finish ();
                            activity.overridePendingTransition (R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                    }
                });
        AlertDialog alert = builder.create ();
        alert.show ();
    }

    public static String URLToBase64 (String url) {
        try {
            URL url2 = new URL (url);
            HttpURLConnection connection = (HttpURLConnection) url2.openConnection ();
            connection.setDoInput (true);
            connection.connect ();
            InputStream input = connection.getInputStream ();
            Bitmap myBitmap = BitmapFactory.decodeStream (input);
            return Utils.bitmapToBase64 (myBitmap);
        } catch (IOException e) {
            e.printStackTrace ();
            return "";
        }
    }

    public static Bitmap URLToBitmap (String url) {
        try {
            URL url2 = new URL (url);
            HttpURLConnection connection = (HttpURLConnection) url2.openConnection ();
            connection.setDoInput (true);
            connection.connect ();
            InputStream input = connection.getInputStream ();
            Bitmap myBitmap = BitmapFactory.decodeStream (input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace ();
            return null;
        }
    }


    public static void showSnackBar (CoordinatorLayout coordinatorLayout, String message) {
        final Snackbar snackbar = Snackbar
                .make (coordinatorLayout, message, Snackbar.LENGTH_LONG)
                .setAction ("DISMISS", new View.OnClickListener () {
                    @Override
                    public void onClick (View view) {
                    }
                });
        snackbar.show ();
    }

    public static void showToast (Activity activity, String message) {
        Toast.makeText (activity, message, Toast.LENGTH_SHORT).show ();
    }

    public static void setTypefaceToAllViews (Activity activity, View view) {
        Typeface tf = SetTypeFace.getTypeface (activity);
        SetTypeFace.applyTypeface (SetTypeFace.getParentView (view), tf);
    }

    public static void showProgressDialog (ProgressDialog progressDialog, String message) {
        // Initialize the progressDialog before calling this function
        TextView tvMessage;
        progressDialog.show ();
        progressDialog.getWindow ().setBackgroundDrawable (new ColorDrawable (android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView (R.layout.progress_dialog);
        tvMessage = (TextView) progressDialog.findViewById (R.id.tvProgressDialogMessage);
        if (message != null) {
            tvMessage.setText (message);
            tvMessage.setVisibility (View.VISIBLE);
        } else
            tvMessage.setVisibility (View.GONE);
        progressDialog.setCancelable (false);
    }

    public static void showLog (int log_type, String tag, String message, boolean show_flag) {
        if (Constants.show_log) {
            if (show_flag) {
                switch (log_type) {
                    case Log.DEBUG:
                        Log.d (tag, message);
                        break;
                    case Log.ERROR:
                        Log.e (tag, message);
                        break;
                    case Log.INFO:
                        Log.i (tag, message);
                        break;
                    case Log.VERBOSE:
                        Log.v (tag, message);
                        break;
                    case Log.WARN:
                        Log.w (tag, message);
                        break;
                    case Log.ASSERT:
                        Log.wtf (tag, message);
                        break;
                }
            }
        }
    }

    public static void showErrorInEditText (EditText editText, String message) {
        editText.setError (message);
    }

    public static void hideSoftKeyboard (Activity activity) {
        View view = activity.getCurrentFocus ();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService (Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow (view.getWindowToken (), 0);
        }
    }

    public static boolean isPackageExists (Activity activity, String targetPackage) {
        List<ApplicationInfo> packages;
        PackageManager pm;
        pm = activity.getPackageManager ();
        packages = pm.getInstalledApplications (0);
        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals (targetPackage))
                return true;
        }
        return false;
    }

    public static void sendRequest (StringRequest strRequest, int timeout_seconds) {
        int timeout = timeout_seconds * 1000;
        strRequest.setShouldCache (false);
        AppController.getInstance ().addToRequestQueue (strRequest);
        strRequest.setRetryPolicy (new DefaultRetryPolicy (timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public static void hideKeyboard (final Activity activity, View view) {
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount (); i++) {
                View innerView = ((ViewGroup) view).getChildAt (i);
                hideKeyboard (activity, innerView);
            }
        }
        if (! (view instanceof EditText)) {
            view.setOnTouchListener (new View.OnTouchListener () {
                @Override
                public boolean onTouch (View v, MotionEvent event) {
                    hideSoftKeyboard (activity, v);
                    return false;
                }

            });
        }

    }

    public static void hideSoftKeyboard (Activity activity, View view) {
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService (Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                if (android.os.Build.VERSION.SDK_INT < 11) {
                    inputManager.hideSoftInputFromWindow (view.getWindowToken (),
                            0);
                } else {
                    if (activity.getCurrentFocus () != null) {
                        inputManager.hideSoftInputFromWindow (activity.getCurrentFocus ().getWindowToken (),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    view.clearFocus ();
                }
                view.clearFocus ();
            }
        }
    }

    public static String getServiceCheckJSONFromAsset (Activity activity) {
        String json = null;
        try {
            InputStream is = activity.getAssets ().open ("service_check.json");
            int size = is.available ();
            byte[] buffer = new byte[size];
            is.read (buffer);
            is.close ();
            json = new String (buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace ();
            return null;
        }
        return json;
    }

    public static String getGeneratorConditionJSONFromAsset (Activity activity) {
        String json = null;
        try {
            InputStream is = activity.getAssets ().open ("generator_condition.json");
            int size = is.available ();
            byte[] buffer = new byte[size];
            is.read (buffer);
            is.close ();
            json = new String (buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace ();
            return null;
        }
        return json;
    }

    public static String getManufacturerName (int manufacture_id) {
        for (int i = 0; i < Constants.manufacturerList.size (); i++) {
            Manufacturer manufacturer = Constants.manufacturerList.get (i);
            if (manufacturer.getManufacturer_id () == manufacture_id) {
                return manufacturer.getManufacturer_name ();
            }
        }
        return null;
    }

    public static void shakeView (Activity activity, View view) {
        Animation shake;
        shake = AnimationUtils.loadAnimation (activity, R.anim.shake);
        view.setAnimation (shake);
    }

    public static void clearAllServiceChecks () {
        for (int i = 0; i < Constants.serviceCheckList.size (); i++) {
            ServiceCheck serviceCheck = Constants.serviceCheckList.get (i);
            serviceCheck.setSelection_flag (0);
            serviceCheck.setSelection_text ("");
            serviceCheck.setComment ("");
            serviceCheck.removeSmCheckImageInList (0);
        }
    }

    public static Bitmap compressBitmap (Bitmap bitmap, Activity activity) {
        Bitmap decoded = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream ();
            if (NetworkConnection.isNetworkAvailable (activity)) {
                bitmap.compress (Bitmap.CompressFormat.JPEG, 20, out);
            } else {
                bitmap.compress (Bitmap.CompressFormat.JPEG, 20, out);
            }
            decoded = Utils.scaleDown (BitmapFactory.decodeStream (new ByteArrayInputStream (out.toByteArray ())), 800, true);
        } catch (Exception e) {
            e.printStackTrace ();
            Utils.showLog (Log.ERROR, "EXCEPTION", e.getMessage (), true);
        }
        return decoded;
    }

    public static Bitmap scaleDown (Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min ((float) maxImageSize / realImage.getWidth (), (float) maxImageSize / realImage.getHeight ());
        int width = Math.round ((float) ratio * realImage.getWidth ());
        int height = Math.round ((float) ratio * realImage.getHeight ());
        Bitmap newBitmap = Bitmap.createScaledBitmap (realImage, width, height, filter);
        return newBitmap;
    }

    public static boolean shouldAskPermission () {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public static void selectSpinnerValue (Spinner spinner, String myString) {
        for (int i = 0; i < Constants.manufacturerList.size (); i++) {
            Manufacturer manufacturer = Constants.manufacturerList.get (i);
            if (manufacturer.getManufacturer_name ().equalsIgnoreCase (myString)) {
                spinner.setSelection (i);
                break;
            }
        }
    }

}
