package Main;

import controller.TaskController;
import model.TaskModel;
import view.TaskView;


public class Demo {

    public static void main(String[] args) {
        TaskModel model = new TaskModel();
        TaskView view = new TaskView(model);

        model.registerObserver(view); // Register the view as an observer
        new TaskController(model, view);

        view.setVisible(true);
    }
}
