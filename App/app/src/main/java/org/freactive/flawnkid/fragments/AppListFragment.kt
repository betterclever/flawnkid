package org.freactive.flawnkid.fragments

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.apps_list_fragment.*
import org.freactive.flawnkid.AppDetail
import org.freactive.flawnkid.R
import org.freactive.flawnkid.adapters.AppListAdapter
import java.util.*
import kotlin.concurrent.schedule

/**
 * Created by akshat on 17/3/18.
 */

class AppListFragment: Fragment() {

    private lateinit var manager: PackageManager
    private lateinit var apps: ArrayList<AppDetail>
    private val PREFS_KEY = "org.freactive.flawnkid.PREFS"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.apps_list_fragment,container,false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadApps()
        loadListView()
    }

    private fun loadApps() {
        manager = activity.packageManager
        apps = ArrayList()

        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)

        val availableActivities = manager.queryIntentActivities(i, 0)
        for (ri in availableActivities) {
            val app = AppDetail()
            app.label = ri.loadLabel(manager)
            app.name = ri.activityInfo.packageName
            app.icon = ri.activityInfo.loadIcon(manager)
            apps.add(app)
        }
    }

    private fun loadListView() {
        appsList.layoutManager = GridLayoutManager(context,4,GridLayoutManager.VERTICAL,false)
        appsList.adapter = AppListAdapter(context, apps)
    }
}