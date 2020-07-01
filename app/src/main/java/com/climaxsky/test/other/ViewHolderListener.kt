package com.climaxsky.test.other

interface ViewHolderListener<T> {

    fun itemClicked(data: T, positon: Int)
}