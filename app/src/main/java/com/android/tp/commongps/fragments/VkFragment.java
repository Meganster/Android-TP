package com.android.tp.commongps.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.tp.commongps.R;


public class VkFragment extends Fragment {
    public static Fragment newInstance() {
        VkFragment vkFragment = new VkFragment();

        Bundle myBundle = new Bundle();
        //myBundle.putInt(somedata);
        //vkFragment.setArguments(myBundle);

        return vkFragment;
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
        final View view = inflater.inflate(R.layout.vk_fragment, container, false);
        return view;
    }
}