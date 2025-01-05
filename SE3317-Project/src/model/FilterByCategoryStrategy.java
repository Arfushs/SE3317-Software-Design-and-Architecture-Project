package model;

import java.util.List;
import java.util.stream.Collectors;

public class FilterByCategoryStrategy implements TaskStrategy {
    private final String category;

    public FilterByCategoryStrategy(String category) {
        this.category = category;
    }

    @Override
    public List<Task> execute(List<Task> tasks) {
        return tasks.stream()
                .filter(task -> task.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
}
