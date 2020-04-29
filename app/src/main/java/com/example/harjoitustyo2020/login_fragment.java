package com.example.harjoitustyo2020;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class login_fragment extends Fragment {
    private EditText user_name_field;
    private EditText password_field;
    private Button login_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Get values from edit fields
        user_name_field = view.findViewById(R.id.username_field);
        password_field = view.findViewById(R.id.password_field);
        login_button = view.findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_clicked();
            }
        });
    }

    private void login_clicked() {

        //((MainActivity)getActivity()).authenticate();
        //TODO REMOVE WHEN READY^^
        String user_name = user_name_field.getText().toString();
        String password = password_field.getText().toString();
        String hashedPassword = password_manager.get_hashed_password(password, user_name);
        try{
        User login_user = is_credintials_correct(user_name, hashedPassword);

        Log.d("LOGIN", "---------------");

        if (login_user != null) {
            Log.d("LOGIN", "Login accepted");
            User.setCurrentUser(login_user);

            ((MainActivity)getActivity()).authenticate();

        } else {
            Log.d("LOGIN", "Login denied");
        }

        Log.d("LOGIN", user_name + "-" + password);}
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private User is_credintials_correct(String userName, String passwordHash) {

        Log.d("LOGIN", "Hash 1: " + passwordHash);

        for (User user : ReservationManager.usersList) {
            if (user.getUserName().equals(userName)) {
                Log.d("LOGIN", "Found matching username: " + user.getUserName());
                Log.d("LOGIN", "Hash 2: " + user.getPasswordHash());
                if (user.getPasswordHash().equals(passwordHash)) {
                    return user;
                }
            }
        }
        return null;
    }
}

