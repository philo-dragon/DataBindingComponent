package com.pfl.module_user.fragment;


import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseFragment;
import com.pfl.common.di.AppComponent;
import com.pfl.common.service.ModuleUserRouteService;
import com.pfl.common.utils.RouteUtils;
import com.pfl.common.utils.RxClickUtil;
import com.pfl.module_user.R;
import com.pfl.module_user.databinding.ModuleUserFragmentMineBinding;


@Route(path = RouteUtils.MODULE_USER_FRAGMENT_MINE)
public class ModuleUserMineFragment extends BaseFragment<ModuleUserFragmentMineBinding> implements View.OnClickListener {

    @Override
    public int getContentView() {
        return R.layout.module_user_fragment_mine;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

    }

    @Override
    public void initView() {
        RxClickUtil.RxClick(mBinding.inHeader.imgAvatar, this);
        RxClickUtil.RxClick(mBinding.inHeader.layoutFollowers, this);
        RxClickUtil.RxClick(mBinding.inHeader.layoutFollowing, this);
        RxClickUtil.RxClick(mBinding.inHeader.layoutLogin, this);
        RxClickUtil.RxClick(mBinding.inContent.tvBuyIcon, this);
        RxClickUtil.RxClick(mBinding.inContent.tvApplySfs, this);
        RxClickUtil.RxClick(mBinding.inContent.tvBuyRecommend, this);
        RxClickUtil.RxClick(mBinding.inContent.tvFeedback, this);
        RxClickUtil.RxClick(mBinding.inContent.tvIconDetails, this);
        RxClickUtil.RxClick(mBinding.inContent.tvMoreSetting, this);
        RxClickUtil.RxClick(mBinding.inContent.tvMyFavorite, this);
        RxClickUtil.RxClick(mBinding.inContent.tvMyWallet, this);
        RxClickUtil.RxClick(mBinding.inContent.rlMyMessage, this);
    }

    @Override
    public void setToolBar() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.img_avatar) {
            if (null != ModuleUserRouteService.getUserInfo()) {
                RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_ACCOUNT_INFO);
            }
        } else if (i == R.id.layout_login) {
            RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_FOCUS_AND_FANS);
        } else if (i == R.id.layout_following) {
            RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_FOCUS_AND_FANS);
        } else if (i == R.id.layout_followers) {
            RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_FOCUS_AND_FANS);
        } else if (i == R.id.tv_buy_icon) {
            RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_BUY_GOLD_COINS);
        } else if (i == R.id.tv_icon_details) {
            RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_GOLD_DETAIL);
        } else if (i == R.id.tv_my_wallet) {
            RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_MY_WALLET);
        } else if (i == R.id.tv_buy_recommend) {
            RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_HAS_BUY_RECOMMEND);
        } else if (i == R.id.tv_my_favorite) {
            RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_MY_FAVORITE);
        } else if (i == R.id.rl_my_message) {
            RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_MY_MESSAGE);
        } else if (i == R.id.tv_apply_sfs) {
            RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_APPLY_SFS);
        } else if (i == R.id.tv_feedback) {
            RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_FEED_BACK);
        } else if (i == R.id.tv_more_setting) {
            RouteUtils.actionStart(RouteUtils.MODULE_USER_ACTIVITY_SETTING);
        } else {
        }
    }
}
