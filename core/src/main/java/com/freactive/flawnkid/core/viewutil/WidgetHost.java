package com.freactive.flawnkid.core.viewutil;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;

import com.freactive.flawnkid.core.widget.WidgetView;

public class WidgetHost extends AppWidgetHost {

    public WidgetHost(Context context, int hostId) {
        super(context, hostId);
    }

    @Override
    protected AppWidgetHostView onCreateView(Context context, int appWidgetId, AppWidgetProviderInfo appWidget) {
        return new WidgetView(context);
    }
}
