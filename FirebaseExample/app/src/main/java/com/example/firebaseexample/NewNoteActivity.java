package com.example.firebaseexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewNoteActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etPriority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        etTitle = findViewById(R.id.edit_text_title);
        etDescription = findViewById(R.id.edit_text_description);
        etPriority = findViewById(R.id.edit_text_priority);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        int priority = Integer.parseInt(etPriority.getText().toString());



        if(title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and a description",
                    Toast.LENGTH_LONG).show();
            return;
        }

        CollectionReference notebookRef = FirebaseFirestore.getInstance()
                .collection("Notebook");
        notebookRef.add(new Note(title, description, priority));
        Toast.makeText(this, "Note added", Toast.LENGTH_LONG).show();
        finish();
    }
}