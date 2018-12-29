package practical.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Patrick on 03/04/2017.
 */

public class NotesOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String NOTES_TABLE_NAME = "notes";
    private static final String NOTES_TABLE_CREATE =
            "CREATE TABLE " + NOTES_TABLE_NAME +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1, NOTE_TITLE TEXT, NOTE_CONTENT TEXT);";

    NotesOpenHelper(Context context) {
        super(context, "notesdb", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(NOTES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE notes");
        onCreate(db);
    }

    /** to erase all the notes */
    public void dropNotes(SQLiteDatabase db){
        // Create note in database
        db.delete("notes", "", null);
    }
}