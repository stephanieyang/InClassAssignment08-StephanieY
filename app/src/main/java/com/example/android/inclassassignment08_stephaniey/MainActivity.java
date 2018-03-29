package com.example.android.inclassassignment08_stephaniey;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText keyBox;
    EditText valBox;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize EditText member variables
        keyBox = (EditText) findViewById(R.id.entry_key);
        valBox = (EditText) findViewById(R.id.entry_value);

    }

    public void writeToDatabase(View view) {
        // get values
        String keyToWrite = keyBox.getText().toString();
        String valToWrite = valBox.getText().toString();

        // write to database
        DatabaseReference myRef = database.getReference(keyBox.getText().toString());
        myRef.setValue(valBox.getText().toString());
    }

    public void readFromDatabase(View view) {
        String keyToRead = keyBox.getText().toString();

        // read from database according to key
        DatabaseReference myRef = database.getReference(keyToRead);
        // Read from the database
        final Context currentContext = this;
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // if key exists, show
                if (dataSnapshot.exists()) {
                    String readValue = dataSnapshot.getValue(String.class);
                    valBox.setText(readValue);
                } else { // else, show toast
                    Toast.makeText(currentContext, R.string.no_key_text, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("sy", "Failed to read value.", error.toException());
                Toast.makeText(currentContext, R.string.failure_text, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
