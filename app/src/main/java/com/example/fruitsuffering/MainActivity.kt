package com.example.fruitsuffering

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import java.util.jar.Manifest


class MainActivity : AppCompatActivity() {

    private lateinit var btnScan: Button
    private lateinit var btnTodo: Button
    private lateinit var btnTracker: Button
    private lateinit var btnSearch: Button

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Bind element in xml
        btnScan = findViewById(R.id.btnScan)
        btnTodo = findViewById(R.id.btnTodo)
        btnTracker = findViewById(R.id.btnTracker)
        btnSearch = findViewById(R.id.btnSearch)

        //Hide that ugly title bar
        this.supportActionBar?.hide();


        btnScan.setOnClickListener{
            val intent = Intent(this, ImageActivity::class.java)
            startActivity(intent)
        }

        btnTodo.setOnClickListener{
            val intent = Intent(this, TodoActivity::class.java)
            startActivity(intent)
        }

    }



}