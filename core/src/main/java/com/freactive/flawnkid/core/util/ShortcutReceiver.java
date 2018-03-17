package com.freactive.flawnkid.core.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.Log;

import com.freactive.flawnkid.core.R;
import com.freactive.flawnkid.core.activity.CoreHome;
import com.freactive.flawnkid.core.interfaces.AbstractApp;
import com.freactive.flawnkid.core.manager.Setup;
import com.freactive.flawnkid.core.model.Item;

public class ShortcutReceiver extends BroadcastReceiver {

    public ShortcutReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getExtras() == null) return;

        String name = intent.getExtras().getString(Intent.EXTRA_SHORTCUT_NAME);
        Intent newIntent = (Intent) intent.getExtras().get(Intent.EXTRA_SHORTCUT_INTENT);
        Drawable shortcutIconDrawable = null;

        try {
            Parcelable iconResourceParcelable = intent.getExtras().getParcelable(Intent.EXTRA_SHORTCUT_ICON_RESOURCE);
            if (iconResourceParcelable != null && iconResourceParcelable instanceof Intent.ShortcutIconResource) {
                Intent.ShortcutIconResource iconResource = (Intent.ShortcutIconResource) iconResourceParcelable;
                Resources resources = context.getPackageManager().getResourcesForApplication(iconResource.packageName);
                if (resources != null) {
                    int id = resources.getIdentifier(iconResource.resourceName, null, null);
                    shortcutIconDrawable = resources.getDrawable(id);
                }
            }
        } catch (Exception ignore) {
        } finally {
            if (shortcutIconDrawable == null)
                shortcutIconDrawable = new BitmapDrawable(context.getResources(), (Bitmap) intent.getExtras().getParcelable(Intent.EXTRA_SHORTCUT_ICON));
        }

        AbstractApp app = Setup.Companion.appLoader().createApp(newIntent);
        Item item;
        if (app != null) {
            item = Item.Companion.newAppItem(app);
        } else {
            item = Item.Companion.newShortcutItem(newIntent, shortcutIconDrawable, name);
        }
        Point preferredPos = CoreHome.Companion.getLauncher().getDesktop().getPages().get(CoreHome.Companion.getLauncher().getDesktop().getCurrentItem()).findFreeSpace();
        if (preferredPos == null) {
            Tool.INSTANCE.toast(CoreHome.Companion.getLauncher(), R.string.toast_not_enough_space);
        } else {
            item.setX(preferredPos.x);
            item.setY(preferredPos.y);
            CoreHome.Companion.getDb().saveItem(item, CoreHome.Companion.getLauncher().getDesktop().getCurrentItem(), Definitions.ItemPosition.Desktop);
            boolean added = CoreHome.Companion.getLauncher().getDesktop().addItemToPage(item, CoreHome.Companion.getLauncher().getDesktop().getCurrentItem());

            Setup.Companion.logger().log(this, Log.INFO, null, "Shortcut installed - %s => Intent: %s (Item type: %s; x = %d, y = %d, added = %b)", name, newIntent, item.getType(), item.getX(), item.getY(), added);
        }
    }
}
