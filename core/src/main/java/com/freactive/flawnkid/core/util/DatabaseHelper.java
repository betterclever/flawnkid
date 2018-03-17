package com.freactive.flawnkid.core.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.freactive.flawnkid.core.activity.CoreHome;
import com.freactive.flawnkid.core.interfaces.AbstractApp;
import com.freactive.flawnkid.core.manager.Setup;
import com.freactive.flawnkid.core.model.Item;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper implements Setup.DataManager {
    protected static final String DATABASE_HOME = "home.db";
    protected static final String TABLE_HOME = "home";
    protected static final String COLUMN_TIME = "time";
    protected static final String COLUMN_TYPE = "type";
    protected static final String COLUMN_LABEL = "label";
    protected static final String COLUMN_X_POS = "x";
    protected static final String COLUMN_Y_POS = "y";
    protected static final String COLUMN_DATA = "data";
    protected static final String COLUMN_PAGE = "page";
    protected static final String COLUMN_DESKTOP = "desktop";
    protected static final String COLUMN_STATE = "state";

    protected static final String SQL_CREATE_HOME =
            "CREATE TABLE " + TABLE_HOME + " (" +
                    COLUMN_TIME + " INTEGER PRIMARY KEY," +
                    COLUMN_TYPE + " VARCHAR," +
                    COLUMN_LABEL + " VARCHAR," +
                    COLUMN_X_POS + " INTEGER," +
                    COLUMN_Y_POS + " INTEGER," +
                    COLUMN_DATA + " VARCHAR," +
                    COLUMN_PAGE + " INTEGER," +
                    COLUMN_DESKTOP + " INTEGER," +
                    COLUMN_STATE + " INTEGER)";
    protected static final String SQL_DELETE = "DROP TABLE IF EXISTS ";
    protected static final String SQL_QUERY = "SELECT * FROM ";
    protected SQLiteDatabase db;
    protected Context context;

    public DatabaseHelper(Context c) {
        super(c, DATABASE_HOME, null, 1);
        db = getWritableDatabase();
        context = c;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HOME);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // discard the data and start over
        db.execSQL(SQL_DELETE + TABLE_HOME);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void createItem(Item item, int page, Definitions.ItemPosition itemPosition) {
        ContentValues itemValues = new ContentValues();
        itemValues.put(COLUMN_TIME, item.getId());
        itemValues.put(COLUMN_TYPE, item.getType().toString());
        itemValues.put(COLUMN_LABEL, item.getLabel());
        itemValues.put(COLUMN_X_POS, item.getX());
        itemValues.put(COLUMN_Y_POS, item.getY());

        Setup.Companion.logger().log(this, Log.INFO, null, "createItem: %s (ID: %d)", item != null ? item.getLabel() : "NULL", item != null ? item.getId() : -1);

        String concat = "";
        switch (item.getType()) {
            case APP:
                if (Setup.Companion.appSettings().enableImageCaching()) {
                    Tool.INSTANCE.saveIcon(context, Tool.INSTANCE.drawableToBitmap(item.getIconProvider().getDrawableSynchronously(-1)), Integer.toString(item.getId()));
                }
                itemValues.put(COLUMN_DATA, Tool.INSTANCE.getIntentAsString(item.getIntent()));
                break;
            case GROUP:
                for (Item tmp : item.getItems()) {
                    concat += tmp.getId() + Definitions.INT_SEP;
                }
                itemValues.put(COLUMN_DATA, concat);
                break;
            case ACTION:
                itemValues.put(COLUMN_DATA, item.getActionValue());
                break;
            case WIDGET:
                concat = Integer.toString(item.getWidgetValue()) + Definitions.INT_SEP
                        + Integer.toString(item.getSpanX()) + Definitions.INT_SEP
                        + Integer.toString(item.getSpanY());
                itemValues.put(COLUMN_DATA, concat);
                break;
        }
        itemValues.put(COLUMN_PAGE, page);
        itemValues.put(COLUMN_DESKTOP, itemPosition.ordinal());

        // item will always be visible when first added
        itemValues.put(COLUMN_STATE, 1);
        db.insert(TABLE_HOME, null, itemValues);
    }

    @Override
    public void saveItem(Item item) {
        updateItem(item);
    }

    @Override
    public void saveItem(Item item, Definitions.ItemState state) {
        updateItem(item, state);
    }

    @Override
    public void saveItem(Item item, int page, Definitions.ItemPosition itemPosition) {
        String SQL_QUERY_SPECIFIC = SQL_QUERY + TABLE_HOME + " WHERE " + COLUMN_TIME + " = " + item.getId();
        Cursor cursor = db.rawQuery(SQL_QUERY_SPECIFIC, null);
        if (cursor.getCount() == 0) {
            createItem(item, page, itemPosition);
        } else if (cursor.getCount() == 1) {
            updateItem(item, page, itemPosition);
        }
    }

    @Override
    public void deleteItem(Item item, boolean deleteSubItems) {
        // if the item is a group then remove all entries
        if (deleteSubItems && item.getType() == Item.Type.GROUP) {
            for (Item i : item.getGroupItems()) {
                deleteItem(i, deleteSubItems);
            }
        }
        // delete the item itself
        db.delete(TABLE_HOME, COLUMN_TIME + " = ?", new String[]{String.valueOf(item.getId())});
    }

    @Override
    public List<List<Item>> getDesktop() {
        String SQL_QUERY_DESKTOP = SQL_QUERY + TABLE_HOME;
        Cursor cursor = db.rawQuery(SQL_QUERY_DESKTOP, null);
        List<List<Item>> desktop = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int page = Integer.parseInt(cursor.getString(6));
                int desktopVar = Integer.parseInt(cursor.getString(7));
                int stateVar = Integer.parseInt(cursor.getString(8));
                while (page >= desktop.size()) {
                    desktop.add(new ArrayList<Item>());
                }
                if (desktopVar == 1 && stateVar == 1) {
                    desktop.get(page).add(getSelection(cursor));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return desktop;
    }

    @Override
    public List<Item> getDock() {
        String SQL_QUERY_DESKTOP = SQL_QUERY + TABLE_HOME;
        Cursor cursor = db.rawQuery(SQL_QUERY_DESKTOP, null);
        List<Item> dock = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int desktopVar = Integer.parseInt(cursor.getString(7));
                int stateVar = Integer.parseInt(cursor.getString(8));
                if (desktopVar == 0 && stateVar == 1) {
                    dock.add(getSelection(cursor));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        Tool.INSTANCE.print("database : dock size is ", dock.size());
        return dock;
    }

    @Override
    public Item getItem(int id) {
        String SQL_QUERY_SPECIFIC = SQL_QUERY + TABLE_HOME + " WHERE " + COLUMN_TIME + " = " + id;
        Cursor cursor = db.rawQuery(SQL_QUERY_SPECIFIC, null);
        Item item = null;
        if (cursor.moveToFirst()) {
            item = getSelection(cursor);
        }
        cursor.close();
        return item;
    }

    // update data attribute for an item
    public void updateItem(Item item) {
        ContentValues itemValues = new ContentValues();
        itemValues.put(COLUMN_LABEL, item.getLabel());
        itemValues.put(COLUMN_X_POS, item.getX());
        itemValues.put(COLUMN_Y_POS, item.getY());

        Setup.Companion.logger().log(this, Log.INFO, null, "updateItem: %s (ID: %d)", item != null ? item.getLabel() : "NULL", item != null ? item.getId() : -1);

        String concat = "";
        switch (item.getType()) {
            case APP:
                if (Setup.Companion.appSettings().enableImageCaching()) {
                    Tool.INSTANCE.saveIcon(context, Tool.INSTANCE.drawableToBitmap(item.getIconProvider().getDrawableSynchronously(Definitions.NO_SCALE)), Integer.toString(item.getId()));
                }
                itemValues.put(COLUMN_DATA, Tool.INSTANCE.getIntentAsString(item.getIntent()));
                break;
            case GROUP:
                for (Item tmp : item.getItems()) {
                    concat += tmp.getId() + Definitions.INT_SEP;
                }
                itemValues.put(COLUMN_DATA, concat);
                break;
            case ACTION:
                itemValues.put(COLUMN_DATA, item.getActionValue());
                break;
            case WIDGET:
                concat = Integer.toString(item.getWidgetValue()) + Definitions.INT_SEP
                        + Integer.toString(item.getSpanX()) + Definitions.INT_SEP
                        + Integer.toString(item.getSpanY());
                itemValues.put(COLUMN_DATA, concat);
                break;
        }
        db.update(TABLE_HOME, itemValues, COLUMN_TIME + " = " + item.getId(), null);
    }

    // update the state of an item
    public void updateItem(Item item, Definitions.ItemState state) {
        ContentValues itemValues = new ContentValues();
        Setup.Companion.logger().log(this, Log.INFO, null, "updateItem (state): %s (ID: %d)", item != null ? item.getLabel() : "NULL", item != null ? item.getId() : -1);
        itemValues.put(COLUMN_STATE, state.ordinal());
        db.update(TABLE_HOME, itemValues, COLUMN_TIME + " = " + item.getId(), null);
    }

    // update the fields only used by the database
    public void updateItem(Item item, int page, Definitions.ItemPosition itemPosition) {
        Setup.Companion.logger().log(this, Log.INFO, null, "updateItem (delete + create): %s (ID: %d)", item != null ? item.getLabel() : "NULL", item != null ? item.getId() : -1);
        deleteItem(item, false);
        createItem(item, page, itemPosition);
    }

    private Item getSelection(Cursor cursor) {
        Item item = new Item();
        int id = Integer.parseInt(cursor.getString(0));
        Item.Type type = Item.Type.valueOf(cursor.getString(1));
        String label = cursor.getString(2);
        int x = Integer.parseInt(cursor.getString(3));
        int y = Integer.parseInt(cursor.getString(4));
        String data = cursor.getString(5);

        item.setItemId(id);
        item.setLabel(label);
        item.setX(x);
        item.setY(y);
        item.setType(type);

        String[] dataSplit;
        switch (type) {
            case APP:
            case SHORTCUT:
                item.setIntent(Tool.INSTANCE.getIntentFromString(data));
                if (Setup.Companion.appSettings().enableImageCaching()) {
                    item.setIconProvider(Setup.get().getImageLoader().createIconProvider(Tool.INSTANCE.getIcon(CoreHome.Companion.getLauncher(), Integer.toString(id))));
                } else {
                    switch (type) {
                        case APP:
                        case SHORTCUT:
                            AbstractApp app = Setup.Companion.get().getAppLoader().findItemApp(item);
                            item.setIconProvider(app != null ? app.getIconProvider() : null);
                            break;
                        default:
                            // TODO...
                            break;
                    }
                }
                break;
            case GROUP:
                item.setItems(new ArrayList<Item>());
                dataSplit = data.split(Definitions.INT_SEP);
                for (String s : dataSplit) {
                    item.getItems().add(getItem(Integer.parseInt(s)));
                }
                break;
            case ACTION:
                item.setActionValue(Integer.parseInt(data));
                break;
            case WIDGET:
                dataSplit = data.split(Definitions.INT_SEP);
                item.setWidgetValue(Integer.parseInt(dataSplit[0]));
                item.setSpanX(Integer.parseInt(dataSplit[1]));
                item.setSpanY(Integer.parseInt(dataSplit[2]));
                break;
        }
        return item;
    }
}
