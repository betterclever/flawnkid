package com.freactive.flawnkid.core.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowInsets;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.freactive.flawnkid.core.R;
import com.freactive.flawnkid.core.activity.CoreHome;
import com.freactive.flawnkid.core.manager.Setup;
import com.freactive.flawnkid.core.util.Tool;

import io.codetail.widget.RevealFrameLayout;

public class AppDrawerController extends RevealFrameLayout {
    public AppDrawerPaged drawerViewPaged;
    public AppDrawerVertical drawerViewGrid;
    public int drawerMode;
    public boolean isOpen = false;
    private CallBack openCallBack, closeCallBack;
    private Animator appDrawerAnimator;
    private Long drawerAnimationTime = 200L;

    public AppDrawerController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppDrawerController(Context context) {
        super(context);
    }

    public AppDrawerController(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setCallBack(CallBack openCallBack, CallBack closeCallBack) {
        this.openCallBack = openCallBack;
        this.closeCallBack = closeCallBack;
    }

    public View getDrawer() {
        return getChildAt(0);
    }

    public void open(int cx, int cy, int startRadius, int finalRadius) {
        if (isOpen) return;
        isOpen = true;
        drawerAnimationTime = (long) (240 * Setup.appSettings().getOverallAnimationSpeedModifier());

        appDrawerAnimator = io.codetail.animation.ViewAnimationUtils.createCircularReveal(getChildAt(0), cx, cy, startRadius, finalRadius);
        appDrawerAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        appDrawerAnimator.setDuration(drawerAnimationTime);
        appDrawerAnimator.setStartDelay((int) (Setup.appSettings().getOverallAnimationSpeedModifier() * 200));
        openCallBack.onStart();
        appDrawerAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator p1) {
                getChildAt(0).setVisibility(View.VISIBLE);

                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(getBackground(), PropertyValuesHolder.ofInt("alpha", 0, 255));
                animator.setDuration(drawerAnimationTime);
                animator.start();

                switch (drawerMode) {
                    case DrawerMode.HORIZONTAL_PAGED:
                        for (int i = 0; i < drawerViewPaged.pages.size(); i++) {
                            drawerViewPaged.pages.get(i).findViewById(R.id.group).setAlpha(1);
                        }
                        if (drawerViewPaged.pages.size() > 0) {
                            View mGrid = drawerViewPaged.pages.get(drawerViewPaged.getCurrentItem()).findViewById(R.id.group);
                            mGrid.setAlpha(0);
                            mGrid.animate().alpha(1).setDuration(150L).setStartDelay(Math.max(drawerAnimationTime - 50, 1)).setInterpolator(new AccelerateDecelerateInterpolator());
                        }
                        break;
                    case DrawerMode.VERTICAL:
                        drawerViewGrid.recyclerView.setAlpha(0);
                        drawerViewGrid.recyclerView.animate().alpha(1).setDuration(150L).setStartDelay(Math.max(drawerAnimationTime - 50, 1)).setInterpolator(new AccelerateDecelerateInterpolator());
                        break;
                }
            }

            @Override
            public void onAnimationEnd(Animator p1) {
                openCallBack.onEnd();
            }

            @Override
            public void onAnimationCancel(Animator p1) {
            }

            @Override
            public void onAnimationRepeat(Animator p1) {
            }
        });

        appDrawerAnimator.start();
    }

    public void close(int cx, int cy, int startRadius, int finalRadius) {
        if (!isOpen) return;
        isOpen = false;

        if (appDrawerAnimator == null || appDrawerAnimator.isRunning())
            return;

        appDrawerAnimator = io.codetail.animation.ViewAnimationUtils.createCircularReveal(getChildAt(0), cx, cy, finalRadius, startRadius);
        appDrawerAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        appDrawerAnimator.setDuration(drawerAnimationTime);
        appDrawerAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator p1) {
                closeCallBack.onStart();

                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(getBackground(), PropertyValuesHolder.ofInt("alpha", 255, 0));
                animator.setDuration(drawerAnimationTime);
                animator.start();
            }

            @Override
            public void onAnimationEnd(Animator p1) {
                closeCallBack.onEnd();
            }

            @Override
            public void onAnimationCancel(Animator p1) {
            }

            @Override
            public void onAnimationRepeat(Animator p1) {
            }
        });

        switch (drawerMode) {
            case DrawerMode.HORIZONTAL_PAGED:
                if (drawerViewPaged.pages.size() > 0) {
                    View mGrid = drawerViewPaged.pages.get(drawerViewPaged.getCurrentItem()).findViewById(R.id.group);
                    mGrid.animate().setStartDelay(0).alpha(0).setDuration(60L).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            appDrawerAnimator.start();
                        }
                    });
                }
                break;
            case DrawerMode.VERTICAL:
                drawerViewGrid.recyclerView.animate().setStartDelay(0).alpha(0).setDuration(60L).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        appDrawerAnimator.start();
                    }
                });
                break;
        }
    }

    public void init() {
        if (isInEditMode()) return;
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        drawerMode = Setup.Companion.appSettings().getDrawerStyle();
        switch (drawerMode) {
            case DrawerMode.HORIZONTAL_PAGED:
                drawerViewPaged = (AppDrawerPaged) layoutInflater.inflate(R.layout.view_app_drawer_paged, this, false);
                addView(drawerViewPaged);
                layoutInflater.inflate(R.layout.view_drawer_indicator, this, true);
                break;
            case DrawerMode.VERTICAL:
                drawerViewGrid = (AppDrawerVertical) layoutInflater.inflate(R.layout.view_app_drawer_vertical, this, false);
                int marginHorizontal = Tool.INSTANCE.dp2px(Setup.Companion.appSettings().getVerticalDrawerHorizontalMargin(), getContext());
                int marginVertical = Tool.INSTANCE.dp2px(Setup.Companion.appSettings().getVerticalDrawerVerticalMargin(), getContext());
                RevealFrameLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                lp.leftMargin = marginHorizontal;
                lp.rightMargin = marginHorizontal;
                lp.topMargin = marginVertical;
                lp.bottomMargin = marginVertical;
                addView(drawerViewGrid, lp);
                break;
        }
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            setPadding(0, insets.getSystemWindowInsetTop(), 0, insets.getSystemWindowInsetBottom());
            return insets;
        }
        return insets;
    }

    public void reloadDrawerCardTheme() {
        switch (drawerMode) {
            case DrawerMode.HORIZONTAL_PAGED:
                drawerViewPaged.resetAdapter();
                break;
            case DrawerMode.VERTICAL:
                if (!Setup.Companion.appSettings().isDrawerShowCardView()) {
                    drawerViewGrid.setCardBackgroundColor(Color.TRANSPARENT);
                    drawerViewGrid.setCardElevation(0);
                } else {
                    drawerViewGrid.setCardBackgroundColor(Setup.Companion.appSettings().getDrawerCardColor());
                    drawerViewGrid.setCardElevation(Tool.INSTANCE.dp2px(4, getContext()));
                }
                if (drawerViewGrid.gridDrawerAdapter != null) {
                    drawerViewGrid.gridDrawerAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    public void scrollToStart() {
        switch (drawerMode) {
            case DrawerMode.HORIZONTAL_PAGED:
                drawerViewPaged.setCurrentItem(0, false);
                break;
            case DrawerMode.VERTICAL:
                drawerViewGrid.recyclerView.scrollToPosition(0);
                break;
        }
    }

    public void setHome(CoreHome home) {
        switch (drawerMode) {
            case DrawerMode.HORIZONTAL_PAGED:
                drawerViewPaged.withHome(home, (PagerIndicator) findViewById(R.id.appDrawerIndicator));
                break;
            case DrawerMode.VERTICAL:
                break;
        }
    }

    public interface CallBack {
        void onStart();

        void onEnd();
    }

    public static class DrawerMode {
        public static final int HORIZONTAL_PAGED = 0;
        public static final int VERTICAL = 1;
    }
}
