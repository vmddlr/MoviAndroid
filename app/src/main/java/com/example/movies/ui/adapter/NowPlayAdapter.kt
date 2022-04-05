package com.example.movies.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movies.R
import com.example.movies.data.model.response.NowPlayResultEntityResponse
import com.example.movies.data.model.response.PopulateResultEntityResponse
import com.example.movies.databinding.ItemNowPlayLayoutBinding

class NowPlayAdapter(
    private val listNowPlay: List<NowPlayResultEntityResponse>? = null,
    private val listPopulate: List<PopulateResultEntityResponse>? = null,
    private val listener: INowPlay
) : RecyclerView.Adapter<NowPlayAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context = parent.context

        val view = LayoutInflater
            .from(this.context)
            .inflate(R.layout.item_now_play_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var entityNowPlay: NowPlayResultEntityResponse? = null
        var entityPopulate: PopulateResultEntityResponse? = null
        listNowPlay?.let {
            entityNowPlay = listNowPlay[position]
        } ?: kotlin.run {
            entityPopulate = listPopulate!![position]

        }


        with(holder) {
            binding.tvTitle.text = entityNowPlay?.let {
                entityNowPlay!!.title
            } ?: kotlin.run {
                entityPopulate!!.title
            }

            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/${
                    entityNowPlay?.let {
                        entityNowPlay!!.poster_path
                    } ?: kotlin.run {
                        entityPopulate!!.poster_path
                    }
                }")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_movies)
                .centerCrop()
                .into(binding.ivPoster)

            binding.cardView.setOnClickListener {
                listNowPlay?.let {
                    listener.onNowPlayClickListener(entityNowPlay!!)
                } ?: kotlin.run {
                    listener.onPopulateClickListener(entityPopulate!!)
                }
            }
        }

    }

    override fun getItemCount(): Int = listNowPlay?.let {
        listNowPlay.size
    } ?: kotlin.run {
        listPopulate!!.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemNowPlayLayoutBinding.bind(view)
    }
}