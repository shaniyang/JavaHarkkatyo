package com.example.harjoitustyo2020;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class user_profile_fragment extends Fragment {
    private EditText username_field;
    private Button saveButton;
    private EditText phone_number_field;
    private EditText email_field;
    private TextView displayUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.user_profile, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        displayUser = view.findViewById(R.id.user_name_view);
        saveButton = view.findViewById(R.id.create_new_event_button);
        username_field = view.findViewById(R.id.username_field);
        phone_number_field = view.findViewById(R.id.user_phone_field);
        email_field = view.findViewById(R.id.user_email_field);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked();
            }
        });
        displayUser.setText("Hello user: "+User.getCurrentUser().getUserName());

    }


    private void toast(String message){
        Context context = getActivity();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context,text, duration);
        toast.show();
    }


    private void buttonClicked() {
        String name = "'" + username_field.getText().toString() + "'";
        String phone = "'" + phone_number_field.getText().toString() + "'";
        String email = "'" + email_field.getText().toString() + "'";
        System.out.println(name.length());
        if (name.length() > 0 ){
            SqlManager.SQLuser.updateRow(Integer.toString(User.getCurrentUser().getUUID()), SqlTablenames.userTable.COLUMN_NAME_FIRSTNAME, name);
            toast("Updated!");
        }
        if (phone.length()>0){ // if field contains data, it is saved to database
            SqlManager.SQLuser.updateRow(Integer.toString(User.getCurrentUser().getUUID()), SqlTablenames.userTable.COLUMN_NAME_PHONE_NUMBER, phone);
            toast("Updated!");
        }
        if (email.length()>0 ){ // if field contains data, it is saved to database
            SqlManager.SQLuser.updateRow(Integer.toString(User.getCurrentUser().getUUID()), SqlTablenames.userTable.COLUMN_NAME_EMAIL, email);
            toast("Updated!");
        }
        if (phone.length() == 0 && email.length() == 0){toast("Input data please.");}
    }

}

