package org.freactive.flawnkid.adapters

import android.content.Context
import android.content.Intent
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.list_item.view.*
import org.freactive.flawnkid.AppDetail
import org.freactive.flawnkid.R
import java.util.*
import kotlin.concurrent.schedule

/**
 * Created by sashank on 18/3/18.
 */

class AppListAdapter(val context: Context, val appsList : ArrayList<AppDetail>) : RecyclerView.Adapter<AppListAdapter.AppListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AppListViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false))

    override fun getItemCount() = appsList.size

    override fun onBindViewHolder(holder: AppListViewHolder, position: Int) {
        holder.bindView(context, appsList[position])
    }

    class AppListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        private val PREFS_KEY = "org.freactive.flawnkid.PREFS"

        fun bindView(context: Context, app: AppDetail ) {
            itemView.appIcon.setImageDrawable(app.icon)
            itemView.appLabel.text = app.label

            itemView.setOnClickListener {
                val i = context.packageManager.getLaunchIntentForPackage(app.name.toString())

                val sharedPref = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
                val count = sharedPref.getInt(app.label.toString(),0)

                if (count == 0) {
                    with (sharedPref.edit()) {
                        putInt(app.label.toString(), 1)
                        commit()
                    }
                    context.startActivity(i)

                    Timer("",false).schedule(5000) {
                        val i = Intent(Intent.ACTION_MAIN)
                        i.addCategory(Intent.CATEGORY_HOME)
                        context.startActivity(i)
                    }
                } else {
                    //viewPager.currentItem = 3

                    Toast.makeText(context, "You're out of Pojos. Let's play some games to earn more!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}