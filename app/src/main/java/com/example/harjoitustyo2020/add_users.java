package com.example.harjoitustyo2020;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_users extends Fragment {
    private Button create_new_user;
    private EditText username_field;
    private EditText firstname_field;
    private EditText lastname_field;
    private EditText email_field;
    private EditText phone_field;
    private EditText password_field;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_users, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        create_new_user = view.findViewById(R.id.create_new_user_button);
        username_field = view.findViewById(R.id.new_username_field);
        firstname_field = view.findViewById(R.id.new_firstname_field);
        lastname_field = view.findViewById(R.id.new_lastname_field);
        email_field = view.findViewById(R.id.new_email_field);
        phone_field = view.findViewById(R.id.new_phone_field);
        password_field = view.findViewById(R.id.new_password);

        create_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_new_user();}
        });
        
    }
    //method to create new user and saving it database
    public void create_new_user(){
        String username = username_field.getText().toString();
        String password = password_field.getText().toString();
        String firstname = firstname_field.getText().toString();
        String Surname = lastname_field.getText().toString(); 
        String phone = phone_field.getText().toString();
        String email = email_field.getText().toString();
        String university = "LUT";

        boolean compliant = password_manager.passwordIsCompliant(password);
        String hashed_password = password_manager.get_hashed_password(password, username);

        String[] userinfo = {"'"+username+"'", "'"+firstname+"'", "'"+Surname+"'", "'"+phone+"'", "'"+email+"'", "'"+hashed_password+"'", String.valueOf(0)};

        if (compliant) {
            for (User user : ReservationManager.usersList) {
                if (!username.equals(user.getUserName())) {
                    SqlManager.SQLuser.insertRow(userinfo);
                }
            }
            int user_uuid = 0;
            int uni_uuid = 0;
            user_uuid = SqlManager.getUserUUID(username);
            uni_uuid = SqlManager.getUniUUid(university);
            String Uni_uuid = Integer.toString(uni_uuid);
            String User_uuid = Integer.toString(user_uuid);
            SqlManager.SQLaccess.insertRow(User_uuid,Uni_uuid);
            System.out.println(username + password + hashed_password);
            clear_fields();
        } else {toast("Password is not compliant");}
    }
    private void toast(String input) {
        Context context = getActivity();
        CharSequence text = input;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    // clear fields for easy re-entry of data
    public void clear_fields(){
        username_field.setText("");
        firstname_field.setText("");
        lastname_field.setText("");
        phone_field.setText("");
        email_field.setText("");
        password_field.setText("");
    }


}
