package com.companion.locationapp.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.companion.locationapp.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.R.attr.resource;
import static android.R.attr.thickness;

/**
 * Created by Turni on 1/17/17.
 */

public class UserAdapter extends ArrayAdapter {

    private ArrayList<User> userArrayList;

    private static class ViewHolder {
        TextView name;
        TextView latitude;
        TextView longitude;
    }

    public UserAdapter(Context context, ArrayList<User> objects) {
        super(context, R.layout.user_item_list, objects);
        this.userArrayList = objects;
    }



    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_item_list, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.nameItem);
            viewHolder.latitude = (TextView) convertView.findViewById(R.id.latitudeItem);
            viewHolder.longitude = (TextView) convertView.findViewById(R.id.longitudeItem);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        viewHolder.name.setText(user.getName());
        if (user.getLatitude() != null && user.getLongitude() != null){

            CharSequence latitudeString = "Latitude : "+numberFormat.format(Double.parseDouble(user.getLatitude()));
            CharSequence longitudeString = "Longitude : "+numberFormat.format(Double.parseDouble(user.getLongitude()));
            viewHolder.latitude.setText(latitudeString);
            viewHolder.longitude.setText(longitudeString);
        }


        return convertView;
    }

    @Nullable
    @Override
    public User getItem(int position) {
        return userArrayList.get(position);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

   public void removeAll (){
       this.userArrayList = new ArrayList<>();
   }
}
