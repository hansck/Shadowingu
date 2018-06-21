package com.hansck.shadowingu.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.model.Word
import com.hansck.shadowingu.presentation.customview.OnWordSelected
import kotlinx.android.synthetic.main.item_word.view.*
import java.util.*

/**
 * Created by Hans CK on 20-Jun-18.
 */
class WordAdapter(private val items: ArrayList<Word>, private val listener: OnWordSelected)
    : RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    private fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordAdapter.ViewHolder {
        val inflatedView = parent.inflate(R.layout.item_word)
        return ViewHolder(inflatedView, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View, private val listener: OnWordSelected) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        lateinit var item: Word

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onWordSelected(item)
        }

        fun bind(word: Word) = with(itemView) {
            item = word
            kanji.text = word.kanji
        }
    }
}