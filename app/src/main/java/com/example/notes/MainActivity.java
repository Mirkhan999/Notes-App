package com.example.notes;

import static com.example.notes.R.layout.activity_main;

import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton fab = findViewById(R.id.fab);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(noteAdapter);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
        });
        loadNotes();
    }




    private void loadNotes() {
        class LoadNotesTask extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                NoteDatabase db = NoteDatabase.getInstance(MainActivity.this);
                return db.noteDao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                noteAdapter = new NoteAdapter(MainActivity.this, notes);
                recyclerView.setAdapter(noteAdapter);
            }
        }

        new LoadNotesTask().execute();
    }


}