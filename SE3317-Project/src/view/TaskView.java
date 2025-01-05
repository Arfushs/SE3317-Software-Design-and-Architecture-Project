package view;

import model.Notification;
import model.Observer;
import model.Task;
import Main.Demo;
import model.TaskModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TaskView extends JFrame implements Observer {
    private TaskModel model;
    private JButton addButton, editButton, deleteButton;
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private JLabel dayLabel, dateLabel, birthdayMessageLabel;
    private JList<String> notificationList;
    private DefaultListModel<String> notificationListModel;
    private JButton optionsButton;


    public TaskView(TaskModel model) {
        this.model = model;
        setTitle("Task Planner");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Gün, tarih ve doğum günü mesajı için panel
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        dayLabel = new JLabel("Day: ");
        dateLabel = new JLabel("Date: ");
        birthdayMessageLabel = new JLabel("Birthday Celebration Message: ");
        topPanel.add(dayLabel);
        topPanel.add(dateLabel);
        topPanel.add(birthdayMessageLabel);

        // Bildirimler paneli
        notificationListModel = new DefaultListModel<>();
        notificationList = new JList<>(notificationListModel);
        JPanel notificationPanel = new JPanel(new BorderLayout());
        notificationPanel.add(new JLabel("Notifications:"), BorderLayout.NORTH);
        notificationPanel.add(new JScrollPane(notificationList), BorderLayout.CENTER);

        // Görev listesi ve butonlar
        String[] columnNames = {"Name", "Description", "Category", "Deadline"};
        tableModel = new DefaultTableModel(columnNames, 0);
        taskTable = new JTable(tableModel);
        addButton = new JButton("Add Task"); // "+" simgesi
        deleteButton = new JButton("Delete Task"); // Çöp simgesi
        editButton = new JButton("Edit Task"); // Kalem simgesi

        JPanel taskPanel = new JPanel(new BorderLayout());
        JPanel taskButtonPanel = new JPanel();
        taskButtonPanel.add(addButton);
        taskButtonPanel.add(deleteButton);
        taskButtonPanel.add(editButton);
        taskPanel.add(taskButtonPanel, BorderLayout.NORTH);
        taskPanel.add(new JScrollPane(taskTable), BorderLayout.CENTER);

        // Ana düzen
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(notificationPanel, BorderLayout.WEST);
        mainPanel.add(taskPanel, BorderLayout.CENTER);

        optionsButton = new JButton("Options");
        taskButtonPanel.add(optionsButton);

        add(mainPanel);
    }

    public void updateTaskList(List<Task> tasks) {
        tableModel.setRowCount(0); // Mevcut verileri temizle
        for (Task task : tasks) {
            tableModel.addRow(new Object[]{
                    task.getTaskName(),
                    task.getDescription(),
                    task.getCategory(),
                    task.getDeadline()
            });
        }
    }

    public void updateDay(String day) {
        dayLabel.setText("Day: " + day);
    }

    public void updateDate(String date) {
        dateLabel.setText("Date: " + date);
    }

    public void updateBirthdayMessage(String message) {
        birthdayMessageLabel.setText("Birthday Celebration Message: " + message);
    }

    public void updateNotifications(Notification compositeNotification) {
        notificationListModel.clear();
        String[] messages = compositeNotification.getMessage().split("\n");
        for (String message : messages) {
            notificationListModel.addElement(message);
        }
    }


    @Override
    public void update() {
        System.out.println("Observer triggered: Task list is being updated.");
        updateTaskList(model.getAllTasks());
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JTable getTaskTable() {
        return taskTable;
    }

    public JButton getOptionsButton() {
        return optionsButton;
    }
}
