package com.example.movies.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.data.model.response.NowPlayResultEntityResponse
import com.example.movies.data.model.response.PopulateResultEntityResponse
import com.example.movies.databinding.FragmentHomeBinding
import com.example.movies.ui.adapter.INowPlay
import com.example.movies.ui.adapter.NowPlayAdapter
import com.example.movies.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), INowPlay {

    private lateinit var binding: FragmentHomeBinding
    private var list: List<NowPlayResultEntityResponse>? = null

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        serviceNowPlay()

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbNowPlay -> serviceNowPlay()
                R.id.rbPopulate -> populate()
            }
        }

//        binding.rvNowPlay.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//
//                if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN) && recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE) {
//                    Toast.makeText(requireContext(), "Last", Toast.LENGTH_LONG).show()
//                }
//            }
//        })

        viewModel.isLoading.observe(viewLifecycleOwner) {
            this.binding.progress.isVisible = it
        }
    }

    private fun serviceNowPlay() {
        this.viewModel.getNowPlayViewModel(this.requireContext())
        this.viewModel.mldNowPlayViewModel.observe(viewLifecycleOwner) { response ->
            response?.let { entity ->
                list = entity.results!!
                this.binding.rvNowPlay.apply {
                    layoutManager = LinearLayoutManager(
                        this@HomeFragment.requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    adapter =
                        NowPlayAdapter(listNowPlay = entity.results!!, listener = this@HomeFragment)
                }
            } ?: kotlin.run {
                Toast.makeText(
                    this.requireContext(),
                    "Error al consultar las peliculas",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun populate() {
        this.viewModel.getPopulateViewModel(this.requireContext())
        this.viewModel.mldPopulateViewModel.observe(viewLifecycleOwner) { response ->
            response?.let { entity ->
                this.binding.rvNowPlay.apply {
                    layoutManager = LinearLayoutManager(
                        this@HomeFragment.requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    adapter =
                        NowPlayAdapter(listPopulate = entity.results, listener = this@HomeFragment)
                }
            } ?: kotlin.run {
                Toast.makeText(
                    this.requireContext(),
                    "Error al consultar las peliculas",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onNowPlayClickListener(entity: NowPlayResultEntityResponse) {
        val bundle = bundleOf("entityNowPlay" to entity)
        Navigation.findNavController(binding.root).navigate(R.id.to_detailFragment, bundle)
    }

    override fun onPopulateClickListener(entity: PopulateResultEntityResponse) {
        val bundle = bundleOf("entityPopulate" to entity)
        Navigation.findNavController(binding.root).navigate(R.id.to_detailFragment, bundle)
    }
}