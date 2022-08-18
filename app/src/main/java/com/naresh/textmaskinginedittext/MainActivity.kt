package com.naresh.textmaskinginedittext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val dobEditText: EditText

        dobEditText = findViewById<View>(R.id.etDate) as EditText
        dobEditText.addTextChangedListener(DateMaskFormatter())

    }
}