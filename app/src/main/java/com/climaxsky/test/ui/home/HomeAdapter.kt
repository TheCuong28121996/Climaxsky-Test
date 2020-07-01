package com.climaxsky.test.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.climaxsky.test.R
import com.climaxsky.test.base.BaseAdapter
import com.climaxsky.test.base.BaseHolder
import com.climaxsky.test.data.ImageEntity

class HomeAdapter: BaseAdapter<ImageEntity>(diffUtil){

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<ImageEntity>(){
            override fun areItemsTheSame(oldItem: ImageEntity, newItem: ImageEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ImageEntity, newItem: ImageEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<ImageEntity> {
        return HomeViewHodel(
            createView(R.layout.item_adapter_home, parent), getListener()
        )
    }
}