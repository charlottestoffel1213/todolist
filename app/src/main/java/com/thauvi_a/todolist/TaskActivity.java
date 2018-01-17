package com.thauvi_a.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by thauvi_a on 1/15/18.
 * <p>
 * ******************************
 * * BOOM BITCH GET OUT THE WAY *
 * ******************************
 */

public class TaskActivity extends AppCompatActivity {
    private String key;
    private DatabaseReference mDatabase;
    private TextView name;
    private TextView date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        key = getIntent().getExtras().getString("key");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tasks");
        name = findViewById(R.id.name);
        date = findViewById(R.id.date);

        mDatabase.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    name.setText(dataSnapshot.child("name").getValue().toString());
                    date.setText(dataSnapshot.child("date").getValue().toString());
                }
                catch (NullPointerException e) {
                    e.getMessage();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.done) {
            mDatabase.child(key).removeValue();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Finished");
            DatabaseReference oldTask = mDatabase.push();
            oldTask.child("date").setValue(date.getText());
            oldTask.child("name").setValue(name.getText());
            TaskActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
