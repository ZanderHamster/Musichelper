package com.zanderhamster.musichelper.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.zanderhamster.musichelper.R

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import com.zanderhamster.musichelper.db.SongEntity
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
        holder.tvName.text = items[position].name.plus(items[position].artist)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        @BindView(R.id.item_list_name)
        lateinit var tvName: TextView

        init {
            ButterKnife.bind(this, v)
        }
    }

}

