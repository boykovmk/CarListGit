package com.example.car_list_new.data.model

import java.io.Serializable

data class Car(val id: String,
               val model: String,
               val year: String,
               val producer: String,
               val `class`: String,
               val bodyType: String): Serializable
