package com.citymart.app;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;

public class loading_activity extends Dialog {
    public loading_activity(@NonNull Context context) {
        super(context);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_loading_activity);
//    }

//    public class lottiedialogfragment extends Dialog {
//        public loading_activity(Context context) {
//            super(context);
//
//            WindowManager.LayoutParams wlmp = getWindow().getAttributes();
//
//            wlmp.gravity = Gravity.CENTER_HORIZONTAL;
//            getWindow().setAttributes(wlmp);
//            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            setTitle(null);
//            setCancelable(false);
//            setOnCancelListener(null);
//            View view = LayoutInflater.from(context).inflate(
//                    R.layout.activity_loading_activity, null);
//            setContentView(view);
//        }
}