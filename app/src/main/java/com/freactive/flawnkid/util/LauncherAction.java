package com.freactive.flawnkid.util;

import android.app.NotificationManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.freactive.flawnkid.R;
import com.freactive.flawnkid.activity.Home;
import com.freactive.flawnkid.activity.MinibarEditActivity;
import com.freactive.flawnkid.activity.SettingsActivity;
import com.freactive.flawnkid.core.util.Tool;
import com.freactive.flawnkid.viewutil.DialogHelper;

public class LauncherAction {

    static ActionDisplayItem[] actionDisplayItems = new ActionDisplayItem[]{
            new ActionDisplayItem(Action.EditMinBar, Home.Companion.get_resources().getString(R.string.minibar_0), R.drawable.ic_mode_edit_black_24dp),
            new ActionDisplayItem(Action.SetWallpaper, Home.Companion.get_resources().getString(R.string.minibar_1), R.drawable.ic_photo_black_24dp),
            new ActionDisplayItem(Action.LockScreen, Home.Companion.get_resources().getString(R.string.minibar_2), R.drawable.ic_lock_black_24dp),
            new ActionDisplayItem(Action.ClearRam, Home.Companion.get_resources().getString(R.string.minibar_3), R.drawable.ic_donut_large_black_24dp),
            new ActionDisplayItem(Action.DeviceSettings, Home.Companion.get_resources().getString(R.string.minibar_4), R.drawable.ic_settings_applications_black_24dp),
            new ActionDisplayItem(Action.LauncherSettings, Home.Companion.get_resources().getString(R.string.minibar_5), R.drawable.ic_settings_launcher_black_24dp),
            new ActionDisplayItem(Action.VolumeDialog, Home.Companion.get_resources().getString(R.string.minibar_7), R.drawable.ic_volume_up_black_24dp),
            new ActionDisplayItem(Action.OpenAppDrawer, Home.Companion.get_resources().getString(R.string.minibar_8), R.drawable.ic_apps_dark_24dp)
    };

    public static boolean clearingRam = false;

    public static void RunAction(@Nullable ActionItem actionItem, final Context context) {
        if (actionItem != null)
            switch (actionItem.action) {
                case LaunchApp:
                    Tool.startApp(context, actionItem.extraData);
                    break;

                default:
                    RunAction(actionItem.action, context);
            }
    }

    public static void RunAction(Action action, final Context context) {
        switch (action) {
            case EditMinBar:
                context.startActivity(new Intent(context, MinibarEditActivity.class));
                break;
            case SetWallpaper:
                DialogHelper.INSTANCE.setWallpaperDialog(context);
                break;
            case LockScreen:
                try {
                    ((DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE)).lockNow();
                } catch (Exception e) {
                    DialogHelper.INSTANCE.alertDialog(context, "Device Admin Required", "To lock your screen by gesture, OpenLauncher require Device Administration, please enable it in the settings to use this features. The force-lock policy will only be used for locking the screen via gesture.", "Enable", new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            Tool.toast(context, context.getString(R.string.toast_device_admin_required));
                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.DeviceAdminSettings"));
                            context.startActivity(intent);
                        }
                    });
                }
                break;
            case ClearRam:
                if (clearingRam) {
                    break;
                }
                new ClearRamAsyncTask().execute();
                break;
            case DeviceSettings:
                context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                break;
            case LauncherSettings:
                context.startActivity(new Intent(context, SettingsActivity.class));
                break;
            case OpenAppDrawer:
                Home.Companion.getLauncher().openAppDrawer();
                break;
            case VolumeDialog:
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    try {
                        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                        audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamVolume(AudioManager.STREAM_RING), AudioManager.FLAG_SHOW_UI);
                    } catch (Exception e) {
                        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        if (!mNotificationManager.isNotificationPolicyAccessGranted()) {
                            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                            context.startActivity(intent);
                        }
                    }
                } else {
                    AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamVolume(AudioManager.STREAM_RING), AudioManager.FLAG_SHOW_UI);
                }
                break;
        }
    }

    public static ActionDisplayItem getActionItemFromString(String string) {
        for (ActionDisplayItem item : actionDisplayItems) {
            if (item.label.toString().equals(string)) {
                return item;
            }
        }
        return null;
    }

    public static ActionItem getActionItem(int position) {
        return new ActionItem(Action.values()[position], null);
    }

    public static int getActionItemIndex(ActionItem item) {
        if (item == null) return -1;
        for (int i = 0; i < Action.values().length; i++) {
            if (item.action == Action.values()[i])
                return i;
        }
        return -1;
    }

    public enum Theme {
        Dark, Light
    }

    public enum Action {
        EditMinBar, SetWallpaper, LockScreen, ClearRam, DeviceSettings, LauncherSettings, VolumeDialog, OpenAppDrawer, LaunchApp
    }

    public static class ActionItem {
        public Action action;
        public Intent extraData;

        public ActionItem(Action action, Intent extraData) {
            this.action = action;
            this.extraData = extraData;
        }
    }

    public static class ActionDisplayItem {
        public Action label;
        public String description;
        public int icon;

        public ActionDisplayItem(Action label, String description, int icon) {
            this.label = label;
            this.description = description;
            this.icon = icon;
        }
    }
}