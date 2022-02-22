package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MealServlet extends HttpServlet {
    private final static MealRepository repo = new MealRepositoryImpl();
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String LIST_MEALS = "/meals.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime ldt = LocalDateTime.parse(req.getParameter("date"));
        String desc = req.getParameter("desc");
        int calories = Integer.parseInt(req.getParameter("calories"));
        String mealId = req.getParameter("mealId");
        if (mealId == null || mealId.isEmpty()) {
            repo.create(new Meal(ldt, desc, calories));
        }else{
            Meal meal = new Meal(ldt, desc, calories);
            meal.setId(Integer.parseInt(mealId));
            repo.update(meal);
        }
        req.setAttribute("meals", MealsUtil.filteredByStreams(repo.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.caloriesPerDay));
        req.getRequestDispatcher(LIST_MEALS).forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        if(action.equalsIgnoreCase("delete")){
            int id = Integer.parseInt(request.getParameter("id"));
            repo.delete(id);
            forward = LIST_MEALS;
            request.setAttribute("meals", MealsUtil.filteredByStreams(repo.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.caloriesPerDay));
        } else if (action.equalsIgnoreCase("update")) {
            forward = INSERT_OR_EDIT;
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = repo.getById(id);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("insert")) {
            forward = INSERT_OR_EDIT;
        }
        else{
            forward = LIST_MEALS;
            request.setAttribute("meals", MealsUtil.filteredByStreams(repo.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.caloriesPerDay));

        }

        request.getRequestDispatcher(forward).forward(request, response);
    }
}
