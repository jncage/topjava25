<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Add new user</title>
</head>
<body>

<form method="POST" action='meals' name="frmAddMeal">
    ID : <input type="text" readonly="readonly" name="mealId"
                value="${meal.id}"/><br/>
    Date : <input
        type="datetime-local" name="date"
        value="${meal.dateTime}"/> <br/>
    Description : <input
        type="text" name="desc"
        value="${meal.description}"/><br/>
    Calories : <input
        type="number" name="calories"
        value="${meal.calories}"/> <br/>
    <input
            type="submit" value="Save"/>
</form>
</body>
</html>