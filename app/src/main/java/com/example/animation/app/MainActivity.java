package com.example.animation.app;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;


public class MainActivity extends ActionBarActivity {

    private LinearLayout blocks;
    private ImageView ivBlue;
    private ImageView ivGreen;
    private ImageView ivRed;
    int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivRed = (ImageView) findViewById(R.id.ivRed);
        ivGreen = (ImageView) findViewById(R.id.ivGreen);
        ivBlue = (ImageView) findViewById(R.id.ivBlue);
        blocks = (LinearLayout) findViewById(R.id.blocks);

        initTransition();
    }

    public void startAnimation(View view) {
        int i = blocks.getChildCount();
        int index = i == 0 ? 0 : new Random().nextInt(i);

        switch (view.getId()) {
            case R.id.btnRed:

                ObjectAnimator objectanimator = ObjectAnimator.ofFloat(ivRed, View.ALPHA, 1f, 0f);
                objectanimator.setDuration(2000);
                objectanimator.setRepeatCount(1);
                objectanimator.setRepeatMode(ObjectAnimator.REVERSE);
                objectanimator.start();
                return;

            case R.id.btnGreen:

                ivGreen.animate()
                       .scaleX(2f)
                       .scaleY(2f)
                       .rotationBy(360)
                       .setDuration(2000)
                       .setInterpolator(new AccelerateInterpolator())
                       .start();
                return;

            case R.id.btnBlue:

                Integer blue = Integer.valueOf(0xff0e11e6);
                Integer yellow = Integer.valueOf(0xfff8ff49);

                ValueAnimator valueanimator = ValueAnimator.ofObject(new ArgbEvaluator(), blue, yellow);
                valueanimator.setDuration(2000);
                valueanimator.addUpdateListener(new AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueanimator1) {
                        ivBlue.setBackgroundColor(((Integer)valueanimator1.getAnimatedValue()).intValue());
                    }
                });
                valueanimator.start();
                return;

            case R.id.btnAdd:

                blocks.addView(getRandomColorView(), index);
                return;

            case R.id.btnRemove:

                if (i > 0) blocks.removeViewAt(index);
                return;
        }
    }

    private View getRandomColorView() {
        View view = new View(this);
        view.setLayoutParams(new android.widget.LinearLayout.LayoutParams(-1, 96));
        Random random = new Random();
        view.setBackgroundColor(Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        view.setTranslationX(-width);
        return view;
    }

    //API Level 11+
    private void initTransition() {
        width = getResources().getDisplayMetrics().widthPixels;
        LayoutTransition layouttransition = new LayoutTransition();

        ObjectAnimator anim0 = ObjectAnimator.ofFloat(null, View.TRANSLATION_X, 0);
        anim0.setInterpolator(new BounceInterpolator());
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(null, View.ALPHA, 0.5f, 1.0f);
        anim1.setInterpolator(new AccelerateInterpolator());

        AnimatorSet appearSet = new AnimatorSet();
        appearSet.playTogether(anim0, anim1);

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(null, View.TRANSLATION_X, 0f, width);
        anim2.setInterpolator(new AnticipateInterpolator());
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(null, View.ALPHA, 1.0F, 0.5F);

        AnimatorSet disappearSet = new AnimatorSet();
        disappearSet.playTogether(anim2, anim3);

        layouttransition.setAnimator(LayoutTransition.APPEARING, appearSet);
        layouttransition.setAnimator(LayoutTransition.DISAPPEARING, disappearSet);
        layouttransition.setDuration(LayoutTransition.APPEARING, 2000);
        blocks.setLayoutTransition(layouttransition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_view_pager) {
            Intent intent = new Intent(this, ViewPagerActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
