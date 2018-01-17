package com.thauvi_a.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

        final FirebaseRecyclerAdapter<Task, OldTaskActivity.TaskViewHolder> array = new FirebaseRecyclerAdapter<Task, MainActivity.TaskViewHolder>(options) {

            @Override
            public int getItemCount() {
                return super.getItemCount();
            }

            @Override
            protected void onBindViewHolder(OldTaskActivity.TaskViewHolder holder, int position, Task model) {
                holder.setName(model.getName());
                holder.setDate(model.getDate());

                final String key = getRef(position).getKey();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(OldTaskActivity.this, TaskActivity.class);
                        intent.putExtra("key", key);
                        startActivity(intent);

                    }
                });
            }

            @Override
            public OldTaskActivity.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row,
                        parent, false);
                return new OldTaskActivity.TaskViewHolder(view);
            }
        };

        array.startListening();
        array.notifyDataSetChanged();
        mTaskList.setAdapter(array);
    }
}
