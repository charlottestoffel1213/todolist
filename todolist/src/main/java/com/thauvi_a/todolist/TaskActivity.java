package com.thauvi_a.todolist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


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
    private EditText name;
    private TextView date;
    private TextView time;
    private EditText desc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Task");
        setSupportActionBar(toolbar);

        key = getIntent().getExtras().getString("key");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tasks");
        name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);
        date = findViewById(R.id.date);


        mDatabase.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    name.setText(dataSnapshot.child("name").getValue().toString());
                    desc.setText(dataSnapshot.child("desc").getValue().toString());
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

    public void retrieveFieldsValue() {
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        name = findViewById(R.id.name);
        desc = findViewById(R.id.desc);
    }


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            TextView textView = getActivity().findViewById(R.id.time);
            StringBuilder formattedTime = new StringBuilder().append(hourOfDay)
                    .append(":")
                    .append(minute < 10 ? "0" + minute : minute);
            textView.setText(formattedTime);
        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);

        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView textView = getActivity().findViewById(R.id.date);
            StringBuilder formattedDate = new StringBuilder().append(year)
                    .append("-")
                    .append(month + 1 < 10 ? "0" + (month + 1) : month + 1)
                    .append("-")
                    .append(day + 1 < 10 ? "0" + (day + 1) : day);
            textView.setText(formattedDate);
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
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
        retrieveFieldsValue();
        //noinspection SimplifiableIfStatement

        if (id == R.id.done) {
            mDatabase.child(key).removeValue();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Finished");
            DatabaseReference oldTask = mDatabase.push();
            oldTask.child("date").setValue(Calendar.getInstance().getTime().toString());
            oldTask.child("name").setValue(name.getText().toString());
            oldTask.child("desc").setValue(desc.getText().toString());
            TaskActivity.this.finish();
            return true;
        }

        else if (id == R.id.delete) {
            mDatabase.child(key).removeValue();
            this.finish();
        }

        else if (id == R.id.save) {
            boolean dateIsEmpty = false;
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date dueDate;
            try {
                dueDate = dateFormatter.parse(date.getText().toString() + " " + time.getText().toString());
            } catch (ParseException e) {
                try {
                    dueDate = dateFormatter.parse("2016-01-05 05:05");
                    dateIsEmpty = true;
                } catch (ParseException f) {
                    f.getMessage();
                    return false;
                }
            }
            mDatabase.child(key).child("name").setValue(name.getText().toString());
            mDatabase.child(key).child("desc").setValue(desc.getText().toString());
            if (!dateIsEmpty)
                mDatabase.child(key).child("date").setValue(dueDate.toString());
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
