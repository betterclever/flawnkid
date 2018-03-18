package org.freactive.flawnkid.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.freactive.flawnkid.R

/**
 * Created by akshat on 18/3/18.
 */

class FeedsFragment: Fragment () {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_feed,container,false)
    }


}