package com.example.fruitsuffering

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fruitsuffering.adapter.TodoAdapter
import com.example.fruitsuffering.data.TodoList
import com.example.fruitsuffering.data.TodoSource
import com.example.fruitsuffering.model.Todo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class TodoActivity : AppCompatActivity() {

    companion object {
        const val TAG = "TodoActivity"
    }

    private lateinit var todoView: RecyclerView
    private lateinit var btnAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        //Hide that ugly title bar
        this.supportActionBar?.hide()

        todoView = findViewById(R.id.todoView)
        btnAdd = findViewById(R.id.btnAdd)
        todoView.adapter = TodoAdapter(this, TodoList)
        todoView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        todoView.setHasFixedSize(true)

        btnAdd.setOnClickListener{
            showAddTodoDialog()
            Log.d(TAG, "Opening Dialog")
        }

    }

    fun showAddTodoDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.add_todo_sheet)

        val tagInput = dialog.findViewById(R.id.tagInput) as EditText
        val titleInput = dialog.findViewById(R.id.titleInput) as EditText
        val timeInput = dialog.findViewById(R.id.timeInput) as TextView
        val btnTime = dialog.findViewById(R.id.btnTime) as Button
        val btnCreate = dialog.findViewById(R.id.btnCreate) as Button

        btnTime.setOnClickListener{
            val cal = Calendar.getInstance()

            val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                val hour = hour
                val minute = minute

                timeInput.append("$hour:$minute")
//                timeInput.text = SimpleDateFormat("HH:mm").format(cal.time)

            }

            val dateSetListener = DatePickerDialog.OnDateSetListener{ timePicker, year, month, day ->
                val year = year
                val month = month
                val day = day

//                timeInput.append("$day:$month")
//                timeInput.text = SimpleDateFormat("DD:MM").format(cal.time)
            }

            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), true).show()
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()

        }

        btnCreate.setOnClickListener{
            TodoList = (TodoList + Todo(titleInput.text.toString(), tagInput.text.toString(), false)) as MutableList<Todo>
            Log.d(TAG, "Create new Todo!")
            Log.d(TAG, TodoList.size.toString())
            (todoView.adapter as TodoAdapter).update(TodoList)
            Toast.makeText(this, "New Todo added!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.AddTodoTransition
        dialog.window?.setGravity(Gravity.BOTTOM)
    }



}