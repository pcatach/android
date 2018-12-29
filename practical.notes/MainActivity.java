package practical.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        setContentView(R.layout.activity_main);
        writeNotes();
    }

    @Override
    public void onResume(){
        super.onResume();
        ((EditText)findViewById(R.id.newNote)).setText("");
        writeNotes();
    }

    public void writeNotes(){
        // get notes from database
        NotesOpenHelper dbHelper = new NotesOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //dbHelper.onUpgrade(db, 1, 2);
        Cursor cursor = db.query("notes", new String[] {"_id", "NOTE_TITLE", "NOTE_CONTENT"}, "", new String[] {}, null, null, "NOTE_TITLE ASC");

        //write to layout
        ListView listView = (ListView) findViewById(R.id.listaDeNotas);
        listView.setEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> adapter,View arg1, int position, long arg3)
            {
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                intent.putExtra("practical.notes._id", Long.toString(arg3));
                startActivity(intent);
            }
        });
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[] {"NOTE_TITLE"}, new int[] {android.R.id.text1}, 0);
        listView.setAdapter(adapter);
    }

    /** Called when the user taps the "+" button */
    public void addNote(View view){
        // Get title
        EditText editText = (EditText) findViewById(R.id.newNote);
        String title = editText.getText().toString();

        // Create note in database
        NotesOpenHelper dbHelper = new NotesOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NOTE_TITLE", title);
        values.put("NOTE_CONTENT", "");
        long note_id = db.insertOrThrow("notes", null, values);

        // Open new note
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("practical.notes._id", Long.toString(note_id));
        startActivity(intent);
    }

    /** View database */
    public void showDatabase(View view){
        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);
    }
}