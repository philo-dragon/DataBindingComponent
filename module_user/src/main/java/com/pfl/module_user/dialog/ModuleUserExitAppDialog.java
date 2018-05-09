package com.pfl.module_user.dialog;

import android.view.View;

import com.pfl.common.dialog.BaseBottomSheetFragment;
import com.pfl.module_user.R;

/**
 * Created by Administrator on 2018\5\9 0009.
 */

public class ModuleUserExitAppDialog extends BaseBottomSheetFragment {

    @Override
    public int getLayoutResId() {
        return R.layout.bottom_sheet;
    }

    @Override
    public void initView() {

        View exit = rootView.findViewById(R.id.tv_exit);
        View no = rootView.findViewById(R.id.tv_no);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
