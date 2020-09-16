package com.s.springrnewsapp.ui.newsDetailScreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.s.springrnewsapp.R
import com.s.springrnewsapp.databinding.FragmentNewsDetailBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import java.io.File
import java.io.FileOutputStream

@Suppress("DEPRECATION")
class NewsDetailFragment : Fragment() {

    private lateinit var viewModel: NewsDetailViewModel
    private lateinit var binding: FragmentNewsDetailBinding

    private val args: NewsDetailFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(NewsDetailViewModel::class.java)

        Glide.with(this)
            .load(args.newsArticle.urlToImage)
            .into(binding.newsDetailImageView)

        binding.newsDetailAuthor.text = "- ${args.newsArticle.author}"
        binding.newsArticleTitle.text = args.newsArticle.title
        binding.newsArticleDescriptionTv.text = args.newsArticle.description
        binding.shareImageView.setOnClickListener {
            shareUrl()
        }

        binding.readMoreText.setOnClickListener {
            findNavController().navigate(
                NewsDetailFragmentDirections
                    .actionNewsDetailFragmentToNewsDetailWebViewFragment(args.newsArticle.url)
            )
        }

        binding.saveImageButton.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this.requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED){

                //ask for permission
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
                )
            }else{
                saveImage()
            }
        }
        return binding.root
    }

    private fun shareUrl(){
        val intent= Intent()
        intent.action=Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, args.newsArticle.url)
        intent.type="text/plain"
        startActivity(Intent.createChooser(intent, "Share To:"))
    }

    private val target = object :Target{
        override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom?) {
            Thread {
                val folderName = getString(R.string.app_name)
                val file = File(
                    Environment.getExternalStoragePublicDirectory(folderName).path
                )

                if (!file.exists()) {
                  file.mkdirs()
                }


                val file1 = File(
                    Environment.getExternalStoragePublicDirectory(folderName).path + "/" + args.newsArticle.publishedAt + ".jpg"
                )
                try {
                    file1.createNewFile()
                    val outputStream = FileOutputStream(file1)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.close()

                    showSnackBar("Image Saved !!")
                    galleryAddPicBroadCast(file1)


                } catch (e: Exception) {
                    showSnackBar(e.message.toString())
                }
            }.start()
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            showSnackBar(e?.message.toString())
        }
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        }
    }

    private fun galleryAddPicBroadCast(file:File) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val contentUri = Uri.fromFile(file)
        mediaScanIntent.data = contentUri
        requireContext().sendBroadcast(mediaScanIntent)
    }

    private fun saveImage() {
        try {
            Picasso.get()
                .load(args.newsArticle.urlToImage)
                .into(target)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveImage()
                }
            }
        }
    }

    fun showSnackBar(message: String){
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}