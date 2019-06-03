package com.example.car_list_new.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.car_list_new.data.model.Car
import com.example.car_list_new.R

class CarListAdapter: RecyclerView.Adapter<CarListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int)
    }

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val carIndexTextView: TextView = v.findViewById(R.id.carIndexTextView) as TextView
        val carIdTextView: TextView = v.findViewById(R.id.carIdTextView) as TextView
        val carModelTextView: TextView = v.findViewById(R.id.carModelTextView) as TextView
        val carYearTextView: TextView = v.findViewById(R.id.carYearTextView) as TextView
        val carBodyTypeTextView: TextView = v.findViewById(R.id.carBodyTypeTextView) as TextView
        val carProducerTextView: TextView = v.findViewById(R.id.carProducerTextView) as TextView
        val carClassTextView: TextView = v.findViewById(R.id.carClassTextView) as TextView
    }

    private val items = arrayListOf<Car>()
    var itemClickListener: OnItemClickListener? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.carIndexTextView.text = (position+1).toString()
        holder.carIdTextView.text = item.id
        holder.carModelTextView.text = item.model
        holder.carYearTextView.text = item.year
        holder.carBodyTypeTextView.text = item.bodyType
        holder.carProducerTextView.text = item.producer
        holder.carClassTextView.text = item.`class`
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.car_items, parent, false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener { itemClickListener?.onItemClick(it, viewHolder.adapterPosition) }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<Car>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size-position)/* Не обновлялись индексы элементов после удаленной */
    }

    fun updateAt(index: Int, car: Car) {
        items[index] = car
        notifyItemChanged(index)
    }

}
