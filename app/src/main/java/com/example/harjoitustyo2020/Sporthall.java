package com.example.harjoitustyo2020;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Sporthall {
    private int UUID;                       // Sporthall's own UUID
    private String name;                    // Name
    //private int maximumCapacity;            // Maximum capacity
    private String type;
    private boolean disabled;               // If sporthall is disabled for repair etc.
    private String universityName;          // Name of the university the sporthall is located in
    private List<Reservation> reservationsList; // List of all the reservations for this sporthall

    Sporthall() {
        reservationsList = new ArrayList<>();
    }

    public int getUUID() {return UUID;}
    public String getName() {return name;}
    public String getType() {return type;}
    public boolean getDisabled() {return disabled;}
    public String getUniversityName() {return universityName;}
    public int getReservationAmount() {return reservationsList.size();}
    public List<Reservation> getReservations() {return reservationsList;}



    public void setUUID(int ID) {UUID = ID;}
    public void setName(String nam) {name = nam;}
    public void setType(String newType) {type = newType;}
    public void setDisabled(boolean isDisabled) {disabled = isDisabled;}
    public void setUniversityName(String UNIname) {universityName = UNIname;}
    public void setReservationsList(ArrayList<Reservation> resevations) {reservationsList = resevations;}


    public void updateReservationsFromSQL() {
        reservationsList = SqlManager.getReservationsFromDatabase(this);
    }


    public boolean addReservation(Reservation reservation) {
        if (reservation != null) {
            reservationsList.add(reservation);
            return true;
        }
        return false;
    }


    public void logAllReservations(String TAG) {
        for (Reservation reservation : reservationsList) {
            Log.d(TAG, reservation.toString());
        }
    }


    // USED ONLY FOR DEBUGGIN PURPOSES
    public String toString() {
        return (UUID + " " + name + " " + type + " " + universityName + " " + disabled);
    }

    private boolean doesReservationExist(Reservation reservation) {
        for (Reservation reserv : reservationsList) {
            if (reserv.equals(reservation)) {
                return true;
            }
        }
        return false;
    }
}
