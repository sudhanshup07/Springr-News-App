package com.s.springrnewsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.s.springrnewsapp.databinding.FragmentNewsBinding
import com.s.springrnewsapp.ui.newsScreen.NewsArticlesAdapter
import com.s.springrnewsapp.ui.newsScreen.NewsViewModel
import com.s.springrnewsapp.ui.newsScreen.NewsViewModelFactory

class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModelFactory:NewsViewModelFactory

    val args: NewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModelFactory = NewsViewModelFactory(args.newsSource.id)
        viewModel  = ViewModelProvider(this,viewModelFactory).get(NewsViewModel::class.java)
        binding.viewmodel = viewModel

        binding.newsArticleRecyclerView.adapter = NewsArticlesAdapter(NewsArticlesAdapter.OnClickListener{
            viewModel.displayNewsArticleDetails(it)
        })

        viewModel.navigateToSelectedNewsArticle.observe(viewLifecycleOwner, Observer {
            if(it!= null){
                //navigate
                viewModel.displayNewsArticleDetailsComplete()
            }
        })

        binding.newsArticleRecyclerView.addItemDecoration(MarginItemDecoration(16))
        return binding.root
    }
}