package com.dc.doctor_communication.DoctorMeeting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.dc.doctor_communication.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseExpandableListAdapter {

    ArrayList<ParentData> groupDatas;
    ArrayList<ArrayList<ContentData>> childDatas;
    LayoutInflater inflater;
    Context context;

    public CustomAdapter(Context context, ArrayList<ParentData> parentDatas,ArrayList<ArrayList<ContentData>> childDatas){
        Log.d("myapp","CustomAdapter 어댑터 생성됨");
        this.groupDatas = parentDatas;
        this.childDatas = childDatas;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return groupDatas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childDatas.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupDatas.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childDatas.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //부모 포함되는 내용 : 날짜,파트,통증정도
        Log.d("myapp","getGroupView 들어옴");
        if(convertView==null){
            convertView = inflater.inflate(R.layout.dc_list_group, parent, false);
        }
        TextView group_date = convertView.findViewById(R.id.group_date);
        TextView group_title = convertView.findViewById(R.id.group_title);

        group_date.setText(groupDatas.get(groupPosition).getDate());
        group_title.setText(groupDatas.get(groupPosition).getPart()+", "+groupDatas.get(groupPosition).getPain_level());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Log.d("myapp","getChildView 들어옴");
        if(convertView == null){
            convertView = inflater.inflate(R.layout.dc_list_item,parent, false);
        }
        TextView list_part = convertView.findViewById(R.id.list_part);
        TextView list_painLevel = convertView.findViewById(R.id.list_painLevel);
        TextView list_characteristics = convertView.findViewById(R.id.list_characteristics);
        TextView list_situation = convertView.findViewById(R.id.list_situation);
        TextView list_accompany_pain = convertView.findViewById(R.id.list_accompany_pain);
        TextView list_additional = convertView.findViewById(R.id.list_additional);

        list_part.setText(childDatas.get(groupPosition).get(childPosition).getPart());
        list_painLevel.setText(childDatas.get(groupPosition).get(childPosition).getPain_level());
        list_characteristics.setText(childDatas.get(groupPosition).get(childPosition).getCharacteristics());
        list_situation.setText(childDatas.get(groupPosition).get(childPosition).getSituation());
        list_accompany_pain.setText(childDatas.get(groupPosition).get(childPosition).getAccompany_symp());
        list_additional.setText(childDatas.get(groupPosition).get(childPosition).getAdditional());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}