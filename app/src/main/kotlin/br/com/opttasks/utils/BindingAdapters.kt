package br.com.opttasks.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import br.com.opttasks.BR
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

object BindingAdapters {

    @BindingAdapter("entries", "layout", "removeBtnIndex", requireAll = false)
    @JvmStatic
    fun <T> setEntries(viewGroup: ViewGroup, entries: List<T>?, layoutId: Int?, removeBtnIndex: Int?) {
        viewGroup.removeAllViews()
        val inflater =
            viewGroup.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        entries?.forEach { entry ->
            val binding = DataBindingUtil.inflate(inflater, layoutId!!, viewGroup, true) as ViewDataBinding
            binding.setVariable(BR.data, entry)

            removeBtnIndex?.let {
                val layout = binding.root as ConstraintLayout

                (layout.getChildAt(removeBtnIndex) as FloatingActionButton).
                    setOnClickListener {
                        (entries as ArrayList).remove(entry)
                        (viewGroup as LinearLayout).removeView(layout)
                }
            }
        }
    }

    @BindingAdapter(value = ["isMandatory", "errorMessage"])
    @JvmStatic
    fun setIsMandatory(view: TextInputEditText, isMandatory: Boolean, errorMessage: String) {
        if (isMandatory) {
            val layout = view.parent.parent as TextInputLayout
            if (view.text.isNullOrEmpty()) layout.error = errorMessage

            val validate = { s: Editable? ->
                layout.error = if (s.isNullOrEmpty()) errorMessage else null
            }

            view.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) { validate(s) }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            })

            view.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) validate((v as TextInputEditText).text)
            }
        }
    }
}