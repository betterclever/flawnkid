package com.freactive.flawnkid.core.viewutil

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast

import com.freactive.flawnkid.core.R
import com.freactive.flawnkid.core.activity.CoreHome
import com.freactive.flawnkid.core.interfaces.AbstractApp
import com.freactive.flawnkid.core.manager.Setup
import com.freactive.flawnkid.core.model.Item
import com.freactive.flawnkid.core.util.DragAction
import com.freactive.flawnkid.core.util.DragNDropHandler
import com.freactive.flawnkid.core.util.Tool
import com.freactive.flawnkid.core.widget.AppItemView
import com.freactive.flawnkid.core.widget.CellContainer
import com.freactive.flawnkid.core.widget.OverviewPage
import com.freactive.flawnkid.core.widget.WidgetView

object ItemViewFactory {

    val NO_FLAGS = 0x01
    val NO_LABEL = 0x02

    fun getItemView(context: Context, item: Item, showLabels: Boolean, callBack: DesktopCallBack<*>, iconSize: Int): View? {
        val flag = if (showLabels) ItemViewFactory.NO_FLAGS else ItemViewFactory.NO_LABEL
        return getItemView(context, callBack, item, iconSize, flag)
    }

    private fun getItemView(context: Context, callBack: DesktopCallBack<*>, item: Item, iconSize: Int, flags: Int): View? {
        var view: View? = null
        when (item.type) {
            Item.Type.APP -> {
                val app = Setup.appLoader().findItemApp(item)
                view = AppItemView.Builder(context, iconSize)
                        .setAppItem(item, app)
                        .withOnTouchGetPosition(item, Setup.itemGestureCallback())
                        .vibrateWhenLongPress()
                        .withOnLongClick(item, DragAction.Action.APP, object : AppItemView.LongPressCallBack {
                            override fun readyForDrag(view: View): Boolean {
                                return true
                            }

                            override fun afterDrag(view: View) {
                                callBack.setLastItem(item, view)
                            }
                        })
                        .setLabelVisibility(flags and NO_LABEL != NO_LABEL)
                        .setTextColor(Color.WHITE)
                        .view
            }
            Item.Type.SHORTCUT -> view = AppItemView.Builder(context, iconSize)
                    .setShortcutItem(item)
                    .withOnTouchGetPosition(item, Setup.itemGestureCallback())
                    .vibrateWhenLongPress()
                    .withOnLongClick(item, DragAction.Action.SHORTCUT, object : AppItemView.LongPressCallBack {
                        override fun readyForDrag(view: View): Boolean {
                            return true
                        }

                        override fun afterDrag(view: View) {
                            callBack.setLastItem(item, view)
                        }
                    })
                    .setLabelVisibility(flags and NO_LABEL != NO_LABEL)
                    .setTextColor(Color.WHITE)
                    .view
            Item.Type.GROUP -> {
                view = AppItemView.Builder(context, iconSize)
                        .setGroupItem(context, callBack, item, iconSize)
                        .withOnTouchGetPosition(item, Setup.itemGestureCallback())
                        .vibrateWhenLongPress()
                        .withOnLongClick(item, DragAction.Action.GROUP, object : AppItemView.LongPressCallBack {
                            override fun readyForDrag(view: View): Boolean {
                                return true
                            }

                            override fun afterDrag(view: View) {
                                callBack.setLastItem(item, view)
                            }
                        })
                        .setLabelVisibility(flags and NO_LABEL != NO_LABEL)
                        .setTextColor(Color.WHITE)
                        .view
                view!!.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            }
            Item.Type.ACTION -> view = AppItemView.Builder(context, iconSize)
                    .setActionItem(item)
                    .withOnTouchGetPosition(item, Setup.itemGestureCallback())
                    .vibrateWhenLongPress()
                    .withOnLongClick(item, DragAction.Action.ACTION, object : AppItemView.LongPressCallBack {
                        override fun readyForDrag(view: View): Boolean {
                            return true
                        }

                        override fun afterDrag(view: View) {
                            callBack.setLastItem(item, view)
                        }
                    })
                    .setLabelVisibility(flags and NO_LABEL != NO_LABEL)
                    .setTextColor(Color.WHITE)
                    .view
            Item.Type.WIDGET -> {
                if (CoreHome.appWidgetHost == null) return null
                val appWidgetInfo = CoreHome.appWidgetManager.getAppWidgetInfo(item.widgetValue)
                val widgetView = CoreHome.appWidgetHost!!.createView(context, item.widgetValue, appWidgetInfo) as WidgetView

                widgetView.setAppWidget(item.widgetValue, appWidgetInfo)
                widgetView.post { updateWidgetOption(item) }

                val widgetContainer = LayoutInflater.from(context).inflate(R.layout.view_widget_container, null) as FrameLayout
                widgetContainer.addView(widgetView)

                val ve = widgetContainer.findViewById<View>(R.id.vertexpand)
                ve.bringToFront()
                val he = widgetContainer.findViewById<View>(R.id.horiexpand)
                he.bringToFront()
                val vl = widgetContainer.findViewById<View>(R.id.vertless)
                vl.bringToFront()
                val hl = widgetContainer.findViewById<View>(R.id.horiless)
                hl.bringToFront()

                ve.animate().scaleY(1f).scaleX(1f)
                he.animate().scaleY(1f).scaleX(1f)
                vl.animate().scaleY(1f).scaleX(1f)
                hl.animate().scaleY(1f).scaleX(1f)

                val action = Runnable {
                    ve.animate().scaleY(0f).scaleX(0f)
                    he.animate().scaleY(0f).scaleX(0f)
                    vl.animate().scaleY(0f).scaleX(0f)
                    hl.animate().scaleY(0f).scaleX(0f)
                }

                widgetContainer.postDelayed(action, 2000)
                widgetView.setOnTouchListener(Tool.getItemOnTouchListener(item, Setup.itemGestureCallback()))
                widgetView.setOnLongClickListener(View.OnLongClickListener { view ->
                    if (Setup.appSettings().isDesktopLock) {
                        return@OnLongClickListener false
                    }
                    view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                    DragNDropHandler.startDrag(view, item, DragAction.Action.WIDGET, null)

                    callBack.setLastItem(item, widgetContainer)
                    true
                })

                ve.setOnClickListener(View.OnClickListener { view ->
                    if (view.scaleX < 1) return@OnClickListener
                    item.spanY = item.spanY + 1
                    scaleWidget(widgetContainer, item)
                    widgetContainer.removeCallbacks(action)
                    widgetContainer.postDelayed(action, 2000)
                })
                he.setOnClickListener(View.OnClickListener { view ->
                    if (view.scaleX < 1) return@OnClickListener
                    item.spanX = item.spanX + 1
                    scaleWidget(widgetContainer, item)
                    widgetContainer.removeCallbacks(action)
                    widgetContainer.postDelayed(action, 2000)
                })
                vl.setOnClickListener(View.OnClickListener { view ->
                    if (view.scaleX < 1) return@OnClickListener
                    item.spanY = item.spanY - 1
                    scaleWidget(widgetContainer, item)
                    widgetContainer.removeCallbacks(action)
                    widgetContainer.postDelayed(action, 2000)
                })
                hl.setOnClickListener(View.OnClickListener { view ->
                    if (view.scaleX < 1) return@OnClickListener
                    item.spanX = item.spanX - 1
                    scaleWidget(widgetContainer, item)
                    widgetContainer.removeCallbacks(action)
                    widgetContainer.postDelayed(action, 2000)
                })
                view = widgetContainer
            }
        }
        if (view != null) {
            view.tag = item
        }
        return view
    }

    private fun scaleWidget(view: View, item: Item) {
        val page = CoreHome.launcher!!.getDesktop().currentPage
        if (page is OverviewPage) return
        (page as CellContainer).apply {
            item.spanX = Math.min(item.spanX, this.cellSpanH)
            item.spanX = Math.max(item.spanX, 1)
            item.spanY = Math.min(item.spanY, this.cellSpanV)
            item.spanY = Math.max(item.spanY, 1)

            this.setOccupied(false, view.layoutParams as CellContainer.LayoutParams)
            if (!this.checkOccupied(Point(item.x, item.y), item.spanX, item.spanY)) {
                val newWidgetLayoutParams = CellContainer.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, item.x, item.y, item.spanX, item.spanY)

                // update occupied array
                this.setOccupied(true, newWidgetLayoutParams)

                // update the view
                view.layoutParams = newWidgetLayoutParams
                updateWidgetOption(item)

                // update the widget size in the database
                CoreHome.db.saveItem(item)
            } else {
                Toast.makeText(CoreHome.launcher!!.getDesktop().context, R.string.toast_not_enough_space, Toast.LENGTH_SHORT).show()

                // add the old layout params to the occupied array
                this.setOccupied(true, view.layoutParams as CellContainer.LayoutParams)
            }
        }
    }

    private fun updateWidgetOption(item: Item) {
        val newOps = Bundle()
        val page  = CoreHome.launcher!!.getDesktop().currentPage
        if (page is OverviewPage) return
        (page as CellContainer).apply {
            newOps.putInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH, item.spanX * this.cellWidth)
            newOps.putInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH, item.spanX * this.cellWidth)
            newOps.putInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT, item.spanY * this.cellHeight)
            newOps.putInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT, item.spanY * this.cellHeight)
        }
        CoreHome.appWidgetManager.updateAppWidgetOptions(item.widgetValue, newOps)
    }
}
