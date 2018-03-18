package org.freactive.flawnkid

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by akshat on 17/3/18.
 */

class HomePagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        if(position == 0) return FeedsFragment()
        else if (position == 1) return AppListFragment()
        return GameFragment()
    }

    override fun getCount(): Int = 3

}