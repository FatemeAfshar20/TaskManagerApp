package com.example.taskmanagerapp.Databese.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanagerapp.Model.User.User;

import java.util.List;
import java.util.UUID;

@Dao
public interface UserTableDAO {

    @Query(value = "SELECT * FROM User")
    List<User> getList();
    @Query(value = "SELECT * FROM User WHERE uuid=:uuid")
    User get(UUID uuid);
    @Query(value = "SELECT * FROM User WHERE username=:username")
    User get(String username);
    @Delete
    void delete(User user);
    @Insert
    void insert(User user);
    @Update
    void update(User user);
}
