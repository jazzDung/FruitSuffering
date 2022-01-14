package com.example.fruitsuffering.adapter
import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitsuffering.R
import com.example.fruitsuffering.TodoActivity
import com.example.fruitsuffering.model.Todo


class TodoAdapter(private val context: Context, private var todoList: List<Todo>) : RecyclerView.Adapter<TodoAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val tvTag: TextView = view.findViewById(R.id.tvTag)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)

        init {
            itemView.setOnClickListener{
                Toast.makeText(itemView.context, "Todo Clicked", Toast.LENGTH_SHORT).show()
            }

            checkBox.setOnClickListener{
                if (checkBox.isChecked) {
                    tvTitle.paintFlags = tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    tvTitle.paintFlags = 0
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = todoList[position]
        holder.tvTag.text = item.tag
        Log.d(TodoActivity.TAG, item.tag)
        holder.tvTitle.text = item.title
        Log.d(TodoActivity.TAG, item.title)
        holder.checkBox.isChecked = item.isChecked
        Log.d(TodoActivity.TAG, "Alo alo 123")

    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun update(newData: List<Todo>) {
        todoList = newData
        notifyDataSetChanged()
    }
}

