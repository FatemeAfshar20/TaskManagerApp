package com.example.taskmanagerapp.Repository;

import java.util.UUID;

public interface IRepository<E> {

    void insert(E e);
    void delete(E e);
    void update(E e);
    E get(UUID uuid);

}
