package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {

    private String title;

    private String description;

    private boolean completed;

    private int priority;

    public Task(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.completed = false;
        this.priority = priority;
    }

    public Task(Parcel in) {
        title = in.readString();
        description = in.readString();
        priority = in.readInt();
        completed = in.readByte() != 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(priority);
        dest.writeByte((byte) ((completed == true) ? 1 : 0));
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", priority=" + priority +
                '}';
    }
}
