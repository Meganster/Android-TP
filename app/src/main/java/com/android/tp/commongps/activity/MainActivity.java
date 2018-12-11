package com.android.tp.commongps.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.tp.commongps.R;
import com.android.tp.commongps.core.FirebaseManager;
import com.android.tp.commongps.fragments.FriendsFragment;
import com.android.tp.commongps.fragments.MyMapFragment;

public class MainActivity extends AppCompatActivity {
    private int currentMenuId = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    if(currentMenuId != R.id.navigation_map) {
                        renderMap();
                    }
                    return true;
                case R.id.navigation_friends:
                    if(currentMenuId != R.id.navigation_friends) {
                        renderMap();
                    }
                    renderFriendsList();
                    return true;
                case R.id.navigation_settings:
                    if(currentMenuId != R.id.navigation_settings) {

                        // stub
                        if(FirebaseManager.getInstance().isAuthenticated()) {
                            Toast.makeText(getApplicationContext(), "Authentication success, uid = " + FirebaseManager.getInstance().getUID(),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        openVkActivity();

        setContentView(R.layout.activity_main);
        FirebaseManager.getInstance();
        renderMap();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void openVkActivity() {
        Intent vkLoginIntent = new Intent(this, VkLoginActivity.class);
        startActivity(vkLoginIntent);
    }

    public void renderMap() {
        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentcontainer, MyMapFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();

        currentMenuId = R.id.navigation_map;
    }

    public void renderFriendsList() {
        final FragmentManager manager = getSupportFragmentManager();
        final FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentcontainer, FriendsFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();

        currentMenuId = R.id.navigation_friends;
    }
}
