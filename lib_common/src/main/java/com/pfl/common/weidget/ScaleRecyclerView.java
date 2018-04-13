package com.pfl.common.weidget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * 实现类似小米页面顶部下拉、底部上拉 沿着Y轴缩放效果
 */
public class ScaleRecyclerView extends RecyclerView {

    private final int minTouchSlop;
    /**
     * action down 产生的x,y坐标
     */
    private float lastX;
    private float lastY;
    /**
     * 当前View是否处于缩放状态
     */
    private boolean isScale = false;
    /**
     * 当前View的缩放值
     */
    private float mScale = 1.0f;
    /**
     * 产生缩放时，使用的缩放系数，该值越大缩放比率就越大
     */
    private float scaleRatio = 0.2f;

    private int animationTime = 150;

    public ScaleRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        minTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (getAnimation() != null) {
                    clearAnimation();
                }
                //记录Down时的 x y
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                float diffY = y - lastY;
                float absDiff = Math.abs(diffY);

                if (diffY > 0) {
                    if (!canChildScrollUp() && absDiff > minTouchSlop) {
                        //计算偏移量
                        float distance = diffY;
                        //计算出要缩放的scale
                        mScale = 1 + distance * scaleRatio / getHeight();
                        //设置缩放的锚点
                        setPivotY(0f);
                        setPivotX(getWidth() / 2);
                        //进行缩放
                        setScaleY(mScale);
                        isScale = true;
                    }
                } else {
                    if (!canChildScrollDown() && absDiff > minTouchSlop) {
                        //滑动到最底部 向上拉
                        float distance = diffY;
                        mScale = 1 - scaleRatio * distance / getHeight();
                        //设置锚点
                        setPivotX(getWidth() / 2);
                        setPivotY(getHeight());
                        setScaleY(mScale);
                        isScale = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isScale) {
                    //如果已经进行了缩放，up的时候应该恢复原状，从当前scale 恢复至1.0f
                    ObjectAnimator animator = ObjectAnimator.ofFloat(this, "scaleY", mScale, 1.0f);
                    animator.setDuration(animationTime);
                    animator.start();
                    isScale = false;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            return canScrollVertically(-1) || getScrollY() > 0;
        } else {
            return canScrollVertically(-1);
        }
    }

    private boolean canChildScrollDown() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            return canScrollVertically(1) || getScrollY() < getChildAt(0).getMeasuredHeight() - getMeasuredHeight();
        } else {
            return canScrollVertically(1);
        }
    }

}
