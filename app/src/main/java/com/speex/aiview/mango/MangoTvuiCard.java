package com.speex.aiview.mango;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.speex.aiview.R;
import com.speex.aiview.utils.UiUtil;

/**
 * Created by Jie.Chen on 2018/5/23.
 * 芒果TV 识别Card
 */

public class MangoTvuiCard extends RelativeLayout implements IMicCard {
    private RelativeLayout mCardContainer;
    /**
     * 处理卡片显示与隐藏
     */
    private Handler mCardHandler;
    /**
     * 移动的语音条
     */
    private LayoutParams mMicContainerLayoutParams;

    /**
     * 识别内容
     */
    private LayoutParams mRecognitionCardLayoutParams;

    /**
     * Tip帮助
     */
    private LayoutParams mSimpleTipsViewLayoutParams;

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
        mCardHandler = new Handler(Looper.getMainLooper());

        mMicContainerLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//语音条
        mRecognitionCardLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);//识别内容
        mSimpleTipsViewLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);//Tip帮助


        mCardContainer = (RelativeLayout) View.inflate(getContext(), R.layout.mango_view_card_container, null);
//        mCardContainer.setFocusable(false);

        mMicContainer = (RelativeLayout) mCardContainer.findViewById(R.id.rl_mic);//语音条
        mRecongnitionContainer = (RelativeLayout) mCardContainer.findViewById(R.id.rl_recognition);//识别内容
        mTipsContainer = (RelativeLayout) mCardContainer.findViewById(R.id.rl_tips);//Tip帮助
//        mMicContainerLayoutParams.addRule(ALIGN_PARENT_RIGHT); // 靠右显示

        mSimpleTipsViewLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT); //Tip帮助 居中显示
        mTipsContainer.setLayoutParams(mSimpleTipsViewLayoutParams);

//        mMicContainerLayoutParams.topMargin = UiUtil.dip2px(context, 10);
//        mMicContainer.setLayoutParams(mMicContainerLayoutParams);
//
//        mRecognitionCardLayoutParams.bottomMargin = UiUtil.dip2px(context, 30);
//        mRecongnitionContainer.setLayoutParams(mRecognitionCardLayoutParams);
//
//        mSimpleTipsViewLayoutParams.bottomMargin = UiUtil.dip2px(context, 10);
//        mTipsContainer.setLayoutParams(mSimpleTipsViewLayoutParams);

        this.addView(mCardContainer);

        //设置显示位置
    }

    @Override
    public void show() {

    }

    @Override
    public void shortPressed() {

    }

    @Override
    public void onHomeKeyPressed() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void delayHide(int delayMillis) {

    }

    @Override
    public void updateMicState(String event, String data) {

    }

    @Override
    public void setRecognition(String recognitionStr) {

    }

    @Override
    public void onContextOutputText(String outputText) {

    }

    @Override
    public void onDialogError() {

    }

    @Override
    public void onDialogStart() {

    }

    @Override
    public void onDialogEnd() {

    }

    @Override
    public void onTTSError(String data) {

    }

    @Override
    public void onTTSStart(String data) {

    }

    @Override
    public void onTTSEnd(String data) {

    }
}
