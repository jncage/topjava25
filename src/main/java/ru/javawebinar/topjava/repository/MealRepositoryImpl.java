package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepositoryImpl implements MealRepository {
    private static final AtomicInteger counter = new AtomicInteger(0);
    private final static Map<Integer, Meal> repo = new ConcurrentHashMap<>();

    {
        MealsUtil.meals.forEach(this::create);

    }

    @Override
    public void create(Meal meal) {
        meal.setId(counter.incrementAndGet());
        repo.putIfAbsent(meal.getId(), meal);

    }

    @Override
    public void delete(int mealId) {
        repo.remove(mealId);
    }

    @Override
    public void update(Meal meal) {
        repo.computeIfPresent(meal.getId(), (k, v) -> meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(repo.values());
    }

    @Override
    public Meal getById(int mealId) {
        return repo.get(mealId);
    }
}
