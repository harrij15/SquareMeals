package sm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by putriz on 4/10/2016.
 * A database for the users.
 */
public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";

    private static final String TABLE_CREATE = "create table users (name text not null, username text not null, " +
            "password text not null, email text not null);";

    SQLiteDatabase db;

    public UserDatabaseHelper(Context context) {
        // default constructor
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Inserts a new user into the database
     *
     * @param user
     */
    public void insertUser(User user) {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_EMAIL, user.getEmail());

        db.insert(TABLE_NAME, null, values);
        db.close();

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
