package com.pfl.common.weidget;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pfl.common.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;

public class ClassicsHeader extends LinearLayout implements RefreshHeader {

    private TextView mHeaderText;//标题文本
    private ImageView mArrowView;
    private RotateAnimation rotate;

    public ClassicsHeader(Context context) {
        super(context);
        initView(context);
    }

    public ClassicsHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public ClassicsHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }

    private void initView(Context context) {
        setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        setOrientation(VERTICAL);
        mHeaderText = new TextView(context);
        addView(mHeaderText, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        addView(new View(context), DensityUtil.dp2px(10), DensityUtil.dp2px(10));
        mArrowView = new ImageView(context);
        mArrowView.setImageResource(R.drawable.footerball);
        addView(mArrowView, DensityUtil.dp2px(20), DensityUtil.dp2px(20));
        addView(new View(context), DensityUtil.dp2px(10), DensityUtil.dp2px(10));
        setMinimumHeight(DensityUtil.dp2px(70));

        //匀速加速器
        LinearInterpolator lir = new LinearInterpolator();
        rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //默认为0，为-1时一直循环动画
        rotate.setRepeatCount(-1);
        //添加匀速加速器
        rotate.setInterpolator(lir);
        rotate.setDuration(500);
        rotate.setFillAfter(true);
    }

    @NonNull
    public View getView() {
        return this;//真实的视图就是自己，不能返回null
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int headHeight, int maxDragHeight) {
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        if (success) {
            mHeaderText.setText("正在刷新");
        } else {
            mHeaderText.setText("刷新失败");
        }
        mArrowView.clearAnimation();
        return 100;//延迟100毫秒之后再弹回
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                mHeaderText.setText("下拉刷新");
                break;
            case Refreshing:
                Animation();
                mHeaderText.setText("正在刷新");
                break;
            case ReleaseToRefresh:
                mHeaderText.setText("释放刷新");
                break;
        }
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int maxDragHeight) {
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }


    @Override
    public void onPullingDown(float percent, int offset, int headerHeight, int extendHeight) {
        mArrowView.setRotation(360 * percent);
    }

    @Override
    public void onReleasing(float percent, int offset, int headHeight, int maxDragHeight) {
        mArrowView.setRotation(360 * percent);
    }

    @Override
    public void onRefreshReleased(RefreshLayout layout, int headerHeight, int maxDragHeight) {
    }

    @Override
    public void setPrimaryColors(@ColorInt int... colors) {
    }

    //控制动画
    public void Animation() {
        mArrowView.startAnimation(rotate);
    }
}