package com.noname.weblabs

import com.google.gson.GsonBuilder

val gson = GsonBuilder().setPrettyPrinting().create()

fun Any.toJson(): String = gson.toJson(this)