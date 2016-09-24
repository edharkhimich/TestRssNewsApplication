package com.edgar.rss_app2;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by edgar on 23.09.16.
 */

public class LanProgressDialog extends Dialog {

    public LanProgressDialog(Context context) {
        super(context);
    }

    {
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setCancelable(false);
        setContentView(R.layout.dialog_lan);
    }

}
