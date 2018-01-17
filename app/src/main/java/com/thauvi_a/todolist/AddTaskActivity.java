package com.thauvi_a.todolist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by thauvi_a on 1/10/18.
 * <p>
 * ******************************
 * * BOOM BITCH GET OUT THE WAY *
 * ******************************
 */

public class AddTaskActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    private TextView date;
    private TextView time;
    DatabaseReference myRef;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Adding task");
        setSupportActionBar(toolbar);

    }

    public void retrieveFieldsValue() {
        date = findViewById(R.id.peeked_date);
        time = findViewById(R.id.peeked_time);
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
            TextView textView = getActivity().findViewById(R.id.peeked_time);
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
            TextView textView = getActivity().findViewById(R.id.peeked_date);
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
        getMenuInflater().inflate(R.menu.menu_addtask, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        retrieveFieldsValue();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        int id = item.getItemId();
        editText = findViewById(R.id.desc);
        Date dueDate;
        String name = editText.getText().toString();
        try {
            dueDate = dateFormatter.parse(date.getText().toString() + " " + time.getText().toString());
        }
        catch (ParseException e)
        {
            return false;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            myRef = db.getInstance().getReference().child("Tasks");
            DatabaseReference newTask = myRef.push();
            newTask.child("date").setValue(dueDate.toString());
            newTask.child("name").setValue(name);
            AddTaskActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
