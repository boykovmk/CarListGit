package com.example.car_list_new.activity

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.car_list_new.R
import com.example.car_list_new.activity.MainActivity.Companion.EXTRA_CAR
import com.example.car_list_new.data.model.Car
import kotlinx.android.synthetic.main.activity_edit_car.*
import java.util.*

class EditCarActivity : AppCompatActivity() {

    private var editedCar: Car? = null
    private lateinit var option: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_car)

        option = findViewById(R.id.classCarSpinner)
        val adapter = ArrayAdapter.createFromResource(this,R.array.car_classes, android.R.layout.simple_spinner_item)
        option.onItemSelectedListener = object : OnItemSelectedListener {

            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                carClassTextView.text = (adapterView.getItemAtPosition(i)).toString()
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
        option.adapter = adapter

        alertDialogBodyTypeButton.setOnClickListener{
            showDialog()
        }

        val car = intent?.extras?.getSerializable(EXTRA_CAR) as? Car
        editedCar = car

        if (car == null) {
            carIdLabel.text = (UUID.randomUUID().toString())
        } else {
            carIdLabel.text= car.id
            carModelEditText.setText(car.model)
            carYearEditText.setText(car.year)
            carProducerEditText.setText(car.producer)
            carClassTextView.text = car.`class`
            carBodyTypeEditText.setText(car.bodyType)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit_car, menu)
        return true
    }

    private fun showDialog(){
        lateinit var array: Array<String>
        array.copyOf(R.array.car_body_types)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Выберите тип кузова")
        builder.setItems(array) { _, which ->
            val selected = array[which]
            carBodyTypeEditText.setText(selected)
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun createCarFromView(): Car {
        return Car(
            carIdLabel.text.toString(),
            carModelEditText.text.toString(),
            carYearEditText.text.toString(),
            carProducerEditText.text.toString(),
            carClassTextView.text.toString(),
            carBodyTypeEditText.text.toString()
        )
    }

    fun onSaveButtonTap(menuItem: MenuItem) {
        if (carModelEditText.text!!.isNotBlank() &&
            carYearEditText.text!!.isNotBlank() &&
            carProducerEditText.text!!.isNotBlank() &&
            carClassTextView.text!!.isNotBlank() &&
            carBodyTypeEditText.text!!.isNotBlank()) {
            val car = createCarFromView()
            intent.putExtra(EXTRA_CAR, car)
            setResult(Activity.RESULT_OK, intent)
            finish()
        } else Toast.makeText(this, "Необходимо заполнить все поля", Toast.LENGTH_LONG).show()
    }
}