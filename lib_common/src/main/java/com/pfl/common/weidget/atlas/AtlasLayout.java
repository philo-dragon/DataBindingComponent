package com.pfl.common.weidget.atlas;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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

import java.util.List;

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

    static final float MIN_SCALE = 0.5f;
    static final float MAX_SCALE = 3.8f;

    private int mTouchMode = TOUCH_MODE_NONE;

    private GlideLoader loader;
    private int mErrorImageRes = R.mipmap.img_error;

    private ViewPager mViewPager;

    private final float mTouchSlop;
    private int mWidth, mHeight;

    private float downX;
    private float downY;

    private int initPosition;//点击图片的角标
    private List<ImageView> mImageGroupList;
    private List<String> mUrlList;

    private ImageView iSource;
    private ImageView iOrigin;

    private ValueAnimator animBackground;
    private ValueAnimator animImageTransform;

    private int mBackgroundColor = 0x00000000;
    private float mExitScalingRef;
    private ImagePagerAdapter pagerAdapter;


    public AtlasLayout(@NonNull Context context) {
        this(context, null);
    }

    public AtlasLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        loader = new GlideLoader();
        addView(mViewPager = new ViewPager(getContext()));
        mViewPager.addOnPageChangeListener(this);
        setVisibility(View.INVISIBLE);
        mWidth = ScreenUtils.getScreenWidth();
        mHeight = ScreenUtils.getScreenHeight();
    }

    /**
     * 取消动画 回收对象
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animImageTransform != null) animImageTransform.cancel();
        animImageTransform = null;
        if (animBackground != null) animBackground.cancel();
        animBackground = null;
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
                mTouchMode = TOUCH_MODE_DOWN;
                ViewState.write(iSource, ViewState.STATE_TOUCH_DOWN);
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = event.getRawX() - downX;
                float deltaY = event.getRawY() - downY;

                if (Math.abs(deltaY) > mTouchSlop// Y轴是有效滑动距离
                        || Math.abs(deltaX) > mTouchSlop// X轴是有效滑动距离
                        && (Math.abs(deltaY) > Math.abs(deltaX))// X轴滑动距离大于Y轴滑动距离
                        ) {
                    Log.e(TAG, (deltaY) - Math.abs(deltaX) + "");
                    ViewState.write(iSource, ViewState.STATE_TOUCH_DOWN);
                    handleDragGesture(deltaX, deltaY);
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                final float upX = event.getRawX();
                final float upY = event.getRawY();

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 处理响应单手拖拽平移
     */
    private void handleDragGesture(float moveX, float moveY) {
        if (iSource == null) return;
        ViewState vsTouchDown = ViewState.read(iSource, ViewState.STATE_TOUCH_DOWN);
        if (vsTouchDown == null) return;

        iSource.setTranslationX(vsTouchDown.translationX + moveX * 0.1f);
        iSource.setTranslationY(vsTouchDown.translationY + moveY * 0.1f);

    }


    public void showAtlas(ImageView i, List<ImageView> imageGroupList, final List<String> urlList) {

        if (loader == null) {
            throw new NullPointerException("GlideLoader is null");
        }

        if (i == null || imageGroupList == null || urlList == null || imageGroupList.size() < 1 ||
                urlList.size() < imageGroupList.size()) {
            String info = "i[" + i + "]";
            info += "#imageGroupList " + (imageGroupList == null ? "null" : "size : " + imageGroupList.size());
            info += "#urlList " + (urlList == null ? "null" : "size :" + urlList.size());
            throw new IllegalArgumentException("error params \n" + info);
        }

        initPosition = imageGroupList.indexOf(i);
        if (initPosition < 0) {
            throw new IllegalArgumentException("param ImageView i must be a member of the List <ImageView> imageGroupList!");
        }

        if (i.getDrawable() == null) return;

        if (animImageTransform != null) animImageTransform.cancel();
        animImageTransform = null;

        mImageGroupList = imageGroupList;
        mUrlList = urlList;

        iOrigin = null;
        iSource = null;

        setVisibility(View.VISIBLE);
        pagerAdapter = new ImagePagerAdapter();
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(initPosition);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        iSource = pagerAdapter.mImageSparseArray.get(position);
        if (iOrigin != null) {
            iOrigin.setVisibility(View.VISIBLE);
        }
        if (position < mImageGroupList.size()) {
            iOrigin = mImageGroupList.get(position);
            if (iOrigin.getDrawable() != null) iOrigin.setVisibility(View.INVISIBLE);
        }

        ImageView mLast = pagerAdapter.mImageSparseArray.get(position - 1);
        if (ViewState.read(mLast, ViewState.STATE_DEFAULT) != null) {
            ViewState.restoreByAnim(mLast, ViewState.STATE_DEFAULT).create().start();
        }
        ImageView mNext = pagerAdapter.mImageSparseArray.get(position + 1);
        if (ViewState.read(mNext, ViewState.STATE_DEFAULT) != null) {
            ViewState.restoreByAnim(mNext, ViewState.STATE_DEFAULT).create().start();
        }

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
        private boolean hasPlayBeginAnimation;

        @Override
        public int getCount() {
            return mUrlList != null ? mUrlList.size() : 0;
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

        @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            FrameLayout itemView = new FrameLayout(container.getContext());
            container.addView(itemView);

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
            errorView.setImageResource(mErrorImageRes);
            itemView.addView(errorView);
            errorView.setVisibility(View.GONE);

            if (setDefaultDisplayConfigs(imageView, position, hasPlayBeginAnimation)) {
                hasPlayBeginAnimation = true;
            }

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


        private boolean setDefaultDisplayConfigs(final ImageView imageView, final int pos, boolean hasPlayBeginAnimation) {
            boolean isFindEnterImagePicture = false;
            ViewState.write(imageView, ViewState.STATE_ORIGIN).alpha(0).scaleXBy(1.5f).scaleYBy(1.5f);
            if (pos < mImageGroupList.size()) {
                ImageView originRef = mImageGroupList.get(pos);
                if (pos == initPosition && !hasPlayBeginAnimation) {
                    isFindEnterImagePicture = true;
                    iSource = imageView;
                    iOrigin = originRef;
                }

                int[] location = new int[2];
                originRef.getLocationOnScreen(location);
                imageView.setTranslationX(location[0]);
                int locationYOfFullScreen = location[1];
                //locationYOfFullScreen -= mStatusBarHeight;
                imageView.setTranslationY(locationYOfFullScreen);
                imageView.getLayoutParams().width = originRef.getWidth();
                imageView.getLayoutParams().height = originRef.getHeight();

                ViewState.write(imageView, ViewState.STATE_ORIGIN).width(originRef.getWidth()).height(originRef.getHeight());

                Drawable bmpMirror = originRef.getDrawable();
                if (bmpMirror != null) {
                    int bmpMirrorWidth = bmpMirror.getBounds().width();
                    int bmpMirrorHeight = bmpMirror.getBounds().height();
                    ViewState vsThumb = ViewState.write(imageView, ViewState.STATE_THUMB).width(bmpMirrorWidth).height(bmpMirrorHeight)
                            .translationX((mWidth - bmpMirrorWidth) / 2).translationY((mHeight - bmpMirrorHeight) / 2);
                    imageView.setImageDrawable(bmpMirror);

                    if (isFindEnterImagePicture) {
                        animSourceViewStateTransform(imageView, vsThumb);
                    } else {
                        ViewState.restore(imageView, vsThumb.mTag);
                    }
                }
            }

            final boolean isPlayEnterAnimation = isFindEnterImagePicture;
            // loadHighDefinitionPicture
            ViewState.clear(imageView, ViewState.STATE_DEFAULT);

            loader.load(imageView.getContext(), mUrlList.get(pos), new LoadCallback() {
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
                    notifyItemChangedState(pos, false, false);

                    ViewState vsDefault = ViewState.write(imageView, ViewState.STATE_DEFAULT).width(sourceDefaultWidth).height(sourceDefaultHeight)
                            .translationX(sourceDefaultTranslateX).translationY(sourceDefaultTranslateY);
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
                    notifyItemChangedState(pos, true, false);
                }

                @Override
                public void onLoadFailed(Drawable errorDrawable) {
                    notifyItemChangedState(pos, false, imageView.getDrawable() == null);

                }
            });

            if (isPlayEnterAnimation) {
                //iOrigin.setVisibility(View.INVISIBLE);
                animBackgroundTransform(0xFF000000);
            }
            return isPlayEnterAnimation;

        }

    }


    /**
     * 执行ImageWatcher自身的背景色渐变至期望值[colorResult]的动画
     */
    private void animBackgroundTransform(final int colorResult) {
        if (colorResult == mBackgroundColor) return;
        if (animBackground != null) animBackground.cancel();
        final int mCurrentBackgroundColor = mBackgroundColor;
        animBackground = ValueAnimator.ofFloat(0, 1).setDuration(300);
        animBackground.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float p = (float) animation.getAnimatedValue();
                setBackgroundColor(mColorEvaluator.evaluate(p, mCurrentBackgroundColor, colorResult));
            }
        });
        animBackground.start();
    }

    /**
     * 处理单击的手指事件
     */
    public boolean onSingleTapConfirmed() {
        if (iSource == null) return false;
        ViewState vsCurrent = ViewState.write(iSource, ViewState.STATE_CURRENT);
        ViewState vsDefault = ViewState.read(iSource, ViewState.STATE_DEFAULT);
        if (vsDefault == null || (vsCurrent.scaleY <= vsDefault.scaleY && vsCurrent.scaleX <= vsDefault.scaleX)) {
            mExitScalingRef = 0;
        } else {
            mExitScalingRef = 1;
        }
        handleExitTouchResult();
        return true;
    }

    /**
     * 处理结束下拉退出的手指事件，进行退出图片查看或者恢复到初始状态的收尾动画<br>
     * 还需要还原背景色
     */
    private void handleExitTouchResult() {
        if (iSource == null) return;

        if (mExitScalingRef > 0.9f) {
            ViewState vsDefault = ViewState.read(iSource, ViewState.STATE_DEFAULT);
            if (vsDefault == null) return;
            animSourceViewStateTransform(iSource, vsDefault);
            animBackgroundTransform(0xFF000000);
        } else {
            ViewState vsOrigin = ViewState.read(iSource, ViewState.STATE_ORIGIN);
            if (vsOrigin == null) return;
            if (vsOrigin.alpha == 0)
                vsOrigin.translationX(iSource.getTranslationX()).translationY(iSource.getTranslationY());

            animSourceViewStateTransform(iSource, vsOrigin);
            animBackgroundTransform(0x00000000);

            ((FrameLayout) iSource.getParent()).getChildAt(2).animate().alpha(0).start();
        }
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
        return isInTransformAnimation || (iSource != null && getVisibility() == View.VISIBLE && onSingleTapConfirmed());
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
                        if (iOrigin != null) iOrigin.setVisibility(View.VISIBLE);
                        setVisibility(View.GONE);
                    }
                });
            }
            animImageTransform.start();
        }
    }


    final TypeEvaluator<Integer> mColorEvaluator = new TypeEvaluator<Integer>() {
        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            int startColor = startValue;
            int endColor = endValue;

            int alpha = (int) (Color.alpha(startColor) + fraction * (Color.alpha(endColor) - Color.alpha(startColor)));
            int red = (int) (Color.red(startColor) + fraction * (Color.red(endColor) - Color.red(startColor)));
            int green = (int) (Color.green(startColor) + fraction * (Color.green(endColor) - Color.green(startColor)));
            int blue = (int) (Color.blue(startColor) + fraction * (Color.blue(endColor) - Color.blue(startColor)));
            return Color.argb(alpha, red, green, blue);
        }
    };

    private boolean isInTransformAnimation;

    /**
     * 动画执行时加入这个监听器后会自动记录标记 {@link AtlasLayout2#isInTransformAnimation} 的状态<br/>
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
