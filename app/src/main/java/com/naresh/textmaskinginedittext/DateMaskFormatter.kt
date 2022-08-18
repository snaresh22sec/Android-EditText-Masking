package com.naresh.textmaskinginedittext

import android.text.Editable
import android.text.TextWatcher
import java.util.*

class DateMaskFormatter:TextWatcher {

    private var updatedText: String? = null
    private var editing: Boolean = false

    companion object{
        private const val MAX_LENGTH = 8
        private const val MIN_LENGTH = 2
    }

    override fun beforeTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
        if(text.toString() == updatedText || editing) return

        var digits = text.toString().replace("\\D".toRegex(),"")
        val length = digits.length

        if(length <= MIN_LENGTH){
            digits = validateDay(digits)
            updatedText = digits
            return
        }

        if(length > MAX_LENGTH){
            digits = digits.substring(0, MAX_LENGTH)
        }

        updatedText = if (length <= 4){
            digits = validateMonth(digits.substring(0,2),digits.substring(2))
            val day = digits.substring(0,2)
            val month = digits.substring(2)
            String.format(Locale.US, "%s/%s", day,month)
        } else {
            digits = digits.substring(0,2) + digits.substring(2,4) + validateYear(digits.substring(0,2)
                ,digits.substring(2,4), digits.substring(4))
            digits = digits.substring(0,2) + digits.substring(2,4) + digits.substring(4)

            val day = digits.substring(0,2)
            val month = digits.substring(2,4)
            val year = digits.substring(4)
            String.format(Locale.US, "%s/%s/%s",day, month, year)
        }
    }

    private fun validateMonth(day: String, month: String): String{
        val arr31 = intArrayOf(1,3,5,7,8,10,12)
        val arr30 = intArrayOf(4,6,9,11)
        val arrFeb = intArrayOf(2)
        val zero = "0"
        if(month.toInt() in arrFeb && day.toInt() <= 29){
                if(month.toInt() in 2..9 && month.length == 1){
                    return "$day$zero$month"
                }
                else{
                    return "$day$month"
                }
        }
        else if(month.toInt() in arr31 && day.toInt() <= 31) {
            if(month.toInt() in 2..9 && month.length == 1){
                return return "$day$zero$month"
            }
            else{
                return "$day$month"
            }
        }
        else if(month.toInt() in arr30 && day.toInt() <= 30){
            if(month.toInt() in 2..9 && month.length == 1){
                return "$day$zero$month"
            }else{
                return "$day$month"
            }
        }
        return "$day$month"
    }

    private fun validateDay(day: String): String{
        if(day.length == 1 && day.toInt() in 4..9){
            return "0$day"
        }
        if(day.length == 2 && day.toInt() > 31){
            return day.substring(0,1)
        }
        return day
    }

    private fun validateYear(day: String, month: String, year: String): String{
        if (year.length == 1 && (year.toInt() in 3..9 || year.toInt() == 0 )){
            return ""
        }
        if (year.length == 2 && year.toInt() !in 19..20){
            return year.substring(0,1)
        }
        if(year.length == 4 && day.toInt() == 29 && month.toInt() == 2){
            if(year.toInt() %  4 == 0){
                return year
            }
            else{
                return year.substring(2)
            }
        }
        return  year
    }


    override fun afterTextChanged(editable: Editable?) {
        if(editing) return

        editing = true
        editable?.clear()
        editable?.insert(0, updatedText)
        editing = false
    }
}