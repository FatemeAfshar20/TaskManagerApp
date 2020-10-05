package com.example.taskmanagerapp.Repository;

import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

public interface IRepository<E> {
    List<E> getList();
    E get(UUID uuid);
    void delete(E element);
    void insert(E element);
    void update(E element);
}
