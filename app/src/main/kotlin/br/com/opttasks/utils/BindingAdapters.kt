package br.com.opttasks.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import br.com.opttasks.BR
import com.google.android.material.floatingactionbutton.FloatingActionButton

object BindingAdapters {

    @BindingAdapter(value = ["entries", "layout", "removeBtnIndex"], requireAll = false)
    @JvmStatic
    fun <T> setEntries(viewGroup: ViewGroup, entries: List<T>?, layoutId: Int?, removeBtnIndex: Int?) {
        viewGroup.removeAllViews()
        val inflater =
            viewGroup.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        entries?.forEachIndexed { index, entry ->
            val binding = DataBindingUtil.inflate(inflater, layoutId!!, viewGroup, true) as ViewDataBinding
            binding.setVariable(BR.data, entry)

            removeBtnIndex?.let {
                ((binding.root as ConstraintLayout).getChildAt(removeBtnIndex) as FloatingActionButton).
                    setOnClickListener {
                        (entries as ArrayList<T>).removeAt(index)
                        (viewGroup as LinearLayout).removeViewAt(index)
                }
            }
        }
    }
}