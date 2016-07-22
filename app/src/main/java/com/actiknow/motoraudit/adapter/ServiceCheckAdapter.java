package com.actiknow.motoraudit.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.actiknow.motoraudit.R;
import com.actiknow.motoraudit.model.ServiceCheck;

import java.util.List;

public class ServiceCheckAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;

    private List<ServiceCheck> serviceCheckList;
    int group_id;
    int count = 0;
    private Typeface typeface;

    public ServiceCheckAdapter (Activity activity, List<ServiceCheck> serviceCheckList, int group_id) {
        this.activity = activity;
        this.serviceCheckList = serviceCheckList;
        this.group_id = group_id;
//        typeface = Typeface.createFromAsset(activity.getAssets(), "Kozuka-Gothic.ttf");
    }

    @Override
    public int getCount () {
//        for(int i=0; i<serviceCheckList.size ();i++){
//            if (serviceCheckList.get (i).getGroup_id () == this.group_id)
//                count++;
//        }
//        return count;
        return serviceCheckList.size ();
    }

    @Override
    public Object getItem (int i) {

        return serviceCheckList.get (i);
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
            convertView = inflater.inflate (R.layout.listview_item_service_check, null);
            holder = new ViewHolder ();

            holder.tvHeading = (TextView) convertView.findViewById (R.id.tvHeading);
            holder.tvSubHeading = (TextView) convertView.findViewById (R.id.tvSubHeading);
            holder.ivImage = (ImageView) convertView.findViewById (R.id.ivImage);
            holder.ivImageSelected = (ImageView) convertView.findViewById (R.id.ivImageSelected);
            holder.rgSelection = (RadioGroup) convertView.findViewById (R.id.rgSelection);
            holder.rbPass = (RadioButton) convertView.findViewById (R.id.rbPass);
            holder.rbAdvise = (RadioButton) convertView.findViewById (R.id.rbAdvise);
            holder.rbFail = (RadioButton) convertView.findViewById (R.id.rbFail);
            holder.rbNA = (RadioButton) convertView.findViewById (R.id.rbNA);
            holder.etComment = (EditText) convertView.findViewById (R.id.etComments);

            convertView.setTag (holder);
        } else
            holder = (ViewHolder) convertView.getTag ();


        ServiceCheck serviceCheck = serviceCheckList.get (position);

        holder.tvHeading.setText (serviceCheck.getHeading ());
        holder.tvSubHeading.setText (serviceCheck.getSub_heading ());

        Spanned text;
        if (serviceCheck.getSub_heading ().length ()>0)
            text = Html.fromHtml ("<b>" + serviceCheck.getHeading () + "</b> - " + serviceCheck.getSub_heading ());
        else
            text = Html.fromHtml ("<b>" + serviceCheck.getHeading () + "</b>");

        holder.tvHeading.setText (text);


//        if(serviceCheck.isComment_required ())
//            holder.etComment.setHint ("Comment is required");

        if (serviceCheck.isComment_required () && holder.etComment.getText ().toString ().length ()==0)
            holder.etComment.setError ("Comment is required");

        convertView.setOnClickListener (new View.OnClickListener () {
            private void die () {
            }

            @Override
            public void onClick (View arg0) {
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView tvHeading;
        TextView tvSubHeading;
        ImageView ivImageSelected;
        ImageView ivImage;
        RadioGroup rgSelection;
        RadioButton rbPass;
        RadioButton rbAdvise;
        RadioButton rbFail;
        RadioButton rbNA;
        EditText etComment;
    }
}