package com.example.harjoitustyo2020;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class create_event_fragment extends Fragment {
    private Spinner sporthall_spinner;
    private EditText set_sportname;
    private Button setdate_button;
    private EditText set_start_time;
    private TextView selected_date;
    private EditText set_duration;
    private TextView event_date_time;
    private EditText set_max_participants_field;
    private Button new_event_button;
    private TextView is_timeslot_free_text;

    private Context cxt;
    private Sporthall chosen_sport_hall;

    private List<String> spinner_list;

    final Calendar show_start_calendar = Calendar.getInstance();
    Calendar show_end_calendar;

    SimpleDateFormat DMY_format;
    SimpleDateFormat full_format;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_event, container, false);
    }
    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        cxt = this.getContext();
        DMY_format = new SimpleDateFormat("yyyy.MM.dd");
        full_format = new SimpleDateFormat("yyyy.MM.dd  kk:mm");

        sporthall_spinner = getView().findViewById(R.id.spinner);
        set_sportname = view.findViewById(R.id.set_event_sport_field);
        setdate_button = view.findViewById(R.id.set_date_button);
        set_start_time = view.findViewById(R.id.start_time_field);
        selected_date = view.findViewById(R.id.start_view);
        set_duration = view.findViewById(R.id.set_duration_field);
        event_date_time = view.findViewById(R.id.end_view);
        set_max_participants_field = view.findViewById(R.id.set_max_participants_field);
        new_event_button = view.findViewById(R.id.create_new_event_button);
        is_timeslot_free_text = view.findViewById(R.id.is_available_view);


        sporthall_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updating_all(); // If sporthall spinner is touched all will update inside this fragment
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //asd
            }
        });

        final DatePickerDialog.OnDateSetListener date_datepicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                show_start_calendar.set(Calendar.YEAR, i);
                show_start_calendar.set(Calendar.MONTH, i1);
                show_start_calendar.set(Calendar.DAY_OF_MONTH, i2);
                updating_all();
            }
        };
        setdate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(cxt, date_datepicker, show_start_calendar.get(Calendar.YEAR),
                        show_start_calendar.get(Calendar.MONTH), show_start_calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        set_duration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updating_all();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updating_all();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updating_all();
            }
        });

        set_start_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updating_all();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updating_all();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updating_all();
            }
        });

        new_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_event();
                set_start_time.setText(set_start_time.getText());
            }
        });

        update_sporthall_spinner();
        updating_all();
        update_sporthall_spinner();
    }


    private void updating_all() {
        // Method that updates texts whenever it is called
        set_hours_to_dateformat();
        show_selected_date();
        set_whole_event();
        checkIf_reservation_possible(sporthall_spinner.getSelectedItemPosition());
        //updateSporthallSpinner();
    }


    // Checks if given time has no overlapping reservations
    // changes the color and content of the bottom text according to it
    private void checkIf_reservation_possible(int index) {
        chosen_sport_hall = get_sporthall_from_spinner(index);
        if (ReservationManager.isTimeSlotReserved(chosen_sport_hall, show_start_calendar, show_end_calendar)) {
            is_timeslot_free_text.setText("Time is available!");
            is_timeslot_free_text.setTextColor(Color.parseColor("#45f542")); // GREEN
            //Log.d("CREATE", "Reservation possible");
        }else if (chosen_sport_hall.getDisabled()){
            is_timeslot_free_text.setText("Hall is not available");
            is_timeslot_free_text.setTextColor(Color.parseColor("#f54242")); // RED
        }
        else {
            is_timeslot_free_text.setText("Time is taken!");
            is_timeslot_free_text.setTextColor(Color.parseColor("#f54242")); // RED
            //Log.d("CREATE", "Reservation NOT possible");
        }
    }


    // sets the hours and minutes to match the user input field
    private void set_hours_to_dateformat() {
        String clockText = set_start_time.getText().toString();
        if (!clockText.isEmpty()) {
            if (clockText.length() == 5) { // 5 characters
                String[] splitted = clockText.split(":");
                if (splitted.length == 2) {
                    int hours = Integer.parseInt(splitted[0]);
                    int minutes = Integer.parseInt(splitted[1]);
                    show_start_calendar.set(Calendar.HOUR_OF_DAY, hours);
                    show_start_calendar.set(Calendar.MINUTE, minutes);
                    Log.d("CREATE", "Time: " + hours + ":" + minutes);
                }
            }
        }
    }


    // When the create event button is pressed
    private void create_event() {
        if (checkif_info_correct()) { // Calls to check if all the necessary info is given
            if (chosen_sport_hall != null) { // Just to be sure check that the sporthall exists
                System.out.println("START TIME:   "+ show_start_calendar.after(Calendar.getInstance()));
                if (show_start_calendar.after(Calendar.getInstance())) { // Checks if the desired start date is after the current date
                    if (ReservationManager.isTimeSlotReserved(chosen_sport_hall, show_start_calendar, show_end_calendar)) { // Calls method for final check if timeslot is free
                        // TODO Add system to and reoccuring events ReservationManager.addNewWeeklyReservation()
                        String sportName = set_sportname.getText().toString();
                        int duration = Integer.parseInt(set_duration.getText().toString());
                        int maxPart = Integer.parseInt(set_max_participants_field.getText().toString());

                        // call to reservationmanager to add a new reservation
                        ReservationManager.addNewReservation(User.getCurrentUser(), chosen_sport_hall, sportName, show_start_calendar, duration, maxPart, 0);
                        Toast.makeText(getActivity(), "Reservation created!", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Toast.makeText(getActivity(), "Time is taken!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(getActivity(), "Illegal start time", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
        Toast.makeText(getActivity(), "Fill all the fields!", Toast.LENGTH_SHORT).show();
    }


    private boolean checkif_info_correct() { // checks if all the necessary info is given and properly
        if (!set_sportname.getText().toString().isEmpty()) {
            if (!set_max_participants_field.getText().toString().isEmpty()) {
                if (!set_duration.getText().toString().isEmpty()) {
                    if (Integer.parseInt(set_duration.getText().toString()) > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private void update_sporthall_spinner() {
        spinner_list = new ArrayList<>();
        for (Sporthall sporthall : ReservationManager.sporthallsList) {
            if (!sporthall.getDisabled()) {
                spinner_list.add(sporthall.getName());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getView().getContext(),R.layout.support_simple_spinner_dropdown_item, spinner_list);
        sporthall_spinner.setAdapter(adapter);
    }


    private Sporthall get_sporthall_from_spinner(int index) {

        for (Sporthall sporthall : ReservationManager.sporthallsList) {
            if (!sporthall.getDisabled()) {
                if (sporthall.getName().equalsIgnoreCase(spinner_list.get(index))) {
                    return sporthall;
                }
            }
        }
        return null;
    }


    private void show_selected_date() {
        selected_date.setText("Selected date: "+ DMY_format.format(show_start_calendar.getTime()));
    }


    private void set_whole_event() {
        show_end_calendar = (Calendar) show_start_calendar.clone();
        String innerText = set_duration.getText().toString();
        if (!innerText.isEmpty()) {
            int duration = Integer.parseInt(innerText);
            show_end_calendar.add(Calendar.HOUR_OF_DAY, duration);
        }
        event_date_time.setText("Your event starts at "+ full_format.format(show_end_calendar.getTime()));
    }
}
