package com.example.harjoitustyo2020;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class edit_event_fragment extends Fragment {
    private Spinner event_spinner;
    private EditText set_sport;
    private EditText edit_duration;
    private EditText edit_max_participants;
    private Button delete_reservation_button;
    private Button saving_edits;
    private ArrayList spinner_list;
    private ArrayList spinner_id_list;
    private Button export_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_event, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        export_button = view.findViewById(R.id.export_button);
        event_spinner = view.findViewById(R.id.event_spinner);
        set_sport = view.findViewById(R.id.set_sport_field);
        edit_duration = view.findViewById(R.id.set_duration_field);
        edit_max_participants = view.findViewById(R.id.set_max_participants_field);
        delete_reservation_button = view.findViewById(R.id.delete_event_button);
        saving_edits = view.findViewById(R.id.create_new_event_button);
        delete_reservation_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_reservation();
            }
        });
        saving_edits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saving_changes();
            }
        });
        update_event_spinner();

        export_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {((MainActivity)getActivity()).exportCSV(); }
        });
    }

    private void delete_reservation(){ // Deletes reservation from database
        int pos = event_spinner.getSelectedItemPosition();
        String Uuid = (String) spinner_id_list.get(pos);
        SqlManager.SQLreservation.removeRow(Uuid);
        update_event_spinner();
    }


    private void saving_changes() { //Saves any changes made to event attributes and updates spinner
        int pos = event_spinner.getSelectedItemPosition();
        String sport = set_sport.getText().toString();
        String duration = edit_duration.getText().toString();
        String max_participants = edit_max_participants.getText().toString();
        String user_id = (String) spinner_id_list.get(pos);
        if (sport.length() > 0 ){
            sport = "'" +sport+ "'";
            SqlManager.SQLreservation.updateRow(user_id,"sport", sport);
            toast("Updated!");
        }
        if (duration.length() > 0){
            SqlManager.SQLreservation.updateRow(user_id,"duration", duration);
            toast("Updated!");
        }
        if (max_participants.length() > 0){
            SqlManager.SQLreservation.updateRow(user_id,"maxparticipants", max_participants);
            toast("Updated!");
        }
        update_event_spinner();
    }


    private void toast(String message){
        Context cxt = getActivity();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(cxt,text, duration);
        toast.show();
    }


    private void update_event_spinner() {
        spinner_list = (ArrayList) get_reservations();
        spinner_id_list = (ArrayList) get_reservations_id();
        if (spinner_list.size() ==0){
            spinner_list.add("Empty");
            spinner_id_list.add(0,0);}


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
               getView().getContext(),R.layout.support_simple_spinner_dropdown_item, spinner_list);
        event_spinner.setAdapter(adapter);
    }

    //get reservations and return them as a list
    public List<String> get_reservations(){ //Gets events from database and puts them to list for spinner
        List<String> reservations = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd kk:mm");
        int user_uuid;
        user_uuid = User.getCurrentUser().getUUID();
        for (Sporthall sporthall : ReservationManager.sporthallsList) {
            sporthall.updateReservationsFromSQL();
            for (Reservation reservation : sporthall.getReservations()) {
                if (reservation.getOwner().getUUID() == user_uuid) {
                    reservations.add(reservation.getUUID() +": "+reservation.getSporthall().getName() + ": " + format.format(reservation.getStartDate().getTime()) + ": " + reservation.getSport()+": "+reservation.getMaxParticipants());
                }
            }
        }
        return reservations;
    }

    //return corresponding id for reservations
    public List<String> get_reservations_id(){ //Gets events from database and puts ID's to list. saveChanges method uses on ID's to alter values
        List<String> reservations = new ArrayList<>();
        int user_uuid;
        user_uuid = User.getCurrentUser().getUUID();
        for (Sporthall sporthall : ReservationManager.sporthallsList) {
            sporthall.updateReservationsFromSQL();
            for (Reservation reservation : sporthall.getReservations()) {
                if (reservation.getOwner().getUUID() == user_uuid) {
                    reservations.add(Integer.toString(reservation.getUUID()));
                }
            }
        }
        return reservations;
    }
}