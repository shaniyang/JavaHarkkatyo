package com.example.harjoitustyo2020;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class authentication_fragment extends Fragment {
    private EditText auth_code_field;
    private TextView authentication_code;
    private Button confirm_button;
    private String auth_code;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.authentication, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        auth_code_field = view.findViewById(R.id.auth_code_field);
        authentication_code = view.findViewById(R.id.auth_code_view);
        confirm_button = view.findViewById(R.id.authenticate_button);
        set_authentication_code();
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_clicked();
            }
        });
    }

    //authenticate button click
    private void button_clicked() {

        if (check_authentication(auth_code_field.getText().toString())) {
            System.out.println("Authentication accepted");

            ((MainActivity)getActivity()).main_menu();
        } else {
            System.out.println("Authentication denied");
        }
    }

    //set random authentication code to text box for user
    private void set_authentication_code() {
        Random rnd = new Random();
        String authText = "";

        for (int i = 0; i < 6; i++) {
            authText += Integer.toString(rnd.nextInt(9));
        }
        auth_code = authText;
        authentication_code.setText("Your authentication code is "+ auth_code);
    }

    // check if authentication code was correctly typed
    private boolean check_authentication(String input) {
        return input.equals(auth_code);
    }
}

