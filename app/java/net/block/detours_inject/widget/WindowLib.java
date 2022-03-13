package net.block.detours_inject.widget;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class WindowLib {
    public Activity activity;

    public WindowLib(Activity act) {
        activity = act;
    }

    public int H() {
        DisplayMetrics metrics = new DisplayMetrics();
        this.activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public int W() {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public Object[] addWindowView(View v, int w, int h, int gravity) {
        final WindowManager wm = activity.getWindowManager();

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();

        if (gravity != 0) {
            params.gravity = gravity;
        } else {
            params.gravity = Gravity.CENTER;
        }

        params.format = PixelFormat.RGBA_8888;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
        if (w == 0 || h == 0) {
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        } else {
            params.width = w;
            params.height = h;
        }

        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        params.windowAnimations = android.R.style.Animation_Translucent;

        wm.addView(v, params);

        return new Object[]{
                wm,
                params
        };
    }

    public static void addViews(LinearLayout main, View[] views) {
        for (int i = 0; i < views.length; i++) {
            main.addView(views[i]);
        }
    }

    //Mixtion -Copyright © 2018-2022 AbyssView Team.
    public static int getPopupWindowAnimation(int index) {
        int[] list = {android.R.style.Animation_Activity, android.R.style.Animation_Dialog,
                android.R.style.Animation_InputMethod, android.R.style.Animation_Toast, android.R.style.Animation_Translucent};
        return list[index];
    }

    public String getRandomColor() {
        Double color = Math.floor(Math.random() * 16777215);
        String hexColor = color.toString(16);
        return "#" + hexColor;
    }

    public interface OnClick {
        void OnClickListener(boolean bool);
    }

    public interface OnBtnClick {
        void OnClickListener();
    }

    public interface OnSwitchClick {
        void OnClickListener(int id);
    }


    public interface OnUiStart{
        void OnUIStartListener();
    }

    public interface OnSeekBarCall{
        void OnSeekBarLinstener(SeekBar thisObject);
    }

    public static void setDrawable(String color,View view,int corn){
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setColor(Color.parseColor(color));
        gradientDrawable2.setCornerRadius(corn);
        view.setBackgroundDrawable(gradientDrawable2);
    }

    public static void setDrawable(String color,View view,float[] cr){
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setColor(Color.parseColor(color));
        gradientDrawable2.setCornerRadii(new float[]{cr[0],cr[0],cr[1],cr[1],cr[2],cr[2],cr[3],cr[3]});
        view.setBackgroundDrawable(gradientDrawable2);
    }

    //setCornerRadii([cr[0],cr[0],cr[1],cr[1],cr[2],cr[2],cr[3],cr[3]])

    public ViewTreeObserver.OnGlobalLayoutListener startReveal(View view, int x, int y, float startRadius, float endRadius, long time) {
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, endRadius)
                        .setDuration(time)
                        .start();
            }
        };
        view.setVisibility(View.VISIBLE);
        view.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        return onGlobalLayoutListener;
    }

    public TranslateAnimation startMove(View view, float x1, float x2, float y1, float y2, long time, int type) {
        int tp = ((type == 0) ? Animation.RELATIVE_TO_SELF : type == 1 ? Animation.RELATIVE_TO_PARENT : Animation.ABSOLUTE);
        TranslateAnimation translateAnimation = new TranslateAnimation(tp, x1 * 0.01f, tp, x2 * 0.01f, tp, y1 * 0.01f, tp, y2 * 0.01f);
        translateAnimation.setDuration(time);
        if (view != null) {
            view.startAnimation(translateAnimation);
        }
        return translateAnimation;
    }

    public RotateAnimation startRotate(View view, float a, float b, float x, float y, long time, int type) {
        int tp = ((type == 0) ? Animation.RELATIVE_TO_SELF : type == 1 ? Animation.RELATIVE_TO_PARENT : Animation.ABSOLUTE);
        RotateAnimation rotateAnimation = new RotateAnimation(a, b, tp, x * 0.01f, tp, y * 0.01f);
        rotateAnimation.setDuration(time);
        rotateAnimation.setFillAfter(true);
        if (view != null) {
            view.startAnimation(rotateAnimation);
        }
        return rotateAnimation;
    }

    public ScaleAnimation startShrink(View view, float x1, float x2, float y1, float y2, float x, float y, long time, int type) {
        int tp = ((type == 0) ? Animation.RELATIVE_TO_SELF : type == 1 ? Animation.RELATIVE_TO_PARENT : Animation.ABSOLUTE);
        ScaleAnimation scaleAnimation = new ScaleAnimation(x1 * 0.01f, x2 * 0.01f, y1 * 0.01f, y2 * 0.01f, tp, x * 0.01f, tp, y * 0.01f);
        scaleAnimation.setDuration(time);
        if (view != null) {
            view.startAnimation(scaleAnimation);
        }
        return scaleAnimation;
    }

    public AlphaAnimation startFadein(View view, float a, float b, long time) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(a * 0.01f, b * 0.01f);
        alphaAnimation.setDuration(time);
        if (view != null) {
            view.startAnimation(alphaAnimation);
        }
        return alphaAnimation;
    }

    public ScaleAnimation startZoom(View view, float in, float out, long time, int type) {
        return startShrink(view, in, out, in, out, 50, 50, time, type);
    }

    public TranslateAnimation startLevel(View view, float in, float out, long time, int type) {
        return startMove(view, in, out, 0, 0, time, type);
    }

    public TranslateAnimation startPlumb(View view, float in, float out, long time, int type) {
        return startMove(view, 0, 0, in, out, time, type);
    }

    public void startValue(View v, int i, long time, OnUiStart onClick) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                onClick.OnUIStartListener();
            }
        });
    }

    public GradientDrawable.Orientation getGradientDrawableJB(int index) {
        GradientDrawable.Orientation[] list = {GradientDrawable.Orientation.TOP_BOTTOM, GradientDrawable.Orientation.LEFT_RIGHT, GradientDrawable.Orientation.BL_TR, GradientDrawable.Orientation.TL_BR};
        return list[index];
    }

    public int getGradientDrawablevarype(int index) {
        int[] list = {android.graphics.drawable.GradientDrawable.LINEAR_GRADIENT, android.graphics.drawable.GradientDrawable.RADIAL_GRADIENT, android.graphics.drawable.GradientDrawable.SWEEP_GRADIENT};
        return list[index];
    }


    public SeekBar getSeekBar(String color, int max, int pro, OnSeekBarCall run) {
        SeekBar seekBar = new SeekBar(activity);
        seekBar.getThumb().setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN);
        seekBar.getProgressDrawable().setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN);
        seekBar.setMax(max);
        seekBar.setProgress(pro);
        if (run != null) {
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    run.OnSeekBarLinstener(seekBar);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }

            });
        }

        return seekBar;
    }
}





