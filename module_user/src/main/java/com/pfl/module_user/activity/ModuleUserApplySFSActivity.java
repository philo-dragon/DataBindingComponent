package com.pfl.module_user.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.RouteUtils;
import com.pfl.common.weidget.ExpandableTextView;
import com.pfl.module_user.R;
import com.pfl.module_user.databinding.ModuleUserActivityApplySfsBinding;

import java.util.ArrayList;


/**
 * 申请胜负师
 */
@Route(path = RouteUtils.MODULE_USER_ACTIVITY_APPLY_SFS)
public class ModuleUserApplySFSActivity extends BaseActivity<ModuleUserActivityApplySfsBinding> {


    private ArrayList<ExpandableBean> expandableBeans;

    @Override
    public int getContentView() {
        return R.layout.module_user_activity_apply_sfs;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

    }

    @Override
    public void initView() {

        expandableBeans = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            expandableBeans.add(new ExpandableBean());
        }

        ExpandableAdapter adapter = new ExpandableAdapter();
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setAdapter(adapter);

    }

    @Override
    public void setToolBar() {
        setToolBarHasBack(mBinding.inToolbarLayout.titleBar);
    }

    @Override
    public void initData() {

    }

    class ExpandableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ExpandableTextView.OnExpandListener {


        //只要在getview时为其赋值为准确的宽度值即可，无论采用何种方法
        private int etvWidth;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_user_item_apply_sfs, parent, false);
            ExpandableViewHolder viewHolder = new ExpandableViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ExpandableViewHolder viewHolder = (ExpandableViewHolder) holder;
            if (etvWidth == 0) {
                viewHolder.etv.post(new Runnable() {
                    @Override
                    public void run() {
                        etvWidth = viewHolder.etv.getWidth();
                    }
                });
            }
            viewHolder.etv.setTag(position);
            viewHolder.etv.setExpandListener(this);
            Integer state = expandableBeans.get(position).state;
            viewHolder.etv.updateForRecyclerView(getResources().getString(R.string.str_expandable), etvWidth, state == null ? 0 : state);//第一次getview时肯定为etvWidth为0
        }

        @Override
        public int getItemCount() {
            return expandableBeans.size();
        }

        @Override
        public void onExpand(ExpandableTextView view) {
            Object obj = view.getTag();
            if (obj != null && obj instanceof Integer) {
                expandableBeans.get((Integer) obj).state = view.getExpandState();
            }
        }

        @Override
        public void onShrink(ExpandableTextView view) {
            Object obj = view.getTag();
            if (obj != null && obj instanceof Integer) {
                expandableBeans.get((Integer) obj).state = view.getExpandState();
            }
        }
    }

    class ExpandableViewHolder extends RecyclerView.ViewHolder {
        ExpandableTextView etv;

        public ExpandableViewHolder(View itemView) {
            super(itemView);
            etv = itemView.findViewById(R.id.etv);
        }
    }

    class ExpandableBean {
        int state = ExpandableTextView.STATE_SHRINK;
    }

}
