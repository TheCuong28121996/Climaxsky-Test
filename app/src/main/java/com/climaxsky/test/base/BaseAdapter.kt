package com.climaxsky.test.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.climaxsky.test.other.ViewHolderListener

abstract class BaseAdapter<T>(diffUtil: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, BaseHolder<T>>(diffUtil) {

    private var dataSource: MutableList<T> = ArrayList()

    private lateinit var listener: ViewHolderListener<T>

    open fun setOnClickListener(listener: ViewHolderListener<T>) {
        this.listener = listener
    }

    open fun getListener(): ViewHolderListener<T> {
        return this.listener
    }

    open fun addData(data: Collection<T>) {
        val size = data.size
        if (size > 0) {
            val currentTotal = this.dataSource.size
            this.dataSource.addAll(data)
            notifyItemRangeInserted(currentTotal, (currentTotal + size))
        }
    }

    open fun addData(data: T) {
        this.dataSource.add(data)
        notifyItemInserted(this.dataSource.size)
    }

    open fun addData(data: T, position: Int) {
        var pos = position
        if (pos < 0) {
            pos = this.dataSource.size
        }
        this.dataSource.add(pos, data)
        notifyItemChanged(pos)
    }

    open fun getLastItem(): T? {
        return if (this.dataSource.size > 0) this.dataSource[this.dataSource.size - 1] else null
    }

    open fun getFirstItem(): T? {
        return if (this.dataSource.size > 0) this.dataSource[0] else null
    }

    open fun getItemAt(position: Int): T? {
        if (position >= 0 && (itemCount > position)) {
            return this.dataSource.get(position)
        }
        return null
    }

    open fun removeItem(position: Int) {
        if (0 < position && position < this.dataSource.size) {
            this.dataSource.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    open fun getListItem(): Collection<T> {
        return dataSource
    }

    fun createView(resource: Int, parent: ViewGroup, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(parent.context).inflate(resource, parent, attachToRoot)
    }

    override fun onBindViewHolder(holder: BaseHolder<T>, position: Int) {
        val item = getItemAt(position)
        holder.bindData(item)
    }

    override fun getItemCount(): Int {
        return this.dataSource.size
    }
}