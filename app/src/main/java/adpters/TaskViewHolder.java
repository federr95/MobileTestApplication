package adpters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mobiletestapplication.R;


public class TaskViewHolder extends RecyclerView.ViewHolder {

    public TextView textTask;

    public TaskViewHolder(View itemView) {

        super(itemView);
        textTask = itemView.findViewById(R.id.itemTask);

    }

}
