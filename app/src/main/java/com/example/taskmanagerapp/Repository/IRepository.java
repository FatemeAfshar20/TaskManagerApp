package com.example.taskmanagerapp.Repository;

public interface IRepository<E> {

    void insert(E e);
    void delete(E e);
    void update(E e);
    E get(E e);

}
