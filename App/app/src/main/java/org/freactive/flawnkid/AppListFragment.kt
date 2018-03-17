package org.freactive.flawnkid


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_apps_list.*
import java.util.*
import kotlin.concurrent.schedule

/**
 * Created by akshat on 17/3/18.
 */

class AppListFragment: Fragment() {

    private lateinit var manager: PackageManager
    private lateinit var apps: ArrayList<AppDetail>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.apps_list_fragment,container,false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadApps()
        loadListView()
        addClickListener()
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

        val adapter = object : ArrayAdapter<AppDetail>(activity,
                R.layout.list_item,
                apps) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
                var convertView = convertView
                if (convertView == null) {
                    convertView = activity.layoutInflater.inflate(R.layout.list_item, null)
                }

                val appIcon = convertView!!.findViewById(R.id.item_app_icon) as ImageView
                appIcon.setImageDrawable(apps.get(position).icon)

                val appLabel = convertView.findViewById(R.id.item_app_label) as TextView
                appLabel.setText(apps.get(position).label)

                val appName = convertView.findViewById(R.id.item_app_name) as TextView
                appName.setText(apps.get(position).name)

                return convertView
            }
        }

        apps_list.setAdapter(adapter)
    }

    private fun addClickListener() {
        apps_list.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(av: AdapterView<*>, v: View, pos: Int,
                                     id: Long) {

                val i = manager.getLaunchIntentForPackage(apps.get(pos).name.toString())
                activity.startActivityForResult(i,100)

                Timer("",false).schedule(5000) {
                    val i = Intent(Intent.ACTION_MAIN)
                    i.addCategory(Intent.CATEGORY_HOME)
                    startActivity(i)
                }
            }
        })
    }
}