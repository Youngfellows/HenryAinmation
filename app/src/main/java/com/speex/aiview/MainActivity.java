package com.speex.aiview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.speex.aiview.view.VerticalScrollLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private VerticalScrollLayout vScrollLayout;
    private List<Item> mItemList;
    private TextView mTvWakeUp;
    private Animation mAnimation;
    private TextView mTvRecognition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVScrollLayout();

        initWakeUp();
        initImageAndText();
    }

    private void initImageAndText() {
        TextView textView = (TextView) findViewById(R.id.tv_img_text);

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        ImageSpan imageSpan1 = new ImageSpan(this, R.mipmap.small_mic);
        SpannableString spannableString1 = new SpannableString("点 ");
        spannableString1.setSpan(imageSpan1, spannableString1.length() - 1, spannableString1.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

//        ImageSpan imageSpan = new ImageSpan(this,R.mipmap.small_mic);
//        SpannableString spannableString2 = new SpannableString(" ");
//        spannableString2.setSpan(imageSpan, spannableString2.length() - 1, spannableString2.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        spannableStringBuilder.append(spannableString1);
        spannableStringBuilder.append(" 查看更多语音提示");
//        spannableStringBuilder.append(spannableString2);

        textView.setText(spannableStringBuilder);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpMangoTV();
            }
        });
        textView.requestFocus();
    }

    private void jumpMangoTV() {
        Toast.makeText(this, "你妹的", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setClass(this, MangoTVActivity.class);
        startActivity(intent);
    }

    private void initWakeUp() {
        mTvWakeUp = (TextView) findViewById(R.id.tv_wake_up);
        mTvRecognition = (TextView) findViewById(R.id.tv_recognition);
//        mAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
    }

    public void addMessage(View view) {
        jumpMangoTV();//跳转到测试页

        //平移 + 透明度变化 + 缩放动画
//        ObjectAnimator translationX = new ObjectAnimator().ofFloat(mTvRecognition, "translationX", 0f, 0f);
//        ObjectAnimator translationY = new ObjectAnimator().ofFloat(mTvRecognition, "translationY", 0, -mTvRecognition.getMeasuredHeight());
//        ObjectAnimator alpha = new ObjectAnimator().ofFloat(mTvRecognition, "alpha", 1f, 0.8f, 0.5f);
//
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mTvRecognition, "scaleX", 1f, 0.7f);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mTvRecognition, "scaleY", 1f, 0.7f);
//
//        AnimatorSet animatorSet2 = new AnimatorSet();  //组合动画
//        animatorSet2.playTogether(translationX, translationY, alpha, scaleX, scaleY); //设置动画
//        animatorSet2.setDuration(500);  //设置动画时间
//        animatorSet2.start(); //启动
//
//
//        //下面的动画
//        ObjectAnimator translationX1 = new ObjectAnimator().ofFloat(mTvWakeUp, "translationX", 0f, 0f);
//        ObjectAnimator translationY1 = new ObjectAnimator().ofFloat(mTvWakeUp, "translationY", mTvWakeUp.getMeasuredHeight(), 0);
//        ObjectAnimator alpha1 = new ObjectAnimator().ofFloat(mTvWakeUp, "alpha", 0, 1, 1, 1);
//
//
//        AnimatorSet animatorSet = new AnimatorSet();  //组合动画
//        animatorSet.playTogether(translationX1, translationY1, alpha1); //设置动画
//        animatorSet.setDuration(500);  //设置动画时间
//        animatorSet.start(); //启动


    }

    /**
     * 模拟语音输入
     *
     * @param view
     */
    public void imitateInput(View view) {
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


    private void initVScrollLayout() {
        vScrollLayout = (VerticalScrollLayout) findViewById(R.id.scroll_layout);
        TestAdapter adapter = new TestAdapter();
        vScrollLayout.setAdapter(adapter);
        mItemList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Item item = new Item();
            item.title = "XXX" + i;
            item.text = "应该要显示的识别内容" + i;
            mItemList.add(item);
        }
        adapter.setList(mItemList);
    }


    private static class Item {
        public String title;
        public String text;
    }


    private static class TestAdapter extends BaseAdapter {
        private List<Item> list;

        public TestAdapter() {
            this.list = new ArrayList<>();
        }

        public void setList(List<Item> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Item getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Item item = getItem(position);
            holder.title.setText(item.title);
            holder.text.setText(item.text);
            holder.title1.setText(item.title);
            holder.text1.setText(item.text);
            return convertView;
        }
    }

    private static class ViewHolder {
        private TextView title;
        private TextView text;
        private TextView title1;
        private TextView text1;

        public ViewHolder(View view) {
            this.title = (TextView) view.findViewById(R.id.title);
            this.text = (TextView) view.findViewById(R.id.text);
            this.title1 = (TextView) view.findViewById(R.id.title1);
            this.text1 = (TextView) view.findViewById(R.id.text1);
        }
    }
}
