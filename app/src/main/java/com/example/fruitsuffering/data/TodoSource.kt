package com.example.fruitsuffering.data

import android.os.Build.VERSION_CODES.M
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.fruitsuffering.model.Todo


var TodoList = mutableListOf<Todo>(Todo("Apple", "Testing", false),
        Todo("Apple", "Testing", false))

var FruitTag = listOf<String>(
        "apple", "banana", "beetroot", "bell pepper", "cabbage", "carrot", "cauliflower",
        "chilli pepper", "corn", "cucumber", "eggplant", "garlic", "ginger", "grapes", "kiwi", "lemon", "lettuce"
        , "mango", "onion", "orange", "pear", "peas", "pineapple", "pomegranate", "potato",
        "radish", "soy beans", "spinach", "sweet potato", "tomato", "turnip", "watermelon"
)

class TodoSource{

}
