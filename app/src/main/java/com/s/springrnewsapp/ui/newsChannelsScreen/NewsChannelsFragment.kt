package com.s.springrnewsapp.ui.newsChannelsScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.s.springrnewsapp.MarginItemDecoration
import com.s.springrnewsapp.databinding.FragmentNewsChannelsBinding

class NewsChannelsFragment : Fragment() {

    private lateinit var viewModel: NewsChannelsViewModel
    private lateinit var binding: FragmentNewsChannelsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsChannelsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(NewsChannelsViewModel::class.java)
        binding.viewmodel = viewModel

        binding.newsChannelRecyclerView.adapter = NewsChannelsAdapter(NewsChannelsAdapter.OnClickListener{
            viewModel.displayNewsSourceDetails(it)
        })

        viewModel.navigateToSelectedNewsSource.observe(viewLifecycleOwner, Observer {
            if(it!= null){
                findNavController().navigate(NewsChannelsFragmentDirections.actionNewsChannelsFragmentToNewsFragment(it))
                viewModel.displayNewsSourceDetailsComplete()
            }
        })

        binding.newsChannelRecyclerView.addItemDecoration(MarginItemDecoration(16))
        return binding.root
    }
}