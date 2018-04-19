package com.pfl.module_user.fragment;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.pfl.common.base.BaseFragment;
import com.pfl.common.di.AppComponent;
import com.pfl.common.imageloader.ImageLoader;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.R;
import com.pfl.module_user.databinding.ModuleUserFragmentScaleRecyclerViewBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerManager;
import cn.jzvd.JZVideoPlayerStandard;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;
import static android.support.v7.widget.RecyclerView.ViewHolder;


@Route(path = RouteUtils.MODULE_USER_SCALE_RECYCLER_VIEW_FRAGMENT)
public class ModuleUserScaleRecyclerViewFragment extends BaseFragment<ModuleUserFragmentScaleRecyclerViewBinding> {


    private ImageLoader imageLoader;

    public int firstVisible = 0, visibleCount = 0, totalCount = 0;
    private LinearLayoutManager layoutManager;

    @Override
    public int getContentView() {
        return R.layout.module_user_fragment_scale_recycler_view;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

        imageLoader = appComponent.getImageLoader();

    }

    @Override
    public void initView() {

        layoutManager = new LinearLayoutManager(mContext);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        VHAdapter adapter = new VHAdapter(mContext);
        mBinding.recyclerView.setAdapter(adapter);

        mBinding.sRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
            }
        });
        mBinding.sRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
            }
        });

        mBinding.recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                JZVideoPlayer jzvd = view.findViewById(R.id.videoplayer);
                if (jzvd != null && JZUtils.dataSourceObjectsContainsUri(jzvd.dataSourceObjects, JZMediaManager.getCurrentDataSource())) {
                    JZVideoPlayer currentJzvd = JZVideoPlayerManager.getCurrentJzvd();
                    if (currentJzvd != null && currentJzvd.currentScreen != JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN) {
                        JZVideoPlayer.releaseAllVideos();
                    }
                }
            }
        });


        mBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case SCROLL_STATE_IDLE:
                        autoPlayVideo(recyclerView);
                        break;
                    case SCROLL_STATE_DRAGGING:
                        break;
                    case SCROLL_STATE_SETTLING:
                        break;
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                firstVisible = layoutManager.findFirstVisibleItemPosition();//可见范围内的第一项的位置
                visibleCount = layoutManager.findLastVisibleItemPosition();//可见范围内的最后一项的位置
                totalCount = layoutManager.getItemCount();//recyclerview中的item的所有的数目
            }
        });

    }

    void autoPlayVideo(RecyclerView view) {
        for (int i = 0; i < visibleCount; i++) {
            if (view != null && view.getChildAt(i) != null && view.getChildAt(i).findViewById(R.id.videoplayer) != null) {
                JZVideoPlayerStandard videoPlayerStandard = view.getChildAt(i).findViewById(R.id.videoplayer);
                Rect rect = new Rect();
                videoPlayerStandard.getLocalVisibleRect(rect);
                int videoheight = videoPlayerStandard.getHeight();
                if (rect.top == 0 && rect.bottom == videoheight) {
                    if (videoPlayerStandard.currentState == JZVideoPlayer.CURRENT_STATE_NORMAL || videoPlayerStandard.currentState == JZVideoPlayer.CURRENT_STATE_ERROR) {
                        videoPlayerStandard.startButton.performClick();
                    }
                    return;
                }
            }
        }
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void setToolBar() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    // 返回键按下时会被调用
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (JZVideoPlayer.backPress()) {
                return false;
            }
            return true;
        }
        return false;
    }


    class VHAdapter extends RecyclerView.Adapter<VH> {

        private final LayoutInflater inflater;

        public VHAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_videoview, parent, false);
            VH viewHolder = new VH(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.videoPlayer.setUp(
                    "http://jzvd.nathen.cn/c494b340ff704015bb6682ffde3cd302/64929c369124497593205a4190d7d128-5287d2089db37e62345123a1be272f8b.mp4"
                    , JZVideoPlayer.SCREEN_WINDOW_LIST
                    , "测试视频 " + position);

            Glide.with(holder.videoPlayer.getContext()).load("http://g.hiphotos.baidu.com/image/pic/item/c8ea15ce36d3d539f09733493187e950342ab095.jpg").into(holder.videoPlayer.thumbImageView);
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }

    static class VH extends ViewHolder {

        JZVideoPlayerStandard videoPlayer;

        public VH(View itemView) {
            super(itemView);
            videoPlayer = itemView.findViewById(R.id.videoplayer);
            videoPlayer.getLayoutParams().width = ScreenUtils.getScreenWidth();
            videoPlayer.getLayoutParams().height = 3 * ScreenUtils.getScreenWidth() / 4;
        }
    }
}
