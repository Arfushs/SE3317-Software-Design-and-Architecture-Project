package model;

import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class SortByDeadlineStrategy implements TaskStrategy {
    @Override
    public List<Task> execute(List<Task> tasks) {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getDeadline))
                .collect(Collectors.toList());
    }
}
