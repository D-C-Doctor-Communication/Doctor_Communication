package com.dc.doctor_communication.MedicalChart;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doctorcommunication.R;

import java.util.ArrayList;

public class MCListViewAdapter extends BaseAdapter {
    private ImageView icon;
    private TextView titleText;
    private TextView location;
    private TextView time;

    private ArrayList<MCListViewItem> listViewItemList = new ArrayList<>();


    public MCListViewAdapter(){

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mc_listview_item,parent,false);
        }

        icon = convertView.findViewById(R.id.icon);
        titleText = convertView.findViewById(R.id.titleText);
        location = convertView.findViewById(R.id.location);
        time = convertView.findViewById(R.id.time);

        MCListViewItem listViewItem = listViewItemList.get(position);

        icon.setImageResource(listViewItem.getIcon());
        titleText.setText(listViewItem.getTitleText());
        location.setText(listViewItem.getLocation());
        time.setText(listViewItem.getTime());

        return convertView;
    }


    public void clearData() {
        listViewItemList.clear();
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(int icon, String title,String location,String time){
        MCListViewItem item = new MCListViewItem();

        item.setIcon(icon);
        item.setTitleText(title);
        item.setLocation(location);
        item.setTime(time);

        listViewItemList.add(item);
    }

}