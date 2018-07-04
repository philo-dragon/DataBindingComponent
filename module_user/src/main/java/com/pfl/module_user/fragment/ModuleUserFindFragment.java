package com.pfl.module_user.fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.pfl.common.base.BaseFragment;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.RouteUtils;
import com.pfl.common.weidget.GalleryLayoutManager;
import com.pfl.module_user.R;
import com.pfl.module_user.databinding.ModuleUserFragmentFindBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现
 */
@Route(path = RouteUtils.MODULE_USER_FRAGMENT_FIND)
public class ModuleUserFindFragment extends BaseFragment<ModuleUserFragmentFindBinding> {

    private static final String TAG = ModuleUserFindFragment.class.getSimpleName();

    @Override
    public int getContentView() {
        return R.layout.module_user_fragment_find;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

    }

    @Override
    public void initView() {
        setToolBar();
        GalleryLayoutManager layoutManager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
        //layoutManager.attach(mPagerRecycleView);  默认选中位置为0
        //不要使用 RecycleView#setLayoutManager 方法，而是使用 GalleryLayoutManager#attach 方法
        layoutManager.attach(mBinding.recyclerView, 0);
        //添加 Item 转换器，和 ViewPager 一样使用
        layoutManager.setItemTransformer(new ScaleTransformer());
        VHAdapter vhAdapter = new VHAdapter(getActivity());
        mBinding.recyclerView.setAdapter(vhAdapter);
        //添加视图选中监听
        layoutManager.setOnItemSelectedListener(new GalleryLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                ToastUtils.showShort("position: " + position);
            }
        });
    }

    @Override
    public void setToolBar() {
        mBinding.inToolbarLayout.titleBar.setTitle("发现");
        mBinding.inToolbarLayout.titleBar.setHeight(96 * 3);
    }

    @Override
    public void initData() {

    }

    class VHAdapter extends RecyclerView.Adapter<VH> {

        private final LayoutInflater inflater;

        public VHAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.module_user_item_find, parent, false);
            VH viewHolder = new VH(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 30;
        }
    }

    static class VH extends RecyclerView.ViewHolder {

        public VH(View itemView) {
            super(itemView);
        }
    }

    public class ScaleTransformer implements GalleryLayoutManager.ItemTransformer {

        @Override
        public void transformItem(GalleryLayoutManager layoutManager, View item, float fraction) {
            float scale = 1 - 0.2f * Math.abs(fraction);
            item.setPivotX(item.getWidth() / 2.f);
            item.setPivotY(item.getHeight() / 2.0f);
            item.setScaleX(scale);
            item.setScaleY(scale);

            Log.e(TAG, "fraction " + fraction);
            item.findViewById(R.id.view).setAlpha(Math.abs(fraction));
            int position = layoutManager.getCurSelectedPosition();
            View view = layoutManager.getChildAt(position - 1);
            if (view != view) {
                view.findViewById(R.id.view).setAlpha(1 - Math.abs(fraction));
            }
            view = layoutManager.getChildAt(position + 1);
            if (view != view) {
                view.findViewById(R.id.view).setAlpha(1 - Math.abs(fraction));
            }
        }
    }
}
