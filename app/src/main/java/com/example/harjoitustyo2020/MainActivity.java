package com.example.harjoitustyo2020;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ReservationManager reservation_manager;
    private ArrayList reservations_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (database_exists()) {
            Log.d("FILE", "ON OLEMASSA!");
            new SqlManager(this);
        } else {
            Log.d("FILE", "EI OLE OLEMASSA");
            new SqlManager(this); // This must be after the existance of the file is checked
            SqlManager.presetDatabaseValues();
        }
        object_initalization_test();
        login();
    }
    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (f instanceof login_fragment) {
            Log.d("ONBACK", "Login fragment it is");
            finish();
        } else if (f instanceof authentication_fragment) {
            Log.d("ONBACK", "Authenticator it is");
            finish();
        } else if (f instanceof main_menu_fragment) {
            Log.d("ONBACK", "Main menu it is");
            finish();
        } else {
            Log.d("ONBACK", "popBackStack");
            getSupportFragmentManager().popBackStackImmediate();
        }
    }


    public void popStacks() {
        Log.d("ONBACK", "popBackStack on command");
        getSupportFragmentManager().popBackStackImmediate();
    }


    public void logOut(View v){
        User.setCurrentUser(null);
        login();
    }

    public boolean database_exists() {
        String path = this.getFilesDir().getPath();
        path = path.substring(0, 28);
        path += "/databases";
        path += "/sporthallmanager.db";
        File file = new File(path);
        Log.d("FILE", path);
        return file.exists();
    }

    public void object_initalization_test() { // TODO DELETE ONCE TEST OVER
        reservation_manager = new ReservationManager();
        reservation_manager.logAllUsers("OBJECT");
        reservation_manager.logAllSporthalls("OBJECT");
        reservation_manager.logAllReservations("OBJECT");
    }


    public void login() {
        login_fragment login = new login_fragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, login);
        fragmentTransaction.commit();
    }

    public void authenticate() {
        authentication_fragment authentication = new authentication_fragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, authentication);
        fragmentTransaction.commit();
    }

    public void main_menu() {
        main_menu_fragment main_menu = new main_menu_fragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, main_menu);
        fragmentTransaction.commit();
    }

    public void user_profile(View v) {
        user_profile_fragment user_profile = new user_profile_fragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, user_profile);
        fragmentTransaction.commit();
    }

    public void create_event(View v) {
        create_event_fragment create_event_fragment = new create_event_fragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, create_event_fragment);
        fragmentTransaction.commit();
    }

    public void edit_event(View v) {
        edit_event_fragment edit_event_fragment = new edit_event_fragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, edit_event_fragment);
        fragmentTransaction.commit();
    }

    public void enroll(View V) {
        enroll_fragment enroll_fragment = new enroll_fragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, enroll_fragment);
        fragmentTransaction.commit();
    }

    public void view_events(View V) {
        view_own_events_fragment view_own_events_fragment = new view_own_events_fragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, view_own_events_fragment);
        fragmentTransaction.commit();
    }
    public void create_user(View v) {
        if (User.getCurrentUser().isAdmin()){
        add_users add_users = new add_users();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, add_users);
        fragmentTransaction.commit();}
    }

    public void to_menu(View v) {
        popStacks();
        main_menu();
    }

    public void exportCSV() {
        reservations_list = (ArrayList) get_reservations();
        FileOutputStream fos;
        String completestr = "";
        String s;
        try {
            fos = openFileOutput("reservationslist.txt", MODE_PRIVATE);
            for (Object str : reservations_list) {
                String str2 = str.toString();
                String[] str3 = str2.split(" ");
                for (int i = 0; i < str3.length; i++ ){
                    s = str3[i].replace(':',';');
                    completestr += s;
                }
                completestr += "\n";
                fos.write(completestr.getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public List<String> get_reservations(){ //Gets events from database and puts them to list for spinner
        java.util.List<java.lang.String> reservations = new ArrayList<>();
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
}