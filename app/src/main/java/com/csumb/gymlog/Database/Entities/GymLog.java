package com.csumb.gymlog.Database.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.csumb.gymlog.Database.GymLogDatabase;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Entity(tableName = GymLogDatabase.gymLogTable)
public class GymLog {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    private int id;
    private String exercise;
    private double weight;
    private int reps;
    private LocalDate date;

    public GymLog(String exercise, double weight, int reps) {
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        date = LocalDate.now();
    }

    @NotNull
    public int getId() {
        return id;
    }

    public void setId(@NotNull int id) {
        this.id = id;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
