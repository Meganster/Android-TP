package com.android.tp.commongps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tp.commongps.R;
import com.android.tp.commongps.models.Friend;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FriendsFragmentListAdapter extends BaseExpandableListAdapter {
    private ArrayList<Friend> friendsList;
    private Context context;
    private DateFormat dateFormat;

    public FriendsFragmentListAdapter(Context context, ArrayList<Friend> friendsList){
        this.context = context;
        this.friendsList = friendsList;
        this.dateFormat = new SimpleDateFormat("HH:mm");

        Collections.sort(friendsList, new Comparator<Friend>() {
            @Override
            public int compare(Friend lFriend, Friend rFriend) {
                String leftFullName = lFriend.getFirstName() + " " + lFriend.getLastName();
                String rightFullName = rFriend.getFirstName() + " " + rFriend.getLastName();
                return leftFullName.compareTo(rightFullName);
            }
        });
    }

    @Override
    public int getGroupCount() {
        return friendsList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return friendsList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return friendsList.get(groupPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_view, null);
        }

        if (isExpanded){
            //Изменяем что-нибудь, если текущая Group раскрыта
        }
        else{
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.textGroup);
        String FIO = friendsList.get(groupPosition).getFirstName() + " " + friendsList.get(groupPosition).getLastName();
        textGroup.setText(FIO);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_view, null);
        }

        TextView textChild = (TextView) convertView.findViewById(R.id.textChild);

        String textChildStr = "";
        if(friendsList.get(groupPosition).isOnline()) {
            textChildStr = "Online";
        } else {
            if(friendsList.get(groupPosition).isHasLastSeen()) {
                textChildStr = "Last seen " + dateFormat.format(friendsList.get(groupPosition).getLastSeen());
            } else {
                textChildStr = "Last seen long time ago";
            }
        }

        textChild.setText(textChildStr);

        Button button = (Button)convertView.findViewById(R.id.buttonChild);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Button is pressed",Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}