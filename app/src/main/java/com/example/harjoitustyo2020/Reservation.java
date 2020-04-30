package com.example.harjoitustyo2020;


import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Reservation {
    private int UUID;                       // Reservation's UUID
    //private String title;                   // Title of the reservation
    private Sporthall sporthall;            // The sporthall that is being reserved
    private String sport;             // Describtion of the reservation
    private User owner;                     // owner user of the reservation
    private int maxParticipants;
    private Calendar startCalendar;             // Date at which the reservation starts
    private Calendar endCalendar;               // Date at which the reservation starts
    private List<User> attenderList;   // List of users attending the reservation


    Reservation() {

        attenderList = new ArrayList<>();

    }

    int getUUID() {return UUID;}
    String getSport() {return sport;}
    User getOwner() {return owner;}
    int getMaxParticipants() {return maxParticipants;}
    Calendar getStartDate() {return startCalendar;}
    Calendar getEndDate() {return endCalendar;}
    Sporthall getSporthall() {return sporthall;}

    List<User> getAttenderList(Reservation reservation) {
        attenderList = new ArrayList<>();
        int reserveid = reservation.getUUID();
        //System.out.println(reserveid);
        for (Enroll enroll : SqlManager.getEnrollsFromDatabase()) {
            if (enroll.getReserveID() == reserveid) {
                for (User user : SqlManager.getUsersFromDatabase()) {
                    if (enroll.getUserUUID() == user.getUUID()) {
                        attenderList.add(user);
                    }
                }
            }
        }
        return attenderList;}

    int getAttenderAmount() {
        return attenderList.size();
    }

    public List<User> getAttendersOfThis() {
        return attenderList;
    }


    void setUUID(int newID) {UUID = newID;}
    void setParent(Sporthall parent) {
        sporthall = parent;
    }
    void setSport(String text) {sport = text;}
    void setOwner(int ownerID) {

        for (User user : ReservationManager.usersList) {
            if (ownerID == user.getUUID()) {
                owner = user;
                return;
            }
        }
        Log.e("RESERVATION", "no such owner can be found");
    }
    void setMaxParticipants(int max) {maxParticipants = max;}
    void setStartCalendar(Calendar calend) {startCalendar = calend;}


    public void setEndFromStartDur(Calendar startDate, int duration) {
        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.HOUR_OF_DAY, duration);
        setEndDate(endDate);
    }


    public boolean hasUserAsAttendant(User user) {
        return isUserAttender(user);
    }


    // USED ONLY FOR DEBUGGIN PURPOSES
    public String toString() {
        return (UUID + " " + sport + " " + getAttenderAmount());
    }



    private boolean setStartDate(Calendar newStartDate) {
        if (newStartDate != null) {
            startCalendar = newStartDate;
            return true;
        }
        return false;
    }


    private boolean setEndDate(Calendar newEndDate) {
        if (newEndDate != null) {
            endCalendar = newEndDate;
            return true;
        }
        return false;
    }


    // Checks the attenderList for given User
    private boolean isUserAttender(User testUser) {
        if (testUser != null) {
            for (User user : attenderList) {
                if (user.equals(testUser)) {
                    return true;
                }
            }
        }
        return false;
    }
}
