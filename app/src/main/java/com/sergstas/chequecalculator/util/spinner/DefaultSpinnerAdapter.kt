package com.sergstas.chequecalculator.util.spinner

import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.sergstas.chequecalculator.App
import com.sergstas.chequecalculator.R

class DefaultSpinnerAdapter(labels: List<String>): ArrayAdapter<String>(
    App.instance,
    R.layout.spinner_item_text,
    labels,
) {
    init {
        setDropDownViewResource(R.layout.spinner_item_text)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val view = LayoutInflater.from(App.instance).inflate(R.layout.spinner_item_text, parent, false)
        convertView?.findViewById<TextView>(R.id.sit_tv_text)?.text = getItem(position)
        return super.getView(position, convertView, parent)
    }
}