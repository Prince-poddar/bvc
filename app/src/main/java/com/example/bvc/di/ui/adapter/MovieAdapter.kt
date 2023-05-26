package com.example.bvc.di.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bvc.databinding.LayoutMoviesItemBinding
import com.example.bvc.response.Search

class MovieAdapter(
    private val context: Context,
    private val moviesList: ArrayList<Search>,

    ) :
    RecyclerView.Adapter<MovieAdapter.Holder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        val bindingLayout =
            LayoutMoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(bindingLayout)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }


    class Holder(val binding: LayoutMoviesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var searchItem: Search = moviesList[position]
        if (searchItem != null) {
            holder.binding.ivPoster.let {
                Glide.with(context).load(searchItem.Poster).into(it)
            }
            holder.binding.tvTitle.text = (searchItem.Title)
            holder.binding.tvType.text = (searchItem.Type)
            holder.binding.tvYear.text = (searchItem.Year)
            holder.binding.tvImdbId.text = (searchItem.imdbID)


        }
    }

}