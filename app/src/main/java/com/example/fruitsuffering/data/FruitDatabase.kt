package com.example.fruitsuffering.data

import com.example.fruitsuffering.model.FruitNutrition

class FruitDatabase {

    val Database = listOf<FruitNutrition>(
         FruitNutrition("Apple", 3.1, 2.8, 2.0, 100, 0.5, 52.0, 13.8, 2.4, 0.1, 10.4, 0.3, 0.2, 54.0, 4.6, 0.0, 0.2, 2.2, 0.0, 0.0, 3.0,)
        ,FruitNutrition("Banana", 2.8, 2.9, 2.7,100, 1.0, 89.0, 22.8, 2.6, 5.4, 12.2, 1.1, 0.3, 64.0, 8.7, 0.0, 0.1, 0.5 , 0.4, 0.0, 20.0)
        ,FruitNutrition("Beetroot", 4.2, 3.9, 2.1, 100, 0.9, 43.0, 9.6, 2.8, 0.0,6.8,1.6, 0.2, 33.0, 4.9, 0.0, 0.0, 0.2, 0.1, 0.0, 109.0)
    )


    var FruitTag = listOf<String>(
        "apple", "banana", "beetroot", "bell pepper", "cabbage", "carrot", "cauliflower",
        "chilli pepper", "corn", "cucumber", "eggplant", "garlic", "ginger", "grapes", "kiwi", "lemon", "lettuce"
        , "mango", "onion", "orange", "pear", "peas", "pineapple", "pomegranate", "potato",
        "radish", "soy beans", "spinach", "sweet potato", "tomato", "turnip", "watermelon"
    )
}