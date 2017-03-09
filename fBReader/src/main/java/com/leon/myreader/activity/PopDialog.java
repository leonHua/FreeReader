package com.leon.myreader.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.geometerplus.android.fbreader.libraryService.BookCollectionShadow;
import org.geometerplus.fbreader.Paths;
import org.geometerplus.fbreader.fbreader.ActionCode;
import org.geometerplus.fbreader.fbreader.FBReaderApp;
import org.geometerplus.fbreader.fbreader.options.ColorProfile;
import org.geometerplus.zlibrary.core.view.ZLViewEnums;
import org.geometerplus.zlibrary.ui.android.R;

/**
 * Created by Leon
 */
public class PopDialog {
    static FBReaderApp myFBReaderApp;
    /**
     * 显示设置界面
     */
    public static void showPopwindow(final Context context, final Activity activity, int layout) {
        // 利用layoutInflater获得View
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popView = inflater.inflate(layout, null);
        final PopupWindow window = new PopupWindow(popView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        window.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(activity.findViewById(R.id.root_view), Gravity.BOTTOM, 0, 0);
        //背景变暗
        changeLightShow(activity);
        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeLightclose(activity);
            }
        });
        myFBReaderApp = (FBReaderApp) FBReaderApp.Instance();
        if (myFBReaderApp == null) {
            myFBReaderApp = new FBReaderApp(Paths.systemInfo(activity), new BookCollectionShadow());
        }
        switch (layout) {
            case R.layout.readsetting:
                //初始控件
                init(popView, window, context, activity);
                break;
            case R.layout.options:
                //初始控件
                initOptions(popView, window, activity);
                break;
        }
    }

    /**
     * 初始化字体设置等控件
     *
     * @param popView
     * @param window
     */
    private static void initOptions(View popView, final PopupWindow window, final Activity activity) {
        Button btnIncrease = (Button) popView.findViewById(R.id.btn_increase);
        Button btnDecrease = (Button) popView.findViewById(R.id.btn_decrease);
        Button btnSlide = (Button) popView.findViewById(R.id.btn_slide);
        Button btnShift = (Button) popView.findViewById(R.id.btn_shift);
        Button btnCurl = (Button) popView.findViewById(R.id.btn_curl);
        final TextView tvSize = (TextView) popView.findViewById(R.id.tv_fontsize);

        final FBReaderApp fbReaderApp = myFBReaderApp;
        tvSize.setText(fbReaderApp.ViewOptions.getTextStyleCollection().getBaseStyle().FontSizeOption.getValue() + "");
        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbReaderApp.runAction(ActionCode.INCREASE_FONT);
                long currentSize = Long.parseLong(tvSize.getText().toString());
                tvSize.setText((currentSize + 2) + "");
            }
        });
        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbReaderApp.runAction(ActionCode.DECREASE_FONT);
                long currentSize = Long.parseLong(tvSize.getText().toString());
                tvSize.setText((currentSize - 2) + "");
            }
        });
        btnSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbReaderApp.PageTurningOptions.Animation.setValue(ZLViewEnums.Animation.slide);
                closePopDialog(window);
            }
        });
        btnShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbReaderApp.PageTurningOptions.Animation.setValue(ZLViewEnums.Animation.shift);
                closePopDialog(window);
            }
        });
        btnCurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbReaderApp.PageTurningOptions.Animation.setValue(ZLViewEnums.Animation.curl);
                closePopDialog(window);
            }
        });
    }

    /**
     * 初始化控件对象
     */
    private static void init(View view, final PopupWindow window, final Context context, final Activity activity) {
        TextView options = (TextView) view.findViewById(R.id.tv_options);
        TextView dayAndNight = (TextView) view.findViewById(R.id.tv_dayandnight);
        //设置点击事件监听
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopDialog(window);
                showPopwindow(context, activity, R.layout.options);
            }
        });
        dayAndNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopDialog(window);
               String style =  myFBReaderApp.ViewOptions.ColorProfileName.getValue();
                if(style.equals(ColorProfile.NIGHT)){
                    myFBReaderApp.ViewOptions.ColorProfileName.setValue(ColorProfile.DAY);
                }else{
                    myFBReaderApp.ViewOptions.ColorProfileName.setValue(ColorProfile.NIGHT);
                }
                myFBReaderApp.getViewWidget().reset();
                myFBReaderApp.getViewWidget().repaint();

            }
        });
    }

    /**
     * 背景变暗,并增加渐变动画
     */
    public static void changeLightShow(Activity activity) {
        final ValueAnimator animation = ValueAnimator.ofFloat(1.0f, 0.6f);
        animation.setDuration(200);
        animation.start();
        final Window window = activity.getWindow();
        final WindowManager.LayoutParams lp = window.getAttributes();
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.alpha = (Float) valueAnimator.getAnimatedValue();
                window.setAttributes(lp);
            }
        });
    }

    /**
     * 恢复背景,并增加渐变动画
     */
    public static void changeLightclose(Activity activity) {
        final ValueAnimator animation = ValueAnimator.ofFloat(0.6f, 1.0f);
        animation.setDuration(200);
        animation.start();
        final Window window = activity.getWindow();
        final WindowManager.LayoutParams lp = window.getAttributes();
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.alpha = (Float) valueAnimator.getAnimatedValue();
                window.setAttributes(lp);
            }
        });
    }

    /**
     * 关闭弹窗
     */
    public static void closePopDialog(PopupWindow window) {
        window.dismiss();
    }

}
