package com.pfl.module_user.fragment;


import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseFragment;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.R;
import com.pfl.module_user.databinding.ModuleUserFragmentScaleRecyclerViewBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import static android.support.v7.widget.RecyclerView.ViewHolder;


@Route(path = RouteUtils.MODULE_USER_SCALE_RECYCLER_VIEW_FRAGMENT)
public class ModuleUserScaleRecyclerViewFragment extends BaseFragment<ModuleUserFragmentScaleRecyclerViewBinding> {


    @Override
    public int getContentView() {
        return R.layout.module_user_fragment_scale_recycler_view;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

    }

    @Override
    public void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        VHAdapter adapter = new VHAdapter();
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

    }

    @Override
    public void setToolBar() {

    }

    @Override
    public void initData() {

    }


    static class VHAdapter extends RecyclerView.Adapter<VH> {

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setPadding(30, 20, 0, 20);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(30);
            VH viewHolder = new VH(textView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.textView.setText(position + "");
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }

    static class VH extends ViewHolder {

        TextView textView;

        public VH(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
