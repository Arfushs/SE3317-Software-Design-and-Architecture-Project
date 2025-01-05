package model;

import java.util.Date;

public class Task {
    private int id;
    private String taskName;
    private String description;
    private String category;
    private Date deadline;

    // Constructor
    public Task(int id, String taskName, String description, String category, Date deadline) {
        this.id = id;
        this.taskName = taskName;
        this.description = description;
        this.category = category;
        this.deadline = deadline;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
