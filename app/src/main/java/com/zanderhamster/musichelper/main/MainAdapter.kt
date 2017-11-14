package com.zanderhamster.musichelper.main

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.zanderhamster.musichelper.R

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import com.zanderhamster.musichelper.db.SongModel

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var items: List<SongModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_list, parent, false)
        return MainViewHolder(view)
    }

    fun setItems(items: List<SongModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val song = items[position]
        val color: Int = if (position % 2 == 0) R.color.background_item else R.color.white
        holder.tvName.setBackgroundColor(ContextCompat.getColor(holder.tvName.context, color))
        if (song.number < 0) {
            holder.tvName.setBackgroundColor(ContextCompat.getColor(holder.tvName.context, R.color.colorAccent))
        }
        holder.tvNumber.text = song.number.toString()
        holder.tvName.text = holder.tvName.resources.getString(
                R.string.song_name_placeholder,
                song.name, song.artist)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        @BindView(R.id.item_list_name)
        lateinit var tvName: TextView
        @BindView(R.id.item_list_number)
        lateinit var tvNumber: TextView

        init {
            ButterKnife.bind(this, v)
        }
    }

}

