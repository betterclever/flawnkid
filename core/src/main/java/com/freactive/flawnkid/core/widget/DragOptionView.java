package com.freactive.flawnkid.core.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freactive.flawnkid.core.R;
import com.freactive.flawnkid.core.activity.CoreHome;
import com.freactive.flawnkid.core.interfaces.DialogListener;
import com.freactive.flawnkid.core.manager.Setup;
import com.freactive.flawnkid.core.model.Item;
import com.freactive.flawnkid.core.util.DragAction;
import com.freactive.flawnkid.core.util.DragNDropHandler;
import com.freactive.flawnkid.core.util.Tool;

public class DragOptionView extends CardView {
    public boolean isDraggedFromDrawer = false;
    public boolean dragging = false;
    private View[] hideViews;
    private LinearLayout dragOptions;
    private TextView editIcon;
    private TextView removeIcon;
    private TextView infoIcon;
    private TextView deleteIcon;
    private CoreHome home;
    private Long animSpeed = 120L;

    public DragOptionView(Context context) {
        super(context);
        init();
    }

    public DragOptionView(Context context, AttributeSet attr) {
        super(context, attr);
        init();
    }

    public void setHome(CoreHome home) {
        this.home = home;
    }

    public void setAutoHideView(View... v) {
        hideViews = v;
    }

    public void resetAutoHideView() {
        hideViews = null;
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            ((ViewGroup.MarginLayoutParams) getLayoutParams()).topMargin = insets.getSystemWindowInsetTop() + Tool.INSTANCE.dp2px(14, getContext());
        }
        return insets;
    }

    private void init() {
        setCardElevation(Tool.INSTANCE.dp2px(4, getContext()));
        setRadius(Tool.INSTANCE.dp2px(2, getContext()));

        dragOptions = (LinearLayout) ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_drag_option, this, false);
        addView(dragOptions);

        editIcon = dragOptions.findViewById(R.id.editIcon);
        editIcon.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(final View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        if (((DragAction) dragEvent.getLocalState()).action == DragAction.Action.APP_DRAWER) {
                            return false;
                        }
                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        return true;
                    case DragEvent.ACTION_DROP:
                        final Item item = DragNDropHandler.INSTANCE.getDraggedObject(dragEvent);

                        Setup.Companion.eventHandler().showEditDialog(getContext(), item, new DialogListener.OnEditDialogListener() {
                            @Override
                            public void onRename(String name) {
                                item.setLabel(name);
                                CoreHome.Companion.getDb().saveItem(item);

                                CoreHome.Companion.getLauncher().getDesktop().addItemToCell(item, item.getX(), item.getY());
                                CoreHome.Companion.getLauncher().getDesktop().removeItem(CoreHome.Companion.getLauncher().getDesktop().getCurrentPage().coordinateToChildView(new Point(item.getX(), item.getY())), false);
                            }
                        });
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;
                }
                return false;
            }
        });
        removeIcon = (TextView) dragOptions.findViewById(R.id.removeIcon);
        removeIcon.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        switch (((DragAction) dragEvent.getLocalState()).action) {
                            case GROUP:
                            case APP:
                            case WIDGET:
                            case SHORTCUT:
                            case APP_DRAWER:
                            case ACTION:
                                return true;
                        }
                    case DragEvent.ACTION_DRAG_ENTERED:
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        return true;
                    case DragEvent.ACTION_DROP:
                        Item item = DragNDropHandler.INSTANCE.getDraggedObject(dragEvent);

                        // remove all items from the database
                        CoreHome.Companion.getLauncher().Companion.getDb().deleteItem(item, true);

                        home.getDesktop().consumeRevert();
                        home.getDock().consumeRevert();
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;
                }
                return false;
            }
        });
        infoIcon = (TextView) dragOptions.findViewById(R.id.infoIcon);
        infoIcon.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        switch (((DragAction) dragEvent.getLocalState()).action) {
                            case APP_DRAWER:
                            case APP:
                                return true;
                        }
                    case DragEvent.ACTION_DRAG_ENTERED:
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        return true;
                    case DragEvent.ACTION_DROP:
                        Item item = DragNDropHandler.INSTANCE.getDraggedObject(dragEvent);
                        if (item.getType() == Item.Type.APP) {
                            try {
                                getContext().startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + item.getIntent().getComponent().getPackageName())));
                            } catch (Exception e) {
                                Tool.INSTANCE.toast(getContext(), R.string.toast_app_uninstalled);
                            }
                        }
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;
                }
                return false;
            }
        });
        deleteIcon = (TextView) dragOptions.findViewById(R.id.deleteIcon);
        deleteIcon.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        switch (((DragAction) dragEvent.getLocalState()).action) {
                            case APP_DRAWER:
                            case APP:
                                return true;
                        }
                    case DragEvent.ACTION_DRAG_ENTERED:
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        return true;
                    case DragEvent.ACTION_DROP:
                        //Setup.Companion.eventHandler().showDeletePackageDialog(getContext(), dragEvent);
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;
                }
                return false;
            }
        });

        editIcon.setText(editIcon.getText(), TextView.BufferType.SPANNABLE);
        removeIcon.setText(removeIcon.getText(), TextView.BufferType.SPANNABLE);
        infoIcon.setText(infoIcon.getText(), TextView.BufferType.SPANNABLE);
        deleteIcon.setText(deleteIcon.getText(), TextView.BufferType.SPANNABLE);

        for (int i = 0; i < dragOptions.getChildCount(); i++) {
            dragOptions.getChildAt(i).setVisibility(View.GONE);
        }
    }

    @Override
    public boolean dispatchDragEvent(DragEvent ev) {
        final DragEvent event = ev;
        boolean r = super.dispatchDragEvent(ev);
        if (r && (ev.getAction() == DragEvent.ACTION_DRAG_STARTED || ev.getAction() == DragEvent.ACTION_DRAG_ENDED)) {
            // If we got a start or end and the return value is true, our
            // onDragEvent wasn't called by ViewGroup.dispatchDragEvent
            // So we do it here.
            this.post(new Runnable() {
                @Override
                public void run() {
                    onDragEvent(event);
                }
            });


            // fix crash on older versions of android
            try {
                super.dispatchDragEvent(ev);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return r;
    }

    private void animShowView() {
        if (hideViews != null) {
            isDraggedFromDrawer = true;

            if (Setup.Companion.get().getAppSettings().getSearchBarEnable())
                Tool.INSTANCE.invisibleViews(Math.round(animSpeed / 1.3f), hideViews);

            animate().alpha(1);
        }
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                dragging = true;
                animShowView();
                boolean desktopHideGrid = Setup.Companion.appSettings().isDesktopHideGrid();
                home.getDock().setHideGrid(desktopHideGrid);
                for (CellContainer cellContainer : home.getDesktop().getPages()) {
                    cellContainer.setHideGrid(desktopHideGrid);
                }
                switch (((DragAction) event.getLocalState()).action) {
                    case ACTION:
                        editIcon.setVisibility(View.VISIBLE);
                        removeIcon.setVisibility(View.VISIBLE);
                        return true;
                    case APP:
                        editIcon.setVisibility(View.VISIBLE);
                        removeIcon.setVisibility(View.VISIBLE);
                        infoIcon.setVisibility(View.VISIBLE);
                        deleteIcon.setVisibility(View.VISIBLE);
                    case APP_DRAWER:
                        removeIcon.setVisibility(View.VISIBLE);
                        infoIcon.setVisibility(View.VISIBLE);
                        deleteIcon.setVisibility(View.VISIBLE);
                        return true;
                    case WIDGET:
                        removeIcon.setVisibility(View.VISIBLE);
                        return true;
                    case GROUP:
                        editIcon.setVisibility(View.VISIBLE);
                        removeIcon.setVisibility(View.VISIBLE);
                        return true;
                    case SHORTCUT:
                        removeIcon.setVisibility(View.VISIBLE);
                        return true;
                }
            case DragEvent.ACTION_DRAG_ENTERED:
                return true;
            case DragEvent.ACTION_DRAG_EXITED:
                return true;
            case DragEvent.ACTION_DROP:
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                dragging = false;
                home.getDock().setHideGrid(true);
                for (CellContainer cellContainer : home.getDesktop().getPages()) {
                    cellContainer.setHideGrid(true);
                }

                animate().alpha(0);
                editIcon.setVisibility(View.GONE);
                removeIcon.setVisibility(View.GONE);
                infoIcon.setVisibility(View.GONE);
                deleteIcon.setVisibility(View.GONE);

                if (Setup.Companion.get().getAppSettings().getSearchBarEnable())
                    Tool.INSTANCE.visibleViews(Math.round(animSpeed / 1.3f), hideViews);

                // the search view might be disabled
                CoreHome.Companion.getLauncher().updateSearchBar(true);

                isDraggedFromDrawer = false;

                home.getDock().revertLastItem();
                home.getDesktop().revertLastItem();
                return true;
        }
        return false;
    }
}
