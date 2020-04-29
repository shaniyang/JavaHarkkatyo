package com.example.harjoitustyo2020;

import android.provider.BaseColumns;


public final class SqlTablenames {
    // To prevent someone from accidentally instantiating the class,
    // make the constructor private.
    private SqlTablenames() {}

    // Inner class that defines the table contents
    public static class userTable implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_USER_UUID = "user_uuid";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_FIRSTNAME = "firstname";
        public static final String COLUMN_NAME_SURNAME = "surname";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PHONE_NUMBER = "phone_number";
        public static final String COLUMN_NAME_PWD_HASH = "pwd_hash";
        public static final String COLUMN_NAME_ADMINISTRATOR = "administrator";
    }


    public static class sporthallTable implements BaseColumns {
        public static final String TABLE_NAME = "sporthall";
        public static final String COLUMN_NAME_HALLID = "hallid";
        public static final String COLUMN_NAME_HALLNAME = "hallname";
        public static final String COLUMN_NAME_UNI_UUID = "uni_uuid";
        public static final String COLUMN_NAME_HALLTYPE = "halltype";
        public static final String COLUMN_NAME_NOT_AVAILABLE = "not_available";
    }


    public static class reservationsTable implements BaseColumns {
        public static final String TABLE_NAME = "reservations";
        public static final String COLUMN_NAME_RESERVEID = "reserveid";
        public static final String COLUMN_NAME_HALLID = "hallid";
        public static final String COLUMN_NAME_SPORT = "sport";
        public static final String COLUMN_NAME_START_TIME = "start_time";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_USER_UUID = "user_uuid";
        public static final String COLUMN_NAME_MAXPARTICIPANTS = "maxparticipants";
        public static final String COLUMN_NAME_RECURRING_EVENT = "recurring_event";
    }


    public static class enrollsTable implements BaseColumns {
        public static final String TABLE_NAME = "enrolls";
        public static final String COLUMN_NAME_ENROLLID = "enrollid";
        public static final String COLUMN_NAME_USER_UUID = "user_uuid";
        public static final String COLUMN_NAME_RESERVEID = "reserveid";
    }


    public static class universitiesTable implements BaseColumns {
        public static final String TABLE_NAME = "universities";
        public static final String COLUMN_NAME_UNI_UUID = "uni_uuid";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_ADDRESS = "address";
    }


    public static class user_access_uni_Table implements BaseColumns {
        public static final String TABLE_NAME = "user_access_uni";
        public static final String COLUMN_NAME_ACCESS_UUID = "access_uuid";
        public static final String COLUMN_NAME_USER_UUID = "user_uuid";
        public static final String COLUMN_NAME_UNI_UUID = "uni_uuid";
    }


    public static class admin_accounts_Table implements BaseColumns {
        public static final String TABLE_NAME = "admin_accounts";
        public static final String COLUMN_NAME_ADMIN_USER_UUID = "admin_user_uuid";
        public static final String COLUMN_NAME_USER_UUID = "user_uuid";
    }
}
