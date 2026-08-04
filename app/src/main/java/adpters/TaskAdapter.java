package adpters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobiletestapplication.R;

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

        taskViewHolder.textTask.setText(task.getTitle());

    }

    @Override
    public int getItemCount() {
        if (tasks == null || tasks.isEmpty())
            return 0;
        return tasks.size();
    }
}
