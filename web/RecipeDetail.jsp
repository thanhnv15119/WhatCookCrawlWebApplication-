<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: thanhnv
  Date: 18/03/2020
  Time: 21:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<header>
    <link rel="stylesheet" type="text/css" href="css/main.css"/>
    <script src="js/main.js"></script>
</header>
<body>
<div class="header-bar">
    <h1>WhatCook?</h1>
</div>
<div class="detail-layout">
    <img src="${RecipeDTO.img}" width="250px"/>
    <h1>${RecipeDTO.name}</h1>
    <ul class="detail-list">
        <li>Prep Time: ${RecipeDTO.prepTime} mins</li>
        <li>Cook Time: ${RecipeDTO.cookTime} mins</li>
        <li>${RecipeDTO.yeild}</li>
        <li>
            <table>
                <thead>
                <th>Kcal</th>
                <th>Proteins</th>
                <th>Carbs</th>
                <th>Fiber</th>
                <th>Fat</th>
                </thead>
                <tr>
                    <td>${RecipeDTO.nutrion.calories}</td>
                    <td>${RecipeDTO.nutrion.protein}</td>
                    <td>${RecipeDTO.nutrion.carbs}</td>
                    <td>${RecipeDTO.nutrion.fiber}</td>
                    <td>${RecipeDTO.nutrion.fat}</td>
                </tr>
            </table>
        </li>

    </ul>
    <div class="ingredients-layout">
        <ul class="ingredients">
            <c:forEach items="${RecipeDTO.ingredients}" var="ingredient">
                <li>${ingredient.content}</li>
            </c:forEach>
        </ul>
    </div>
    <div class="methods-layout">
        <ul>
            <c:forEach items="${RecipeDTO.methods}" var="method">
                <li>
                    <b>Step ${method.step}:</b>
                    <p>${method.content}</p>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
</body>
</html>

