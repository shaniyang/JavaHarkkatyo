package com.example.harjoitustyo2020;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class password_manager {

    password_manager() {}


    public static String get_hashed_password(String password, String userName) {
        if (password != null && userName != null) {
            return get_SHA_512_SecurePassword(password, userName);
        }
        return null;
    }


    public static String auth_numbers() { // It will generate 6 digit random Number.
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }


    // Checks if authentication code is correct
    public boolean authenticated(View view){
        String auth_numbers = auth_numbers();
        TextView authcode = view.findViewById(R.id.auth_code_view);
        authcode.setText("Your authentication code is "+auth_numbers);
        EditText user_input = view.findViewById(R.id.auth_code_field);
        String input = user_input.getText().toString();
        if (auth_numbers == input){
            return true;
        } else;
        return false;
    }

    // password meet requirements
    public static boolean passwordIsCompliant(String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        boolean lengthFlag = false;

        if (str.length() > 12){
            lengthFlag = true;
        }

        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag && lengthFlag)
                return true;
        }
        return false;
    }

    //hash password with SHA-512
    private static String get_SHA_512_SecurePassword(String passwordToHash, String salt){
        String generated_password = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generated_password = sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return generated_password;
    }
}
