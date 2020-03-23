<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<title>What Cook Home Page</title>
<header>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
    <script src="main.js"></script>
</header>
<body>
<div class="header-bar">
    <h1>WhatCook?</h1>
</div>
<div class="detail-layout">
    <h2>We fond some for you with under <span>${txtKcal}</span> recipes</h2>
    Divide into:
    <form action="findMealsServlet" method="get">
        <input type="hidden" id="mealNumber" name="mealNumber" value="${mealNumber}">
        <input type="hidden" id="txtKcal" name="txtKcal" value="${txtKcal}">
        <span id="cbMealsDivided">${mealNumber}</span>
        <input type="submit" value="submit"/>
    </form>
    <button value="+" onclick="increateNum()">+</button>
    <button value="-" onclick="decreaseNum()">-</button>
    meal(s)<br/>

    <ul class="recommend-recipe">
        <c:forEach items="${recipeList}" var="item" varStatus="count">
            <li id="M${count.index}">
                <h3>${count.index + 1}th Meal</h3>
                <div class="meal-layout">
                    <a href="recipe?recipeId=${item.id}">

                        <div style="width: 85%;float: left;"><img src="${item.img}" height="130px"/>
                            <p>${item.name}</p>
                            <table class="nutrition-recipe">
                                <thead>
                                <th>Kcal</th>
                                <th>Proteins</th>
                                <th>Carbs</th>
                                <th>Fiber</th>
                                <th>Fat</th>
                                </thead>
                                <tr>
                                    <td>${item.nutrion.calories}</td>
                                    <td>${item.nutrion.protein}</td>
                                    <td>${item.nutrion.carbs}</td>
                                    <td>${item.nutrion.fiber}</td>
                                    <td>${item.nutrion.fat}</td>
                                </tr>
                            </table>
                        </div>
                    </a>

                    <input type="button" value="Change" class="btnChange" onclick="changeRecipe('M${count.index}')">
                </div>
            </li>
        </c:forEach>

    </ul>
</div>
</div>
</body>
<footer></footer>
</html>
