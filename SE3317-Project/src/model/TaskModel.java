package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskModel implements  Subject {
    private Connection connection;
    private List<Observer> observers;
    // Constructor: Connect to the database
    public TaskModel() {
        this.observers = new ArrayList<>();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/task_planner", "root", "C856500n.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch all tasks
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Task task = new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("task_name"),
                        resultSet.getString("description"),
                        resultSet.getString("category"),
                        resultSet.getDate("deadline")
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    // Add a task
    public void addTask(Task task) {
        String query = "INSERT INTO tasks (task_name, description, category, deadline) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, task.getTaskName());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getCategory());
            statement.setDate(4, new java.sql.Date(task.getDeadline().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update a task
    public void updateTask(Task task) {
        String query = "UPDATE tasks SET task_name = ?, description = ?, category = ?, deadline = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, task.getTaskName());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getCategory());
            statement.setDate(4, new java.sql.Date(task.getDeadline().getTime()));
            statement.setInt(5, task.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a task
    public void deleteTask(int id) {
        String query = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }
}
