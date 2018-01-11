package com.thauvi_a.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by thauvi_a on 1/10/18.
 * <p>
 * ******************************
 * * BOOM BITCH GET OUT THE WAY *
 * ******************************
 */

public class AddTaskActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    DatabaseReference myRef;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        editText = findViewById(R.id.desc);
        String desc = editText.getText().toString();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            myRef = db.getInstance().getReference().child("Task");
            DatabaseReference newTask = myRef.push();
            newTask.child("name").setValue(desc);
            AddTaskActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
