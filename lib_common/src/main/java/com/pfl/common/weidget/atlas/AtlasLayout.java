package com.pfl.common.weidget.atlas;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pfl.common.R;

import java.util.List;

public class AtlasLayout extends ViewPager {

    private List<ImageView> mImageGroupList;
    private List<String> mUrlList;

    public AtlasLayout(@NonNull Context context) {
        this(context, null);
    }

    public AtlasLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void showAtlas() {
        ImagePagerAdapter pagerAdapter = new ImagePagerAdapter();
        setAdapter(pagerAdapter);
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
            return 20;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            FrameLayout itemView = new FrameLayout(container.getContext());
            container.addView(itemView);

            ImageView imageView = new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            itemView.addView(imageView);

            if (setDefaultDisplayConfigs(imageView, position, hasPlayBeginAnimation)) {
                hasPlayBeginAnimation = true;
            }

            return itemView;
        }

        private boolean setDefaultDisplayConfigs(ImageView imageView, int position, boolean hasPlayBeginAnimation) {

            boolean isFindEnterImagePicture = false;

            ViewState.write(imageView, ViewState.STATE_ORIGIN).alpha(0).scaleXBy(1.5f).scaleYBy(1.5f);

            Glide.with(imageView)
                    .asBitmap()
                    .load("http://img5.duitang.com/uploads/item/201503/19/20150319112409_2Gwjz.thumb.700_0.jpeg")
                    .into(imageView);

            return isFindEnterImagePicture;
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
