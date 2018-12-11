package com.android.tp.commongps.core;

import android.app.Application;

import com.android.tp.commongps.activity.MainActivity;
import com.android.tp.commongps.fragments.MyMapFragment;
import com.android.tp.commongps.models.Friend;
import com.android.tp.commongps.models.User;
import com.vk.sdk.VKSdk;

import java.util.ArrayList;

public class CoreApplication extends Application {
    //private static MapManager mapManager = new MapManager(MainActivity, MyMapFragment.newInstance());
    private static User client;
    private static ArrayList<Friend> friendsList = new ArrayList();

    public static void setClient(User user) {
        client = user;
    }

    public static User getClient() {
        return client;
    }

    public static ArrayList<Friend> getFriendsList() {
        return friendsList;
    }

    public static void setFriendsList(ArrayList<Friend> friendsList) {
        CoreApplication.friendsList = friendsList;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);
    }
}
