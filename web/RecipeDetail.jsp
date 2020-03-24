<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
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
    <x:parse xml="${RecipeXml}" var="recipeXML"/>
    <img src="<x:out select="$recipeXML/recipe/image"/>" width="250px"/>
    <h1><x:out select="$recipeXML/recipe/name"/></h1>
    <ul class="detail-list">
        <li>Prep Time: <x:out select="$recipeXML/recipe/prepTime"/> mins</li>
        <li>Cook Time: <x:out select="$recipeXML/recipe/cookTime"/> mins</li>
        <li><x:out select="$recipeXML/recipe/yield"/></li>
    </ul>
    <table id="nutrition-detail">
        <thead>
        <th>Kcal</th>
        <th>Proteins</th>
        <th>Carbs</th>
        <th>Fiber</th>
        <th>Fat</th>
        </thead>
        <tr>
            <td><x:out select="$recipeXML/recipe/nutrition/calories"/></td>
            <td><x:out select="$recipeXML/recipe/nutrition/protein"/></td>
            <td><x:out select="$recipeXML/recipe/nutrition/carbs"/></td>
            <td><x:out select="$recipeXML/recipe/nutrition/fiber"/></td>
            <td><x:out select="$recipeXML/recipe/nutrition/fat"/></td>
        </tr>
    </table>
    <div class="ingredients-layout">
        <h2 style="margin-left: 10px;">Ingredients</h2>
        <ul class="ingredients">
            <x:forEach select="$recipeXML/recipe/ingredients/ingredient" var="ingredient">
                <li><x:out select="$ingredient"/></li>
            </x:forEach>
        </ul>
    </div>
    <div class="methods-layout">
        <h2>Methods</h2>
        <ul>
            <x:forEach select="$recipeXML/recipe/methods/method"  var="method">
                <li>
                    <b>Step <x:out select="step"/></b>
                    <p><x:out select="content"/></p>
                </li>
            </x:forEach>
        </ul>
    </div>
</div>
</body>
</html>

