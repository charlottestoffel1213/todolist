package com.thauvi_a.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mTaskList;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTaskList = findViewById(R.id.task_list);
        //mTaskList.setHasFixedSize(true);
        mTaskList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tasks");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

    }

    private static class TaskViewHolder extends RecyclerView.ViewHolder{
        private TaskViewHolder(View itemView) {
            super(itemView);
            View mView = itemView;
        }
        private void setName(String name)
        {
            TextView task_name = itemView.findViewById(R.id.task_name);
            task_name.setText(name);

        }

        private void setDate(String date)
        {
            TextView task_date = itemView.findViewById(R.id.task_date);
            task_date.setText(date);
        }
    }



/*
    public FirebaseRecyclerAdapter<Task, TaskViewHolder> getArray()
    {
        return this.array;
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("POS", "onStart: ");

        FirebaseRecyclerOptions<Task> options =
                new FirebaseRecyclerOptions.Builder<Task>()
                        .setQuery(mDatabase, Task.class)
                        .build();

        final FirebaseRecyclerAdapter<Task, TaskViewHolder> array = new FirebaseRecyclerAdapter<Task, TaskViewHolder>(options) {

            @Override
            public int getItemCount() {
                return super.getItemCount();
            }

            @Override
            protected void onBindViewHolder(TaskViewHolder holder, int position, Task model) {
                holder.setName(model.getName());
                holder.setDate(model.getDate());

            }

            @Override
            public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row,
                        parent, false);
                return new TaskViewHolder(view);
            }
        };

        array.startListening();
        array.notifyDataSetChanged();
        mTaskList.setAdapter(array);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //array.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
