package com.thauvi_a.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by thauvi_a on 1/17/18.
 * <p>
 * ******************************
 * * BOOM BITCH GET OUT THE WAY *
 * ******************************
 */

public class OldTaskActivity extends AppCompatActivity {
    private RecyclerView mTaskList;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oldtask_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Finished");
        setSupportActionBar(toolbar);
        mTaskList = findViewById(R.id.task_list);
        mTaskList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Finished");

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


    @Override
    protected void onStart() {
        super.onStart();

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
                holder.setDate("Finished the " + model.getDate());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_old, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_return) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
