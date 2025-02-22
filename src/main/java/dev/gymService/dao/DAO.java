package dev.gymService.dao;

import java.util.Optional;

public interface DAO<T, Id>{
   T getById(Id Id);
    T save(T t);
    void delete(T t);
}
