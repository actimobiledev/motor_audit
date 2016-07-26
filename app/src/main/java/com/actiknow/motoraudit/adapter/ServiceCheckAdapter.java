package com.actiknow.motoraudit.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.actiknow.motoraudit.R;
import com.actiknow.motoraudit.model.ServiceCheck;
import com.actiknow.motoraudit.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ServiceCheckAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;

    private List<ServiceCheck> serviceCheckList;
    int group_id, start_position;

    private ArrayList<Boolean> isCommentSatisfied;

    private Typeface typeface;

    public ServiceCheckAdapter (Activity activity, List<ServiceCheck> serviceCheckList, int group_id, int start_position) {
        this.activity = activity;
        this.serviceCheckList = serviceCheckList;
        this.group_id = group_id;
        this.start_position = start_position;
        isCommentSatisfied = new ArrayList<Boolean> ();
        for (int i = 0; i < serviceCheckList.size (); i++) {
            isCommentSatisfied.add (false);
        }
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
    public View getView (final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate (R.layout.listview_item_service_check, null);
            holder = new ViewHolder ();

            holder.tvHeading = (TextView) convertView.findViewById (R.id.tvHeading);
            holder.ivImage = (ImageView) convertView.findViewById (R.id.ivImage);
            holder.ivImageSelected = (ImageView) convertView.findViewById (R.id.ivImageSelected);
            holder.rbPass = (RadioButton) convertView.findViewById (R.id.rbPass);
            holder.rbAdvise = (RadioButton) convertView.findViewById (R.id.rbAdvise);
            holder.rbFail = (RadioButton) convertView.findViewById (R.id.rbFail);
            holder.rbNA = (RadioButton) convertView.findViewById (R.id.rbNA);
            holder.rbYes = (RadioButton) convertView.findViewById (R.id.rbYes);
            holder.rbNo = (RadioButton) convertView.findViewById (R.id.rbNo);
            holder.rbNA2 = (RadioButton) convertView.findViewById (R.id.rbNA2);

            holder.llPassAdviceFail = (LinearLayout) convertView.findViewById (R.id.llPassAdviceFail);
            holder.llYesNo = (LinearLayout) convertView.findViewById (R.id.llYesNo);
            holder.etComment = (EditText) convertView.findViewById (R.id.etComments);

            convertView.setTag (holder);
        } else
            holder = (ViewHolder) convertView.getTag ();


        final ServiceCheck serviceCheck = serviceCheckList.get (position);

        final ServiceCheck serviceCheckTemp = Constants.serviceCheckList.get (position + start_position);

        holder.tvHeading.setText (serviceCheck.getHeading ());

        Spanned text;
        if (serviceCheck.getSub_heading ().length ()>0)
            text = Html.fromHtml ("<b>" + serviceCheck.getHeading () + "</b> - " + serviceCheck.getSub_heading ());
        else
            text = Html.fromHtml ("<b>" + serviceCheck.getHeading () + "</b>");

        holder.tvHeading.setText (text);


        holder.rbPass.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                if (b){
                    holder.rbNA.setChecked (false);
                    holder.rbFail.setChecked (false);
                    holder.rbAdvise.setChecked (false);
                    holder.rbPass.setChecked (true);
                    serviceCheck.setSelection_text ("Pass");
                    serviceCheck.setSelection_flag (3);
                }
            }
        });
        holder.rbAdvise.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                if (b){
                    holder.rbNA.setChecked (false);
                    holder.rbFail.setChecked (false);
                    holder.rbAdvise.setChecked (true);
                    holder.rbPass.setChecked (false);
                    serviceCheck.setSelection_text ("Advise");
                    serviceCheck.setSelection_flag (2);
                }
            }
        });
        holder.rbFail.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                if (b){
                    holder.rbNA.setChecked (false);
                    holder.rbFail.setChecked (true);
                    holder.rbAdvise.setChecked (false);
                    holder.rbPass.setChecked (false);
                    serviceCheck.setSelection_text ("Fail");
                    serviceCheck.setSelection_flag (1);
                }
            }
        });
        holder.rbNA.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                if(b){
                    holder.rbNA.setChecked (true);
                    holder.rbFail.setChecked (false);
                    holder.rbAdvise.setChecked (false);
                    holder.rbPass.setChecked (false);
                    serviceCheck.setSelection_text ("N/A");
                    serviceCheck.setSelection_flag (0);
                }
            }
        });


        holder.rbYes.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                if (b) {
                    holder.rbYes.setChecked (true);
                    holder.rbNo.setChecked (false);
                    holder.rbNA2.setChecked (false);
                    serviceCheck.setSelection_text ("Yes");
                    serviceCheck.setSelection_flag (2);
                }
            }
        });
        holder.rbNo.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                if (b) {
                    holder.rbYes.setChecked (false);
                    holder.rbNo.setChecked (true);
                    holder.rbNA2.setChecked (false);
                    serviceCheck.setSelection_text ("No");
                    serviceCheck.setSelection_flag (1);
                }
            }
        });
        holder.rbNA2.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged (CompoundButton compoundButton, boolean b) {
                if (b) {
                    holder.rbYes.setChecked (false);
                    holder.rbNo.setChecked (false);
                    holder.rbNA2.setChecked (true);
                    serviceCheck.setSelection_text ("N/A");
                    serviceCheck.setSelection_flag (0);
                }
            }
        });


        switch (group_id){
            case 12 :
                switch (serviceCheck.getSelection_flag ()) {
                    case 0:
                        holder.rbNA2.setChecked (true);
                        holder.rbNo.setChecked (false);
                        holder.rbYes.setChecked (false);
                        break;
                    case 1:
                        holder.rbNA2.setChecked (false);
                        holder.rbNo.setChecked (true);
                        holder.rbYes.setChecked (false);
                        break;
                    case 2:
                        holder.rbNA2.setChecked (false);
                        holder.rbNo.setChecked (false);
                        holder.rbYes.setChecked (true);
                        break;
                }
                holder.llYesNo.setVisibility (View.VISIBLE);
                holder.llPassAdviceFail.setVisibility (View.GONE);
                break;
            default:
                switch (serviceCheck.getSelection_flag ()){
                    case 0  :
                        holder.rbNA.setChecked (true);
                        holder.rbFail.setChecked (false);
                        holder.rbAdvise.setChecked (false);
                        holder.rbPass.setChecked (false);
                        break;
                    case 1  :
                        holder.rbNA.setChecked (false);
                        holder.rbFail.setChecked (true);
                        holder.rbAdvise.setChecked (false);
                        holder.rbPass.setChecked (false);
                        break;
                    case 2  :
                        holder.rbNA.setChecked (false);
                        holder.rbFail.setChecked (false);
                        holder.rbAdvise.setChecked (true);
                        holder.rbPass.setChecked (false);
                        break;
                    case 3  :
                        holder.rbNA.setChecked (false);
                        holder.rbFail.setChecked (false);
                        holder.rbAdvise.setChecked (false);
                        holder.rbPass.setChecked (true);
                        break;
                }
                holder.llYesNo.setVisibility (View.GONE);
                holder.llPassAdviceFail.setVisibility (View.VISIBLE);
                break;
        }


        holder.etComment.addTextChangedListener (new TextWatcher () {
            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                if (holder.etComment.getText ().toString ().length ()!=0)
                    serviceCheckTemp.setComment (String.valueOf (s));
                else
                    serviceCheckTemp.setComment ("");
            }

            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged (Editable s) {

                if(serviceCheck.isComment_required ()){
                    if (holder.rbFail.isChecked () || holder.rbAdvise.isChecked ()) {
                        if (s.toString ().trim ().length () > 0) {
                            isCommentSatisfied.add (position, true);
                        } else {
                            isCommentSatisfied.add (position, false);
                        }
                    } else {
                        if (s.toString ().trim ().length () > 0){
                            isCommentSatisfied.add (position, true);
                        } else {
                            isCommentSatisfied.add (position, false);
                        }
                    }
                } else {
                    if (holder.rbFail.isChecked () || holder.rbAdvise.isChecked ()) {
                        if (s.toString ().trim ().length () > 0) {
                            isCommentSatisfied.add (position, true);
                        } else {
                            isCommentSatisfied.add (position, false);
                        }
                    } else
                        isCommentSatisfied.add (position, true);
                }

            }
        });




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
        ImageView ivImageSelected;
        ImageView ivImage;
        RadioButton rbPass;
        RadioButton rbAdvise;
        RadioButton rbFail;
        RadioButton rbNA;
        RadioButton rbNA2;
        RadioButton rbYes;
        RadioButton rbNo;
        EditText etComment;

        LinearLayout llYesNo;
        LinearLayout llPassAdviceFail;
    }

    public boolean isCommentSatisfied () {
        boolean isAnyEmpty = true;
        for (int i = 0; i < isCommentSatisfied.size (); i++) {
            if (! isCommentSatisfied.get (i)) {
                isAnyEmpty = false;
                break;
            }
        }
        return isAnyEmpty;
    }
}