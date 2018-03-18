package org.freactive.flawnkid.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.freactive.flawnkid.adapters.HomePagerAdapter
import org.freactive.flawnkid.R


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        pager.adapter = HomePagerAdapter(supportFragmentManager)
    }


}
