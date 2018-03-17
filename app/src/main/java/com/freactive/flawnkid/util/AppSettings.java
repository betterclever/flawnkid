package com.freactive.flawnkid.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.freactive.flawnkid.App;
import com.freactive.flawnkid.R;
import com.freactive.flawnkid.core.interfaces.SettingsManager;
import com.freactive.flawnkid.core.manager.Setup;
import com.freactive.flawnkid.core.widget.AppDrawerController;
import com.freactive.flawnkid.core.widget.Desktop;
import com.freactive.flawnkid.core.widget.PagerIndicator;

import net.gsantner.opoc.util.AppSettingsBase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AppSettings extends AppSettingsBase implements SettingsManager {
    private AppSettings(Context context) {
        super(context);
    }

    public static AppSettings get() {
        return new AppSettings(App.Companion.get());
    }

    public int getDesktopColumnCount() {
        return getInt(R.string.pref_key__desktop_columns, 5);
    }

    public int getDesktopRowCount() {
        return getInt(R.string.pref_key__desktop_rows, 6);
    }

    public int getDesktopStyle() {
        return getIntOfStringPref(R.string.pref_key__desktop_style, Desktop.DesktopMode.INSTANCE.getNORMAL());
    }

    public void setDesktopStyle(int style) {
        setInt(R.string.pref_key__desktop_style, style);
    }

    public boolean isDesktopFullscreen() {
        return getBool(R.string.pref_key__desktop_fullscreen, false);
    }

    public boolean isDesktopShowIndicator() {
        return getBool(R.string.pref_key__desktop_show_position_indicator, true);
    }

    public boolean isDesktopShowLabel() {
        return getBool(R.string.pref_key__desktop_show_label, true);
    }

    public boolean getSearchBarEnable() {
        return getBool(R.string.pref_key__search_bar_enable, true);
    }

    public String getSearchBarBaseURI() {
        return getString(R.string.pref_key__search_bar_base_uri, R.string.pref_default__search_bar_base_uri);
    }

    public boolean getSearchBarForceBrowser() {
        return getBool(R.string.pref_key__search_bar_force_browser, false);
    }

    @Override
    public boolean getSearchBarShouldShowHiddenApps() {
        return getBool(R.string.pref_key__search_bar_show_hidden_apps, false);
    }

    @Override
    public boolean isSearchBarTimeEnabled() {
        return true;
    }

    @Override
    @SuppressLint("SimpleDateFormat")
    public SimpleDateFormat getUserDateFormat() {
        String line1 = getString(R.string.pref_key__date_bar_date_format_custom_1, rstr(R.string.pref_key__date_bar_date_format_custom__default_value_1));
        String line2 = getString(R.string.pref_key__date_bar_date_format_custom_2, rstr(R.string.pref_key__date_bar_date_format_custom__default_value_2));

        try {
            return new SimpleDateFormat((line1 + "'\n'" + line2).replace("''", ""), Locale.getDefault());
        } catch (Exception ex) {
            return new SimpleDateFormat("'Invalid pattern''\n''Invalid Pattern'");
        }
    }

    @Override
    public int getDesktopDateMode() {
        return getIntOfStringPref(R.string.pref_key__date_bar_date_format_type, 1);
    }

    @Override
    public int getDesktopDateTextColor() {
        return getInt(R.string.pref_key__date_bar_date_text_color, Color.WHITE);
    }

    @Override
    public boolean isResetSearchBarOnOpen() {
        return false;
    }

    @Override
    public boolean isSearchGridListSwitchEnabled() {
        return false;
    }

    @Override
    public boolean isSearchUseGrid() {
        return getBool(R.string.pref_key__desktop_search_use_grid, false);
    }

    @Override
    public void setSearchUseGrid(boolean enabled) {
        setBool(R.string.pref_key__desktop_search_use_grid, enabled);
    }

    @Override
    public int getSearchGridSize() {
        return 4;
    }

    @Override
    public int getSearchLabelLines() {
        return Integer.MAX_VALUE;
    }

    public int getDesktopBackgroundColor() {
        return getInt(R.string.pref_key__desktop_background_color, Color.TRANSPARENT);
    }

    @Override
    public int getDesktopFolderColor() {
        return getInt(R.string.pref_key__desktop_folder_color, Color.WHITE);
    }

    public int getMinibarBackgroundColor() {
        return getInt(R.string.pref_key__minibar_background_color, ContextCompat.getColor(_context, R.color.colorPrimary));
    }

    @Override
    public int getFolderLabelColor() {
        return getInt(R.string.pref_key__desktop_folder_label_color, Color.BLACK);
    }

    @Override
    public int getDesktopIconSize() {
        return getIconSize();
    }

    public boolean getDockEnable() {
        return getBool(R.string.pref_key__dock_enable, true);
    }

    public void setDockEnable(boolean enable) {
        setBool(R.string.pref_key__dock_enable, enable);
    }

    public int getDockSize() {
        return getInt(R.string.pref_key__dock_size, 5);
    }

    public boolean isDockShowLabel() {
        return getBool(R.string.pref_key__dock_show_label, false);
    }

    public int getDockColor() {
        return getInt(R.string.pref_key__dock_background_color, Color.TRANSPARENT);
    }

    @Override
    public int getDockIconSize() {
        return getIconSize();
    }

    public int getDrawerColumnCount() {
        return getInt(R.string.pref_key__drawer_columns, 5);
    }

    public int getDrawerRowCount() {
        return getInt(R.string.pref_key__drawer_rows, 6);
    }

    public int getDrawerStyle() {
        return getIntOfStringPref(R.string.pref_key__drawer_style, AppDrawerController.DrawerMode.VERTICAL);
    }

    public boolean isDrawerShowCardView() {
        return getBool(R.string.pref_key__drawer_show_card_view, true);
    }

    public boolean isDrawerRememberPosition() {
        return getBool(R.string.pref_key__drawer_remember_position, true);
    }

    public boolean isDrawerShowIndicator() {
        return getBool(R.string.pref_key__drawer_show_position_indicator, true);
    }

    public boolean isDrawerShowLabel() {
        return getBool(R.string.pref_key__drawer_show_label, true);
    }

    public int getDrawerBackgroundColor() {
        return getInt(R.string.pref_key__drawer_background_color, Color.TRANSPARENT);
    }

    public int getDrawerCardColor() {
        return getInt(R.string.pref_key__drawer_card_color, rcolor(R.color.drawer_background_transparentish));
    }

    public int getDrawerLabelColor() {
        return getInt(R.string.pref_key__drawer_label_color, Color.WHITE);
    }

    @Override
    public int getDrawerFastScrollColor() {
        return getInt(R.string.pref_key__drawer_fast_scroll_color, ContextCompat.getColor(Setup.Companion.appContext(), R.color.op_red));
    }

    @Override
    public int getVerticalDrawerHorizontalMargin() {
        return 8;
    }

    @Override
    public int getVerticalDrawerVerticalMargin() {
        return 16;
    }

    @Override
    public int getDrawerIconSize() {
        return getIconSize();
    }

    public boolean isGestureFeedback() {
        return getBool(R.string.pref_key__gesture_feedback, false);
    }

    public boolean getGestureDockSwipeUp() {
        return getBool(R.string.pref_key__gesture_quick_swipe, true);
    }

    public String getGestureDoubleTap() {
        return getString(R.string.pref_key__gesture_double_tap, "0");
    }

    public String getGestureSwipeUp() {
        return getString(R.string.pref_key__gesture_swipe_up, "8");
    }

    public String getGestureSwipeDown() {
        return getString(R.string.pref_key__gesture_swipe_down, "0");
    }

    public String getGesturePinch() {
        return getString(R.string.pref_key__gesture_pinch, "0");
    }

    public String getGestureUnpinch() {
        return getString(R.string.pref_key__gesture_unpinch, "0");
    }

    @Override
    public boolean isDesktopHideGrid() {
        return getBool(R.string.pref_key__desktop_hide_grid, true);
    }

    @Override
    public void setDesktopHideGrid(boolean hideGrid) {
        setBool(R.string.pref_key__desktop_hide_grid, hideGrid);
    }

    public int getIconSize() {
        return getInt(R.string.pref_key__icon_size, 48);
    }

    public String getIconPack() {
        return getString(R.string.pref_key__icon_pack, "");
    }

    public void setIconPack(String value) {
        setString(R.string.pref_key__icon_pack, value);
    }

    public String getLanguage() {
        return getString(R.string.pref_key__language, "");
    }

    public String getTheme() {
        return getString(R.string.pref_key__theme, "0");
    }

    public int getPrimaryColor() {
        return getInt(R.string.pref_key__primary_color, _context.getResources().getColor(R.color.colorPrimary));
    }

    // internal preferences below here
    public boolean getMinibarEnable() {
        return getBool(R.string.pref_key__minibar_enable, true);
    }

    public void setMinibarEnable(boolean value) {
        setBool(R.string.pref_key__minibar_enable, value);
    }

    public ArrayList<String> getMinibarArrangement() {
        ArrayList<String> ret = getStringList(R.string.pref_key__minibar_arrangement);
        if (ret.isEmpty()) {
            for (LauncherAction.ActionDisplayItem item : LauncherAction.actionDisplayItems) {
                ret.add("0" + item.label.toString());
            }
            setMinibarArrangement(ret);
        }
        return ret;
    }

    public void setMinibarArrangement(ArrayList<String> value) {
        setStringList(R.string.pref_key__minibar_arrangement, value);
    }

    public ArrayList<String> getHiddenAppsList() {
        return getStringList(R.string.pref_key__hidden_apps);
    }

    public void setHiddenAppsList(ArrayList<String> value) {
        setStringList(R.string.pref_key__hidden_apps, value);
    }

    public int getDesktopPageCurrent() {
        return getInt(R.string.pref_key__desktop_current_position, 0);
    }

    public void setDesktopPageCurrent(int value) {
        setInt(R.string.pref_key__desktop_current_position, value);
    }

    public boolean isDesktopLock() {
        return getBool(R.string.pref_key__desktop_lock, false);
    }

    public void setDesktopLock(boolean value) {
        setBool(R.string.pref_key__desktop_lock, value);
    }

    @Override
    public int getDesktopIndicatorMode() {
        return getIntOfStringPref(R.string.pref_key__desktop_indicator_style, PagerIndicator.Mode.INSTANCE.getNORMAL());
    }

    @Override
    public void setDesktopIndicatorMode(int mode) {
        setInt(R.string.pref_key__desktop_indicator_style, mode);
    }

    public boolean getAppRestartRequired() {
        return getBool(R.string.pref_key__queue_restart, false);
    }

    @SuppressLint("ApplySharedPref")
    public void setAppRestartRequired(boolean value) {
        // MUST be committed
        _prefApp.edit().putBoolean(_context.getString
                (R.string.pref_key__queue_restart), value).commit();
    }

    public boolean isAppFirstLaunch() {
        return getBool(R.string.pref_key__first_start, true);
    }

    @SuppressLint("ApplySharedPref")
    public void setAppFirstLaunch(boolean value) {
        // MUST be committed
        _prefApp.edit().putBoolean(_context.getString(R.string.pref_key__first_start), value).commit();
    }

    @Override
    public boolean enableImageCaching() {
        return true;
    }

    @Override
    public float getDrawerLabelFontSize() {
        return getInt(R.string.pref_key__drawer_label_font_size, 13);
    }

    @Override
    public float getOverallAnimationSpeedModifier() {
        return (float) (getInt(R.string.pref_key__overall_animation_speed_modifier, 30) / 100.0);
    }
}
