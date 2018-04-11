package cn.com.topzuqiu;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseIntArray;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pfl.common.base.BaseActivity;
import com.pfl.common.di.AppComponent;
import com.pfl.common.utils.RouteUtils;

import java.util.ArrayList;
import java.util.List;

import cn.com.topzuqiu.adapter.MainViewPagerAdapter;
import cn.com.topzuqiu.databinding.ActivityMainBinding;

@Route(path = RouteUtils.APP_MAIN_ACTIVITY)
public class MainActivity extends BaseActivity<ActivityMainBinding> {


    private List<Fragment> fragments;
    private SparseIntArray items;

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

        fragments = new ArrayList(5);
        items = new SparseIntArray(5);
        fragments.add(RouteUtils.newFragment(RouteUtils.MODULE_USER_FRAGMENT_MINE));
        fragments.add(RouteUtils.newFragment(RouteUtils.MODULE_USER_FRAGMENT_MINE));
        fragments.add(RouteUtils.newFragment(RouteUtils.MODULE_USER_FRAGMENT_MINE));
        fragments.add(RouteUtils.newFragment(RouteUtils.MODULE_USER_FRAGMENT_MINE));
        fragments.add(RouteUtils.newFragment(RouteUtils.MODULE_USER_FRAGMENT_MINE));

        // add to items for change ViewPager item
        items.put(R.id.i_music, 0);
        items.put(R.id.i_backup, 1);
        items.put(R.id.i_friends, 2);
        items.put(R.id.i_favor, 3);
        items.put(R.id.i_visibility, 4);

        // set adapter
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragments);
        mBinding.viewPager.setAdapter(adapter);

        // binding with ViewPager
        mBinding.bottomNavigationViewEx.setupWithViewPager(mBinding.viewPager);

        initEvent();
    }

    /**
     * set listeners
     */
    private void initEvent() {
        mBinding.bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            private int previousPosition = -1;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = items.get(item.getItemId());
                if (previousPosition != position) {
                    previousPosition = position;
                    mBinding.viewPager.setCurrentItem(position);
                }
                return true;
            }
        });

        mBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBinding.bottomNavigationViewEx.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void setToolBar() {
    }

    @Override
    public void initData() {

    }

}
