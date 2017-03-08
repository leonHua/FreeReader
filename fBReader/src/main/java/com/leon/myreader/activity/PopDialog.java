package com.leon.myreader.activity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import org.geometerplus.zlibrary.ui.android.R;

/**
 * Created by Leon
 */
public class PopDialog {
    /**
     * 显示设置界面
     */
    public static void showPopwindow(final Context context, final Activity activity, String updateInfo, final String downloadUrl) {
        // 利用layoutInflater获得View
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popView = inflater.inflate(R.layout.readsetting, null);
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
        //更新显示信息
        Button btnCancel = (Button) popView.findViewById(R.id.btn_cancel);
        Button btnUpdate = (Button) popView.findViewById(R.id.btn_update);
        // 在底部显示
        if(null == activity){
            Log.i("LEONLEONLEON","KONG");
            return;
        }
        window.showAtLocation(activity.findViewById(R.id.root_view),
                Gravity.BOTTOM, 0, 0);
        //恢复背景亮度
        //changeLightShow(activity);

        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //changeLightclose(activity);
                System.out.println("popWindow消失");
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
                long temp = (Long) valueAnimator.getAnimatedValue();
                lp.alpha = (float) temp;
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
                long temp = (Long) valueAnimator.getAnimatedValue();
                lp.alpha = (float) temp;
                window.setAttributes(lp);
            }
        });
    }

}
