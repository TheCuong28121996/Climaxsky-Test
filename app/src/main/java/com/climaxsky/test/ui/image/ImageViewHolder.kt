package com.climaxsky.test.ui.image

import android.view.View
import com.bumptech.glide.Glide
import com.climaxsky.test.base.BaseHolder
import com.climaxsky.test.utils.InternalStorageProvider
import kotlinx.android.synthetic.main.item_adapter_image.view.*

class ImageViewHolder(view: View): BaseHolder<String>(view) {

    override fun bindData(data: String?) {
        if (data != null) {
            showReponData(data)
        }else{
            itemView.image.visibility = View.GONE
        }
    }

    private fun showReponData(data: String){
        Glide.with(itemView.context)
            .load(InternalStorageProvider(itemView.context).loadBitmap(data))
            .into(itemView.image)
        itemView.image.visibility = View.VISIBLE
    }

}