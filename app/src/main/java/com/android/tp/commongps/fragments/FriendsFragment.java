package com.android.tp.commongps.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;


import com.android.tp.commongps.R;
import com.android.tp.commongps.adapter.FriendsFragmentListAdapter;
import com.android.tp.commongps.core.CoreApplication;

import java.util.ArrayList;


public class FriendsFragment extends Fragment {
    ExpandableListView listView;

    public static Fragment newInstance() {
        FriendsFragment friendsFragment = new FriendsFragment();

        Bundle myBundle = new Bundle();
        //myBundle.putInt(somedata);
        //myMapFragment.setArguments(myBundle);

        return friendsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        // work with arguments
//        if (arguments != null) {
//            data = arguments.getData(DATA_KEY);
//        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.friends_fragment, container, false);

        listView = (ExpandableListView) view.findViewById(R.id.friendslist);
        listView.setAdapter(new FriendsFragmentListAdapter(getContext(), CoreApplication.getFriendsList()));
        return view;
    }
}