package com.example.pregnagrowth.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener

class PasswordEditText : AppCompatEditText {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        addTextChangedListener(onTextChanged = { s, _, _, _ ->
            if(s.toString().length < 8) {
                setError("Password tidak boleh kurang dari 8 karakter", null)
            }
            else {
                error = null
            }
        })
    }
}