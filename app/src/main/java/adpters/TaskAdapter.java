package adpters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobiletestapplication.R;
import com.example.mobiletestapplication.TaskActivity;

import java.util.ArrayList;

import model.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private ArrayList<Task> tasks;

    public TaskAdapter(ArrayList<Task> tasks) {
        if (tasks == null || tasks.isEmpty())
            this.tasks = new ArrayList<>();
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup,
            int i) {

        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_task,
                        viewGroup,
                        false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull TaskViewHolder taskViewHolder,
            int position) {

        Task task = tasks.get(position);
        Log.d("task object", task.toString());

        taskViewHolder.textTask.setText(task.getTitle());

        taskViewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), TaskActivity.class);
            intent.putExtra("task", task);   // Task deve implementare Parcelable
            v.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        if (tasks == null || tasks.isEmpty())
            return 0;
        return tasks.size();
    }
}
