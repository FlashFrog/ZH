package com.example.frog.zhbj;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import com.example.frog.zhbj.Utils.CacheUtils;

public class WelcomeUI extends Activity {

    private final static long  DURATION = 1000;

    private View mContainer; //外侧容器

    public final static String KEY_IS_FIRST = "is_first"; //第一次登陆的标记

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        mContainer = findViewById(R.id.welcome_container);

        //实现动画
        AnimationSet set =  new AnimationSet(false);
        //1.旋转
        RotateAnimation rotateAnimation = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setFillEnabled(true);//是否保持状态
        rotateAnimation.setFillAfter(true);//保持为之后状态
        rotateAnimation.setDuration(DURATION);

        //2.缩放
        ScaleAnimation scaleAnimation = new ScaleAnimation(0f,1f,0f,1f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillEnabled(true);//是否保持状态
        scaleAnimation.setFillAfter(true);//保持为之后状态
        scaleAnimation.setDuration(DURATION);
        //3.透明渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f,1f);
        alphaAnimation.setFillEnabled(true);//是否保持状态
        alphaAnimation.setFillAfter(true);//保持为之后状态
        alphaAnimation.setDuration(DURATION);

        //添加动画
        set.addAnimation(rotateAnimation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);

        //开启动画
        mContainer.startAnimation(set);

        //设置动画的监听
        set.setAnimationListener(new WelcomeAnimationListener());
    }

    class WelcomeAnimationListener implements Animation.AnimationListener{
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //动画结束时调用
            boolean isFirst = CacheUtils.getBoolean(WelcomeUI.this,KEY_IS_FIRST,true);//默认是第一次打开应用。
            //页面跳转
            //第一次进入app，需要跳转至引导页面
            if(isFirst){
                Intent intent = new Intent(WelcomeUI.this,GuideUI.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(WelcomeUI.this,MainUI.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }


}
