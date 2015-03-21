package org.iflab.icampus.model;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by ahtcfg24 on 2015/3/16.
 */
public class MyProgressDialog extends ProgressDialog {
    public MyProgressDialog(Context context) {
        super(context);
    }

    public MyProgressDialog(Context context, String title, String message,
                                boolean touchCancle) {
        super(context);
        // TODO Auto-generated constructor stub
        setTitle(title);
        setMessage(message);
        setCanceledOnTouchOutside(touchCancle);
    }

    public void hideAndCancel() {
        hide();
        cancel();
    }
}
