package com.example.harjoitustyo2020;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class view_own_events_fragment extends Fragment {
    TextView scrollable_field;
    List<Enroll> enroll_list;
    List<Reservation> all_reservation_list;
    List<Reservation> reservation_to_enroll_list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_own_events, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        scrollable_field = getView().findViewById(R.id.enrolled_event_view);
        scrollable_field.setMovementMethod(new ScrollingMovementMethod());
        //printAllUsersEnrolls();
        print_all_user_enrolls();
    }



    public List<Reservation> get_all_reservations() {
        all_reservation_list = new ArrayList<>();

        for (Sporthall sporthall : ReservationManager.sporthallsList) {
            for (Reservation reservation : SqlManager.getReservationsFromDatabase(sporthall)) {
                all_reservation_list.add(reservation);
            }
        }
        return all_reservation_list;
    }


    public List<String> get_user_enrolls() {
        List<String> UsersEnrolls = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd' 'kk:mm");
        enroll_list = SqlManager.getEnrollsFromDatabase();
        reservation_to_enroll_list = get_all_reservations();
        for (Enroll enroll : enroll_list) {
            if (enroll.getUserUUID() == User.getCurrentUser().getUUID()) {
                for (Reservation reservation : reservation_to_enroll_list) {
                    if (enroll.getReserveID() == reservation.getUUID()) {
                        reservation.getAttenderList(reservation);
                        String an_enroll = reservation.getSporthall().getName() + ", " + format.format(reservation.getStartDate().getTime()) + ", " + reservation.getSport() + ", " + reservation.getAttenderAmount();
                        UsersEnrolls.add(an_enroll);
                    }
                }
            }
        }
        return UsersEnrolls;
    }

    public void print_all_user_enrolls() {
        scrollable_field.setText("");
        for (int i = 0; i< get_user_enrolls().size(); i++) {
            scrollable_field.append(get_user_enrolls().get(i) + "\n");
        }
    }
}

