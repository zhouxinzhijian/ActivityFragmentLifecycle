package com.example.bruce.androidlifecycle.lifecyclelog;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.util.Log;
import android.view.Menu;

import com.example.bruce.androidlifecycle.R;

import static com.example.bruce.androidlifecycle.lifecyclelog.Util.LifecycleState.CALL_TO_SUPER;
import static com.example.bruce.androidlifecycle.lifecyclelog.Util.LifecycleState.RETURN_FROM_SUPER;
import static com.example.bruce.androidlifecycle.lifecyclelog.Util.recLifeCycle;

/**
 * A standard v4 support version of Activity.
 */
public class BruceFragmentActivity extends FragmentActivity {
    @Override
    public Resources getResources() {//解决系统改变字体大小的时候导致的界面布局混乱的问题,Application中也要加
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics());
        return res;
    }
    /**-------------------------------经常会用到的------------------------------------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(getClass().getSimpleName(), "this is " + this);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        setContentView(R.layout.activity_bruce_compat);

        FragmentManager.enableDebugLogging(true);
        LoaderManager.enableDebugLogging(true);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, BruceFragment.newInstance(0))
                    .commit();
            //TODO:需要测试attach方法，对于的生命周期执行逻辑？？？？？？？
        }else{
            //Activity对相应的Fragment进行重建，重建的时候到底恢复了哪些数据，View内部的context是哪个context（这一个还是上一个）
            //Glide的引用了已经onDestory的Activity是厉害之后产生的，还是回到界面后产生的？？？？？
            //Fragment中显示在界面上的View，getContext会是null吗？？，或者getActivity会已经被destory了吗？？？
        }

        //TODO:设置Layout
        //TODO:初始化布局
        //TODO:初始化成员变量
        //TODO:加载数据
    }

    @Override
    protected void onStart() {
        super.onStart();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        //TODO:这个做什么呢？
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        //TODO:恢复重建数据
    }

    @Override
    protected void onResume() {
        super.onResume();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        //TODO：非焦点Activity，但是可见时候的状态
    }

    @Override
    protected void onPause() {
        super.onPause();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        //TODO:保存系统杀死时当时的数据
    }

    @Override
    protected void onStop() {
        super.onStop();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        //TODO:跳转到其他页面，不可见
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        //TODO:Activity销毁时
    }

    /**-------------------------------偶尔会用到的------------------------------------------*/
    @Override
    protected void onRestart() {
        super.onRestart();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        //TODO:从下一个界面修改了数据，返回刷新可以用
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        //TODO：lanchMode是SingleTop的时候会调用
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        //TODO:旋转屏幕
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        //TODO：显示到屏幕上：可以获取到控件宽高了
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        //TODO：从window上移除，在onDestroy之后调用，基本没什么用
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        //TODO:通过startActivityForResult打开返回数据时调用
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        //TODO:焦点变化时调用，包括下拉通知栏，都会调用
    }
    /**-------------------------------基本不用的------------------------------------------*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        boolean result = super.onPrepareOptionsMenu(menu);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        return result;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    //ViewPager的使用
    //FragmentPagerAdapter：对于不再需要的fragment，选择调用detach方法，仅销毁视图，并不会销毁fragment实例。这个时候保存了mRootView能有更好的效率，当然内存也占用更多
    //FragmentStatePagerAdapter：会销毁不再需要的fragment，当当前事务提交以后，会彻底的将fragmeng从当前Activity的FragmentManager中移除
}
