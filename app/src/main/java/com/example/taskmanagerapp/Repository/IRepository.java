package com.example.taskmanagerapp.Repository;

import java.util.List;
import java.util.UUID;

public interface IRepository<E> {

    void insert(E e);
    void delete(E e);
    void update(E e,E e2);
    void update(E e);
    E get(UUID uuid);
    E get(E e);
    List<E> getLists();
}
