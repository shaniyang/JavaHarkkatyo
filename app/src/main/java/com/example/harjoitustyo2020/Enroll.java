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

    // These are used to get enroll info to other classes
    public int getEnrollID() {return EnrollID;}
    public int getReserveID() {return ReserveID;}
    public int getUserUUID() {return UserUUID;}

    public void setEnrollID(int newEnrollID) {EnrollID = newEnrollID;}
    public void setReserveID(int newReserveID) {ReserveID = newReserveID;}
    public void setUserUUID(int newUserID) {UserUUID = newUserID;}

    public void setEnrollsList(ArrayList<Enroll> enrolls) {enrollsList = enrolls;}

    public boolean addEnroll(Enroll enroll) {
        if (enroll != null) {
            enrollsList.add(enroll);
            return true;
        }
        return false;
    }



}
