package org.freactive.flawnkid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_feed.*

/**
 * Created by akshat on 18/3/18.
 */

class FeedsFragment: Fragment () {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_feed,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FeedsAdapter(context)
        val a = listOf(Feeds(Source("",""),"aaa","aaa","aaa","aaa","Aaa","aa"))
        feed_recycler.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        adapter.update(a)
        feed_recycler.adapter = adapter

        fetchLatestData(adapter)


    }
}