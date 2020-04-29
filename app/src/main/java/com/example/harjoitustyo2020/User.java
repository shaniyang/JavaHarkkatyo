package com.example.harjoitustyo2020;

public class User {

    private static User currentlyLoggedin;

    private int UUID;                       // User's UUID
    private String userName;                // Username
    private String firstName;
    private String surName;
    private String email;
    private String phoneNum;
    private String passwordHash;
    private boolean admin;
    private String uniName;// Is the user admin (True = yes, False = no)

    // LIST OF USERS IS IN RESERVATION MANAGER!

    User() {
        // Empty
    }


    public static User getCurrentUser() {return currentlyLoggedin;}
    public static void setCurrentUser(User newUser) {currentlyLoggedin = newUser;}


    // ======= PUBLIC GETTERS =======

    public int getUUID() {return UUID;}
    public String getUserName() {return userName;}
    public String getFirstName() {return firstName;}
    public String getSurName() {return surName;}
    public String getEmail() {return email;}
    public String getPhoneNum() {return phoneNum;}
    public String getPasswordHash() {return passwordHash;}
    public boolean isAdmin() {return admin;}
    public String getUniName() {return uniName;}


    // ======= PUBLIC SETTERS =======

    public void setUUID(int newUUID) {UUID = newUUID;}
    public void setUserName(String newName) {userName = newName;}
    public void setFirstName(String newName) {firstName = newName;}
    public void setSurName(String newName) {surName = newName;}
    public void setEmail(String newEmail) {email = newEmail;}
    public void setPhoneNum(String phone) {phoneNum = phone;}
    public void setPasswordHash(String pwdHash) {passwordHash = pwdHash;}
    public void setAdminPrivilege(boolean isAdmin) {admin = isAdmin;}
    public void setUniName(String newUniName) {uniName = newUniName;}


    // ======= PUBLIC OTHER METHODS =======

    // USED ONLY FOR DEBUGGIN PURPOSES
    public String toString() {
        return (UUID + ", " + userName + ", " + firstName + " " + surName + ", " + email + ", " + admin + ", " + uniName);
    }
}
