package com.gl4.tp2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private val subjectSpinner: Spinner by lazy { findViewById(R.id.subjectSpinner) }
    private val presenceSpinner: Spinner by lazy { findViewById(R.id.presenceSpinner) }
    private var subjects = listOf("Cours", "TP")
    private var presenceTypes = listOf("Absents", "Présents")
    private lateinit var searchText: TextInputEditText
    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: StudentsAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview = findViewById(R.id.recyclerView)
        recyclerview.layoutManager = LinearLayoutManager(this)
        adapter = StudentsAdapter(initStudents())
        recyclerview.adapter = adapter

        subjectSpinner.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, subjects)
        presenceSpinner.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, presenceTypes)

        subjectSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val toast = Toast(this@MainActivity)
                toast.setText(subjects[id.toInt()]);
                toast.show()
                adapter.getSubjectFilter().filter(id.toString())
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }

        presenceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                adapter.getPresenceFilter().filter(id.toString())
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }

        searchText = findViewById(R.id.search)
        searchText.doOnTextChanged { text, s, t, b -> adapter.filter.filter(text) }
    }

    private fun initStudents(): ArrayList<Student> {
        val output = ArrayList<Student>()
        var genre: String
        var subject: Int = 0;
        for (i in 1..20) {
            genre = if (i % 2 == 0) {
                "male"
            } else {
                "female"
            }

            if (i > 10) subject = 1

            output.add(Student("name$i", "surname$i", genre, subject, i % 2 == 0))
        }

        return output
    }
}