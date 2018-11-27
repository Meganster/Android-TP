package com.android.tp.commongps.models;

import java.util.Date;

public class Friend {
    private Integer id;
    private String firstName;
    private String lastName;
    private boolean online;
    private boolean hasLastSeen;
    private Date lastSeen;

    public Friend() { } // Required for serialization

    public Friend(Integer id, String firstName, String lastName, Integer online, Date lastSeen) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.online = online > 0;
        this.lastSeen = lastSeen;
        this.hasLastSeen = true;
    }

    public Friend(Integer id, String firstName, String lastName, Integer online) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.online = online > 0;
        this.lastSeen = null;
        this.hasLastSeen = false;
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    public boolean isHasLastSeen() {
        return hasLastSeen;
    }

    public void setHasLastSeen(boolean hasLastSeen) {
        this.hasLastSeen = hasLastSeen;
    }
}
