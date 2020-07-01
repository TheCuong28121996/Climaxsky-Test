package com.climaxsky.test.utils

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import com.climaxsky.test.R

object CommonUtils {
    // This is func show loading
    fun showLoadingDialog(context: Context?): Dialog {
        val mProgressDialog = Dialog(context!!)
        mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mProgressDialog.setCancelable(false)
        mProgressDialog.setContentView(R.layout.progress_dialog)
        mProgressDialog.show()
        if (mProgressDialog.window != null) {
            mProgressDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val lp = WindowManager.LayoutParams()
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT
                lp.gravity = Gravity.CENTER
                mProgressDialog.window!!.attributes = lp
            } else {
                mProgressDialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
            if (!mProgressDialog.isShowing) {
                mProgressDialog.show()
            }
        }
        return mProgressDialog
    }
}