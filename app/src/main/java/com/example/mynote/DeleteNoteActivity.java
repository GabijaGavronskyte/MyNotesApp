package com.example.mynote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DeleteNoteActivity extends AppCompatActivity {

    private Spinner spinner;
    private SharedPreferences sharedPreferences;
    private ArrayList<String> notesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        spinner = findViewById(R.id.Notes_spinner);
        Button buttonDelete = findViewById(R.id.btn_delete);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        Set<String> notesSet = sharedPreferences.getStringSet("notes", new HashSet<String>());
        notesList.addAll(notesSet);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, notesList);
        spinner.setAdapter(adapter);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();
            }
        });
    }
    private void deleteNote() {
        String selectedNote = spinner.getSelectedItem().toString();
        notesList.remove(selectedNote);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("notes", new HashSet<>(notesList));
        editor.apply();

        Toast.makeText(this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
    public static Intent createIntent(Context context) {
        return new Intent(context, DeleteNoteActivity.class);
    }
}
