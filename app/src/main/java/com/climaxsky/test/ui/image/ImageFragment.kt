package com.climaxsky.test.ui.image

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.climaxsky.test.R
import com.climaxsky.test.base.BaseFragment
import com.climaxsky.test.data.ImageEntity
import com.climaxsky.test.other.AsynTaskListener
import com.climaxsky.test.utils.InternalStorageProvider
import com.climaxsky.test.utils.PermisstionHelper
import kotlinx.android.synthetic.main.image_fragment.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import org.parceler.Parcels


class ImageFragment : BaseFragment() {

    private lateinit var mList: ArrayList<ImageEntity>
    private val mAdapter by lazy { ImageAdapter() }

    override fun onExtras(bundle: Bundle?) {
        super.onExtras(bundle)
        if (bundle!!.containsKey("data")) {
            val parcelable = bundle.getParcelable<Parcelable>("data")
            mList = Parcels.unwrap(parcelable)
        }
    }

    override fun getLayoutResId(): Int = R.layout.image_fragment

    override fun initView() {

        mAdapter.run { }

        recyclerView.apply {
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            setItemViewCacheSize(20)
            adapter = mAdapter
        }

        checkPermission()
    }

    override fun initData() {
    }

    override fun startObserve() {
    }

    // Option 2 using Recursion
//    private fun getUrlImage() {
//        if (mList.size > 0) {
//            getImageFromUrl(mList.get(0).avatar, object : AsynTaskListener {
//                override fun callback(url: String?) {
//                    mList.removeAt(0)
//                    getUrlImage()
//                    val handler = Handler(Looper.getMainLooper())
//                    handler.post {
//                        mAdapter.addData(url!!)
//                    }
//                }
//            })
//        }
//    }
//
//    private fun getImageFromUrl(url: String?, listener: AsynTaskListener){
//        val fileName = "image" + System.currentTimeMillis()+".jpg"
//
//        Glide.with(requireContext())
//            .asBitmap()
//            .load(url)
//            .into(object : SimpleTarget<Bitmap?>() {
//                override fun onResourceReady(
//                    resource: Bitmap,
//                    transition: Transition<in Bitmap?>?
//                ) {
//                    // Save image
//                    InternalStorageProvider(requireContext()).saveBitmap(resource, fileName)
//                    // Recursion
//                    listener.callback(fileName)
//                }
//            })
//        mAdapter.addData(fileName)
//    }


//  Option 1 using CoroutineScope
    private suspend fun getUrlImage() {
        withContext(IO) {
            for (item in mList) {
                var bitmap: Bitmap? = null

                val getBitmap = launch {
                    bitmap = downLoadImage(item.avatar)
                    Log.d("ImageFragment", "getBitmap ")
                }
                getBitmap.join()

                var pathImage: String? = ""
                val saveImage = async {
                    if (bitmap != null) {
                        pathImage = saveImage(bitmap!!)
                        Log.d("ImageFragment", "saveImage " + pathImage)
                    }
                }.await()

                withContext(Dispatchers.Main){
                    Log.d("ImageFragment", "udateUi " + pathImage)
                    mAdapter.addData(pathImage!!)
                }
            }
        }
    }

    private suspend fun downLoadImage(url: String?): Bitmap? {
        var bitmap: Bitmap? = null

        Glide.with(requireContext())
            .asBitmap()
            .load(url)
            .into(object : SimpleTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    bitmap = resource
                }
            })
        delay(1000)
        return bitmap
    }

    private fun saveImage(bitmap: Bitmap): String {
        val fileName = "image" + System.currentTimeMillis() + ".jpg"
        InternalStorageProvider(requireContext()).saveBitmap(bitmap, fileName)
        return fileName
    }

    private fun checkPermission() {
        val permission = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (!PermisstionHelper.hasPermissions(requireActivity(), permission)) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permission,
                PERMISSIONS_REQUEST
            )
        } else {
            CoroutineScope(IO).launch {
                getUrlImage()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST && grantResults.size > 0) {
            var allgranted = false
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true
                } else {
                    allgranted = false
                    break
                }
            }
            if (allgranted) {
                CoroutineScope(IO).launch {
                    getUrlImage()
                }
            }
        } else {
            Toast.makeText(requireContext(), "Permisstion not granted", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private val PERMISSIONS_REQUEST: Int = 1
    }
}