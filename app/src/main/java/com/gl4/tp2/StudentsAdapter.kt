package com.gl4.tp2

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class StudentsAdapter(private val data: ArrayList<Student>) :
    RecyclerView.Adapter<StudentsAdapter.ViewHolder>(), Filterable {

    var dataFilterList = ArrayList<Student>()
    var initDataSet = ArrayList<Student>()

    init {
        dataFilterList = data
        initDataSet = data
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()

                dataFilterList = if (charSearch.isEmpty()) {
                    initDataSet
                } else {
                    val resultList = ArrayList<Student>()
                    for (student in data) {
                        if (student.name.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(student)
                        }
                    }
                    resultList
                }

                val filterResults = FilterResults()
                filterResults.values = dataFilterList
                return filterResults
            }


            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFilterList = results?.values as ArrayList<Student>
                notifyDataSetChanged()
            }

        }
    }

    fun getSubjectFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                val charSearch = constraint.toString()

                dataFilterList = if (charSearch.isEmpty()) {
                    initDataSet
                } else {
                    val resultList = ArrayList<Student>()
                    for (student in data) {
                        if (student.subject.toString() == charSearch) {
                            resultList.add(student)
                        }
                    }
                    resultList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = dataFilterList
                return filterResults
            }


            override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults?) {
                dataFilterList = results?.values as ArrayList<Student>
                notifyDataSetChanged()
            }
        }
    }

    fun getPresenceFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
                val charSearch = constraint.toString()

                dataFilterList = if (charSearch.isEmpty()) {
                    initDataSet
                } else {
                    val resultList = ArrayList<Student>()
                    for (student in data) {
                        var booleanText = ""

                        booleanText = if (charSearch == "1") {
                            "true"
                        } else {
                            "false"
                        }

                        if (student.present.toString() === booleanText) {
                            resultList.add(student)
                        }
                    }
                    resultList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = dataFilterList
                return filterResults
            }


            override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults?) {
                dataFilterList = results?.values as ArrayList<Student>
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val image: ImageView
        val checkbox: CheckBox


        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.name)
            image = view.findViewById(R.id.avatar)
            checkbox = view.findViewById(R.id.presence)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.student_item, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (dataFilterList[position].gender == "male") {
            viewHolder.image.setImageResource(R.drawable.male)
        } else {
            viewHolder.image.setImageResource(R.drawable.female)
        }
        viewHolder.textView.text =
            dataFilterList[position].name + " " + dataFilterList[position].surname

        viewHolder.checkbox.isChecked = dataFilterList[position].present

        viewHolder.checkbox.setOnClickListener(){
            dataFilterList[position].present = viewHolder.checkbox.isChecked
        }
    }

    override fun getItemCount() = dataFilterList.size

}