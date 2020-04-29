package com.example.harjoitustyo2020;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//SQL HELPER
public class SqlDatabaseInitializer extends SQLiteOpenHelper {

        // If you change the database schema, you must increment the version.
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "sporthallmanager.db";

        public SqlDatabaseInitializer(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // Creation of the sql tables
        public void onCreate(SQLiteDatabase db) {

            final String SQL_CREATE_USER =
                    "CREATE TABLE " + SqlTablenames.userTable.TABLE_NAME + " (" +
                            SqlTablenames.userTable.COLUMN_NAME_USER_UUID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                            SqlTablenames.userTable.COLUMN_NAME_USERNAME + " TEXT NOT NULL," +
                            SqlTablenames.userTable.COLUMN_NAME_FIRSTNAME + " TEXT NOT NULL," +
                            SqlTablenames.userTable.COLUMN_NAME_SURNAME + " TEXT NOT NULL," +
                            SqlTablenames.userTable.COLUMN_NAME_EMAIL + " TEXT NOT NULL," +
                            SqlTablenames.userTable.COLUMN_NAME_PHONE_NUMBER + " TEXT NOT NULL," +
                            SqlTablenames.userTable.COLUMN_NAME_PWD_HASH + " TEXT NOT NULL," +
                            SqlTablenames.userTable.COLUMN_NAME_ADMINISTRATOR + " INTEGER DEFAULT 0 " +
                            "CHECK ("+SqlTablenames.userTable.COLUMN_NAME_ADMINISTRATOR+" = 0 OR "+SqlTablenames.userTable.COLUMN_NAME_ADMINISTRATOR+" = 1));";

            final String SQL_CREATE_SPORTHALL =
                    "CREATE TABLE " + SqlTablenames.sporthallTable.TABLE_NAME + " (" +
                            SqlTablenames.sporthallTable.COLUMN_NAME_HALLID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                            SqlTablenames.sporthallTable.COLUMN_NAME_HALLNAME + " TEXT NOT NULL," +
                            SqlTablenames.sporthallTable.COLUMN_NAME_UNI_UUID + " INTEGER," +
                            SqlTablenames.sporthallTable.COLUMN_NAME_HALLTYPE + " TEXT NOT NULL," +
                            SqlTablenames.sporthallTable.COLUMN_NAME_NOT_AVAILABLE +" INTEGER NOT NULL DEFAULT 0 " +
                            "CHECK ("+SqlTablenames.sporthallTable.COLUMN_NAME_NOT_AVAILABLE+" = 0 OR "+SqlTablenames.sporthallTable.COLUMN_NAME_NOT_AVAILABLE+" = 1), " +
                            "FOREIGN KEY ("+SqlTablenames.user_access_uni_Table.COLUMN_NAME_UNI_UUID+") REFERENCES "+SqlTablenames.universitiesTable.TABLE_NAME+" ("+SqlTablenames.universitiesTable.COLUMN_NAME_UNI_UUID+") ON DELETE CASCADE);";

            final String SQL_CREATE_RESERVATION =
                    "CREATE TABLE " + SqlTablenames.reservationsTable.TABLE_NAME + " (" +
                            SqlTablenames.reservationsTable.COLUMN_NAME_RESERVEID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                            SqlTablenames.reservationsTable.COLUMN_NAME_HALLID + " INTEGER," +
                            SqlTablenames.reservationsTable.COLUMN_NAME_SPORT + " TEXT NOT NULL DEFAULT 'default'," +
                            SqlTablenames.reservationsTable.COLUMN_NAME_START_TIME + " TEXT NOT NULL," +
                            SqlTablenames.reservationsTable.COLUMN_NAME_DURATION + " INTEGER NOT NULL," +
                            SqlTablenames.reservationsTable.COLUMN_NAME_USER_UUID + " INTEGER," +
                            SqlTablenames.reservationsTable.COLUMN_NAME_MAXPARTICIPANTS + " INTEGER," +
                            SqlTablenames.reservationsTable.COLUMN_NAME_RECURRING_EVENT + " INTEGER DEFAULT 0 " +
                            "CHECK ("+SqlTablenames.reservationsTable.COLUMN_NAME_RECURRING_EVENT+" = 0 OR "+SqlTablenames.reservationsTable.COLUMN_NAME_RECURRING_EVENT+" = 1)," +
                            "FOREIGN KEY ("+SqlTablenames.reservationsTable.COLUMN_NAME_HALLID+") REFERENCES "+SqlTablenames.sporthallTable.TABLE_NAME+" ("+SqlTablenames.sporthallTable.COLUMN_NAME_HALLID+") ON DELETE CASCADE," +
                            "FOREIGN KEY ("+SqlTablenames.reservationsTable.COLUMN_NAME_USER_UUID+") REFERENCES "+SqlTablenames.userTable.TABLE_NAME+" ("+SqlTablenames.userTable.COLUMN_NAME_USER_UUID+") ON DELETE CASCADE);";

            final String SQL_CREATE_ENROLLS =
                    "CREATE TABLE " + SqlTablenames.enrollsTable.TABLE_NAME + " (" +
                            SqlTablenames.enrollsTable.COLUMN_NAME_ENROLLID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                            SqlTablenames.enrollsTable.COLUMN_NAME_USER_UUID + " INTEGER," +
                            SqlTablenames.enrollsTable.COLUMN_NAME_RESERVEID + " INTEGER," +
                            "FOREIGN KEY ("+SqlTablenames.enrollsTable.COLUMN_NAME_RESERVEID+") REFERENCES "+SqlTablenames.reservationsTable.TABLE_NAME+" ("+SqlTablenames.reservationsTable.COLUMN_NAME_RESERVEID+") ON DELETE CASCADE," +
                            "FOREIGN KEY ("+SqlTablenames.enrollsTable.COLUMN_NAME_USER_UUID+") REFERENCES "+SqlTablenames.userTable.TABLE_NAME+" ("+SqlTablenames.userTable.COLUMN_NAME_USER_UUID+") ON DELETE CASCADE);";

            final String SQL_CREATE_UNIVERSITIES =
                    "CREATE TABLE " + SqlTablenames.universitiesTable.TABLE_NAME + " (" +
                            SqlTablenames.universitiesTable.COLUMN_NAME_UNI_UUID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                            SqlTablenames.universitiesTable.COLUMN_NAME_NAME + " TEXT NOT NULL," +
                            SqlTablenames.universitiesTable.COLUMN_NAME_ADDRESS + " TEXT NOT NULL);";

            final String SQL_CREATE_USER_ACCESS_UNI =
                    "CREATE TABLE " + SqlTablenames.user_access_uni_Table.TABLE_NAME + " (" +
                            SqlTablenames.user_access_uni_Table.COLUMN_NAME_ACCESS_UUID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                            SqlTablenames.user_access_uni_Table.COLUMN_NAME_USER_UUID + " INTEGER," +
                            SqlTablenames.user_access_uni_Table.COLUMN_NAME_UNI_UUID + " INTEGER," +
                            "FOREIGN KEY ("+SqlTablenames.user_access_uni_Table.COLUMN_NAME_USER_UUID+") REFERENCES "+SqlTablenames.userTable.TABLE_NAME+" ("+SqlTablenames.userTable.COLUMN_NAME_USER_UUID+") ON DELETE CASCADE," +
                            "FOREIGN KEY ("+SqlTablenames.user_access_uni_Table.COLUMN_NAME_UNI_UUID+") REFERENCES "+SqlTablenames.universitiesTable.TABLE_NAME+" ("+SqlTablenames.universitiesTable.COLUMN_NAME_UNI_UUID+") ON DELETE CASCADE);";

            final String SQL_CREATE_ADMIN_ACCOUNTS =
                    "CREATE TABLE " + SqlTablenames.admin_accounts_Table.TABLE_NAME + " (" +
                            SqlTablenames.admin_accounts_Table.COLUMN_NAME_ADMIN_USER_UUID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                            SqlTablenames.admin_accounts_Table.COLUMN_NAME_USER_UUID + " INTEGER," +
                            "FOREIGN KEY ("+SqlTablenames.admin_accounts_Table.COLUMN_NAME_USER_UUID+") REFERENCES "+SqlTablenames.userTable.TABLE_NAME+" ("+SqlTablenames.userTable.COLUMN_NAME_USER_UUID+") ON DELETE CASCADE);";


            db.execSQL(SQL_CREATE_USER);
            db.execSQL(SQL_CREATE_SPORTHALL);
            db.execSQL(SQL_CREATE_RESERVATION);
            db.execSQL(SQL_CREATE_ENROLLS);
            db.execSQL(SQL_CREATE_UNIVERSITIES);
            db.execSQL(SQL_CREATE_USER_ACCESS_UNI);
            db.execSQL(SQL_CREATE_ADMIN_ACCOUNTS);
        }


        // kuinka tämän käyttö ??? tarvitaanko edes koska update metodit on olemassa
        //  voidaan suoraan päivittää tietokantaan ja hakea tietokannasta olioihin uudestaan käyttämättä tätä???
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


            //Delete functions for sql tables
            final String SQL_DELETE_USER =
                    "DROP TABLE IF EXISTS " + SqlTablenames.userTable.TABLE_NAME;

            final String SQL_DELETE_SPORTHALL =
                    "DROP TABLE IF EXISTS " + SqlTablenames.sporthallTable.TABLE_NAME;

            final String SQL_DELETE_RESERVATION =
                    "DROP TABLE IF EXISTS " + SqlTablenames.reservationsTable.TABLE_NAME;

            final String SQL_DELETE_ENROLLS =
                    "DROP TABLE IF EXISTS " + SqlTablenames.enrollsTable.TABLE_NAME;

            final String SQL_DELETE_UNIVERSITIES =
                    "DROP TABLE IF EXISTS " + SqlTablenames.universitiesTable.TABLE_NAME;

            final String SQL_DELETE_USER_ACCESS_UNI =
                    "DROP TABLE IF EXISTS " + SqlTablenames.user_access_uni_Table.TABLE_NAME;

            final String SQL_DELETE_ADMIN_ACCOUNTS =
                    "DROP TABLE IF EXISTS " + SqlTablenames.admin_accounts_Table.TABLE_NAME;

            db.execSQL(SQL_DELETE_USER);
            db.execSQL(SQL_DELETE_SPORTHALL);
            db.execSQL(SQL_DELETE_RESERVATION);
            db.execSQL(SQL_DELETE_ENROLLS);
            db.execSQL(SQL_DELETE_UNIVERSITIES);
            db.execSQL(SQL_DELETE_USER_ACCESS_UNI);
            db.execSQL(SQL_DELETE_ADMIN_ACCOUNTS);

            onCreate(db);
        }
}
