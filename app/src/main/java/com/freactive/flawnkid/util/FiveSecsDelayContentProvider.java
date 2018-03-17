package com.freactive.flawnkid.util;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.freactive.flawnkid.activity.AutoFinishActivity;
import com.freactive.flawnkid.core.util.Tool;

public class FiveSecsDelayContentProvider extends ContentProvider {

    /**
     * Path used by Kustom to ask a 5 secs delay reset
     */
    private final static String PATH_RESET_5SEC_DELAY = "reset5secs";

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        // Not supported
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        // Not supported
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public int delete(@NonNull Uri uri,
                      String selection,
                      String[] selectionArgs) {
        Tool.INSTANCE.print("dfshgjsdfdfrghid");
        checkCallingPackage();
        if (PATH_RESET_5SEC_DELAY.equals(uri.getLastPathSegment())) {
            /**
             * This assumes you have a transparent activity that will just call finish() during its onCreate method
             * Activity in this case also provides a static method for starting itself
             */
            AutoFinishActivity.Companion.start(getContext());
            return 1;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri,
                      ContentValues values,
                      String selection,
                      String[] selectionArgs) {
        // Not supported
        throw new UnsupportedOperationException("Unsupported");
    }

    /**
     * Will check weather or not calling pkg is authorized to talk with this provider
     *
     * @throws SecurityException
     */
    private void checkCallingPackage() throws SecurityException {
        String callingPkg = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            callingPkg = getCallingPackage();
        }
        if ("org.kustom.wallpaper".equals(callingPkg)) return;
        if ("org.kustom.widget".equals(callingPkg)) return;
        throw new SecurityException("Unauthorized");
    }
}