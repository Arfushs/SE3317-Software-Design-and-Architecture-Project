package controller;

import model.Message;
import model.BasicMessage;
import model.BirthdayMessageDecorator;
import model.NotificationDecorator;
import model.Task;
import model.TaskModel;
import view.TaskView;

import javax.swing.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskController {
    private final TaskModel model;
    private final TaskView view;
    private final String userBirthday;

    public TaskController(TaskModel model, TaskView view) {
        this.model = model;
        this.view = view;
        this.userBirthday = askUserBirthday();

        // Başlangıçta görev listesini güncelle
        view.updateTaskList(model.getAllTasks());

        // Action Listener'ları ekle
        addListeners();

        // Timer'ı başlat
        startTimer();
    }

    private String askUserBirthday() {
        String birthday = JOptionPane.showInputDialog(
                null,
                "Lütfen doğum gününüzü girin (MM-dd formatında):",
                "Doğum Günü Ayarı",
                JOptionPane.QUESTION_MESSAGE
        );

        // Doğum günü kontrolü
        if (birthday == null || !birthday.matches("\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(
                    null,
                    "Geçersiz format! Varsayılan olarak 01-01 ayarlandı.",
                    "Hata",
                    JOptionPane.WARNING_MESSAGE
            );
            return "01-01";
        }

        return birthday;
    }

    private void addListeners() {
        // Görev ekleme
        view.getAddButton().addActionListener(e -> handleAddTask());

        // Görev silme
        view.getDeleteButton().addActionListener(e -> handleDeleteTask());

        // Görev düzenleme
        view.getEditButton().addActionListener(e -> handleEditTask());
    }

    private void startTimer() {
        Timer timer = new Timer(1000, e -> {
            // Gün ve tarih bilgisi
            String currentDay = new SimpleDateFormat("EEEE").format(new java.util.Date());
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());

            // Doğum günü mesajı
            String birthdayMessage = "";
            if (currentDate.endsWith(userBirthday)) {
                birthdayMessage = "Happy Birthday!";
            }

            // Bildirimler
            List<String> notifications = new ArrayList<>();
            for (Task task : model.getAllTasks()) {
                if (isDeadlineApproaching(task)) {
                    notifications.add(task.getTaskName() + " için 1 gün kaldı.");
                }
            }

            // UI'yi güncelle
            view.updateDay(currentDay);
            view.updateDate(currentDate);
            view.updateBirthdayMessage(birthdayMessage);
            view.updateNotifications(notifications);
        });

        timer.start();
    }

    private boolean isDeadlineApproaching(Task task) {
        long currentTime = System.currentTimeMillis();
        long taskDeadline = task.getDeadline().getTime();
        long oneDayMillis = 24 * 60 * 60 * 1000;

        return taskDeadline - currentTime <= oneDayMillis && taskDeadline - currentTime > 0;
    }

    private void handleAddTask() {
        try {
            String taskName = JOptionPane.showInputDialog("Task Name:");
            if (isNullOrEmpty(taskName)) return;

            String description = JOptionPane.showInputDialog("Description:");
            if (isNullOrEmpty(description)) return;

            String category = JOptionPane.showInputDialog("Category:");
            if (isNullOrEmpty(category)) return;

            String deadlineStr = JOptionPane.showInputDialog("Deadline (yyyy-mm-dd):");
            if (isNullOrEmpty(deadlineStr)) return;

            Date deadline = Date.valueOf(deadlineStr);

            Task task = new Task(0, taskName, description, category, deadline);
            model.addTask(task);
            view.updateTaskList(model.getAllTasks());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Hata: Geçerli bir tarih formatı girin (yyyy-mm-dd).", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteTask() {
        int selectedRow = view.getTaskTable().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Lütfen silmek için bir görev seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = model.getAllTasks().get(selectedRow).getId();
        model.deleteTask(id);
        view.updateTaskList(model.getAllTasks());
    }

    private void handleEditTask() {
        int selectedRow = view.getTaskTable().getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Lütfen düzenlemek için bir görev seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Task selectedTask = model.getAllTasks().get(selectedRow);

        try {
            String newTaskName = JOptionPane.showInputDialog("Yeni görev adı:", selectedTask.getTaskName());
            if (isNullOrEmpty(newTaskName)) return;

            String newDescription = JOptionPane.showInputDialog("Yeni açıklama:", selectedTask.getDescription());
            if (isNullOrEmpty(newDescription)) return;

            String newCategory = JOptionPane.showInputDialog("Yeni kategori:", selectedTask.getCategory());
            if (isNullOrEmpty(newCategory)) return;

            String newDeadlineStr = JOptionPane.showInputDialog("Yeni teslim tarihi (yyyy-mm-dd):", selectedTask.getDeadline().toString());
            if (isNullOrEmpty(newDeadlineStr)) return;

            Date newDeadline = Date.valueOf(newDeadlineStr);

            // Görevi güncelle
            selectedTask.setTaskName(newTaskName);
            selectedTask.setDescription(newDescription);
            selectedTask.setCategory(newCategory);
            selectedTask.setDeadline(newDeadline);

            model.updateTask(selectedTask);
            view.updateTaskList(model.getAllTasks());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Hata: Geçerli bir tarih formatı girin (yyyy-mm-dd).", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
