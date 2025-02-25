package dev.gymService.dao;

import java.util.Optional;

public interface Dao<T, Id>{
   T getById(Long Id);
    T save(T t);
    void delete(T t);
}
