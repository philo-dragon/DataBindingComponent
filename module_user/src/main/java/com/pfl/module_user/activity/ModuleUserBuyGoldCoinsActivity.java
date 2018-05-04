package com.pfl.module_user.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.R;
import com.pfl.module_user.databinding.ModuleUserActivityBuyGoldCoinsBinding;
import com.yayandroid.rotatable.Rotatable;

/**
 * 购买金币
 */
@Route(path = RouteUtils.MODULE_USER_ACTIVITY_BUY_GOLD_COINS)
public class ModuleUserBuyGoldCoinsActivity extends BaseActivity<ModuleUserActivityBuyGoldCoinsBinding> implements View.OnClickListener {


   /* private AnimatorSet mRightOutSet1;
    private AnimatorSet mLeftInSet1;
    private AnimatorSet mRightOutSet2;
    private AnimatorSet mLeftInSet2;
    private AnimatorSet mRightOutSet3;
    private AnimatorSet mLeftInSet3;
    private boolean mIsShowBack;*/

    @Override
    public int getContentView() {
        return R.layout.module_user_activity_buy_gold_coins;
    }

    @Override
    public void componentInject(AppComponent appComponent) {


    }


    @Override
    public void initView() {
        mBinding.fl1.setOnClickListener(this);
        mBinding.fl2.setOnClickListener(this);
        mBinding.fl3.setOnClickListener(this);

        //setAnimators();

        setCameraDistance(mBinding.fl1);
        setCameraDistance(mBinding.fl2);
        setCameraDistance(mBinding.fl3);
    }


    @Override
    public void setToolBar() {
        setToolBarHasBack(mBinding.inToolbarLayout.titleBar);
    }

    @Override
    public void initData() {

    }

    /**
     * 翻牌
     */
    public void cardTurnover(View parent, View front, View back, int frontId, int backId) {
        if (View.VISIBLE == back.getVisibility()) {
            front.setRotationY(180f);//先翻转180，转回来时就不是反转的了
            Rotatable rotatable = new Rotatable.Builder(parent)
                    .sides(backId, frontId)
                    .direction(Rotatable.ROTATE_Y)
                    .rotationCount(1)
                    .build();
            rotatable.setTouchEnable(false);
            rotatable.rotate(Rotatable.ROTATE_Y, -180, 500);
        } else if (View.VISIBLE == front.getVisibility()) {
            Rotatable rotatable = new Rotatable.Builder(parent)
                    .sides(backId, frontId)
                    .direction(Rotatable.ROTATE_Y)
                    .rotationCount(1)
                    .build();
            rotatable.setTouchEnable(false);
            rotatable.rotate(Rotatable.ROTATE_Y, 0, 500);
        }
    }

    @Override
    public void onClick(View v) {
        //flipCard();
        cardTurnover(mBinding.fl1, mBinding.flFront1, mBinding.flBack1, R.id.fl_front_1, R.id.fl_back_1);
        cardTurnover(mBinding.fl2, mBinding.flFront2, mBinding.flBack2, R.id.fl_front_2, R.id.fl_back_2);
        cardTurnover(mBinding.fl3, mBinding.flFront3, mBinding.flBack3, R.id.fl_front_3, R.id.fl_back_3);
    }

    /**
     * 改变视角距离, 贴近屏幕
     */
    private void setCameraDistance(View parent) {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        parent.setCameraDistance(scale);
    }

    // 设置动画
   /* @SuppressLint("ResourceType")
    private void setAnimators() {
        mRightOutSet1 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_out);
        mLeftInSet1 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_in);
        mRightOutSet2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_out);
        mLeftInSet2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_in);
        mRightOutSet3 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_out);
        mLeftInSet3 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_in);

        // 设置点击事件
        mRightOutSet3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mBinding.fl1.setClickable(false);
                mBinding.fl2.setClickable(false);
                mBinding.fl3.setClickable(false);
            }
        });
        mLeftInSet3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mBinding.fl1.setClickable(true);
                mBinding.fl2.setClickable(true);
                mBinding.fl3.setClickable(true);
            }
        });
        // 设置点击事件
        mRightOutSet1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mBinding.fl1.setClickable(false);
                mBinding.fl2.setClickable(false);
                mBinding.fl3.setClickable(false);
            }
        });
        mLeftInSet1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mBinding.fl1.setClickable(true);
                mBinding.fl2.setClickable(true);
                mBinding.fl3.setClickable(true);
            }
        });
        // 设置点击事件
        mRightOutSet2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mBinding.fl1.setClickable(false);
                mBinding.fl2.setClickable(false);
                mBinding.fl3.setClickable(false);
            }
        });
        mLeftInSet2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mBinding.fl1.setClickable(true);
                mBinding.fl2.setClickable(true);
                mBinding.fl3.setClickable(true);
            }
        });
    }

    // 翻转卡片
    public void flipCard() {
        // 正面朝上
        if (!mIsShowBack) {
            mRightOutSet1.setTarget(mBinding.flFront1);
            mLeftInSet1.setTarget(mBinding.flBack1);
            mRightOutSet2.setTarget(mBinding.flFront2);
            mLeftInSet2.setTarget(mBinding.flBack2);
            mRightOutSet3.setTarget(mBinding.flFront3);
            mLeftInSet3.setTarget(mBinding.flBack3);
            mRightOutSet1.start();
            mLeftInSet1.start();
            mRightOutSet2.start();
            mLeftInSet2.start();
            mRightOutSet3.start();
            mLeftInSet3.start();
            mIsShowBack = true;
        } else { // 背面朝上
            mRightOutSet1.setTarget(mBinding.flBack1);
            mLeftInSet1.setTarget(mBinding.flFront1);
            mRightOutSet2.setTarget(mBinding.flBack2);
            mLeftInSet2.setTarget(mBinding.flFront2);
            mRightOutSet3.setTarget(mBinding.flBack3);
            mLeftInSet3.setTarget(mBinding.flFront3);
            mRightOutSet1.start();
            mLeftInSet1.start();
            mRightOutSet2.start();
            mLeftInSet2.start();
            mRightOutSet3.start();
            mLeftInSet3.start();
            mIsShowBack = false;
        }
    }*/
}
