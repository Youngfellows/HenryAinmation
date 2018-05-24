package com.speex.aiview.mango;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.speex.aiview.R;

import java.util.Random;

/**
 * Created by Jie.Chen on 2018/5/23.
 * 芒果TV 识别Card
 */

public class MangoTvuiCard extends RelativeLayout {
    private String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private RelativeLayout mCardContainer;
    /**
     * 处理卡片显示与隐藏
     */
    private Handler mCardHandler;
//    /**
//     * 移动的语音条
//     */
//    private LayoutParams mMicContainerLayoutParams;
//
//    /**
//     * 识别内容
//     */
//    private LayoutParams mRecognitionCardLayoutParams;
//
//    /**
//     * Tip帮助
//     */
//    private LayoutParams mSimpleTipsViewLayoutParams;

    /**
     * 移动的语音条
     */
    private RelativeLayout mMicContainer;

    /**
     * 识别内容
     */
    private RelativeLayout mRecongnitionContainer;

    /**
     * Tip帮助
     */
    private RelativeLayout mTipsContainer;
    private TextView mTvWakeUp;
    private TextView mTvRecognition;
    private VoiceAnimView mVoiceAnimView;

    public MangoTvuiCard(Context context) {
        this(context, null);
    }

    public MangoTvuiCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MangoTvuiCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    /**
     * 加载布局
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        mCardHandler = new Handler(Looper.getMainLooper());

//        mMicContainerLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//语音条
//        mRecognitionCardLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);//识别内容
//        mSimpleTipsViewLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);//Tip帮助

        //加载布局
        mCardContainer = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.mango_view_card_container, this, false);
        this.addView(mCardContainer);

        mMicContainer = (RelativeLayout) mCardContainer.findViewById(R.id.rl_mic);//语音条
        mRecongnitionContainer = (RelativeLayout) mCardContainer.findViewById(R.id.rl_recognition);//识别内容
        mTipsContainer = (RelativeLayout) mCardContainer.findViewById(R.id.rl_tips);//Tip帮助

        //识别内容
        mTvWakeUp = (TextView) findViewById(R.id.tv_wake_up);
        mTvRecognition = (TextView) findViewById(R.id.tv_recognition);

        //语音动效
        mVoiceAnimView = findViewById(R.id.voice_anim_view);

        // TODO: 2018/5/24 测试用
        mMicContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                imitateInput(null);
            }
        });
        findViewById(R.id.btn1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                middleMoveAnim();
            }
        });
        findViewById(R.id.btn2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                middleScaleAnim();
            }
        });
        findViewById(R.id.btn3).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                flyAnim();
            }
        });
        findViewById(R.id.btn4).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeAnim();
            }
        });
        findViewById(R.id.btn5).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                imitateInput(null);
            }
        });

    }

    /**
     * 语音条中间移动的动画
     */
    public void middleMoveAnim() {
        mVoiceAnimView.doAnim(mVoiceAnimView.getAnimFactory().createMoveAnim(0.3f, 0.8f, 800, 300));
    }

    /**
     * 语音条中间收缩、放大的动画
     */
    public void middleScaleAnim() {
        mVoiceAnimView.doAnim(mVoiceAnimView.getAnimFactory().createScaleMoveAnim(0.5f, 0.8f, 1200, 600));
    }

    /**
     * 语音条左右移动移动的动画
     */
    public void flyAnim() {
        mVoiceAnimView.doAnim(mVoiceAnimView.getAnimFactory().createFlyAnim(0.3f, 600));
    }

    /**
     * 语音条中间移动的动画
     */
    public void closeAnim() {
        mVoiceAnimView.doAnim(mVoiceAnimView.getAnimFactory().createCloseAnim(0.5f, 400, 300));
    }


    /**
     * 语音输入
     *
     * @param inputContext 输入内容
     */
    public void imitateInput(String inputContext) {
        int randomNumber = new Random(System.currentTimeMillis()).nextInt(10000);
        String newInput = "我想看周星驰他老婆演的电影" + randomNumber;
        String olderInput = mTvWakeUp.getText().toString();
        Log.i(TAG, "olderInput: " + olderInput);
        Log.i(TAG, "newInput: " + newInput);

        // TODO: 2018/5/22 设置动画，并设置内容改变
        setRecognitionAinmation(mTvRecognition, olderInput);
        setPushUpAnimation(mTvWakeUp, newInput);
    }

    /**
     * 设置识别内容的动画
     *
     * @param mTvRecognition
     * @param content        本次的识别内容
     */
    public void setRecognitionAinmation(TextView mTvRecognition, String content) {
        //设置内容
        mTvRecognition.setText(content);

        //平移 + 透明度变化 + 缩放动画
        ObjectAnimator translationX = new ObjectAnimator().ofFloat(mTvRecognition, "translationX", 0f, 0f);
        ObjectAnimator translationY = new ObjectAnimator().ofFloat(mTvRecognition, "translationY", 0, -mTvRecognition.getMeasuredHeight());
        ObjectAnimator alpha = new ObjectAnimator().ofFloat(mTvRecognition, "alpha", 1f, 0.8f, 0.5f);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mTvRecognition, "scaleX", 1f, 0.7f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mTvRecognition, "scaleY", 1f, 0.7f);

        AnimatorSet animatorSet2 = new AnimatorSet();  //组合动画
        animatorSet2.playTogether(translationX, translationY, alpha, scaleX, scaleY); //设置动画
        animatorSet2.setDuration(500);  //设置动画时间
        animatorSet2.start(); //启动
    }


    /**
     * 向上推动上次的识别内容
     *
     * @param mTvWakeUp
     * @param content   上次的识别内容
     */
    public void setPushUpAnimation(TextView mTvWakeUp, String content) {
        //设置内容
        mTvWakeUp.setText(content);

        //下面的动画
        ObjectAnimator translationX1 = new ObjectAnimator().ofFloat(mTvWakeUp, "translationX", 0f, 0f);
        ObjectAnimator translationY1 = new ObjectAnimator().ofFloat(mTvWakeUp, "translationY", mTvWakeUp.getMeasuredHeight(), 0);
        ObjectAnimator alpha1 = new ObjectAnimator().ofFloat(mTvWakeUp, "alpha", 0, 1, 1, 1);

        AnimatorSet animatorSet = new AnimatorSet();  //组合动画
        animatorSet.playTogether(translationX1, translationY1, alpha1); //设置动画
        animatorSet.setDuration(500);  //设置动画时间
        animatorSet.start(); //启动
    }
}
