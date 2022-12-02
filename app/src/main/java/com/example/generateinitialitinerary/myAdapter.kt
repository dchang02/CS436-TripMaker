package com.example.generateinitialitinerary

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.new_item.view.*

class myAdapter(context: SearchScreen, arrayList: ArrayList<ModelClass>):
        RecyclerView.Adapter<myAdapter.ViewHolder>(){

        private val context : Context
        private val arrayList: ArrayList<ModelClass>

        init {
                this.context = context.requireContext()
                this.arrayList = arrayList
        }

        override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int):
                ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.new_item, parent, false)
                return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val modelClass = arrayList[position]

                holder.itemView.textView.text = modelClass.name

                if (modelClass.isSelected){
                        holder.itemView.setBackgroundColor(context.resources.getColor(androidx.appcompat.R.color.highlighted_text_material_dark))
                }
                else{
                        holder.itemView.setBackgroundColor(Color.TRANSPARENT)
                }
        }

        override fun getItemCount(): Int {
                return arrayList.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
                init {
                    itemView.rowitem.setOnClickListener{
                            setMultipleSelection(adapterPosition)
                    }
                }
        }

        private fun setMultipleSelection(adapterPosition: Int){
                arrayList[adapterPosition].isSelected = !arrayList[adapterPosition].isSelected

                notifyDataSetChanged()
        }

        }