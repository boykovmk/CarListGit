package com.example.car_list_new.data

import com.example.car_list_new.data.model.Car
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class CarStorage(private val filePath: String) {

    private val gson = Gson()
    var items = loadCars()
        private set

    fun removeAt(index: Int) {
        items.removeAt(index)
        save()
    }

    fun add(car: Car) {
        items.add(car)
        save()
    }

    fun updateAt(index: Int, car: Car) {
        items[index] = car
        save()
    }

    fun setDefault() {
        items = getDefaultCars()
        save()
    }

    fun sortList() {
        val sortedList: List<Car> = items.sortedWith(compareBy {it.model})
        items.clear()
        items.addAll(sortedList)
        save()
    }

    fun sortListDate() {
        val sortedList: List<Car> = items.sortedWith(compareBy {it.year})
        items.clear()
        items.addAll(sortedList)
        save()
    }

    private fun save() {
        val json = gson.toJson(items)
        File(filePath).writeText(json)
    }

    private fun loadCars(): ArrayList<Car> {
        val file = File(filePath)
        if (!file.exists()) {
            return getDefaultCars()
        }
        val json = file.readText()
        if (json.isEmpty()) {
            return arrayListOf()
        }
        val carsTypeToken = object : TypeToken<ArrayList<Car>>() {}.type
        return gson.fromJson(json, carsTypeToken)
    }

    private fun getDefaultCars(): ArrayList<Car> {
        return arrayListOf(
            Car(UUID.randomUUID().toString(), "Lada Granta", "2014", "Россия", "A", "Хэтчбэк"),
            Car(UUID.randomUUID().toString(), "Toyota Corolla", "1997", "Япония", "B", "Седан"),
            Car(UUID.randomUUID().toString(), "Volkswagen Passat ", "1999", "Германия", "C", "Кроссовер"),
            Car(UUID.randomUUID().toString(), "Toyota Camry", "2009", "Япония", "D", "Седан"),
            Car(UUID.randomUUID().toString(), "Opel Corsa", "2013", "Германия", "B", "Седан"),
            Car(UUID.randomUUID().toString(), "BMW X6", "2017", "Германия", "S", "Внедорожник"),
            Car(UUID.randomUUID().toString(), "Dodge Challendger", "2010", "Америка", "S", "Седан"),
            Car(UUID.randomUUID().toString(), "Lada Granta", "2014", "Россия", "A", "Хэтчбэк"),
            Car(UUID.randomUUID().toString(), "Toyota Corolla", "1997", "Япония", "B", "Седан"),
            Car(UUID.randomUUID().toString(), "Volkswagen Passat ", "1999", "Германия", "C", "Кроссовер"),
            Car(UUID.randomUUID().toString(), "Toyota Camry", "2009", "Япония", "D", "Седан"),
            Car(UUID.randomUUID().toString(), "Opel Corsa", "2013", "Германия", "B", "Седан"),
            Car(UUID.randomUUID().toString(), "BMW X6", "2017", "Германия", "S", "Внедорожник")
        )
    }

}