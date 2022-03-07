package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        final int[] id = {0};
        MealsUtil.meals.forEach(meal -> {

            save(++id[0] % 2 + 1, meal);
        });
    }


    @Override
    public Meal save(int userId, Meal meal) {

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            Map<Integer, Meal> mealMap = repository.get(userId);
            if (mealMap == null) {
                mealMap = new ConcurrentHashMap<>();
            }
            mealMap.put(meal.getId(), meal);
            repository.put(userId, mealMap);
            return meal;
        }

        return repository.get(userId).put(meal.getId(), meal);


    }

    @Override
    public boolean delete(int userId, int id) {

        return repository.get(userId).remove(id) != null;

    }

    @Override
    public Meal get(int userId, int id) {

        return repository.get(userId).get(id);

    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.get(userId).values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime))
                .collect(Collectors.toList());
    }

    @Override
    public List<MealTo> getAllBetween(int userId, String startDate, String endDate, String startTime, String endTime) {
        LocalDate sDate = startDate == null || startDate.isEmpty() ? LocalDate.MIN : LocalDate.parse(startDate);
        LocalDate eDate = endDate == null || endDate.isEmpty() ? LocalDate.MAX : LocalDate.parse(endDate).plusDays(1);
        LocalTime sTime = startTime == null || startTime.isEmpty() ? LocalTime.MIN : LocalTime.parse(startTime);
        LocalTime eTime = endTime == null || endTime.isEmpty() ? LocalTime.MAX : LocalTime.parse(endTime);
        List<MealTo> betweenDates = MealsUtil.getFilteredTos(getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY, sDate, eDate);
        return betweenDates.stream()
                .filter(mealTo -> DateTimeUtil.isBetweenHalfOpen(mealTo.getDateTime().toLocalTime(), sTime, eTime))
                .collect(Collectors.toList());
    }
}

