package com.noname.weblabs

import com.google.gson.Gson
import com.google.gson.GsonBuilder

val gson: Gson = GsonBuilder().setPrettyPrinting().create()

fun Any.toJson(): String = gson.toJson(this)