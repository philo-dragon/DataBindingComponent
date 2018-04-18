package com.pfl.module_user.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pfl.common.utils.RouteUtils;
import com.pfl.module_user.channel_manager.ChannelEntity;
import com.pfl.module_user.fragment.ModuleUserChannelFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by yuelin on 2016/5/30.
 */
public class TitleFragmentAdapter extends FragmentPagerAdapter {
    List<ChannelEntity> channels;
    int id = 1;
    Map<String, Integer> IdsMap = new HashMap<>();
    List<String> preIds = new ArrayList<>();

    public TitleFragmentAdapter(FragmentManager fm, @NonNull List<ChannelEntity> channels) {
        super(fm);
        this.channels = channels;
    }

    @Override
    public Fragment getItem(int position) {
        HashMap<String, String> map = new HashMap<>();
        map.put("title", channels.get(position).getName());
        Fragment newFragment = RouteUtils.newFragment(RouteUtils.MODULE_USER_FRAGMENT_CHANNEL, map);
        return newFragment;
    }

    @Override
    public int getCount() {
        return channels.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return channels.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return IdsMap.get(getPageTitle(position));
    }

    @Override
    public int getItemPosition(Object object) {
        ModuleUserChannelFragment fragment = (ModuleUserChannelFragment) object;
        int preId = preIds.indexOf(fragment.getTitle());
        int newId = -1;
        int i = 0;
        int size = getCount();
        for (; i < size; i++) {
            if (getPageTitle(i).equals(fragment.getTitle())) {
                newId = i;
                break;
            }
        }
        if (newId != -1 && newId == preId) {
            return POSITION_UNCHANGED;
        }
        if (newId != -1) {
            return newId;
        }
        return POSITION_NONE;
    }

    @Override
    public void notifyDataSetChanged() {
        for (ChannelEntity info : channels) {
            if (!IdsMap.containsKey(info.getName())) {
                IdsMap.put(info.getName(), id++);
            }
        }
        super.notifyDataSetChanged();
        preIds.clear();
        int size = getCount();
        for (int i = 0; i < size; i++) {
            preIds.add((String) getPageTitle(i));
        }
    }

    public int getPosition(String title) {
        return preIds.indexOf(title);
    }
}