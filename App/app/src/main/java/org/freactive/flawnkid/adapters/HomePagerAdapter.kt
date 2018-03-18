package org.freactive.flawnkid.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import org.freactive.flawnkid.fragments.AppListFragment
import org.freactive.flawnkid.fragments.FeedsFragment
import org.freactive.flawnkid.fragments.GameFragment

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