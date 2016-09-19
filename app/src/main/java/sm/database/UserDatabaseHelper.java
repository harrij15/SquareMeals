package sm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import sm.profile.User;

/**
 * Created by putriz on 4/10/2016.
 * A database for the users.
 */
public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "users";
    private static final String ROW_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PREFERENCE = "preference"; // for now the user can only have one preference

    private static final String TABLE_CREATE = "create table users (_id integer primary key autoincrement, name text not null, username text not null, " +
            "password text not null, email text not null, preference text not null);";

    SQLiteDatabase db;

    /**
     * Constructor
     * @param context
     */
    public UserDatabaseHelper(Context context) {
        // default constructor
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Inserts a new user into the database
     *
     * @param user The new User object after sign up to insert into the database
     * @effects
     */
    public long insertUser(User user) {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PREFERENCE, user.getPreference());

        return db.insert(TABLE_NAME, null, values); // returns row id

    }

    /**
     * Updates the data of a user
     *
     * This function is used whenever a user updates their profile information
     * or when a user updates a preference in editPref
     *
     * @param id
     * @param username
     * @param name
     * @param password
     * @param email
     * @param preference
     */
    public boolean updateData(long id, String username, String name, String password, String email, String preference) {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PREFERENCE, preference);

        return db.update(TABLE_NAME, values, ROW_ID + "=" + id, null) > 0;

    }

    /**
     * Closes the database after opening it for insert or update
     */
    public void close() {
        db.close();
    }

    /**
     * Gets the ROW_ID of a user
     *
     * @param username
     * @return
     */
    public long searchID(String username) {

        db = this.getReadableDatabase();
        String query = "select _id, username from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        long id = -1;
        String uname;

        if (cursor.moveToFirst()) {

            do {
                uname = cursor.getString(1);
                if (uname.equals(username)) {
                    id = cursor.getLong(cursor.getColumnIndex(ROW_ID));
                }
            } while (cursor.moveToNext());
        }
        return id;
    }

    /**
     * Gets the password of a user
     *
     * @param username
     * @return
     */
    public String searchPassword(String username){

        db = this.getReadableDatabase();
        String query = "select username, password from "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        String uname, pass;
        pass = "not found";
        if (cursor.moveToFirst()){

            do {
                uname = cursor.getString(0);
                if (uname.equals(username)) {
                    pass = cursor.getString(1);
                    break;
                }

            } while (cursor.moveToNext());
        }

        return pass;
    }

    /**
     * Gets the name of a user
     *
     * @param username
     * @return
     */
    public String searchName(String username) {

        db = this.getReadableDatabase();
        String query = "select username, name from "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        String uname, name;
        name = "not found";

        if (cursor.moveToFirst()) {

            do {
                uname = cursor.getString(0);
                if (uname.equals(username)) {
                    name = cursor.getString(1);
                    break;
                }
            } while (cursor.moveToNext());
        }

        return name;
    }

    /**
     * Gets the preference of a user
     *
     * @param username
     * @return
     */
    public String searchPreference(String username) {

        db = this.getReadableDatabase();
        String query = "select username, preference from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        String uname, prefer;
        prefer = "not found";

        if (cursor.moveToFirst()) {

            do {
                uname = cursor.getString(0);
                if (uname.equals(username)) {
                    prefer = cursor.getString(1);
                    break;
                }
            } while (cursor.moveToNext());
        }

        return prefer;
    }

    /**
     * Gets the email of a user
     *
     * @param username
     * @return
     */
    public String searchEmail(String username) {

        db = this.getReadableDatabase();
        String query = "select username, email from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);

        String uname, email;
        email = "not found";

        if (cursor.moveToFirst()) {

            do {
                uname = cursor.getString(0);
                if (uname.equals(username)) {
                    email = cursor.getString(1);
                    break;
                }
            } while (cursor.moveToNext());
        }
        return email;
    }

    /**
     * Check to see if a username already exists
     *
     * @param username
     * @return
     */
    public boolean isUsernameTaken(String username) {

        db = this.getReadableDatabase();
        String query = "select username from "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        String uname;

        if (cursor.moveToFirst()) {

            do {
                uname = cursor.getString(0);
                if(uname.equals(username)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }

        return false;
    }


    /**
     * Deletes a user account
     *
     * @param username
     * @return
     */
    public boolean deleteUser(String username) {
        return db.delete(TABLE_NAME, COLUMN_USERNAME + "=" + username, null) > 0;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
}
