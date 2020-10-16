package com.example.firstgame;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener {
    String[] notes = new String[500];
    int[] notesID = new int[500];
    String[] date = new String[500];
    DBHelper dbHelper;
    int cnt = 0;
    final String myLog = "Mylog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        // kursordan foydalanib tablitsadagoi har bir elementni koramiz
        Cursor c = db.query("noteTable", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int noteColIndex = c.getColumnIndex("note");
            int dateColIndex = c.getColumnIndex("date");
            do {
                Log.d(myLog,
                        "ID = " + c.getInt(idColIndex) +
                                ", note =" + c.getString(noteColIndex) +
                                ", date =" + c.getString(dateColIndex)
                );
                notesID[cnt] = c.getInt(idColIndex);
                notes[cnt] = c.getString(noteColIndex);
                date[cnt] = c.getString(dateColIndex);
                cnt++;
            } while (c.moveToNext());

        } else {
            Log.d(myLog, "0 rows");
        }
        c.close();

        LinearLayout linlayout = (LinearLayout) findViewById(R.id.linlayout);

        final LayoutInflater ltInflater = getLayoutInflater();

        if (cnt != 0)
            for (int i = 0; i < cnt; i++) {
                final int itemID = i;
                View my_item = ltInflater.inflate(R.layout.item_note, linlayout, false);

                TextView textNote = (TextView) my_item.findViewById(R.id.tvNote);
                TextView textDate = (TextView) my_item.findViewById(R.id.tvDate);
                Log.d(myLog, notes[i]);
                textNote.setText(notes[i]);
                textDate.setText(date[i]);
                linlayout.addView(my_item);

                // 02.09.2020
                final Toast toast = Toast.makeText(getApplicationContext(), "salom " + notes[i], Toast.LENGTH_SHORT);
                // ko'p vaqt bosilganda chiqadigan menyu
                registerForContextMenu(my_item);

                // element bir marta bosilsa bo'ladigan hodisa
                my_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toast.show();
                    }
                });
                my_item.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                        menu.add(0, 1, 0, "Change the note");
                        menu.add(0, 2, 0, "Delete the note");
                        MenuItem.OnMenuItemClickListener listener = new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                onContextItemSelected(menuItem);
                                int menuIndex = menuItem.getItemId();
                                switch (menuIndex) {
                                    case 1:
                                        Intent intent = new Intent(NoteActivity.this, EditNote.class);
                                        intent.putExtra("noteText", notes[itemID]);
                                        intent.putExtra("noteId", itemID);
                                        startActivity(intent);

                                        break;

                                    case 2:
                                        final Toast toast2 = Toast.makeText(getApplicationContext(), "try to delete", Toast.LENGTH_SHORT);
                                        toast2.show();
                                        int delCount = db.delete("noteTable", "id =" + notesID[itemID], null);
                                        Log.d(myLog, "deleted rows count = " + delCount);
                                        finish();
                                        startActivity(getIntent());
                                        break;
                                }
                                return true;
                            }
                        };
                        for (int i = 0, n = menu.size(); i < n; i++)
                            menu.getItem(i).setOnMenuItemClickListener(listener);
                    }
                });

            }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteActivity.this, AddNote.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }
}