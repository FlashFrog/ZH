package com.example.frog.zhbj;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.frog.zhbj.Utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;

public class GuideUI extends Activity {

    private ViewPager mPager ;
    private Button mBtnStart;
    private List<ImageView> mImageList;
    private View mSelectPoint; //选中的点

    private LinearLayout mPointContainer; //装点的容器
    private int mSpace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);

        mPager = (ViewPager)findViewById(R.id.guide_pager);
        mBtnStart = (Button)findViewById(R.id.guide_btn_start);
        mPointContainer = (LinearLayout)findViewById(R.id.guide_point_container);
        mSelectPoint = findViewById(R.id.guide_point_selected);
        initData();

        //计算点与点之间的距离
        mSelectPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //当UI的树布局改变时调用
                if(mImageList == null){return;}
                mSelectPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mSpace = mPointContainer.getChildAt(1).getLeft() - mPointContainer.getChildAt(0).getLeft();
            }
        });

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //保存非第一次进入的状态
                CacheUtils.setBoolean(GuideUI.this,WelcomeUI.KEY_IS_FIRST,false);
                Intent intent = new Intent(GuideUI.this,MainUI.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //初始化数据
    private void initData(){

        int[] imgRes = {R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};

        //完善list
        mImageList = new ArrayList<>();
        for(int i=0; i<imgRes.length; i++){
            //新建ImageView
            ImageView iv = new ImageView(this);
            iv.setImageResource(imgRes[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            //给list添加ImageView
            mImageList.add(iv);

            //动态添加点
            View point = new View(this);
            point.setBackgroundResource(R.drawable.guide_point_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,30);
            if(i != 0){
                params.leftMargin = 15;
            }
            mPointContainer.addView(point,params);

        }


        mPager.setAdapter(new GuidePagerAdapter());

        //给Viewpager监听
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //1.去对滑动的点做操作
                //2.设置marginLeft
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mSelectPoint.getLayoutParams();
                params.leftMargin = (int)(mSpace * position + mSpace * positionOffset+0.5f);                                                                                                                 ; //值的设置
            }

            @Override
            public void onPageSelected(int position) {
                mBtnStart.setVisibility(position == mImageList.size()-1? View.VISIBLE:View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class GuidePagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            if(mImageList != null){return mImageList.size(); }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            ImageView iv = mImageList.get(position);

            // 添加到viewpager中
            // mPager.addView(iv);
            container.addView(iv);

            // 需要返回的是显示的ImageView
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            // 从viewpager中移除imageView
            // ImageView iv = mImgList.get(position);
            // mPager.removeView(iv);
            container.removeView((View) object);
        }
    }
}
