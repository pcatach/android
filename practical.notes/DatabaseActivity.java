package practical.notes;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DatabaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        // read database
        NotesOpenHelper dbHelper = new NotesOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("notes", new String[] {"_id", "NOTE_TITLE", "NOTE_CONTENT"}, "", new String[] {}, null, null, "NOTE_TITLE ASC");

        // write to layout
        TextView textView = (TextView) findViewById(R.id.databaseText);
        textView.setText("");
        while(cursor.moveToNext()) {
            String note_id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
            textView.append("(id): " + note_id + "\n");
            String note_title = cursor.getString(cursor.getColumnIndexOrThrow("NOTE_TITLE"));
            textView.append(note_title + "\n");
            String note_content = cursor.getString(cursor.getColumnIndexOrThrow("NOTE_CONTENT"));
            textView.append("(content): " + note_content + "\n");
        }
        cursor.close();
    }

    public void drop(View view){
        NotesOpenHelper dbHelper = new NotesOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(db, 1, 2);
    }
}
