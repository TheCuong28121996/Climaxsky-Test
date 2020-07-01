package com.climaxsky.test

import com.climaxsky.test.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {}

    override fun initData() {
    }
}