package com.example.movies.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movies.R
import com.example.movies.data.model.response.NowPlayResultEntityResponse
import com.example.movies.data.model.response.PopulateResultEntityResponse
import com.example.movies.databinding.FragmentDetailBinding
import com.example.movies.ui.viewmodel.DetailViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentDetailBinding.inflate(inflater, container, false)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var entityNowPlay: NowPlayResultEntityResponse? = null
        var entityPopulate: PopulateResultEntityResponse? = null
        arguments?.getSerializable("entityNowPlay")?.let {
            entityNowPlay =
                arguments?.getSerializable("entityNowPlay") as NowPlayResultEntityResponse
        } ?: kotlin.run {
            entityPopulate =
                arguments?.getSerializable("entityPopulate") as PopulateResultEntityResponse
        }


        Glide.with(this.requireContext())
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

        this.binding.tvTitle.text = entityNowPlay?.let {
            entityNowPlay!!.title
        } ?: kotlin.run {
            entityPopulate!!.title
        }
        this.binding.tvOverview.text =
            entityNowPlay?.let {
                entityNowPlay!!.overview
            } ?: kotlin.run {
                entityPopulate!!.overview
            }

        this.viewModel.getVideoViewModel(
            movie_id =
            entityNowPlay?.let {
                entityNowPlay!!.id.toString()
            } ?: kotlin.run {
                entityPopulate!!.id.toString()
            }
        )
        this.viewModel.mldViewModel.observe(viewLifecycleOwner) { request ->
            request?.let { entity ->
                entity.let {
                    if (entity.results.isNotEmpty()) {
                        if (entity.results[0].site == "YouTube") {
                            try {
                                binding.videoView.addYouTubePlayerListener(object :
                                    AbstractYouTubePlayerListener() {
                                    override fun onReady(youTubePlayer: YouTubePlayer) {
                                        super.onReady(youTubePlayer)
                                        youTubePlayer.loadVideo(entity.results[0].key, 0F)
                                    }
                                })
                            } catch (e: Exception) {
                                Log.e("Error", "Error video ${e.message.toString()}")
                                e.printStackTrace()
                            }
                        } else {
                            binding.videoView.visibility = View.GONE
                        }
                    }
                }
            } ?: kotlin.run {
                Toast.makeText(
                    this.requireContext(),
                    "Error al consultar los videos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}