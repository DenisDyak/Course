package com.bignerdranch.android.coursevalute

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText

class Converter : AppCompatActivity() {

    lateinit var textRub: TextInputEditText
    private lateinit var spinner: Spinner
    lateinit var conResult: TextView
    lateinit var valList: List<Valute>
    private lateinit var valutList: ListJson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)

        textRub = findViewById(R.id.sum_rub2)
        spinner = findViewById(R.id.spinner)
        conResult = findViewById(R.id.result)
        valutList = intent.getSerializableExtra(LIST_VAL) as ListJson
        valList = valutList.Valute.values.toList()
        val nameList: MutableList<String> = mutableListOf()
        var result: String
        valList.forEach {
            nameList.add(it.name)
        }
        conResult.inputType = 3
        val spinnerArrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nameList)
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerArrayAdapter

        textRub.doOnTextChanged { text, _, _, _ ->
            try{
                result = "%.4f".format(valList[spinner.selectedItemPosition].nominal.toDouble() / valList[spinner.selectedItemPosition].value * text.toString()
                        .toDouble())
            conResult.text = result
            }
            catch (e:NumberFormatException){}
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
               try {
                   result = "%.4f".format(valList[position].nominal.toDouble() / valList[position].value * textRub.text.toString()
                           .toDouble())
                   conResult.text = result
               }
               catch (e: NumberFormatException){}
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("LIST_VALUTE", valutList)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        valutList = savedInstanceState.getSerializable("LIST_VALUTE") as ListJson
    }
}