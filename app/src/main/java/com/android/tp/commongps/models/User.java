package com.android.tp.commongps.models;

import java.util.List;

public class User {
    private Integer id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Double accuracy;
    private Long lastSeenDate;
    private List<Integer> friendsIds;

    public User() { } // Required for serialization

    public User(int id, String name, Double latitude, Double Longitude, Double accuracy, List<Integer> friendsIds) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = Longitude;
        this.accuracy = accuracy;
        this.lastSeenDate = System.currentTimeMillis();
        this.friendsIds = friendsIds;
    }

    public User(Integer id, String name, List<Integer> friendsIds) {
        this.id = id;
        this.name = name;
        this.friendsIds = friendsIds;
        this.lastSeenDate = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public Long getLastSeenDate() {
        return lastSeenDate;
    }

    public List<Integer> getFriendsIds() {
        return friendsIds;
    }

    public void setPosition(Double latitude, Double longitude, Double accuracy) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
    }
}
