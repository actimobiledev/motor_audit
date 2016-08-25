package com.actiknow.motoraudit.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.actiknow.motoraudit.R;
import com.actiknow.motoraudit.activity.DetailActivity;
import com.actiknow.motoraudit.activity.ViewFormActivity;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GeneratorSerialAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Serial> generatorSerialList;

    private Typeface typeface;

    public GeneratorSerialAdapter (Activity activity, List<Serial> generatorSerialList) {
        this.activity = activity;
        this.generatorSerialList = generatorSerialList;
//        typeface = Typeface.createFromAsset(activity.getAssets(), "Kozuka-Gothic.ttf");
    }

    @Override
    public int getCount () {
        return generatorSerialList.size ();
    }

    @Override
    public Object getItem (int i) {
        return generatorSerialList.get (i);
    }

    @Override
    public long getItemId (int position) {
        return position;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new ViewHolder ();
            convertView = inflater.inflate (R.layout.listview_item_generator_serial, null);
            holder.tvGeneratorSerial = (TextView) convertView.findViewById (R.id.tvGeneratorSerial);
            holder.tvGeneratorModel = (TextView) convertView.findViewById (R.id.tvGeneratorModel);
            holder.tvGeneratorManufacturer = (TextView) convertView.findViewById (R.id.tvGeneratorManufacturer);
            holder.cbSelected = (CheckBox) convertView.findViewById (R.id.cbSelected);
            convertView.setTag (holder);
        } else
            holder = (ViewHolder) convertView.getTag ();

        final Serial generatorSerial = generatorSerialList.get (position);
        holder.tvGeneratorSerial.setText ("" + generatorSerial.getSerial_number ());
        holder.tvGeneratorModel.setText ("" + generatorSerial.getModel_number ());
        holder.tvGeneratorManufacturer.setText (generatorSerial.getManufacturer_name ());
        holder.cbSelected.setVisibility (View.GONE);

        convertView.setOnClickListener (new View.OnClickListener () {
            private void die () {
            }

            @Override
            public void onClick (View arg0) {


                Constants.workOrderDetail.setGenerator_model (generatorSerial.getModel_number ());
                Constants.workOrderDetail.setGenerator_serial (generatorSerial.getSerial_number ());
                Constants.workOrderDetail.setGenerator_make_id (generatorSerial.getManufacturer_id ());
                Constants.workOrderDetail.setGenerator_make_name (generatorSerial.getManufacturer_name ());
                Constants.workOrderDetail.setGenerator_serial_id (generatorSerial.getSerial_id ());
                getWorkOrderDetailFromServer (Constants.workOrderDetail.getWork_order_id (), Constants.workOrderDetail.getGenerator_serial_id (), generatorSerial.getForm_id ());
            }
        });
        return convertView;
    }

    private void getWorkOrderDetailFromServer (final int wo_id, final int generator_serial_id, final int form_id) {
        if (NetworkConnection.isNetworkAvailable (activity)) {
            Utils.showLog (Log.INFO, AppConfigTags.URL, AppConfigURL.API_URL, true);
            StringRequest strRequest = new StringRequest (Request.Method.POST, AppConfigURL.API_URL,
                    new Response.Listener<String> () {
                        @Override
                        public void onResponse (String response) {
                            Intent intent = null;
                            Utils.showLog (Log.INFO, AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject (response);
                                    switch (jsonObj.getInt ("error_code")) {
                                        case 0:
                                            intent = new Intent (activity, ViewFormActivity.class);
                                            break;
                                        default:
                                            intent = new Intent (activity, DetailActivity.class);
                                            break;
                                    }

                                } catch (JSONException e) {
                                    intent = new Intent (activity, DetailActivity.class);
                                    e.printStackTrace ();
                                }
                            } else {
                                intent = new Intent (activity, DetailActivity.class);
                                Utils.showLog (Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }

                            activity.startActivity (intent);


                        }
                    },
                    new Response.ErrorListener () {
                        @Override
                        public void onErrorResponse (VolleyError error) {
                            Utils.showLog (Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString (), true);
                            Intent intent = new Intent (activity, DetailActivity.class);
                            activity.startActivity (intent);
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
//            Utils.showLog (Log.DEBUG, "FORM ID IN OFFLINE :", "" + form_id, true);
            if (form_id == 0) {
                Intent intent = new Intent (activity, DetailActivity.class);
                activity.startActivity (intent);
            } else {
                Utils.showOkDialog (activity, "Sorry a form for this Serial already exist", false);
            }
        }
    }

    static class ViewHolder {
        TextView tvGeneratorSerial;
        TextView tvGeneratorModel;
        TextView tvGeneratorManufacturer;
        CheckBox cbSelected;
    }

}