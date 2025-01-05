package model;

import java.util.List;

public interface TaskStrategy {
    List<Task> execute(List<Task> tasks);
}
