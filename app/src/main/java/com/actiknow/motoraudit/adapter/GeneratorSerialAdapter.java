package com.actiknow.motoraudit.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.actiknow.motoraudit.R;
import com.actiknow.motoraudit.activity.DetailActivity;
import com.actiknow.motoraudit.model.Serial;
import com.actiknow.motoraudit.utils.Constants;

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
                Intent intent = new Intent (activity, DetailActivity.class);
                Constants.workOrderDetail.setGenerator_model (generatorSerial.getModel_number ());
                Constants.workOrderDetail.setGenerator_serial (generatorSerial.getSerial_number ());
                Constants.workOrderDetail.setGenerator_make_id (generatorSerial.getManufacturer_id ());
                Constants.workOrderDetail.setGenerator_make_name (generatorSerial.getManufacturer_name ());
                Constants.workOrderDetail.setGenerator_serial_id (String.valueOf (generatorSerial.getSerial_id ()));
//                intent.putExtra ("wo_id", workOrder.getWo_id ());
//                intent.putExtra ("wo_contract_num", workOrder.getWo_contract_num ());
//                intent.putExtra ("wo_site_name", workOrder.getWo_site_name ());
                activity.startActivity (intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView tvGeneratorSerial;
        TextView tvGeneratorModel;
        TextView tvGeneratorManufacturer;
        CheckBox cbSelected;
    }
}