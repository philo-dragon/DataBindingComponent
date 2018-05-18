package com.pfl.module_user.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.imageloader.ImageLoader;
import com.pfl.common.imageloader.glide.ImageConfigImpl;
import com.pfl.common.utils.RouteUtils;
import com.pfl.common.utils.RxClickUtil;
import com.pfl.common.weidget.atlas.ImageWatcher;
import com.pfl.module_user.R;
import com.pfl.module_user.databinding.ModuleUserActivityMyWalletBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的钱包
 */
@Route(path = RouteUtils.MODULE_USER_ACTIVITY_MY_WALLET)
public class ModuleUserMyWalletActivity extends BaseActivity<ModuleUserActivityMyWalletBinding> implements View.OnClickListener {

    private ImageWatcher vImageWatcher;
    private ImageLoader imageLoader;

    @Override
    public int getContentView() {
        return R.layout.module_user_activity_my_wallet;
    }

    @Override
    public void componentInject(AppComponent appComponent) {
        imageLoader = appComponent.getImageLoader();
    }

    @Override
    public void initView() {

        Glide.with(mContext).load("http://img.my.csdn.net/uploads/201701/06/1483664940_9893.jpg").into(mBinding.img1);
        Glide.with(mContext).load("http://img.my.csdn.net/uploads/201701/06/1483664940_9893.jpg").into(mBinding.img2);
        Glide.with(mContext).load("http://img.my.csdn.net/uploads/201701/06/1483664940_9893.jpg").into(mBinding.img3);
        Glide.with(mContext).load("http://img.my.csdn.net/uploads/201701/06/1483664940_9893.jpg").into(mBinding.img4);
        Glide.with(mContext).load("http://img.my.csdn.net/uploads/201701/06/1483664940_9893.jpg").into(mBinding.img5);
        Glide.with(mContext).load("http://img.my.csdn.net/uploads/201701/06/1483664940_9893.jpg").into(mBinding.img6);

        RxClickUtil.RxClick(mBinding.img1, this);
        RxClickUtil.RxClick(mBinding.img2, this);
        RxClickUtil.RxClick(mBinding.img3, this);
        RxClickUtil.RxClick(mBinding.img4, this);
        RxClickUtil.RxClick(mBinding.img5, this);
        RxClickUtil.RxClick(mBinding.img6, this);
    }

    @Override
    public void setToolBar() {
        setToolBarHasBack(mBinding.inToolbarLayout.titleBar);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        List<ImageView> imgList = new ArrayList<>();
        List<String> urlList = new ArrayList<>();
        ImageView initImageView = null;
        for (int i = 0; i < mBinding.rlImgContent.getChildCount(); i++) {
            ImageView imageView = (ImageView) mBinding.rlImgContent.getChildAt(i);
            imgList.add(imageView);
            urlList.add("http://img.my.csdn.net/uploads/201701/06/1483664940_9893.jpg");
            if (v.getId() == imageView.getId()) {
                initImageView = imageView;
            }
        }

        urlList.add("http://img.my.csdn.net/uploads/201701/06/1483664940_9893.jpg");
        urlList.add("http://img.my.csdn.net/uploads/201701/06/1483664940_9893.jpg");
        urlList.add("http://img.my.csdn.net/uploads/201701/06/1483664940_9893.jpg");

        // 不再需要在布局文件中加入<ImageWatcher>标签 减少布局嵌套
        // 一般来讲， ImageWatcher 需要占据全屏的位置
        // 如果是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
        // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
        //调用show方法前，请先调用setLoader 给ImageWatcher提供加载图片的实现
        vImageWatcher = ImageWatcher.Helper.with(this) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setTranslucentStatus(0) // 如果是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
                .setErrorImageRes(R.mipmap.img_error) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
                .setLoader(new ImageWatcher.Loader() {//调用show方法前，请先调用setLoader 给ImageWatcher提供加载图片的实现
                    @Override
                    public void load(Context context, String url, final ImageWatcher.LoadCallback lc) {

                        imageLoader.loadImage(context, ImageConfigImpl.
                                builder().
                                url("http://img.my.csdn.net/uploads/201701/06/1483664940_9893.jpg").
                                loadCallback(new ImageConfigImpl.LoadCallback() {
                                    @Override
                                    public void onResourceReady(Bitmap resource) {
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
                                }).
                                build());

                    }
                })
                .create();

        vImageWatcher.show(initImageView, imgList, urlList);

    }

    @Override
    public void onBackPressed() {
        if (!vImageWatcher.handleBackPressed()) {
            super.onBackPressed();
        }
    }
}
