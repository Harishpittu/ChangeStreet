package com.harish.changestreetassignment.utils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.harish.changestreetassignment.R;

import java.util.ArrayList;
import java.util.List;

public class SlideMenuAnimator {

    private Activity activity;
    private ArrayList<View> viewsList = new ArrayList<>();
    private List<String> menuList = new ArrayList<>();
    private static int OPEN_ANIMATION_DURATION = 300;
    private static int CLOSE_ANIMATION_DURATION = 250;
    private static int FLIP_ANIMATION_DURATION = 100;
    private static int FLIP_ANGLE = -15;
    private RelativeLayout gradeOutLayout;
    private static int viewWidth;
    private OnMenuItemClickListener onMenuItemClickListener;

    public SlideMenuAnimator(Activity activity, List<String> menuList, OnMenuItemClickListener onMenuItemClickListener) {
        this.activity = activity;
        this.menuList = menuList;
        this.onMenuItemClickListener = onMenuItemClickListener;
        init();
    }

    private void init() {
        addViewToList();
        viewWidth = convertDptoPx(100);
    }

    private int convertDptoPx(int dp) {

        Resources r = activity.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return (int) px;
    }

    private void addViewToList() {
        LinearLayout menuLayout = (LinearLayout) activity.findViewById(R.id.menu_layout);
        gradeOutLayout = (RelativeLayout) activity.findViewById(R.id.gradeOutLayout);
        gradeOutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeAnimation();
            }
        });

        for (int i = 0; i < menuList.size(); i++) {
            View viewMenu = activity.getLayoutInflater().inflate(R.layout.custom_slide_menu_item, null);
            TextView textView = (TextView) viewMenu.findViewById(R.id.textview);
            textView.setText(menuList.get(i));
            View space = new View(activity);
            space.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5));
            menuLayout.addView(viewMenu);
            menuLayout.addView(space);

            viewsList.add(viewMenu);
            viewMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView textView = (TextView) view.findViewById(R.id.textview);
                    onMenuItemClickListener.onMenuItemClickListener(textView.getText().toString());
                    clickAnimation(view);

                }
            });
        }
    }

    public void clickAnimation(final View view) {
        setViewColorsDefault();
        view.setBackgroundColor(Color.parseColor("#76c7e4"));
        TextView tv = (TextView) view.findViewById(R.id.textview);
        tv.setTextColor(Color.WHITE);

        CustomFlipAnimation rotation =
                new CustomFlipAnimation(0, FLIP_ANGLE, view.getWidth(), view.getHeight() / 2.0f);
        rotation.setDuration(FLIP_ANIMATION_DURATION);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new LinearInterpolator());
        rotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                CustomFlipAnimation reverseRotation =
                        new CustomFlipAnimation(FLIP_ANGLE, 0, view.getWidth(), view.getHeight() / 2.0f);
                reverseRotation.setDuration(FLIP_ANIMATION_DURATION);
                reverseRotation.setFillAfter(true);
                reverseRotation.setInterpolator(new LinearInterpolator());
                reverseRotation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        closeAnimation();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(reverseRotation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(rotation);
    }

    public void setViewColorsDefault() {
        for (View view :
                viewsList) {
            view.setBackgroundColor(Color.WHITE);
            TextView tv = (TextView) view.findViewById(R.id.textview);
            tv.setTextColor(Color.parseColor("#424242"));
        }
    }

    public void openAnimation() {

        AlphaAnimation animation1 = new AlphaAnimation(0f, 0.5f);
        animation1.setDuration(100);
        animation1.setFillAfter(true);
        gradeOutLayout.setVisibility(View.VISIBLE);
        gradeOutLayout.setEnabled(true);
        gradeOutLayout.startAnimation(animation1);
        for (int i = 0; i < viewsList.size(); i++) {
            final double position = i;
            final int viewPosition = i;
            final double delay = (OPEN_ANIMATION_DURATION) * (position / viewsList.size());
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (position < viewsList.size()) {
                        final View view = viewsList.get(viewPosition);
                        CustomFlipAnimation rotation =
                                new CustomFlipAnimation(-20, 0, viewWidth, view.getHeight() / 2.0f);
                        rotation.setDuration(OPEN_ANIMATION_DURATION);
                        rotation.setFillAfter(true);
                        rotation.setInterpolator(new DecelerateInterpolator());
                        view.startAnimation(rotation);

                        widthanim(view, 0, viewWidth, new AccelerateDecelerateInterpolator(), OPEN_ANIMATION_DURATION);
                    }

                }
            }, (long) delay);
        }
    }

    private void widthanim(final View view, int xFrom, int xTo, Interpolator interpolator, int duration) {
        ValueAnimator anim = ValueAnimator.ofInt(xFrom, xTo);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = val;
                view.setLayoutParams(layoutParams);
            }
        });
        anim.setInterpolator(interpolator);
        anim.setDuration(duration);
        anim.start();
    }

    public void closeAnimation() {

        for (int i = 0; i < viewsList.size(); i++) {
            final double position = i;
            final int viewPosition = i;
            final double delay = (CLOSE_ANIMATION_DURATION) * (position / viewsList.size());
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (position < viewsList.size()) {
                        final View view = viewsList.get(viewPosition);
                        widthanim(view, viewWidth, 0, new AccelerateInterpolator(), CLOSE_ANIMATION_DURATION);
                        CustomFlipAnimation rotation =
                                new CustomFlipAnimation(0, -20, viewWidth, view.getHeight() / 2.0f);
                        rotation.setDuration(CLOSE_ANIMATION_DURATION);
                        rotation.setFillAfter(true);
                        rotation.setInterpolator(new AccelerateInterpolator());
                        view.startAnimation(rotation);
                        if (position == viewsList.size() - 1) {
                            AlphaAnimation animation1 = new AlphaAnimation(0.5f, 0f);
                            animation1.setDuration(150);
                            animation1.setFillAfter(true);
                            gradeOutLayout.setEnabled(false);
                            gradeOutLayout.startAnimation(animation1);
                        }
                    }

                }
            }, (long) delay);
        }
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClickListener(String menuItem);
    }

}
