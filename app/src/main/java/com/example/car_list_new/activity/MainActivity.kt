package com.example.car_list_new.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.car_list_new.R
import com.example.car_list_new.adapter.CarListAdapter
import com.example.car_list_new.data.CarStorage
import com.example.car_list_new.data.model.Car
import com.example.car_list_new.helper.MarginItemDecoration
import com.example.car_list_new.helper.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CREATE_CAR = 1001
        const val REQUEST_EDIT_CAR = 1002
        const val EXTRA_CAR = "car"
        const val EXTRA_CAR_INDEX = "car_index"
        const val RECYCLER_VIEW_MARGIN_DP = 8
    }

    private var adapter = CarListAdapter()
    private lateinit var carStorage: CarStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(MarginItemDecoration(RECYCLER_VIEW_MARGIN_DP))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        carStorage = CarStorage("$filesDir/cars.json")
        adapter.setItems(carStorage.items)
        adapter.itemClickListener = object : CarListAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                editCar(position)
            }
        }

        val touchHelper = ItemTouchHelper(object : SwipeToDeleteCallback(this@MainActivity) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                adapter.removeAt(position)
                carStorage.removeAt(position)
            }
        })
        touchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    fun onExitButtonTap(menuItem: MenuItem) {
        System.exit(-1)
    }

    fun onAddButtonTap(view: View) {
        val intent = Intent(this, EditCarActivity::class.java)
        startActivityForResult(intent, REQUEST_CREATE_CAR)
    }

    fun onDefaultButtonTap(menuItem: MenuItem) {
        carStorage.setDefault()
        adapter.setItems(carStorage.items)
    }

    fun onSortByModelButtonTap(menuItem: MenuItem) {
        carStorage.sortList()
        adapter.setItems(carStorage.items)
    }

    fun onSortByDateButtonTap(menuItem: MenuItem) {
        carStorage.sortListDate()
        adapter.setItems(carStorage.items)
    }

    fun editCar(index: Int) {
        val editedCar = carStorage.items[index]
        val intent = Intent(this, EditCarActivity::class.java)
        intent.putExtra(EXTRA_CAR, editedCar)
        intent.putExtra(EXTRA_CAR_INDEX, index)
        startActivityForResult(intent, REQUEST_EDIT_CAR)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CREATE_CAR) {
            (data?.extras?.getSerializable(EXTRA_CAR) as? Car)?.let {
                carStorage.add(it)
                adapter.setItems(carStorage.items)
            }
        }
        if (requestCode == REQUEST_EDIT_CAR) {
            val updatedCar = data?.extras?.getSerializable(EXTRA_CAR) as? Car
            val updatedCarIndex = data?.extras?.getInt(EXTRA_CAR_INDEX)
            if (updatedCar != null && updatedCarIndex != null) {
                adapter.updateAt(updatedCarIndex, updatedCar)
                carStorage.updateAt(updatedCarIndex, updatedCar)
            }
        }
    }

}