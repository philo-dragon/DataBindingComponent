package com.pfl.common.weidget.atlas;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.pfl.common.R;

public class AtlasLayout extends FrameLayout implements ViewPager.OnPageChangeListener {

    private static final String TAG = AtlasLayout.class.getSimpleName();

    private static final int TOUCH_MODE_NONE = 0; // 无状态
    private static final int TOUCH_MODE_DOWN = 1; // 按下
    private static final int TOUCH_MODE_DRAG = 2; // 单点拖拽
    private static final int TOUCH_MODE_EXIT = 3; // 退出动作
    private static final int TOUCH_MODE_SLIDE = 4; // 页面滑动
    private static final int TOUCH_MODE_SCALE_ROTATE = 5; // 缩放旋转
    private static final int TOUCH_MODE_LOCK = 6; // 缩放旋转锁定
    private static final int TOUCH_MODE_AUTO_FLING = 7; // 动画中
    private static final float MIN_SCALE_WEIGHT = 0.25f;

    private int mTouchMode = TOUCH_MODE_NONE;

    private GlideLoader loader;
    private int mErrorImageRes = R.mipmap.img_error;

    private ViewPager mViewPager;

    private final float mTouchSlop;
    private int mPagerPositionOffsetPixels;
    private int mWidth, mHeight;
    private int maxTranslateX, maxTranslateY;

    private ValueAnimator animImageTransform;
    private boolean isInTransformAnimation;

    private float downX;
    private float downY;

    private OnPictureLongPressListener mOnPictureLongPressListener;

    public AtlasLayout(@NonNull Context context) {
        this(context, null);
    }

    public AtlasLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setBackgroundColor(Color.BLACK);
        loader = new GlideLoader();
        addView(mViewPager = new ViewPager(getContext()));
        mViewPager.addOnPageChangeListener(this);
        setVisibility(View.INVISIBLE);
        mWidth = ScreenUtils.getScreenWidth();
        mHeight = ScreenUtils.getScreenHeight();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        maxTranslateX = mWidth / 2;
        maxTranslateY = mHeight / 2;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getRawX();
                downY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaY = ev.getRawY() - downY;
                float deltaX = ev.getRawX() - downX;

                if (Math.abs(deltaY) > mTouchSlop// Y轴是有效滑动距离
                        || Math.abs(deltaX) > mTouchSlop// X轴是有效滑动距离
                        && (Math.abs(deltaY) > Math.abs(deltaX))// X轴滑动距离大于Y轴滑动距离
                        ) {
                    Log.e(TAG, (Math.abs(deltaY) - Math.abs(deltaX) + ""));
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                //Log.e(TAG, "ACTION_DOWN ------ downX = " + downX + " , " + "downY = " + downY);
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = event.getRawX() - downX;
                float deltaY = event.getRawY() - downY;
                //Log.e(TAG, "ACTION_MOVE ------ deltaX = " + deltaX + " , " + "deltaY = " + deltaY);

                onDrag(event.getRawX(), event.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                //Log.e(TAG, "ACTION_UP ------ ");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                // Log.e(TAG, "ACTION_POINTER_DOWN ------ ");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                //Log.e(TAG, "ACTION_POINTER_UP ------ ");
                break;
        }

        return super.onTouchEvent(event);
    }

    private void onDrag(float dx, float dy) {
        float deltaX = dx - downX;
        float deltaY = dy - downY;
        float scale = 1f;
        if (deltaY > 0) {
            scale = 1 - Math.abs(deltaY) / mHeight;
        }
        //移动
        mViewPager.setTranslationX(deltaX);
        mViewPager.setTranslationY(deltaY);
        //缩放
        scale = Math.min(Math.max(scale, MIN_SCALE_WEIGHT), 1);
        mViewPager.setScaleX(scale);
        mViewPager.setScaleY(scale);
        //设置背景颜色
        float alpha = scale * 255;
        setBackgroundColor(Color.argb((int) alpha, 0, 0, 0));
        finishDeltaY = deltaY;
    }

    private float finishDeltaY;

    public void showAtlas() {
        setVisibility(View.VISIBLE);
        ImagePagerAdapter pagerAdapter = new ImagePagerAdapter();
        mViewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mPagerPositionOffsetPixels = positionOffsetPixels;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * 显示图片的PagerAdapter
     */
    class ImagePagerAdapter extends PagerAdapter {

        private final FrameLayout.LayoutParams lpCenter = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        private final SparseArray<ImageView> mImageSparseArray = new SparseArray<>();

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            mImageSparseArray.remove(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            FrameLayout itemView = new FrameLayout(container.getContext());
            container.addView(itemView);

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;

            // 要显示的ImageView
            final ImageView imageView = new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            itemView.addView(imageView);
            mImageSparseArray.put(position, imageView);

            // 进度条
            MaterialProgressView loadView = new MaterialProgressView(container.getContext());
            lpCenter.gravity = Gravity.CENTER;
            loadView.setLayoutParams(lpCenter);
            itemView.addView(loadView);

            // 错误图片显示
            ImageView errorView = new ImageView(container.getContext());
            errorView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            errorView.setLayoutParams(layoutParams);
            errorView.setImageResource(mErrorImageRes);
            itemView.addView(errorView);
            errorView.setVisibility(View.GONE);

            /**
             * 加载图片
             */
            String defUrl = "http://img.my.csdn.net/uploads/201701/06/1483664940_9893.jpg";
            loader.load(container.getContext(), defUrl, new LoadCallback() {
                @Override
                public void onResourceReady(Bitmap resource) {

                    final int sourceDefaultWidth, sourceDefaultHeight, sourceDefaultTranslateX, sourceDefaultTranslateY;
                    int resourceImageWidth = resource.getWidth();
                    int resourceImageHeight = resource.getHeight();
                    if (resourceImageWidth * 1f / resourceImageHeight > mWidth * 1f / mHeight) {
                        sourceDefaultWidth = mWidth;
                        sourceDefaultHeight = (int) (sourceDefaultWidth * 1f / resourceImageWidth * resourceImageHeight);
                        sourceDefaultTranslateX = 0;
                        sourceDefaultTranslateY = (mHeight - sourceDefaultHeight) / 2;
                        imageView.setTag(R.id.image_orientation, "horizontal");
                    } else {
                        sourceDefaultHeight = mHeight;
                        sourceDefaultWidth = (int) (sourceDefaultHeight * 1f / resourceImageHeight * resourceImageWidth);
                        sourceDefaultTranslateY = 0;
                        sourceDefaultTranslateX = (mWidth - sourceDefaultWidth) / 2;
                        imageView.setTag(R.id.image_orientation, "vertical");
                    }

                    imageView.setImageBitmap(resource);
                    notifyItemChangedState(position, false, false);//更新显示状态

                    ViewState vsDefault = ViewState.write(imageView, ViewState.STATE_DEFAULT).width(sourceDefaultWidth).height(sourceDefaultHeight)
                            .translationX(sourceDefaultTranslateX).translationY(sourceDefaultTranslateY);
                    boolean isPlayEnterAnimation = false;

                    if (isPlayEnterAnimation) {
                        animSourceViewStateTransform(imageView, vsDefault);
                    } else {
                        ViewState.restore(imageView, vsDefault.mTag);
                        imageView.setAlpha(0f);
                        imageView.animate().alpha(1).start();
                    }
                }

                @Override
                public void onLoadStarted(Drawable placeholder) {
                    notifyItemChangedState(position, true, false);//更新显示状态
                }

                @Override
                public void onLoadFailed(Drawable errorDrawable) {
                    notifyItemChangedState(position, false, imageView.getDrawable() == null);//更新显示状态
                }
            });

            return itemView;
        }


        /**
         * 更新ViewPager中每项的当前状态，比如是否加载，比如是否加载失败
         *
         * @param position 当前项的位置
         * @param loading  是否显示加载中
         * @param error    是否显示加载失败
         */
        void notifyItemChangedState(int position, boolean loading, boolean error) {
            ImageView imageView = mImageSparseArray.get(position);
            if (imageView != null) {
                FrameLayout itemView = (FrameLayout) imageView.getParent();
                MaterialProgressView loadView = (MaterialProgressView) itemView.getChildAt(1);
                if (loading) {
                    loadView.setVisibility(View.VISIBLE);
                    loadView.start();
                } else {
                    loadView.stop();
                    loadView.setVisibility(View.GONE);
                }

                ImageView errorView = (ImageView) itemView.getChildAt(2);
                errorView.setAlpha(1f);
                errorView.setVisibility(error ? View.VISIBLE : View.GONE);
            }
        }

    }

    /**
     * 处理单击的手指事件
     */
    public boolean onSingleTapConfirmed() {
        setVisibility(View.GONE);
        return true;
    }

    /**
     * 当界面处于图片查看状态需要在Activity中的{@link Activity#onBackPressed()}
     * 将事件传递给ImageWatcher优先处理<br/>
     * 1、当处于收尾动画执行状态时，消费返回键事件<br/>
     * 2、当图片处于放大状态时，执行图片缩放到原始大小的动画，消费返回键事件<br/>
     * 3、当图片处于原始状态时，退出图片查看，消费返回键事件<br/>
     * 4、其他情况，ImageWatcher并没有展示图片
     */
    public boolean handleBackPressed() {
        return getVisibility() == View.VISIBLE && onSingleTapConfirmed();
    }


    /**
     * 将指定的ImageView形态(尺寸大小，缩放，旋转，平移，透明度)逐步转化到期望值
     */
    private void animSourceViewStateTransform(ImageView view, final ViewState vsResult) {
        if (view == null) return;
        if (animImageTransform != null) animImageTransform.cancel();

        animImageTransform = ViewState.restoreByAnim(view, vsResult.mTag).addListener(mAnimTransitionStateListener).create();

        if (animImageTransform != null) {
            if (vsResult.mTag == ViewState.STATE_ORIGIN) {
                animImageTransform.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // 如果是退出查看操作，动画执行完后，原始被点击的ImageView恢复可见
                        //if (iOrigin != null) iOrigin.setVisibility(View.VISIBLE);
                        setVisibility(View.GONE);
                    }
                });
            }
            animImageTransform.start();
        }
    }

    /**
     * 动画执行时加入这个监听器后会自动记录标记 {@link AtlasLayout#isInTransformAnimation} 的状态<br/>
     * isInTransformAnimation值为true的时候可以达到在动画执行时屏蔽触摸操作的目的
     */
    final AnimatorListenerAdapter mAnimTransitionStateListener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationCancel(Animator animation) {
            isInTransformAnimation = false;
        }

        @Override
        public void onAnimationStart(Animator animation) {
            isInTransformAnimation = true;
            mTouchMode = TOUCH_MODE_AUTO_FLING;//TouchMode 修改为 动画中
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            isInTransformAnimation = false;
        }
    };

    public class GlideLoader implements Loader {

        @Override
        public void load(Context context, String url, final LoadCallback lc) {

            Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    lc.onResourceReady(resource);
                }

                @Override
                public void onLoadStarted(Drawable placeholder) {
                    lc.onLoadStarted(placeholder);
                }

                @Override
                public void onLoadFailed(Drawable errorDrawable) {
                    lc.onLoadFailed(errorDrawable);
                }
            });
        }
    }

    public interface Loader {
        void load(Context context, String url, LoadCallback lc);
    }

    public interface LoadCallback {
        void onResourceReady(Bitmap resource);

        void onLoadStarted(Drawable placeholder);

        void onLoadFailed(Drawable errorDrawable);
    }

    /**
     * 当前展示图片长按的回调
     */
    public interface OnPictureLongPressListener {
        /**
         * @param v   当前被按的ImageView
         * @param url 当前ImageView加载展示的图片url地址
         * @param pos 当前ImageView在展示组中的位置
         */
        void onPictureLongPress(ImageView v, String url, int pos);
    }

    public static class Builder {

        private static final int ATLAS_LAYOUT = R.id.atlas_layout;
        private final ViewGroup activityDecorView;
        private final AtlasLayout mAtlasLayout;

        private Builder(Activity activity) {
            mAtlasLayout = new AtlasLayout(activity);
            mAtlasLayout.setId(ATLAS_LAYOUT);
            activityDecorView = (ViewGroup) activity.getWindow().getDecorView();
        }

        public static Builder with(Activity activity) {
            return new Builder(activity);
        }

        public AtlasLayout create() {
            removeExistingOverlayInView(activityDecorView);
            activityDecorView.addView(mAtlasLayout);
            return mAtlasLayout;
        }

        /**
         * 递归删除id为ATLAS_LAYOUT的view
         *
         * @param parent
         */
        void removeExistingOverlayInView(ViewGroup parent) {
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                if (child.getId() == ATLAS_LAYOUT) {
                    parent.removeView(child);
                }
                if (child instanceof ViewGroup) {
                    removeExistingOverlayInView((ViewGroup) child);
                }
            }
        }
    }


}
