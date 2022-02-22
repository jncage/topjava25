package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    void create(Meal meal);
    void delete(int mealId);
    void update(Meal meal);
    List<Meal> getAll();
    Meal getById(int mealId);
}
