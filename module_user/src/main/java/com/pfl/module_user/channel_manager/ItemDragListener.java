package com.pfl.module_user.channel_manager;

/**
 * 移动交换的数据更新监听
 */
public interface ItemDragListener {
    void onItemMove(int fromPosition, int toPosition);

    void onItemSwiped(int position);
}