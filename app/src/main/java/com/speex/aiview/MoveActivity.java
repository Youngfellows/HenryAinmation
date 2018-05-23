package com.speex.aiview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MoveActivity extends AppCompatActivity {
    private ImageView mIv;
    private TextView mTvBlue;
    private TextView mTvGreen;
    private ViewGroup.LayoutParams mVgLp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);
        mIv = (ImageView) findViewById(R.id.mIv);
        mTvBlue = (TextView) findViewById(R.id.mTvBlue);
        mTvGreen = (TextView) findViewById(R.id.mTvGreen);

        mVgLp = mIv.getLayoutParams();

        findViewById(R.id.mTvMoveRight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup.LayoutParams vg_lp = mIv.getLayoutParams();
                // RelativeLayout.LayoutParams 不可抽取为成员变量
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(vg_lp);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, R.id.mIv);
                mIv.setLayoutParams(params); //使layout更新
            }
        });

        findViewById(R.id.mTvMoveLeft).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 左 （默认处于顶部，所以看起来是左上）
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mVgLp);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                mIv.setLayoutParams(params);
            }
        });

        findViewById(R.id.mTvCenter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 居中
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mVgLp);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                mIv.setLayoutParams(params);
            }
        });

        findViewById(R.id.mTvLeftVer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 左部垂直居中
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mVgLp);
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT | RelativeLayout.CENTER_VERTICAL);
                mIv.setLayoutParams(params);
            }
        });

        findViewById(R.id.mTvRightBot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 右下
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mVgLp);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                mIv.setLayoutParams(params);
            }
        });
        //  绿在蓝右边
        findViewById(R.id.mTvaGRightB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup.LayoutParams vgLp = mTvGreen.getLayoutParams();
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(vgLp);
                params.addRule(RelativeLayout.RIGHT_OF, R.id.mTvBlue);
                mTvGreen.setLayoutParams(params);
            }
        });

        // 绿在蓝下边
        findViewById(R.id.mTvGElowB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup.LayoutParams vgLp = mTvGreen.getLayoutParams();
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(vgLp);
                params.addRule(RelativeLayout.BELOW, R.id.mTvBlue);
                mTvGreen.setLayoutParams(params);
            }
        });
    }
}
