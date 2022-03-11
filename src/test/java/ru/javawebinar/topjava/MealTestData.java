package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;
public class MealTestData {
   public static final int ADMIN_LUNCH_ID = START_SEQ+3;
   public static final int ADMIN_DINNER_ID = START_SEQ+4;
   public static final int USER_LUNCH_ID = START_SEQ+5;
   public static final int USER_DINNER_ID = START_SEQ+6;
   public static final int USER_SUPPER_ID = START_SEQ+7;
   public static final int USER_BORDER_LINE_FOOD_ID = START_SEQ+8;
   public static final int USER_LUNCH_NEXT_DAY_ID = START_SEQ+9;
   public static final int USER_DINNER_NEXT_DAY_ID = START_SEQ+10;
   public static final int USER_SUPPER_NEXT_DAY_ID = START_SEQ+11;
   public static final Meal admin_lunch = new Meal(ADMIN_LUNCH_ID,LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
   public static final Meal admin_dinner = new Meal(ADMIN_DINNER_ID,LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);
   public static final Meal user_lunch = new Meal(USER_LUNCH_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
   public static final Meal user_dinner = new Meal(USER_DINNER_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
   public static final Meal user_supper =  new Meal(USER_SUPPER_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
   public static final Meal user_border_line_food = new Meal(USER_BORDER_LINE_FOOD_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
   public static final Meal user_lunch_next_day = new Meal(USER_LUNCH_NEXT_DAY_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
   public static final Meal user_dinner_next_day = new Meal(USER_DINNER_NEXT_DAY_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
   public static final Meal user_supper_next_day = new Meal(USER_SUPPER_NEXT_DAY_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
   public static Meal getNew(){
      return new Meal(null, LocalDateTime.now(), "Breakfast", 1000);
   }
   public static Meal getUpdated(){
      Meal updated = new Meal(admin_lunch);
      updated.setDateTime(LocalDateTime.of(2020, Month.JANUARY, 1, 10, 20));
      updated.setDescription("SecondLunch");
      updated.setCalories(200);
      return updated;
   }
}
