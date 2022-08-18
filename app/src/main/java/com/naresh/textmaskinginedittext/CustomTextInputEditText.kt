package com.naresh.textmaskinginedittext

import android.content.Context
import android.text.Editable
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CustomTextInputEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
        defStyleAttr: Int = com.google.android.material.R.attr.editTextStyle) : 
        TextInputEditText(context, attrs, defStyleAttr){

    @Override
    override fun getText(): Editable? {
        val text = super.getText()
        if(!text.isNullOrEmpty())
            return  text

        if(!hint.isNullOrEmpty() && Thread.currentThread().stackTrace[3].className
            == TextInputLayout::class.qualifiedName){
            return SpannableStringBuilder(hint)
        }
        return text
    }
            
            
            
            
}