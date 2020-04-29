package com.example.harjoitustyo2020;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SqlManager {


    private static SqlManager uniqueInstance;
    private static SQLiteDatabase Wdb; // Tämä varmaan pitänee olla täällä?
    private static SQLiteDatabase Rdb; // Tätä varmaan TARVITAAN/KÄYTETÄÄN?


    SqlManager(Context context) {
        SqlDatabaseInitializer dbHelper = new SqlDatabaseInitializer(context);
        Wdb = dbHelper.getWritableDatabase();
        Rdb = dbHelper.getReadableDatabase();
        // Lets ser PRAGMA foreign_keys = ON; so cascade will work
        String SQLquery = "PRAGMA foreign_keys = ON;";
        Wdb.execSQL(SQLquery);
    }


    public static SqlManager getInstance(Context context) {
        if (uniqueInstance == null) {
            uniqueInstance = new SqlManager(context);
        }
        return uniqueInstance;
    }


    public static class SQLuser {

        public static void insertRow(String[] userInfo) {

            String SQLquery = "INSERT INTO " + SqlTablenames.userTable.TABLE_NAME + "(" +
                    SqlTablenames.userTable.COLUMN_NAME_USERNAME + "," +
                    SqlTablenames.userTable.COLUMN_NAME_FIRSTNAME + "," +
                    SqlTablenames.userTable.COLUMN_NAME_SURNAME + "," +
                    SqlTablenames.userTable.COLUMN_NAME_EMAIL + "," +
                    SqlTablenames.userTable.COLUMN_NAME_PHONE_NUMBER + "," +
                    SqlTablenames.userTable.COLUMN_NAME_PWD_HASH + "," +
                    SqlTablenames.userTable.COLUMN_NAME_ADMINISTRATOR +
                    ") VALUES " + "(";

            for (int i = 0; i < userInfo.length; i++) {
                SQLquery += userInfo[i];
                if (i < (userInfo.length-1)) {
                    SQLquery += ",";
                }
            }
            SQLquery += ");";

            Wdb.execSQL(SQLquery);
        }


        public static void updateRow(String UUID, String COLUMN_NAME, String DATA) {
            String SQLquery = "UPDATE " + SqlTablenames.userTable.TABLE_NAME +
                    " SET " + COLUMN_NAME + " = " + DATA +
                    " WHERE " + SqlTablenames.userTable.COLUMN_NAME_USER_UUID + " = " + UUID + ";";
            Wdb.execSQL(SQLquery);
        }


        public static void removeRow(String name) {
            String SQLquery = "DELETE FROM " + SqlTablenames.userTable.TABLE_NAME +
                    " WHERE " + SqlTablenames.userTable.COLUMN_NAME_USERNAME + " = " + name + ";";
            Wdb.execSQL(SQLquery);
        }
    }


    public static class SQLsporthall{

        public static void insertRow(String[] hallInfo) {
            String SQLquery = "INSERT INTO " + SqlTablenames.sporthallTable.TABLE_NAME + "(" +
                    SqlTablenames.sporthallTable.COLUMN_NAME_HALLNAME + "," +
                    SqlTablenames.sporthallTable.COLUMN_NAME_UNI_UUID + "," +
                    SqlTablenames.sporthallTable.COLUMN_NAME_HALLTYPE + "," +
                    SqlTablenames.sporthallTable.COLUMN_NAME_NOT_AVAILABLE +
                    ") VALUES " + "(";

            for (int i = 0; i < hallInfo.length; i++) {
                SQLquery += hallInfo[i];
                if (i < (hallInfo.length-1)) {
                    SQLquery += ",";
                }
            }
            SQLquery += ");";

            Wdb.execSQL(SQLquery);
        }


        public static void updateRow(String UUID, String COLUMN_NAME, String DATA) {
            String SQLquery = "UPDATE " + SqlTablenames.sporthallTable.TABLE_NAME +
                    " SET " + COLUMN_NAME + " = " + DATA +
                    " WHERE " + SqlTablenames.sporthallTable.COLUMN_NAME_HALLID + " = " + UUID + ";";
            Wdb.execSQL(SQLquery);
        }


        public static void removeRow(String UUID) {
            String SQLquery = "DELETE FROM " + SqlTablenames.sporthallTable.TABLE_NAME +
                    " WHERE " + SqlTablenames.sporthallTable.COLUMN_NAME_HALLID + " = " + UUID + ";";
            Wdb.execSQL(SQLquery);
        }
    }


    public static class SQLreservation {

        public static void insertRow(String[] userInfo) {
            String SQLquery = "INSERT INTO " + SqlTablenames.reservationsTable.TABLE_NAME + "(" +
                    SqlTablenames.reservationsTable.COLUMN_NAME_HALLID + "," +
                    SqlTablenames.reservationsTable.COLUMN_NAME_SPORT + "," +
                    SqlTablenames.reservationsTable.COLUMN_NAME_START_TIME + "," +
                    SqlTablenames.reservationsTable.COLUMN_NAME_DURATION + "," +
                    SqlTablenames.reservationsTable.COLUMN_NAME_USER_UUID + "," +
                    SqlTablenames.reservationsTable.COLUMN_NAME_MAXPARTICIPANTS + "," +
                    SqlTablenames.reservationsTable.COLUMN_NAME_RECURRING_EVENT +
                    ") VALUES " + "(";

            for (int i = 0; i < userInfo.length; i++) {
                SQLquery += userInfo[i];
                if (i < (userInfo.length-1)) {
                    SQLquery += ",";
                }
            }
            SQLquery += ");";

            Log.d("SQL", "Reservation inserted");

            Wdb.execSQL(SQLquery);
        }


        public static void updateRow(String UUID, String COLUMN_NAME, String DATA) {
            String SQLquery = "UPDATE " + SqlTablenames.reservationsTable.TABLE_NAME +
                    " SET " + COLUMN_NAME + " = " + DATA +
                    " WHERE " + SqlTablenames.reservationsTable.COLUMN_NAME_RESERVEID + " = " + UUID + ";";
            Wdb.execSQL(SQLquery);
        }


        public static void removeRow(String UUID) {
            String SQLquery = "DELETE FROM " + SqlTablenames.reservationsTable.TABLE_NAME +
                    " WHERE " + SqlTablenames.reservationsTable.COLUMN_NAME_RESERVEID + " = " + UUID + ";";
            Wdb.execSQL(SQLquery);
        }
    }

    //Methods for adding and removing enrolls
    public static class SQLenrolls {

        public static void insertRow(String USER_UUID, String RESERVEID) {
            String SQLquery = "INSERT INTO " + SqlTablenames.enrollsTable.TABLE_NAME + " (" +
                    SqlTablenames.enrollsTable.COLUMN_NAME_USER_UUID + "," +
                    SqlTablenames.enrollsTable.COLUMN_NAME_RESERVEID +
                    ") VALUES " + "(" + USER_UUID + ", " + RESERVEID + ");";

            Wdb.execSQL(SQLquery);
        }


        public static void removeEnrollsByID(String UUID, String User) {
            String SQLquery = "DELETE FROM " + SqlTablenames.enrollsTable.TABLE_NAME +
                    " WHERE " + SqlTablenames.enrollsTable.COLUMN_NAME_ENROLLID + " = " + UUID + " AND " + SqlTablenames.enrollsTable.COLUMN_NAME_USER_UUID + " = " + User + ";";
            Wdb.execSQL(SQLquery);
        }


        public static void removeAllUsersEnrolls(int userUUID) {
            String SQLquery = "DELETE FROM " + SqlTablenames.enrollsTable.TABLE_NAME +
                    " WHERE " + SqlTablenames.enrollsTable.COLUMN_NAME_USER_UUID + " = " + userUUID + ";";
            Wdb.execSQL(SQLquery);
        }
    }

    //Methods for adding, updating and removing universities
    public static class SQLuniversities {

        public static void insertRow(String UNI_NAME, String UNI_ADDRESS) {
            String SQLquery = "INSERT INTO " + SqlTablenames.universitiesTable.TABLE_NAME + " (" +
                    SqlTablenames.universitiesTable.COLUMN_NAME_NAME + "," +
                    SqlTablenames.universitiesTable.COLUMN_NAME_ADDRESS +
                    ") VALUES " + "(" + UNI_NAME + ", " + UNI_ADDRESS + ");";

            Wdb.execSQL(SQLquery);
        }


        public static void updateRow(String UUID, String COLUMN_NAME, String DATA) {
            String SQLquery = "UPDATE " + SqlTablenames.universitiesTable.TABLE_NAME +
                    " SET " + COLUMN_NAME + " = " + DATA +
                    " WHERE " + SqlTablenames.universitiesTable.COLUMN_NAME_UNI_UUID + " = " + UUID + ";";
            Wdb.execSQL(SQLquery);
        }


        public static void removeRow(String UUID) {
            String SQLquery = "DELETE FROM " + SqlTablenames.universitiesTable.TABLE_NAME +
                    " WHERE " + SqlTablenames.universitiesTable.COLUMN_NAME_UNI_UUID + " = " + UUID + ";";
            Wdb.execSQL(SQLquery);
        }
    }

    //Methods for adding and removing user access to universities
    public static class SQLaccess {

        public static void insertRow(String USER_UUID, String UNI_UUID) {
            String SQLquery = "INSERT INTO " + SqlTablenames.user_access_uni_Table.TABLE_NAME + " (" +
                    SqlTablenames.user_access_uni_Table.COLUMN_NAME_USER_UUID + "," +
                    SqlTablenames.user_access_uni_Table.COLUMN_NAME_UNI_UUID +
                    ") VALUES " + "(" + USER_UUID + ", " + UNI_UUID + ");";

            Wdb.execSQL(SQLquery);
        }


        public static void removeRow(String UUID) {
            String SQLquery = "DELETE FROM " + SqlTablenames.user_access_uni_Table.TABLE_NAME +
                    " WHERE " + SqlTablenames.user_access_uni_Table.COLUMN_NAME_ACCESS_UUID + " = " + UUID + ";";
            Wdb.execSQL(SQLquery);
        }
    }


    ///// DATA FROM DATABASE TO OBJECTS /////
    // university name to arraylist
    public static ArrayList<String> getUniNameFromDatabase() throws SQLException {
        ArrayList<String> uniList = new ArrayList<>();

        String rawQuery = "SELECT " + SqlTablenames.universitiesTable.COLUMN_NAME_NAME + " FROM " + SqlTablenames.universitiesTable.TABLE_NAME +";";
        Cursor cursor = Rdb.rawQuery(
                rawQuery,
                null
        );

        if (cursor.moveToFirst()) {
            do {

                String name = cursor.getString(cursor.getColumnIndex(
                        SqlTablenames.universitiesTable.COLUMN_NAME_NAME
                ));

                uniList.add(name);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return uniList;
    }


    public static ArrayList<Integer> getUniUUIDFromDatabase() throws SQLException {
        ArrayList<Integer> uni_uuid_List = new ArrayList<>();

        String rawQuery = "SELECT " + SqlTablenames.universitiesTable.COLUMN_NAME_UNI_UUID + " FROM " + SqlTablenames.universitiesTable.TABLE_NAME +";";
        Cursor cursor = Rdb.rawQuery(
                rawQuery,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int UUID = cursor.getInt(cursor.getColumnIndex(
                        SqlTablenames.universitiesTable.COLUMN_NAME_UNI_UUID
                ));

                uni_uuid_List.add(UUID);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return uni_uuid_List;
    }


    public static ArrayList<Integer> getHallUUIDFromDatabase() throws SQLException {
        ArrayList<Integer> hall_uuid_List = new ArrayList<>();

        String rawQuery = "SELECT " + SqlTablenames.sporthallTable.COLUMN_NAME_HALLID + " FROM " + SqlTablenames.sporthallTable.TABLE_NAME +";";
        Cursor cursor = Rdb.rawQuery(
                rawQuery,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int UUID = cursor.getInt(cursor.getColumnIndex(
                        SqlTablenames.sporthallTable.COLUMN_NAME_HALLID
                ));

                hall_uuid_List.add(UUID);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return hall_uuid_List;
    }


    public static String getUniAccessUniName(int userID) throws SQLException {
        String uniName = "default";
        String rawQuery = "SELECT "+ SqlTablenames.universitiesTable.COLUMN_NAME_NAME + " FROM " + SqlTablenames.user_access_uni_Table.TABLE_NAME + " INNER JOIN " + SqlTablenames.universitiesTable.TABLE_NAME
                + " ON " + SqlTablenames.universitiesTable.TABLE_NAME + "." + SqlTablenames.universitiesTable.COLUMN_NAME_UNI_UUID + " = "
                + SqlTablenames.user_access_uni_Table.TABLE_NAME + "." + SqlTablenames.user_access_uni_Table.COLUMN_NAME_UNI_UUID
                + " WHERE " + SqlTablenames.user_access_uni_Table.COLUMN_NAME_USER_UUID + " = " + userID + ";";
        Cursor cursor = Rdb.rawQuery(
                rawQuery,
                null
        );

        if (cursor.moveToFirst()) {
            uniName = cursor.getString(cursor.getColumnIndex(SqlTablenames.universitiesTable.COLUMN_NAME_NAME));
        }
        cursor.close();
        return uniName;
    }


    public static Integer getUniUUid(String name) throws SQLException {
        int uniID = 0;
        String rawQuery = "SELECT "+ SqlTablenames.universitiesTable.COLUMN_NAME_UNI_UUID + " FROM " + SqlTablenames.universitiesTable.TABLE_NAME
                + " WHERE " + SqlTablenames.universitiesTable.COLUMN_NAME_NAME + " = '" + name + "';";
        Cursor cursor = Rdb.rawQuery(
                rawQuery,
                null
        );

        if (cursor.moveToFirst()) {
            uniID = cursor.getInt(cursor.getColumnIndex(SqlTablenames.universitiesTable.COLUMN_NAME_UNI_UUID));
        }
        cursor.close();
        return uniID;
    }


    public static Integer getUserUUID(String name) throws SQLException {
        int userID = 0;
        String rawQuery = "SELECT "+ SqlTablenames.userTable.COLUMN_NAME_USER_UUID + " FROM " + SqlTablenames.userTable.TABLE_NAME
                + " WHERE " + SqlTablenames.userTable.COLUMN_NAME_USERNAME + " = '" + name + "';";
        Cursor cursor = Rdb.rawQuery(
                rawQuery,
                null
        );

        if (cursor.moveToFirst()) {
            userID = cursor.getInt(cursor.getColumnIndex(SqlTablenames.userTable.COLUMN_NAME_USER_UUID));
        }
        cursor.close();
        return userID;
    }


    // userdata to user object
    public static List<User> getUsersFromDatabase() throws SQLException {
        List<User> userList = new ArrayList<>();

        String rawQuery = "SELECT "+ SqlTablenames.userTable.TABLE_NAME + "." + SqlTablenames.userTable.COLUMN_NAME_USER_UUID + ", " + SqlTablenames.userTable.TABLE_NAME + "." + SqlTablenames.userTable.COLUMN_NAME_USERNAME
                + ", " + SqlTablenames.userTable.TABLE_NAME + "." + SqlTablenames.userTable.COLUMN_NAME_FIRSTNAME + ", " + SqlTablenames.userTable.TABLE_NAME + "." + SqlTablenames.userTable.COLUMN_NAME_SURNAME
                + ", " + SqlTablenames.userTable.TABLE_NAME + "." + SqlTablenames.userTable.COLUMN_NAME_EMAIL + ", " + SqlTablenames.userTable.TABLE_NAME + "." + SqlTablenames.userTable.COLUMN_NAME_PHONE_NUMBER
                + ", " + SqlTablenames.userTable.TABLE_NAME + "." + SqlTablenames.userTable.COLUMN_NAME_PWD_HASH
                + ", " + SqlTablenames.userTable.TABLE_NAME + "." + SqlTablenames.userTable.COLUMN_NAME_ADMINISTRATOR + ", " + SqlTablenames.universitiesTable.TABLE_NAME + "." + SqlTablenames.universitiesTable.COLUMN_NAME_NAME
                + " FROM ((" + SqlTablenames.userTable.TABLE_NAME + " INNER JOIN " + SqlTablenames.user_access_uni_Table.TABLE_NAME
                + " ON " + SqlTablenames.user_access_uni_Table.TABLE_NAME + "." + SqlTablenames.user_access_uni_Table.COLUMN_NAME_USER_UUID + " = "
                + SqlTablenames.userTable.TABLE_NAME + "." + SqlTablenames.userTable.COLUMN_NAME_USER_UUID + ") INNER JOIN " + SqlTablenames.universitiesTable.TABLE_NAME
                + " ON " + SqlTablenames.universitiesTable.TABLE_NAME + "." + SqlTablenames.universitiesTable.COLUMN_NAME_UNI_UUID + "="
                + SqlTablenames.user_access_uni_Table.TABLE_NAME + "." + SqlTablenames.user_access_uni_Table.COLUMN_NAME_UNI_UUID +");";
        Cursor cursor = Rdb.rawQuery(
                rawQuery,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int ID = cursor.getInt(cursor.getColumnIndex(
                        SqlTablenames.userTable.COLUMN_NAME_USER_UUID
                ));
                String userName = cursor.getString(cursor.getColumnIndex(
                        SqlTablenames.userTable.COLUMN_NAME_USERNAME
                ));
                String firstName = cursor.getString(cursor.getColumnIndex(
                        SqlTablenames.userTable.COLUMN_NAME_FIRSTNAME
                ));
                String surName = cursor.getString(cursor.getColumnIndex(
                        SqlTablenames.userTable.COLUMN_NAME_SURNAME
                ));
                String email = cursor.getString(cursor.getColumnIndex(
                        SqlTablenames.userTable.COLUMN_NAME_EMAIL
                ));
                String phoneNum = cursor.getString(cursor.getColumnIndex(
                        SqlTablenames.userTable.COLUMN_NAME_PHONE_NUMBER
                ));
                String pwdHash = cursor.getString(cursor.getColumnIndex(
                        SqlTablenames.userTable.COLUMN_NAME_PWD_HASH
                ));
                boolean admin = (cursor.getInt(cursor.getColumnIndex(
                        SqlTablenames.userTable.COLUMN_NAME_ADMINISTRATOR
                )) == 1);
                String uniName = cursor.getString(cursor.getColumnIndex(
                        SqlTablenames.universitiesTable.COLUMN_NAME_NAME
                ));


                User user = new User();

                user.setUUID(ID);
                user.setUserName(userName);
                user.setFirstName(firstName);
                user.setSurName(surName);
                user.setEmail(email);
                user.setPhoneNum(phoneNum);
                user.setPasswordHash(pwdHash);
                user.setAdminPrivilege(admin);
                user.setUniName(uniName);

                userList.add(user);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }


    // sporthalldata to object
    public static List<Sporthall> getSporthallsFromDatabase() {
        List<Sporthall> hallList = new ArrayList<>();

        // tässä sporthalliim liitetään unin tiedot (nyt hakee kaikki sporthallin tiedot ja liittää niihin uni tablen niin että molemmissa uni id = 1)
        String rawQuery = "SELECT "+ SqlTablenames.sporthallTable.COLUMN_NAME_HALLID + ", " + SqlTablenames.sporthallTable.COLUMN_NAME_HALLNAME
                + ", " + SqlTablenames.universitiesTable.COLUMN_NAME_NAME + ", " + SqlTablenames.sporthallTable.COLUMN_NAME_HALLTYPE + ", " + SqlTablenames.sporthallTable.COLUMN_NAME_NOT_AVAILABLE
                + " FROM " + SqlTablenames.sporthallTable.TABLE_NAME + " INNER JOIN " + SqlTablenames.universitiesTable.TABLE_NAME
                + " ON " + SqlTablenames.universitiesTable.TABLE_NAME + "." + SqlTablenames.universitiesTable.COLUMN_NAME_UNI_UUID + " = "
                + SqlTablenames.sporthallTable.TABLE_NAME + "." + SqlTablenames.sporthallTable.COLUMN_NAME_UNI_UUID + ";";
        Cursor c = Rdb.rawQuery(
                rawQuery,
                null
        );

        if (c.moveToFirst()) {
            do {
                int ID = c.getInt(c.getColumnIndex(
                        SqlTablenames.sporthallTable.COLUMN_NAME_HALLID
                ));
                String name = c.getString(c.getColumnIndex(
                        SqlTablenames.sporthallTable.COLUMN_NAME_HALLNAME
                ));
                String uniname = c.getString(c.getColumnIndex(
                        SqlTablenames.universitiesTable.COLUMN_NAME_NAME
                ));
                String type = c.getString(c.getColumnIndex(
                        SqlTablenames.sporthallTable.COLUMN_NAME_HALLTYPE
                ));
                boolean notAvailable = (c.getInt(c.getColumnIndex(
                        SqlTablenames.sporthallTable.COLUMN_NAME_NOT_AVAILABLE
                )) == 1);

                Sporthall sporthall = new Sporthall();
                sporthall.setUUID(ID);
                sporthall.setName(name);
                sporthall.setUniversityName(uniname);
                sporthall.setType(type);
                sporthall.setDisabled(notAvailable);

                sporthall.updateReservationsFromSQL();

                hallList.add(sporthall);

            } while (c.moveToNext());
        }
        c.close();
        return hallList;
    }


    //get reservations from database
    public static List<Reservation> getReservationsFromDatabase(Sporthall sporthall) {
        List<Reservation> reservationList = new ArrayList<>();

        String[] sporthallID = {Integer.toString(sporthall.getUUID())}; // The id of the sporthall
        String whereClause = SqlTablenames.reservationsTable.COLUMN_NAME_HALLID + " = ?";

        Cursor cursor = Rdb.query(SqlTablenames.reservationsTable.TABLE_NAME,null,
                whereClause, sporthallID,
                null, null, null);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm");

        if (cursor.moveToFirst()) {
            do {

                int ID = cursor.getInt(cursor.getColumnIndex(
                        SqlTablenames.reservationsTable.COLUMN_NAME_RESERVEID
                ));
                String sport = cursor.getString(cursor.getColumnIndex(
                        SqlTablenames.reservationsTable.COLUMN_NAME_SPORT
                ));

                // CALENDAR PARSE STARTS
                Calendar startTime = Calendar.getInstance();
                try {
                    startTime.setTime(format.parse(cursor.getString(cursor.getColumnIndex(
                            SqlTablenames.reservationsTable.COLUMN_NAME_START_TIME
                    ))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // CALENDAR PARSE ENDS

                int duration = cursor.getInt(cursor.getColumnIndex(
                        SqlTablenames.reservationsTable.COLUMN_NAME_DURATION
                ));
                int ownerID = cursor.getInt(cursor.getColumnIndex(
                        SqlTablenames.reservationsTable.COLUMN_NAME_USER_UUID
                ));
                int maxPart = cursor.getInt(cursor.getColumnIndex(
                        SqlTablenames.reservationsTable.COLUMN_NAME_MAXPARTICIPANTS
                ));
                int recurEvent = cursor.getInt(cursor.getColumnIndex(
                        SqlTablenames.reservationsTable.COLUMN_NAME_RECURRING_EVENT
                ));


                Reservation reservation = new Reservation();
                reservation.setUUID(ID);
                reservation.setParent(sporthall);
                reservation.setSport(sport);
                reservation.setStartCalendar(startTime);
                reservation.setEndFromStartDur(startTime, duration);
                reservation.setOwner(ownerID);
                reservation.setMaxParticipants(maxPart);
                // TODO UPDATE ATTENDANCES
                // TODO RECURRING EVENT

                reservationList.add(reservation);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return reservationList;
    }


    // get enrolls from database
    public static List<Enroll> getEnrollsFromDatabase() throws SQLException {
        List<Enroll> enrollsList = new ArrayList<>();

        String[] reservationID = {SqlTablenames.enrollsTable.COLUMN_NAME_ENROLLID, SqlTablenames.enrollsTable.COLUMN_NAME_RESERVEID, SqlTablenames.enrollsTable.COLUMN_NAME_USER_UUID};

        Cursor cursor = Rdb.query(SqlTablenames.enrollsTable.TABLE_NAME, reservationID,
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int enrollID = cursor.getInt(cursor.getColumnIndex(
                        SqlTablenames.enrollsTable.COLUMN_NAME_ENROLLID
                ));
                int reserveID = cursor.getInt(cursor.getColumnIndex(
                        SqlTablenames.enrollsTable.COLUMN_NAME_RESERVEID
                ));
                int userUUID = cursor.getInt(cursor.getColumnIndex(
                        SqlTablenames.enrollsTable.COLUMN_NAME_USER_UUID
                ));


                Enroll enroll = new Enroll();

                enroll.setEnrollID(enrollID);
                enroll.setReserveID(reserveID);
                enroll.setUserUUID(userUUID);

                enrollsList.add(enroll);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return enrollsList;
    }


    /////////////////////////////////
    //PRESETTING VALUES TO DATABASE//
    /////////////////////////////////

    public static void presetDatabaseValues() {

        //User preset values
        String[] user = {"'admin'", "'admin'", "'admin'", "'admin.admin@adminmail.com'", "'0550165498'", "'" + password_manager.get_hashed_password("admin", "admin") + "'" , "1" };
        SQLuser.insertRow(user);
        user = new String[]{"'sanniaho'", "'Sanni'", "'Aho'", "'Sanni.Aho@gmail.com'", "'0502149132'", "'" + password_manager.get_hashed_password("Kissaton!han1111111", "sanniaho") + "'" , "0"};
        SQLuser.insertRow(user);
        user = new String[]{"'marttiko'", "'Martti'", "'Korhonen'", "'Martti.Korhone@hotmail.com'", "'0499709857'", "'" + password_manager.get_hashed_password("oispaK4lj4444444??", "marttiko") + "'" , "0"};
        SQLuser.insertRow(user);
        user = new String[]{"'liisamaria'", "'Liisa'", "'Melender'", "'lisbe99@hotmail.com'", "'0401325468'", "'" + password_manager.get_hashed_password("V4ppuperuttuuyyh!", "liisamaria") + "'" , "0"};
        SQLuser.insertRow(user);
        user = new String[]{"'Miav'", "'Mia'", "'Virtanen'", "'Mia.Virr@gmail.com'", "'04004721563'", "'" + password_manager.get_hashed_password("Itscoron4Tiiiime@", "Miav") + "'" , "0"};
        SQLuser.insertRow(user);
        user = new String[]{"'Georgegrey'", "'George'", "'Grey'", "'George123@hotmail.com'", "'0400121212'", "'" + password_manager.get_hashed_password("Beestfreend4ever!", "Georgegrey") + "'" , "0"};
        SQLuser.insertRow(user);
        user = new String[]{"'Ninnil'", "'Ninni'", "'Laine'", "'ninnilaine@hotmail.com'", "'0513935874'", "'" + password_manager.get_hashed_password("Yolo&Yolo999999", "Ninnil") + "'" , "0"};
        SQLuser.insertRow(user);
        user = new String[]{"'Lolamann'", "'Lola'", "'Manninen'", "'lola.mann@gmail.com'", "'0672211447'", "'" + password_manager.get_hashed_password("jeeeeeee654321!!", "Lolamann") + "'" , "0"};
        SQLuser.insertRow(user);
        user = new String[]{"'shaniii'", "'Shani'", "'Yang'", "'shani@hotmail.com'", "'0671649785'", "'" + password_manager.get_hashed_password("programmer123!", "shaniii") + "'" , "1"};
        SQLuser.insertRow(user);

        //Universities preset values
        SQLuniversities.insertRow("'LUT'", "'Yliopistonkatu 34, 53850 Lappeenranta'");

        //User_access_uni preset values
        SQLaccess.insertRow("1", "1");
        SQLaccess.insertRow("2", "1");
        SQLaccess.insertRow("3", "1");
        SQLaccess.insertRow("4", "1");
        SQLaccess.insertRow("5", "1");
        SQLaccess.insertRow("6", "1");
        SQLaccess.insertRow("7", "1");
        SQLaccess.insertRow("8", "1");
        SQLaccess.insertRow("9", "1");

        //Sporthall preset values
        String[] hall = { "'Titanium'", "1", "'Multipurpose'", "0" };
        SQLsporthall.insertRow(hall);
        hall = new String[]{ "'Silver'", "1", "'Badminton'", "0" };
        SQLsporthall.insertRow(hall);
        hall = new String[]{ "'Iron'", "1", "'Multipurpose'", "0" };
        SQLsporthall.insertRow(hall);
        hall = new String[]{ "'Steel'", "1", "'Gym'", "0" };
        SQLsporthall.insertRow(hall);

        //Reservations preset values
        String[] reserved = { "2", "'football'", "'2020-08-20T14:00'", "3", "4", "30", "0" };
        SQLreservation.insertRow(reserved);
        reserved = new String[]{ "1", "'volleyball'", "'2020-08-20T16:00'", "2", "2", "16", "0" };
        SQLreservation.insertRow(reserved);

        //Enrolls preset values
        SQLenrolls.insertRow("3", "1");
        SQLenrolls.insertRow("4", "1");
        SQLenrolls.insertRow("5", "1");
        SQLenrolls.insertRow("6", "1");
        SQLenrolls.insertRow("2", "2");
        SQLenrolls.insertRow("7", "2");
        SQLenrolls.insertRow("8", "2");
        SQLenrolls.insertRow("1", "2");
        SQLenrolls.insertRow("4", "2");
    }
}