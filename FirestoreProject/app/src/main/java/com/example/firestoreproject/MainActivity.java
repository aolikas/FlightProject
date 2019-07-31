package com.example.firestoreproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;


import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText etTitle;
    private EditText etDescription;
    private EditText etPriority;
    private EditText etTags;
    private TextView textViewData;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Notebook");
    private DocumentSnapshot lastResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.edit_title);
        etDescription = findViewById(R.id.edit_description);
        etPriority = findViewById(R.id.edit_priority);
        etTags = findViewById(R.id.edit_tags);
        textViewData = findViewById(R.id.text_view_data);

        updateNestedValue();
    }


    public void addNote(View v) {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();

        if (etPriority.length() == 0) {
            etPriority.setText("0");
        }

        int priority = Integer.parseInt(etPriority.getText().toString());

        String tagInput = etTags.getText().toString();
        String[] tagArray = tagInput.split("\\s*,\\s*");
        Map<String, Boolean>  tags = new HashMap<>();

        for(String tag : tagArray) {
            tags.put(tag, true);
        }

        Note note = new Note(title, description, priority, tags);

        notebookRef.add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(MainActivity.this, "Note  saved", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, e.toString());
            }
        });
    }


    public void loadNotes(View v) {

        notebookRef.whereEqualTo("tags.tag2", true).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Note note = documentSnapshot.toObject(Note.class);
                            note.setDocumentId(documentSnapshot.getId());

                            String documentId = note.getDocumentId();

                            data += "id: " + documentId;

                            for (String tag : note.getTags().keySet()) {
                                data += "\n-" + tag;
                            }
                            data += "\n\n";

                        }

                        textViewData.setText(data);
                    }
                });
        //-----------------------------------------------------------------

        //notebookRef.orderBy("priority")
        //      .startAt(3)
        //    .get()
        //  .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        //    @Override
        //  public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
        //    String data = "";

        //  for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
        //    Note note = documentSnapshot.toObject(Note.class);
        //  note.setDocumentId(documentSnapshot.getId());

        //String documentId = note.getDocumentId();
        //          String title = note.getTitle();
        //        String description = note.getDescription();
        //      int priority = note.getPriority();
        //    data += "ID: " + documentId + "\n" +
        //          "Title: " + title + "\n" +
        //        "Description: " + description + "\n" +
        //      "priority: " + priority + "\n\n";
        //               }
        //             textViewData.setText(data);
        //       }
        // });


        //--------------------------------------

//        Task task1 = notebookRef.whereLessThan("priority", 2)
        //              .orderBy("priority")
        //            .get();

//        Task task2 = notebookRef.whereGreaterThan("priority", 2)
        //              .orderBy("priority").limit(3)
        //            .get();

        //  Task<List<QuerySnapshot>> allTask = Tasks.whenAllSuccess(task1, task2);

        //allTask.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
        //  @Override
        //public void onSuccess(List<QuerySnapshot> querySnapshots) {
        //  String data = "";

        //for (QuerySnapshot queryDocumentSnapshots : querySnapshots) {


        //  for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
        //    Note note = documentSnapshot.toObject(Note.class);
        //  note.setDocumentId(documentSnapshot.getId());

        //String documentId = note.getDocumentId();
        //     String title = note.getTitle();
        //   String description = note.getDescription();
        // int priority = note.getPriority();
        //      data += "ID: " + documentId + "\n" +
        //            "Title: " + title + "\n" +
        //          "Description: " + description + "\n" +
        //        "priority: " + priority + "\n\n";
        // }
        // textViewData.setText(data);

        // }
        //    }
        //  });

        //----------------------------------------------------

        //notebookRef.whereGreaterThanOrEqualTo("priority", 2)
        //      .whereEqualTo("title", "Aa")
        //    .orderBy("priority", Query.Direction.ASCENDING)
        //  .get()
        //.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        //  @Override
        //public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
        //  String data = "";
        //for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
        //  Note note = documentSnapshot.toObject(Note.class);
        //note.setDocumentId(documentSnapshot.getId());

        //String documentId = note.getDocumentId();
        //   String title = note.getTitle();
        // String description = note.getDescription();
        //      int priority = note.getPriority();

        //    data += "ID: " +  documentId + "\n" +
        //          "Title: " + title + "\n" +
        //        "Description: " + description + "\n" +
        //      "priority: " + priority + "\n\n";
        // }
        // textViewData.setText(data);
        // }
        // }).addOnFailureListener(new OnFailureListener() {
        // @Override
        //  public void onFailure(@NonNull Exception e) {
        //    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
        //  Log.d(TAG, e.toString());
        // }
        // });
    }

    private void updateNestedValue() {
        notebookRef.document(
                "kG3JmmEA7MMMd1jGTCrS")
                .update("tags.tag2", false);
    }
}
