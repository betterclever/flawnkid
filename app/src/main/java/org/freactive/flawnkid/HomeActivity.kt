package org.freactive.flawnkid

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        pager.adapter = HomePagerAdapter(supportFragmentManager)
    }


}
