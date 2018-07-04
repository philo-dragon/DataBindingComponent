package cn.com.topzuqiu;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseIntArray;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.AppManager;
import com.pfl.common.utils.RouteUtils;

import java.util.ArrayList;
import java.util.List;

import cn.com.topzuqiu.adapter.AppMainNavigationViewPagerAdapter;
import cn.com.topzuqiu.databinding.ActivityMainBinding;

@Route(path = RouteUtils.APP_MAIN_ACTIVITY)
public class MainActivity extends BaseActivity<ActivityMainBinding> {


    private List<Fragment> fragments;
    private SparseIntArray items;
    private long firstTime;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void componentInject(AppComponent appComponent) {

    }

    @Override
    public void initView() {

        mBinding.bottomNavigationViewEx.enableAnimation(false);
        mBinding.bottomNavigationViewEx.enableShiftingMode(false);
        mBinding.bottomNavigationViewEx.enableItemShiftingMode(false);

        mBinding.bottomNavigationViewEx.setItemHeight(144);
        mBinding.bottomNavigationViewEx.setIconsMarginTop(20);
        mBinding.bottomNavigationViewEx.setTextSize(11);
        mBinding.bottomNavigationViewEx.setIconSize(20, 20);

        fragments = new ArrayList(5);
        items = new SparseIntArray(5);
        fragments.add(RouteUtils.newFragment(RouteUtils.APP_FRAGMENT_FAST_NEWS));
        fragments.add(RouteUtils.newFragment(RouteUtils.MODULE_USER_FRAGMENT_MINE));
        fragments.add(RouteUtils.newFragment(RouteUtils.MODULE_USER_FRAGMENT_MINE));
        fragments.add(RouteUtils.newFragment(RouteUtils.MODULE_USER_FRAGMENT_FIND));
        fragments.add(RouteUtils.newFragment(RouteUtils.MODULE_USER_SCALE_RECYCLER_VIEW_FRAGMENT));

        // add to items for change ViewPager item
        items.put(R.id.i_music, 0);
        items.put(R.id.i_backup, 1);
        items.put(R.id.i_friends, 2);
        items.put(R.id.i_favor, 3);
        items.put(R.id.i_visibility, 4);

        // set adapter
        AppMainNavigationViewPagerAdapter adapter = new AppMainNavigationViewPagerAdapter(getSupportFragmentManager(), fragments);
        mBinding.viewPager.setOffscreenPageLimit(fragments.size());
        mBinding.viewPager.setAdapter(adapter);

        // binding with ViewPager
        mBinding.bottomNavigationViewEx.setupWithViewPager(mBinding.viewPager);

        initEvent();
    }


    private void initEvent() {

        mBinding.bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            private int previousPosition = -1;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = items.get(item.getItemId());
                if (previousPosition != position) {
                    previousPosition = position;
                    startAnimation(previousPosition);
                    mBinding.viewPager.setCurrentItem(position, false);
                }
                return true;
            }
        });

        mBinding.bottomNavigationViewEx.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                startAnimation(items.get(item.getItemId()));
                refreshChildUI(items.get(item.getItemId()));
            }
        });

        mBinding.viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mBinding.bottomNavigationViewEx.setCurrentItem(position);
            }
        });


    }

    /**
     * 如果已经选中，再次点击刷新界面
     *
     * @param position
     */
    private void refreshChildUI(int position) {

    }

    /**
     * 底部图标做缩放动画
     *
     * @param position
     */
    private void startAnimation(int position) {

        ImageView iconAt = mBinding.bottomNavigationViewEx.getIconAt(position);
        AnimatorSet animatorSet = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iconAt, "scaleX", 1f, 0.8f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iconAt, "scaleY", 1f, 0.8f, 1f);

        animatorSet.setDuration(600);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(scaleX).with(scaleY);//两个动画同时开始
        animatorSet.start();

    }

    @Override
    public void setToolBar() {
    }

    @Override
    public void initData() {

    }

    @Override
    public void onBackPressed() {

        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            AppManager.getAppManager().exit(this);
        }
    }
}
