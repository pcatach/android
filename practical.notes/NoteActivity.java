package practical.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class NoteActivity extends AppCompatActivity {
    public String note_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // Get the Intent that started this activity and extract the note_id
        Intent intent = getIntent();
        note_id = intent.getStringExtra("practical.notes._id");

        // Get title and content from db
        NotesOpenHelper dbHelper = new NotesOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("notes", new String[] {"NOTE_TITLE", "NOTE_CONTENT"}, "_id =?", new String[] {note_id}, null, null, "_id DESC");
        cursor.moveToFirst();
        String note_title = cursor.getString(cursor.getColumnIndex("NOTE_TITLE"));
        String note_content = cursor.getString(cursor.getColumnIndex("NOTE_CONTENT"));
        cursor.close();

        // Capture the layout's TextView and set the title as its text
        EditText note_title_field = (EditText) findViewById(R.id.note_title);
        EditText note_content_field = (EditText) findViewById(R.id.note_content);
        note_title_field.setText(note_title);
        note_content_field.setText(note_content);
    }

    @Override
    protected void onPause(){
        EditText note_content_field = (EditText) findViewById(R.id.note_content);
        EditText note_title_field = (EditText) findViewById(R.id.note_title);
        String note_content = note_content_field.getText().toString();
        String note_title = note_title_field.getText().toString();

        // Save note content to database
        NotesOpenHelper dbHelper = new NotesOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NOTE_TITLE", note_title);
        values.put("NOTE_CONTENT", note_content);
        db.update("notes", values, "_id=\""+note_id+"\"" , null);

        super.onStop();
    }
}
