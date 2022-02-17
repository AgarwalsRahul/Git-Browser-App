package com.example.gitbrowser.presentation.util


import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout


class CustomTextInputLayout  : TextInputLayout {

    constructor(context:Context,attributeSet: AttributeSet) :super(context,attributeSet)

    constructor(context: Context):super(context)
    constructor(context: Context,attributeSet: AttributeSet,styleAttr:Int):super(context,attributeSet,styleAttr)

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        clearEditTextColorfilter()
    }

    override fun setError( error: CharSequence?) {
        super.setError(error)
        clearEditTextColorfilter()
    }

    private fun clearEditTextColorfilter() {
        val editText = editText
        if (editText != null) {
            val background = editText.background
            background?.clearColorFilter()
        }
    }
}