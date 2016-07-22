package com.actiknow.motoraudit.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.actiknow.motoraudit.R;
import com.actiknow.motoraudit.model.Manufacturer;

import java.util.List;

public class ManufacturerAdapter extends ArrayAdapter<String> {

    private List data;
    Manufacturer manufacturer = null;
    LayoutInflater inflater;

    TextView tvManufacturerName;
    TextView tvManufacturerID;

    public ManufacturerAdapter (Activity activity, int textViewResourceId, List objects) {
        super (activity, textViewResourceId, objects);
        data = objects;
        inflater = (LayoutInflater) activity.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView (int position, View convertView, ViewGroup parent) {
        return getCustomView (position, convertView, parent);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        return getCustomView (position, convertView, parent);
    }

    public View getCustomView (int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate (R.layout.spinner_item, parent, false);
        manufacturer = (Manufacturer) data.get (position);
        tvManufacturerName = (TextView) row.findViewById (R.id.tvManufacturerName);
        tvManufacturerID = (TextView) row.findViewById (R.id.tvManufacturerID);
        if (position == 0) {
            // Default selected Spinner item
            tvManufacturerName.setText ("Please select Manufacturer");
            tvManufacturerID.setText ("0");
        } else {
            // Set values for spinner each row
            tvManufacturerName.setText (manufacturer.getManufacturer_name ());
            tvManufacturerID.setText ("" + manufacturer.getManufacturer_id ());
        }
        return row;
    }
}
