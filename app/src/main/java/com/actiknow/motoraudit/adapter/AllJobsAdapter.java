package com.actiknow.motoraudit.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.actiknow.motoraudit.R;
import com.actiknow.motoraudit.model.Job;
import com.actiknow.motoraudit.utils.Utils;

import java.util.List;

public class AllJobsAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Job> jobList;

	public AllJobsAdapter (Activity activity, List<Job> jobList) {
		this.activity = activity;
		this.jobList = jobList;
	}

	@Override
	public int getCount() {
		return jobList.size ();
	}

	@Override
	public Object getItem(int location) {
		return jobList.get (location);
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
			convertView = inflater.inflate (R.layout.listview_item_job, null);
			holder = new ViewHolder ();
			holder.job_date = (TextView) convertView.findViewById (R.id.tvJobDate);
			holder.job_serial_number = (TextView) convertView.findViewById (R.id.tvJobSerialNumber);
			convertView.setTag (holder);
		} else
			holder = (ViewHolder) convertView.getTag ();

		Utils.setTypefaceToAllViews (activity, holder.job_date);

		final Job job = jobList.get (position);
		holder.job_date.setText (job.getJob_id ());
		holder.job_serial_number.setText (job.getJob_serial_number ());

		convertView.setOnClickListener (new View.OnClickListener () {
			private void die () {
			}

			@Override
			public void onClick (View arg0) {
//				Constants.atm_unique_id = atm.getAtm_unique_id ().toUpperCase ();
//				Constants.atm_agency_id = atm.getAtm_agency_id ();

//				Constants.report.setAtm_id (atm.getAtm_id ());
//				Constants.report.setAuditor_id (Constants.auditor_id_main);
//				Constants.report.setAgency_id (atm.getAtm_agency_id ());
//				Constants.report.setAtm_unique_id (atm.getAtm_unique_id ().toUpperCase ());
//				Constants.report.setLatitude (String.valueOf (Constants.latitude));
//				Constants.report.setLongitude (String.valueOf (Constants.longitude));



/*
				AlertDialog.Builder builder = new AlertDialog.Builder (activity);
				builder.setMessage ("Please take ahe ATM Machine\nNote : This image will be Geotagged")
						.setCancelable (false)
						.setPositiveButton ("OK", new DialogInterface.OnClickListener () {
							public void onClick (DialogInterface dialog, int id) {
								dialog.dismiss ();
								Intent mIntent = null;
							}
						})
						.setNegativeButton ("CANCEL", new DialogInterface.OnClickListener () {
							public void onClick (DialogInterface dialog, int id) {
								dialog.dismiss ();
							}
						});
				AlertDialog alert = builder.create ();
				alert.show ();
				*/
			}
		});
		return convertView;
	}

	static class ViewHolder {
		TextView job_date;
		TextView job_serial_number;
	}
}