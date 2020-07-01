package com.climaxsky.test.ui.image

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.climaxsky.test.R
import com.climaxsky.test.base.BaseAdapter
import com.climaxsky.test.base.BaseHolder

class ImageAdapter: BaseAdapter<String>(diffUtil) {

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<String>(){
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<String> {
        return ImageViewHolder(
            createView(R.layout.item_adapter_image, parent)
        )
    }
}