package br.com.lcl.test.screens.callbacks

import android.view.View

interface OnClickAdapterCallback<T> {
    fun onItemClicked(item: T)
}