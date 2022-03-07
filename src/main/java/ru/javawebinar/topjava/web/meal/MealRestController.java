package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

@Controller
public class MealRestController {
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }
    public List<MealTo> getAll(){
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
    public Meal get(int id){
        return service.get(SecurityUtil.authUserId(), id);
    }
    public Meal create(Meal meal){
        ValidationUtil.checkNew(meal);
        return service.create(SecurityUtil.authUserId(), meal);
    }
    public void delete(int id){
        service.delete(SecurityUtil.authUserId(), id);
    }
    public void update(Meal meal, int id){
        ValidationUtil.assureIdConsistent(meal, id);
        service.update(SecurityUtil.authUserId(), meal);
    }

    public List<MealTo> getAllBetween(String startDate, String endDate, String startTime, String endTime) {



        return service.getAllBetween(SecurityUtil.authUserId(), startDate, endDate, startTime, endTime);
    }
}