package com.alancaps.iva_calculadora

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val types = resources.getStringArray(R.array.tipos)
        val origen: Spinner = findViewById(R.id.sp_origen)
        val calculate: Button = findViewById(R.id.btnCalc)
        val amount: EditText = findViewById(R.id.ed_amount)
        val resultTextView: TextView = findViewById(R.id.tv_result)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, types
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        origen.adapter = adapter

        origen.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: android.view.View,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@MainActivity, "Seleccionado: ${types[position]}", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        calculate.setOnClickListener {
            calculateTax(amount, origen.selectedItem.toString(), resultTextView)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateTax(amount: EditText, origin: String, resultTextView: TextView) {
        val value = amount.text.toString().trim()
        val doubleValue = value.toDoubleOrNull()

        if (doubleValue != null && doubleValue > 0) {
            val taxRate = if (origin == "Fronterizo") 1.10 else 1.16
            val totalWithTax = doubleValue * taxRate
            resultTextView.text = "Total con IVA (${(taxRate - 1) * 100}%): $%.2f".format(totalWithTax)
        } else {
            Toast.makeText(this, "Por favor, ingresa un monto válido mayor a 0.", Toast.LENGTH_SHORT).show()
            resultTextView.text = ""
        }
    }
}
