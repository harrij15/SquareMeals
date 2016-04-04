package sm;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by putriz on 4/2/2016.
 * A databse for the recipes
 */
public class RecipeDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "recipes.db";
    private static final String TABLE_NAME = "recipes";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_INGREDIENTS = "ingredients";
    private static final String COLUMN_TIME = "cook_time";

    private static final String TABLE_CREATE = "create table recipes (name text not null, image text not null, description text not null," +
            "ingredients text not null, cook_time text not null);";

    SQLiteDatabase db;

    public RecipeDatabaseHelper(Context context){
        // default constructor
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Insert a recipe into the database.
     *
     * @param recipe, an object of the Recipe class,
     *                whose information will be put into the
     *                database table
     */
    public void insertRecipe(Recipe recipe) {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, recipe.getName());
        values.put(COLUMN_IMAGE, recipe.getImage());
        values.put(COLUMN_DESCRIPTION, recipe.getDescription());

        db.insert(TABLE_NAME, null, values);
        db.close();

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
