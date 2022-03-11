package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(value = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }
    @Autowired
    MealService service;
    @Test
public void duplicateDateTimeCreate(){
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Duplicate", 1000), USER_ID));

}
    @Test
    public void deleteOther() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_LUNCH_ID, ADMIN_ID));
    }
    @Test
    public void updateOther() {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, USER_ID));
    }
    @Test
    public void getOther() {
        assertThrows(NotFoundException.class, () -> service.get(USER_LUNCH_ID, ADMIN_ID));
    }

    @Test
    public void get() {
        Meal meal = service.get(USER_LUNCH_ID, USER_ID);
        assertThat(meal).usingRecursiveComparison().isEqualTo(user_lunch);

    }

    @Test
    public void delete() {
        service.delete(USER_LUNCH_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_LUNCH_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> users_meal_between = service.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 31), LocalDate.of(2020, Month.JANUARY, 31), USER_ID);
        assertThat(users_meal_between).usingRecursiveFieldByFieldElementComparator().isEqualTo(Arrays.asList(user_supper_next_day, user_dinner_next_day, user_lunch_next_day, user_border_line_food));
    }

    @Test
    public void getAll() {
        List<Meal> all_users = service.getAll(USER_ID);
        assertThat(all_users).usingRecursiveFieldByFieldElementComparator().isEqualTo(Arrays.asList(user_supper_next_day, user_dinner_next_day, user_lunch_next_day, user_border_line_food, user_supper, user_dinner, user_lunch));
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, ADMIN_ID);
        assertThat(service.get(ADMIN_LUNCH_ID, ADMIN_ID)).usingRecursiveComparison().isEqualTo(getUpdated());
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertThat(created).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(newMeal);
        assertThat(service.get(newId, USER_ID)).usingRecursiveComparison().ignoringFields("dateTime").isEqualTo(newMeal);
    }
}