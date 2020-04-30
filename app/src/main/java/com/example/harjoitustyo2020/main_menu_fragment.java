package com.example.harjoitustyo2020;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class main_menu_fragment extends Fragment {
    private TextView user_profile_name;
    private Button create_new_user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_menu, container, false);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        user_profile_name = view.findViewById(R.id.username_view);
        create_new_user = view.findViewById(R.id.create_new_user_button);
        set_user_profilename();
        ImageView profile_image = view.findViewById(R.id.userprofile_pic);


    }

    // set current username to textview
    private void set_user_profilename() {
        user_profile_name.setText("Hello user: "+User.getCurrentUser().getUserName());
    }

}

