package com.freactive.flawnkid.core.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.util.Log

import com.freactive.flawnkid.core.R
import com.freactive.flawnkid.core.activity.CoreHome
import com.freactive.flawnkid.core.interfaces.AbstractApp
import com.freactive.flawnkid.core.manager.Setup
import com.freactive.flawnkid.core.model.Item
import com.freactive.flawnkid.core.widget.CellContainer
import com.freactive.flawnkid.core.widget.OverviewPage

class ShortcutReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.extras == null) return

        val name = intent.extras!!.getString(Intent.EXTRA_SHORTCUT_NAME)
        val newIntent = intent.extras!!.get(Intent.EXTRA_SHORTCUT_INTENT) as Intent
        var shortcutIconDrawable: Drawable? = null

        try {
            val iconResourceParcelable = intent.extras!!.getParcelable<Parcelable>(Intent.EXTRA_SHORTCUT_ICON_RESOURCE)
            if (iconResourceParcelable != null && iconResourceParcelable is Intent.ShortcutIconResource) {
                val resources = context.packageManager.getResourcesForApplication(iconResourceParcelable.packageName)
                if (resources != null) {
                    val id = resources.getIdentifier(iconResourceParcelable.resourceName, null, null)
                    shortcutIconDrawable = resources.getDrawable(id)
                }
            }
        } catch (ignore: Exception) {
        } finally {
            if (shortcutIconDrawable == null)
                shortcutIconDrawable = BitmapDrawable(context.resources, intent.extras!!.getParcelable<Parcelable>(Intent.EXTRA_SHORTCUT_ICON) as Bitmap)
        }

        val app = Setup.appLoader().createApp(newIntent)
        val item: Item
        if (app != null) {
            item = Item.newAppItem(app)
        } else {
            item = Item.newShortcutItem(newIntent, shortcutIconDrawable, name!!)
        }
        val page = CoreHome.launcher!!.getDesktop().pages[CoreHome.launcher!!.getDesktop().currentItem]
        if (page is OverviewPage) return
            (page as CellContainer).apply {
            val preferredPos  = this.findFreeSpace()
            if (preferredPos == null) {
                Tool.toast(CoreHome.launcher!!, R.string.toast_not_enough_space)
            } else {
                item.x = preferredPos.x
                item.y = preferredPos.y
                CoreHome.db.saveItem(item, CoreHome.launcher!!.getDesktop().currentItem, Definitions.ItemPosition.Desktop)
                val added = CoreHome.launcher!!.getDesktop().addItemToPage(item, CoreHome.launcher!!.getDesktop().currentItem)

                //Setup.logger().log(this, Log.INFO, null, "Shortcut installed - %s => Intent: %s (Item type: %s; x = %d, y = %d, added = %b)", name, newIntent, item.type, item.x, item.y, added)
            }
        }
    }
}
