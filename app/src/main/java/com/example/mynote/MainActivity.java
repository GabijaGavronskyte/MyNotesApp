package com.example.mynote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import android.content.Context;


public class MainActivity extends AppCompatActivity {

    private static final String PREF_NAME = "MyAppPrefs";
    private static final String NOTES_KEY = "notes";

    private ArrayList<String> notesList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        ListView listView = findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        listView.setAdapter(adapter);
        loadNotes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();

        Set<String> notesSet = sharedPreferences.getStringSet("notes", new HashSet<String>());
        notesList.clear();
        notesList.addAll(notesSet);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_note_activity) {
            startActivity(new Intent(this, AddNoteActivity.class));
            return true;
        } else if (id == R.id.delete_note_activity) {
            startActivity(new Intent(this, DeleteNoteActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void loadNotes() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        Set<String> savedNotesSet = sharedPreferences.getStringSet(NOTES_KEY, new HashSet<String>());
        notesList.clear();
        notesList.addAll(savedNotesSet);
        adapter.notifyDataSetChanged();
    }
}
