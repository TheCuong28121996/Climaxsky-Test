package com.climaxsky.test.ui.home

import android.view.View
import com.bumptech.glide.Glide
import com.climaxsky.test.base.BaseHolder
import com.climaxsky.test.data.ImageEntity
import com.climaxsky.test.other.ViewHolderListener
import kotlinx.android.synthetic.main.item_adapter_home.view.*

class HomeViewHodel(view: View, listener: ViewHolderListener<ImageEntity>): BaseHolder<ImageEntity>(view, listener) {

    override fun bindData(data: ImageEntity?) {
        if (data != null) {
            showReponData(data)
        }else{
            itemView.textViewUserName.visibility = View.GONE
            itemView.imageViewAvatar.visibility = View.GONE
        }
    }

    private fun showReponData(data: ImageEntity){

        var nameVisibility = View.GONE
        if(!data.name.isNullOrEmpty()){
            itemView.textViewUserName.text = data.name
            nameVisibility = View.VISIBLE
        }
        itemView.textViewUserName.visibility = nameVisibility


        var avatarVisibility = View.GONE
        if(!data.avatar.isNullOrEmpty()){
            Glide.with(itemView.imageViewAvatar.context)
                .load(data.avatar)
                .into(itemView.imageViewAvatar)
            avatarVisibility = View.VISIBLE
        }
        itemView.imageViewAvatar.visibility = avatarVisibility

        itemView.setOnClickListener{
            sendListener(data)
        }
    }
}