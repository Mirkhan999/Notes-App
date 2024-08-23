package com.example.notes;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {
    private EditText titleEditText, contentEditText;
    private Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_dialog);
        titleEditText = findViewById(R.id.noteTitle);
        contentEditText = findViewById(R.id.noteContent);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v -> saveNote());
    }
    private void saveNote(){
        final String title = titleEditText.getText().toString().trim();
        final String content = contentEditText.getText().toString().trim();
        final long timestamp = System.currentTimeMillis();

        if (!title.isEmpty() && !content.isEmpty()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Note note = new Note();
                        note.setTitle(title);
                        note.setContent(content);
                        note.setTimestamp(timestamp);

                        NoteDatabase.getInstance(getApplicationContext()).noteDao().insert(note);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        });
                    } catch (Exception e) {
                        Log.e("SaveNote", "Error saving note", e);
                    }
                }
            }).start();
        }
    }
}
