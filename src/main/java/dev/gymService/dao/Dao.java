package dev.gymService.dao;

import java.util.List;
import java.util.OptionalLong;
import java.util.function.ToLongFunction;

public abstract class Dao<T> {
    protected abstract List<T> getAll();

    protected Long generateId(ToLongFunction<T> input) {
        OptionalLong maxNumber = getAll().stream().mapToLong(input).max();
        return maxNumber.orElse(0) + 1;
    }
}
