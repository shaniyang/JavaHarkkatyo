package com.example.harjoitustyo2020;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Enroll {

    private int EnrollID;
    private int ReserveID;
    private int UserUUID;
    private List<Enroll> enrollsList;

    Enroll() {
        enrollsList = new ArrayList<>();
    }


    // ======= PUBLIC GETTERS =======

    public int getEnrollID() {return EnrollID;}
    public int getReserveID() {return ReserveID;}
    public int getUserUUID() {return UserUUID;}


    // ======= PUBLIC SETTERS =======

    public void setEnrollID(int newEnrollID) {EnrollID = newEnrollID;}
    public void setReserveID(int newReserveID) {ReserveID = newReserveID;}
    public void setUserUUID(int newUserID) {UserUUID = newUserID;}

    public void setEnrollsList(ArrayList<Enroll> enrolls) {enrollsList = enrolls;}


    // ======= PUBLIC OTHER METHODS =======

    public boolean addEnroll(Enroll enroll) {
        if (enroll != null) {
            enrollsList.add(enroll);
            return true;
        }
        return false;
    }


    public void logAllEnrolls(String TAG) {
        for (Enroll enroll : enrollsList) {
            Log.d(TAG, enroll.toString());
        }
    }


    // ======= PRIVATE METHODS =======

    private boolean doesEnrollExist(Enroll enroll) {
        for (Enroll enrl : enrollsList) {
            if (enrl.equals(enroll)) {
                return true;
            }
        }
        return false;
    }

}
