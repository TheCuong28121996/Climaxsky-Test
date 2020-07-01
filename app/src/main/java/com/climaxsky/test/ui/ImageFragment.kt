package com.climaxsky.test.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.climaxsky.test.R
import com.climaxsky.test.base.BaseFragment
import com.climaxsky.test.other.OnLoadMoreListener
import com.climaxsky.test.utils.RecyclerViewLoadMoreScroll
import kotlinx.android.synthetic.main.image_fragment.*
import android.os.Handler
import com.climaxsky.test.data.ImageEntity
import com.climaxsky.test.other.ViewHolderListener
import com.climaxsky.test.utils.Event


class ImageFragment : BaseFragment() {

    private lateinit var viewModel: ImageViewModel
    private val mAdapter by lazy { ImageAdapter() }
    private var page: Int = 0
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun getLayoutResId(): Int = R.layout.image_fragment

    override fun initView() {

        if (!this::viewModel.isInitialized) {
            viewModel = ViewModelProviders.of(this@ImageFragment)
                .get(ImageViewModel::class.java)
            setObserveLive(viewModel)
        }

        mAdapter.run {
            setOnClickListener(imageListener)
        }

        mLayoutManager = LinearLayoutManager(requireContext())

        recyclerview.apply {
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            setItemViewCacheSize(20)
            layoutManager = mLayoutManager
            adapter = mAdapter
        }

        setRVScrollListener()

        floating_action_button.setOnClickListener {

        }
    }

    override fun initData() {
        viewModel.getImage(requireContext(), page)
    }

    override fun startObserve() {
        viewModel.apply {
            _reporepoImage.observe(this@ImageFragment, Observer {
                if (it != null) {
                    mAdapter.addData(it)
                }
            })
        }
    }

    private fun setRVScrollListener() {
        scrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                LoadMoreData()
            }
        })
        recyclerview.addOnScrollListener(scrollListener)
    }

    private fun LoadMoreData() {
        Handler().postDelayed({
            page += 1
            viewModel.getImage(requireContext(), page)
            scrollListener.setLoaded()
        }, 1000)
    }

    private val imageListener = object: ViewHolderListener<ImageEntity>{
        override fun itemClicked(data: ImageEntity, positon: Int) {
            viewModel.eventMessage.value = Event(data.name!!)
        }
    }
}