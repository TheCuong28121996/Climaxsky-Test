package com.climaxsky.test.base

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.climaxsky.test.utils.CommonUtils

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var mProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())

        initView()

        initData()

    }

    open fun getLayoutResId(): Int = 0

    abstract fun initView()

    abstract fun initData()

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showLoadingDialog() {
        hideLoadingDialog()
        mProgressDialog = CommonUtils.showLoadingDialog(this)
    }

    fun hideLoadingDialog() {
        if (this::mProgressDialog.isInitialized && mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }
}