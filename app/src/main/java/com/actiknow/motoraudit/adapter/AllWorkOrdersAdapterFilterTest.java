package com.actiknow.motoraudit.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.actiknow.motoraudit.R;
import com.actiknow.motoraudit.activity.ServiceFormsListActivity;
import com.actiknow.motoraudit.model.WorkOrder;
import com.actiknow.motoraudit.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class AllWorkOrdersAdapterFilterTest extends BaseAdapter implements Filterable {
    WorkOrderFilter workOrderFilter;
    private Activity activity;
    private LayoutInflater inflater;
    private List<WorkOrder> workOrderList;
    private List<WorkOrder> filteredWorkOrderList;
    private Typeface typeface;

    public AllWorkOrdersAdapterFilterTest (Activity activity, List<WorkOrder> workOrderList) {
        this.activity = activity;
        this.workOrderList = workOrderList;
        this.filteredWorkOrderList = workOrderList;

        getFilter ();
        typeface = Typeface.createFromAsset (activity.getAssets (), "Kozuka-Gothic.ttf");
    }

    @Override
    public int getCount () {
        return filteredWorkOrderList.size ();
    }

    @Override
    public Object getItem (int location) {
        return filteredWorkOrderList.get (location);
    }

    @Override
    public long getItemId (int position) {
        return position;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate (R.layout.listview_item_work_order, null);
            holder = new ViewHolder ();
            holder.tvWOId = (TextView) convertView.findViewById (R.id.tvWorkOrderSerialNumber);
            holder.tvWOSiteName = (TextView) convertView.findViewById (R.id.tvWorkOrderSiteName);
            convertView.setTag (holder);
        } else
            holder = (ViewHolder) convertView.getTag ();


        holder.tvWOId.setTypeface (typeface);
        holder.tvWOSiteName.setTypeface (typeface);

        final WorkOrder workOrder = workOrderList.get (position);
        holder.tvWOId.setText ("" + workOrder.getWo_id ());
        holder.tvWOSiteName.setText (workOrder.getWo_site_name ());

        convertView.setOnClickListener (new View.OnClickListener () {
            private void die () {
            }

            @Override
            public void onClick (View arg0) {
                Intent intent = new Intent (activity, ServiceFormsListActivity.class);
                Constants.workOrderDetail.setWork_order_id (workOrder.getWo_id ());
                Constants.workOrderDetail.setContract_number (workOrder.getWo_contract_num ());
                Constants.workOrderDetail.setSite_name (workOrder.getWo_site_name ());
                Constants.workOrderDetail.setCustomer_name (workOrder.getWo_customer_name ());
                activity.startActivity (intent);
            }
        });
        return convertView;
    }

    @Override
    public Filter getFilter () {
        if (workOrderFilter == null) {
            workOrderFilter = new WorkOrderFilter ();
        }
        return workOrderFilter;
    }

    static class ViewHolder {
        TextView tvWOId;
        TextView tvWOSiteName;
    }

    /**
     * Custom filter for friend list
     * Filter content in friend list according to the search text
     */
    private class WorkOrderFilter extends Filter {

        @Override
        protected FilterResults performFiltering (CharSequence constraint) {
            FilterResults filterResults = new FilterResults ();
            if (constraint != null && constraint.length () > 0) {
                ArrayList<WorkOrder> workOrderTempList = new ArrayList<WorkOrder> ();

                // search content in friend list
                for (WorkOrder workOrder : workOrderList) {
                    if (workOrder.getWo_id () == Integer.parseInt (constraint.toString ())) {
                        workOrderTempList.add (workOrder);
                    }
                }

                filterResults.count = workOrderTempList.size ();
                filterResults.values = workOrderTempList;
            } else {
                filterResults.count = workOrderList.size ();
                filterResults.values = workOrderList;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         *
         * @param constraint text
         * @param results    filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults (CharSequence constraint, FilterResults results) {
            filteredWorkOrderList = (ArrayList<WorkOrder>) results.values;
            notifyDataSetChanged ();
        }
    }

}