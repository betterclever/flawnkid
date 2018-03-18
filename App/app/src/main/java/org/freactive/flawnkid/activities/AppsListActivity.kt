/*
package org.freactive.flawnkid.activities

import android.os.Bundle
import android.content.Intent
import android.view.ViewGroup
import android.app.Activity
import android.view.View
import kotlinx.android.synthetic.main.activity_apps_list.*
import android.content.pm.PackageManager
import android.widget.*
import org.freactive.flawnkid.AppDetail
import org.freactive.flawnkid.R


class AppsListActivity : Activity() {

    private lateinit var manager: PackageManager
    private lateinit var apps: ArrayList<AppDetail>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps_list)

        loadApps()
        loadListView()
        addClickListener()
    }

    private fun loadApps() {
        manager = packageManager
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

        val adapter = object : ArrayAdapter<AppDetail>(this,
                R.layout.list_item,
                apps) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
                var convertView = convertView
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.list_item, null)
                }

                val appIcon = convertView!!.findViewById(R.id.item_app_icon) as ImageView
                appIcon.setImageDrawable(apps[position].icon)

                //val appLabel = convertView!!.findViewById(R.id.item_app_label) as TextView
                //appLabel.setText(apps.get(position).label)

                val appName = convertView.findViewById(R.id.item_app_name) as TextView
                appName.text = apps[position].name

                return convertView
            }
        }

        apps_list.adapter = adapter
    }

    private fun addClickListener() {
        apps_list.onItemClickListener = AdapterView.OnItemClickListener { _, _, pos, _ ->
            val i = manager.getLaunchIntentForPackage(apps[pos].name.toString())
            this@AppsListActivity.startActivity(i)
        }
    }
}
*/
