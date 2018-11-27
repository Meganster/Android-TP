package com.android.tp.commongps.core;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.tp.commongps.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class FirebaseManager {
    private static FirebaseManager instance;
    private boolean isAuthenticated;
    private String uid = "";
    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;

    public static FirebaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseManager();
        }

        return instance;
    }

    private FirebaseManager() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("FIREBASE", "signInAnonymously:success");

                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            if (currentUser != null) {
                                uid = currentUser.getUid();
                            }

                            isAuthenticated = true;
                            rootRef = FirebaseDatabase.getInstance("https://tensile-verve-223518.firebaseio.com").getReference();
                        } else {
                            Log.w("FIREBASE", "signInAnonymously:failure", task.getException());
                            isAuthenticated = false;
                        }
                    }
                });
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public String getUID() {
        return uid;
    }

    public boolean initUser(User user) {
        try {
            Map<String, Object> newFriendMap = new HashMap<>();
            newFriendMap.put("name", user.getName());
            newFriendMap.put("latitude", user.getLatitude());
            newFriendMap.put("longitude", user.getLongitude());
            newFriendMap.put("accuracy", user.getAccuracy());
            newFriendMap.put("lastonline", user.getLastSeenDate());
            newFriendMap.put("listfriends", user.getFriendsIds());

            DatabaseReference newNote = rootRef.child("users").child(String.valueOf(user.getId()));
            //newNote.setValue(newFriendMap);
            newNote.updateChildren(newFriendMap);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean setPosition(User user) {
        try {
            Map<String, Object> newPositionMap = new HashMap<>();
            newPositionMap.put("latitude", user.getLatitude());
            newPositionMap.put("longitude", user.getLongitude());
            newPositionMap.put("accuracy", user.getAccuracy());

            DatabaseReference newPosition = rootRef.child("users").child(String.valueOf(user.getId()));
            newPosition.updateChildren(newPositionMap);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean setLastOnline(User user) {
        try {
            Map<String, Object> newLastOnlineMap = new HashMap<>();
            newLastOnlineMap.put("lastonline", user.getLastSeenDate());

            DatabaseReference newPosition = rootRef.child("users").child(String.valueOf(user.getId()));
            newPosition.updateChildren(newLastOnlineMap);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }
}
