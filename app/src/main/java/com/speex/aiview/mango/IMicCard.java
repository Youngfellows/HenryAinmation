package com.speex.aiview.mango;

public interface IMicCard {
    /**
     * 设置可见
     */
    void show();

    /**
     * 短按
     */
    void shortPressed();

    /**
     * 短按
     */
    void onHomeKeyPressed();

    /**
     * 设置隐藏
     */
    void hide();

    /**
     * 指定时长,延时隐藏
     *
     * @param delayMillis
     */
    void delayHide(int delayMillis);

    /**
     * 更新mic状态
     *
     * @param event
     * @param data
     */
    void updateMicState(String event, String data);

    /**
     * 设置识别框内容
     *
     * @param recognitionStr
     */
    void setRecognition(String recognitionStr);

    void onContextOutputText(String outputText);

    void onDialogError();

    void onDialogStart();

    void onDialogEnd();

    void onTTSError(String data);

    void onTTSStart(String data);

    void onTTSEnd(String data);
}
