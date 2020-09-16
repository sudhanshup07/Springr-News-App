package com.s.springrnewsapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.s.springrnewsapp.databinding.FragmentNewsDetailWebViewBinding


class NewsDetailWebViewFragment : Fragment() {

    private lateinit var binding:FragmentNewsDetailWebViewBinding
    private val args: NewsDetailWebViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailWebViewBinding.inflate(inflater)

        setUpWebView()

        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun setUpWebView(){
        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(args.newsUrl)
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                binding.webViewProgressBar.visibility = View.GONE
            }
        }
    }
}