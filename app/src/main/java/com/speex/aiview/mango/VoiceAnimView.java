package com.speex.aiview.mango;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

/**
 * Created by dell on 2018/5/21.
 * 移动的语音条
 */

public class VoiceAnimView extends RelativeLayout {
    public enum AnimType {
        move,
        scaleMove,
        fly,
        close
    }

    private enum LightBarMode {
        left,
        middle,
        right
    }

    private BgView mBgView;
    private LightBar mLightBar;

    private static final int DEFAULT_BACK_COLOR = 0xff191F2B;
    private static final int DEFAULT_LIGHT_COLOR = 0xff009FE9;
    private static final int DEFAULT_DIM_COLOR = 0x0000CDF6;

    private int mBackColor = DEFAULT_BACK_COLOR;
    private int mColorLight = DEFAULT_LIGHT_COLOR;
    private int mColorDim = DEFAULT_DIM_COLOR;

    private IAnim mRunningAnim;

    private AnimFactory mAnimFactory;

    public VoiceAnimView(@NonNull Context context) {
        super(context);
    }

    public VoiceAnimView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {

        initResouces();

        initViews();

    }

    private void initResouces() {
    }

    private void initViews() {
        mBgView = new BgView(getContext());
        mBgView.setBackgroundColor(mBackColor);
        addView(mBgView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        mLightBar = new LightBar(getContext());
        LayoutParams lightbarParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lightbarParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        addView(mLightBar, lightbarParams);
    }

    public void doAnim(AnimType type) {
        cancelAnim();

        switch (type) {
            case move:

                if (!(mRunningAnim instanceof MoveAnim)) {
                    mRunningAnim = new MoveAnim();
                }

                break;
            case scaleMove:

                if (!(mRunningAnim instanceof ScaleAnim)) {
                    mRunningAnim = new ScaleAnim();
                }

                break;
            case fly:

                if (!(mRunningAnim instanceof FlyAnim)) {
                    mRunningAnim = new FlyAnim();
                }

                break;
            case close:

                if (!(mRunningAnim instanceof CloseAnim)) {
                    mRunningAnim = new CloseAnim();
                }

                break;
            default:

                mRunningAnim = null;

                break;
        }

        if (mRunningAnim != null) {
            mRunningAnim.start();
        }
    }

    public void doAnim(IAnim anim) {
        cancelAnim();

        if (anim != null) {
            mRunningAnim = anim;
            mRunningAnim.start();
        }
    }

    public void cancelAnim() {
        if (mRunningAnim != null) {
            mRunningAnim.cancel();
        }
    }

    public boolean isAnimRunning(AnimType type) {
        if (mRunningAnim != null && mRunningAnim.isRunning()) {
            switch (type) {
                case move:
                    return true;
                case scaleMove:
                    return true;
            }
            return false;
        }
        return false;
    }

    public boolean isAnimRunning() {
        if (mRunningAnim != null && mRunningAnim.isRunning()) {
            return true;
        }
        return false;
    }

    public void reset() {
        setScaleX(1);
        mLightBar.setScaleX(1);

    }

    public void setBackColor(int color) {
        mBackColor = color;
        mBgView.setBackgroundColor(mBackColor);
    }

    public void setLightColor(int color) {
        mColorLight = color;
        mLightBar.invalidate();
    }

    public void setDimColor(int color) {
        mColorDim = color;
        mLightBar.invalidate();
    }

    private int dp2px(int dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public AnimFactory getAnimFactory() {
        if (mAnimFactory == null) {
            mAnimFactory = new AnimFactory();
        }
        return mAnimFactory;
    }

    public class AnimFactory {
        public IAnim createMoveAnim(float selfRatio, float moveRatio, int moveTime, int delayTime) {
            MoveAnim anim = new MoveAnim();
            anim.selfRatio = selfRatio;
            anim.moveRatio = moveRatio;
            anim.moveTime = moveTime;
            anim.delayTime = delayTime;
            return anim;
        }

        public IAnim createScaleMoveAnim(float selfRatio, float moveRatio, int moveTime, int scaleTime) {
            ScaleAnim anim = new ScaleAnim();
            anim.selfRatio = selfRatio;
            anim.moveRatio = moveRatio;
            anim.moveTime = moveTime;
            anim.scaleTime = scaleTime;
            return anim;
        }

        public IAnim createFlyAnim(float selfRatio, int moveTime) {
            FlyAnim anim = new FlyAnim();
            anim.selfRatio = selfRatio;
            anim.moveTime = moveTime;
            return anim;
        }

        public IAnim createCloseAnim(float selfRatio, int lightScaleTime, int backScaleTime) {
            CloseAnim anim = new CloseAnim();
            anim.selfRatio = selfRatio;
            anim.lightScaleTime = lightScaleTime;
            anim.backScaleTime = backScaleTime;
            return anim;
        }
    }

    private interface IAnim {
        void start();

        void cancel();

        boolean isRunning();
    }

    private class MoveAnim implements IAnim {
        private ValueAnimator animator;
        private ValueAnimator animator2;

        private float selfRatio;
        private float moveRatio;
        private int moveTime;
        private int delayTime;

        private boolean isStart;

        MoveAnim() {
            selfRatio = 0.3f;
            moveRatio = 0.8f;
            moveTime = 800;
            delayTime = 300;
        }

        private void initAnim() {
            final float fromValue = 0.5f * (1 - moveRatio);
            final float toValue = 0.5f * (1 + moveRatio);
            animator = ValueAnimator.ofFloat(fromValue, toValue);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = animation.getAnimatedFraction() * (toValue - fromValue) + fromValue;
                    Log.i("MoveAnim", "animator onAnimationUpdate " + value);

                    mLightBar.setLightPosX(value);
                }
            });
            animator.setDuration(moveTime);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (isStart) {
                        Log.i("MoveAnim", "animator onAnimationEnd ");
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                animator2.start();
                            }
                        }, delayTime);

                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.i("MoveAnim", "animator onAnimationCancel");
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            animator2 = ValueAnimator.ofFloat(toValue, fromValue);
            animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = animation.getAnimatedFraction() * (fromValue - toValue) + toValue;
                    Log.i("MoveAnim", "animator onAnimationUpdate " + value);
                    mLightBar.setLightPosX(value);
                }
            });
            animator2.setDuration(moveTime);
            animator2.setInterpolator(new AccelerateDecelerateInterpolator());
            animator2.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (isStart) {
                        Log.i("MoveAnim", "animator2 onAnimationEnd");
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                animator.start();
                            }
                        }, delayTime);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.i("MoveAnim", "animator2 onAnimationCancel");
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        @Override
        public void start() {
            isStart = true;

            reset();

            int parentWidth = ((View) getParent()).getWidth();
            LayoutParams params = (LayoutParams) mLightBar.getLayoutParams();
            params.width = (int) (parentWidth * selfRatio);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            mLightBar.setLayoutParams(params);
            mLightBar.setDefaultMode();

            initAnim();

            animator.start();

        }

        @Override
        public void cancel() {
            isStart = false;
            Log.i("MoveAnim", "animator2 cancel ");
            if (animator.isRunning() || animator.isStarted()) {
                animator.cancel();
            }
            if (animator2.isRunning() || animator2.isStarted()) {
                animator2.cancel();
            }
        }

        @Override
        public boolean isRunning() {
            return animator.isRunning() || animator2.isRunning();
        }
    }

    private class ScaleAnim implements IAnim {
        private ObjectAnimator scaleAnimator;

        private ValueAnimator animator;
        private ValueAnimator animator2;

        private AnimatorSet animatorSet;

        private float selfRatio;
        private float moveRatio;
        private int scaleTime;
        private int moveTime;

        private boolean isStart;

        ScaleAnim() {
            selfRatio = 0.5f;
            moveRatio = 0.8f;
            moveTime = 1200;
            scaleTime = 800;
        }

        private void initAnim() {
            animatorSet = new AnimatorSet();

            scaleAnimator = ObjectAnimator.ofFloat(mLightBar, "scaleX", 1f, 0.5f, 1f);
            scaleAnimator.setDuration(scaleTime);
//            scaleAnimator.setInterpolator(new AccelerateInterpolator());
            scaleAnimator.setInterpolator(new TimeInterpolator() {
                private AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
                private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();

                @Override
                public float getInterpolation(float input) {
                    float result = 0;
                    if (input < 0.5f) {
                        result = decelerateInterpolator.getInterpolation(input);
                    } else {
                        result = accelerateInterpolator.getInterpolation(input);
                    }
                    return result;
                }
            });
            scaleAnimator.setRepeatCount(-1);

            final float fromValue = 0.5f * (1 - moveRatio);
            final float toValue = 0.5f * (1 + moveRatio);
            animator = ValueAnimator.ofFloat(fromValue, toValue);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = animation.getAnimatedFraction() * (toValue - fromValue) + fromValue;
                    Log.i("MoveAnim", "animator onAnimationUpdate " + value);

                    mLightBar.setLightPosX(value);
                }
            });
            animator.setDuration(moveTime);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (isStart) {
                        animator2.start();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            animator2 = ValueAnimator.ofFloat(toValue, fromValue);
            animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = animation.getAnimatedFraction() * (fromValue - toValue) + toValue;
                    Log.i("MoveAnim", "animator onAnimationUpdate " + value);
                    mLightBar.setLightPosX(value);
                }
            });
            animator2.setDuration(moveTime);
            animator2.setInterpolator(new AccelerateDecelerateInterpolator());
            animator2.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.i("VoiceAnimView", "onAnimationEnd");
                    if (isStart) {
                        animator.start();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.i("VoiceAnimView", "onAnimationCancel");
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            animatorSet.playTogether(scaleAnimator, animator);
        }

        @Override
        public void start() {
            isStart = true;

            reset();

            int parentWidth = ((View) getParent()).getWidth();
            LayoutParams params = (LayoutParams) mLightBar.getLayoutParams();
            params.width = (int) (parentWidth * selfRatio);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            mLightBar.setLayoutParams(params);
            mLightBar.setTranslationX(0);
            mLightBar.setDefaultMode();

            initAnim();

            animatorSet.start();
        }

        @Override
        public void cancel() {
            isStart = false;

            if (animatorSet.isRunning() || animatorSet.isStarted()) {
                animatorSet.cancel();
            }
            if (animator.isRunning() || animator.isStarted()) {
                animator.cancel();
            }
            if (animator2.isRunning() || animator2.isStarted()) {
                animator2.cancel();
            }

        }

        @Override
        public boolean isRunning() {
            return animatorSet.isRunning();
        }
    }

    private class FlyAnim implements IAnim {
        private ValueAnimator innerAnimator;
        private ObjectAnimator animator;
        private ValueAnimator innerAnimator2;
        private ObjectAnimator animator2;
        private AnimatorSet animatorSet;

        private float selfRatio;
        private int moveDistance;
        private int innerMoveTime;
        private int moveTime;

        private boolean isStart;

        FlyAnim() {
            selfRatio = 0.3f;
            moveTime = 1600;
        }

        private void initAnim() {
            int parentWidth = ((View) getParent()).getWidth();
            int width = mLightBar.getLayoutParams().width;
            moveDistance = parentWidth - width;

            innerMoveTime = (int) (width / (moveDistance * 1f / moveTime));

            final float fromValue = 0f;
            final float toValue = 1f;
            innerAnimator = ValueAnimator.ofFloat(fromValue, toValue);
//            innerAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            innerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = animation.getAnimatedFraction() * (toValue - fromValue) + fromValue;
                    Log.i("MoveAnim", "animator onAnimationUpdate " + value);

                    mLightBar.setLightPosX(value);
                }
            });
            innerAnimator.setDuration(innerMoveTime);

            animator = ObjectAnimator.ofFloat(mLightBar, "translationX", 0, moveDistance);
//            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(moveTime);

            innerAnimator2 = ValueAnimator.ofFloat(toValue, fromValue);
//            innerAnimator2.setInterpolator(new AccelerateDecelerateInterpolator());
            innerAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = animation.getAnimatedFraction() * (fromValue - toValue) + toValue;
                    Log.i("MoveAnim", "animator onAnimationUpdate " + value);

                    mLightBar.setLightPosX(value);
                }
            });
            innerAnimator2.setDuration(innerMoveTime);


            animator2 = ObjectAnimator.ofFloat(mLightBar, "translationX", moveDistance, 0);
//            animator2.setInterpolator(new AccelerateDecelerateInterpolator());
            animator2.setDuration(moveTime);
            animator2.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    mLightBar.setMode(LightBarMode.right);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (isStart) {
                        animatorSet.start();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            animatorSet = new AnimatorSet();
            animatorSet.playSequentially(innerAnimator, animator, innerAnimator2, animator2);

        }

        @Override
        public void start() {
            isStart = true;

            reset();

            int parentWidth = ((View) getParent()).getWidth();
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params.width = (int) (parentWidth * selfRatio);
            mLightBar.setLayoutParams(params);

            mLightBar.setTranslationX(0);

            initAnim();

            animatorSet.start();
        }

        @Override
        public void cancel() {
            isStart = false;
            animatorSet.cancel();
        }

        @Override
        public boolean isRunning() {
            return animatorSet.isRunning();
        }
    }

    private class CloseAnim implements IAnim {
        private ObjectAnimator scaleAnimator;
        private ObjectAnimator scaleAnimator2;

        private AnimatorSet animatorSet;

        private float selfRatio;
        private int lightScaleTime;
        private int backScaleTime;

        private boolean isStart;

        CloseAnim() {
            selfRatio = 0.5f;
            lightScaleTime = 400;
            backScaleTime = 400;
        }

        private void initAnim() {
            animatorSet = new AnimatorSet();

            scaleAnimator = ObjectAnimator.ofFloat(mLightBar, "scaleX", 1f, 0f);
            scaleAnimator.setDuration(lightScaleTime);
            scaleAnimator.setInterpolator(new AccelerateInterpolator());

            scaleAnimator2 = ObjectAnimator.ofFloat(VoiceAnimView.this, "scaleX", 1f, 0f);
            scaleAnimator2.setDuration(backScaleTime);
            scaleAnimator2.setInterpolator(new AccelerateInterpolator());

            animatorSet.playSequentially(scaleAnimator, scaleAnimator2);
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    reset();
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

        }

        @Override
        public void start() {
            isStart = true;

            int parentWidth = ((View) getParent()).getWidth();
            LayoutParams params = (LayoutParams) mLightBar.getLayoutParams();
            params.width = (int) (parentWidth * selfRatio);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            mLightBar.setLayoutParams(params);
            mLightBar.setTranslationX(0);
            mLightBar.setDefaultMode();

            initAnim();

            animatorSet.start();
        }

        @Override
        public void cancel() {
            isStart = false;

            if (animatorSet.isRunning() || animatorSet.isStarted()) {
                animatorSet.cancel();
            }
            if (scaleAnimator.isRunning() || scaleAnimator.isStarted()) {
                scaleAnimator.cancel();
            }
            if (scaleAnimator2.isRunning() || scaleAnimator2.isStarted()) {
                scaleAnimator2.cancel();
            }

        }

        @Override
        public boolean isRunning() {
            return animatorSet.isRunning();
        }
    }

    private class BgView extends View {

        public BgView(Context context) {
            super(context);
        }
    }

    private class ClipView extends RelativeLayout {
        private boolean isClip = true;

        public ClipView(Context context) {
            super(context);
        }

        public void setClip(boolean isClip) {
            this.isClip = isClip;
        }

        @Override
        public void draw(Canvas canvas) {
            if (!isClip) {
                super.draw(canvas);
                return;
            }

            int left = 200;
            int top = 0;
            int right = getWidth() - 200;
            int bottom = getHeight();
            canvas.save();
            canvas.clipRect(left, top, right, bottom);

            super.draw(canvas);

            canvas.restore();
        }
    }

    private class LightBar extends View {
        private Paint paint;

        private LightBarMode curMode;

        private LinearGradient backGradient;

        private float lightPosX;

        public LightBar(Context context) {
            super(context);

            curMode = LightBarMode.middle;
            paint = new Paint();
            updateGradient();
        }

        public void setMode(LightBarMode mode) {
            if (curMode == mode) {
                return;
            }
            curMode = mode;
            switch (curMode) {
                case left:

                    lightPosX = 0.9f;

                    break;
                case middle:

                    lightPosX = 0.5f;

                    break;
                case right:

                    lightPosX = 0.1f;

                    break;
            }

            updateGradient();

            invalidate();
        }

        public void setDefaultMode() {
            setMode(LightBarMode.middle);
        }

        public void setLightPosX(float lightPosX) {
            if (lightPosX >= 0 && lightPosX <= 1) {
                this.lightPosX = lightPosX;
                postInvalidate();
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            int width = getWidth();
            int height = getHeight();

            updateGradient();

            canvas.drawRect(0, 0, width, height, paint);
        }

        private void updateGradient() {
            int width = getWidth();
            int height = getHeight();

            if (width <= 0) {
                backGradient = null;
                return;
            }

            int colorLight = mColorLight;
            int colorDim = mColorDim;

            backGradient = new LinearGradient(0, 0, width, 0, new int[]{colorDim, colorLight, colorDim}, new float[]{0f, lightPosX, 1f}, Shader.TileMode.CLAMP);

            if (backGradient != null) {
                paint.setShader(backGradient);
            }

        }
    }

}
