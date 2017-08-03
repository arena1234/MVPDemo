package com.wq.demo.widget;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by qiangwang on 8/6/15.
 * 抽屉菜单的摆放和布局通过android:layout_gravity属性来控制，可选值为left、right或start、end
 * 通过xml来布局的话，需要把DrawerLayout作为父容器
 * 主界面布局\抽屉布局在XML中位置没有要求,带有layout_gravity属性,即为抽屉,否则为主布局
 * <p>
 * 增加了折叠抽屉效果
 * 并保证在手势滑动的情况下只出现一个抽屉;在非手势的情况下,打开第二个抽屉的同时要关闭第一个打开的抽屉
 * <p>
 * 修改了抽屉动画监听
 * <p>
 *         抽屉关闭             抽屉打开
 *      **************     **************
 *      **************     **************
 * -----**************     ----**********
 * -----**************     ----**********
 * -----**************     ----**********
 * -----**************     ----**********
 *      **************     **************
 *      **************     **************
 */
public class CustomDrawerLayout extends DrawerLayout {
    private View otherChild;    // 关闭另一个正在显示的抽屉

    public CustomDrawerLayout(Context context) {
        super(context);
    }

    public CustomDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        addDrawerListener(new DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                int gravity = ((LayoutParams) drawerView.getLayoutParams()).gravity;
                // 在非手势,将要出现第二个抽屉时,关闭掉之前的那个抽屉
                if (otherChild == null) {
                    otherChild = findDrawerWithGravity(gravity == Gravity.LEFT ? Gravity.RIGHT : Gravity.LEFT);
                    if (otherChild != null && isDrawerVisible(otherChild)) {
                        closeDrawer(otherChild);
                    } else {
                        otherChild = null;
                    }
                }

                // 添加折叠动画效果
                if (drawerView instanceof FoldLayout) {
                    // 从左边出来的抽屉,折叠效果就需要以右边界为参考点(防止拖动了半天都没有看见抽屉)
                    if (gravity == Gravity.LEFT)
                        ((FoldLayout) drawerView).setFoldRate(slideOffset, false);
                    else {
                        ((FoldLayout) drawerView).setFoldRate(slideOffset, true);
                    }
                } else {
                    // 其他动画,这里使用了属性动画,采用nineoldandroids库
                    View mContent = getChildAt(0);
                    View mMenu = drawerView;
                    if (gravity == Gravity.LEFT) {
//                        ViewHelper.setScaleX(mMenu, 0.6f + (0.4f * slideOffset));
//                        ViewHelper.setScaleY(mMenu, 0.6f + (0.4f * slideOffset));
//                        ViewHelper.setAlpha(mMenu, 0.4f + (0.6f * slideOffset));
//                        ViewHelper.setTranslationX(mMenu, (mMenu.getMeasuredWidth() * 0.75f) * (1 - slideOffset));
//                        ViewHelper.setPivotX(mContent, 0);
//                        ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
//                        ViewHelper.setTranslationX(mContent, mMenu.getMeasuredWidth() * slideOffset);
//                        ViewHelper.setScaleX(mContent, 1 - (0.2f * slideOffset));
//                        ViewHelper.setScaleY(mContent, 1 - (0.2f * slideOffset));
                        ViewHelper.setTranslationX(mContent, mMenu.getMeasuredWidth() * slideOffset);
                        ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
                        ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
                    } else {
                        ViewHelper.setTranslationX(mContent, -mMenu.getMeasuredWidth() * slideOffset);
                        ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
                        ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
                    }
                }

                if (mDrawerListener != null && gravity == Gravity.LEFT) {
                    mDrawerListener.onDrawerSlide(drawerView, slideOffset);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                int gravity = ((LayoutParams) drawerView.getLayoutParams()).gravity;

                // 保证只能显示一侧的抽屉(仅仅适用于手势滑动)
                // 左侧抽屉打开了,锁住右侧抽屉;右侧抽屉打开了,锁住左侧抽屉
                if (gravity == Gravity.LEFT) {
                    setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
                } else {
                    setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
                }

                if (mDrawerListener != null && gravity == Gravity.LEFT) {
                    mDrawerListener.onDrawerOpened(drawerView);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                int gravity = ((LayoutParams) drawerView.getLayoutParams()).gravity;

                if (otherChild == drawerView) {
                    otherChild = null;
                }
                // 保证只能显示一侧的抽屉(仅仅适用于手势滑动)
                // 左侧抽屉关闭了,解锁右侧抽屉;右侧抽屉关闭了,解锁左侧抽屉
                if (gravity == Gravity.LEFT) {
                    setDrawerLockMode(LOCK_MODE_UNLOCKED, Gravity.RIGHT);
                } else {
                    setDrawerLockMode(LOCK_MODE_UNLOCKED, Gravity.LEFT);
                }

                if (mDrawerListener != null && gravity == Gravity.LEFT) {
                    mDrawerListener.onDrawerClosed(drawerView);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                if (mDrawerListener != null) {
                    mDrawerListener.onDrawerStateChanged(newState);
                }
            }
        });
    }

    /**
     * 通过抽屉的方向查找抽屉的View
     *
     * @param gravity
     * @return
     */
    private View findDrawerWithGravity(int gravity) {
        final int absHoriGravity = GravityCompat.getAbsoluteGravity(
                gravity, ViewCompat.getLayoutDirection(this)) & Gravity.HORIZONTAL_GRAVITY_MASK;
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            final int childAbsGravity = GravityCompat.getAbsoluteGravity(
                    ((LayoutParams) child.getLayoutParams()).gravity,
                    ViewCompat.getLayoutDirection(this));
            if ((childAbsGravity & Gravity.HORIZONTAL_GRAVITY_MASK) == absHoriGravity) {
                return child;
            }
        }
        return null;
    }

    /**
     * 此回调用于关联抽屉开关的状态更新
     */
    private DrawerListener mDrawerListener;

    public void setCustomDrawerListener(DrawerListener listener) {
        mDrawerListener = listener;
    }
}
