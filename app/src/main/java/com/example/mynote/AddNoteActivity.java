package com.example.mynote;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashSet;
import java.util.Set;

public class AddNoteActivity extends AppCompatActivity {

    private EditText Note_name;
    private EditText Note_text;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Note_name = findViewById(R.id.Note_name);
        Note_text = findViewById(R.id.Note_text);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        Button buttonSave = findViewById(R.id.btn_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String name = Note_name.getText().toString().trim();
        String content = Note_text.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(content)) {
            Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String newNote = name + ": " + content;

        // Retrieve the existing set of notes from SharedPreferences
        Set<String> notesSet = sharedPreferences.getStringSet("notes", new HashSet<String>());

        // Add the new note to the existing set of notes
        notesSet.add(newNote);

        // Update the notes set in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("notes", notesSet);
        editor.apply();

        Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show();

        finish();
    }
}
