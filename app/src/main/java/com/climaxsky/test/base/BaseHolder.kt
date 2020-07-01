package com.climaxsky.test.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.climaxsky.test.other.ViewHolderListener

abstract class BaseHolder<T>: RecyclerView.ViewHolder {

    constructor(itemView: View): super(itemView)

    constructor(itemView: View, listener: ViewHolderListener<T>): super(itemView){
        this.listener = listener
    }

    private var listener: ViewHolderListener<T>? = null

    abstract fun bindData(data: T?)

    open fun sendListener(data: T){
        this.listener!!.itemClicked(data, adapterPosition)
    }
}