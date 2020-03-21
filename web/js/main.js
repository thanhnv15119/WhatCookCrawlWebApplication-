const activityLevel = {
    sedentary: 1.2,
    lightactive: 1.35,
    ModeratelyActive: 1.55,
    VeryActive:1.8,
    ExtremelyActive: 1.95
}

function increateNum() {
  let txtMealNumber = document.getElementById('mealNumber');
  let spanMealNumber = document.getElementById('cbMealsDivided');
  txtMealNumber.value = txtMealNumber.value * 1 + 1;
  spanMealNumber.innerHTML = spanMealNumber.innerHTML * 1 +1;
}

function decreaseNum() {
  let txtMealNumber = document.getElementById('mealNumber');
  let spanMealNumber = document.getElementById('cbMealsDivided');
  txtMealNumber.value = txtMealNumber.value * 1 - 1;
  spanMealNumber.innerHTML = spanMealNumber.innerHTML * 1 - 1;
}

function initHomePage() {
  loadsCategories();
  loadRecipes();
  loadRandomList();
}

function handleMoreAdvanced() {
  let btnMore = document.getElementById("btnMore");
  let hiddenBox = document.getElementById("hidden-box");
  if (hiddenBox.style.display != "none") {
    btnMore.value = "More Advanced >>";

    hiddenBox.style.display = "none";
  } else {
    btnMore.value = "<<Less";

    hiddenBox.style.display = "contents";
  }
}

function loadRandomList() {
  getXHR(
    "http://localhost:8080/WhatCookWebSerivce_war_exploded/rest/recipe?random=true",
    {}
  ).then(rs => {
    var listRandom = document.getElementsByClassName("random-recipe").item(0);
    listRandom.innerHTML = "";
    let rootNode = rs.childNodes.item(0).childNodes;
    rootNode.forEach(value => {
      let listElement = document.createElement("li");
      let imageElement = document.createElement("img");
      let spanElement = document.createElement("span");
      let aElement = document.createElement("a");
      let url = "recipe" + "?recipeId=" + value.childNodes.item(0).innerHTML;
      aElement.setAttribute("href", url);
      spanElement.innerText = value.childNodes
        .item(1)
        .innerHTML.replace("&amp;amp;", "&&")
        .replace("&amp;#039;", "&");
      imageElement.setAttribute("width", "150px");
      imageElement.setAttribute("height", "180px");
      imageElement.setAttribute("src", value.childNodes.item(2).innerHTML);
      aElement.appendChild(imageElement);
      aElement.appendChild(spanElement);
      listElement.appendChild(aElement);
      listRandom.appendChild(listElement);
    });
  });
}

function loadsCategories() {
  var list = document.getElementById("list-category");
  list.innerHTML = "";
  getXHR(
    "http://localhost:8080/WhatCookWebSerivce_war_exploded/rest/category"
  ).then(function(res) {
    var rootNode = res.childNodes.item(0).childNodes;
    rootNode.forEach((value, key) => {
      var liElement = document.createElement("li");
      var aElement = document.createElement("a");
      var url = "category/" + value.childNodes.item(0).innerHTML;
      aElement.setAttribute("href", "#");
      aElement.setAttribute(
        "onclick",
        "handleClickCategory(" + value.childNodes.item(0).innerHTML + ")"
      );
      aElement.innerText = value.childNodes.item(1).innerHTML;
      liElement.appendChild(aElement);
      list.appendChild(liElement);
    });
  });
}

function loadRecipes() {
  getXHR("http://localhost:8080/WhatCookWebSerivce_war_exploded/rest/recipe", {
    page: 1,
    size: 10
  }).then(function(res) {
    inputRecipesData(res);
  });
}

function inputRecipesData(res) {
  var list = document.getElementById("list-recipes");
  list.innerHTML = "";
  var rootNode = res.childNodes.item(0).childNodes;
  rootNode.forEach(value => {
    var liElement = document.createElement("li");
    var aElement = document.createElement("a");
    var imageElement = document.createElement("img");
    imageElement.setAttribute("width", "150px");
    imageElement.setAttribute("height", "180px");
    var spanElement = document.createElement("span");
    spanElement.innerText = value.childNodes
      .item(1)
      .innerHTML.replace("&amp;amp;", "&&")
      .replace("&amp;#039;", "&");
    let url = "recipe" + "?recipeId=" + value.childNodes.item(0).innerHTML;
    aElement.setAttribute("href", url);
    imageElement.setAttribute("src", value.childNodes.item(2).innerHTML);
    aElement.appendChild(imageElement);
    aElement.appendChild(spanElement);
    liElement.appendChild(aElement);
    list.appendChild(liElement);
  });
}

function handleClickCategory(id) {
  getXHR("http://localhost:8080/WhatCookWebSerivce_war_exploded/rest/recipe", {
    categoryId: id
  }).then(rs => {
    inputRecipesData(rs);
  });
}

function objectToQueryParam(obj) {
  var result = "";
  // @ts-ignore
  Object.entries(obj).forEach(function(_a) {
    var key = _a[0],
      value = _a[1];
    result += key + "=" + value + "&";
  });
  return result.slice(0, result.length - 1);
}

function getXHR(url, data) {
  var xhr = new XMLHttpRequest();
  if (data != null) {
    if (Object.keys(data).length !== 0) {
      url += "?" + objectToQueryParam(data);
    }
  }
  xhr.open("GET", url, true);
  // @ts-ignore
  return new Promise(function(resolve, reject) {
    xhr.onreadystatechange = function() {
      if (xhr.readyState === 4) {
        if (xhr.status >= 300) {
          reject("Error, status code = " + xhr.status);
        } else {
          resolve(xhr.responseXML);
        }
      }
    };
    xhr.send(null);
  });
}

function handleCaculate() {
  let rdMale = document.getElementById("rdMale");
  let rdFemale = document.getElementById("rdFemale");
  let txtHeight = document.getElementById("txtHeight");
  let txtWeight = document.getElementById("txtWeight");
  let txtAge = document.getElementById("txtAge");
  let txtFatPercent = document.getElementById("txtFatPercent");
  let errorMessage = "";
  let cbActivity = document.getElementById("activityLevel");
  let txtKcal = document.getElementById('txtKcal');
  if (rdMale.checked == false && rdFemale.checked == false) {
    errorMessage += "please choose your gender. \n";
  }

  if (txtHeight.value.length == 0) {
    errorMessage += "please input your height.\n";
  }

  if (txtWeight.value.length == 0) {
    errorMessage += "please input your txtWeight.\n";
  }

  if (txtAge.value.length == 0) {
    errorMessage += "please input your txtAge.\n";
  }

  if (txtHeight.value.match("\\D") != null) {
    errorMessage += "Height must be number\n";
    if (txtHeight < 100) {
      errorMessage += "Height must be more than 100cm\n";
    }
  }
  if (txtWeight.value.match("\\D") != null) {
    errorMessage += "Weight must be number\n";
    if (txtHeight < 10) {
      errorMessage += "Weight must be more than 10 Kg\n";
    }
  }

  if (txtAge.value.match("\\D") != null) {
    errorMessage += "Weight must be number\n";
    if (txtHeight > 10) {
      errorMessage += "Age mustbe more than 10\n";
    }
  }

  if (errorMessage.length > 1) {
    alert(errorMessage);
  } else {
    let multiActivceLevel = 1;
    switch(cbActivity.selectedIndex){
      case 0: multiActivceLevel = activityLevel.sedentary; break;
      case 1: multiActivceLevel = activityLevel.lightactive; break;
      case 2: multiActivceLevel = activityLevel.ModeratelyActive; break;
      case 3: multiActivceLevel = activityLevel.VeryActive; break;
      case 4: multiActivceLevel = activityLevel.ExtremelyActive; break;
    }
    if (txtFatPercent.value.length != 0) {
        txtKcal.value = caculateBMRKatch({height: txtHeight.value, weight: txtWeight.value, fatPercent: txtFatPercent.value}) * multiActivceLevel
    } else {
        txtKcal.value = caculateJeor({height: txtHeight.value, weight: txtWeight.value, age: txtAge.value})* multiActivceLevel
    }
  }
}

function caculateJeor(data) {
  if (data.gender == "Male") {
    return 10 * data.weight + 6.25 * data.height - 5 * data.age + 5;
  } else {
    return 10 * data.weight + 6.25 * data.height - 5 * data.age - 161;
  }
}

function caculateBMRKatch(data) {
  var LBM = data.weight - (data.weight * data.fatPercent) / 100;
  return 21.6 * LBM + 370;
}

function changeRecipe(elementId) {
  let kcal = document.getElementsByTagName("span").item(0).innerHTML
  let mealNumber = document.getElementById("cbMealsDivided").innerHTML;
  getXHR("rest/recipe/kcal", {"kcal": kcal, "mealNumber":mealNumber}).then(rs => {
    let elment = document.getElementById(elementId).children.item(1).children.item(0).children.item(0);
    let imgRecipe = elment.children.item(0);
    var rootNode = rs.childNodes.item(0).childNodes;
    imgRecipe.src = rootNode.item(2).innerHTML;
    let title = elment.children.item(1);
    title.innerHTML = rootNode.item(1).innerHTML
    let bodytable = elment.getElementsByClassName("nutrition-recipe").item(0).children.item(1).children.item(0).children;
    bodytable.item(0).innerHTML = rootNode.item(3).childNodes.item(0).innerHTML;
    bodytable.item(1).innerHTML = rootNode.item(3).childNodes.item(3).innerHTML;
    bodytable.item(2).innerHTML = rootNode.item(3).childNodes.item(1).innerHTML;
    bodytable.item(3).innerHTML = rootNode.item(3).childNodes.item(4).innerHTML;
    bodytable.item(4).innerHTML = rootNode.item(3).childNodes.item(2).innerHTML;

  })
}