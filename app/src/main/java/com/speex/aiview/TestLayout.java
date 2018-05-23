package com.speex.aiview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TestLayout extends AppCompatActivity {

    private LinearLayout mRootView;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_layout);
        mRootView = (LinearLayout) findViewById(R.id.mRootView);


        // 第一步，把 LinearLayout 添加到布局里面
        // 给LinearLayoutnew一个LinearLayout.LayoutParams，并且通过  etLayoutParams  让layout更新
        // LayoutParams可以从父窗体获得也可以自己new出来，这里我们采用自己new的方式
        mLinearLayout = new LinearLayout(this);
        mLinearLayout.setBackgroundColor(Color.parseColor("#0000ff"));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mLinearLayout.setLayoutParams(layoutParams);
        mRootView.addView(mLinearLayout);

        // 第二步，把TextView 添加到 第一步的 LinearLayout 里面
        TextView textView = new TextView(this);
        textView.setText("代码在此2");
        textView.setBackgroundColor(Color.parseColor("#ff0000"));
        textView.setGravity(Gravity.CENTER);
        mLinearLayout.addView(textView);

        // 第三步，把TextView获取对应父窗体类型的LayoutParams 并且设置参数更新layout
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(textView.getLayoutParams());
        textParams.width = dip2px(this, 200);
        textParams.height = dip2px(this, 30);
        //textParams.gravity = Gravity.CENTER;  // 无效，这样并不能让文字在控件里居中
        textView.setLayoutParams(textParams);

    }

    private int dip2px(Context context, float dipValue) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
    }
}
