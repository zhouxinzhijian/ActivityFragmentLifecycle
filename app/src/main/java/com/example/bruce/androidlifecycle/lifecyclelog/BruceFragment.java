package com.example.bruce.androidlifecycle.lifecyclelog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bruce.androidlifecycle.R;

import static com.example.bruce.androidlifecycle.lifecyclelog.Util.LifecycleState.RETURN_FROM_SUPER;
import static com.example.bruce.androidlifecycle.lifecyclelog.Util.recLifeCycle;

public class BruceFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "arg_column_count";
    private Activity mActivity;
    private View mRootView;
    private boolean mCreated;

    public BruceFragment(){//TODO:不要用有参的构造函数，当Fragment重建时，会用默认的构造函数创建新的Fragment，参数会丢失，用setArguments传递参数
        Log.e(getClass().getSimpleName(), "constructor invoke");
    }


    public static BruceFragment newInstance(int arg){
        BruceFragment fragment = new BruceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, arg);
        fragment.setArguments(args);
        return fragment;
    }

    /**-------------------------------经常会用到的------------------------------------------*/
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        //TODO：Attach到到Activity上
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
//        setHasOptionsMenu(true);//创建菜单
//        setRetainInstance(true);
        //TODO：设置上面参数，当配置变化时（旋转屏幕，修改系统字体，修改系统语言等）onCreate不会再调用,会重用上一个Fragment
        //TODO: 当Fragment作为后台工作任务时，将setRetainInstance(true)很有用，比如讲工作线程写到Fragment中。
        //TODO: 如果setRetainInstance(true)，这必须在onCreateView必须重建布局，不能保存上一次的布局，因为那样会导致内存泄露
        //TODO：其他时候没多大用。因为现在的应用多数都不让屏幕旋转，唱吧也不能旋转（即便旋转也可以将对应的Activity设置成android:configChanges="orientation|keyboardHidden|screenSize"，在onConfigurationChanged中处理旋转后的相关值变化，比如屏幕参数）
        //TODO:如果是pad，横竖屏不一样另作详细研究处理
        //1.旋转屏幕后不调用onCreate（Activity销毁重建依然会走）
        //2.使用这种操作的Fragment不能加入backstack后退栈中（为什么？？）
        //还有其他什么用呢？？（在ViewPager的相关操作中会不会起到相应的作用
        //TODO：创建成员变量，普通成员变量赋值，getArguments
        if(getArguments() != null){
            getArguments().getInt(ARG_COLUMN_COUNT);
        }
        //TODO:初始化用到的基本对象
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(getClass().getSimpleName(), "this is " + this);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
//        if(getRetainInstance()){//TODO:可以不要，用后面方式替代
//            if(mRootView != null){//设置了setRetainInstance(true)后，屏幕旋转mView不等于null，并且ParentView此时已经是null了
//                Log.e(getClass().getSimpleName(), "mRootView is not empty");
//                ViewParent parent = mRootView.getParent();
//                Log.e(getClass().getSimpleName(), "mRootView' parent is " + parent);
//                return mRootView;
//            }else{
//                View v = inflater.inflate(R.layout.fragment_test, container, false);
//                recLifeCycle(getClass(), RETURN_FROM_SUPER);
//                mRootView = v;
//                return v;
//            }
//        }
        //TODO:构建布局
        if (mRootView != null) {//FIXME：这个用法只能在特定的情况下使用（使用attach和detach方法的地方，如FragmentViewPagerAdapter，BottomNavigationView），否则会有严重的问题。当内存重启后会导致两个问题，第一内存泄露，第二Glide崩溃（引用了已经destory掉的Activity）
            //FIXME:用Glide的情况，并且用了setRetainInstance(true)，一定不能重用mRootView，切记
            if(mRootView.getParent() != null){
                ((ViewGroup) mRootView.getParent()).removeView(mRootView);
            }
            mCreated = true;

            FragmentActivity activity = (FragmentActivity) mRootView.getContext();
            if(activity == null){
                Log.e(getClass().getSimpleName(), "activity is empty");
            }else{
                Log.e(getClass().getSimpleName(), "activity is not empty");
                Log.e(getClass().getSimpleName(), "activity is " + (activity.isDestroyed() ? "" : "not") + " destory");
            }
            return mRootView;
        }
        mRootView = createView(inflater, container, savedInstanceState);//构建布局
        mCreated = false;

        Context context = mRootView.getContext();
        Log.e(getClass().getSimpleName(), "mRootView's context is " + context.getClass().getSimpleName());

        //test
        mRootView.findViewById(R.id.log_view).addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                Log.e(BruceFragment.class.getSimpleName(), "onViewAttachedToWindow");
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                Log.e(BruceFragment.class.getSimpleName(), "onViewDetachedFromWindow");
            }
        });

        return mRootView;
    }

    //创建View
    protected /*abstract*/ View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        if(!mCreated){
            //TODO:初始化布局,findViewById

//            getFragmentManager();
//            getChildFragmentManager();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        if(savedInstanceState == null){
            //TODO：在这里设置View的初始内容，当Fragment加入BackStack后点击返回，不会在调用该方法，会通过onViewStateRestored直接恢复显示的内容，前提是有在View的onSaveInstanceState保存数据
        }else{
            //TODO:这里恢复数据1
        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        if(savedInstanceState != null){
            //TODO：恢复显示的数据（如果没用重写View的onSaveInstanceState方法保存数据）
            //TODO:恢复变量数据2。（和onActivityCreated对比到底谁更应该恢复数据）
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    @Override
    public void onResume() {
        super.onResume();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    @Override
    public void onPause() {
        super.onPause();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        //TODO:保存系统杀死时当时的数据，1.View的数据，2.对象数据
    }

    @Override
    public void onStop() {
        super.onStop();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    /**-------------------------------偶尔会用到的------------------------------------------*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        //TODO:如果该Fragment还有嵌套的Fragment，那应该将result的结果再分发下去
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //当调用Fragment的hide和show方法后，onResume方法不会被调用，但是该方法会被调用，但是是通过add(Fragment）不会调用该方法
        //TODO:Fragment的显示隐藏回调方法，但是如果第一次加载出来，不会调用，如果程序要在切换显示隐藏状态的时候做一些刷新操作，要特别注意
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //TODO:用于在ViewPager中的Fragment显示隐藏处理，其他时候用不到
        //TODO：利用这个方法可以实现 Fragment 懒加载。当Fragment用在ViewPager中要特别注意，该方法的调用在所有生命周期方法之前（包括onAttach方法，可以看相关源码），所有，如果要在这个方法中做网络请求相关操作，通过bundle传递的参数不能及时获得
    }

    /**-------------------------------基本不用的------------------------------------------*/
    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
        //将Fragment写在layout布局文件中调用
    }

    @Override
    public void onPrepareOptionsMenu(final Menu menu) {
        super.onPrepareOptionsMenu(menu);
        recLifeCycle(getClass(), RETURN_FROM_SUPER);
    }

    //TODO:对于Support 24.0.0以下的版本，采用如下代码来保证内存重启后的Fragment重叠问题
    /*private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
...
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
    ...
            outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
        }*/
}
