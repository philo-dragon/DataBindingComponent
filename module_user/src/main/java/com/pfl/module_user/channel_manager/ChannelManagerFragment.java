package com.pfl.module_user.channel_manager;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.pfl.common.entity.message_event.BaseMessageEvent;
import com.pfl.common.utils.EventBusUtil;
import com.pfl.module_user.R;
import com.pfl.module_user.databinding.ModuleUserDialogFragmentChannelManagerBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rocky on 2018/4/23.
 */

public class ChannelManagerFragment extends DialogFragment {

    public static final int RESULE_TYPE = -1;//判断是点击完成/还是单个频道

    private ModuleUserDialogFragmentChannelManagerBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.style_dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.module_user_dialog_fragment_channel_manager, null, false);
        View view = mBinding.getRoot();

        //添加这一行
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        Window window = getDialog().getWindow();
        window.setGravity(Gravity.BOTTOM); //可设置dialog的位置
        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = dm.widthPixels; //设置宽度充满屏幕
        lp.height = dm.heightPixels;
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        lp.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        window.setAttributes(lp);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final List<ChannelEntity> items = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            ChannelEntity entity = new ChannelEntity();
            entity.setName("频道" + i);
            items.add(entity);
        }
        final List<ChannelEntity> otherItems = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ChannelEntity entity = new ChannelEntity();
            entity.setName("其他" + i);
            otherItems.add(entity);
        }

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        mBinding.recyclerView.setLayoutManager(manager);
        final ChannelAdapter adapter = new ChannelAdapter(getActivity(), items, otherItems);
        mBinding.recyclerView.setAdapter(adapter);

        mBinding.imgDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        adapter.setOnMyChannelItemClickListener(new ChannelAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(boolean update, int position) {

                BaseMessageEvent<List<ChannelEntity>> messageEvent = new BaseMessageEvent<>();
                messageEvent.setData(adapter.getMyChannelItems());
                messageEvent.setUpdate(update);
                messageEvent.setType(position);
                EventBusUtil.postMessage(messageEvent);
                dismiss();
            }

            @Override
            public void onFinish(boolean update) {
                BaseMessageEvent<List<ChannelEntity>> messageEvent = new BaseMessageEvent<>();
                messageEvent.setType(RESULE_TYPE);
                messageEvent.setData(adapter.getMyChannelItems());
                messageEvent.setUpdate(update);
                EventBusUtil.postMessage(messageEvent);
                dismiss();
            }
        });

    }
}
