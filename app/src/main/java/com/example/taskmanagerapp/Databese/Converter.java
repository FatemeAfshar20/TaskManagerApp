package com.example.taskmanagerapp.Databese;

import androidx.room.TypeConverter;

import com.example.taskmanagerapp.Model.Task.TaskState;

import java.util.Date;
import java.util.UUID;

public class Converter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static UUID strToUUID(String uuid) {
        return UUID.fromString(uuid);
    }

    @TypeConverter
    public static String uuidToStr(UUID uuid) {
        return uuid.toString();
    }

    @TypeConverter
    public static String taskStateToStr(TaskState taskState) {
        return taskState.toString();
    }

    @TypeConverter
    public static TaskState strToTaskState(String str) {
        switch (str) {
            case "TODO":
                return TaskState.TODO;
            case "DONE":
                return TaskState.DONE;
            case "DOING":
                return TaskState.DOING;
            default:
                return null;
        }
    }
}
