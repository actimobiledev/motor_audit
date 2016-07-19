package com.actiknow.motoraudit.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.actiknow.motoraudit.R;
import com.actiknow.motoraudit.activity.DetailActivity;
import com.actiknow.motoraudit.model.WorkOrder;

import java.util.List;

public class AllWorkOrdersAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<WorkOrder> workOrderList;
    private Typeface typeface;

	public AllWorkOrdersAdapter (Activity activity, List<WorkOrder> workOrderList) {
		this.activity = activity;
		this.workOrderList = workOrderList;
        typeface = Typeface.createFromAsset (activity.getAssets (), "Kozuka-Gothic.ttf");
    }

	@Override
	public int getCount() {
		return workOrderList.size ();
	}

	@Override
	public Object getItem(int location) {
		return workOrderList.get (location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
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
				Intent intent = new Intent (activity, DetailActivity.class);
                intent.putExtra ("wo_id", workOrder.getWo_id ());
                intent.putExtra ("wo_contract_num", workOrder.getWo_contract_num ());
                activity.startActivity (intent);
//                Utils.showToast (activity, workOrder.getWo_site_name ());
			}
		});
		return convertView;
	}

	static class ViewHolder {
		TextView tvWOId;
		TextView tvWOSiteName;
	}
}