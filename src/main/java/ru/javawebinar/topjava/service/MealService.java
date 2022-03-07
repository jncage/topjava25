package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

@Service
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public List<Meal> getAll(int authUserId) {
        return repository.getAll(authUserId);
    }

    public Meal get(int authUserId, int id) {
        if(!isUserMeal(authUserId, id)) throw new NotFoundException("there is no meal's id for the user");
        return repository.get(authUserId, id);
    }

    public Meal create(int authUserId, Meal meal) {
        return repository.save(authUserId, meal);
    }

    public void delete(int authUserId, int id) {
        if(isUserMeal(authUserId, id))
        repository.delete(authUserId, id);
        else
            throw new NotFoundException("there is no meal's id for the user");
    }

    public void update(int authUserId, Meal meal) {
        if(isUserMeal(authUserId, meal.getId()))
        ValidationUtil.checkNotFoundWithId(repository.save(authUserId, meal), meal.getId());
        else
            throw new NotFoundException("there is no meal's id for the user");
    }
    private boolean isUserMeal(int userId, int id) {
        return repository.getAll(userId).stream()
                .anyMatch(meal -> meal.getId() == id);
    }

    public List<MealTo> getAllBetween(int userId, String startDate, String endDate, String startTime, String endTime) {
        return repository.getAllBetween(userId, startDate, endDate, startTime, endTime);
    }
}