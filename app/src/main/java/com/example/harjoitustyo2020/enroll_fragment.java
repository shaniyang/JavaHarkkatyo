package com.example.harjoitustyo2020;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class enroll_fragment extends Fragment {
    private EditText edit_date;
    private Spinner sportevents_spinner;
    private Button join_button;
    private Button cancel_button;
    private Button search_button;
    private TextView succesfully_joined;
    private String set_date = "";
    SimpleDateFormat format;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.enroll, container, false);
    }


    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        search_button = view.findViewById(R.id.search_button);
        edit_date = view.findViewById(R.id.set_date_field);
        sportevents_spinner = view.findViewById(R.id.event_spinner);
        succesfully_joined = view.findViewById(R.id.join_notification_view);
        join_button = view.findViewById(R.id.enroll_button);
        cancel_button = view.findViewById(R.id.main_menu_button);
        format = new SimpleDateFormat("yyyy.MM.dd kk:mm");
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                join_event();
            }
        });

        update_sportevent_spinner();
        sportevents_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {}

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_date = edit_date.getText().toString();
                update_sportevent_spinner();
            }
        });

    }


    public void join_event() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd' 'kk:mm");
        String spinner = sportevents_spinner.getSelectedItem().toString();
        String Attend = "No";
        int reservID = 0;
        for (Reservation reservation : get_event_from_list()) {
            String reservationData = reservation.getSport() + "  " + format.format(reservation.getStartDate().getTime());
            if (spinner.equals(reservationData)) {
                reservID = reservation.getUUID();
                for (User user : reservation.getAttenderList(reservation)) {
                    if (user.getUUID() == User.getCurrentUser().getUUID()) {
                        Attend = "Yes";
                        update_sportevent_spinner();
                        break;
                    }
                }
                break;
            }
        }

        if (Attend.equals("No")) {
            SqlManager.SQLenrolls.insertRow(Integer.toString(User.getCurrentUser().getUUID()), Integer.toString(reservID));
            succesfully_joined.setText("Successfully joined to event!");

        }else {toast();}

        update_sportevent_spinner();
    }


    private void toast(){ //pop up message
        Context context = getActivity();
        CharSequence text = "You are already in this event";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context,text, duration);
        toast.show();
    }


    private List<Reservation> get_event_from_list() {
        List<Reservation> reservations = new ArrayList<>();
        for (Sporthall sporthall : ReservationManager.sporthallsList) {
            if (!sporthall.getDisabled()) {
                for (Reservation reservation : sporthall.getReservations()) {
                    reservations.add(reservation);
                }
            }
        }
        return reservations;
    }

    // go through list and show only corresponding events
    private void update_sportevent_spinner() {

        List<String> reservationStrings = new ArrayList<>();
        for (Sporthall sporthall : ReservationManager.sporthallsList) {
            if (!sporthall.getDisabled()) {
                sporthall.updateReservationsFromSQL();
                for (Reservation reservation : sporthall.getReservations()) {
                    String date = format.format(reservation.getStartDate().getTime());
                    String[] splitted = date.split(" ");
                    if ((splitted[0]).equals(set_date)){
                        String text = reservation.getSport();
                        text += "  " + format.format(reservation.getStartDate().getTime());
                        reservationStrings.add(text);}
                    else if (set_date.isEmpty()){
                        String text = reservation.getSport();
                        text += "  " + format.format(reservation.getStartDate().getTime());
                        reservationStrings.add(text);}
                    }
                }
            }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getView().getContext(),R.layout.support_simple_spinner_dropdown_item,reservationStrings);
        sportevents_spinner.setAdapter(adapter);
    }
}

